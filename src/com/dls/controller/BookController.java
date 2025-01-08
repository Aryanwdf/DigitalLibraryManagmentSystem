package com.dls.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.dls.dal.BookDAO;
import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.BookDAOInterface;

/**
 * 
 */
public class BookController implements BookControllerInterface {

	private final BookDAOInterface bookDAO;
	private TreeSet<Book> books = null;
	private Set<String> bookIds = null;
	private HashMap<String, Book> booksLibrary = null;

	public BookController() {
		bookDAO = new BookDAO();
	}

	@Override
	public void addBook(Book book) throws CustomException {
		bookDAO.addBook(book);
		books = bookDAO.getAllBooks();
	}

	@Override
	public void updateBook(Book book) throws CustomException {
		bookDAO.updateBook(book);
		books = bookDAO.getAllBooks();
	}

	@Override
	public void deleteBook(Book book) throws CustomException {
		bookDAO.deleteBook(book);
		books = bookDAO.getAllBooks();
	}

	@Override
	public HashMap<String, Book> buildBooksLibrary() throws CustomException {
		try {
			if (books == null) {
				books = bookDAO.getAllBooks();
			}
			Iterator<Book> iterator = books.iterator();
			booksLibrary = new HashMap<>();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				booksLibrary.put(Integer.toString(book.getId()), book);
			}
		} catch (Exception e) {
			throw new CustomException("Can't build DS For Book - " + e.getMessage());
		}
		return booksLibrary;
	}

	@Override
	public Book getBook(int id) throws CustomException {
		try {
			if (books == null || booksLibrary == null || bookIds == null) {
				books = bookDAO.getAllBooks();
				booksLibrary = buildBooksLibrary();
				bookIds = getBooksIds();
			}
			if (checkIDExistence(Integer.toString(id), bookIds)) {
				return booksLibrary.get(Integer.toString(id));
			} else {
				throw new Exception("Book id " + id + " not valid");
			}
		} catch (Exception e) {
			throw new CustomException("Can't get Book - " + e.getMessage());
		}

	}

	@Override
	public boolean checkIDExistence(String id, Set<String> ids) throws CustomException {
		if (ids.contains(id)) {
			return true;
		}
		return false;
	}

	@Override
	public Set<String> getBooksIds() throws CustomException {
		try {
			if (books == null || booksLibrary == null) {
				books = bookDAO.getAllBooks();
				booksLibrary = buildBooksLibrary();
			}
			bookIds = booksLibrary.keySet();
		} catch (Exception e) {
			throw new CustomException("can't for DS for IDS - " + e.getMessage());
		}
		return bookIds;
	}

	@Override
	public TreeSet<Book> getAllBooks() throws CustomException {
		return bookDAO.getAllBooks();
	}

	@Override
	public HashMap<String, String> booksIssued() throws CustomException {
		return bookDAO.booksIssued();
	}

}
