package com.example.plant.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginButton.setOnClickListener {
            val username = binding.edtTextUsername.text.toString()
            val password = binding.edtTextPass.text.toString()
//            if (username.isNotEmpty() && password.isNotEmpty()) {
//                // Login Logic Later Here
//                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Make sure all of your credential is inputted", Toast.LENGTH_SHORT).show()
//            }

            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }
    }
}
