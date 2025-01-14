package com.dls.ui;

// Importieren von Java-Standardklassen für GUI-Elemente, Farben, Dimensionen, etc.
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

// Importieren benutzerdefinierter Schnittstellen und Ressourcen
import com.dls.contstant.GlobalResources;
import com.dls.interfaces.AdminControllerInterface;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;

// Die Klasse LibraryMenu erbt von JFrame und implementiert ActionListener
public class LibraryMenu extends JFrame implements ActionListener {

    // Deklaration von Schaltflächen und anderen GUI-Komponenten
    public JButton books, issueBook, returnBook, person, settings, about;
    public JLabel heading;
    public Container c;

    // Deklaration von Schnittstellen, die Steuerungsfunktionen bereitstellen
    private AdminControllerInterface adminController;
    private BookControllerInterface bookController;
    private StudentControllerInterface studentController;
    private FacultyControllerInterface facultyController;

    // Boolean zur Steuerung, ob ein untergeordnetes Fenster geöffnet ist
    private boolean isChildFrameOpened;

    // Konstruktor: Initialisierung der GUI und ihrer Komponenten
    public LibraryMenu(AdminControllerInterface adminController, BookControllerInterface bookController,
                       StudentControllerInterface studentController, FacultyControllerInterface facultyController) {
        // Initialisieren der Schnittstellen
        this.adminController = adminController;
        this.bookController = bookController;
        this.studentController = studentController;
        this.facultyController = facultyController;

        // Erstellen und Anpassen der Überschrift
        heading = new JLabel(GlobalResources.APPLICATION_NAME);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        heading.setBackground(
                new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));

        // Layout des Inhaltsbereichs auf Null setzen (manuelle Positionierung)
        getContentPane().setLayout(null);

        // Erstellen und Anpassen von Schaltflächen mit Symbolen und Texten
        books = new JButton("Books");
        books.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/Book1.png")));
        books.setFont(new Font("Times New Roman", Font.BOLD, 20));

        issueBook = new JButton("Book Issue");
        issueBook.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/issue_book.png")));
        issueBook.setFont(new Font("Times New Roman", Font.BOLD, 20));

        returnBook = new JButton("Book Return");
        returnBook.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/book_return.png")));
        returnBook.setFont(new Font("Times New Roman", Font.BOLD, 20));

        person = new JButton("Persons");
        person.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/users.png")));
        person.setFont(new Font("Times New Roman", Font.BOLD, 20));

        settings = new JButton("Settings");
        settings.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/settings.png")));
        settings.setFont(new Font("Times New Roman", Font.BOLD, 20));

        about = new JButton("Support");
        about.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/support.png")));
        about.setFont(new Font("Times New Roman", Font.BOLD, 20));

        // Holen des Inhaltsbereichs und Hinzufügen der Komponenten
        c = getContentPane();
        heading.setBounds(97, 0, 400, 60);
        books.setBounds(0, 60, 200, 200);
        issueBook.setBounds(200, 60, 200, 200);
        returnBook.setBounds(400, 60, 200, 200);
        person.setBounds(0, 260, 200, 200);
        settings.setBounds(200, 260, 200, 200);
        about.setBounds(400, 260, 200, 200);
        c.add(settings);
        c.add(about);
        c.add(books);
        c.add(issueBook);
        c.add(returnBook);
        c.add(person);
        c.add(heading);

        // Hintergrundfarbe des Inhaltsbereichs setzen
        c.setBackground(new Color(192, 192, 192));

        // Hinzufügen von Logos
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/book3.png")));
        lblNewLabel.setBounds(20, 5, 60, 50);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(LibraryMenu.class.getResource("/resources/book4.png")));
        lblNewLabel_1.setBounds(495, 5, 60, 50);
        getContentPane().add(lblNewLabel_1);

        // Hinzufügen von Listenern zu den Schaltflächen
        this.addListeners();

        // Konfigurieren des Fensters
        this.setSize(605, 488);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle(GlobalResources.APPLICATION_NAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());

        // Anwenden des Systems-Designs für die Benutzeroberfläche
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            
        }
    }

    // Methode zum Hinzufügen von ActionListenern zu den Schaltflächen
    public void addListeners() {
        books.addActionListener(this);
        issueBook.addActionListener(this);
        returnBook.addActionListener(this);
        person.addActionListener(this);
        settings.addActionListener(this);
        about.addActionListener(this);
    }

    // Aktivieren des Elternfensters (deaktiviert durch untergeordnete Fenster)
    public void setParentActive() {
        isChildFrameOpened = false;
    }
// Hauptlogik der Aktionen bei dem Homebildschirm
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object o = ae.getSource();

        // Verhindern mehrfacher untergeordneter Fenster
        if (!isChildFrameOpened) {
            isChildFrameOpened = true;

            // Bedingte Aktionen je nach Quelle des Ereignisses
            if (o == books) {
                new BookMaintenance(this, bookController);
            } else if (o == issueBook) {
                new BookIssue(this, bookController, studentController, facultyController);
            } else if (o == returnBook) {
                new BookReturn(this, bookController, studentController, facultyController);
            } else if (o == person) {
                new PersonMaintenance(this, studentController, facultyController);
            } else if (o == settings) {
                new Setting(this, adminController);
            } else if (o == about) {
                // Zeigen einer Dialogbox mit Support-Informationen
                JOptionPane.showMessageDialog(this,
                        "Developer - Aryan Arora \n Contact - +491793713742 \n E-mail - aryanarorawdf@gmail.com",
                        "Help & Support", JOptionPane.INFORMATION_MESSAGE);
                isChildFrameOpened = false; // Untergeordnetes Fenster wird nicht blockiert
            }
        }
    }

    
}
