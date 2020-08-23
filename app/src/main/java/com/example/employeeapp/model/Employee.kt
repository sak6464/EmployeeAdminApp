package com.example.employeeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var firstName: String = "",
    var lastName : String = "",
    var email : String = "",
    var userName : String = "",
    var password : String = "",
    var department : String = "",
    var roles : List<String> = listOf()
)
