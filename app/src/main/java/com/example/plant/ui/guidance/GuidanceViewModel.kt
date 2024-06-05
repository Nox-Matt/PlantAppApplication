package com.example.plant.ui.guidance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListGuidance
import com.example.plant.ListHistory
import kotlinx.coroutines.launch

class GuidanceViewModel: ViewModel() {
    private val _guidanceList = MutableLiveData<ArrayList<ListGuidance>>()
    val guidanceList: LiveData<ArrayList<ListGuidance>> get() = _guidanceList

    fun setGuidance(listGuidance: ArrayList<ListGuidance>) {
        viewModelScope.launch {
            _guidanceList.value = listGuidance
        }
    }
}