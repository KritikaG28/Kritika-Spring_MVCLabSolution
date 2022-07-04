package com.gl.studentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.studentManagement.entity.Student;
import com.gl.studentManagement.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// add mapping for "student list"
	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		// get Students from db
		List<Student> students = studentService.findAll();

		// add to the spring model
		theModel.addAttribute("Students", students);

		return "list-Students";
	}

	// add mapping for "showFormForAdd"
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	// add mapping for "showFormForUpdate"
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId,
			Model theModel) {

		// get the Student from the service
		Student theStudent = studentService.findById(theId);


		// set Student as a model attribute to pre-populate the form
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";			
	}

	// add mapping for "save"
	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id,
			@RequestParam("name") String name,@RequestParam("department") String department,@RequestParam("country") String country) {

		System.out.println(id);
		Student theStudent;
		if(id!=0)
		{
			theStudent=studentService.findById(id);
			theStudent.setName(name);
			theStudent.setDepartment(department);
			theStudent.setCountry(country);
		}
		else
			theStudent=new Student(name, department, country);
		// save the Student
		studentService.save(theStudent);


		// use a redirect to prevent duplicate submissions
		return "redirect:/students/list";

	}

	// add mapping for "delete"
	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the Student
		studentService.deleteById(theId);

		// redirect to /student/list
		return "redirect:/students/list";

	}

}
