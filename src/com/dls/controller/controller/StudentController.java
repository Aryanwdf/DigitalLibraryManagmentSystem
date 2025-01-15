package com.dls.controller;

// Importiert Klassen und Schnittstellen, die für die Funktionalität der StudentController-Klasse benötigt werden.
import java.util.ArrayList;
import java.util.TreeSet;

import com.dls.dal.StudentDAO; // Datenzugriffsschicht für Studenten.
import com.dls.entity.Book; // Entity-Klasse für Bücher.
import com.dls.entity.Student; // Entity-Klasse für Studenten.
import com.dls.exception.CustomException; // Benutzerdefinierte Ausnahmebehandlung.
import com.dls.interfaces.StudentControllerInterface; // Schnittstelle für die StudentController-Klasse.
import com.dls.interfaces.StudentDAOInterface; // Schnittstelle für das StudentDAO.
import com.dls.utility.BookUtility; // Hilfsklasse für Buchoperationen.

// Die Klasse `StudentController` implementiert die Logik für studentenspezifische Operationen.
// Sie verwendet ein DAO für den Zugriff auf die Datenbank.
public class StudentController implements StudentControllerInterface {

    // Deklariert ein DAO-Interface für Studenten als Instanzvariable.
    private final StudentDAOInterface studentDAO;

    // Konstruktor: Initialisiert das StudentDAO-Objekt.
    public StudentController() {
        studentDAO = new StudentDAO();
    }

    // Fügt einen neuen Studenten hinzu, indem die DAO-Methode aufgerufen wird.
    @Override
    public void addStudent(Student student) throws CustomException {
        studentDAO.addStudent(student);
    }

    // Gibt Bücher an einen Studenten aus, prüft vorher auf verschiedene Bedingungen.
    @Override
    public void issueBook(Student student, ArrayList<Book> books) throws CustomException {
        // Überprüft, ob die Buchliste Duplikate enthält.
        if (BookUtility.isContainDuplicateValue(books)) {
            throw new CustomException("Duplication not Allowed In Book ID's");
        }

        // Ruft die Liste der aktuell ausgeliehenen Bücher des Studenten ab.
        ArrayList<String> ids = studentDAO.getIssueBooksDetails(student);

        // Überprüft, ob die maximale Anzahl an Büchern überschritten wird.
        if (ids != null) {
            if (ids.size() == 5) {
                throw new CustomException("Already Issued 5 books on Student ID : " + student.getStudentId());
            }
            if ((ids.size() + books.size()) > 5) {
                throw new CustomException(
                    "Already Issued " + ids.size() + " books ,only " + (5 - ids.size()) + " can be issued");
            }
        }

        // Delegiert die Buchausgabe an das DAO.
        studentDAO.issueBook(student, books);
    }

    // Gibt Bücher von einem Studenten zurück, überprüft zuvor auf verschiedene Bedingungen.
    @Override
    public void returnBook(Student student, ArrayList<Book> books) throws CustomException {
        // Überprüft, ob die Buchliste Duplikate enthält.
        if (BookUtility.isContainDuplicateValue(books)) {
            throw new CustomException("Duplication not Allowed In Book ID's");
        }

        // Ruft die Liste der aktuell ausgeliehenen Bücher des Studenten ab.
        ArrayList<String> ids = studentDAO.getIssueBooksDetails(student);

        // Überprüft, ob überhaupt Bücher ausgeliehen wurden.
        if (ids == null) {
            throw new CustomException("no book issued to StudentId : " + student.getStudentId());
        }

        // Überprüft, ob mehr Bücher zurückgegeben werden, als ausgeliehen sind.
        if (ids.size() < books.size()) {
            throw new CustomException(ids.size() + " books issued to StudentId :" + student.getStudentId());
        }

        // Prüft, ob alle zurückgegebenen Bücher tatsächlich ausgeliehen wurden.
        for (int i = 0; i < books.size(); i++) {
            if (!ids.contains(Integer.toString(books.get(i).getId()))) {
                throw new CustomException(
                    "Book " + books.get(i).getId() + " is not Issued to StudentId : " + student.getStudentId());
            }
        }

        // Delegiert die Buchrückgabe an das DAO.
        studentDAO.returnBook(student, books);
    }

    // Ruft Details der ausgeliehenen Bücher eines Studenten ab.
    @Override
    public ArrayList<String> getIssueBooksDetails(Student student) throws CustomException {
        return studentDAO.getIssueBooksDetails(student);
    }

    // Holt die Informationen eines bestimmten Studenten anhand seiner ID.
    @Override
    public Student getStudent(String id) throws CustomException {
        return studentDAO.getStudent(id);
    }

    // Ruft eine Liste aller Studenten ab.
    @Override
    public TreeSet<Student> getAllStudents() throws CustomException {
        return studentDAO.getAllStudents();
    }

    // Aktualisiert die Informationen eines Studenten.
    @Override
    public void updateStudent(Student student) throws CustomException {
        studentDAO.updateStudent(student);
    }

    // Löscht einen Studenten aus dem System.
    @Override
    public void deleteStudent(Student student) throws CustomException {
        studentDAO.deleteStudent(student);
    }
}
