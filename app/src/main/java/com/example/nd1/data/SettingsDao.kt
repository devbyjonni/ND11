package com.example.nd1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetting(setting: SettingsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(settings: List<SettingsEntity>)

    @Query("SELECT * FROM settings ORDER BY date ASC")
    fun getAll(): LiveData<List<SettingsEntity>>

    @Query("SELECT * FROM settings WHERE date = :date ORDER BY date ASC")
    fun getAll(date: Date): LiveData<List<SettingsEntity>>

    @Query("SELECT * FROM settings WHERE id = :id")
    fun getSettingsById(id: Int): SettingsEntity?

    @Query("SELECT COUNT(*) from settings")
    fun getCount(): Int

    @Delete
    fun deleteSettings(selectedSettings: List<SettingsEntity>): Int

    @Query("DELETE FROM settings WHERE id = :id")
    fun deleteById(id: Int):Int

    @Query("DELETE FROM settings")
    fun deleteAll():Int

    @Delete
    fun deleteHabit(settings: SettingsEntity)
}