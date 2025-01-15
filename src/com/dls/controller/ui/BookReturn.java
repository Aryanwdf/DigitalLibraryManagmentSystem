package com.dls.ui;

// Importiert die benötigten Klassen für die GUI-Entwicklung und Ereignisbehandlung
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Importiert benutzerdefinierte Klassen und Interfaces
import com.dls.contstant.GlobalResources;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.entity.Student;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;
import com.dls.ui.tabledataview.BookIssueTableView;

public class BookReturn extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

    // Enumeration zur Definition der Bewegungsrichtung
    public enum Direction {
        FORWARD, BACKWARD
    };

    // Deklaration der GUI-Komponenten
    private final JTable booksTable;
    private final JTextField searchBox;
    private final JLabel heading, error, errorIcon, search, totalCount, list, close, found;
    private final JScrollPane scrollPane;
    private BookIssueTableView bookIssueTableView;
    private final IssueDetails issueDetails;
    private final Container container;
    private final ReturnBook returnBookPanel;
    private LibraryMenu libraryMenu;
    private final BookControllerInterface bookController;
    private final StudentControllerInterface studentController;
    private final FacultyControllerInterface facultyController;
    ArrayList<Book> bookIssueArrayList;
    boolean isStudentReturn;
    int personIdReturn;

    // Konstruktor der Klasse, der die GUI initialisiert und alle Komponenten hinzufügt
    public BookReturn(LibraryMenu libraryMenu, BookControllerInterface bookController,
            StudentControllerInterface studentController, FacultyControllerInterface facultyController) {
        try {
            // Setzt das Look and Feel auf das des Systems
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Zeigt eine Fehlermeldung, wenn das Look and Feel nicht gesetzt werden konnte
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Initialisiert die Controller
        this.bookController = bookController;
        this.studentController = studentController;
        this.facultyController = facultyController;
        // Setzt das Standard-Verhalten beim Schließen des Fensters
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.libraryMenu = libraryMenu;
        returnBookPanel = new ReturnBook();
        // Erstellt ein JLabel für das Suchen von Büchern
        found = new JLabel("");
        found.setForeground(Color.red);
        found.setVisible(true);
        issueDetails = new IssueDetails();
        this.setUndecorated(false);
        close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
        
        // Fügt einen WindowListener hinzu, der das Bibliotheksmenü anzeigt, wenn das Fenster geschlossen wird
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent me) {
                BookReturn.this.libraryMenu.setVisible(true);
                BookReturn.this.libraryMenu.setParentActive();
                BookReturn.this.dispose();
            }
        });

        // Setzt das Fenster-Layout und initialisiert alle Komponenten
        heading = new JLabel("Digital Library Management System - Book Return");
        heading.setFont(new Font("Times New Roman", Font.BOLD, 26));
        getContentPane().setLayout(null);
        error = new JLabel("");
        errorIcon = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
        container = this.getContentPane();
        container.setBackground(new Color(240, 240, 240));
        bookIssueTableView = new BookIssueTableView(bookController);
        search = new JLabel("Search Book:");
        search.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/find_icon.png")));
        search.setFont(new Font("Times New Roman", Font.BOLD, 20));
        totalCount = new JLabel("");
        totalCount.setFont(new Font("Times New Roman", Font.BOLD, 20));
        list = new JLabel("Total Records: ");
        list.setFont(new Font("Times New Roman", Font.BOLD, 20));
        searchBox = new JTextField("");
        booksTable = new JTable(bookIssueTableView);
        
        // Setzt das Design der JTable-Komponenten
        booksTable.setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
        this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(170);
        this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookIssueTableView.getColumnName(0));
        this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookIssueTableView.getColumnName(1));
        this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        this.booksTable.getTableHeader().setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
        this.setAreaTypeTableRowHeight();  // Setzt die Höhe der Tabellenzeilen
        this.scrollPane = new JScrollPane(booksTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
        
        // Setzt die Position und Größe der GUI-Komponenten
        search.setBounds(10, 70, 162, 30);
        searchBox.setBounds(182, 70, 328, 35);
        found.setBounds(310, 100, 80, 10);
        list.setBounds(10, 123, 132, 30);
        totalCount.setBounds(152, 123, 100, 30);
        scrollPane.setBounds(10, 165, 350, 240);
        issueDetails.setBounds(380, 154, 615, 250);
        returnBookPanel.setBounds(10, 420, 990, 170);
        errorIcon.setBounds(10, 590, 32, 32);
        error.setBounds(40, 590, 800, 32);
        error.setForeground(Color.red);
        error.setFont(new Font("Times New Roman", Font.BOLD, 15));
        heading.setBounds(80, 10, 590, 40);
        close.setBounds(980, 3, 32, 32);
        
        // Fügt alle Komponenten zum Container hinzu
        container.add(list);
        container.add(search);
        container.add(searchBox);
        container.add(totalCount);
        container.add(this.scrollPane);
        container.add(heading);
        container.add(found);
        container.add(returnBookPanel);
        container.add(issueDetails);
        
        errorIcon.setVisible(false);  // Versteckt das Fehler-Icon
        this.setSize(1020, 647);  // Setzt die Fenstergröße
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);  // Zentriert das Fenster
        this.setResizable(false);  // Verhindert das Vergrößern des Fensters
        this.setTitle("Book Return - " + GlobalResources.APPLICATION_NAME);  // Setzt den Fenstertitel
        this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());  // Setzt das Fenstersymbol
        updateBookCount();  // Aktualisiert die Anzahl der Bücher
        addListeners();  // Fügt Listener hinzu
        issueDetails.setDefaults(false);  // Setzt die Standardwerte für die Ausleihdetails
        found.setVisible(true);
        this.setTitle("Book Return");  // Setzt den Titel erneut
        this.setVisible(true);  // Zeigt das Fenster an
    }

    // Methode zum Aktualisieren der Bücheranzahl
    public void refreshAll() {
        // Erneuert die Ansicht der ausgeliehenen Bücher
        bookIssueTableView = new BookIssueTableView(bookController);
        booksTable.setModel(bookIssueTableView);
        setAreaTypeTableRowHeight();  // Setzt die Höhe der Zeilen
        booksTable.repaint();  // Zeichnet die Tabelle neu
        booksTable.setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
        this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(170);
        this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookIssueTableView.getColumnName(0));
        this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookIssueTableView.getColumnName(1));
        this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        this.booksTable.getTableHeader().setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
        this.setAreaTypeTableRowHeight();  // Setzt erneut die Höhe der Zeilen
        updateBookCount();  // Aktualisiert die Anzahl der Bücher
    }

    // Methode zum Setzen der Zeilenhöhe der Tabelle
    private void setAreaTypeTableRowHeight() {
        // Setzt die Zeilenhöhe jeder Zeile in der Tabelle
        for (int x = 0; x < this.booksTable.getRowCount(); x++) {
            this.booksTable.setRowHeight(x, 25);
        }
    }

    // Methode zum Hinzufügen der Listener für die Tabelle und das Suchfeld
    public void addListeners() {
        // Hört auf Änderungen an der Auswahl der Tabelle und an Änderungen im Suchfeld
        booksTable.getSelectionModel().addListSelectionListener(this);
        searchBox.getDocument().addDocumentListener(this);
    }



// Till Here

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Diese Methode wird aufgerufen, wenn eine Aktion (z.B. Button-Klick) ausgeführt wird.
        // Aktuell ist sie leer und erwartet eine konkrete Implementierung.
    }

    public void updateBookCount() {
        // Diese Methode aktualisiert die Anzeige der Gesamtzahl der Bücher in der `totalCount`-Label.
        // Die Anzahl der Zeilen in `bookIssueTableView` (die Tabelle mit den Buchausgaben) wird abgerufen
        // und als Text im `totalCount`-Label angezeigt.
        this.totalCount.setText(String.valueOf(bookIssueTableView.getRowCount()));
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        // Diese Methode wird aufgerufen, wenn Text in das Dokument (Suchfeld) eingefügt wird.
        // Sie ruft die `search`-Methode auf und sucht nach dem eingegebenen Text.
        this.search(this.searchBox.getText().trim(), 0, Direction.FORWARD, false);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        // Diese Methode wird aufgerufen, wenn Text aus dem Dokument (Suchfeld) entfernt wird.
        // Sie ruft die `search`-Methode auf und sucht nach dem Text in umgekehrter Richtung.
        this.search(this.searchBox.getText().trim(), 0, Direction.BACKWARD, false);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        // Diese Methode wird aufgerufen, wenn sich Attribute im Dokument ändern.
        // In der Anwendung wird in diesem Fall nichts unternommen.
    }

    private void search(String electronicUnitName, int fromIndex, Direction direction, boolean isSelected) {
        // Diese Methode führt eine Suche in der Tabelle durch. Sie durchsucht die erste und zweite Spalte
        // der Tabelle nach dem `electronicUnitName` und markiert die entsprechende Zeile.
        if (electronicUnitName.length() == 0) {
            // Wenn der Suchtext leer ist, wird die Suche abgebrochen und alle Zeilen werden deselektiert.
            found.setText("");
            this.booksTable.clearSelection();
            return;
        }
        if (booksTable.getRowCount() == 0) {
            // Wenn es keine Zeilen in der Tabelle gibt, wird die Methode abgebrochen.
            return;
        }
        if (direction == Direction.FORWARD) {
            // Wenn die Suche in Vorwärtsrichtung erfolgt, wird die Tabelle von `fromIndex` bis zum Ende durchsucht.
            for (int x = fromIndex; x < bookIssueTableView.getRowCount(); x++) {
                // Überprüft, ob der Name in der ersten oder zweiten Spalte mit dem Suchtext übereinstimmt.
                if (((String) this.bookIssueTableView.getValueAt(x, 0)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
                    // Wenn ein Treffer gefunden wird, wird die Zeile markiert und die Ansicht darauf gescrollt.
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    found.setText("");
                    return;
                } else if (((String) this.bookIssueTableView.getValueAt(x, 1)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
                    // Wenn ein Treffer gefunden wird, wird die Zeile markiert und die Ansicht darauf gescrollt.
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    found.setText("");
                    return;
                }
                found.setText("Not Found");
            }
        }
        if (direction == Direction.BACKWARD) {
            // Wenn die Suche in Rückwärtsrichtung erfolgt, wird die Tabelle von `fromIndex` bis zum Anfang durchsucht.
            for (int x = fromIndex; x >= 0; x--) {
                // Überprüft, ob der Name in der ersten oder zweiten Spalte mit dem Suchtext übereinstimmt.
                if (((String) this.bookIssueTableView.getValueAt(x, 0)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
                    // Wenn ein Treffer gefunden wird, wird die Zeile markiert und die Ansicht darauf gescrollt.
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    found.setText("");
                    return;
                } else if (((String) this.bookIssueTableView.getValueAt(x, 1)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
                    // Wenn ein Treffer gefunden wird, wird die Zeile markiert und die Ansicht darauf gescrollt.
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    found.setText("");
                    return;
                }
                found.setText("Not Found");
            }
        }
        if (!isSelected) {
            // Wenn `isSelected` false ist, wird die Auswahl in der Tabelle aufgehoben.
            found.setText("");
            this.booksTable.clearSelection();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        // Diese Methode wird aufgerufen, wenn die Auswahl in der `booksTable` geändert wird.
        // Wenn `issueDetails` null ist, wird die Methode beendet.
        if (issueDetails == null) {
            return;
        }
        int selectedRowIndex = this.booksTable.getSelectedRow();
        // Wenn keine Zeile ausgewählt oder die Auswahl außerhalb der Zeilenanzahl liegt, wird `setDefaults(false)` aufgerufen.
        if (selectedRowIndex < 0 || selectedRowIndex >= this.booksTable.getRowCount()) {
            issueDetails.setDefaults(false);
        } else {
            try {
                // Holt sich die Werte der ausgewählten Zeile und zeigt die Details an.
                String id = (String) booksTable.getValueAt(selectedRowIndex, 0);
                String books = (String) booksTable.getValueAt(selectedRowIndex, 1);
                showDetails(id, books);
                errorIcon.setVisible(false);
                error.setText("");
            } catch (Exception e) {
                // Wenn ein Fehler auftritt, wird eine Fehlermeldung angezeigt.
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                errorIcon.setVisible(true);
                error.setText(e.getMessage());
                issueDetails.setDefaults(false);
            }
        }
    }

    public void showDetails(String id, String books) throws Exception {
        // Diese Methode zeigt die Details zu einem bestimmten Buchausleihvorgang an.
        // Sie verarbeitet `id` und `books`, um entweder Studenten- oder Fakultätsdetails anzuzeigen.
        Student student = null;
        System.out.println(id);
        Faculty faculty = null;
        bookIssueArrayList = new ArrayList<>();
        String[] values = id.split(" : ");
        personIdReturn = Integer.parseInt(values[1]);
        if (values[0].trim().contains("Student")) {
            // Wenn der ID-Wert "Student" enthält, wird ein Studentenobjekt abgerufen.
            student = studentController.getStudent(values[1].trim());
            isStudentReturn = true;
        } else {
            // Ansonsten wird ein Fakultätsobjekt abgerufen.
            faculty = facultyController.getFaculty(values[1].trim());
            isStudentReturn = false;
        }
        // Teilt die Bücherliste und fügt die Bücher zur `bookIssueArrayList` hinzu.
        String[] bookIds = books.split(",");
        for (String str : bookIds) {
            bookIssueArrayList.add(bookController.getBook(Integer.parseInt(str)));
        }
        if (student == null) {
            // Wenn kein Student gefunden wird, zeigt es die Fakultätsdetails an.
            issueDetails.setFacultyDetails(faculty, bookIssueArrayList);
        } else {
            // Ansonsten zeigt es die Studentendetails an.
            issueDetails.setStudentDetails(student, bookIssueArrayList);
        }
    }

// Till Here

    /**
     * Die Klasse IssueDetails repräsentiert ein JPanel, das Details zu ausgeliehenen Büchern
     * und den ausleihenden Personen oder Fakultäten anzeigt.
     */
    public class IssueDetails extends JPanel {

        // Labels für die Anzeige von Informationen
        JLabel name, id, b1, b2, b3, b4, b5, person, books, contact;

        // Referenz auf eine Instanz von BookReturn
        private BookReturn br;

        /**
         * Konstruktor der Klasse IssueDetails.
         * Initialisiert das Panel, die Layout-Einstellungen und die grafischen Komponenten.
         */
        IssueDetails() {
            // Layout wird deaktiviert, um absolute Positionierung zu verwenden.
            this.setLayout(null);
            
            // Referenz auf das übergeordnete BookReturn-Objekt.
            br = BookReturn.this;

            // Initialisierung der Labels mit leerem Text und Schriftart-Einstellungen.
            name = new JLabel("");
            name.setFont(new Font("Times New Roman", Font.BOLD, 15));
            id = new JLabel("");
            id.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b1 = new JLabel("");
            b1.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b2 = new JLabel("");
            b2.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b3 = new JLabel("");
            b3.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b4 = new JLabel("");
            b4.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b5 = new JLabel("");
            b5.setFont(new Font("Times New Roman", Font.BOLD, 15));

            // Initialisierung weiterer Labels
            contact = new JLabel("");
            contact.setFont(new Font("Times New Roman", Font.BOLD, 15));
            person = new JLabel("Issued to : ");
            person.setFont(new Font("Times New Roman", Font.BOLD, 20));
            books = new JLabel("Book List : ");
            books.setFont(new Font("Times New Roman", Font.BOLD, 20));

            // Festlegen der Positionen der Labels im Panel.
            person.setBounds(10, 15, 500, 20);
            name.setBounds(10, 40, 500, 20);
            id.setBounds(10, 60, 500, 20);
            contact.setBounds(10, 80, 500, 20);
            books.setBounds(10, 110, 500, 20);
            b1.setBounds(10, 140, 500, 20);
            b2.setBounds(10, 160, 500, 20);
            b3.setBounds(10, 180, 500, 20);
            b4.setBounds(10, 200, 500, 20);
            b5.setBounds(10, 220, 500, 20);

            // Hinzufügen der Komponenten zum Panel.
            this.add(b1);
            this.add(b4);
            this.add(b3);
            this.add(b2);
            this.add(b5);
            this.add(person);
            this.add(name);
            this.add(id);
            this.add(contact);
            this.add(books);

            // Sichtbarkeit des Panels aktivieren.
            this.setVisible(true);

            // Hintergrundfarbe des Panels einstellen.
            this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
                    GlobalResources.COLOR_CODE_B));

            // Rahmen mit Titel hinzufügen.
            TitledBorder tb = new TitledBorder(new EtchedBorder(), "Issue Details");
            tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
            this.setBorder(tb);
        }

        /**
         * Setzt alle Labels auf ihre Standardwerte zurück und legt fest,
         * ob sie sichtbar sind.
         *
         * @param value Sichtbarkeitsstatus der Labels.
         */
        public void setDefaults(boolean value) {
            id.setVisible(value);
            b1.setVisible(value);
            b2.setVisible(value);
            b3.setVisible(value);
            b4.setVisible(value);
            b5.setVisible(value);
            name.setVisible(value);
            contact.setVisible(false);
            b1.setText("");
            b2.setText("");
            b3.setText("");
            b4.setText("");
            b5.setText("");
            name.setText("");
            id.setText("");
            contact.setText("");
        }

        /**
         * Setzt die Details eines Studenten, einschließlich Name, ID und Kontaktinformationen,
         * sowie eine Liste der ausgeliehenen Bücher.
         *
         * @param s                 Das Student-Objekt mit den Details des Studenten.
         * @param bookIssueArrayList Eine Liste der ausgeliehenen Bücher.
         */
        public void setStudentDetails(Student s, ArrayList<Book> bookIssueArrayList) {
            setDefaults(false);
            id.setVisible(true);
            name.setVisible(true);
            contact.setVisible(true);
            id.setText("StudentId : " + s.getStudentId());
            name.setText("Name      : " + s.getName());
            contact.setText("Contact   : " + s.getContact());
            setBooks(bookIssueArrayList);
        }

        /**
         * Setzt die Details eines Fakultätsmitglieds, einschließlich Name, ID und Abteilung,
         * sowie eine Liste der ausgeliehenen Bücher.
         *
         * @param s                 Das Faculty-Objekt mit den Details des Fakultätsmitglieds.
         * @param bookIssueArrayList Eine Liste der ausgeliehenen Bücher.
         */
        public void setFacultyDetails(Faculty s, ArrayList<Book> bookIssueArrayList) {
            setDefaults(false);
            id.setVisible(true);
            name.setVisible(true);
            contact.setVisible(true);
            id.setText("FacultyId : " + s.getEmployeeId());
            name.setText("Name      : " + s.getName());
            contact.setText("Department: " + s.getDepartment());
            setBooks(bookIssueArrayList);
        }

        /**
         * Aktualisiert die Buchliste und zeigt bis zu fünf ausgeliehene Bücher an.
         *
         * @param bookIssueArrayList Eine Liste der ausgeliehenen Bücher.
         */
        public void setBooks(ArrayList<Book> bookIssueArrayList) {
            int i = 1;
            for (Book book : bookIssueArrayList) {
                switch (i) {
                    case 1:
                        b1.setVisible(true);
                        b1.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
                        break;
                    case 2:
                        b2.setVisible(true);
                        b2.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
                        break;
                    case 3:
                        b3.setVisible(true);
                        b3.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
                        break;
                    case 4:
                        b4.setVisible(true);
                        b4.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
                        break;
                    case 5:
                        b5.setVisible(true);
                        b5.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
                        break;
                    default:
                        break;
                }
                i++;
            }
        }
    }
// Till Here

 // Definiert die Klasse ReturnBook, die JPanel erweitert und die Interfaces ActionListener und ItemListener implementiert
    class ReturnBook extends JPanel implements ActionListener, ItemListener {

        // Definiert die Schaltflächen (Buttons), die in der Benutzeroberfläche verwendet werden
        private final JButton returnButton, saveButton, close;
        // Definiert die Labels, die in der Benutzeroberfläche angezeigt werden
        private final JLabel personIdLabel, b1, b2, b3, b4, b5, b111, b222, b333, b444, b555, idEr, qtyEr, qtyLabel;
        // Definiert Textfelder zur Eingabe von Werten
        private JTextField b11, b22, b33, b44, b55, personIdTextBox;
        // Definiert eine Instanz der BookReturn-Klasse
        private final BookReturn br;
        // Definiert eine Dropdown-Auswahl für die Anzahl (Quantity) der zurückgegebenen Bücher
        private final JComboBox qty;
        // Definiert eine ArrayList von Book-Objekten, die die Bücher repräsentieren
        ArrayList<Book> books = null;
        // Eine Ganzzahl, die verwendet wird, um eine Anzahl zu speichern
        int num = 0;

        // Konstruktor der ReturnBook-Klasse
        ReturnBook() {
            // Verweist auf die BookReturn-Instanz
            br = BookReturn.this;
            // Initialisiert das Label für die "Quantity"
            qtyLabel = new JLabel("Quantity:");
            qtyLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
            // Initialisiert die ComboBox mit möglichen Werten für die Menge
            String values[] = { "Select", "1", "2", "3", "4", "5" };
            qty = new JComboBox(values);
            // Initialisiert das Fehlerlabel für die Menge
            qtyEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            // Setzt das Layout der Benutzeroberfläche auf null (für manuelle Positionierung der Komponenten)
            this.setLayout(null);
            // Initialisiert die Schaltflächen
            returnButton = new JButton("Return");
            saveButton = new JButton("  Save");
            close = new JButton("  Close");
            // Initialisiert die Label für die Personen-ID
            personIdLabel = new JLabel("PersonId:");
            personIdLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
            // Initialisiert das Fehlerlabel für die Personen-ID
            idEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            // Initialisiert die Label für die Bücher
            b1 = new JLabel("Book 1:");
            b1.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b2 = new JLabel("Book 2:");
            b2.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b3 = new JLabel("Book 3:");
            b3.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b4 = new JLabel("Book 4:");
            b4.setFont(new Font("Times New Roman", Font.BOLD, 15));
            b5 = new JLabel("Book 5:");
            b5.setFont(new Font("Times New Roman", Font.BOLD, 15));
            // Initialisiert die Fehlerlabel für jedes Buch
            b111 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            b222 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            b333 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            b444 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            b555 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
            // Initialisiert Textfelder für die Buch-IDs
            b11 = new JTextField("");
            b22 = new JTextField("");
            b33 = new JTextField("");
            b44 = new JTextField("");
            b55 = new JTextField("");
            // Initialisiert das Textfeld für die Personen-ID
            personIdTextBox = new JTextField("");
            // Setzt die Position und Größe der Schaltflächen und Textfelder
            returnButton.setBounds(20, 25, 115, 40);
            returnButton.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/return_book.png")));
            returnButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            saveButton.setBounds(150, 20, 115, 40);
            close.setBounds(300, 20, 115, 40);
            saveButton.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
            saveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            close.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/close_icon.png")));
            close.setFont(new Font("Times New Roman", Font.BOLD, 14));
            personIdLabel.setBounds(10, 70, 80, 30);
            personIdTextBox.setBounds(90, 70, 100, 30);
            idEr.setBounds(190, 70, 30, 30);
            qtyLabel.setBounds(234, 70, 70, 30);
            qty.setBounds(300, 70, 80, 30);
            qtyEr.setBounds(380, 70, 30, 30);
            b1.setBounds(10, 110, 50, 30);
            b11.setBounds(70, 110, 100, 30);
            b111.setBounds(160, 110, 30, 30);
            b2.setBounds(200, 110, 60, 30);
            b22.setBounds(260, 110, 100, 30);
            b222.setBounds(360, 110, 30, 30);
            b3.setBounds(400, 110, 60, 30);
            b33.setBounds(460, 110, 100, 30);
            b333.setBounds(560, 110, 30, 30);
            b4.setBounds(600, 110, 60, 30);
            b44.setBounds(660, 110, 100, 30);
            b444.setBounds(760, 110, 30, 30);
            b5.setBounds(800, 110, 60, 30);
            b55.setBounds(860, 110, 100, 30);
            b555.setBounds(960, 110, 30, 30);
            // Fügt die Schaltflächen, Labels und Textfelder zur Benutzeroberfläche hinzu
            this.add(b1);
            this.add(b2);
            this.add(b3);
            this.add(b4);
            this.add(b5);
            this.add(b11);
            this.add(b22);
            this.add(b33);
            this.add(b44);
            this.add(b55);
            this.add(b111);
            this.add(b222);
            this.add(b333);
            this.add(b444);
            this.add(b555);
            this.add(close);
            this.add(returnButton);
            this.add(saveButton);
            this.add(personIdTextBox);
            this.add(personIdLabel);
            this.add(idEr);
            this.add(qtyLabel);
            this.add(qty);
            this.add(qtyEr);
            // Macht die Benutzeroberfläche sichtbar und setzt den Hintergrund
            this.setVisible(true);
            this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
                    GlobalResources.COLOR_CODE_B));
            // Fügt der Benutzeroberfläche einen Titelrahmen hinzu
            TitledBorder tb = new TitledBorder(new EtchedBorder(), "Return Book");
            tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
            this.setBorder(tb);
            // Setzt die Standardwerte für die Benutzeroberfläche
            this.setDefault();
            // Fügt ActionListener für die Schaltflächen hinzu
            returnButton.addActionListener(this);
            saveButton.addActionListener(this);
            close.addActionListener(this);
            // Setzt die Standardauswahl für die Menge (Quantity)
            this.qty.setSelectedIndex(0);
            // Macht das Label für die Menge sichtbar
            this.qtyLabel.setVisible(true);
            // Deaktiviert die Auswahl der Menge, wenn sie nicht benötigt wird
            this.qty.setEnabled(false);
            // Fügt einen ItemListener für die Menge hinzu
            qty.addItemListener(this);
        }

        // Setzt die Standardwerte für die Rückgabeansicht
        public void setDefault() {
            b111.setVisible(false);
            b222.setVisible(false);
            b333.setVisible(false);
            b444.setVisible(false);
            b555.setVisible(false);
            idEr.setVisible(false);
            // Setzt die Hintergrundfarbe der Textfelder auf Grau
            b11.setBackground(new Color(240, 240, 240));
            b22.setBackground(new Color(240, 240, 240));
            b33.setBackground(new Color(240, 240, 240));
            b44.setBackground(new Color(240, 240, 240));
            b55.setBackground(new Color(240, 240, 240));
            // Setzt den Text für die Personen-ID auf den Standardwert
            personIdLabel.setText("Person Id:");
            // Setzt den Hintergrund der Personen-ID-TextBox auf Grau
            personIdTextBox.setBackground(new Color(240, 240, 240));
            // Setzt die Auswahl der Menge zurück
            this.qty.setSelectedIndex(0);
            qtyEr.setVisible(false);
            // Macht das Personen-ID-Textfeld nicht bearbeitbar
            personIdTextBox.setEditable(false);
            // Deaktiviert die Menge und stellt sie auf eine bestimmte Farbe
            this.qty.setSelectedIndex(0);
            this.qty.setBackground(new Color(225, 176, 98));
            this.qty.setEnabled(false);
            // Macht die Textfelder für die Bücher nicht bearbeitbar
            b11.setEditable(false);
            b22.setEditable(false);
            b33.setEditable(false);
            b44.setEditable(false);
            b55.setEditable(false);
            // Versteckt die Schaltflächen "Close" und "Save"
            close.setVisible(false);
            saveButton.setVisible(false);
            // Löscht die Inhalte der Textfelder
            personIdTextBox.setText("");
            b11.setText("");
            b22.setText("");
            b33.setText("");
            b44.setText("");
            b55.setText("");
        }

     // Diese Methode wird aufgerufen, wenn eine Aktion (z. B. ein Button-Klick) ausgelöst wird.
        public void actionPerformed(ActionEvent ae) {

            // Wenn der "close" Button geklickt wurde, werden folgende Aktionen durchgeführt.
            if (ae.getSource() == close) {
                // Der "returnButton" wird sichtbar gemacht.
                returnButton.setVisible(true);
                // Setzt die Standardwerte zurück.
                setDefault();
                // Ermöglicht das Bearbeiten des Suchfelds.
                br.searchBox.setEditable(true);
                // Entfernt die Auswahl in der Tabelle.
                br.booksTable.clearSelection();
                // Aktiviert die Bücher-Tabelle.
                br.booksTable.setEnabled(true);
                // Setzt die "issueDetails" auf die Standardwerte.
                br.issueDetails.setDefaults(false);
            }

            // Wenn der "returnButton" geklickt wurde, wird die Rückgabe von Büchern verarbeitet.
            if (ae.getSource() == returnButton) {
                // Holt sich die ausgewählte Zeile aus der Bücher-Tabelle.
                int selectedRowIndex = br.booksTable.getSelectedRow();
                // Falls keine Zeile ausgewählt wurde, wird eine Fehlermeldung angezeigt.
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(this, "Select a record to return.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Macht den "returnButton" unsichtbar.
                returnButton.setVisible(false);
                // Setzt die Standardwerte zurück.
                setDefault();
                // Setzt die Auswahl des "qty"-Dropdowns auf den letzten Index.
                this.qty.setSelectedIndex(bookIssueArrayList.size());
                // Macht den "close"-Button sichtbar und den "saveButton" sichtbar.
                close.setVisible(true);
                saveButton.setVisible(true);
                // Deaktiviert das Suchfeld und die Bücher-Tabelle.
                br.searchBox.setEditable(false);
                br.booksTable.clearSelection();
                br.booksTable.setEnabled(false);
                // Setzt die "issueDetails" auf die Standardwerte zurück.
                br.issueDetails.setDefaults(false);
                // Setzt die Person-ID in das Textfeld.
                personIdTextBox.setText(Integer.toString(personIdReturn));
                // Setzt das Label für die Person-ID, je nach Typ (Student oder Faculty).
                if (isStudentReturn) {
                    personIdLabel.setText("Student Id:");
                } else {
                    personIdLabel.setText("Faculty Id:");
                }
                // Iteriert über alle Bücher in der Liste und weist ihnen die richtigen IDs zu.
                int i = 1;
                for (Book book : bookIssueArrayList) {
                    switch (i) {
                    case 1:
                        b11.setText(Integer.toString(book.getId()));
                        break;
                    case 2:
                        b22.setText(Integer.toString(book.getId()));
                        break;
                    case 3:
                        b33.setText(Integer.toString(book.getId()));
                        break;
                    case 4:
                        b44.setText(Integer.toString(book.getId()));
                        break;
                    case 5:
                        b55.setText(Integer.toString(book.getId()));
                        break;
                    default:
                        break;
                    }
                    i++;
                }
            }

            // Wenn der "saveButton" geklickt wurde, wird die Rückgabe der Bücher gespeichert.
            if (ae.getSource() == saveButton) {
                try {
                    Student student = null;
                    Faculty faculty = null;
                    // Holt sich den Studenten oder Faculty je nach Rückgabetyp.
                    if (isStudentReturn) {
                        student = br.studentController.getStudent(Integer.toString(personIdReturn));
                    } else {
                        faculty = br.facultyController.getFaculty(Integer.toString(personIdReturn));
                    }

                    // Führt die Rückgabe des Buches entweder für den Studenten oder Faculty aus.
                    if (isStudentReturn) {
                        br.studentController.returnBook(student, bookIssueArrayList);
                    } else {
                        br.facultyController.returnBook(faculty, bookIssueArrayList);
                    }

                    // Setzt den Status der Bücher auf "Available" und aktualisiert die Bücher.
                    for (Book book : bookIssueArrayList) {
                        book.setStatus("Available");
                        br.bookController.updateBookStatus(book);
                    }

                    // Aktualisiert die Ansicht und die Tabelle.
                    br.bookIssueTableView.fireTableDataChanged();
                    br.booksTable.repaint();
                    br.setAreaTypeTableRowHeight();
                    br.refreshAll();
                    // Macht den "returnButton" sichtbar und setzt Standardwerte zurück.
                    returnButton.setVisible(true);
                    setDefault();
                    br.searchBox.setEditable(true);
                    br.booksTable.clearSelection();
                    br.booksTable.setEnabled(true);
                    br.issueDetails.setDefaults(false);
                    // Zeigt eine Erfolgsmeldung an.
                    br.error.setText("Successfully Returned!!");
                    br.error.setForeground(Color.blue);
                    br.errorIcon.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Successfully Returned!!", "Notification",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    // Falls ein Fehler auftritt, wird eine Fehlermeldung angezeigt.
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    br.errorIcon.setVisible(true);
                    br.error.setText(e.getMessage());
                    br.error.setForeground(Color.red);
                }
            }
        }

        // Diese Methode wird aufgerufen, wenn sich der Zustand eines Items (z. B. Dropdown) ändert.
        @Override
        public void itemStateChanged(ItemEvent ie) {
            // Wenn das "qty"-Dropdown verändert wird, kann hier eine Aktion durchgeführt werden.
            if (ie.getSource() == qty) {
                // Keine spezifische Aktion hier.
            }
        }
   }
}

