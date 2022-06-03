package com.yseko.customtimers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer (
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    @ColumnInfo(name = "hours")
    val hours: Int,
    @ColumnInfo(name = "minutes")
    val minutes: Int,
    @ColumnInfo(name = "seconds")
    val seconds: Int,
    @ColumnInfo(name="task")
    val task: String,
    @ColumnInfo(name = "order")
    val order: Int=0
    )