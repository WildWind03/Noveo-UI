package com.noveogroup.ui.activities;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noveogroup.ui.R;
import com.noveogroup.ui.adapters.MyArrayAdapter;
import com.noveogroup.ui.model.Employee;
import com.noveogroup.ui.util.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private TextView nameTextView;
    private TextView skillsTextView;

    private final static String EMPLOYEES_KEY = "EMPLOYEES_KEY";
    private final static String CURRENT_EMPLOYEE_KEY = "EMPLOYEE_KEY";
    private final static String CURRENT_POS_IN_LIST_VIEW = "POS_IN_LIST_VIEW";

    private ArrayList<Employee> employees;

    private int positionOfCurrentEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            employees = new ArrayList<>();
            Util.init(employees);
        } else {
            employees = savedInstanceState.getParcelableArrayList(EMPLOYEES_KEY);
            positionOfCurrentEmployee = savedInstanceState.getInt(CURRENT_EMPLOYEE_KEY);
        }

        listView = (ListView) findViewById(R.id.employees_view);

        if (isPortrait()) {
            listView.addHeaderView(createHeader());
        }


        Button editSkillsButton = (Button) findViewById(R.id.edit_skills_button);
        editSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditSkillsClick(view);
            }
        });

        nameTextView = (TextView) findViewById(R.id.person_name);
        skillsTextView = (TextView) findViewById(R.id.person_skills);

        ArrayAdapter<Employee> arrayAdapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionOfCurrentEmployee = i - listView.getHeaderViewsCount();
                updateView(employees.get(positionOfCurrentEmployee));
            }
        });

        updateView(employees.get(positionOfCurrentEmployee));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(EMPLOYEES_KEY, employees);
        outState.putInt(CURRENT_EMPLOYEE_KEY, positionOfCurrentEmployee);
        outState.putInt(CURRENT_POS_IN_LIST_VIEW, listView.getFirstVisiblePosition() - listView.getHeaderViewsCount());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            listView.setSelection(savedInstanceState.getInt(CURRENT_POS_IN_LIST_VIEW) + listView.getHeaderViewsCount());
        }
    }

    public void onEditSkillsClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final Employee employee = employees.get(positionOfCurrentEmployee);

        final EditText editText = new EditText(this);
        editText.setText(employee.getSkills());
        builder
                .setTitle(getString(R.string.edit_skills_dialog_title))
                .setCancelable(true)
                .setView(editText)
                .setNeutralButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newSkills = editText.getText().toString();
                        if (newSkills.length() <= getResources().getInteger(R.integer.max_skills_length)
                                && Util.getCountOfLinesInString(newSkills) < getResources().getInteger(R.integer.max_count_of_lines_in_skills)) {
                            employee.setSkills(newSkills);
                            updateView(employee);
                        } else {
                            Toast.makeText(getApplicationContext(), "So many skills! It is impossible to have them all!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create()
                .show();
    }

    private View createHeader() {
        return getLayoutInflater().inflate(R.layout.person_info_layout, listView, false);
    }

    private boolean isPortrait() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
    }

    private void updateView(Employee curEmployee) {
        skillsTextView.setText(curEmployee.getSkills());
        nameTextView.setText(getString(R.string.name_surname_template, curEmployee.getName(), curEmployee.getSurname()));
    }
}
