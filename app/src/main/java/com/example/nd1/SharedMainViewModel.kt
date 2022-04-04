package com.example.nd1


import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

class SharedMainViewModel : ViewModel() {

    val model: MutableLiveData<List<Habit>> by lazy {
        MutableLiveData<List<Habit>>()
    }

    val selectedModel: MutableLiveData<Habit> by lazy {
        MutableLiveData<Habit>()
    }

    init {
        model.value = listOf<Habit>(
            Habit(0, "Workout", "Completed 10 times", false),
            Habit(1, "Meditate", "Completed 10 times", false),
            Habit(2, "Code lessons", "Completed 10 times", false),
            Habit(3, "Read", "Completed 10 times", true)
        )
    }

    fun load() {
        Log.i("LOGTEST", "VMODEL")
    }

    fun setSelectedHabit() {
        selectedModel.value = Habit(0,"Test","Test",false)
    }

    fun updateData() {
        model.value = model.value?.plus(Habit(4,"New Habit", "Completed 0 times", true)) ?: listOf()
    }
}

@Parcelize
data class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val completed: Boolean
    ) : Parcelable