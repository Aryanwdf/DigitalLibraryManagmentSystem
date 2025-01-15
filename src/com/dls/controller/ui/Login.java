// Package-Deklaration: Gibt an, dass sich diese Klasse im Package "com.dls.ui" befindet.
package com.dls.ui;

// Importieren von Java-Bibliotheken für GUI-Komponenten, Layout und Events.
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

// Importieren von benutzerdefinierten Klassen und Schnittstellen aus dem Projekt.
import com.dls.contstant.GlobalResources;
import com.dls.entity.Admin;
import com.dls.exception.CustomException;
import com.dls.interfaces.AdminControllerInterface;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;

// Definition der Klasse "Login", die ein JFrame (Fenster) ist und ActionListener implementiert.
public class Login extends JFrame implements ActionListener {
    
    // GUI-Komponenten für die Eingabe von Benutzerdaten und Interaktion.
    private JTextField username; // Eingabefeld für den Benutzernamen.
    private JPasswordField password; // Eingabefeld für das Passwort.
    private JButton btnLogin, btnExit; // Buttons für Login und Schließen.
    private JLabel usernameLabel, passwordLabel, frontLabel; // Labels für Benutzeroberfläche.
    Container container; // Hauptcontainer des Fensters.
    
    // Schnittstellen zur Steuerung von verschiedenen Aspekten der Anwendung.
    private AdminControllerInterface adminController; 
    private BookControllerInterface bookController;
    private StudentControllerInterface studentController;
    private FacultyControllerInterface facultyController;

    // Konstruktor der Klasse.
    public Login() {
        // Setzt den Hintergrund des Fensters auf eine graue Farbe.
        setBackground(new Color(192, 192, 192));

        try {
            // Setzt das Design der GUI auf das systemeigene Look-and-Feel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fehlerbehandlung für Look-and-Feel-Ausnahmen 
        }
          
        // Setzt den Titel des Fensters, basierend auf einer globalen Konstante.
        this.setTitle("Login - " + GlobalResources.APPLICATION_NAME);

        // Setzt das Anwendungs-Icon, basierend auf einer globalen Ressource.
        this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());

        // Initialisiert das Label für "Username" mit Schriftart und Position.
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        usernameLabel.setBounds(29, 64, 92, 26);

        // Initialisiert das Label für den Anwendungsnamen (z. B. als Überschrift).
        frontLabel = new JLabel(GlobalResources.APPLICATION_NAME);
        frontLabel.setBounds(13, 11, 392, 26);
        frontLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));

        // Initialisiert das Label für "Password" mit Schriftart und Position.
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        passwordLabel.setBounds(29, 124, 92, 17);

        // Holen des Hauptcontainers des Fensters.
        container = getContentPane();

        // Initialisiert den Login-Button mit Text, Icon und Position.
        btnLogin = new JButton("   Login");
        btnLogin.setBounds(23, 192, 115, 40);
        btnLogin.setIcon(new ImageIcon(Login.class.getResource("/resources/log_in.png")));
        btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 14));

        // Initialisiert den Exit-Button mit Text, Icon und Position.
        btnExit = new JButton("  Close");
        btnExit.setBounds(290, 192, 115, 40);
        btnExit.setIcon(new ImageIcon(Login.class.getResource("/resources/close_icon.png")));
        btnExit.setHorizontalAlignment(SwingConstants.TRAILING);
        btnExit.setFont(new Font("Times New Roman", Font.BOLD, 14));

        // Initialisiert das Eingabefeld für den Benutzernamen.
        username = new JTextField();
        username.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        username.setBounds(131, 60, 260, 35);

        // Initialisiert das Eingabefeld für das Passwort.
        password = new JPasswordField();
        password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        password.setBounds(131, 116, 260, 35);

        // Setzt die Hintergrundfarbe des Containers.
        container.setBackground(new Color(240, 240, 240));

        // Setzt das Layout des Containers auf "null" für absolute Positionierung.
        getContentPane().setLayout(null);

        // Setzt Eigenschaften für das Überschrift-Label (z. B. Hintergrund, Textfarbe).
        frontLabel.setOpaque(true);
        frontLabel.setForeground(Color.black);
        frontLabel.setBackground(new Color(240, 240, 240));

        // Fügt die Komponenten dem Container hinzu.
        container.add(frontLabel);
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(username);
        container.add(password);
        container.add(btnLogin);
        container.add(btnExit);

        // Initialisiert einen Clear-Button, der die Eingabefelder leert.
        JButton clear = new JButton("   Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText(""); // Leert das Username-Feld.
                password.setText(""); // Leert das Passwort-Feld.
            }
        });
        clear.setBounds(158, 192, 115, 40);
        clear.setIcon(new ImageIcon(Login.class.getResource("/resources/clear_icon.png")));
        clear.setHorizontalAlignment(SwingConstants.TRAILING);
        clear.setFont(new Font("Times New Roman", Font.BOLD, 14));
        getContentPane().add(clear); // Fügt den Clear-Button dem Container hinzu.

        // Setzt die Größe des Fensters.
        this.setSize(427, 286);

        // Berechnet die Bildschirmgröße und positioniert das Fenster in der Mitte.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);

        // Macht das Fenster sichtbar.
        this.setVisible(true);

        // Registriert die Login- und Exit-Buttons für das ActionListener-Interface.
        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);

        // Setzt das Verhalten beim Schließen des Fensters.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

   
    // Überladener Konstruktor: Übergibt Controller-Objekte.
    public Login(AdminControllerInterface adminController, BookControllerInterface bookController,
            StudentControllerInterface studentController, FacultyControllerInterface facultyController) {
        this(); // Ruft den Standardkonstruktor auf.
        this.adminController = adminController;
        this.bookController = bookController;
        this.studentController = studentController;
        this.facultyController = facultyController;
    }
// Hauptlogik der Aktionen bei dem Anmeldebildschirms
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object o = ae.getSource(); // Bestimmt die Quelle des Events.

        if (o == btnLogin) { // Login-Button wurde geklickt.
            try {
                if (isNotEmpty()) { // Überprüft, ob Felder nicht leer sind.
                    Admin admin = new Admin(username.getText(), password.getText()); // Erstellt Admin-Objekt.
                    if (adminController.isRightAuthentication(admin)) { // Authentifiziert den Benutzer.
                        this.dispose(); // Schließt das Login-Fenster.
                        new LibraryMenu(adminController, bookController, studentController, facultyController); // Öffnet das Hauptmenü.
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Username/Password !!", "Error",
                                JOptionPane.ERROR_MESSAGE); // Fehlermeldung bei falschen Daten.
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Fehlermeldung für andere Fehler.
            }
        }

        if (o == btnExit) { // Exit-Button wurde geklickt.
            System.exit(0); // Schließt die Anwendung.
        }
    }

    // Methode zur Überprüfung, ob die Felder nicht leer sind.
    public boolean isNotEmpty() throws CustomException {
        if (this.username.getText().equals("")) {
            throw new CustomException("Username Can't be empty !!"); // Fehler, wenn Benutzername leer ist.
        }
        if (this.password.getText().equals("")) {
            throw new CustomException("Password Can't be empty !!"); // Fehler, wenn Passwort leer ist.
        }
        return true; // Gibt true zurück, wenn alle Felder ausgefüllt sind.
    }
}
