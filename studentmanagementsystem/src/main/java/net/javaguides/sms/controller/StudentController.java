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
	    
	 // Show the form to enter email before updating
	    @GetMapping("/students/edit/{id}")
	    public String showEmailForm(@PathVariable("id") Long id, Model model) {
	        model.addAttribute("id", id);
	        return "email_input"; // Email input page
	    }

	    // Check the email and redirect to the update form if valid
	    @PostMapping("/students/checkEmail/{id}")
	    public String checkEmail(@PathVariable("id") Long id,
	                             @RequestParam("email") String email,
	                             Model model) {
	        // Check if the email exists in the database
	        Student existingStudentByEmail = studentService.getStudentByEmail(email);
	        
	        // If no student with this email, return to email input with error
	        if (existingStudentByEmail == null) {
	            model.addAttribute("errorMessage", "Email does not exist!");
	            model.addAttribute("id", id);
	            return "email_input"; // Return to the email input page with error
	        }

	        // If email is valid, load the student data and proceed to the update form
	        Student existingStudent = studentService.getStudentById(id);
	        model.addAttribute("student", existingStudent);
	        return "update_student"; // Proceed to the update form
	    }

	    // Handle the actual update
	    @PostMapping("/students/edit/{id}")
	    public String updateStudent(@PathVariable("id") Long id,
	                                @ModelAttribute("student") Student student) {
	        student.setId(id);
	        studentService.updateStudent(student);
	        return "redirect:/studcrud";  // Redirect after successful update
	    }
	    
	    @GetMapping("students/delete/{id}")
	    public String deleteStudentById(@PathVariable ("id") Long id,Model model) {
	        // Check if student exists before attempting to delete
	        
	    Student student= studentService.getStudentById(id);  // Delete student from the database
	        model.addAttribute("student",student);
	        return "confirm_delete";  // Redirect back to student list after deletion
	    }
	    @PostMapping("/students/delete/{id}")
	    public String confirmDeleteStudent(@PathVariable("id") Long id, @RequestParam("email") String email) {
	        // Fetch the student by ID
	        Student student = studentService.getStudentById(id);
	        
	        // Check if the email provided matches the student's email
	        if (student != null && student.getEmail().equals(email)) {
	            studentService.deleteStudentById(id);  // Delete the student if the email matches
	            return "redirect:/studcrud";  // Redirect to the student list after successful deletion
	        } else {
	            return "error";  // Redirect to an error page or show a message if email doesn't match
	        }
	    }
	   	}