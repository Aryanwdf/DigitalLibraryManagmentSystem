package com.dls.controller;

// Importiert die AdminDAO-Klasse, um Datenbankoperationen durchzuführen.
import com.dls.dal.AdminDAO;

// Importiert die Admin-Entitätsklasse, die die Eigenschaften eines Administrators definiert.
import com.dls.entity.Admin;

// Importiert eine benutzerdefinierte Ausnahme für Fehlerbehandlung.
import com.dls.exception.CustomException;

// Importiert die Schnittstelle AdminControllerInterface, um Funktionen für die Controller-Ebene bereitzustellen.
import com.dls.interfaces.AdminControllerInterface;

// Importiert die Schnittstelle AdminDAOInterface, um die Implementierung von Datenbankoperationen zu definieren.
import com.dls.interfaces.AdminDAOInterface;

/**
 * Controller-Klasse zur Vermittlung zwischen der Benutzeroberfläche und der Datenbanklogik (DAO).
 * Der Controller ruft Methoden der DAO-Klasse auf, um Datenbankoperationen durchzuführen.
 * 
 * @author Aryan Arora
 */
public class AdminController implements AdminControllerInterface {

    // Referenz auf die DAO-Schnittstelle für Datenbankoperationen.
    private final AdminDAOInterface adminDAO;

    /**
     * Standardkonstruktor. Initialisiert die `adminDAO`-Referenz mit einer konkreten Instanz von `AdminDAO`.
     */
    public AdminController() {
        adminDAO = new AdminDAO();
    }

    /**
     * Konstruktor zur Verwendung einer benutzerdefinierten Implementierung von `AdminDAOInterface`.
     * Ermöglicht das Injizieren einer Mock- oder Test-DAO-Implementierung.
     * 
     * @param adminDAO die Implementierung der `AdminDAOInterface`, die verwendet werden soll.
     */
    public AdminController(AdminDAOInterface adminDAO) {
        this.adminDAO = adminDAO;
    }

    /**
     * Aktualisiert das Passwort eines Administrators in der Datenbank.
     * 
     * @param password das neue Passwort des Administrators.
     * @return `true`, wenn die Passwortänderung erfolgreich war; andernfalls `false`.
     * @throws CustomException, wenn ein Fehler bei der Datenbankoperation auftritt.
     */
    @Override
    public boolean updatePassword(String password) throws CustomException {
        return adminDAO.updatePassword(password);
    }

    /**
     * Aktualisiert den Benutzernamen eines Administrators in der Datenbank.
     * 
     * @param username der neue Benutzername des Administrators.
     * @return `true`, wenn die Änderung erfolgreich war; andernfalls `false`.
     * @throws CustomException, wenn ein Fehler bei der Datenbankoperation auftritt.
     */
    @Override
    public boolean updateUsername(String username) throws CustomException {
        return adminDAO.updateUsername(username);
    }

    /**
     * Überprüft die Authentifizierung eines Administrators (z. B. Benutzernamen und Passwort).
     * 
     * @param admin das `Admin`-Objekt mit den Anmeldedaten.
     * @return `true`, wenn die Authentifizierung erfolgreich war; andernfalls `false`.
     * @throws CustomException, wenn ein Fehler bei der Datenbankoperation auftritt.
     */
    @Override
    public boolean isRightAuthentication(Admin admin) throws CustomException {
        return adminDAO.isRightAuthentication(admin);
    }

    /**
     * Überprüft, ob ein gegebener Benutzername in der Datenbank existiert.
     * 
     * @param username der zu überprüfende Benutzername.
     * @return `true`, wenn der Benutzername existiert; andernfalls `false`.
     * @throws CustomException, wenn ein Fehler bei der Datenbankoperation auftritt.
     */
    @Override
    public boolean checkUsername(String username) throws CustomException {
        return adminDAO.checkUsername(username);
    }

    /**
     * Überprüft, ob ein gegebenes Passwort korrekt ist.
     * 
     * @param password das zu überprüfende Passwort.
     * @return `true`, wenn das Passwort korrekt ist; andernfalls `false`.
     * @throws CustomException, wenn ein Fehler bei der Datenbankoperation auftritt.
     */
    @Override
    public boolean checkPassword(String password) throws CustomException {
        return adminDAO.checkPassword(password);
    }

}
