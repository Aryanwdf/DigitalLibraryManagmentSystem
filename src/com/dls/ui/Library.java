package com.dls.ui;

// Importiert benötigte Controller-Klassen und Schnittstellen
import com.dls.controller.AdminController;
import com.dls.controller.BookController;
import com.dls.controller.FacultyController;
import com.dls.controller.StudentController;
import com.dls.interfaces.AdminControllerInterface;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;

// Importiert GUI-Komponenten und Layout-Tools
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.UIManager;

// Die Klasse `Library` implementiert eine grafische Oberfläche (Splashscreen)
// und führt das Hauptprogramm aus.
public class Library extends JWindow implements Runnable {

    // GUI-Komponenten
    private JLabel loading, logo, name1, name2, developed;

    // Controller-Interfaces, um Abhängigkeiten zu kapseln
    private AdminControllerInterface adminController;
    private BookControllerInterface bookController;
    private StudentControllerInterface studentController;
    private FacultyControllerInterface facultyController;

    // Konstruktor der Klasse `Library`
    public Library() {
        // Initialisiert die Controller-Instanzen
        this.adminController = new AdminController();
        this.bookController = new BookController();
        this.studentController = new StudentController();
        this.facultyController = new FacultyController();

        // Setzt das Layout auf null, um absolute Positionen zu verwenden
        getContentPane().setLayout(null);

        // Erstellt und konfiguriert die GUI-Komponenten
        name1 = new JLabel("Digital Library"); // Titel der Anwendung
        name2 = new JLabel("Management System"); // Untertitel
        name1.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Schriftart und -größe
        name1.setForeground(new Color(0, 0, 0)); // Schriftfarbe
        name2.setFont(new Font("Times New Roman", Font.BOLD, 20));

        // Entwicklerhinweis
        developed = new JLabel("Developed By : Aryan Arora");
        developed.setFont(new Font("Times New Roman", Font.BOLD, 15));

        // Logo-Label, das ein Bild anzeigt
        logo = new JLabel(new ImageIcon(Library.class.getResource("/resources/logo.png")));

        // Lade-Statusanzeige
        loading = new JLabel("Loading...");
        loading.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // Setzt die Position und Größe der GUI-Komponenten
        logo.setBounds(10, 10, 100, 100);
        developed.setBounds(10, 102, 200, 30);
        name1.setBounds(120, 20, 270, 40);
        name2.setBounds(190, 71, 200, 20);
        loading.setBounds(280, 102, 100, 30);

        // Fügt die GUI-Komponenten zum Fenster hinzu
        getContentPane().add(name1);
        getContentPane().add(name2);
        getContentPane().add(loading);
        getContentPane().add(developed);
        getContentPane().add(logo);

        // Versucht, das native Look-and-Feel des Systems zu verwenden
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fehler beim Setzen des Look-and-Feels ignorieren
        }

        // Setzt die Größe des Fensters
        this.setSize(400, 130);

        // Zentriert das Fenster auf dem Bildschirm
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);

        // Zeigt das Fenster an
        this.setVisible(true);
    }

    // Überschreibt die Methode `run` der Schnittstelle `Runnable`
    @Override
    public void run() {
        try {
            // Simuliert das Laden mit einer Fortschrittsanzeige
            for (int i = 1; i <= 100; i++) {
                this.loading.setText("Loading " + i + "%"); // Aktualisiert den Lade-Status
                Thread.sleep(50); // Verzögert die Anzeige für 50 ms
            }

            // Schließt das Splashscreen-Fenster
            this.dispose();

            // Startet das Login-Fenster mit den initialisierten Controllern
            new Login(adminController, bookController, studentController, facultyController);
        } catch (Exception e) {
            // Fehler während des Ladeprozesses ignorieren
        }
    }

    // Hauptmethode, Einstiegspunkt der Anwendung
    public static void main(String gg[]) {
        // Startet die `Library`-Klasse in einem neuen Thread
        new Thread(new Library()).start();
    }
}
