package com.example.demomybatisredis.service.impl;

import com.example.demomybatisredis.mapper.StudentMapper;
import com.example.demomybatisredis.model.Student;
import com.example.demomybatisredis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> findAll() {
        return studentMapper.selectByExample(null);
    }

    @Override
    @CachePut(value = "student",key = "#student.sId")
    public Student save(Student student) {
        return studentMapper.insert(student)==1?student:null;
    }

    @Override
    @Cacheable(value = "student", key = "#id")
    public Student findById(String id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value = "student",key = "#id")
    public Integer delete(String id) {
        return studentMapper.deleteByPrimaryKey(id);
    }


}
