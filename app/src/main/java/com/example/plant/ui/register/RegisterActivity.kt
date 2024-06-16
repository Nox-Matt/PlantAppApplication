package com.example.plant.ui.register

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.example.plant.databinding.ActivityRegisterBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
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

        val pref = UserPreference.getInstance(this.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

//        datastoreViewModel.getValid().observe(this){
//            if(it){
//                val intenMain = Intent(this, MainActivity::class.java)
//                startActivity(intenMain)
//            }
//        }


        val hyperlinkLogin: TextView = findViewById(R.id.hyperlinkLogin)
        hyperlinkLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            if(username.length < 4 && username.length > 14){
                showErrorDialog("username must be more 4 element and less than 14 element")
            }
            else if(password != confirmPassword) {
                showErrorDialog("Password and Confirm Password must be the same")
            }
            else if (!password.contains(Regex("[0-9]"))){
                showErrorDialog("Password must contain at least one number")
            }
            else if (!password.contains(Regex("[^A-Za-z0-9]"))){
                showErrorDialog("Password must contain at least one special character")
            }
            else if (username.isEmpty() || password.isEmpty()){
                showErrorDialog("Make sure all of your credential is inputted correctly")
            }
            else {
                register(username, password)
            }
        }
    }

    private fun register(username: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().Register(username, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showDialogSuccess("${responseBody.message}")
                    } else {
                        Log.d(TAG, "onResponseNull ${response.message()}")
                    }
                } else {
                    showLoading(false)
                    val errorBody = (response?.errorBody() as ResponseBody).string()
                    val error1 = errorBody.split("{")
                    val error2 = error1[1].split("}")
                    val error3 = error2[0].split(",")
                    val error4 = error3[1].split(":")
                    val error5 = error4[1].split("\"")
                    val errorfinal = error5[1]
                    Log.d(TAG, errorfinal)
                    showErrorDialog(errorfinal)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

    }
    private fun showErrorDialog(message:String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogcustom)
        dialog.setCancelable(false )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnOK : Button =dialog.findViewById(R.id.btn_ok_err)
        val txterr : TextView = dialog.findViewById(R.id.txt_warning)
        val img : ImageView = dialog.findViewById(R.id.img_dialog)

        txterr.text = "$message"

        btnOK.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogSuccess(message : String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogcustom)
        dialog.setCancelable(false )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnOK : Button =dialog.findViewById(R.id.btn_ok_err)
        val txterr : TextView = dialog.findViewById(R.id.txt_warning)
        val img : ImageView = dialog.findViewById(R.id.img_dialog)

        txterr.text = "$message"

        img.setImageResource(R.drawable.ic_success)

        btnOK.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

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
        const val TAG = "RegisterActivity"
    }
}