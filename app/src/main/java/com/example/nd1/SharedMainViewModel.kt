package com.example.nd1


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedMainViewModel : ViewModel() {

    val model: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    val selectedModel: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        model.value = listOf<String>("Workout", "Meditate", "Code lessons", "Medicine")
    }

    fun updateData() {
        model.value = model.value?.plus("Read") ?: listOf("")
    }
}