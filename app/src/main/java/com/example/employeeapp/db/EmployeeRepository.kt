package com.example.employeeapp.db

import androidx.lifecycle.LiveData
import com.example.employeeapp.model.Employee

/**
 * Employee repository gets/passes data to Employee Database. We are only keeping a local database here and
 * not a network data source,so our data comes/goes only to local DB.
 * So ROOM/sqlite is our single source of truth.
 */
class EmployeeRepository(private val employeeDAO: EmployeeDAO) {
    val allEmployees: LiveData<List<Employee>> = employeeDAO.getAllEmployee()

    suspend fun insert(employee: Employee) {
        employeeDAO.insertEmployee(employee)
    }

     suspend fun getUserById(userId : Int): List<Employee> {
        return employeeDAO.getEmployee(userId)
    }

    suspend fun delete(employee: Employee) {
        employeeDAO.delete(employee)
    }
}