package com.jindvir.dieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jindvir.dieta.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this@LoginScreen , DashboardScreen::class.java))
        }
    }
}