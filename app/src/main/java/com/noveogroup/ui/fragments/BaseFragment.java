package com.noveogroup.ui.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.noveogroup.ui.R;
import com.noveogroup.ui.activities.MainActivity;
import com.noveogroup.ui.interfaces.OnEmployeeChanged;
import com.noveogroup.ui.model.Employee;

abstract public class BaseFragment extends Fragment implements OnEmployeeChanged {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }


    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    final public boolean isPortrait() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
    }

    protected void showTypeInformationDialog(Employee currentEmployee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final EditText editText = new EditText(getContext());
        editText.setText(currentEmployee.getSkills());
        builder
                .setTitle(getString(R.string.edit_skill_dialog_title))
                .setCancelable(true)
                .setView(editText)
                .setNeutralButton(getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newSkills = editText.getText().toString();

                        Activity activity = getActivity();
                        if (activity instanceof MainActivity) {
                            ((MainActivity) activity).onPersonSkillsChanged(newSkills);
                        }
                    }
                })
                .create()
                .show();
    }

    protected abstract int getLayout();
}
