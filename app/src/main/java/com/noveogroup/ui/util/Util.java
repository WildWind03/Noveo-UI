package com.noveogroup.ui.util;

import com.noveogroup.ui.model.Employee;

import java.util.List;

public class Util {

    private Util() {

    }

    private static final int COUNT_OF_EMPLOYEES = 20;

    public static void init(List<Employee> employees) {
        Employee sasha = new Employee("Alexander", "Chirikhin");
        sasha.addSkill("Java");
        sasha.addSkill("C++");
        sasha.addSkill("C");
        sasha.addSkill("Git");
        sasha.addSkill("SVN");
        sasha.addSkill("Unix");

        Employee putin = new Employee("Vladimir", "Putin");
        putin.addSkill("Ruling");
        putin.addSkill("Karate");
        putin.addSkill("Economics");

        Employee typicalWorker = new Employee("Stypid Stypid Very Stypid", "Person");
        typicalWorker.addSkill("Drinking");
        typicalWorker.addSkill("Smoking");
        typicalWorker.addSkill("Complaining about life");
        typicalWorker.addSkill("Destroying everything");
        typicalWorker.addSkill("Watching TV after working");
        typicalWorker.addSkill("Nothing good");

        employees.add(sasha);
        employees.add(putin);
        employees.add(typicalWorker);

        for (int k = 0; k < COUNT_OF_EMPLOYEES; ++k) {
            Employee employee = new Employee("Name " + k, "Barsik");
            employee.addSkill("Skill" + k);
            employees.add(employee);
        }
    }

    public static int getCountOfLinesInString(String string) {
        String[] lines = string.split("\r\n|\r|\n");
        return lines.length;
    }
}
