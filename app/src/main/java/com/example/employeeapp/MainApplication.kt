package com.example.employeeapp

import android.app.Application
import android.content.Context

class MainApplication: Application()  {
    companion object {
        private var application: Application? = null

        fun getAppContext(): Context? {
            return application?.applicationContext
        }

        fun getApplication(): Application? {
            return application
        }
    }
}