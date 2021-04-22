package com.example.cryptobank.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.CurrencyAdapter
import com.example.cryptobank.datamodel.Currency
import com.example.cryptobank.services.ICryptoCurrencyService
import com.example.cryptobank.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
//        findViewById<Button>(R.id.login).also { img ->
//            SpringAnimation(img, DynamicAnimation.TRANSLATION_X, -110f).apply {
//                //Starting the animation
//                start()
//            }
//        }
        Thread() {
            run {
                Thread.sleep(3000);
            }
            runOnUiThread() {
//                Thread.sleep(7000);
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

            }
        }.start()
        val springAnim = findViewById<Button>(R.id.login).let { img ->
            // Setting up a spring animation to animate the view’s translationY property with the final
            // spring position at 0.
            SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, 0f)
        }
        login.setOnClickListener() {
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, KontoActivity::class.java)

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