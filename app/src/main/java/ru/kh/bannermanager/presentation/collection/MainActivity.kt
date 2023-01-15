package ru.kh.bannermanager.presentation.collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import ru.kh.bannermanager.data.collectionsrepo.DocumentsRepo
import ru.kh.bannermanager.databinding.ActivityMainBinding
import ru.kh.bannermanager.domain.appstate.DownloadDocumentsAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var  viewModel: DocumentsViewModel
    private lateinit var disposable: Disposable

    private val _adapter = DocumentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
    fun init(){
        viewModel = DocumentsViewModel(DocumentsRepo())
        disposable = viewModel.getData().observeOn(AndroidSchedulers.mainThread()).subscribe{render(it)}
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
            _adapter.setData(data)
            adapter = _adapter
        }

    }


    fun showError(exception: Throwable){
        Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    fun render(appState: DownloadDocumentsAppState){
        when(appState){
            is DownloadDocumentsAppState.Loading -> {
                showProgressBar()
            }
            is DownloadDocumentsAppState.Success -> {
                hideProgressBar()
                showDocuments(appState.data)
            }
            is DownloadDocumentsAppState.Error -> {
                showError(appState.error)
            }
        }
    }
}