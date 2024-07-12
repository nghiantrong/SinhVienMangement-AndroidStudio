package com.example.sinhvienmanagement;

import com.example.sinhvienmanagement.Models.Major;
import com.example.sinhvienmanagement.Models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    String STUDENTS = "Student";
    String MAJORS = "Major";

    @GET(STUDENTS)
    Call<List<Student>> getAllStudents();
    @POST(STUDENTS)
    Call<Student> addStudent(@Body Student student);

    @PUT(STUDENTS + "/{id}")
    Call<Student> updateStudent(@Path("id") long id, @Body Student student);

    @DELETE(STUDENTS + "/{id}")
    Call<Student> deleteStudent(@Path("id") long id);




    @GET(MAJORS)
    Call<List<Major>> getAllMajors();
    @POST(MAJORS)
    Call<Major> addMajor(@Body Major major);

    @PUT(MAJORS + "/{id}")
    Call<Major> updateMajor(@Path("id") long id, @Body Major major);

    @DELETE(MAJORS + "/{id}")
    Call<Major> deleteMajor(@Path("id") long id);
}
