package com.example.nd1.data

import java.text.SimpleDateFormat
import java.util.*

class SampleDataProvider {

    companion object {
        private const val habit1 = "Meditate"
        private const val habit2 = "Yoga"
        private const val habit3 = "Workout"
        private const val habit4 = "Read"
        private const val habit5 = "Vitamins"

        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        private fun getDateStr(): String {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateInstance()

            return formatter.format(date)
        }

        fun getHabits() = arrayListOf(
            HabitEntity(habit1, habit1, false, getDate(0), 3600, 0, getDateStr()),
            HabitEntity(habit2, habit2, false, getDate(1), 1200, 0, getDateStr()),
            HabitEntity(habit3, habit3, false, getDate(2), 600, 0, getDateStr()),
            HabitEntity(habit4, habit4, false, getDate(3), 1200, 0, getDateStr()),
            HabitEntity(habit5, habit5, false, getDate(4), 600, 0, getDateStr())
        )
    }
}