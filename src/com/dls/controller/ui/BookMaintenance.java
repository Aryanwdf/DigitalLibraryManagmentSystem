
// Importieren von benötigten Klassen für die UI-Komponenten und Ereignisbehandlung
package com.dls.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

// Importieren von benutzerdefinierten Klassen aus dem Projekt
import com.dls.contstant.GlobalResources;
import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import com.dls.ui.tabledataview.BookTableView;

public class BookMaintenance extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

	// Deklaration der UI-Komponenten und Variablen
	private JButton addBook, delete, update;
	public static boolean isAdding = false;
	private JTable booksTable;
	private JScrollPane scrollPane;
	private BookTableView bookModel;
	private JTextField searchBox;
	private JLabel search, list, totalCount, close, heading, error, errorIcon;
	private Container c;
	private BookDetailPanel bookDetailsPanel;
	private LibraryMenu libraryMenu;
	private BookControllerInterface bookController;

	// Enum zur Definition der Bewegungsrichtungen (for und back)
	public enum Direction {
		FORWARD, BACKWARD
	};

	// Konstruktor der Klasse, der das Layout und die UI-Komponenten initialisiert
	public BookMaintenance(LibraryMenu libraryMenu, BookControllerInterface bookController) {
		// Setzen des Look and Feels der UI auf das des Systems
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.bookController = bookController;
		this.libraryMenu = libraryMenu;
		this.setUndecorated(false); // Deaktiviert die Fensterdekoration

		// Initialisieren und Hinzufügen eines "Schließen"-Icons
		close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
		this.addWindowListener(new WindowAdapter() {
			// Ereignisbehandlung beim Schließen des Fensters
			@Override
			public void windowClosing(WindowEvent me) {
				BookMaintenance.this.libraryMenu.setVisible(true);
				BookMaintenance.this.libraryMenu.setParentActive();
				BookMaintenance.this.dispose();
			}
		});

		// Setzen des Standard-Schließverhaltens
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		// Setzen des Titels und der Schriftart für das Haupt-Label
		heading = new JLabel("Digital Library Management System - Book Maintenance");
		heading.setFont(new Font("Times New Roman", Font.BOLD, 26));

		// Initialisieren des Details-Panel für Bücher
		bookDetailsPanel = new BookDetailPanel();

		// Layout-Manager des Fensters auf null setzen
		getContentPane().setLayout(null);
		c = this.getContentPane();
		// Hintergrundfarbe des Containers setzen
		c.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		
		// Initialisieren der anderen UI-Komponenten
		search = new JLabel("Search Book:");
		search.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/find_icon.png")));
		search.setFont(new Font("Times New Roman", Font.BOLD, 20));

		totalCount = new JLabel("");
		totalCount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		list = new JLabel("Total Book Count : ");
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchBox = new JTextField("");

		// Initialisieren der Buttons mit Icons und Schriftarten
		addBook = new JButton("  Add");
		addBook.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/add_icon.png")));
		addBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
		error = new JLabel("");
		errorIcon = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

		delete = new JButton("  Delete");
		delete.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/trash_icon.png")));
		delete.setFont(new Font("Times New Roman", Font.BOLD, 14));

		update = new JButton(" Update");
		update.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/edit_icon.png")));
		update.setFont(new Font("Times New Roman", Font.BOLD, 14));

		// Initialisieren des Tabellenmodells und der JTable
		bookModel = new BookTableView(bookController);
		booksTable = new JTable(bookModel);
		booksTable.setBackground(Color.white);
		// Setzen der Breite der einzelnen Spalten
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColumnName(2));
		this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColumnName(3));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		// Anpassung der Zeilenhöhe der Tabelle
		this.setAreaTypeTableRowHeight();
		this.scrollPane = new JScrollPane(booksTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		
		// Setzen der Position und der Größe der UI-Komponenten
		search.setBounds(10, 70, 175, 30);
		addBook.setBounds(600, 66, 115, 40);
		update.setBounds(743, 66, 115, 40);
		delete.setBounds(883, 66, 115, 40);
		searchBox.setBounds(195, 70, 354, 30);
		list.setBounds(10, 129, 196, 30);
		totalCount.setBounds(195, 131, 100, 30);
		scrollPane.setBounds(10, 170, 1000, 220);
		bookDetailsPanel.setBounds(10, 400, 1000, 200);
		errorIcon.setBounds(10, 610, 32, 32);
		error.setBounds(40, 610, 800, 32);
		error.setForeground(Color.red);
		error.setFont(new Font("Times New Roman", Font.BOLD, 15));
		close.setBounds(980, 3, 32, 32);
		heading.setBounds(185, 11, 715, 40);

		// Hinzufügen der UI-Komponenten zum Container
		c.add(list);
		c.add(search);
		c.add(searchBox);
		c.add(totalCount);
		c.add(this.scrollPane);
		c.add(addBook);
		c.add(delete);
		c.add(update);
		c.add(bookDetailsPanel);
		c.add(heading);
		errorIcon.setVisible(false);

		// Fenstergröße und Position festlegen
		this.setSize(1020, 640);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);

		// Fenstertitel und Icon festlegen
		this.setTitle("Books Maintenance - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());

		// Listener hinzufügen
		this.addListeners();
		try {
			updateBookCount(); // Buchanzahl aktualisieren
		} catch (CustomException ex) {
		}

		// Fenster sichtbar machen
		this.setVisible(true);
	}

	// Methode zum Hinzufügen von ActionListenern zu den UI-Komponenten
	public void addListeners() {
		addBook.addActionListener(this);
		delete.addActionListener(this);
		update.addActionListener(this);
		booksTable.getSelectionModel().addListSelectionListener(this);
		searchBox.getDocument().addDocumentListener(this);
	}

	// Methode zum Neuladen der Tabelle
	public void repaintTable() {
		bookModel = new BookTableView(bookController);
		booksTable.setModel(bookModel);
		setAreaTypeTableRowHeight();
		booksTable.repaint();
		booksTable.setBackground(Color.white);
		// Spaltenbreiten und Schriftart für die Tabelle setzen
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColumnName(2));
		this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColumnName(3));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.setAreaTypeTableRowHeight();
		// Buchanzahl aktualisieren
		try {
			this.updateBookCount();
		} catch (CustomException ex) {
		}

	}

	// Methode zum Setzen der Zeilenhöhe der Tabelle
	private void setAreaTypeTableRowHeight() {
		for (int x = 0; x < this.booksTable.getRowCount(); x++) {
			this.booksTable.setRowHeight(x, 25);
		}
	}




// Till here
    @Override 
    public void actionPerformed(ActionEvent ae) {
        // Bestimmt das Event-Objekt, das die Aktion ausgelöst hat
        Object o = ae.getSource();

        // Wenn der Benutzer auf "addBook" geklickt hat
        if (o == addBook) {
            try {
                // Löscht aktuelle Auswahl in der Tabelle und setzt das Formular zurück
                booksTable.clearSelection();
                bookDetailsPanel.clear();
                isAdding = true;  // Setzt den Status auf "Hinzufügen"
                bookDetailsPanel.setDefaults(true);  // Setzt die Standardwerte im Details-Panel
                bookDetailsPanel.editFields();  // Aktiviert die Bearbeitung der Felder
                this.setState(false);  // Deaktiviert das Interface für die Bearbeitung
                bookDetailsPanel.idBox.setText("");  // Leert das ID-Feld
                bookDetailsPanel.idBox.setEditable(false);  // ID-Feld nicht bearbeitbar machen
                bookDetailsPanel.idBox.setBackground(new Color(GlobalResources.COLOR_CODE_R,
                        GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));  // Setzt den Hintergrund des ID-Felds
                Date date = new Date();  // Holt das aktuelle Datum
                bookDetailsPanel.entryDateBox.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));  // Setzt das Eingabedatum
                bookDetailsPanel.yearBox.setText("");  // Leert das Jahr-Feld
            } catch (Exception e) {
                // Zeigt eine Fehlermeldung, wenn ein Fehler auftritt
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Wenn der Benutzer auf "update" geklickt hat
        if (o == update) {
            int selectedRowIndex = this.booksTable.getSelectedRow();
            // Wenn keine Zeile ausgewählt wurde, wird eine Fehlermeldung angezeigt
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                isAdding = false;  // Setzt den Status auf "Bearbeiten"
                bookDetailsPanel.setDefaults(true);  // Setzt die Standardwerte im Details-Panel
                bookDetailsPanel.editFields();  // Aktiviert die Bearbeitung der Felder
                this.setState(false);  // Deaktiviert das Interface für die Bearbeitung
                bookDetailsPanel.idBox.setEditable(false);  // ID-Feld nicht bearbeitbar machen
                bookDetailsPanel.idBox.setBackground(new Color(GlobalResources.COLOR_CODE_R,
                        GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));  // Setzt den Hintergrund des ID-Felds
            } catch (Exception e) {
                // Zeigt eine Fehlermeldung, wenn ein Fehler auftritt
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Wenn der Benutzer auf "delete" geklickt hat
        if (o == delete) {
            int selectedRowIndex = this.booksTable.getSelectedRow();
            // Wenn keine Zeile ausgewählt wurde, wird eine Fehlermeldung angezeigt
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Select an Entry to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Book book = null;
            try {
                // Holt das Buch basierend auf der ID aus der Tabelle
                int id = Integer.parseInt((String) bookModel.getValueAt(selectedRowIndex, 0));
                book = bookController.getBook(id);
            } catch (CustomException CustomException) {
                // Dieser Fehler wird nicht erwartet
            }

            // Bestätigungsdialog für das Löschen des Buches
            int selection = JOptionPane.showConfirmDialog(this,
                    "Delete : \n ID = " + book.getId() + "\nName=" + book.getName(), "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            // Wenn der Benutzer bestätigt, wird das Buch gelöscht
            if (selection == JOptionPane.YES_OPTION) {
                try {
                    this.bookController.deleteBook(book);  // Löscht das Buch
                    this.bookModel.fireTableDataChanged();  // Aktualisiert die Tabelle
                    this.setAreaTypeTableRowHeight();  // Passt die Zeilenhöhe an
                    this.booksTable.repaint();  // Repaints die Tabelle
                    errorIcon.setVisible(false);  // Versteckt das Fehler-Icon
                    error.setText("book deleted!!");  // Setzt den Fehlertext
                    error.setForeground(Color.blue);  // Setzt die Textfarbe auf Blau
                    updateBookCount();  // Aktualisiert die Anzahl der Bücher
                    this.repaintTable();  // Repaints die Tabelle
                    bookDetailsPanel.repaint();  // Repaints das Details-Panel
                    bookDetailsPanel.clear();  // Leert das Details-Panel
                    searchBox.setText("");  // Leert das Suchfeld
                } catch (CustomException e) {
                    // Zeigt eine Fehlermeldung, wenn ein Fehler beim Löschen auftritt
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Methode für das Einfügen von Text im Dokument (Suchfunktion)
    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        try {
            // Wird aufgerufen, wenn Text eingefügt wird
            this.search(this.searchBox.getText().trim(), 0, Direction.FORWARD, false);
        } catch (CustomException ex) {
        }
    }

    // Methode für das Entfernen von Text im Dokument (Suchfunktion)
    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        try {
            // Wird aufgerufen, wenn Text entfernt wird
            this.search(this.searchBox.getText().trim(), 0, Direction.BACKWARD, false);
        } catch (CustomException ex) {
        }
    }

    // Methode für geänderte Attribute im Dokument (hier nicht verwendet)
    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        // This method is there due to Interface
    }

    // Methode zum Setzen des Zustands der Schaltflächen und der Tabelle
    public void setState(boolean b) {
        addBook.setEnabled(b);  // Aktiviert/deaktiviert die "Hinzufügen"-Schaltfläche
        update.setEnabled(b);  // Aktiviert/deaktiviert die "Aktualisieren"-Schaltfläche
        delete.setEnabled(b);  // Aktiviert/deaktiviert die "Löschen"-Schaltfläche
        searchBox.setEnabled(b);  // Aktiviert/deaktiviert das Suchfeld
        searchBox.setText("");  // Leert das Suchfeld
        booksTable.setEnabled(b);  // Aktiviert/deaktiviert die Tabelle
        error.setText("");  // Leert den Fehlertext
        errorIcon.setVisible(false);  // Versteckt das Fehler-Icon
    }

    // Methode zur Aktualisierung der Buchanzahl
    private void updateBookCount() throws CustomException {
        this.totalCount.setText(String.valueOf(bookModel.getRowCount()));  // Setzt die Textanzeige der Gesamtzahl der Bücher
    }

    // Methode für Änderungen an der Auswahl der Tabelle (z. B. beim Klicken)
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (bookDetailsPanel == null) {
            return;
        }
        int selectedRowIndex = this.booksTable.getSelectedRow();
        // Wenn keine gültige Zeile ausgewählt wurde, setzt es die Standardwerte
        if (selectedRowIndex < 0 || selectedRowIndex >= this.booksTable.getRowCount()) {
            bookDetailsPanel.setDefaults(false);
        } else {
            try {
                // Holt die Details des Buches basierend auf der ID
                Book book = bookController
                        .getBook(Integer.parseInt((String) bookModel.getValueAt(selectedRowIndex, 0)));
                bookDetailsPanel.setBookDetails(book);  // Zeigt die Buchdetails an
            } catch (CustomException CustomException) {
                // Setzt die Standardwerte im Falle eines Fehlers
                bookDetailsPanel.setDefaults(false);
            }
        }
    }

    // Einfacher Suchalgorithmus, der nach einem bestimmten ID-Wert sucht
    public void search(String id) throws CustomException {
        if (id.length() == 0) {
            this.booksTable.clearSelection();  // Keine Auswahl, wenn die ID leer ist
            return;
        }

        for (int x = 0; x < bookModel.getRowCount(); x++) {
            if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase().startsWith(id.toUpperCase())) {
                // Setzt die Auswahl auf die erste Übereinstimmung
                this.booksTable.setRowSelectionInterval(x, x);
                this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));  // Scrollt zur ausgewählten Zeile
                return;
            }
        }
        this.booksTable.clearSelection();  // Wenn keine Übereinstimmung, wird die Auswahl gelöscht
    }

    // Erweiterte Suchmethode mit Richtung und Auswahlstatus
    private void search(String electronicUnitName, int fromIndex, Direction direction, boolean isSelected)
            throws CustomException {
        if (electronicUnitName.length() == 0) {
            this.booksTable.clearSelection();  // Keine Auswahl, wenn der Name leer ist
            return;
        }

        if (booksTable.getRowCount() == 0) {
            return;
        }

        if (direction == Direction.FORWARD) {
            // Durchläuft die Tabelle vorwärts
            for (int x = fromIndex; x < bookModel.getRowCount(); x++) {
                if (((String) this.bookModel.getValueAt(x, 0)).toUpperCase()
                        .contains(electronicUnitName.toUpperCase())) {
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    return;
                }
                else if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 2)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				}
            }
        }

        if (direction == Direction.BACKWARD) {
            // Durchläuft die Tabelle rückwärts
            for (int x = fromIndex; x >= 0; x--) {
                if (((String) this.bookModel.getValueAt(x, 0)).toUpperCase()
                        .contains(electronicUnitName.toUpperCase())) {
                    this.booksTable.setRowSelectionInterval(x, x);
                    this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
                    return;
                }
                else if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 2)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				}
            }
        }

        if (!isSelected) {
            this.booksTable.clearSelection();  // Löscht die Auswahl, wenn nicht selektiert
        }
    }

// Till here 
 // Die Klasse `BookDetailPanel` erweitert die JPanel-Klasse und implementiert das ActionListener-Interface.
 // Sie repräsentiert ein benutzerdefiniertes Panel für die Eingabe und Anzeige von Buchdetails.
 class BookDetailPanel extends JPanel implements ActionListener {

     // Deklaration der JLabel-Instanzen für die Anzeige von Labels für Buchattribute.
     JLabel name, publisher, isbn, price, author, pages, remark, status, entryDate, id, year;

     // Deklaration der JTextField-Instanzen für die Eingabe von Buchattributen.
     JTextField nameBox, publisherBox, isbnBox, idBox, remarkBox, statusBox, entryDateBox, pagesBox, authorBox, priceBox, yearBox;

     // Deklaration der JButton-Instanzen für die Schaltflächen "Speichern" und "Schließen".
     JButton save, close;

     // Deklaration der JLabel-Instanzen für Fehlermeldungsanzeigen.
     JLabel nameEr, publisherEr, isbnEr, idEr, remarkEr, statusEr, entryDateEr, pagesEr, authorEr, priceEr, yearEr;

     // Referenz auf die BookMaintenance-Klasse, vermutlich für den Zugriff auf die Hauptlogik.
     private final BookMaintenance bookMaintenance;


     // Konstruktor für das BookDetailPanel, in dem die Benutzeroberfläche eingerichtet wird.
     BookDetailPanel() {
         // Layout-Manager wird deaktiviert, um manuelle Platzierung von Komponenten zu ermöglichen.
         this.setLayout(null);

         // Initialisierung des bookMaintenance-Feldes mit der äußeren BookMaintenance-Klasse.
         bookMaintenance = BookMaintenance.this;

         // Initialisierung der Fehler-Icons für die Validierung.
         nameEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         isbnEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         publisherEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         priceEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         authorEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         remarkEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         pagesEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         entryDateEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         idEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         statusEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
         yearEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

         // Initialisierung der Schaltflächen.
         save = new JButton("Save");
         close = new JButton("Close");
         
         // Initialisierung und Stilsetzung der JLabel-Komponenten für Buchattribute.
			name = new JLabel("Name:");
			name.setFont(new Font("Times New Roman", Font.BOLD, 15));
			year = new JLabel("Year:");
			year.setFont(new Font("Times New Roman", Font.BOLD, 15));
			isbn = new JLabel("ISBN:");
			isbn.setFont(new Font("Times New Roman", Font.BOLD, 15));
			publisher = new JLabel("Publisher:");
			publisher.setFont(new Font("Times New Roman", Font.BOLD, 15));
			price = new JLabel("Price:");
			price.setFont(new Font("Times New Roman", Font.BOLD, 15));
			author = new JLabel("Author:");
			author.setFont(new Font("Times New Roman", Font.BOLD, 15));
			remark = new JLabel("Remark:");
			remark.setFont(new Font("Times New Roman", Font.BOLD, 15));
			pages = new JLabel("Pages:");
			pages.setFont(new Font("Times New Roman", Font.BOLD, 15));
			entryDate = new JLabel("Entry Date:");
			entryDate.setFont(new Font("Times New Roman", Font.BOLD, 15));
			id = new JLabel("ID:");
			id.setFont(new Font("Times New Roman", Font.BOLD, 15));
			status = new JLabel("Status:");
			status.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
		 // Initialisierung der JTextField-Komponenten und Stilsetzung.
			nameBox = new JTextField("");
			nameBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			publisherBox = new JTextField("");
			publisherBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			idBox = new JTextField("");
			idBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			idBox.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
			yearBox = new JTextField("");
			yearBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			isbnBox = new JTextField("");
			isbnBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			remarkBox = new JTextField("");
			remarkBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			statusBox = new JTextField("");
			statusBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			entryDateBox = new JTextField("");
			entryDateBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			pagesBox = new JTextField("");
			pagesBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			authorBox = new JTextField("");
			authorBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			priceBox = new JTextField("");
			authorBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
		 // Positionierung der Komponenten auf dem Panel.
			name.setBounds(10, 20, 70, 30);
			nameBox.setBounds(90, 20, 500, 30);
			nameEr.setBounds(590, 20, 30, 30);
			id.setBounds(10, 60, 70, 30);
			idBox.setBounds(90, 60, 100, 30);
			idEr.setBounds(190, 60, 30, 30);
			pages.setBounds(250, 60, 70, 30);
			pagesBox.setBounds(320, 60, 100, 30);
			pagesEr.setBounds(425, 60, 30, 30);
			isbn.setBounds(450, 60, 70, 30);
			isbnBox.setBounds(520, 60, 120, 30);
			isbnEr.setBounds(640, 60, 30, 30);
			publisher.setBounds(10, 100, 70, 30);
			publisherBox.setBounds(90, 100, 300, 30);
			publisherEr.setBounds(390, 100, 30, 30);
			status.setBounds(10, 140, 70, 30);
			statusBox.setBounds(90, 140, 100, 30);
			statusEr.setBounds(190, 140, 30, 30);
			entryDate.setBounds(230, 140, 100, 30);
			entryDateBox.setBounds(320, 140, 100, 30);
			entryDateEr.setBounds(420, 140, 30, 30);
			remark.setBounds(500, 140, 70, 30);
			remarkBox.setBounds(570, 140, 100, 30);
			remarkEr.setBounds(670, 140, 30, 30);
			year.setBounds(750, 140, 70, 30);
			yearBox.setBounds(820, 140, 100, 30);
			yearEr.setBounds(920, 140, 30, 30);
			author.setBounds(450, 100, 70, 30);
			authorBox.setBounds(520, 100, 300, 30);
			authorEr.setBounds(720, 100, 30, 30);
			price.setBounds(660, 20, 70, 30);
			priceBox.setBounds(740, 20, 100, 30);
			priceEr.setBounds(840, 20, 30, 30);
			save.setBounds(870, 20, 115, 40);
			close.setBounds(870, 70, 115, 40);
			
	   // Schaltflächen-Eigenschaften setzen.
			save.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
			save.setFont(new Font("Times New Roman", Font.BOLD, 14));
			close.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/close_icon.png")));
			close.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
	   // Hinzufügen der Komponenten zum Panel.
			this.add(save);
			this.add(name);
			this.add(nameBox);
			this.add(price);
			this.add(priceBox);
			this.add(author);
			this.add(authorBox);
			this.add(publisher);
			this.add(publisherBox);
			this.add(remark);
			this.add(remarkBox);
			this.add(isbn);
			this.add(isbnBox);
			this.add(pages);
			this.add(pagesBox);
			this.add(entryDate);
			this.add(entryDateBox);
			this.add(status);
			this.add(statusBox);
			this.add(id);
			this.add(idBox);
			this.add(nameEr);
			this.add(priceEr);
			this.add(authorEr);
			this.add(publisherEr);
			this.add(remarkEr);
			this.add(isbnEr);
			this.add(pagesEr);
			this.add(entryDateEr);
			this.add(statusEr);
			this.add(idEr);
			this.add(year);
			this.add(yearBox);
			this.add(yearEr);
			this.add(close);
			
      // Hinzufügen von ActionListener zu den Schaltflächen.
			this.save.addActionListener(this);
			close.addActionListener(this);
			
			// Setzt den Hintergrund der Komponente auf eine benutzerdefinierte Farbe (dargestellt durch die GlobalResources-Konstanten)
			this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			// Setzt die Sichtbarkeit der Komponente auf true (zeigt das Fenster)
			this.setVisible(true);
			// Ruft die Methode setDefaults auf, um die Standardeinstellungen zu setzen (mit false als Parameter)
			setDefaults(false);
			// Erstellt einen TitledBorder mit einem EtchedBorder und dem Titel "Book Details"
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Book Details");
			// Setzt die Schriftart des Titels im Border auf "Times New Roman", fett und in der Größe 18
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 18));
			// Setzt den Border der aktuellen Komponente auf den TitledBorder
			this.setBorder(tb);
		}

     void setDefaults(boolean value) {
    		// Setzt die Bearbeitbarkeit der Textfelder auf den angegebenen Wert
    		this.nameBox.setEditable(value);
    		// Setzt den Hintergrund der Textfelder auf eine graue Farbe
    		this.nameBox.setBackground(new Color(240, 240, 240));
    		idBox.setEditable(value);
    		publisherBox.setEditable(value);
    		priceBox.setEditable(value);
    		isbnBox.setEditable(value);
    		authorBox.setEditable(value);
    		pagesBox.setEditable(value);
    		entryDateBox.setEditable(value);
    		remarkBox.setEditable(value);
    		statusBox.setEditable(value);
    		yearBox.setEditable(value);
    		// Steuert die Sichtbarkeit der 'Speichern'- und 'Schließen'-Schaltflächen
    		save.setVisible(value);
    		close.setVisible(value);
    		// Setzt den Hintergrund der Textfelder auf eine graue Farbe
    		idBox.setBackground(new Color(240, 240, 240));
    		publisherBox.setBackground(new Color(240, 240, 240));
    		priceBox.setBackground(new Color(240, 240, 240));
    		isbnBox.setBackground(new Color(240, 240, 240));
    		pagesBox.setBackground(new Color(240, 240, 240));
    		authorBox.setBackground(new Color(240, 240, 240));
    		entryDateBox.setBackground(new Color(240, 240, 240));
    		remarkBox.setBackground(new Color(240, 240, 240));
    		statusBox.setBackground(new Color(240, 240, 240));
    		yearBox.setBackground(new Color(240, 240, 240));
    		// Setzt alle Fehleranzeigen auf unsichtbar
    		idEr.setVisible(false);
    		nameEr.setVisible(false);
    		publisherEr.setVisible(false);
    		priceEr.setVisible(false);
    		pagesEr.setVisible(false);
    		isbnEr.setVisible(false);
    		remarkEr.setVisible(false);
    		statusEr.setVisible(false);
    		entryDateEr.setVisible(false);
    		authorEr.setVisible(false);
    		yearEr.setVisible(false);
    	}

  // Methode, die die Eingabewerte überprüft und das Buch aktualisiert, wenn keine Fehler vorliegen
     public void setUpdateBookEntry() {
         int flag = 0; // Initialisiert eine Fehler-Flag-Variable

         // Überprüft, ob das Textfeld 'nameBox' leer ist und zeigt eine Fehlermeldung, wenn ja
         if (nameBox.getText().trim().equals("")) {
             flag++;
             nameEr.setVisible(true);
         } else {
             nameEr.setVisible(false);
         }

         // Wiederholt diesen Überprüfungs- und Sichtbarkeitsprozess für alle anderen Felder
         if (publisherBox.getText().trim().equals("")) {
             flag++;
             publisherEr.setVisible(true);
         } else {
             publisherEr.setVisible(false);
         }
         if (isbnBox.getText().trim().equals("")) {
             flag++;
             isbnEr.setVisible(true);
         } else {
             isbnEr.setVisible(false);
         }
         if (statusBox.getText().trim().equals("")) {
             flag++;
             statusEr.setVisible(true);
         } else {
             statusEr.setVisible(false);
         }
         if (remarkBox.getText().trim().equals("")) {
             flag++;
             remarkEr.setVisible(true);
         } else {
             remarkEr.setVisible(false);
         }
         if (entryDateBox.getText().trim().equals("")) {
             flag++;
             entryDateEr.setVisible(true);
         } else {
             entryDateEr.setVisible(false);
         }
         if (authorBox.getText().trim().equals("")) {
             flag++;
             authorEr.setVisible(true);
         } else {
             authorEr.setVisible(false);
         }

         if (yearBox.getText().trim().equals("")) {
             flag++;
             yearEr.setVisible(true);
         } else {
             yearEr.setVisible(false);
         }
         if (priceBox.getText().trim().equals("")) {
             flag++;
             priceEr.setVisible(true);
         } else {
             priceEr.setVisible(false);
         }

         if (pagesBox.getText().trim().equals("")) {
             flag++;
             pagesEr.setVisible(true);
         } else {
             pagesEr.setVisible(false);
         }

         // Wenn keine Fehler vorliegen, wird das Buch aktualisiert
         if (flag == 0) {
             try {
                 Book book = new Book();
                 // Setzt die Buchdaten mit den Werten aus den Textfeldern
                 book.setName(nameBox.getText().trim());
                 book.setId(Integer.parseInt(idBox.getText().trim()));
                 book.setYear(Integer.parseInt((yearBox.getText().trim())));
                 book.setPrice(Double.parseDouble((priceBox.getText().trim())));
                 book.setTotalPages(Integer.parseInt(pagesBox.getText().trim()));
                 book.setPublisher(publisherBox.getText().trim());
                 book.setISBN(isbnBox.getText().trim());
                 book.setEntryDate(new SimpleDateFormat("YYYY-MM-DD").parse(entryDateBox.getText().trim()));
                 book.setAuthor(authorBox.getText().trim());
                 book.setRemark(remarkBox.getText().trim());
                 book.setStatus(statusBox.getText().trim());
                 // Ruft die Controller-Methode auf, um das Buch zu aktualisieren
                 bookController.updateBook(book);
                 // Aktualisiert die Buchliste und UI
                 bookMaintenance.bookModel.fireTableDataChanged();
                 bookMaintenance.setAreaTypeTableRowHeight();
                 bookMaintenance.booksTable.repaint();
                 bookMaintenance.updateBookCount();
                 bookMaintenance.repaintTable();
                 this.repaint();
                 // Führt eine Suche nach der ID des Buches aus
                 bookMaintenance.search(idBox.getText().trim(), 0, Direction.FORWARD, false);
                 // Setzt eine Erfolgsmeldung
                 bookMaintenance.error.setText("Book updated!!");
                 bookMaintenance.error.setForeground(Color.blue);
                 bookMaintenance.errorIcon.setVisible(false);
                 bookMaintenance.setState(true);
                 JOptionPane.showMessageDialog(this, "Successfully Book Details Updated!!", "Notification",
                         JOptionPane.INFORMATION_MESSAGE);
             } catch (Exception e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 // Zeigt ein Fehler-Icon und Nachricht an
                 bookMaintenance.errorIcon.setVisible(true);
                 bookMaintenance.error.setText(e.getMessage());
                 bookMaintenance.error.setForeground(Color.red);
             }
         }
     }


  // Methode, die einen neuen Bucheintrag setzt
     public void setNewBookEntry() {
         // Flag zum Überprüfen der Gültigkeit der Felder, anfänglich auf 0 gesetzt
         int flag = 0;
         
         // Überprüfung des Namensfeldes auf leere Eingabe
         if (nameBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             nameEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             nameEr.setVisible(false);  // Fehlermeldung ausblenden
         } 

         // Überprüfung des Verlagsfeldes auf leere Eingabe
         if (publisherBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             publisherEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             publisherEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des ISBN-Feldes auf leere Eingabe
         if (isbnBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             isbnEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             isbnEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Statusfeldes auf leere Eingabe
         if (statusBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             statusEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             statusEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Bemerkungsfeldes auf leere Eingabe
         if (remarkBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             remarkEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             remarkEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Eingabedatums auf leere Eingabe
         if (entryDateBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             entryDateEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             entryDateEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Autorenfeldes auf leere Eingabe
         if (authorBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             authorEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             authorEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Jahresfeldes auf leere Eingabe
         if (yearBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             yearEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             yearEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Preisfeldes auf leere Eingabe
         if (priceBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             priceEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             priceEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Überprüfung des Seitenzahlfeldes auf leere Eingabe
         if (pagesBox.getText().trim().equals("")) {
             flag++;  // Flag erhöhen, wenn das Feld leer ist
             pagesEr.setVisible(true);  // Fehlermeldung anzeigen
         } else {
             pagesEr.setVisible(false);  // Fehlermeldung ausblenden
         }

         // Wenn alle Felder gültig sind (flag == 0)
         if (flag == 0) {
             try {
                 // Ein neues Buchobjekt erstellen und die Eingabewerte setzen
                 Book book = new Book();
                 book.setName(nameBox.getText().trim());
                 book.setYear(Integer.parseInt(yearBox.getText().trim()));
                 book.setPrice(Double.parseDouble(priceBox.getText().trim()));
                 book.setTotalPages(Integer.parseInt(pagesBox.getText().trim()));
                 book.setPublisher(publisherBox.getText().trim());
                 book.setISBN(isbnBox.getText().trim());
                 book.setEntryDate(new SimpleDateFormat("YYYY-MM-DD").parse(entryDateBox.getText().trim()));
                 book.setAuthor(authorBox.getText().trim());
                 book.setRemark(remarkBox.getText().trim());
                 book.setStatus(statusBox.getText().trim());

                 // Das Buch zur Buchverwaltung hinzufügen
                 bookMaintenance.bookController.addBook(book);
                 bookMaintenance.bookModel.fireTableDataChanged();  // Tabelle neu laden
                 bookMaintenance.setAreaTypeTableRowHeight();  // Zeilenhöhe setzen
                 bookMaintenance.booksTable.repaint();  // Tabelle neu zeichnen
                 bookMaintenance.updateBookCount();  // Buchanzahl aktualisieren
                 bookMaintenance.repaintTable();  // Tabelle neu zeichnen
                 this.repaint();  // Aktuelles Fenster neu zeichnen
                 bookMaintenance.search(idBox.getText().trim(), 0, Direction.FORWARD, false);  // Buchsuche starten
                 bookMaintenance.error.setText("Book Added!!");  // Erfolgsmeldung anzeigen
                 bookMaintenance.error.setForeground(Color.blue);  // Textfarbe setzen
                 bookMaintenance.errorIcon.setVisible(false);  // Fehlersymbol ausblenden
                 clear();  // Felder leeren
                 bookMaintenance.setState(true);  // Zustand setzen
                 // Erfolgreiche Meldung anzeigen
                 JOptionPane.showMessageDialog(this, "Successfully New Book Added!!", "Notification", JOptionPane.INFORMATION_MESSAGE);
             } catch (Exception e) {
                 // Fehlerbehandlung bei ungültigen Eingaben
                 JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 bookMaintenance.errorIcon.setVisible(true);  // Fehlersymbol anzeigen
                 bookMaintenance.error.setText(e.getMessage());  // Fehlermeldung anzeigen
                 bookMaintenance.error.setForeground(Color.red);  // Textfarbe auf rot setzen
             }
         }
     }

     // Methode zum Verarbeiten von ActionEvents
     @Override
     public void actionPerformed(ActionEvent ae) {
         Object o = ae.getSource();
         // Wenn der "Save"-Button geklickt wird
         if (o == save) {
             editFields();  // Felder zum Bearbeiten aktivieren
             // Wenn ein neues Buch hinzugefügt wird, setze den neuen Bucheintrag
             if (bookMaintenance.isAdding) {
                 setNewBookEntry();
             } else {
                 setUpdateBookEntry();  // Andernfalls den Bucheintrag aktualisieren
             }
         }

         // Wenn der "Close"-Button geklickt wird
         if (o == close) {
             setDefaults(false);  // Standardwerte zurücksetzen
             clear();  // Felder leeren
             booksTable.clearSelection();  // Auswahl in der Tabelle löschen
             bookMaintenance.setState(true);  // Zustand setzen
         }
     }

     // Methode zum Leeren der Eingabefelder
     public void clear() {
         nameBox.setText("");
         publisherBox.setText("");
         yearBox.setText("");
         priceBox.setText("");
         isbnBox.setText("");
         pagesBox.setText("");
         statusBox.setText("");
         remarkBox.setText("");
         authorBox.setText("");
         entryDateBox.setText("");
         idBox.setText("");
     }

     // Methode zum Zurücksetzen der Hintergrundfarbe der Eingabefelder
     public void editFields() {
         this.nameBox.setBackground(Color.white);
         publisherBox.setBackground(Color.white);
         priceBox.setBackground(Color.white);
         isbnBox.setBackground(Color.white);
         pagesBox.setBackground(Color.white);
         authorBox.setBackground(Color.white);
         entryDateBox.setBackground(Color.white);
         remarkBox.setBackground(Color.white);
         statusBox.setBackground(Color.white);
         yearBox.setBackground(Color.white);
     }

     // Methode zum Setzen der Buchdetails in die Eingabefelder
     public void setBookDetails(Book book) {
         idBox.setText(Integer.toString(book.getId()));
         nameBox.setText(book.getName());
         publisherBox.setText(book.getPublisher());
         isbnBox.setText(book.getISBN());
         statusBox.setText(book.getStatus());
         remarkBox.setText(book.getRemark());
         authorBox.setText(book.getAuthor());
         priceBox.setText(Double.toString(book.getPrice()));
         pagesBox.setText(String.valueOf(book.getTotalPages()));
         entryDateBox.setText(book.getEntryDateStr());
         yearBox.setText(String.valueOf(book.getYear()));
     }


	}

}
