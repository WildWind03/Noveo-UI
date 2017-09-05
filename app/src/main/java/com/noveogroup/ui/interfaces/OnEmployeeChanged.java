package com.noveogroup.ui.interfaces;

import com.noveogroup.ui.model.Employee;

public interface OnEmployeeChanged {
    void onCurrentEmployeeChanged(Employee employee);

    void onPersonSkillsChanged(String newSkills);
}
