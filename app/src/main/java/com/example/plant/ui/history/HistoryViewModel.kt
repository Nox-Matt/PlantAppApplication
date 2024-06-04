package com.example.plant.ui.history

import android.content.res.TypedArray
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListHistory
import com.example.plant.R
import com.example.plant.data.FormList
import kotlinx.coroutines.launch

class HistoryViewModel:ViewModel() {
    private val _historyList = MutableLiveData<ArrayList<ListHistory>>()
    val historyList: LiveData<ArrayList<ListHistory>> get() = _historyList



    fun setHistory(listHistory: ArrayList<ListHistory>) {
        viewModelScope.launch {
            _historyList.value = listHistory
        }
    }




}