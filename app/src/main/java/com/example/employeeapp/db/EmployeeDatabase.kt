package com.example.employeeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.employeeapp.model.Employee

@Database(entities = [Employee::class], version = 1)
@TypeConverters(RoleListConverter::class)
abstract class EmployeeDatabase : RoomDatabase(){
    abstract fun emplyeeDAO() : EmployeeDAO

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(context: Context): EmployeeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employee_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}