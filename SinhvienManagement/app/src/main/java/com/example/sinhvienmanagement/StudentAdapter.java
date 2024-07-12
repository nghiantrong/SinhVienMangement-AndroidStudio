package com.example.sinhvienmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sinhvienmanagement.Models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Map<Long, String> majorMap;

    public StudentAdapter(Context context, List<Student> students, Map<Long, String> majorMap) {
        super(context, 0, students);
        this.majorMap = majorMap;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
        }
        Student student = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvGender = convertView.findViewById(R.id.tvGender);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvAddress = convertView.findViewById(R.id.tvAddress);
        TextView tvMajor = convertView.findViewById(R.id.tvMajor);

        tvName.setText("Name: " + student.getName());
        tvDate.setText("Date: " + student.getDate());
        tvGender.setText("Gender: " + student.getGender());
        tvEmail.setText("Email: " + student.getEmail());
        tvAddress.setText("Address: " + student.getAddress());
        if (majorMap != null && majorMap.containsKey(student.getIdMajor())) {
            tvMajor.setText("Major: " + majorMap.get(student.getIdMajor()));
        } else {
            tvMajor.setText("Major: N/A");
        }


        return convertView;
    }
}
