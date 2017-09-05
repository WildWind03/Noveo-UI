package com.noveogroup.ui.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.noveogroup.ui.R;
import com.noveogroup.ui.fragments.BaseFragment;
import com.noveogroup.ui.fragments.ListEmployeesFragment;
import com.noveogroup.ui.fragments.PersonInfoFragment;
import com.noveogroup.ui.interfaces.OnEmployeeChanged;
import com.noveogroup.ui.model.Employee;
import com.noveogroup.ui.util.Util;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements OnEmployeeChanged {
    private final static String PERSON_INFO_PART_TAG = "PERSON_INFO_PART_TAG";
    private final static String LIST_OF_EMPLOYEES_TAG = "LIST_OF_EMPLOYEES_TAG";

    private final static String CURRENT_EMPLOYEE_KEY = "CURRENT_EMPLOYEE_KEY";

    private Employee currentEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            currentEmployee = savedInstanceState.getParcelable(CURRENT_EMPLOYEE_KEY);
        }

        setContentView(R.layout.activity_main);

        if (!isPortrait()) {
            Fragment personInfoFragment = getSupportFragmentManager().findFragmentByTag(PERSON_INFO_PART_TAG);

            if (null == personInfoFragment) {
                personInfoFragment = PersonInfoFragment.newInstance(currentEmployee);
                getSupportFragmentManager().beginTransaction().add(R.id.person_information_part, personInfoFragment, PERSON_INFO_PART_TAG).commit();
            }
        }
        if (null == savedInstanceState) {
            Fragment employeesFragment = ListEmployeesFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.list_part, employeesFragment, LIST_OF_EMPLOYEES_TAG).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_EMPLOYEE_KEY, currentEmployee);
    }

    final public boolean isPortrait() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
    }

    public BaseFragment getAppropriateFragmentForPersonInfo() {
        Fragment fragment;

        if (isPortrait()) {
            fragment = getSupportFragmentManager().findFragmentByTag(LIST_OF_EMPLOYEES_TAG);
        } else {
            fragment = getSupportFragmentManager().findFragmentByTag(PERSON_INFO_PART_TAG);
        }

        return (BaseFragment) fragment;
    }

    public Employee get() {
        return currentEmployee;
    }

    @Override
    public void onCurrentEmployeeChanged(Employee employee) {
        this.currentEmployee = employee;

        Fragment fragment = getAppropriateFragmentForPersonInfo();

        if (fragment != null) {
            ((OnEmployeeChanged) fragment).onCurrentEmployeeChanged(employee);
        }
    }

    @Override
    public void onPersonSkillsChanged(String newSkills) {
        Fragment fragment = getAppropriateFragmentForPersonInfo();

        if (newSkills.length() < getResources().getInteger(R.integer.skill_field_max_length) && Util.getCountOfLinesInString(newSkills) < getResources().getInteger(R.integer.max_count_of_lines)) {
            if (fragment != null) {
                ((OnEmployeeChanged) fragment).onPersonSkillsChanged(newSkills);
            }
        } else {
            Toast.makeText(getApplicationContext(), "This person can't have so many skills. It is impossible!", Toast.LENGTH_SHORT).show();
        }
    }
}
