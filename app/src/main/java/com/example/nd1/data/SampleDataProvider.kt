package com.example.nd1.data

import java.util.*

class SampleDataProvider {

    companion object {
        private val habit1 = "Meditate"
        private val habit2 = "Code lessons"
        private val habit3 = "Workout"
        private val habit4 = "Read"
        private val habit5 = "Medicine"


        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        fun getHabits() = arrayListOf(
           HabitEntity(habit1, habit1, false, getDate(0)),
            HabitEntity(habit2, habit2, false, getDate(1)),
            HabitEntity(habit3, habit3, false, getDate(2)),
            HabitEntity(habit4, habit4, false, getDate(3)),
            HabitEntity(habit5, habit5, true, getDate(4))
        )
    }
}