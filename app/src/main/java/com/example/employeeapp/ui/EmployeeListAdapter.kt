package com.example.employeeapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.R
import com.example.employeeapp.model.Employee
import kotlinx.android.synthetic.main.employeelistitem.view.*

class EmployeeListAdapter(var employeeData: MutableList<Employee>) : RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {

    private lateinit var employeeViewHolder: EmployeeViewHolder
    val TAG = javaClass.simpleName

    inner class EmployeeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int) {
            var employee = employeeData[position]
            with(itemView){
                employee_name.text = employee.lastName + " , "+ employee.firstName
                itemRow.setOnClickListener{
                    Log.d(TAG, "$TAG item row clicked")
                    val action =
                        EmployeeListFragmentDirections
                            .actionListFragmentToFormFragment(employee.id)
                    this.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.employeelistitem, parent, false)
        employeeViewHolder = EmployeeViewHolder(itemView)
        return employeeViewHolder
    }

    override fun getItemCount(): Int {
        return employeeData.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        employeeViewHolder.bind(position)
    }


}