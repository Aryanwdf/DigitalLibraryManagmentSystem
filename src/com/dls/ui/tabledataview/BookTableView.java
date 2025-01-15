package com.dls.ui.tabledataview;

import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import java.util.*;
import javax.swing.table.*;

/**
 * Die Klasse `BookTableView` ist eine benutzerdefinierte Implementierung eines 
 * Tabellenmodells, das eine Bibliothek von Büchern in einer GUI anzeigt. Es erbt von `AbstractTableModel`
 * und überschreibt Methoden, um die Daten zu liefern, die in einer JTable angezeigt werden können.
 */
public class BookTableView extends AbstractTableModel {

    // Speichert die Bibliothek von Büchern als HashMap mit Book ID als Schlüssel
    private HashMap<String, Book> booksLibrary = null;
    
    // Liste von Buch IDs, die als Schlüssel für die HashMap verwendet werden
    private List<String> bookIDs = null;
    
    // Spaltenüberschriften für die Tabelle
    private String title[] = {"ID", "Name", "Author", "Publisher"};

    /**
     * Konstruktor der Klasse `BookTableView`.
     * Initialisiert das Tabellenmodell mit den Büchern, 
     * die durch den `BookControllerInterface` bereitgestellt werden.
     * 
     * @param bookController Das Interface, das Zugriff auf die Buchbibliothek bietet
     */
    public BookTableView(BookControllerInterface bookController) {
        try {
            // Holt die Bibliothek von Büchern über das Interface
            booksLibrary = bookController.buildBooksLibrary();
            
            // Extrahiert die Buch IDs aus der Bibliothek
            bookIDs = new ArrayList<>(booksLibrary.keySet());
        } catch (CustomException customException) {
            // Bei einer Ausnahme wird eine leere Bibliothek initialisiert
            booksLibrary = new HashMap<>();
            System.out.println(customException.getMessage());
        }
    }

    /**
     * Gibt die Anzahl der Spalten in der Tabelle zurück.
     * 
     * @return Die Anzahl der Spalten (4: ID, Name, Autor und Verlag)
     */
    @Override
    public int getColumnCount() {
        return title.length;
    }

    /**
     * Gibt die Anzahl der Zeilen in der Tabelle zurück, basierend auf der Anzahl der Bücher in der Bibliothek.
     * 
     * @return Die Anzahl der Zeilen (Anzahl der Bücher)
     */
    @Override
    public int getRowCount() {
        return booksLibrary.size();
    }

    /**
     * Gibt den Namen einer Spalte zurück (ID, Name, Autor oder Verlag).
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
        // Holt das Buchobjekt anhand der Buch-ID
        Book book = booksLibrary.get(bookIDs.get(row));

        if (col == 0) {
            return Integer.toString(book.getId());  // ID des Buches zurückgeben
        }
        if (col == 1) {
            return book.getName();  // Name des Buches zurückgeben
        }
        if (col == 2) {
            return book.getAuthor();  // Autor des Buches zurückgeben
        }
        if (col == 3) {
            return book.getPublisher();  // Verlag des Buches zurückgeben
        }
        return null;
    }

    /**
     * Gibt den Datentyp der angegebenen Spalte zurück.
     * In diesem Fall ist der Datentyp für alle Spalten `String`.
     * 
     * @param c Der Index der Spalte
     * @return Die Klasse des Datentyps (String)
     */
    @Override
    public Class getColumnClass(int c) {
        try {
            if (c == 0) {
                return Class.forName("java.lang.String");
            }
            if (c == 1) {
                return Class.forName("java.lang.String");
            }
            if (c == 2) {
                return Class.forName("java.lang.String");
            }
            if (c == 3) {
                return Class.forName("java.lang.String");
            }

        } catch (Exception exception) {
            System.out.println("model.BookModel : Class getColumnClass(int c)" + exception.getMessage());
            //remove after testing
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
