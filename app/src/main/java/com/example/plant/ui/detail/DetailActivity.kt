package com.example.plant.ui.detail

import android.os.Bundle
import android.util.Log
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
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.HistoryDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private var description:String? = null
    private var causes:String? = null
    private var treatment:String? = null

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


        val listContent = getDetail()

//        Log.d(TAG, "tes1 ${listContent.get(0)}")

        val sectionsPagerAdapter = SectionPagerAdapter(this, "$description", "$causes", "$treatment")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabAdd.setOnClickListener {
            Log.d(TAG, "$description")
        }



        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun getDetail(): ArrayList<String?> {
        var listContent : ArrayList<String?> = ArrayList()
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzNlNTUwZGYtOTE4ZS00ZGI3LTljNWItYjk2NGRjZjcwYmJiIn0sImlhdCI6MTcxODEyNjI1OH0.ebu6LZ7qdp8V3W6cUnCnGaODvmxf7iKGqCoedgswnCE"
        val id = intent.getStringExtra(ID)
        id?.let { ApiConfig.getApiService().getDetail("Bearer $token", it) }
            ?.enqueue(object : Callback<HistoryDetailResponse> {
                override fun onResponse(
                    call: Call<HistoryDetailResponse>,
                    response: Response<HistoryDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Glide.with(this@DetailActivity)
                                .load(responseBody.data?.imageUrl)
                                .into(binding.imgDetail)
                            binding.txtNama.text = responseBody.data?.diseasesName
                            val percentage = "${responseBody.data?.percentage}"
                            val percentageS = percentage.split(".")
                            val percentageA = percentageS.get(1).substring(0, 2)
                            binding.txtPercentage.text = "(${percentageS.get(0)}.$percentageA%)"
                            val description = responseBody.data?.description
                            val causes = responseBody.data?.causes
                            val treatment = responseBody.data?.treatment
                            setVariation(description, causes,treatment)
                            listContent.add(description)
                            listContent.add(causes)
                            listContent.add(treatment)

                        }
                    }
                }

                override fun onFailure(call: Call<HistoryDetailResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        Log.d(TAG, "testing: ${listContent}")
        return listContent
    }

    private fun setVar(desc:String?, causes:String?, treatment:String?){
        this.description = desc
        this.causes = causes
        this.treatment = treatment
    }



    private fun getdesc():String?{
        return description
    }

    data class setVariation(
        val descripion : String?,
        val causes: String?,
        val treament: String?
    )



    companion object{
        const val ID = "id"

        const val TAG = "DetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Description,
            R.string.causes,
            R.string.treatment
        )
    }
}