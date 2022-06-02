package com.yseko.customtimers.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao{
    @Query("SELECT * from timer")
    fun getAllTimers(): Flow<List<Timer>>

    @Query("SELECT * from timer WHERE id = :id")
    fun getTimer(id: Int): Flow<Timer>

    @Insert
    suspend fun insert(timer: Timer)

//    @Update
//    suspend fun update(timer: Timer)

//    @Delete
//    suspend fun delete(timer: Timer)

    @Query("UPDATE timer SET task = :task, hours= :hours, minutes = :minutes, seconds = :seconds WHERE id=:id")
    suspend fun update(id: Int, hours: Int, minutes: Int, seconds: Int, task: String)

    @Query("DELETE FROM timer WHERE id = :id")
    suspend fun deleteById(id: Int)


}