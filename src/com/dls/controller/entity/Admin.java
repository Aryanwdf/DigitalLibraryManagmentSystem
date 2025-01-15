package com.dls.entity;  // Paketdeklaration, um die Admin-Klasse in einem bestimmten Namensraum zu platzieren.

import java.io.Serializable;  // Import der Serializable-Schnittstelle, die es der Klasse ermöglicht, Objekte zu serialisieren (in ein Byte-Format zu konvertieren).

/**
 * Diese Klasse repräsentiert ein Admin-Objekt mit den entsprechenden Attributen.
 * 
 * @author Aryan Arora
 */
public class Admin implements Serializable {  // Die Admin-Klasse implementiert das Serializable-Interface, was bedeutet, dass Instanzen dieser Klasse serialisiert werden können.

    private String username;  // Attribut für den Benutzernamen des Administrators.
    private String password;  // Attribut für das Passwort des Administrators.
    private int id;           // Attribut für die ID des Administrators.

    /**
     * Gibt den Benutzernamen des Admins zurück.
     * @return Der Benutzername des Admins.
     */
    public String getUsername() {  // Getter-Methode für den Benutzernamen.
        return username;  // Gibt den aktuellen Benutzernamen zurück.
    }

    /**
     * Setzt den Benutzernamen des Admins.
     * @param username Der neue Benutzername des Admins.
     */
    public void setUsername(String username) {  // Setter-Methode für den Benutzernamen.
        this.username = username;  // Setzt den Wert des Benutzernamens auf den übergebenen Parameter.
    }

    /**
     * Gibt das Passwort des Admins zurück.
     * @return Das Passwort des Admins.
     */
    public String getPassword() {  // Getter-Methode für das Passwort.
        return password;  // Gibt das aktuelle Passwort zurück.
    }

    /**
     * Setzt das Passwort des Admins.
     * @param password Das neue Passwort des Admins.
     */
    public void setPassword(String password) {  // Setter-Methode für das Passwort.
        this.password = password;  // Setzt das Passwort des Admins auf den übergebenen Wert.
    }

    /**
     * Gibt die ID des Admins zurück.
     * @return Die ID des Admins.
     */
    public int getId() {  // Getter-Methode für die ID des Admins.
        return id;  // Gibt die aktuelle ID zurück.
    }

    /**
     * Setzt die ID des Admins.
     * @param id Die neue ID des Admins.
     */
    public void setId(int id) {  // Setter-Methode für die ID des Admins.
        this.id = id;  // Setzt die ID des Admins auf den übergebenen Wert.
    }

    /**
     * Standardkonstruktor für die Admin-Klasse. Wird benötigt, um Instanzen ohne Parameter zu erstellen.
     */
    public Admin() {  // Standardkonstruktor der Admin-Klasse.
        // Der Konstruktor macht nichts, er initialisiert nur das Objekt.
    }

    /**
     * Konstruktor für die Admin-Klasse, um ein Admin-Objekt mit einem Benutzernamen und Passwort zu erstellen.
     * @param username Der Benutzername des Admins.
     * @param password Das Passwort des Admins.
     */
    public Admin(String username, String password) {  // Konstruktor mit Parametern für Benutzername und Passwort.
        super();  // Ruft den Konstruktor der Elternklasse (Object) auf, was in der Regel implizit ist.
        this.username = username;  // Setzt den Benutzernamen auf den übergebenen Wert.
        this.password = password;  // Setzt das Passwort auf den übergebenen Wert.
        this.id = 1;  // Setzt eine Standard-ID von 1 für das Admin-Objekt.
    }

}
