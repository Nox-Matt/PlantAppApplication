package com.example.plant.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.databinding.ActivityRegisterBinding
import com.example.plant.ui.WelcomeActivity
import com.example.plant.ui.login.LoginActivity
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding :ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this@RegisterActivity, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intentWelcome = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                startActivity(intentWelcome)

            }
        })


        val hyperlinkLogin: TextView = findViewById(R.id.hyperlinkLogin)
        hyperlinkLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
//            val intentLogin = Intent(this, LoginActivity::class.java)
//            startActivity(intentLogin)
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            Register(username, password)
        }
    }

    private fun Register(username: String, password: String){
        val client = ApiConfig.getApiService().Register(username, password)
        client.enqueue(object:Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Log.d(TAG, "${responseBody.message}")
                    }else{
                        Log.d(TAG, "onResponseNull ${response.message()}")
                    }
                }else{
                    val errorBody = (response.errorBody() as ResponseBody).toString()
                    Log.d(TAG, errorBody)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "RegisterActivity"
    }
}