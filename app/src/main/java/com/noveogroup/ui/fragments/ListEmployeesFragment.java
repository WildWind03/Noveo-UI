package com.noveogroup.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.noveogroup.ui.R;
import com.noveogroup.ui.activities.MainActivity;
import com.noveogroup.ui.adapters.MyArrayAdapter;
import com.noveogroup.ui.model.Employee;
import com.noveogroup.ui.util.Util;

import java.util.ArrayList;

public class ListEmployeesFragment extends BaseFragment {
    private final static String EMPLOYEES_KEY = "EMPLOYEES_KEY";
    private final static String CURRENT_EMPLOYEE_KEY = "EMPLOYEE_KEY";
    private final static String CURRENT_POS_IN_LIST_VIEW = "POS_IN_LIST_VIEW_KEY";

    private final static int DEFAULT_POS = 0;

    public static ListEmployeesFragment newInstance() {
        return new ListEmployeesFragment();
    }

    private ListView listView;
    private TextView personName;
    private TextView personSkills;
    private Button editSkillsButton;

    private ArrayList<Employee> employees;
    private Employee currentEmployee;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null == savedInstanceState) {
            employees = new ArrayList<>();
        }

        listView = (ListView) view;

        if (isPortrait()) {
            View headerView = createHeader();
            listView.addHeaderView(headerView);

            personName = (TextView) headerView.findViewById(R.id.person_name);
            personSkills = (TextView) headerView.findViewById(R.id.person_skills);
            editSkillsButton = (Button) headerView.findViewById(R.id.edit_skills_button);
        }

        onPostViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            Util.fillEmployeeList(employees);

            if (DEFAULT_POS < employees.size()) {
                currentEmployee = employees.get(DEFAULT_POS);
            } else {
                currentEmployee = new Employee("", "", new String[]{""});
            }
        } else {
            employees = savedInstanceState.getParcelableArrayList(EMPLOYEES_KEY);
            currentEmployee = savedInstanceState.getParcelable(CURRENT_EMPLOYEE_KEY);
        }

        if (isPortrait()) {
            updateView(currentEmployee);

            editSkillsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTypeInformationDialog(currentEmployee);
                }
            });
        }

        MyArrayAdapter arrayAdapter = new MyArrayAdapter(getContext(), android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(arrayAdapter);

        if (null == savedInstanceState) {
            Activity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).onCurrentEmployeeChanged(currentEmployee);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = employees.get(i - listView.getHeaderViewsCount());

                Activity activity = getActivity();
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).onCurrentEmployeeChanged(employee);
                }

                currentEmployee = employee;

            }
        });
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState) {
            listView.setSelection(savedInstanceState.getInt(CURRENT_POS_IN_LIST_VIEW) + listView.getHeaderViewsCount());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(EMPLOYEES_KEY, employees);
        outState.putParcelable(CURRENT_EMPLOYEE_KEY, currentEmployee);
        outState.putInt(CURRENT_POS_IN_LIST_VIEW, listView.getFirstVisiblePosition() - listView.getHeaderViewsCount());
    }

    public void onCurrentEmployeeChanged(Employee employee) {
        updateView(employee);
    }

    public void onPersonSkillsChanged(String newSkills) {
        currentEmployee.setSkills(newSkills);
        updateView(currentEmployee);
    }

    @Override
    protected int getLayout() {
        return R.layout.list_fragment_layout;
    }

    private View createHeader() {
        return getActivity().getLayoutInflater().inflate(R.layout.person_info_fragment_layout, listView, false);
    }

    private void updateView(Employee employee) {
        if (null != employee) {
            personName.setText(getString(R.string.name_surname_template, employee.getName(), employee.getSurname()));
            personSkills.setText(employee.getSkills());
        }
    }
}
