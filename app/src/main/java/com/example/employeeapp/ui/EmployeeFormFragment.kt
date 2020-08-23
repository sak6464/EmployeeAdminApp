package com.example.employeeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.employeeapp.R
import com.example.employeeapp.model.Employee
import com.example.employeeapp.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmployeeFormFragment : Fragment() {
    private val args: EmployeeFormFragmentArgs by navArgs()
    private val TAG = javaClass.simpleName
    private lateinit var employeeViewModel : EmployeeViewModel
    private var formView: View? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if(formView == null){
            formView = inflater.inflate(R.layout.fragment_form, container, false)
        }
        return formView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$TAG:: onViewCreated : triggered")
        val userId = args.userId
        Log.d(TAG, "$TAG:: onViewCreated :  userId : $userId")
        populateDepartmentSpinnerData()
        val homeActivity = activity ?: return
        employeeViewModel =
            ViewModelProviders.of(homeActivity).get(EmployeeViewModel::class.java)
        if(userId > 0){
            okBtn.text = "Update"
            lifecycleScope.launch {
                val user = employeeViewModel.getUserById(userId)
                employeeViewModel.setCurrentUser(user)
                updateFormDetails(user)
            }
        }else{
            okBtn.text = "Save"
            val user = Employee()
            employeeViewModel.setCurrentUser(employee = user)
        }

        rolesBtn.setOnClickListener { view ->
            Log.d(TAG, "$TAG:: onViewCreated : rolesBtn clicked userId = $userId")
            val action =
                EmployeeFormFragmentDirections
                    .actionFormToDialogFragment(userId)
            view.findNavController().navigate(action)
        }

        okBtn.setOnClickListener {
            Log.e(TAG, "$TAG : update save/update user, insert into database")
            employeeViewModel =
                ViewModelProviders.of(homeActivity).get(EmployeeViewModel::class.java)
            val currentUser = employeeViewModel.getCurrentUser()
            currentUser.firstName = firstNameTxt.text.toString()
            currentUser.lastName = lastNameTxt.text.toString()
            currentUser.email = emailTxt.text.toString()
            currentUser.department = departmentSpinner.selectedItem.toString()
            currentUser.password = passwordTxt.text.toString()
            currentUser.userName = userNameTxt.text.toString()
            employeeViewModel.insert(currentUser)
            findNavController().popBackStack()
        }

        cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Set available options in departments spinner
     */
    private fun populateDepartmentSpinnerData(){
        val homeActivity = activity ?: return
        val departments = homeActivity.resources.getStringArray(R.array.departments_array).toList()
        val adapter = ArrayAdapter(homeActivity.applicationContext,
            android.R.layout.simple_spinner_dropdown_item, departments)
        departmentSpinner.adapter = adapter
    }

    /**
     * Populate the form with existing employee data
     */
    private fun updateFormDetails(employee:  Employee) {
        val homeActivity = activity ?: return
        val departments = homeActivity.resources.getStringArray(R.array.departments_array).toList()
        firstNameTxt.setText(employee.firstName)
        emailTxt.setText(employee.email)
        lastNameTxt.setText(employee.lastName)
        userNameTxt.setText(employee.userName)
        passwordTxt.setText(employee.password)
        confirmPasswordTxt.setText(employee.password)
        departmentSpinner.setSelection(departments.indexOf(employee.department))
    }
}