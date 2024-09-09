package net.javaguides.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}

	@Override
	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentRepository.save(student);
		
	}

	@Override
	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return  studentRepository.findById(id).orElse(null);
	}

	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub
		studentRepository.save(student);
	}

}
