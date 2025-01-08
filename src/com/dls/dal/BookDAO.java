package com.dls.dal;

import com.dls.database.DBContext;
import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookDAOInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class BookDAO implements BookDAOInterface {

    public BookDAO() {
    }

    @Override
    public void addBook(Book book) throws CustomException {
        String query = "INSERT INTO Books (name, publisher, isbn, remark, entryDate, author, price, pages, year, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't add Book - " + e.getMessage());
        }

    }

    @Override
    public void updateBook(Book book) throws CustomException {
        String query = "UPDATE Books SET name = ?, publisher = ?, isbn = ?, remark = ?, entryDate = ?, author = ?, price = ?, pages = ?, year = ?, status = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("Can't Update Book - " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(Book book) throws CustomException {
        String query = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, book.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new CustomException("Can't delete Book - " + e.getMessage());
        }
    }

    @Override
    public TreeSet<Book> getAllBooks() throws CustomException {
        TreeSet<Book> bookTreeSet = new TreeSet<>();
        String query = "SELECT id, name, publisher, isbn, remark, entryDate, author, price, pages, year, status FROM Books ORDER BY id";
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
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

    @Override
    public HashMap<String, String> booksIssued() throws CustomException {
        String query = "SELECT bookID, studentID, employeeID FROM BookIssue";
        HashMap<String, String> booksIssued = new HashMap<>();
        HashMap<String, HashSet<String>> bookList = new HashMap<>();
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
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
                    if (bookList.containsKey(personId)) {
                        bookList.get(personId).add(bookId);
                    } else {
                        bookList.put(personId, new HashSet<>());
                        bookList.get(personId).add(bookId);
                    }
                }
            }
            for (Map.Entry<String, HashSet<String>> entry : bookList.entrySet()) {
                String key = entry.getKey();
                HashSet<String> temp = entry.getValue();
                booksIssued.put(key, String.join(",", temp));
            }

        } catch (SQLException e) {
            throw new CustomException("Error in fetching Issue Info - " + e.getMessage());
        }

        return booksIssued;
    }

}
