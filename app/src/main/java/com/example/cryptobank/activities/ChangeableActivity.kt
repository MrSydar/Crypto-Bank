package com.example.cryptobank.activities

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

open class ChangeableActivity : AppCompatActivity() {
    protected val viewArray: ArrayList<View> = ArrayList()

    protected fun enableUI(enable: Boolean) {
        runOnUiThread{
            for(view in viewArray){
                view.isEnabled = enable
            }
        }
    }

    protected fun <T: Any> changeActivity(cls: KClass<T>) {
        val intent = Intent(this, cls.java)
        startActivity(intent)
        finish()
    }
}