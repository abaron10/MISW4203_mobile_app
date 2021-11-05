package com.example.vinilos.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    fun goToActivity(activityClass: Class<*>?, flags: Int? = null, extras: HashMap<String, String>? = null) {
        val intent = Intent(this, activityClass)
        flags?.let {
            intent.addFlags(flags)
        }

        extras?.let{
            for ((key, value) in extras) {
                println("$key = $value")
                intent.putExtra(key, value)
            }
        }

        startActivity(intent)
    }
}