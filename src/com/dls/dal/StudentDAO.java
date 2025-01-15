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

	// Methode zum Hinzufügen eines neuen Studenten in die Datenbank
	@Override
	public void addStudent(Student student) throws CustomException {
		// SQL-Abfrage zum Einfügen eines Studenten
		String query = "INSERT INTO Student(name, year, contact) VALUES (?, ?, ?)";

		// Versuche, eine Verbindung zur Datenbank herzustellen und die Abfrage auszuführen
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Parameter für die vorbereitete Abfrage setzen
			preparedStatement.setString(1, student.getName()); // Name des Studenten
			preparedStatement.setInt(2, student.getYear());    // Jahr des Studenten
			preparedStatement.setLong(3, student.getContact()); // Kontakt des Studenten

			// Abfrage ausführen
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Can't add Student - " + e.getMessage());
		}
	}

	// Methode zum Ausleihen von Büchern für einen Studenten
	@Override
	public void issueBook(Student student, ArrayList<Book> books) throws CustomException {
		// SQL-Abfragen: Eine zum Löschen bestehender Ausleihdaten und eine zum Einfügen neuer Daten
		String deleteQuery = "DELETE FROM BookIssue WHERE studentID = ?";
		String insertQuery = "INSERT INTO BookIssue(bookID, studentID) VALUES (?, ?)";

		// Versuche, eine Verbindung zur Datenbank herzustellen und Abfragen auszuführen
		try (Connection connection = DBContext.getConnection();
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			// Setze die studentID für die Löschabfrage
			deleteStatement.setInt(1, student.getStudentId());

			// Führe die Löschabfrage aus
			deleteStatement.executeUpdate();

			// Füge jedes Buch in die BookIssue-Tabelle ein
			for (Book book : books) {
				insertStatement.setInt(1, book.getId());        // Buch-ID
				insertStatement.setInt(2, student.getStudentId()); // Student-ID
				insertStatement.addBatch(); // Füge die Einfügeoperation der Batch-Verarbeitung hinzu
			}

			// Führe die Batch-Einfügung aus
			insertStatement.executeBatch();

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Error in insert student data to BookIssue table- " + e.getMessage());
		}
	}

	// Methode zum Zurückgeben von Büchern eines Studenten
	@Override
	public void returnBook(Student student, ArrayList<Book> books) throws CustomException {
		Connection connection = null;
		PreparedStatement deleteStatement = null;

		try {
			// Stelle eine Verbindung zur Datenbank her
			connection = DBContext.getConnection();

			// SQL-Abfrage zum Löschen der Buchausleihdaten
			String deleteQuery = "DELETE FROM BookIssue WHERE bookID = ? AND studentID = ?";
			deleteStatement = connection.prepareStatement(deleteQuery);

			// Lösche jedes Buch aus der BookIssue-Tabelle
			for (Book book : books) {
				deleteStatement.setInt(1, book.getId());          // Buch-ID
				deleteStatement.setInt(2, student.getStudentId()); // Student-ID
				deleteStatement.addBatch(); // Füge die Löschoperation der Batch-Verarbeitung hinzu
			}

			// Führe die Batch-Löschung aus
			deleteStatement.executeBatch();

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Error in returning Book for student - " + e.getMessage());
		} finally {
			// Schließe Ressourcen im finally-Block
			try {
				if (deleteStatement != null) {
					deleteStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// Fehlerbehandlung bei SQL-Ausnahmen
				throw new CustomException("Error in returning Book for student - " + e.getMessage());
			}
		}
	}

	// Methode zum Abrufen der Details der ausgeliehenen Bücher eines Studenten
	@Override
	public ArrayList<String> getIssueBooksDetails(Student student) throws CustomException {
		ArrayList<String> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Stelle eine Verbindung zur Datenbank her
			connection = DBContext.getConnection();
			// SQL-Abfrage zum Abrufen der ausgeliehenen Bücher des Studenten
			String query = "SELECT bookID FROM BookIssue WHERE studentID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, student.getStudentId());

			// Führe die Abfrage aus
			resultSet = preparedStatement.executeQuery();

			// Gehe durch die Ergebnisse und füge die Buch-IDs der Liste hinzu
			while (resultSet.next()) {
				String bookId = resultSet.getString("bookID");
				books.add(bookId);
			}

			// Gib null zurück, wenn keine Bücher ausgeliehen wurden
			return books.isEmpty() ? null : books;

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Error fetching issued books for student - " + e.getMessage());
		} finally {
			// Schließe Ressourcen im finally-Block
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
				// Fehlerbehandlung bei SQL-Ausnahmen
				throw new CustomException("Error fetching issued books for student - " + e.getMessage());
			}
		}
	}

	// Methode zum Abrufen aller Studenten aus der Datenbank
	@Override
	public TreeSet<Student> getAllStudents() throws CustomException {
		TreeSet<Student> studentTreeSet = new TreeSet<>();
		String query = "SELECT id,name,contact,year FROM Student ORDER BY id";

		// Versuche, eine Verbindung zur Datenbank herzustellen und die Abfrage auszuführen
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				// Gehe durch die Ergebnisse und erstelle Studentenobjekte
				while (resultSet.next()) {
					Student student = new Student();
					student.setStudentId(resultSet.getInt("id"));
					student.setName(resultSet.getString("name"));
					student.setContact(resultSet.getLong("contact"));
					student.setYear(resultSet.getInt("year"));
					studentTreeSet.add(student); // Füge den Studenten zur TreeSet hinzu
				}
			} catch (Exception e) {
				// Fehlerbehandlung bei SQL-Ausnahmen
				throw new CustomException("Error in Fetching Student list data - " + e.getMessage());
			}

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Error in Fetching Student List data - " + e.getMessage());
		}
		return studentTreeSet;
	}

	// Methode zum Abrufen eines Studenten anhand der ID
	@Override
	public Student getStudent(String id) throws CustomException {
		Student student = null;
		String query = "SELECT id,name,contact,year FROM Student WHERE id = ?";

		// Versuche, eine Verbindung zur Datenbank herzustellen und die Abfrage auszuführen
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
					// Wenn kein Student mit dieser ID gefunden wird, wirf eine Ausnahme
					throw new CustomException("Student ID not Valid");
				}
			}

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Error in getting Student Info - " + e.getMessage());
		}

		return student;
	}

	// Methode zum Löschen eines Studenten
	@Override
	public void deleteStudent(Student student) throws CustomException {
		// SQL-Abfrage zum Löschen eines Studenten
		String query = "DELETE FROM Student WHERE id = ?";

		// Versuche, eine Verbindung zur Datenbank herzustellen und die Abfrage auszuführen
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, student.getStudentId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Can't delete Student - " + e.getMessage());
		}
	}

	// Methode zum Aktualisieren der Daten eines Studenten
	@Override
	public void updateStudent(Student student) throws CustomException {
		// SQL-Abfrage zum Aktualisieren eines Studenten
		String query = "UPDATE Student SET name = ?, contact = ?, year = ? WHERE id = ?";

		// Versuche, eine Verbindung zur Datenbank herzustellen und die Abfrage auszuführen
		try (Connection connection = DBContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, student.getName()); // Name des Studenten
			preparedStatement.setLong(2, student.getContact()); // Kontakt des Studenten
			preparedStatement.setInt(3, student.getYear());     // Jahr des Studenten
			preparedStatement.setInt(4, student.getStudentId()); // Student-ID
			preparedStatement.executeUpdate(); // Führe die Aktualisierung aus

		} catch (SQLException e) {
			// Fehlerbehandlung bei SQL-Ausnahmen
			throw new CustomException("Can't Update Student - " + e.getMessage());
		}
	}
}
