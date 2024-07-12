package com.example.sinhvienmanagement;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView lvManagement;
    private StudentAdapter studentAdapter;
    private ApiService apiService;
    List<Student> studentList;

    Button btnAdd, btnUpdate,btnMajor;
    EditText etName, etDate, etGender, etEmail, etAddress, etMajor;
    long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvManagement = findViewById(R.id.lvManagement);
        studentList = new ArrayList<>();
        Map<Long, String> majorMap = new HashMap<>();
        studentAdapter = new StudentAdapter(this, studentList, majorMap);
        lvManagement.setAdapter(studentAdapter);

        apiService = StudentRepository.getStudentService();

        fetchStudentsAndMajors();

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnMajor = findViewById(R.id.btnMajor);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etGender = findViewById(R.id.etGender);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etMajor = findViewById(R.id.etMajor);

        lvManagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (studentList.size() > position) { // Check if position is valid
                    Student selected = studentList.get(position);
                    etName.setText(selected.getName());
                    etDate.setText(selected.getDate());
                    etGender.setText(selected.getGender());
                    etEmail.setText(selected.getEmail());
                    etAddress.setText(selected.getAddress());

                    etMajor.setText(String.valueOf(selected.getIdMajor()));

                    studentId = selected.getId();
                } else {
                    // Handle error or log the issue
                    Log.e("ListView", "Invalid position clicked: " + position);
                }
            }
        });

        lvManagement.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student selected = studentList.get(position);
                Call<Student> call = apiService.deleteStudent(selected.getId());
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this, "Delete successfully!",
                                    Toast.LENGTH_LONG).show();
                            fetchStudentsAndMajors();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to delete",
                                Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String date = etDate.getText().toString();
                String gender = etGender.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();
                long majorId = Long.parseLong(etMajor.getText().toString());

                Student student = new Student(name, date, gender, email, address, majorId);
                Call<Student> call = apiService.updateStudent(studentId, student);
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(MainActivity.this, "Update successfully",
                                Toast.LENGTH_LONG).show();
                        fetchStudentsAndMajors();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Update failed"
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MajorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void add() {
        String name = etName.getText().toString();
        String date = etDate.getText().toString();
        String gender = etGender.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        long majorId = Long.parseLong(etMajor.getText().toString());

        Student student = new Student(name, date, gender, email, address, majorId);
        try {
            Call<Student> call = apiService.addStudent(student);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.body() != null) {
                        Toast.makeText(MainActivity.this, "Add successfully",
                                Toast.LENGTH_LONG).show();
                        fetchStudentsAndMajors();
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Add failed"
                            , Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Loi", e.getMessage());
        }
    }

    private void fetchStudentsAndMajors() {
        Call<List<Student>> callStudents = apiService.getAllStudents();
        Call<List<Major>> callMajors = apiService.getAllMajors();

        callStudents.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> studentResponse) {
                if (studentResponse.isSuccessful() && studentResponse.body() != null) {
                    List<Student> students = studentResponse.body();
                    callMajors.enqueue(new Callback<List<Major>>() {
                        @Override
                        public void onResponse(Call<List<Major>> call, Response<List<Major>> majorResponse) {
                            if (majorResponse.isSuccessful() && majorResponse.body() != null) {
                                Map<Long, String> majorMap = new HashMap<>();
                                for (Major major : majorResponse.body()) {
                                    majorMap.put(major.getIdMajor(), major.getNameMajor());
                                }
                                studentList.clear();
//                                for (Student student : students) {
//                                    student.setNameMajor(majorMap.get(student.getIdMajor()));
//
//                                    studentList.add(student);
//                                }

                                List<Student> studentsWithMajors = new ArrayList<>();

                                for (Student student : students) {
                                    studentsWithMajors.add(student);

                                    studentList.add(student);

                                }

//                                displayStudents(students);
                                displayStudents(studentsWithMajors, majorMap);
                            } else {
                                showError("Failed to fetch majors");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Major>> call, Throwable t) {
                            showError("Failed to fetch majors");
                        }
                    });
                } else {
                    showError("Failed to fetch students");
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                showError("Failed to fetch students");
            }
        });
    }

    private void displayStudents(List<Student> students, Map<Long, String> majorMap) {
        studentAdapter = new StudentAdapter(MainActivity.this, students, majorMap);
        lvManagement.setAdapter(studentAdapter);
    }

    private void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }



}