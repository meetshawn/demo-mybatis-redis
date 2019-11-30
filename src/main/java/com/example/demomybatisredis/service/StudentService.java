package com.example.demomybatisredis.service;

import com.example.demomybatisredis.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student save(Student student);

    Student findById(String id);

    Integer delete(String id);
}
