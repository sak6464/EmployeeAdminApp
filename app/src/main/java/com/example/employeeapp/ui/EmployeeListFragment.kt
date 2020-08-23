package com.example.employeeapp.ui

import android.graphics.Canvas
import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.R
import com.example.employeeapp.model.Employee
import com.example.employeeapp.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.fragment_emp_list.*


/**
 * Holds the list of employees- this is the first navigation destination in this app.
 */
class EmployeeListFragment : Fragment() {
    private lateinit var employeeListAdapter: EmployeeListAdapter
    private lateinit var employeeViewModel: EmployeeViewModel
    val TAG = javaClass.simpleName

    private var colorDrawableBackground = ColorDrawable(parseColor("#FF0000"))
    private lateinit var deleteIcon: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emp_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "$TAG:: onActivityCreated : triggered")
        val homeActivity = activity ?: return
        employeeViewModel =
            ViewModelProviders.of(homeActivity).get(EmployeeViewModel::class.java)
        employeeViewModel.allEmployees.observe(viewLifecycleOwner, Observer { employeeList ->
            Log.d(TAG, "$TAG:: onActivityCreated : employee info changed")
            updateEmployeeList(employeeList)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeActivity = activity ?: return
        deleteIcon = ContextCompat.getDrawable(homeActivity, R.drawable.ic_delete)!!
        addEmployeeBtn.setOnClickListener { _ ->
            Log.d(TAG, "$TAG:: onViewCreated : addEmployeeBtn clicked")
            val action =
                EmployeeListFragmentDirections
                    .actionListFragmentToFormFragment(0)
            view.findNavController().navigate(action)
        }
    }

    /**
     * Update the employee list when the data is changed in the repository
     */
    private fun updateEmployeeList(employeeList: List<Employee>) {
        Log.d(TAG, "$TAG:: updateEmployeeList : triggered")
        if (employeeList.isEmpty()) {
            Log.d(TAG, "$TAG:: updateEmployeeList : employeeList empty")
            recycler_employee_list.visibility = View.GONE
            return
        }
        recycler_employee_list.visibility = View.VISIBLE
        recycler_employee_list.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        employeeListAdapter = EmployeeListAdapter(employeeList.toMutableList())
        recycler_employee_list.adapter = employeeListAdapter
        recycler_employee_list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        val itemTouchHelper = ItemTouchHelper(getSwipeToLeftCallback(employeeList))
        itemTouchHelper.attachToRecyclerView(recycler_employee_list)
        employeeListAdapter.notifyDataSetChanged()
    }


    /**
     * When a row in our recyclerview is swiped left, we delete the item from our database. This in turn will
     * trigger our livedata observer, which will then update the UI.
     *
     * Overriding the onChildDraw here to draw a red bar with the delete icon
     */
    private fun getSwipeToLeftCallback(employeeList: List<Employee>): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder2: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                Log.d(TAG, "$TAG:: updateEmployeeList : triggered")
                val position = viewHolder.adapterPosition
                employeeViewModel.removeEmployee(employeeList[position])
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                        itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical,
                        itemView.bottom - iconMarginVertical
                    )
                    deleteIcon.level = 0
                }

                colorDrawableBackground.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

}