package com.example.plant.ui.guidance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plant.ui.description.DescriptionFragment

class SectionGuidanceAdapter(activity: AppCompatActivity, private val step1 :String, private val step2: String, private val step3: String, private val step4: String, private val step5:String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 ->{
                fragment =Step1Fragment()
                fragment.arguments = Bundle().apply {
                    putString(Step1Fragment.CONTENT, step1)
                }
            }
            1 ->{
                fragment = Step2Fragment()
                fragment.arguments = Bundle().apply {
                    putString(Step2Fragment.CONTENT, step2)
                }
            }
            2 ->{
                fragment = Step3Fragment()
                fragment.arguments = Bundle().apply {
                    putString(Step3Fragment.CONTENT, step3)
                }
            }
            3 ->{
                fragment = Step4Fragment()
                fragment.arguments = Bundle().apply {
                    putString(Step4Fragment.CONTENT, step4)
                }
            }
            4 ->{
                fragment = Step5Fragment()
                fragment.arguments = Bundle().apply {
                    putString(Step5Fragment.CONTENT, step5)
                }
            }
        }
        return fragment as Fragment
    }

}