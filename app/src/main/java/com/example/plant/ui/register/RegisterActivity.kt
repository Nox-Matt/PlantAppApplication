package com.example.plant.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            if (password != confirmPassword) {
                Toast.makeText(this, "Password and Confirm Password must be the same", Toast.LENGTH_SHORT).show()
            }
            else {
                register(username, password)
            }
        }
    }

    private fun register(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Make sure all of your credential is inputted correctly", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "login: username or password is empty")
        } else{
            val client = ApiConfig.getApiService().Register(username, password)
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.d(TAG, "${responseBody.message}")
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.d(TAG, "onResponseNull ${response.message()}")
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

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
        }
    }
    private fun showErrorDialog(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Register Failed")
            .setMessage("An error occurred during register, check your username and password, then Please try again.")
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    companion object{
        const val TAG = "RegisterActivity"
    }
}