package ru.kh.bannermanager.presentation.adddocument

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.Disposable
import ru.kh.bannermanager.data.collectionsrepo.PromotionsRepo
import ru.kh.bannermanager.databinding.ActivityAddDocumentBinding

class AddPromotionActivity:AppCompatActivity() {
    private val binding: ActivityAddDocumentBinding by lazy { ActivityAddDocumentBinding.inflate(layoutInflater) }
    private val viewModel = AddPromotionViewModel(PromotionsRepo())
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun init(){
        binding.fabAddDocument.setOnClickListener {
        }
    }

   /* private fun fieldsValidation(): Boolean{
        if (binding.editTextTitle.text?.trim()!!.isEmpty())
    }*/

}