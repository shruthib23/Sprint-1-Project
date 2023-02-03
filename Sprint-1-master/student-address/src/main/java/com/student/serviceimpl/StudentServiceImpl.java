package com.student.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.entity.Address;
import com.student.entity.Student;
import com.student.exception.ResourceNotFoundException;
import com.student.model.StudentDTO;
import com.student.repository.AddressRepository;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;
import com.student.util.Converter;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private Converter converter;
	
	@Override
	public String createStudent(Student student) {
		String message=null;
		studentRepository.save(student);
		if(student!=null)
		{
			message="Student details saved successfully";
		}
		return message;
	}

	@Override
	public StudentDTO updateStudent(int id, Student student) {
		Student existingStudent =studentRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Student", "ID", id));
		existingStudent.setStudentName(student.getStudentName());
		existingStudent.setPhone(student.getPhone());
		existingStudent.setEmail(student.getEmail());
		
		studentRepository.save(existingStudent);
		
		return converter.convertToStudentDTO(existingStudent);
	}

	@Override
	public StudentDTO getStudentById(int id) {
		Student getS = studentRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Student", "ID", id));
		return converter.convertToStudentDTO(getS);
	}

	@Override
	public List<StudentDTO> getAllStudents() {
		List<Student> students=studentRepository.findAll();
		
		List<StudentDTO> sDTO = new ArrayList<>();
		for(Student s: students)
		{
			sDTO.add(converter.convertToStudentDTO(s));
		}
		return sDTO;
	}

	@Override
	public String deleteStudentById(int id) {
		String message = null;
		Optional<Student> student= studentRepository.findById(id);
		if(student.isPresent())
		{
			studentRepository.deleteById(id);
			message = "Student details deleted successfully";
		}
		else
		{
			message="Student details not found!!";
		}
		return message;
	}

	@Override
	public void deleteAllStudents() {
		studentRepository.deleteAll();
		
	}

	@Override
	public List<StudentDTO> getStudentByName(String studentName) {
		List<Student> students = studentRepository.getStudentByName(studentName);
		List<StudentDTO> studentDTO = new ArrayList<>();
		for(Student s: students)
		{
			studentDTO.add(converter.convertToStudentDTO(s));
		}
		return studentDTO;
	}

	@Override
	public List<StudentDTO> getStudentByEmail(String email) {
		List<Student> stu = studentRepository.getStudentByEmail(email);
		List<StudentDTO> studentDTO = new ArrayList<>();
		for(Student s: stu)
		{
			studentDTO.add(converter.convertToStudentDTO(s));
		}
		return studentDTO;
	}

	@Override
	public StudentDTO assignAddressToStudent(int id, int addressId) {
		Student student = studentRepository.findById(id).get();
		Address address = addressRepository.findById(addressId).get();
		student.setAddress(address);
		studentRepository.save(student);
		return converter.convertToStudentDTO(student);
	}

	


	
}
