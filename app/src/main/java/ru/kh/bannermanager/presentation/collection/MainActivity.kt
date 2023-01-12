package ru.kh.bannermanager.presentation.collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kh.bannermanager.databinding.ActivityMainBinding
import ru.kh.bannermanager.domain.appstate.AppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: DocumentsViewModel by viewModels()

    private val observer: Observer<AppState> by lazy { Observer <AppState> {state -> render(state)} }
    private val _adapter = DocumentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    fun init(){
        viewModel.getData().observe(this, observer)

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


    fun showError(exception: Exception){
        Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    fun render(appState: AppState){
        when(appState){
            is AppState.Loading -> {
                showProgressBar()
            }
            is AppState.Success -> {
                hideProgressBar()
                showDocuments(appState.data)
            }
            is AppState.Error -> {
                showError(appState.error)
            }
        }
    }
}