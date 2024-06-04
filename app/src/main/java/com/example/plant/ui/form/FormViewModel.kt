package com.example.plant.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.data.FormList

class FormViewModel : ViewModel() {

    private val _formList = MutableLiveData<List<FormList>>()
    val formList: LiveData<List<FormList>> get() = _formList

    init {
        // Dummy Data
        _formList.value = listOf(
            FormList("Budi Setiawan", "2023-01-01", "Cara Membuat Tanaman Hijau?"),
            FormList("Martopo Siliwangi", "2023-01-04", "Cara Menjadi Cediakwan")
        )
    }
}