package com.example.demomybatisredis;

import com.example.demomybatisredis.model.Student;
import com.example.demomybatisredis.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoMybatisRedisApplicationTests {

    @Autowired
    private StudentService studentService;
    //        Student student=new Student();
//        student.setsBirth(new Date().toString());
//        student.setsId("08");
//        student.setsName("潇豪");
//        student.setsSex("男");
    @Test
    void findALL() {
        List<Student> all = studentService.findAll();
        System.out.println(all);

    }
    @Test
    void findById() {
        Student student = studentService.findById("08");
        System.out.println(student);

    }  @Test
    void save() {
        Student student=new Student();
        student.setsBirth("2019-07-03");
        student.setsId("08");
        student.setsName("潇豪");
        student.setsSex("男");
        Student save = studentService.save(student);
        System.out.println(save);

    }
    @Test
    void delete(){
        Integer delete = studentService.delete("08");
        System.out.println(delete);
    }
}
