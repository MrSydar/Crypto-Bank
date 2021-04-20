package com.example.cryptobank.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptobank.R
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)

        val db = FirebaseFirestore.getInstance()
//        val user: MutableMap<String, Any> = HashMap()
//        user["login"] = "werew"
//        user["password"] = "asd12345"
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(
//                    "MainActivity",
//                    "DocumentSnapshot added with ID: " + documentReference.id
//                )
//            }
//            .addOnFailureListener { e -> Log.w("MainActivity", "Error adding document", e) }

        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("MainActivity", document.id + " => " + document.data)
                    }
                } else {
                    Log.w("MainActivity", "Error getting documents.", task.exception)
                }
            }
///////////////

        login.setOnClickListener() {
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, AllCryptoActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }

        register.setOnClickListener() {

            Thread() {
                run {

                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }
    }

}