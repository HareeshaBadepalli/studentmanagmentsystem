package net.javaguides.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	 // Redirect root URL to the student list page
    @GetMapping("/")
    public String redirectToStudentList() {
        return "redirect:/studcrud"; // Redirect to the student list page
    }
	
	 @GetMapping("/addstudent")
	    public String showStudentForm() {
	        return "index"; // This is the form to insert student details
	    }

	    // Method to save student to the database
	    @PostMapping("/savestudent")
	    public String saveStudent(@RequestParam("id") Long id,
	                              @RequestParam("firstName") String firstName, 
	                              @RequestParam("lastName") String lastName, 
	                              @RequestParam("email") String email,
	                              @RequestParam("password") String password)
	     {
	        Student student = new Student();
	        student.setId(id);
	        student.setFirstName(firstName);
	        student.setLastName(lastName);
	        student.setEmail(email);
	        student.setPassword(password);
	        studentService.saveStudent(student);  // Save the student data using the service
	        return "redirect:/studcrud"; // Redirect to the student list page
	    }

	 // Method to retrieve all students and display them
	    @GetMapping("/studcrud")
	    public String getAllStudents(Model model) {
	        model.addAttribute("students", studentService.getAllStudents());
	        return "students"; // This is the view to display the student list
	    }
	    
	    @GetMapping("/students/edit/{id}")

	    public String showUpdateForm(@PathVariable("id") Long id,Model model) {
			Student existingStudent =studentService.getStudentById(id);
	    	model.addAttribute("student",existingStudent);
	    	return "update_student";
	    }
	    
	    
	    @PostMapping("/students/edit/{id}")
	    public String updateStudent(@PathVariable("id") Long id,
	    		                    @ModelAttribute("student") Student student) {
	    	
	    	student.setId(id);
	    	studentService.updateStudent(student);
	    	return "redirect:/studcrud";
	    	
	    }
	   	}