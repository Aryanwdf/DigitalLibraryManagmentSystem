package com.dls.controller;

import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.dal.StudentDAO;
import com.dls.entity.Book;
import com.dls.entity.Student;
import com.dls.exception.CustomException;
import com.dls.interfaces.StudentControllerInterface;
import com.dls.interfaces.StudentDAOInterface;
import com.dls.utility.BookUtility;

public class StudentController implements StudentControllerInterface {

	private final StudentDAOInterface studentDAO;

	public StudentController() {
		studentDAO = new StudentDAO();
	}

	@Override
	public void addStudent(Student student) throws CustomException {
		studentDAO.addStudent(student);
	}

	@Override
	public void issueBook(Student student, ArrayList<Book> books) throws CustomException {
		if (BookUtility.isContainDuplicateValue(books)) {
			throw new CustomException("Duplication not Allowed In Book ID's");
		}
		ArrayList<String> ids = studentDAO.getIssueBooksDetails(student);
		if (ids != null) {
			if (ids.size() == 5) {
				throw new CustomException("Already Issued 5 books on Student ID : " + student.getStudentId());
			}

			if ((ids.size() + books.size()) > 5) {
				throw new CustomException(
						"Already Issued " + ids.size() + " books ,only " + (5 - ids.size()) + " can be issued");
			}

		}
		studentDAO.issueBook(student, books);
	}

	@Override
	public void returnBook(Student student, ArrayList<Book> books) throws CustomException {
		if (BookUtility.isContainDuplicateValue(books)) {
			throw new CustomException("Duplication not Allowed In Book ID's");
		}

		ArrayList<String> ids = studentDAO.getIssueBooksDetails(student);
		if (ids == null) {
			throw new CustomException("no book issued to StudentId : " + student.getStudentId());
		}
		if (ids.size() < books.size()) {
			throw new CustomException(ids.size() + " books issued to StudentId :" + student.getStudentId());
		}
		for (int i = 0; i < books.size(); i++) {
			if (!ids.contains(Integer.toString(books.get(i).getId()))) {
				throw new CustomException(
						"Book " + books.get(i).getId() + " is not Issued to StudentId : " + student.getStudentId());
			}
		}
		studentDAO.returnBook(student, books);
	}

	@Override
	public ArrayList<String> getIssueBooksDetails(Student student) throws CustomException {
		return studentDAO.getIssueBooksDetails(student);
	}

	@Override
	public Student getStudent(String id) throws CustomException {
		return studentDAO.getStudent(id);
	}

	@Override
	public TreeSet<Student> getAllStudents() throws CustomException {
		return studentDAO.getAllStudents();
	}

	@Override
	public void updateStudent(Student student) throws CustomException {
		studentDAO.updateStudent(student);

	}

	@Override
	public void deleteStudent(Student student) throws CustomException {
		studentDAO.deleteStudent(student);

	}

}
