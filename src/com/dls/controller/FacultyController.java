package com.dls.controller;

import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.dal.FacultyDAO;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.FacultyDAOInterface;
import com.dls.utility.BookUtility;

public class FacultyController implements FacultyControllerInterface {

	private final FacultyDAOInterface facultyDAO;

	public FacultyController() {
		facultyDAO = new FacultyDAO();
	}

	@Override
	public void addFaculty(Faculty faculty) throws CustomException {
		facultyDAO.addFaculty(faculty);
	}

	@Override
	public void issueBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
		if (BookUtility.isContainDuplicateValue(books)) {
			throw new CustomException("Duplication not Allowed In Book ID's");
		}
		ArrayList<String> ids = facultyDAO.getIssueBooksDetails(faculty);
		if (ids != null) {
			if (ids.size() == 5) {
				throw new CustomException("Already Issued 5 books on FacultyID : " + faculty.getEmployeeId());
			}

			if ((ids.size() + books.size()) > 5) {
				throw new CustomException(
						"Already Issued " + ids.size() + " books ,only " + (5 - ids.size()) + " can be issued");
			}

		}
		facultyDAO.issueBook(faculty, books);
	}

	@Override
	public void returnBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
		if (BookUtility.isContainDuplicateValue(books)) {
			throw new CustomException("Duplication not Allowed In Book ID's");
		}

		ArrayList<String> ids = facultyDAO.getIssueBooksDetails(faculty);
		if (ids == null) {
			throw new CustomException("no book issued to FacultyID : " + faculty.getEmployeeId());
		}
		if (ids.size() < books.size()) {
			throw new CustomException(ids.size() + " books issued to FacultyID :" + faculty.getEmployeeId());
		}
		for (int i = 0; i < books.size(); i++) {
			if (!ids.contains(Integer.toString(books.get(i).getId()))) {
				throw new CustomException(
						"Book " + books.get(i).getId() + " is not Issued to FacultyID : " + faculty.getEmployeeId());
			}
		}
		facultyDAO.returnBook(faculty, books);
	}

	@Override
	public ArrayList<String> getIssueBooksDetails(Faculty faculty) throws CustomException {
		return facultyDAO.getIssueBooksDetails(faculty);
	}

	@Override
	public Faculty getFaculty(String id) throws CustomException {
		return facultyDAO.getFaculty(id);
	}

	@Override
	public TreeSet<Faculty> getAllFaculties() throws CustomException {
		return facultyDAO.getAllFaculties();
	}

	@Override
	public void updateFaculty(Faculty faculty) throws CustomException {
		facultyDAO.updateFaculty(faculty);

	}

	@Override
	public void deleteFaculty(Faculty faculty) throws CustomException {
		facultyDAO.deleteFaculty(faculty);

	}

}
