package com.example.plant.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plant.ui.Treatment.TreatmentFragment
import com.example.plant.ui.causes.CausesFragment
import com.example.plant.ui.description.DescriptionFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val description: String, private val causes : String, private val treatment: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = DescriptionFragment()
                fragment.arguments = Bundle().apply {
                    putString(DescriptionFragment.DESCRIPTION, description)
                }
            }
            1 -> {
                fragment = CausesFragment()
                fragment.arguments = Bundle().apply {
                    putString(CausesFragment.CAUSES, causes)
                }
            }
            2 -> {
                fragment = TreatmentFragment()
                fragment.arguments = Bundle().apply {
                    putString(TreatmentFragment.TREATMENT, treatment)
                }
            }
        }
        return fragment as Fragment
    }
}