package com.example.vinilos.ui

import android.app.Activity
import android.content.Intent
import android.util.ArrayMap
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        val INTENT_EXTRA_ALBUM_ID: String = "INTENT_EXTRA_ALBUM_ID"
    }

    fun goToActivity(activityClass: Class<*>?, flags: Int? = null, extras: ArrayMap<String, String>? = null) {
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