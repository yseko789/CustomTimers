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

    @Update
    suspend fun update(timer: Timer)

    @Delete
    suspend fun delete(timer: Timer)



}