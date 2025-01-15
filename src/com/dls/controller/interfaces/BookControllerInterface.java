package com.dls.interfaces;

import com.dls.entity.Book;
import com.dls.exception.CustomException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public interface BookControllerInterface {

    void addBook(Book book) throws CustomException;

    void updateBook(Book book) throws CustomException;

    void deleteBook(Book book) throws CustomException;

    TreeSet<Book> getAllBooks() throws CustomException;

    HashMap<String, Book> buildBooksLibrary() throws CustomException;

    boolean checkIDExistence(String id, Set<String> ids) throws CustomException;

    Book getBook(int id) throws CustomException;

    Set<String> getBooksIds() throws CustomException;

    HashMap<String, String> booksIssued() throws CustomException;
    
    void updateBookStatus(Book book) throws CustomException;

}
