package com.dls.exception; // Deklariert das Paket, zu dem diese Klasse gehört

import java.io.Serializable; // Importiert das Serializable-Interface für Serialisierung von Objekten

/**
 * Eine benutzerdefinierte Ausnahme-Klasse, die von der Exception-Klasse erbt.
 * Diese Klasse dient zur Erstellung und Verwendung benutzerdefinierter Ausnahmen.
 * 
 * @author Aryan Arora
 */
public class CustomException extends Exception implements Serializable {
    
    private String message; // Privates Attribut zum Speichern einer benutzerdefinierten Fehlermeldung

    /**
     * Standardkonstruktor der Klasse.
     * Initialisiert das message-Attribut mit null.
     */
    public CustomException() {
        this.message = null; // Setzt die Standard-Fehlermeldung auf null
    }

    /**
     * Konstruktor mit einer benutzerdefinierten Nachricht als Parameter.
     * 
     * @param message Die Fehlermeldung, die übergeben und gespeichert werden soll
     */
    public CustomException(String message) {
        this.message = message; // Initialisiert die Fehlermeldung mit dem übergebenen Wert
    }

    /**
     * Setter-Methode für das Attribut message.
     * 
     * @param message Die neue Fehlermeldung, die gesetzt werden soll
     */
    public void setMessage(String message) {
        this.message = message; // Aktualisiert das message-Attribut
    }

    /**
     * Überschreibt die getMessage-Methode der Exception-Klasse.
     * Gibt die benutzerdefinierte Fehlermeldung zurück.
     * 
     * @return Die gespeicherte Fehlermeldung
     */
    @Override
    public String getMessage() {
        return this.message; // Gibt die Fehlermeldung zurück
    }

    /**
     * Überschreibt die toString-Methode der Object-Klasse.
     * Gibt eine String-Repräsentation der Ausnahme zurück, 
     * die die Fehlermeldung enthält (falls vorhanden).
     * 
     * @return Eine String-Darstellung der Ausnahme
     */
    @Override
    public String toString() {
        if (this.message == null) { // Überprüft, ob die Fehlermeldung null ist
            return "CustomException"; // Gibt nur den Klassennamen zurück, wenn keine Nachricht gesetzt ist
        } else {
            return "CustomException: " + getMessage(); // Gibt den Klassennamen und die Nachricht zurück
        }
    }
}
