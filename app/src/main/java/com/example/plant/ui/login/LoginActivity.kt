package com.example.plant.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModelProvider
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityLoginBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.ui.WelcomeActivity
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.LoginResponse
import com.example.plant.ui.register.RegisterActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
    }
    private fun login(username: String, password: String) {
        val client = ApiConfig.getApiService().login(username, password)
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Make sure all of your credential is inputted correctly", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "login: username or password is empty")
        } else {
            client.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.d(TAG, "${responseBody.token}")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.d(RegisterActivity.TAG, "onResponseNull ${response.message()}")
                        }
                    } else {
                        val errorBody = (response?.errorBody() as ResponseBody).string()
                        val error1 = errorBody.split("{")
                        val error2 = error1[1].split("}")
                        val error3 = error2[0].split(",")
                        val error4 = error3[1].split(":")
                        val error5 = error4[1].split("\"")
                        val errorfinal = error5[1]
                        Log.d(TAG, errorfinal)
                        showErrorDialog()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(RegisterActivity.TAG, "onFailure: ${t.message}")
                }

            })
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


