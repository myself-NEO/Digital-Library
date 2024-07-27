package com.example.library_db.service;

import com.example.library_db.dto.CreateStudentRequest;
import com.example.library_db.dto.UpdateStudentRequest;
import com.example.library_db.model.Student;
import com.example.library_db.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student create(CreateStudentRequest createStudentRequest) {
        Student student = createStudentRequest.to();
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }


    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public Student deleteStudentById(int studentId) {
        Student student = this.getStudentById(studentId);
        if(student != null) studentRepository.deleteById(studentId);
        return student;
    }

    public Student updateStudentData(int studentId, UpdateStudentRequest updateStudentRequest) {
        Student student = this.getStudentById(studentId);
        student.setName(updateStudentRequest.getName());
        student.setContact(updateStudentRequest.getContact());
        student.setValidity(updateStudentRequest.getValidity());
        return student;
    }
}
