package com.example.nd1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nd1.data.AppDatabase
import com.example.nd1.data.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TimerViewModel(app: Application) : AndroidViewModel(app) {
    private val database = AppDatabase.getInstance(app)
   val currentHabit = MutableLiveData<HabitEntity>()

    fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.habiteDao()?.getHabitById(habitId).let {
                    currentHabit.postValue(it)
                }
            }
        }
    }

    fun updateHabit() {
        currentHabit.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    database?.habiteDao()?.insertHabit(it)
                    currentHabit.postValue(it)
                }
            }
        }
    }
}