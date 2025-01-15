package com.dls.ui.tabledataview;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyControllerInterface;

/**
 * Die Klasse `FacultyTableView` ist eine benutzerdefinierte Implementierung eines 
 * Tabellenmodells, das Fakultätsdaten in einer GUI anzeigt. Sie erbt von `AbstractTableModel`
 * und überschreibt Methoden, um die Daten in einer JTable darzustellen.
 */
public class FacultyTableView extends AbstractTableModel {

    // Liste von Fakultäten
    ArrayList<Faculty> facultyList;

    // Spaltenüberschriften für die Tabelle
    private String title[] = { "Faculty ID ", "Faculty Name ", "Faculty Department", "Faculty Contact No" };

    /**
     * Konstruktor der Klasse `FacultyTableView`.
     * Initialisiert das Tabellenmodell mit den Fakultätsdaten,
     * die durch das `FacultyControllerInterface` bereitgestellt werden.
     * 
     * @param facultyController Das Interface, das Zugriff auf die Fakultätsdaten bietet
     */
    public FacultyTableView(FacultyControllerInterface facultyController) {
        try {
            // Holt alle Fakultäten über das Interface
            var facultySet = facultyController.getAllFaculties();
            
            // Konvertiert die Fakultäten in eine ArrayList
            facultyList = new ArrayList<>(facultySet);
        } catch (CustomException ex) {
            // Bei einer Ausnahme wird eine leere Liste initialisiert
            facultyList = new ArrayList<>();
            System.out.println(ex);
        }
    }

    /**
     * Gibt die Anzahl der Zeilen in der Tabelle zurück, basierend auf der Anzahl der Fakultäten.
     * 
     * @return Die Anzahl der Zeilen (Anzahl der Fakultäten)
     */
    @Override
    public int getRowCount() {
        return facultyList.size();
    }

    /**
     * Gibt die Anzahl der Spalten in der Tabelle zurück.
     * 
     * @return Die Anzahl der Spalten (4: Fakultäts-ID, Name, Abteilung und Kontakt)
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
        Faculty faculty = facultyList.get(row);
        switch (col) {
        case 0:
            return Integer.toString(faculty.getEmployeeId());  // Fakultäts-ID zurückgeben
        case 1:
            return faculty.getName();  // Fakultätsname zurückgeben
        case 2:
            return faculty.getDepartment();  // Fakultätsabteilung zurückgeben
        case 3:
            return Long.toString(faculty.getContact());  // Kontakttelefonnummer zurückgeben
        default:
            break;
        }
        return null;
    }

    /**
     * Gibt den Namen der angegebenen Spalte zurück (ID, Name, Abteilung oder Kontakt).
     * 
     * @param c Der Index der Spalte
     * @return Der Name der Spalte als String
     */
    @Override
    public String getColumnName(int c) {
        return title[c];
    }
}
