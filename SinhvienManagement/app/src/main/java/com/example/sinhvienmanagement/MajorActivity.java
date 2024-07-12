package com.example.sinhvienmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sinhvienmanagement.Models.Major;
import com.example.sinhvienmanagement.Models.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MajorActivity extends AppCompatActivity {
    private ApiService apiService;
    ListView lvMajor;
    MajorAdapter adapter;
    List<Major> majorList;

    Button btnAddMajor, btnUpdateMajor;
    EditText etMajorName;
    long majorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);

        lvMajor = findViewById(R.id.lvMajor);
        majorList = new ArrayList<>();
        adapter = new MajorAdapter(this, majorList);
        lvMajor.setAdapter(adapter);

        apiService = StudentRepository.getStudentService();

        fetchMajors();

        btnAddMajor = findViewById(R.id.btnAddMajor);
        btnUpdateMajor = findViewById(R.id.btnUpdateMajor);
        etMajorName = findViewById(R.id.etMajorName);

        lvMajor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Major selected = majorList.get(position);
                etMajorName.setText(selected.getNameMajor());

                majorId = selected.getIdMajor();
            }
        });

        btnAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        btnUpdateMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etMajorName.getText().toString();

                Major major = new Major(name);
                Call<Major> call = apiService.updateMajor(majorId, major);
                call.enqueue(new Callback<Major>() {
                    @Override
                    public void onResponse(Call<Major> call, Response<Major> response) {
                        Toast.makeText(MajorActivity.this, "Update successfully",
                                Toast.LENGTH_LONG).show();
                        fetchMajors();
                    }

                    @Override
                    public void onFailure(Call<Major> call, Throwable t) {
                        Toast.makeText(MajorActivity.this, "Update failed"
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        lvMajor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Major selected = majorList.get(position);
                Call<Major> call = apiService.deleteMajor(selected.getIdMajor());
                call.enqueue(new Callback<Major>() {
                    @Override
                    public void onResponse(Call<Major> call, Response<Major> response) {
                        if (response.body() != null) {
                            Toast.makeText(MajorActivity.this, "Delete successfully!",
                                    Toast.LENGTH_LONG).show();
                            fetchMajors();
                        }
                    }

                    @Override
                    public void onFailure(Call<Major> call, Throwable t) {
                        Toast.makeText(MajorActivity.this, "Failed to delete",
                                Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });
    }

    private void add() {
        String name = etMajorName.getText().toString();

        Major major = new Major(name);
        try {
            Call<Major> call = apiService.addMajor(major);
            call.enqueue(new Callback<Major>() {
                @Override
                public void onResponse(Call<Major> call, Response<Major> response) {
                    if (response.body() != null) {
                        Toast.makeText(MajorActivity.this, "Add successfully",
                                Toast.LENGTH_LONG).show();
                        fetchMajors();
                    }
                }

                @Override
                public void onFailure(Call<Major> call, Throwable t) {
                    Toast.makeText(MajorActivity.this, "Add failed"
                            , Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Loi", e.getMessage());
        }
    }

    private void fetchMajors() {
        Call<List<Major>> call = apiService.getAllMajors();
        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, Response<List<Major>> response) {
                List<Major> majors = response.body();
                if (majors == null) {
                    return;
                }
                majorList.clear();
                for (Major major : majors) {
                    majorList.add(major);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                Toast.makeText(MajorActivity.this, "Failed to fetch majors",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}