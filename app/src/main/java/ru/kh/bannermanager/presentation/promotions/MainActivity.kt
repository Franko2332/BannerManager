package ru.kh.bannermanager.presentation.promotions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kh.bannermanager.data.collectionsrepo.PromotionsRepo
import ru.kh.bannermanager.databinding.ActivityMainBinding
import ru.kh.bannermanager.domain.appstate.DownloadPromotionsAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.presentation.adddocument.AddPromotionActivity

class MainActivity : AppCompatActivity(), PromotionsAdapter.DeleteDocumentListener{
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val observer: Observer<DownloadPromotionsAppState> by lazy { Observer { render(it) } }
    private lateinit var  viewModel: PromotionsViewModel

    private val _adapter = PromotionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    fun init(){
        viewModel = PromotionsViewModel(PromotionsRepo()).apply {
            getData().observe(this@MainActivity, observer)
        }
        binding.addDocumentFab.setOnClickListener{ openAddDocumentActivity()}
    }

    fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.documentsRecyclerView.visibility = View.GONE
    }

    fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        binding.documentsRecyclerView.visibility = View.VISIBLE
    }

    fun showDocuments(data: ArrayList<PromotionEntity>){
        binding.documentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            _adapter.setDeleteListener(this@MainActivity)
            _adapter.setData(data)
            adapter = _adapter
        }

    }

    fun showError(exception: Throwable){
        Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    fun openAddDocumentActivity(){
        val intent = Intent(this, AddPromotionActivity::class.java)
        startActivity(intent)
    }

    fun render(appState: DownloadPromotionsAppState){
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

    override fun deleteDocument(id: String) {
        viewModel.deleteDocument(id)
    }
}