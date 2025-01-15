package com.dls.dal;

// Import der notwendigen Java SQL Klassen
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

// Import der eigenen Klassen
import com.dls.database.DBContext;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyDAOInterface;

public class FacultyDAO implements FacultyDAOInterface {

    // Methode zum Hinzufügen eines Dozenten in die Datenbank
    @Override
    public void addFaculty(Faculty faculty) throws CustomException {
        // SQL-Abfrage zum Einfügen eines Dozenten
        String query = "INSERT INTO Faculty(name, department ,contact) VALUES (?, ?, ?)";

        try (Connection connection = DBContext.getConnection();  // Verbindung zur DB aufbauen
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  // SQL-Abfrage vorbereiten

            // Setze die Parameter des Prepared Statements
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setString(2, faculty.getDepartment());
            preparedStatement.setLong(3, faculty.getContact());

            // Führe die Abfrage aus
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Can't add Faculty - " + e.getMessage());
        }
    }

    // Methode zum Ausleihen von Büchern für einen Dozenten
    @Override
    public void issueBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
        String deleteQuery = "DELETE FROM BookIssue WHERE employeeID = ?"; // SQL-Abfrage zum Löschen von bestehenden Ausleihen
        String insertQuery = "INSERT INTO BookIssue(bookID, employeeID) VALUES (?, ?)"; // SQL-Abfrage zum Hinzufügen neuer Ausleihen

        try (Connection connection = DBContext.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            deleteStatement.setInt(1, faculty.getEmployeeId());

            // Lösche bestehende Ausleihen
            deleteStatement.executeUpdate();

            // Füge für jedes Buch einen Ausleih-Eintrag in die Tabelle hinzu
            for (Book book : books) {
                insertStatement.setInt(1, book.getId());
                insertStatement.setInt(2, faculty.getEmployeeId());
                insertStatement.addBatch(); // Füge die Insert-Operation zur Batch-Operation hinzu
            }

            // Führe die Batch-Operation aus
            insertStatement.executeBatch();

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Error in insert faculty data to BookIssue table- " + e.getMessage());
        }
    }

    // Methode zum Zurückgeben von Büchern durch einen Dozenten
    @Override
    public void returnBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;

        try {
            connection = DBContext.getConnection();

            // SQL-Abfrage zum Löschen von Ausleih-Einträgen
            String deleteQuery = "DELETE FROM BookIssue WHERE bookID = ? AND employeeID = ?";
            deleteStatement = connection.prepareStatement(deleteQuery);

            for (Book book : books) {
                deleteStatement.setInt(1, book.getId());
                deleteStatement.setInt(2, faculty.getEmployeeId());
                deleteStatement.addBatch(); // Füge jedes Lösch-Statement zur Batch-Operation hinzu
            }

            // Führe die Batch-Löschung aus
            deleteStatement.executeBatch();

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Error in returning Book for faculty - " + e.getMessage());
        } finally {
            // Schließe die Ressourcen im finally-Block
            try {
                if (deleteStatement != null) {
                    deleteStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Fehlerbehandlung bei der Schließung von Ressourcen
                throw new CustomException("Error in returning Book for faculty - " + e.getMessage());
            }
        }
    }

    // Methode zum Abrufen der ausgeliehenen Bücher eines Dozenten
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

            return books.isEmpty() ? null : books; // Rückgabe von null, wenn keine Bücher ausgeliehen sind

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Error fetching issued books for faculty - " + e.getMessage());
        } finally {
            // Schließe die Ressourcen im finally-Block
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
                // Fehlerbehandlung bei der Schließung von Ressourcen
                throw new CustomException("Error fetching issued books for faculty - " + e.getMessage());
            }
        }
    }

    // Methode zum Abrufen aller Dozenten
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
                    facultyTreeSet.add(faculty); // Füge den Dozenten zum TreeSet hinzu
                }
            } catch (Exception e) {
                // Fehlerbehandlung bei der Verarbeitung von Ergebnissen
                throw new CustomException("Error in Fetching Faculty list data - " + e.getMessage());
            }

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Error in Fetching Faculty list data - " + e.getMessage());
        }
        return facultyTreeSet;
    }

    // Methode zum Abrufen eines Dozenten anhand seiner ID
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
            // Fehlerbehandlung bei SQLException
            throw new CustomException("Error in getting Faculty Info - " + e.getMessage());
        }

        return faculty;
    }

    // Methode zum Löschen eines Dozenten aus der Datenbank
    @Override
    public void deleteFaculty(Faculty faculty) throws CustomException {
        String query = "DELETE FROM Faculty WHERE id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, faculty.getEmployeeId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            // Fehlerbehandlung bei der Löschung des Dozenten
            throw new CustomException("Can't delete Faculty - " + e.getMessage());
        }
    }

    // Methode zum Aktualisieren der Daten eines Dozenten
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
            // Fehlerbehandlung bei der Aktualisierung des Dozenten
            throw new CustomException("Can't Update Faculty - " + e.getMessage());
        }
    }
}
