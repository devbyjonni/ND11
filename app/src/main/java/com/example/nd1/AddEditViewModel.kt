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
import java.text.SimpleDateFormat
import java.util.*

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
                        val date = Date()
                        val formatter = SimpleDateFormat.getDateInstance()
                        val formattedDate = formatter.format(date)
                        HabitEntity("", "", false, date, 0, 0, formattedDate)
                    }
                currentHabit.postValue(habit!!)
            }
        }
    }

    fun updateHabit() {
        currentHabit.value?.let {
            it.title = it.title.trim()

            if (it.id == NEW_HABIT_ID && it.title.isEmpty()) {
                //Empty object
                return
            }

            it.timerSeconds = it.timerSeconds * 60

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    database?.habiteDao()?.insertHabit(it)
                }
            }
        }
    }
}