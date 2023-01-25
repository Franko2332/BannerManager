package ru.kh.bannermanager.presentation.promotions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.core.Completable
import ru.kh.bannermanager.data.collectionsrepo.PromotionsRepo
import ru.kh.bannermanager.databinding.FragmentPromotionsBinding
import ru.kh.bannermanager.domain.appstate.DownloadPromotionsAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.presentation.addpromotion.ShowAddPromotionFragmentListener

class PromotionsFragment: Fragment(), PromotionsAdapter.DeleteDocumentListener {
    private var binding: FragmentPromotionsBinding? = null
    private val _binding get() = binding!!
    private val viewModel: PromotionsViewModel by lazy { PromotionsViewModel(PromotionsRepo())}
    private val observer: Observer<DownloadPromotionsAppState> by lazy { Observer { render(it) } }
    private val _adapter = PromotionsAdapter()
    private lateinit var showAddPromotionFragmentListener: ShowAddPromotionFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        requireActivity()?.let {
            showAddPromotionFragmentListener = it as ShowAddPromotionFragmentListener
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPromotionsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    fun init(){
        viewModel.getData().observe(viewLifecycleOwner,  observer)
        _binding.addDocumentFab.setOnClickListener{ openAddDocumentActivity()}
        _binding.addDocumentFab.setOnClickListener { showAddPromotionFragmentListener.showAddPromotionFragment() }
    }

    private fun showProgressBar(){
        _binding.progressBar.visibility = View.VISIBLE
        _binding.documentsRecyclerView.visibility = View.GONE
    }

    private fun hideProgressBar(){
        _binding.progressBar.visibility = View.GONE
        _binding.documentsRecyclerView.visibility = View.VISIBLE
    }

    private fun showDocuments(data: ArrayList<PromotionEntity>){
        _binding.documentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            _adapter.setDeleteListener(this@PromotionsFragment)
            _adapter.setData(data)
            adapter = _adapter
        }

    }

    private fun showError(exception: Throwable){
        Toast.makeText(requireActivity(), exception.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun openAddDocumentActivity(){

    }

    private fun render(appState: DownloadPromotionsAppState){
        when(appState){
            is DownloadPromotionsAppState.Loading -> {
                showProgressBar()
            }
            is DownloadPromotionsAppState.Success -> {
                hideProgressBar()
                showDocuments(appState.data)
            }
            is DownloadPromotionsAppState.Error -> {
                showError(appState.error)
            }
        }
    }

    override fun deleteDocument(id: String): Completable {
       return viewModel.deleteDocument(id)
    }
}