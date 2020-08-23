package com.example.employeeapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.example.employeeapp.db.EmployeeDAO
import com.example.employeeapp.db.EmployeeDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class EmployeeListUITest {
    private lateinit var userDao: EmployeeDAO
    private lateinit var db: EmployeeDatabase

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun verify_if_add_employee_button_is_deisplayed() {
        onView(withId(R.id.addEmployeeBtn)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun verify_if_new_form_displayed_on_add_employee_button_click(){
        onView(withId(R.id.addEmployeeBtn)).perform(click())
        //verify the new form is displayed
        onView(withId(R.id.firstNameTxt)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.lastNameTxt)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //test if the firstname, lastname fields are empty for a new form
        onView(withId(R.id.firstNameTxt)).check(ViewAssertions.matches(withText("")))
        onView(withId(R.id.lastNameTxt)).check(ViewAssertions.matches(withText("")))
    }



}