package com.dls.dal;

// Importiert Klassen für die Datenbankverbindung und das Handling von SQL-Anfragen.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Importiert die Klasse für die Datenbankkontextverwaltung.
import com.dls.database.DBContext;

// Importiert die Admin-Entität, die die Eigenschaften eines Administrators beschreibt.
import com.dls.entity.Admin;

// Importiert eine benutzerdefinierte Ausnahme für spezifische Fehlermeldungen.
import com.dls.exception.CustomException;

// Importiert die Schnittstelle AdminDAOInterface, die die zu implementierenden Methoden definiert.
import com.dls.interfaces.AdminDAOInterface;

/**
 * DAO-Klasse zur Verwaltung von Administrator-bezogenen Aktivitäten.
 * Die Klasse ermöglicht es, den Benutzernamen und das Passwort des Administrators zu aktualisieren
 * sowie Authentifizierungsprüfungen durchzuführen.
 * 
 * @author Aryan Arora
 */
public class AdminDAO implements AdminDAOInterface {

    // Standardkonstruktor, der die Klasse initialisiert.
    public AdminDAO() {
    }

    /**
     * Aktualisiert den Benutzernamen des Administrators in der Datenbank.
     * 
     * @param username Der neue Benutzername.
     * @return `true`, wenn die Aktualisierung erfolgreich war.
     * @throws CustomException, wenn ein SQL-Fehler auftritt.
     */
    @Override
    public boolean updateUsername(String username) throws CustomException {
        // SQL-Abfrage, um den Benutzernamen zu aktualisieren.
        String query = "UPDATE Admin SET username = ? WHERE id = 1";

        // Versuch, die Datenbankverbindung und SQL-Anweisung auszuführen.
        try (Connection connection = DBContext.getConnection(); // Stellt eine Verbindung zur Datenbank her.
             PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Bereitet die SQL-Anweisung vor.

            preparedStatement.setString(1, username); // Setzt den neuen Benutzernamen in die SQL-Abfrage ein.
            preparedStatement.executeUpdate(); // Führt die SQL-Abfrage aus.

        } catch (SQLException e) {
            // Wenn ein Fehler auftritt, wird eine benutzerdefinierte Ausnahme mit einer Fehlermeldung geworfen.
            throw new CustomException("Can't change Username!! " + e.getMessage());
        }

        return true; // Gibt `true` zurück, wenn die Aktualisierung erfolgreich war.
    }

    /**
     * Aktualisiert das Passwort des Administrators in der Datenbank.
     * 
     * @param password Das neue Passwort.
     * @return `true`, wenn die Aktualisierung erfolgreich war.
     * @throws CustomException, wenn ein SQL-Fehler auftritt.
     */
    @Override
    public boolean updatePassword(String password) throws CustomException {
        // SQL-Abfrage, um das Passwort zu aktualisieren.
        String query = "UPDATE Admin SET password = ? WHERE id = 1";

        // Versuch, die Datenbankverbindung und SQL-Anweisung auszuführen.
        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password); // Setzt das neue Passwort in die SQL-Abfrage ein.
            preparedStatement.executeUpdate(); // Führt die SQL-Abfrage aus.

        } catch (SQLException e) {
            throw new CustomException("Can't change Password!! " + e.getMessage());
        }

        return true;
    }

    /**
     * Überprüft die Authentifizierung des Administrators (Benutzername und Passwort).
     * 
     * @param admin Das `Admin`-Objekt mit Anmeldedaten.
     * @return `true`, wenn die Anmeldedaten korrekt sind.
     * @throws CustomException, wenn ein SQL-Fehler auftritt.
     */
    @Override
    public boolean isRightAuthentication(Admin admin) throws CustomException {
        String query = "SELECT * FROM Admin"; // SQL-Abfrage, um alle Administrator-Daten abzurufen.
        String username = ""; // Variable für den Benutzernamen aus der Datenbank.
        String password = ""; // Variable für das Passwort aus der Datenbank.

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query); 
             ResultSet resultSet = preparedStatement.executeQuery()) { // Führt die Abfrage aus und erhält das ResultSet.

            while (resultSet.next()) { // Iteriert durch die Ergebnismenge.
                username = resultSet.getString("username"); // Holt den Benutzernamen.
                password = resultSet.getString("password"); // Holt das Passwort.
            }

            // Überprüft, ob die eingegebenen Daten mit den Daten aus der Datenbank übereinstimmen.
            if (username.trim().equals(admin.getUsername()) && password.trim().equals(admin.getPassword())) {
                return true;
            }

        } catch (SQLException e) {
            throw new CustomException("Invalid Username/Password !! " + e.getMessage());
        }

        return false; // Gibt `false` zurück, wenn die Authentifizierung fehlschlägt.
    }

    /**
     * Überprüft, ob ein bestimmter Benutzername existiert.
     * 
     * @param username Der zu überprüfende Benutzername.
     * @return `true`, wenn der Benutzername existiert.
     * @throws CustomException, wenn ein SQL-Fehler auftritt.
     */
    @Override
    public boolean checkUsername(String username) throws CustomException {
        String query = "SELECT username FROM Admin"; // SQL-Abfrage, um den Benutzernamen abzurufen.
        String dbStoredUserName = ""; // Variable für den gespeicherten Benutzernamen.

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query); 
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                dbStoredUserName = resultSet.getString("username"); // Holt den Benutzernamen aus der Datenbank.
            }

            // Vergleicht den eingegebenen Benutzernamen mit dem gespeicherten Benutzernamen.
            if (dbStoredUserName.trim().equals(username.trim())) {
                return true;
            }

        } catch (SQLException e) {
            throw new CustomException("Can't Update Username !! " + e.getMessage());
        }

        return false; // Gibt `false` zurück, wenn der Benutzername nicht existiert.
    } 
    

    /**
     * Überprüft, ob ein bestimmtes Passwort korrekt ist.
     * 
     * @param password Das zu überprüfende Passwort.
     * @return `true`, wenn das Passwort korrekt ist.
     * @throws CustomException, wenn ein SQL-Fehler auftritt.
     */
    @Override
    public boolean checkPassword(String password) throws CustomException {
        String query = "SELECT password FROM Admin"; // SQL-Abfrage, um das Passwort abzurufen.
        String dbStoredPassword = ""; // Variable für das gespeicherte Passwort.

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query); 
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) { // Holt das Passwort, wenn es existiert.
                dbStoredPassword = resultSet.getString("password");
            }

            // Vergleicht das eingegebene Passwort mit dem gespeicherten Passwort.
            if (dbStoredPassword.equals(password.trim())) {
                return true;
            }

        } catch (SQLException e) {
            throw new CustomException("Can't Update Password !! " + e.getMessage());
        }

        return false; // Gibt `false` zurück, wenn das Passwort nicht korrekt ist.
    }

}
