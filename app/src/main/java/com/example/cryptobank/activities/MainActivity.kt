package com.example.cryptobank.activities

import android.accounts.Account
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
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
                        enableUI(true)
                        changeActivity(AccountActivity::class, login)
                    } else {
                        Toast.makeText(applicationContext, "Bad password", Toast.LENGTH_SHORT)
                            .show()
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

        setContentView(R.layout.activity_login)
        val loginButton = findViewById<Button>(R.id.login)
        val registerButton = findViewById<Button>(R.id.register)

        val userLogin = findViewById<EditText>(R.id.loginTextEdit)
        val userPassword = findViewById<EditText>(R.id.passwordLoginTextEdit)

        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        // Animation Purpose Thread
        Thread() {
            run {
                Thread.sleep(3000);
            }
            runOnUiThread() {
                findViewById<Button>(R.id.login).also { img ->
                    SpringAnimation(img, DynamicAnimation.TRANSLATION_X, -110f).apply {
                        //Starting the animation
                        start()
                    }
                }
                findViewById<Button>(R.id.register).also { img ->
                    SpringAnimation(img, DynamicAnimation.TRANSLATION_X, 110f).apply {
                        //Starting the animation
                        start()
                    }
                }
                viewArray.add(loginButton)
                viewArray.add(registerButton)
                viewArray.add(userLogin)
                viewArray.add(userPassword)

            }
        }.start()


        loginButton.setOnClickListener {
            signIn(userLogin.text.toString(), userPassword.text.toString())
        }

        registerButton.setOnClickListener {
            changeActivity(RegisterActivity::class)
        }
    }

}
