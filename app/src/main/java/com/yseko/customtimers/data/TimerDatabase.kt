package com.yseko.customtimers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Timer::class], version = 1, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerDatabase? = null

        fun getDatabase(context: Context): TimerDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimerDatabase::class.java,
                    "timer_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                return instance
            }
        }
    }
}