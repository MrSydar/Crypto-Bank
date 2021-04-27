package com.example.cryptobank.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cryptobank.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class RegisterActivity : ChangeableActivity() {
    private fun registerUser(login: String, password: String, passwordConfirm: String) {
        enableUI(false)

        if (password != passwordConfirm) {
            Toast.makeText(applicationContext, "Passwords are not same", Toast.LENGTH_SHORT).show()
            enableUI(true)
            return
        }

        val fdb = FirebaseFirestore.getInstance()
        fdb.document("users/$login").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val doc = task.result
                if (!doc!!.exists()) {
                    val document = fdb.collection("users").document(login)
                    val data = hashMapOf("password" to password)
                    document.set(data, SetOptions.merge()).addOnSuccessListener {
                        changeActivity(LoginActivity::class)
                    }
                } else {
                    Toast.makeText(applicationContext, "User exists", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Connection error", Toast.LENGTH_SHORT).show()
            }

            enableUI(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.register)
        val loginEditor = findViewById<EditText>(R.id.loginTextEdit)
        val passwordEditor = findViewById<EditText>(R.id.passwordLoginTextEdit)
        val passwordConfirmEditor = findViewById<EditText>(R.id.editTextTextPassword2)

        viewArray.add(loginEditor)
        viewArray.add(passwordEditor)
        viewArray.add(passwordConfirmEditor)
        viewArray.add(registerButton)

        registerButton.setOnClickListener {
            registerUser(
                loginEditor.text.toString(),
                passwordEditor.text.toString(),
                passwordConfirmEditor.text.toString()
            )
        }
    }
}