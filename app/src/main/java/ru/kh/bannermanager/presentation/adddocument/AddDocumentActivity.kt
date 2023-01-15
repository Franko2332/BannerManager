package ru.kh.bannermanager.presentation.adddocument

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kh.bannermanager.databinding.ActivityAddDocumentBinding

class AddDocumentActivity:AppCompatActivity() {
    private val binding: ActivityAddDocumentBinding by lazy { ActivityAddDocumentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}