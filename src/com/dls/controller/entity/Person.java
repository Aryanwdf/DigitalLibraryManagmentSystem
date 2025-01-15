package com.dls.entity; // Deklariert das Paket, zu dem diese Klasse gehört

// Die Klasse Person dient als Basisklasse und enthält allgemeine Attribute und Methoden
public class Person {

    private String name;  // Deklariert ein privates Attribut für den Namen der Person
    private long contact; // Deklariert ein privates Attribut für die Kontaktinformationen der Person

    // Standardkonstruktor, der keine Parameter hat
    public Person() {
        // Der Standardkonstruktor macht nichts, kann aber für Initialisierungen verwendet werden
    }

    // Konstruktor mit Parametern, um die Attribute der Klasse zu initialisieren
    public Person(String Name, long contact) {
        this.name = Name; // Initialisiert das Attribut name mit dem übergebenen Wert
        this.contact = contact; // Initialisiert das Attribut contact mit dem übergebenen Wert
    }

    // Getter-Methode für das Attribut name
    public String getName() {
        return this.name; // Gibt den Wert des Attributs name zurück
    }

    // Setter-Methode für das Attribut name
    public void setName(String Name) {
        this.name = Name; // Setzt den Wert des Attributs name auf den übergebenen Wert
    }

    // Getter-Methode für das Attribut contact
    public long getContact() {
        return contact; // Gibt den Wert des Attributs contact zurück
    }

    // Setter-Methode für das Attribut contact
    public void setContact(long contact) {
        this.contact = contact; // Setzt den Wert des Attributs contact auf den übergebenen Wert
    }
}
