package com.example.employeeapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.employeeapp.model.Employee


@Dao
interface EmployeeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee : Employee)

    @Query("SELECT * FROM Employee")
    fun getAllEmployee() : LiveData<List<Employee>>

    @Query("SELECT * FROM Employee WHERE id =:userId LIMIT 1")
    suspend fun getEmployee(userId: Int): List<Employee>

    @Delete
    suspend fun delete(employee: Employee)
}