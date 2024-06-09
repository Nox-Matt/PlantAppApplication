package com.example.plant.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.plant.R
import com.example.plant.databinding.ActivityDetailBinding
import com.example.plant.ui.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val description =intent.getStringExtra(DESCRIPTION)
        val causes = intent.getStringExtra(CAUSES)
        val treatment = intent.getStringExtra(TREATMENT)

        val sectionsPagerAdapter = SectionPagerAdapter(this, "$description", "$causes", "$treatment")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.imgDetail.setImageResource(intent.getIntExtra(PHOTO_DETAIL,0))
        Glide.with(this)
            .load(intent.getStringExtra(PHOTO_DETAIL))
            .into(binding.imgDetail)
        binding.txtNama.text = intent.getStringExtra(DISEASE_NAME)
        val percentage = intent.getStringExtra(PERCENTAGE)
        val percentageS = percentage?.split(".")
        val percentageA = percentageS?.get(1)?.substring(0,2)
        binding.txtPercentage.text = "${percentageS?.get(0)}.$percentageA %"

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }

    companion object{
        const val PHOTO_DETAIL = "photo detail"
        const val DISEASE_NAME = "disease name"
        const val PERCENTAGE = "percentage"
        const val DESCRIPTION = "description"
        const val CAUSES = "causes"
        const val TREATMENT = "treatment"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Description,
            R.string.causes,
            R.string.treatment
        )
    }
}