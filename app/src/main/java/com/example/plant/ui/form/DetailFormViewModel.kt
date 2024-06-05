package com.example.plant.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.data.Comment

class DetailFormViewModel: ViewModel() {
    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>> get() = _commentList

    init {
        // Dummy Data
        _commentList.value = listOf(
            Comment("Marko Polo", "2023-01-05", "Gatau Gan"),
            Comment("Martopo Siliwangis", "2013-01-04", "Caranya Gini Bang")
        )
    }
}