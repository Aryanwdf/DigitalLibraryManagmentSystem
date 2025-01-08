package com.dls.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.database.DBContext;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyDAOInterface;

public class FacultyDAO implements FacultyDAOInterface {

	@Override
	public void addFaculty(Faculty faculty) throws CustomException {
		String query = "INSERT INTO Faculty(name, department ,contact) VALUES (?, ?, ?)";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Set parameters for the prepared statement
			preparedStatement.setString(1, faculty.getName());
			preparedStatement.setString(2, faculty.getDepartment());
			preparedStatement.setLong(3, faculty.getContact());

			// Execute the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomException("Can't add Faculty - " + e.getMessage());
		}
	}

	@Override
	public void issueBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
		String deleteQuery = "DELETE FROM BookIssue WHERE employeeID = ?";
		String insertQuery = "INSERT INTO BookIssue(bookID, employeeID) VALUES (?, ?)";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			deleteStatement.setInt(1, faculty.getEmployeeId());

			// Execute delete query
			deleteStatement.executeUpdate();

			// Insert each book into BookIssue table
			for (Book book : books) {
				insertStatement.setInt(1, book.getId());
				insertStatement.setInt(2, faculty.getEmployeeId());
				insertStatement.addBatch(); // Add the insert operation to batch
			}

			// Execute batch insert
			insertStatement.executeBatch();

		} catch (SQLException e) {
			throw new CustomException("Error in insert faculty data to BookIssue table- " + e.getMessage());
		}

	}

	@Override
	public void returnBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
		Connection connection = null;
		PreparedStatement deleteStatement = null;

		try {
			connection = DBContext.getConnection();

			String deleteQuery = "DELETE FROM BookIssue WHERE bookID = ? AND employeeID = ?";
			deleteStatement = connection.prepareStatement(deleteQuery);

			for (Book book : books) {
				deleteStatement.setInt(1, book.getId());
				deleteStatement.setInt(2, faculty.getEmployeeId());
				deleteStatement.addBatch(); // Add each delete operation to batch
			}

			// Execute batch delete
			deleteStatement.executeBatch();

		} catch (SQLException e) {
			throw new CustomException("Error in returning Book for faculty - " + e.getMessage());
		} finally {
			// Close resources in finally block using try-with-resources
			try {
				if (deleteStatement != null) {
					deleteStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new CustomException("Error in returning Book for faculty - " + e.getMessage());
			}
		}

	}

	@Override
	public ArrayList<String> getIssueBooksDetails(Faculty faculty) throws CustomException {
		ArrayList<String> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBContext.getConnection();
			String query = "SELECT bookID FROM BookIssue WHERE employeeID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, faculty.getEmployeeId());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String bookId = resultSet.getString("bookID");
				books.add(bookId);
			}

			return books.isEmpty() ? null : books; // Return null if no books are issued

		} catch (SQLException e) {
			throw new CustomException("Error fetching issued books for faculty - " + e.getMessage());
		} finally {
			// Close resources in finally block using try-with-resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new CustomException("Error fetching issued books for faculty - " + e.getMessage());
			}
		}

	}

	@Override
	public TreeSet<Faculty> getAllFaculties() throws CustomException {
		TreeSet<Faculty> facultyTreeSet = new TreeSet<>();
		String query = "SELECT id,name,contact,department FROM Faculty ORDER BY id";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Faculty faculty = new Faculty();
					faculty.setEmployeeId(resultSet.getInt("id"));
					faculty.setName(resultSet.getString("name"));
					faculty.setContact(resultSet.getLong("contact"));
					faculty.setDepartment(resultSet.getString("department"));
					facultyTreeSet.add(faculty);
				}
			} catch (Exception e) {
				throw new CustomException("Error in Fetching Faculty list data - " + e.getMessage());
			}

		} catch (SQLException e) {
			throw new CustomException("Error in Fetching Faculty list data - " + e.getMessage());
		}
		return facultyTreeSet;
	}

	@Override
	public Faculty getFaculty(String id) throws CustomException {
		Faculty faculty = null;
		String query = "SELECT id,name,contact,department FROM Faculty WHERE id = ?";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					faculty = new Faculty();
					faculty.setEmployeeId(resultSet.getInt("id"));
					faculty.setName(resultSet.getString("name"));
					faculty.setDepartment(resultSet.getString("department"));
					faculty.setContact(resultSet.getLong("contact"));
				} else {
					throw new CustomException("Faculty ID not Valid");
				}
			}

		} catch (SQLException e) {
			throw new CustomException("Error in getting Faculty Info - " + e.getMessage());
		}

		return faculty;

	}

	@Override
	public void deleteFaculty(Faculty faculty) throws CustomException {
		String query = "DELETE FROM Faculty WHERE id = ?";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, faculty.getEmployeeId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new CustomException("Can't delete Faculty - " + e.getMessage());
		}
	}

	@Override
	public void updateFaculty(Faculty faculty) throws CustomException {
		String query = "UPDATE Faculty SET name = ?, contact = ?, year = ? WHERE id = ?";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, faculty.getName());
			preparedStatement.setLong(2, faculty.getContact());
			preparedStatement.setString(3, faculty.getDepartment());
			preparedStatement.setInt(4, faculty.getEmployeeId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomException("Can't Update Faculty - " + e.getMessage());
		}
	}

}
