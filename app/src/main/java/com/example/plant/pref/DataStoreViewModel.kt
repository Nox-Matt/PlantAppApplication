package com.example.plant.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: UserPreference): ViewModel() {
    fun getTokenKey(): LiveData<String>{
        return pref.getTokenKey().asLiveData()
    }

    fun setTokenKey(auth:String){
        viewModelScope.launch {
            pref.setTokenKey(auth)
        }
    }

    fun getValid():LiveData<Boolean>{
        return pref.getValid().asLiveData()
    }

    fun setValid(valid:Boolean){
        viewModelScope.launch {
            pref.setValid(valid)
        }
    }
}