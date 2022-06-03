package com.yseko.customtimers

import android.app.Application
import com.yseko.customtimers.data.TimerDatabase

class TimerApplication: Application() {
    val database: TimerDatabase by lazy {TimerDatabase.getDatabase(this)}
}