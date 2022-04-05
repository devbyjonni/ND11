package com.example.nd1


import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nd1.data.AppDatabase
import com.example.nd1.data.SampleDataProvider
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val habitList = database?.noteDao()?.getAll()

    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleHabits = SampleDataProvider.getHabits()
                database?.noteDao()?.insertAll(sampleHabits)
            }
        }
    }
}
