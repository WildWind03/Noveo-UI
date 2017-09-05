package com.noveogroup.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.noveogroup.ui.R;
import com.noveogroup.ui.activities.MainActivity;
import com.noveogroup.ui.model.Employee;

public class PersonInfoFragment extends BaseFragment {

    public static final String EMPLOYEE_KEY = "EMPLOYEE_KEY";

    public static PersonInfoFragment newInstance(Employee employee) {
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EMPLOYEE_KEY, employee);

        personInfoFragment.setArguments(bundle);
        return personInfoFragment;
    }

    private TextView personName;
    private TextView personSkills;
    private Button editSkillsButton;

    private Employee currentEmployee;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personName = (TextView) view.findViewById(R.id.person_name);
        personSkills = (TextView) view.findViewById(R.id.person_skills);
        editSkillsButton = (Button) view.findViewById(R.id.edit_skills_button);

        onPostViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            onCurrentEmployeeChanged((Employee) getArguments().getParcelable(EMPLOYEE_KEY));
        } else {
            Activity myActivity = getActivity();
            if (myActivity instanceof MainActivity) {
                onCurrentEmployeeChanged(((MainActivity) myActivity).get());
            }
        }

        editSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeInformationDialog(currentEmployee);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EMPLOYEE_KEY, currentEmployee);
    }

    @Override
    protected int getLayout() {
        return R.layout.person_info_fragment_layout;
    }

    private void updateView(Employee employee) {
        if (null != employee) {
            personName.setText(getString(R.string.name_surname_template, employee.getName(), employee.getSurname()));
            personSkills.setText(employee.getSkills());
        }
    }

    public void onCurrentEmployeeChanged(Employee employee) {
        this.currentEmployee = employee;
        updateView(employee);
    }

    public void onPersonSkillsChanged(String newSkills) {
        this.currentEmployee.setSkills(newSkills);
        updateView(currentEmployee);
    }
}
