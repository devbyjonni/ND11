package com.example.nd1

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nd1.data.AppDatabase
import com.example.nd1.data.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val currentHabit = MutableLiveData<HabitEntity>()

    fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val habit =
                    if (habitId != NEW_HABIT_ID) {
                        database?.habiteDao()?.getHabitById(habitId)
                    } else {
                        HabitEntity()
                    }
                currentHabit.postValue(habit!!)
            }
        }
    }

    fun updateHabit() {
        currentHabit.value?.let {
            it.title = it.title.trim()
            if (it.id == NEW_HABIT_ID && it.title.isEmpty()) {
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.title.isEmpty()) {
                       // database?.habiteDao()?.deleteHabit(it)
                    } else {
                        database?.habiteDao()?.insertHabit(it)
                    }
                }
            }
        }
    }
}