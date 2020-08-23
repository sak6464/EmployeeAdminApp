package com.example.employeeapp.db

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class RoleListConverter {
    @TypeConverter
    fun fromRoleList(tickets: List<String>?): String? {
        if (tickets == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(tickets, type)
    }

    @TypeConverter
    fun toRoleList(rolesString: String?): List<String>? {
        if (rolesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(rolesString, type)
    }
}