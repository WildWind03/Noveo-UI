package com.noveogroup.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Employee implements Parcelable{
    private final String name;
    private final String surname;
    private List<String> skills;

    public Employee(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.skills =  new LinkedList<>();
    }

    public Employee(String name, String surname, String[] newSkills)
    {
        this.name = name;
        this.surname = surname;
        skills = Arrays.asList(newSkills);
    }

    protected Employee(Parcel in) {
        name = in.readString();
        surname = in.readString();
        skills = in.createStringArrayList();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public void addSkill(String employeeSkill) {
        skills.add(employeeSkill);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getSkills() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String employeeSkill : skills) {
            stringBuilder.append(employeeSkill);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public void clearSkills() {
        skills.clear();;
    }

    public void setSkills(String newSkills) {
        clearSkills();
        String[] skillArr = newSkills.split("\n");

        for(String str : skillArr) {
            skills.add(str);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeStringList(skills);
    }
}
