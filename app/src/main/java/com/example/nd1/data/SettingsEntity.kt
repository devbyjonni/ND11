package com.example.nd1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nd1.NEW_SETTINGS_ID
import kotlinx.parcelize.Parcelize
import java.util.*

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
