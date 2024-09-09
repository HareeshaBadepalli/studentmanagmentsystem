package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.entity.Student;

public interface StudentService {
	List<Student>getAllStudents();
	void saveStudent(Student student);  // Add this method
	Student getStudentById(Long id);
	void updateStudent(Student student);
	
}
