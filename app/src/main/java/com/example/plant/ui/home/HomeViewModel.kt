package com.example.plant.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListHistory
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _historyList = MutableLiveData<ArrayList<ListHistory>>()
    val historyList: LiveData<ArrayList<ListHistory>> get() = _historyList



    fun setHistory(listHistory: ArrayList<ListHistory>) {
        viewModelScope.launch {
            _historyList.value = listHistory
        }
    }
}