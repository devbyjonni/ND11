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

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    var habitList = database?.habiteDao()?.getAll(getDateStr(Calendar.getInstance().time))
    var currentHabit = MutableLiveData<HabitEntity>()
    var currentSettings = MutableLiveData<SettingsEntity>()


    private fun addSampleData() {
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

    private fun addSampleData(currentDate: Date) {
        val habitList = mutableListOf<HabitEntity>()

        for (index in 0 until 5) {
            val date = Date(currentDate.time + index)
            var habit = HabitEntity("TEST $index","TEST $index",false, date,0,0,getDateStr(date))
            habitList.add(habit)
        }
        database?.habiteDao()?.insertAll(habitList)
    }

    fun getAllSettings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var settings: SettingsEntity? = database?.settingsDao()?.getSettingsById(NEW_SETTINGS_ID)
                if (settings != null) {
                    Log.i("LOGTEST", "SETTINGS EXIST!" )

                    settings.date.let { it ->

                        val date = Calendar.getInstance().time
                        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
                        val formattedSettingsDate = formatter.format(it)
                        val formattedTodayDate = formatter.format(date)

                        if (formattedSettingsDate?.compareTo(formattedTodayDate)!! > 0 ){
                            Log.i("LOGTEST", "Today date is less than given date" )

                        }else if(formattedSettingsDate?.compareTo(formattedTodayDate)!! < 0 ){
                            Log.i("LOGTEST", "Today date is grater than given date" )
                            addSampleData(date)
                            database?.habiteDao()?.getAll(getDateStr(date))
                            settings!!.date = date
                            database?.settingsDao()?.insertSetting(settings!!)

                        } else if(formattedSettingsDate?.compareTo(formattedTodayDate) == 0 ){
                            Log.i("LOGTEST", "Same1 date" )

                        } else {

                        }
                    }

                } else {
                    Log.i("LOGTEST", "DID CREATE SETTINGS" )
                    settings = SettingsEntity()
                    database?.settingsDao()?.insertSetting(settings)
                    addSampleData()
                }
                currentSettings.postValue(settings!!)
            }
        }
    }
}
