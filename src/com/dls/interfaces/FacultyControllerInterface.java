package com.dls.interfaces;

import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;

public interface FacultyControllerInterface {

	void addFaculty(Faculty faculty) throws CustomException;

	void issueBook(Faculty faculty, ArrayList<Book> books) throws CustomException;

	void returnBook(Faculty faculty, ArrayList<Book> books) throws CustomException;

	ArrayList<String> getIssueBooksDetails(Faculty faculty) throws CustomException;

	Faculty getFaculty(String id) throws CustomException;

	TreeSet<Faculty> getAllFaculties() throws CustomException;

	void updateFaculty(Faculty faculty) throws CustomException;

	void deleteFaculty(Faculty faculty) throws CustomException;
}
