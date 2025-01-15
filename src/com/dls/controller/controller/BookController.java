// Package-Deklaration, die angibt, dass diese Klasse im Paket com.dls.controller enthalten ist
package com.dls.controller;

// Import von verschiedenen Klassen, die für den Code benötigt werden
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

// Import der benötigten Klassen aus anderen Paketen
import com.dls.dal.BookDAO;
import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.BookDAOInterface;

/**
 * Die BookController-Klasse implementiert das Interface BookControllerInterface und verwaltet
 * Buchobjekte durch die Interaktion mit einem DAO (Data Access Object) und die Verwaltung von
 * Büchern in verschiedenen Datenstrukturen.
 */
public class BookController implements BookControllerInterface {

    // Deklaration eines BookDAOInterface-Objekts, das zur Kommunikation mit der Datenbank dient
    private final BookDAOInterface bookDAO;

    // Deklaration von drei verschiedenen Datenstrukturen zur Verwaltung von Büchern
    private TreeSet<Book> books = null;  // Speichert alle Bücher
    private Set<String> bookIds = null;  // Speichert die IDs der Bücher
    private HashMap<String, Book> booksLibrary = null;  // Speichert Bücher mit ihrer ID als Schlüssel

    // Konstruktor der BookController-Klasse, der ein neues BookDAO-Objekt erstellt
    public BookController() {
        bookDAO = new BookDAO();  // Initialisiert das BookDAO-Objekt
    }

    // Methode zum Hinzufügen eines Buches. Das Buch wird in die Datenbank eingefügt.
    @Override
    public void addBook(Book book) throws CustomException {
        bookDAO.addBook(book);  // Ruft die Methode addBook des BookDAO auf, um das Buch hinzuzufügen
        books = bookDAO.getAllBooks();  // Aktualisiert die Liste der Bücher
    }

    // Methode zum Aktualisieren eines Buches. Das Buch wird in der Datenbank aktualisiert.
    @Override
    public void updateBook(Book book) throws CustomException {
        bookDAO.updateBook(book);  // Ruft die Methode updateBook des BookDAO auf, um das Buch zu aktualisieren
        books = bookDAO.getAllBooks();  // Aktualisiert die Liste der Bücher
    }

    // Methode zum Löschen eines Buches. Das Buch wird aus der Datenbank entfernt.
    @Override
    public void deleteBook(Book book) throws CustomException {
        bookDAO.deleteBook(book);  // Ruft die Methode deleteBook des BookDAO auf, um das Buch zu löschen
        books = bookDAO.getAllBooks();  // Aktualisiert die Liste der Bücher
    }

    // Methode zum Erstellen einer HashMap, die Bücher mit ihrer ID als Schlüssel speichert
    @Override
    public HashMap<String, Book> buildBooksLibrary() throws CustomException {
        try {
            // Wenn die Bücherliste noch nicht geladen wurde, werden die Bücher aus der Datenbank geladen
            if (books == null) {
                books = bookDAO.getAllBooks();  // Ruft alle Bücher aus der Datenbank ab
            }
            // Iterator für die TreeSet-Bücherliste
            Iterator<Book> iterator = books.iterator();
            booksLibrary = new LinkedHashMap<>();  // Initialisiert die HashMap für die Bücherbibliothek
            // Iteriert durch alle Bücher und fügt sie in die HashMap ein
            while (iterator.hasNext()) {
                Book book = iterator.next();  // Holt das nächste Buch aus der Liste
                booksLibrary.put(Integer.toString(book.getId()), book);  // Fügt das Buch in die HashMap ein
            }
        } catch (Exception e) {
            throw new CustomException("Can't build DS For Book - " + e.getMessage());  // Wirft eine benutzerdefinierte Ausnahme, wenn ein Fehler auftritt
        }
        return booksLibrary;  // Gibt die erstellte Bücherbibliothek zurück
    }

    // Methode zum Abrufen eines Buches anhand seiner ID
    @Override
    public Book getBook(int id) throws CustomException {
        try {
            // Wenn Bücher oder die Bücherbibliothek noch nicht geladen wurden, wird dies hier nachgeholt
            if (books == null || booksLibrary == null || bookIds == null) {
                books = bookDAO.getAllBooks();  // Lädt alle Bücher aus der Datenbank
                booksLibrary = buildBooksLibrary();  // Baut die Bücherbibliothek
                bookIds = getBooksIds();  // Holt die IDs der Bücher
            }
            // Überprüft, ob die angegebene ID existiert
            if (checkIDExistence(Integer.toString(id), bookIds)) {
                return booksLibrary.get(Integer.toString(id));  // Gibt das Buch aus der Bibliothek zurück
            } else {
                throw new Exception("Book id " + id + " not valid");  // Wirft eine Ausnahme, wenn die ID ungültig ist
            }
        } catch (Exception e) {
            throw new CustomException("Can't get Book - " + e.getMessage());  // Wirft eine benutzerdefinierte Ausnahme bei einem Fehler
        }

    }

    // Methode zur Überprüfung, ob eine ID in der Liste der Buch-IDs existiert
    @Override
    public boolean checkIDExistence(String id, Set<String> ids) throws CustomException {
        if (ids.contains(id)) {  // Überprüft, ob die ID in der Menge der IDs enthalten ist
            return true;  // Gibt true zurück, wenn die ID vorhanden ist
        }
        return false;  // Gibt false zurück, wenn die ID nicht vorhanden ist
    }

    // Methode zum Abrufen der IDs aller Bücher
    @Override
    public Set<String> getBooksIds() throws CustomException {
        try {
            // Wenn die Bücher oder die Bibliothek noch nicht geladen wurden, wird dies hier nachgeholt
            if (books == null || booksLibrary == null) {
                books = bookDAO.getAllBooks();  // Lädt alle Bücher aus der Datenbank
                booksLibrary = buildBooksLibrary();  // Baut die Bücherbibliothek
            }
            bookIds = booksLibrary.keySet();  // Holt die IDs der Bücher aus der Bibliothek
        } catch (Exception e) {
            throw new CustomException("can't for DS for IDS - " + e.getMessage());  // Wirft eine benutzerdefinierte Ausnahme bei einem Fehler
        }
        return bookIds;  // Gibt die Menge der Buch-IDs zurück
    }

    // Methode zum Abrufen aller Bücher aus der Datenbank
    @Override
    public TreeSet<Book> getAllBooks() throws CustomException {
        return bookDAO.getAllBooks();  // Ruft alle Bücher aus der Datenbank ab
    }

    // Methode zum Abrufen einer HashMap der ausgeliehenen Bücher
    @Override
    public HashMap<String, String> booksIssued() throws CustomException {
        return bookDAO.booksIssued();  // Ruft die Liste der ausgeliehenen Bücher aus der Datenbank ab
    }
   
    public void updateBookStatus(Book book) throws CustomException {
    	bookDAO.updateBookStatus(book);
    }

}
