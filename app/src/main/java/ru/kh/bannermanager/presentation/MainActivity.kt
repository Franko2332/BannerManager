package ru.kh.bannermanager.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import ru.kh.bannermanager.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("promotions").document("1")
        document.get().addOnSuccessListener { document -> if (document != null){
            Log.e("FIRESTORE", "DATA: ${document.data}")
        } else Log.e("FIRESTORE", "NO SUCH DATA")
        }.addOnFailureListener { exception -> Log.e("EXCEPTION", exception.toString()) }
    }
}