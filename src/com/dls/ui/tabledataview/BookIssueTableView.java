package com.dls.ui.tabledataview;

import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 * Die Klasse `BookIssueTableView` ist eine benutzerdefinierte Implementierung eines 
 * Tabellenmodells, das für die Darstellung von ausgeliehenen Büchern in einer GUI verwendet 
 * wird. Es erbt von `AbstractTableModel` und überschreibt Methoden, um Daten zu liefern, 
 * die in einer JTable angezeigt werden können.
 */
public class BookIssueTableView extends AbstractTableModel {

    // Speichert die zugehörigen Person IDs und Buch IDs in Form eines HashMaps
    private HashMap<String, String> booksIssued;
    
    // Listen, die Person IDs und Buch IDs speichern
    ArrayList<String> personIDs, bookIDs;
    
    // Spaltenüberschriften für die Tabelle
    private String title[] = {"Person ID ", "Book ID "};

    /**
     * Konstruktor der Klasse `BookIssueTableView`.
     * Initialisiert das Tabellenmodell mit den ausgeliehenen Büchern, 
     * die durch den `BookControllerInterface` bereitgestellt werden.
     * 
     * @param bookController Das Interface, das Zugriff auf die ausgeliehenen Bücher bietet
     */
    public BookIssueTableView(BookControllerInterface bookController) {
        try {
            // Holt die ausgeliehenen Bücher über das Interface
            booksIssued = bookController.booksIssued();
            
            // Wenn ausgeliehene Bücher vorhanden sind, werden die IDs extrahiert
            if (booksIssued != null) {
                personIDs = new ArrayList<>();
                bookIDs = new ArrayList<>();
                
                // Durchläuft die Bücher und füllt die Listen mit Person IDs und Buch IDs
                for (Object k : booksIssued.keySet()) {
                    personIDs.add((String) k);
                }
                for (Object k : booksIssued.values()) {
                    bookIDs.add((String) k);
                }
            }
        } catch (CustomException CustomException) {
            // Bei einer Ausnahme wird ein leerer HashMap initialisiert
            booksIssued = new HashMap<>();
        }
    }

    /**
     * Gibt die Anzahl der Spalten in der Tabelle zurück.
     * 
     * @return Die Anzahl der Spalten (2: Person ID und Book ID)
     */
    @Override
    public int getColumnCount() {
        return title.length;
    }

    /**
     * Gibt die Anzahl der Zeilen in der Tabelle zurück, basierend auf der Anzahl der 
     * ausgeliehenen Bücher.
     * 
     * @return Die Anzahl der Zeilen (Anzahl der ausgeliehenen Bücher)
     */
    @Override
    public int getRowCount() {
        return booksIssued.size();
    }

    /**
     * Gibt den Namen einer Spalte zurück (Person ID oder Book ID).
     * 
     * @param c Der Index der Spalte
     * @return Der Name der Spalte als String
     */
    @Override
    public String getColumnName(int c) {
        return title[c];
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
        if (col == 0) {
            return personIDs.get(row).trim();  // Person ID zurückgeben
        }
        if (col == 1) {
            return bookIDs.get(row).trim();  // Buch ID zurückgeben
        }
        return null;
    }

    /**
     * Gibt den Datentyp der angegebenen Spalte zurück. 
     * In diesem Fall ist der Datentyp für beide Spalten `String`.
     * 
     * @param c Der Index der Spalte
     * @return Die Klasse des Datentyps (String)
     */
    @Override
    public Class getColumnClass(int c) {
        try {
            if (c == 0) {
                return Class.forName("java.lang.String");  // Person ID ist vom Typ String
            }
            if (c == 1) {
                return Class.forName("java.lang.String");  // Book ID ist vom Typ String
            }

        } catch (Exception exception) {
            System.out.println("model.BookModel : Class getColumnClass(int c)" + exception.getMessage());
        }
        return null;
    }

    /**
     * Gibt zurück, ob die Zellen in der Tabelle bearbeitbar sind. In diesem Fall sind alle Zellen
     * nicht bearbeitbar.
     * 
     * @param r Die Zeilenposition
     * @param c Die Spaltenposition
     * @return `false`, da keine Zellen bearbeitet werden können
     */
    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }
}
