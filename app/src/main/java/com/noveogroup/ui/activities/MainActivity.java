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

import com.noveogroup.ui.R;
import com.noveogroup.ui.model.Employee;
import com.noveogroup.ui.util.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private TextView nameTextView;
    private TextView skillsTextView;

    private Button editSkillsButton;

    private final static String EMPLOYEES_KEY = "EMPLOYEES_KEY";
    private final static String CURRENT_EMPLOYEE_KEY = "EMPLOYEE_KEY";
    private final static String CURRENT_POS_IN_LIST_VIEW = "POS_IN_LIST_VIEW";

    private ArrayList<Employee> employees;

    private int posOfCurEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            employees = new ArrayList<>();
            Util.init(employees);
        } else {
            employees = savedInstanceState.getParcelableArrayList(EMPLOYEES_KEY);
            posOfCurEmployee = savedInstanceState.getInt(CURRENT_EMPLOYEE_KEY);
        }

        listView = (ListView) findViewById(R.id.employee_view);

        if (isPortrait()) {
            listView.addHeaderView(createHeader());
        }

        editSkillsButton = (Button) findViewById(R.id.edit_skills_button);
        editSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditSkillsClick(view);
            }
        });

        nameTextView = (TextView) findViewById(R.id.person_name);
        skillsTextView = (TextView) findViewById(R.id.person_skills);

        ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posOfCurEmployee = i - listView.getHeaderViewsCount();
                updateView(employees.get(posOfCurEmployee));
            }
        });

        updateView(employees.get(posOfCurEmployee));

        if (null != savedInstanceState) {
            listView.setSelection(savedInstanceState.getInt(CURRENT_POS_IN_LIST_VIEW));
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(EMPLOYEES_KEY, employees);
        outState.putInt(CURRENT_EMPLOYEE_KEY, posOfCurEmployee);
        outState.putInt(CURRENT_POS_IN_LIST_VIEW, listView.getFirstVisiblePosition() - listView.getHeaderViewsCount());
    }

    public void onEditSkillsClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final Employee employee = employees.get(posOfCurEmployee);

        final EditText editText = new EditText(this);
        editText.setText(employee.getSkills());
        builder
                .setTitle(getString(R.string.edit_skills_dialog_title))
                .setCancelable(true)
                .setView(editText)
                .setNeutralButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        employee.setSkills(editText.getText().toString());
                        updateView(employee);
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
