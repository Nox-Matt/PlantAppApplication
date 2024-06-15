package com.example.plant.ui.guidance

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityDetailGuidanceBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailGuidanceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailGuidanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailGuidanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val detailguidanceViewModel = ViewModelProvider(this).get(DetailGuidanceViewModel::class.java)
        val pref = UserPreference.getInstance(this.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

        datastoreViewModel.getTokenKey().observe(this){
            detailguidanceViewModel.getDetailGuide(it, "${intent.getStringExtra(ID)}")
        }


        detailguidanceViewModel.dataList.observe(this){
            if (it != null) {
                binding.titleDetailGuidance.text = it.title
                Glide.with(this)
                    .load("${it.imageUrl}")
                    .into(binding.imgDetailGuide)
            }
        }

        detailguidanceViewModel.contentList.observe(this){content->
            if(content != null){
                val contentI = content.get(0)
                val sectionsPagerAdapter = SectionGuidanceAdapter(this,
                    "${contentI?.step1Title}", "${contentI?.step1Body}",
                    "${contentI?.step2Title}", "${contentI?.step2Body}",
                    "${contentI?.step3Title}", "${contentI?.step3Body}",
                    "${contentI?.step4Title}", "${contentI?.step4Body}",
                    "${contentI?.step5Title}", "${contentI?.step5Body}")
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.tabs)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
                supportActionBar?.elevation = 0f
            }

        }

        detailguidanceViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.step1,
            R.string.step2,
            R.string.step3,
            R.string.step4,
            R.string.step5
        )

        const val ID = "id"
        const val TAG = "DetailGuidanceActivity"
    }
}