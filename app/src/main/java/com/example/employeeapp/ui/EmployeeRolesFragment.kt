package com.example.employeeapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeeapp.R
import com.example.employeeapp.model.Employee
import com.example.employeeapp.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.fragment_roles.*

class EmployeeRolesFragment : DialogFragment() {
    private val args: EmployeeRolesFragmentArgs by navArgs()
    private val TAG = javaClass.simpleName
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var rolesListAdapter: EmployeeRolesAdapter
    private lateinit var employeeRolesChangeListener: EmployeeRolesChangeListener
    private lateinit var currentUser: Employee

    private var currentRolesList : List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roles, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$TAG:: onViewCreated : triggered")
        val userId = args.userId
        Log.d(TAG, "$TAG:: onViewCreated :  userId : $userId")
        val homeActivity = activity ?: return
        employeeViewModel =
            ViewModelProviders.of(homeActivity).get(EmployeeViewModel::class.java)
        currentUser = employeeViewModel.getCurrentUser()
        currentRolesList = currentUser.roles
        Log.e(TAG, "$TAG currentuser $currentUser")
        updateRolesUIForEmployee()
        okBtn.setOnClickListener {
            Log.d(TAG, "$TAG : update current user's roles, but dont save to db")
            currentUser.roles = currentRolesList
            employeeViewModel.setCurrentUser(employee = currentUser)
            findNavController().popBackStack()
        }
        cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun updateRolesUIForEmployee() {
        employeeRolesChangeListener = object : EmployeeRolesChangeListener {
            override fun onEmployeeRolesChanged(rolesList: MutableList<String>) {
                currentRolesList = rolesList
            }
        }
        recycler_roles_list.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val homeActivity = activity ?: return
        val rolesArray = homeActivity.resources.getStringArray(R.array.roles_array).toList()
        rolesListAdapter = EmployeeRolesAdapter(
            rolesArray,
            currentUser.roles.toMutableList(),
            employeeRolesChangeListener
        )
        recycler_roles_list.adapter = rolesListAdapter
    }

}