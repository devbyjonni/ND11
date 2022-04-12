package com.example.nd1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(habits: List<HabitEntity>)

    @Query("SELECT * FROM habits ORDER BY date ASC")
    fun getAll(): LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE dateStr = :dateStr ORDER BY date ASC")
    fun getAll(dateStr: String): LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitById(id: Int): HabitEntity?

    @Query("SELECT COUNT(*) from habits")
    fun getCount(): Int

    @Delete
    fun deleteHabits(selectedHabits: List<HabitEntity>): Int

    @Query("DELETE FROM habits WHERE id = :id")
    fun deleteById(id: Int):Int

    @Query("DELETE FROM habits")
    fun deleteAll():Int

    @Delete
    fun deleteHabit(habit: HabitEntity)

}

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