package com.example.plant.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityLoginBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.ui.WelcomeActivity
import com.example.plant.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        onBackPressedDispatcher.addCallback(this@LoginActivity, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intentWelcome = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intentWelcome)

            }
        })


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val pref = UserPreference.getInstance(application.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(DataStoreViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val username = binding.edtTextUsername.text.toString()
            val password = binding.edtTextPass.text.toString()
            loginViewModel.login(username, password)

            loginViewModel.isEmpty.observe(this){
                if(it){
                    Toast.makeText(this, "Make sure all of your credential is inputted correctly", Toast.LENGTH_SHORT).show()
                }
            }

            loginViewModel.isError.observe(this){
                if(it == false){
                    loginViewModel.token.observe(this){token->
                        if (token != null) {
                            datastoreViewModel.setTokenKey(token)
                            datastoreViewModel.setValid(true)
                            val intentMain = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intentMain)
                        }
                    }
                }else{
                    showErrorDialog()
                }
            }

            loginViewModel.isLoading.observe(this){
                showLoading(it)
            }
//            login(username, password)
        }

        binding.txtLogin2.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

        datastoreViewModel.getValid().observe(this){
            if(it == false){
                val intenMain = Intent(this, MainActivity::class.java)
                startActivity(intenMain)
            }
        }
    }
    private fun showErrorDialog(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Login Failed")
            .setMessage("An error occurred during login, check your username and password, then Please try again.")
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    companion object{
        const val TAG = "LoginActivity"
    }
}


