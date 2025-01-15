package com.dls.ui.tabledataview;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.dls.entity.Student;
import com.dls.exception.CustomException;
import com.dls.interfaces.StudentControllerInterface;

/**
 * Die Klasse `StudentTableView` ist eine benutzerdefinierte Implementierung eines 
 * Tabellenmodells, das Studentendaten in einer GUI anzeigt. Sie erbt von `AbstractTableModel`
 * und überschreibt Methoden, um die Daten in einer JTable darzustellen.
 */
public class StudentTableView extends AbstractTableModel {

    // Liste der Studenten
    ArrayList<Student> StudentList;

    // Spaltenüberschriften für die Tabelle
    private String title[] = { "Student ID ", "Student Name ", "Year of Admission", "Student Contact No" };

    /**
     * Konstruktor der Klasse `StudentTableView`.
     * Initialisiert das Tabellenmodell mit den Studentendaten,
     * die durch das `StudentControllerInterface` bereitgestellt werden.
     * 
     * @param studentController Das Interface, das Zugriff auf die Studentendaten bietet
     */
    public StudentTableView(StudentControllerInterface studentController) {
        try {
            // Holt alle Studenten über das Interface
            var studentSet = studentController.getAllStudents();
            
            // Konvertiert die Studenten in eine ArrayList
            StudentList = new ArrayList<>(studentSet);
        } catch (CustomException ex) {
            // Bei einer Ausnahme wird eine leere Liste initialisiert
            StudentList = new ArrayList<>();
            System.out.println(ex);
        }
    }

    /**
     * Gibt die Anzahl der Zeilen in der Tabelle zurück, basierend auf der Anzahl der Studenten.
     * 
     * @return Die Anzahl der Zeilen (Anzahl der Studenten)
     */
    @Override
    public int getRowCount() {
        return StudentList.size();
    }

    /**
     * Gibt die Anzahl der Spalten in der Tabelle zurück.
     * 
     * @return Die Anzahl der Spalten (4: Studenten-ID, Name, Jahr der Aufnahme und Kontakt)
     */
    @Override
    public int getColumnCount() {
        return title.length;
    }

    /**
     * Gibt den Wert an der angegebenen Position in der Tabelle zurück.
     * 
     * @param row Die Zeilenposition
     * @param col Die Spaltenposition
     * @return Der Wert, der an der angegebenen Position angezeigt werden soll
     */
    @Override
    public Object getValueAt(int row, int col) {
        Student student = StudentList.get(row);
        switch (col) {
        case 0:
            return Integer.toString(student.getStudentId());  // Studenten-ID zurückgeben
        case 1:
            return student.getName();  // Studentenname zurückgeben
        case 2:
            return Integer.toString(student.getYear());  // Jahr der Aufnahme zurückgeben
        case 3:
            return Long.toString(student.getContact());  // Kontakttelefonnummer zurückgeben
        default:
            break;
        }
        return null;
    }

    /**
     * Gibt den Namen der angegebenen Spalte zurück (ID, Name, Jahr der Aufnahme oder Kontakt).
     * 
     * @param c Der Index der Spalte
     * @return Der Name der Spalte als String
     */
    @Override
    public String getColumnName(int c) {
        return title[c];
    }
}
