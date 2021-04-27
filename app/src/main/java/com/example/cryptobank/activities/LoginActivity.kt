package com.example.cryptobank.activities

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cryptobank.R
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : ChangeableActivity() {
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

    private fun prepareHeightAnimation(view: View) : ValueAnimator {
        val animation = ValueAnimator.ofInt(view.measuredHeight, 100)
        animation.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.height = currentValue
            view.layoutParams = layoutParams
        }
        animation.duration = 500

        return animation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val loginButton = findViewById<Button>(R.id.login)
        val registerButton = findViewById<Button>(R.id.register)

        val userLogin = findViewById<EditText>(R.id.loginTextEdit)
        val userPassword = findViewById<EditText>(R.id.passwordLoginTextEdit)

        loginButton.setOnClickListener {
            signIn(userLogin.text.toString(), userPassword.text.toString())
        }

        registerButton.setOnClickListener {
            changeActivity(RegisterActivity::class)
        }

        val animation = prepareHeightAnimation(findViewById(R.id.loginButtonContainer))
        runOnUiThread {
            Thread {
                Thread.sleep(1000)
                runOnUiThread {
                    animation.start()
                }
            }.start()
        }
    }

}
