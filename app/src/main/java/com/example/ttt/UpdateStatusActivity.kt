package com.example.ttt

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateStatusActivity {
    private val db = Firebase.firestore
    fun updateScore(usid: String){
        val docRef = db.collection("users").document(usid)
        var score: Int = 0
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                   score = document.getLong("numberOfWIns")?.toInt()!!
                }
            }
        docRef.update("numberOfWIns", score+1)
    }
}