package com.example.plant.ui.form

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.R
import com.example.plant.databinding.ActivityAddFormBinding
import com.example.plant.databinding.ActivityDetailFormBinding

class AddFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }
}