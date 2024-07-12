package com.example.sinhvienmanagement;

public class StudentRepository {
    public static ApiService getStudentService() {
        return ApiClient.getClient().create(ApiService.class);
    }
}
