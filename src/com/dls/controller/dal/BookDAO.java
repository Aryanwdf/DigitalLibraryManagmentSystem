// Package-Deklaration: Definiert, dass diese Klasse Teil des 'com.dls.dal'-Pakets ist.
package com.dls.dal;

// Import von Klassen und Paketen für die Arbeit mit Datenbanken und Collections.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

// Import von benutzerdefinierten Klassen aus dem Projekt.
import com.dls.database.DBContext;
import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookDAOInterface;

// Definition der Klasse 'BookDAO', die das Interface 'BookDAOInterface' implementiert.
public class BookDAO implements BookDAOInterface {

    // Konstruktor: Standardkonstruktor der Klasse.
    public BookDAO() {
    }

    // Überschreibt die Methode 'addBook' aus dem Interface, um ein Buch hinzuzufügen.
    @Override
    public void addBook(Book book) throws CustomException {
        // SQL-Abfrage, um zu prüfen, ob ein Buch mit dem gleichen Namen existiert.
        String checkQuery = "SELECT COUNT(*) FROM Books WHERE LOWER(name) = LOWER(?)";
        // SQL-Abfrage, um ein neues Buch in die Datenbank einzufügen.
        String query = "INSERT INTO Books (name, publisher, isbn, remark, entryDate, author, price, pages, year, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Verbindung und Statements werden im try-with-resources-Block automatisch geschlossen.
        try (Connection connection = DBContext.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Überprüfung, ob das Buch bereits existiert.
            checkStatement.setString(1, book.getName());
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    throw new CustomException("A book with the name '" + book.getName() + "' already exists.");
                }
            }
            // Parameter für die Insert-Abfrage setzen.
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getISBN());
            preparedStatement.setString(4, book.getRemark());
            preparedStatement.setDate(5, new java.sql.Date(book.getEntryDate().getTime()));
            preparedStatement.setString(6, book.getAuthor());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getTotalPages());
            preparedStatement.setInt(9, book.getYear());
            preparedStatement.setString(10, book.getStatus());
            // Abfrage ausführen.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Benutzerdefinierte Ausnahme werfen, wenn ein Fehler auftritt.
            throw new CustomException("Can't add Book - " + e.getMessage());
        }
    }
    
    public void updateBookStatus(Book book) throws CustomException {
        // SQL-Abfragen für die Überprüfung und das Update.
        String query = "UPDATE Books SET status = ? WHERE id = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Parameter für die Update-Abfrage setzen.
            preparedStatement.setString(1, book.getStatus());
            preparedStatement.setInt(2, book.getId());

            // Abfrage ausführen.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't Update Book - " + e.getMessage());
        }
    }

    // Überschreibt die Methode 'updateBook', um ein Buch zu aktualisieren.
    @Override
    public void updateBook(Book book) throws CustomException {
        // SQL-Abfragen für die Überprüfung und das Update.
        String checkQuery = "SELECT COUNT(*) FROM Books WHERE LOWER(name) = LOWER(?) AND id <> ?";
        String query = "UPDATE Books SET name = ?, publisher = ?, isbn = ?, remark = ?, entryDate = ?, author = ?, price = ?, pages = ?, year = ?, status = ? WHERE id = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Überprüfung, ob ein Buch mit dem gleichen Namen existiert.
            checkStatement.setString(1, book.getName());
            checkStatement.setInt(2, book.getId());
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    throw new CustomException("A book with the name '" + book.getName() + "' already exists.");
                }
            }
            // Parameter für die Update-Abfrage setzen.
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getISBN());
            preparedStatement.setString(4, book.getRemark());
            preparedStatement.setDate(5, new java.sql.Date(book.getEntryDate().getTime()));
            preparedStatement.setString(6, book.getAuthor());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getTotalPages());
            preparedStatement.setInt(9, book.getYear());
            preparedStatement.setString(10, book.getStatus());
            preparedStatement.setInt(11, book.getId());

            // Abfrage ausführen.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't Update Book - " + e.getMessage());
        }
    }

    // Überschreibt die Methode 'deleteBook', um ein Buch zu löschen.
    @Override
    public void deleteBook(Book book) throws CustomException {
        String query = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Parameter für die Delete-Abfrage setzen.
            preparedStatement.setInt(1, book.getId());
            // Abfrage ausführen.
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new CustomException("Can't delete Book - " + e.getMessage());
        }
    }

    // Überschreibt die Methode 'getAllBooks', um alle Bücher abzurufen.
    @Override
    public TreeSet<Book> getAllBooks() throws CustomException {
        TreeSet<Book> bookTreeSet = new TreeSet<>();
        String query = "SELECT id, name, publisher, isbn, remark, entryDate, author, price, pages, year, status FROM Books ORDER BY id";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Buch-Objekt aus den Ergebnissen erstellen und zur Menge hinzufügen.
                    Book book = new Book();
                    book.setEntryDate(resultSet.getDate("entryDate"));
                    book.setId(resultSet.getInt("id"));
                    book.setISBN(resultSet.getString("isbn"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setName(resultSet.getString("name"));
                    book.setYear(resultSet.getInt("year"));
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setTotalPages(resultSet.getInt("pages"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setStatus(resultSet.getString("status"));
                    book.setRemark(resultSet.getString("remark"));
                    bookTreeSet.add(book);
                }
            } catch (Exception e) {
                throw new CustomException("Error in Fetching Books data - " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new CustomException("Error in Fetching Books data - " + e.getMessage());
        }
        return bookTreeSet;
    }

    // Überschreibt die Methode 'booksIssued', um Informationen über ausgeliehene Bücher abzurufen.
    @Override
    public HashMap<String, String> booksIssued() throws CustomException {
        String query = "SELECT bookID, studentID, employeeID FROM BookIssue";
        HashMap<String, String> booksIssued = new HashMap<>();
        HashMap<String, HashSet<String>> bookList = new HashMap<>();
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Informationen über die ausgeliehenen Bücher abrufen.
                    String studentId = resultSet.getString("studentID");
                    String employeeId = resultSet.getString("employeeID");
                    String bookId = resultSet.getString("bookID");
                    String personId = "";
                    if (studentId != null) {
                        personId = "(Student) Id : " + studentId;
                    } else if (employeeId != null) {
                        personId = "(Faculty) Id : " + employeeId;
                    } else {
                        personId = "(Unknown)";
                    }
                    // Bücher nach Personen gruppieren.
                    bookList.computeIfAbsent(personId, k -> new HashSet<>()).add(bookId);
                }
            }
            // Erzeugt eine Map mit Buch-IDs als CSV.
            for (Map.Entry<String, HashSet<String>> entry : bookList.entrySet()) {
                booksIssued.put(entry.getKey(), String.join(",", entry.getValue()));
            }
        } catch (SQLException e) {
            throw new CustomException("Error in fetching Issue Info - " + e.getMessage());
        }
        return booksIssued;
    }
}
