package com.example.nd1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nd1.NEW_HABIT_ID
import com.example.nd1.NEW_SETTINGS_ID
import kotlinx.parcelize.Parcelize

import java.util.*


@Parcelize
@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    val description: String,
    var isCompleted: Boolean,
    var date: Date,
    var timerSeconds: Long,
    var totalCompletions: Int,
    var dateStr: String,
) : Parcelable {
    constructor() : this(NEW_HABIT_ID, "", "", false, Date(), 10, 0, "")
    constructor(title: String, description: String, completed: Boolean, date: Date, timerSeconds: Long, totalCompletions: Int, dateStr: String) : this(NEW_HABIT_ID, title, description, completed, date, timerSeconds, totalCompletions, dateStr)
}

@Parcelize
@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var date: Date,
    var didLoadSampleData: Boolean,
) : Parcelable {
    constructor() : this(NEW_SETTINGS_ID, Date(), true)
}