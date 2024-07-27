package com.example.library_db.controller;

import com.example.library_db.dto.CreateStudentRequest;
import com.example.library_db.dto.UpdateStudentRequest;
import com.example.library_db.model.Student;
import com.example.library_db.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/createStudent")
    public Student create(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/delete")
    public Student deleteStudentById(@RequestParam("id") int studentId) {
        return studentService.deleteStudentById(studentId);
    }

    @PutMapping("/{studentId}")
    public Student Student(@PathVariable("studentId") int studentId, @RequestBody @Valid UpdateStudentRequest updateStudentRequest){
        return studentService.updateStudentData(studentId, updateStudentRequest);
    }

}
