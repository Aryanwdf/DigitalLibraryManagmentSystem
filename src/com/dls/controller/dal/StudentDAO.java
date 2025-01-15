package com.dls.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.database.DBContext;
import com.dls.entity.Book;
import com.dls.entity.Student;
import com.dls.exception.CustomException;
import com.dls.interfaces.StudentDAOInterface;

public class StudentDAO implements StudentDAOInterface {

	@Override
	public void addStudent(Student student) throws CustomException {
		String query = "INSERT INTO Student(name, year, contact) VALUES (?, ?, ?)";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Set parameters for the prepared statement
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getYear());
			preparedStatement.setLong(3, student.getContact());

			// Execute the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomException("Can't add Student - " + e.getMessage());
		}
	}

	@Override
	public void issueBook(Student student, ArrayList<Book> books) throws CustomException {
		String deleteQuery = "DELETE FROM BookIssue WHERE studentID = ?";
		String insertQuery = "INSERT INTO BookIssue(bookID, studentID) VALUES (?, ?)";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			// Set studentID for the delete query
			deleteStatement.setInt(1, student.getStudentId());

			// Execute delete query
			deleteStatement.executeUpdate();

			// Insert each book into BookIssue table
			for (Book book : books) {
				insertStatement.setInt(1, book.getId());
				insertStatement.setInt(2, student.getStudentId());
				insertStatement.addBatch(); // Add the insert operation to batch
			}

			// Execute batch insert
			insertStatement.executeBatch();

		} catch (SQLException e) {
			throw new CustomException("Error in insert student data to BookIssue table- " + e.getMessage());
		}
	}

	@Override
	public void returnBook(Student student, ArrayList<Book> books) throws CustomException {
		Connection connection = null;
		PreparedStatement deleteStatement = null;

		try {
			connection = DBContext.getConnection();

			String deleteQuery = "DELETE FROM BookIssue WHERE bookID = ? AND studentID = ?";
			deleteStatement = connection.prepareStatement(deleteQuery);

			for (Book book : books) {
				deleteStatement.setInt(1, book.getId());
				deleteStatement.setInt(2, student.getStudentId());
				deleteStatement.addBatch(); // Add each delete operation to batch
			}

			// Execute batch delete
			deleteStatement.executeBatch();

		} catch (SQLException e) {
			throw new CustomException("Error in returning Book for student - " + e.getMessage());
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
				throw new CustomException("Error in returning Book for student - " + e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<String> getIssueBooksDetails(Student student) throws CustomException {
		ArrayList<String> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBContext.getConnection();
			String query = "SELECT bookID FROM BookIssue WHERE studentID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, student.getStudentId());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String bookId = resultSet.getString("bookID");
				books.add(bookId);
			}

			return books.isEmpty() ? null : books; // Return null if no books are issued

		} catch (SQLException e) {
			throw new CustomException("Error fetching issued books for student - " + e.getMessage());
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
				throw new CustomException("Error fetching issued books for student - " + e.getMessage());
			}
		}
	}

	@Override
	public TreeSet<Student> getAllStudents() throws CustomException {
		TreeSet<Student> studentTreeSet = new TreeSet<>();
		String query = "SELECT id,name,contact,year FROM Student ORDER BY id";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = new Student();
					student.setStudentId(resultSet.getInt("id"));
					student.setName(resultSet.getString("name"));
					student.setContact(resultSet.getLong("contact"));
					student.setYear(resultSet.getInt("year"));
					studentTreeSet.add(student);
				}
			} catch (Exception e) {
				throw new CustomException("Error in Fetching Student list data - " + e.getMessage());
			}

		} catch (SQLException e) {
			throw new CustomException("Error in Fetching Student List data - " + e.getMessage());
		}
		return studentTreeSet;
	}

	@Override
	public Student getStudent(String id) throws CustomException {
		Student student = null;
		String query = "SELECT id,name,contact,year FROM Student WHERE id = ?";

		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					student = new Student();
					student.setStudentId(resultSet.getInt("id"));
					student.setName(resultSet.getString("name"));
					student.setYear(resultSet.getInt("year"));
					student.setContact(resultSet.getLong("contact"));
				} else {
					throw new CustomException("Student ID not Valid");
				}
			}

		} catch (SQLException e) {
			throw new CustomException("Error in getting Student Info - " + e.getMessage());
		}

		return student;
	}

	@Override
	public void deleteStudent(Student student) throws CustomException {
		String query = "DELETE FROM Student WHERE id = ?";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, student.getStudentId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new CustomException("Can't delete Student - " + e.getMessage());
		}
	}

	@Override
	public void updateStudent(Student student) throws CustomException {
		String query = "UPDATE Student SET name = ?, contact = ?, year = ? WHERE id = ?";
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, student.getName());
			preparedStatement.setLong(2, student.getContact());
			preparedStatement.setInt(3, student.getYear());
			preparedStatement.setInt(4, student.getStudentId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomException("Can't Update Student - " + e.getMessage());
		}
	}

}
