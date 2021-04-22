package com.example.cryptobank.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cryptobank.R
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ChangeableActivity() {
    private fun isGoodString(str: String): Boolean {
        return !(str.isEmpty() || str.isBlank())
    }

    private fun signIn(login: String, password: String) {
        enableUI(false)

        if (!isGoodString(login) || !isGoodString(password)) {
            Toast.makeText(applicationContext, "Empty field given", Toast.LENGTH_SHORT).show()
            enableUI(true)
            return
        }

        val fdb = FirebaseFirestore.getInstance()
        fdb.document("users/$login").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val doc = task.result
                if (doc!!.exists()) {
                    val realPassword: String = doc.get("password").toString()
                    if (password == realPassword) {
                        changeActivity(KontoActivity::class)
                    } else {
                        Toast.makeText(applicationContext, "Bad password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "No such user", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Connection error", Toast.LENGTH_SHORT).show()
            }

            enableUI(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val loginButton = findViewById<Button>(R.id.login)
        val registerButton = findViewById<Button>(R.id.register)

        val userLogin = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val userPassword = findViewById<EditText>(R.id.editTextTextPassword)

        viewArray.add(loginButton)
        viewArray.add(registerButton)
        viewArray.add(userLogin)
        viewArray.add(userPassword)

        ////////////////////////////////////////
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
        //
        //        db.collection("users")
        //            .get()
        //            .addOnCompleteListener { task ->
        //                if (task.isSuccessful) {
        //                    for (document in task.result!!) {
        //                        Log.d("MainActivity", document.id + " => " + document.data)
        //                    }
        //                } else {
        //                    Log.w("MainActivity", "Error getting documents.", task.exception)
        //                }
        //            }
        ////////////////////////////////////////

        loginButton.setOnClickListener {
            signIn(userLogin.text.toString(), userPassword.text.toString())
        }

        registerButton.setOnClickListener {
            changeActivity(RegisterActivity::class)
        }
    }

}