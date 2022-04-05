package com.example.nd1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nd1.NEW_HABIT_ID
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String,
    val description: String,
    val completed: Boolean,
    var date: Date
) : Parcelable {
    constructor() : this(NEW_HABIT_ID, "", "", false, Date())
    constructor(title: String, description: String, completed: Boolean, date: Date) : this(NEW_HABIT_ID, title, description, completed, date)
}