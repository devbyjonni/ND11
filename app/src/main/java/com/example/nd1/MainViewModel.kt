package com.example.nd1


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nd1.data.AppDatabase
import com.example.nd1.data.HabitEntity
import com.example.nd1.data.SampleDataProvider
import com.example.nd1.data.SettingsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    var habitList = database?.habiteDao()?.getAll()
    var currentHabit = MutableLiveData<HabitEntity>()
    var currentSettings = MutableLiveData<SettingsEntity>()


    private fun updateForNewDate() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleHabits = SampleDataProvider.getHabits()
                database?.habiteDao()?.insertAll(sampleHabits)
            }
        }
    }

    fun deleteHabits(selectedHabits: List<HabitEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.habiteDao()?.deleteHabits(selectedHabits)
            }
        }
    }

    fun updateHabit() {
        currentHabit.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    database?.habiteDao()?.insertHabit(it)
                }
            }
        }
    }

    private fun getDateStr(date: Date): String {
        val formatter = SimpleDateFormat.getDateInstance()
        return formatter.format(date)
    }

    private fun updateForNewDate(currentDate: Date) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                var sampleHabits = listOf<HabitEntity>()
                sampleHabits = habitList?.value!!
                for (habit in sampleHabits) {
                    habit.isCompleted = false
                }

                database?.habiteDao()?.insertAll(sampleHabits)
            }
        }



    }

    fun getAllSettings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var settings: SettingsEntity? =
                    database?.settingsDao()?.getSettingsById(NEW_SETTINGS_ID)
                if (settings != null) {
                   // Log.i(TAG, "SETTINGS EXIST!")

                    settings.date.let { it ->

                        val date = Calendar.getInstance().time
                        val formatter =
                            SimpleDateFormat.getDateInstance() //or use getDateInstance()
                        val formattedSettingsDate = formatter.format(it)
                        val formattedTodayDate = formatter.format(date)

                        when {
                            formattedSettingsDate.compareTo(formattedTodayDate) > 0 -> {
                                //Log.i(TAG, "Today date is less than given date1")

                            }
                            formattedSettingsDate.compareTo(formattedTodayDate) < 0 -> {
                                //Log.i(TAG, "Today date is grater than given date")
                                updateForNewDate(date)
                                settings!!.date = date
                                database?.settingsDao()?.insertSetting(settings!!)

                            }
                            formattedSettingsDate.compareTo(formattedTodayDate) == 0 -> {
                                //Log.i(TAG, "Same date")

                            }
                            else -> {}
                        }
                    }
                } else {
                    //Log.i(TAG, "DID CREATE SETTINGS")
                    settings = SettingsEntity()
                    database?.settingsDao()?.insertSetting(settings)
                    updateForNewDate()
                }
                currentSettings.postValue(settings!!)
            }
        }
    }
}
