package com.example.plant.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.databinding.ActivityRegisterBinding
import com.example.plant.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding :ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hyperlinkLogin: TextView = findViewById(R.id.hyperlinkLogin)
        hyperlinkLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }
}