package com.example.employeeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.navigation.Navigation.findNavController
import com.example.employeeapp.db.EmployeeDatabase

class MainActivity : AppCompatActivity() {

    private var employeeDatabase: EmployeeDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeDatabase = EmployeeDatabase.getDatabase(context = this)
    }

    override fun onSupportNavigateUp(): Boolean {
       return findNavController(this, R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}