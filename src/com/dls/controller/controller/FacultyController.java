package com.dls.controller;

// Importiert die notwendigen Klassen für die Controller-Logik.
import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.dal.FacultyDAO;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.FacultyDAOInterface;
import com.dls.utility.BookUtility;

// Die Klasse `FacultyController` implementiert die Logik für Fakultätsoperationen 
// und verwendet ein Data Access Object (DAO) für Dateninteraktionen.
public class FacultyController implements FacultyControllerInterface {

    // Referenz auf das DAO-Interface für den Zugriff auf Fakultätsdaten.
    private final FacultyDAOInterface facultyDAO;

    // Konstruktor: Initialisiert das FacultyDAO-Objekt.
    public FacultyController() {
        facultyDAO = new FacultyDAO();
    }

    // Fügt eine neue Fakultät hinzu, delegiert die Operation an das DAO.
    @Override
    public void addFaculty(Faculty faculty) throws CustomException {
        facultyDAO.addFaculty(faculty);
    }

    // Gibt Bücher an eine Fakultät aus, prüft dabei verschiedene Bedingungen.
    @Override
    public void issueBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
        // Überprüft, ob die Buchliste Duplikate enthält.
        if (BookUtility.isContainDuplicateValue(books)) {
            throw new CustomException("Duplication not Allowed In Book ID's");
        }

        // Ruft die Liste der aktuell ausgeliehenen Bücher der Fakultät ab.
        ArrayList<String> ids = facultyDAO.getIssueBooksDetails(faculty);

        // Überprüft, ob die maximale Anzahl an ausgeliehenen Büchern überschritten wird.
        if (ids != null) {
            if (ids.size() == 5) {
                throw new CustomException("Already Issued 5 books on FacultyID : " + faculty.getEmployeeId());
            }
            if ((ids.size() + books.size()) > 5) {
                throw new CustomException(
                    "Already Issued " + ids.size() + " books ,only " + (5 - ids.size()) + " can be issued");
            }
        }

        // Delegiert die eigentliche Buchausleihe an das DAO.
        facultyDAO.issueBook(faculty, books);
    }

    // Gibt Bücher von einer Fakultät zurück, prüft dabei die Bedingungen.
    @Override
    public void returnBook(Faculty faculty, ArrayList<Book> books) throws CustomException {
        // Überprüft, ob die Buchliste Duplikate enthält.
        if (BookUtility.isContainDuplicateValue(books)) {
            throw new CustomException("Duplication not Allowed In Book ID's");
        }

        // Ruft die Liste der aktuell ausgeliehenen Bücher der Fakultät ab.
        ArrayList<String> ids = facultyDAO.getIssueBooksDetails(faculty);

        // Überprüft, ob überhaupt Bücher ausgeliehen wurden.
        if (ids == null) {
            throw new CustomException("no book issued to FacultyID : " + faculty.getEmployeeId());
        }

        // Überprüft, ob mehr Bücher zurückgegeben werden, als ausgeliehen sind.
        if (ids.size() < books.size()) {
            throw new CustomException(ids.size() + " books issued to FacultyID :" + faculty.getEmployeeId());
        }

        // Prüft, ob alle zurückgegebenen Bücher tatsächlich ausgeliehen waren.
        for (int i = 0; i < books.size(); i++) {
            if (!ids.contains(Integer.toString(books.get(i).getId()))) {
                throw new CustomException(
                    "Book " + books.get(i).getId() + " is not Issued to FacultyID : " + faculty.getEmployeeId());
            }
        }

        // Delegiert die Rückgabe an das DAO.
        facultyDAO.returnBook(faculty, books);
    }

    // Ruft Details der ausgeliehenen Bücher für eine Fakultät ab.
    @Override
    public ArrayList<String> getIssueBooksDetails(Faculty faculty) throws CustomException {
        return facultyDAO.getIssueBooksDetails(faculty);
    }

    // Holt die Informationen einer spezifischen Fakultät anhand ihrer ID.
    @Override
    public Faculty getFaculty(String id) throws CustomException {
        return facultyDAO.getFaculty(id);
    }

    // Ruft eine Liste aller Fakultäten ab.
    @Override
    public TreeSet<Faculty> getAllFaculties() throws CustomException {
        return facultyDAO.getAllFaculties();
    }

    // Aktualisiert die Informationen einer Fakultät.
    @Override
    public void updateFaculty(Faculty faculty) throws CustomException {
        facultyDAO.updateFaculty(faculty);
    }

    // Löscht eine Fakultät aus dem System.
    @Override
    public void deleteFaculty(Faculty faculty) throws CustomException {
        facultyDAO.deleteFaculty(faculty);
    }
}
