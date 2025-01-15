package com.dls.interfaces;

import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.entity.Book;
import com.dls.entity.Student;
import com.dls.exception.CustomException;

public interface StudentDAOInterface {

	void addStudent(Student student) throws CustomException;

	void issueBook(Student student, ArrayList<Book> books) throws CustomException;

	void returnBook(Student student, ArrayList<Book> books) throws CustomException;

	ArrayList<String> getIssueBooksDetails(Student student) throws CustomException;

	Student getStudent(String id) throws CustomException;

	TreeSet<Student> getAllStudents() throws CustomException;

	void updateStudent(Student student) throws CustomException;

	void deleteStudent(Student student) throws CustomException;

}
