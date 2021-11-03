package com.example.vinilos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.vinilos.R

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var btn_sign_as_user: Button
    private lateinit var btn_sign_as_collector: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        btn_sign_as_user = findViewById(R.id.btn_sign_as_user)
        btn_sign_as_collector = findViewById(R.id.btn_sign_as_collector)

        btn_sign_as_user.setOnClickListener(this)
        btn_sign_as_collector.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        goToActivity(MenuActivity::class.java)
    }
}