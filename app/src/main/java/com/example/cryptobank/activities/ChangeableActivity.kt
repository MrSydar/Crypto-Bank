package com.example.cryptobank.activities

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

open class ChangeableActivity : AppCompatActivity() {
    protected val viewArray: ArrayList<View> = ArrayList()

    protected fun enableUI(enable: Boolean) {
        runOnUiThread {
            for (view in viewArray) {
                view.isEnabled = enable
            }
        }
    }

    protected fun <T : Any> changeActivity(
        cls: KClass<T>,
        extra: String? = null,
        extraName: String? = "userIN"
    ) {
        val intent = Intent(this, cls.java)
        extra?.let {
            intent.putExtra(extraName, extra)
        }
        startActivity(intent)
        finish()
    }
}