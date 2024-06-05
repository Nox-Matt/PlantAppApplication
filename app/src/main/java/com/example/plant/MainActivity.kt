package com.example.plant

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.plant.databinding.ActivityMainBinding
import com.example.plant.ui.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //Close Application Function
        onBackPressedDispatcher.addCallback(this@MainActivity, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(startMain)
            }
        })

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_Home, R.id.navigation_History, R.id.navigation_Camera, R.id.navigation_Form, R.id.navigation_Guidance
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        binding.btnIntent.setOnClickListener {
//            val intent = Intent(this, WelcomeActivity::class.java)
//            startActivity(intent)
//        }
    }
}