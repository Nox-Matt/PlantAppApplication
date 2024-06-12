package com.example.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference

class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreViewModel::class.java)) {
            return DataStoreViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}