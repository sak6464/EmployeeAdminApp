package com.example.employeeapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.employeeapp.db.EmployeeDatabase
import com.example.employeeapp.db.EmployeeRepository
import com.example.employeeapp.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EmployeeRepository
    private val TAG = javaClass.name

    val allEmployees: LiveData<List<Employee>>

    private lateinit var currentUser : Employee

    init{
        val employeeDAO = EmployeeDatabase.getDatabase(application).emplyeeDAO()
        repository = EmployeeRepository(employeeDAO)
        allEmployees = repository.allEmployees
    }

    fun insert(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        Log.e(TAG, "$TAG: Inserting user : $employee")
        repository.insert(employee)
    }

    suspend fun getUserById(userId : Int)  : Employee {
        val users = repository.getUserById(userId)
        return users[0]
    }

    fun setCurrentUser(employee: Employee){
        this.currentUser = employee
    }

    fun getCurrentUser() : Employee{
        return currentUser
    }

    fun removeEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        Log.e(TAG, "$TAG: Deleting user : $employee")
        repository.delete(employee)
    }


}