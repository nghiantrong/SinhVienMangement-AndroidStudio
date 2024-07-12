package com.example.sinhvienmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sinhvienmanagement.Models.Major;
import com.example.sinhvienmanagement.Models.Student;

import java.util.List;

public class MajorAdapter extends ArrayAdapter<Major> {
    public MajorAdapter(Context context, List<Major> majors) {
        super(context, 0,  majors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Major major = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_major, parent, false);
        }

        // Lookup view for data population
        TextView tvMajorName = convertView.findViewById(R.id.tvMajorName);
        TextView tvMajorId = convertView.findViewById(R.id.tvMajorId);

        // Populate the data into the template view using the data object
        tvMajorName.setText("Name: " + major.getNameMajor());
        tvMajorId.setText("Id: " + major.getIdMajor());

        // Return the completed view to render on screen
        return convertView;
    }
}
