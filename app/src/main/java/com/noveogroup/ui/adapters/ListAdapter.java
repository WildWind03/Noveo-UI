package com.noveogroup.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noveogroup.ui.R;
import com.noveogroup.ui.model.Employee;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private final List<? extends Employee> employees;
    private final Context context;

    public ListAdapter(Context context, List<? extends Employee> employees) {
        this.employees = employees;
        this.context = context;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int i) {
        return employees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = new TextView(context);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Employee currentEmployee = employees.get(i);

        viewHolder.textView.setText(context.getString(R.string.name_surname_template, currentEmployee.getName(), currentEmployee.getSurname()));
        return viewHolder.textView;
    }

    static class ViewHolder {
        private TextView textView;
    }


}
