package com.example.employeeapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.R
import kotlinx.android.synthetic.main.roles_list_item.view.*

class EmployeeRolesAdapter(
    val rolesList: List<String>,
    var employeeRolesList: MutableList<String>,
    val employeeRolesChangeListener: EmployeeRolesChangeListener
) :
    RecyclerView.Adapter<EmployeeRolesAdapter.EmployeeRoleViewHolder>() {

    private lateinit var roleViewHolder: EmployeeRolesAdapter.EmployeeRoleViewHolder
    val TAG = javaClass.simpleName

    inner class EmployeeRoleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int) {
            with(itemView){
                val role = rolesList[position]
                roleNameTxt.text = role
                roleSelectedCheckBox.isChecked = employeeRolesList.contains(role)

                roleSelectedCheckBox.setOnCheckedChangeListener { buttonView, isChecked->
                    Log.d(TAG, "$TAG roles list altered")
                    if(isChecked){
                        if(!employeeRolesList.contains(role)){
                            employeeRolesList.add(role)
                        }
                    }else{
                        if(employeeRolesList.contains(role)){
                            employeeRolesList.remove(role)
                        }
                    }
                    Log.e(TAG, "$TAG roles list after check change : $employeeRolesList")
                    employeeRolesChangeListener.onEmployeeRolesChanged(employeeRolesList)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeRoleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.roles_list_item, parent, false)
        roleViewHolder = EmployeeRoleViewHolder(itemView)
        return roleViewHolder
    }

    override fun getItemCount(): Int {
       return rolesList.size
    }

    override fun onBindViewHolder(holder: EmployeeRoleViewHolder, position: Int) {
        roleViewHolder.bind(position)
    }
}