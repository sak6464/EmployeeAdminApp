package com.example.employeeapp.db

import androidx.lifecycle.LiveData
import com.example.employeeapp.model.Employee

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