package com.example.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.student.StudentApplication;
import com.example.student.entity.Student;
import com.example.student.repo.StudentRepo;

@RestController
public class StudentController {

    public final StudentApplication studentApplication;

    StudentController(StudentApplication studentApplication) {
        this.studentApplication = studentApplication;
    }
    
    @Autowired
    StudentRepo studentRepo;
	
	@PostMapping("/api/students")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		return new ResponseEntity<>(studentRepo.save(student),HttpStatus.CREATED);
	}
	
	@GetMapping("/api/students")
	public ResponseEntity<List<Student>> getStudent() {
		return new ResponseEntity<>(studentRepo.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/api/students/{id}")
	public ResponseEntity<List<Student>> getStudent(@PathVariable long id) {
		Optional<Student> student = studentRepo.findById(id);
		if(student.isPresent()) {
			return new ResponseEntity<>(List.of(student.get()), HttpStatus.OK);

		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/api/students/{id}")
	public ResponseEntity<List<Student>> updateStudent(@PathVariable long id,@RequestBody Student stud) {
		Optional<Student> student = studentRepo.findById(id);
		if(student.isPresent()) {
			student.get().setStudentName(stud.getStudentName());
			student.get().setStudentEmail(stud.getStudentEmail());
			student.get().setStudentAddress(stud.getStudentAddress());
			return new ResponseEntity<>(List.of(studentRepo.save(student.get())), HttpStatus.OK);

		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/api/students/{id}")
	public ResponseEntity<List<Void>> deleteStudent(@PathVariable long id) {
		Optional<Student> student = studentRepo.findById(id);
		if(student.isPresent()) {
			studentRepo.deleteById(id);
			return new ResponseEntity<>(List.of(), HttpStatus.NO_CONTENT);

		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
