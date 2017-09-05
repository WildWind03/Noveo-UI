package com.noveogroup.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.noveogroup.ui.R;
import com.noveogroup.ui.model.Employee;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Employee> {
    public MyArrayAdapter(Context context, int resource, List<Employee> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = new TextView(getContext());
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Employee employee = getItem(position);
        viewHolder.textView.setText(getContext().getString(R.string.name_surname_template, employee.getName(), employee.getSurname()));

        return convertView;
    }


    static class ViewHolder {
        TextView textView;
    }
}
