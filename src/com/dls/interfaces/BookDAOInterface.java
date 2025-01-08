package com.dls.interfaces;

import com.dls.entity.Book;
import com.dls.exception.CustomException;
import java.util.HashMap;
import java.util.TreeSet;

public interface BookDAOInterface {
	void addBook(Book book) throws CustomException;

	void updateBook(Book book) throws CustomException;

	void deleteBook(Book book) throws CustomException;

	TreeSet<Book> getAllBooks() throws CustomException;

	HashMap<String, String> booksIssued() throws CustomException;

}