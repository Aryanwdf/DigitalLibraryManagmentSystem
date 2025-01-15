package com.dls.entity; // Deklariert das Paket, zu dem diese Klasse gehört

// Die Klasse Faculty erbt von der Klasse Person und implementiert das Comparable-Interface
public class Faculty extends Person implements Comparable<Object> {
    private String department; // Deklariert ein Attribut für die Abteilung
    private int employeeId;    // Deklariert ein Attribut für die Mitarbeiter-ID

    // Standardkonstruktor, ruft den Konstruktor der Oberklasse auf
    public Faculty() {
        super(); // Ruft den Standardkonstruktor der Superklasse Person auf
    }

    // Konstruktor mit Parametern, initialisiert die Attribute der Klasse und der Superklasse
    public Faculty(String name, long contact, int employeeId, String department) {
        super(name, contact); // Ruft den parametrisierten Konstruktor der Superklasse auf
        this.employeeId = employeeId; // Initialisiert die Mitarbeiter-ID
        this.department = department; // Initialisiert die Abteilung
    }

    // Getter-Methode für das Attribut department
    public String getDepartment() {
        return department; // Gibt die Abteilung zurück
    }

    // Setter-Methode für das Attribut department
    public void setDepartment(String department) {
        this.department = department; // Setzt die Abteilung
    }

    // Getter-Methode für das Attribut employeeId
    public int getEmployeeId() {
        return employeeId; // Gibt die Mitarbeiter-ID zurück
    }

    // Setter-Methode für das Attribut employeeId
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId; // Setzt die Mitarbeiter-ID
    }

    // Überschreibt die equals-Methode, um Objekte vom Typ Faculty zu vergleichen
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faculty)) { // Prüft, ob das übergebene Objekt ein Faculty-Objekt ist
            return false; // Gibt false zurück, wenn das Objekt kein Faculty-Objekt ist
        }
        Faculty faculty = (Faculty) object; // Castet das Objekt in Faculty
        return this.employeeId == faculty.employeeId; // Vergleicht die Mitarbeiter-IDs
    }

    // Überschreibt die toString-Methode, um die Mitarbeiter-ID als String darzustellen
    @Override
    public String toString() {
        return Integer.toString(employeeId); // Gibt die Mitarbeiter-ID als String zurück
    }

    // Überschreibt die compareTo-Methode, um Faculty-Objekte basierend auf der Mitarbeiter-ID zu vergleichen
    @Override
    public int compareTo(Object object) {
        Faculty faculty = (Faculty) object; // Castet das Objekt in Faculty
        return Integer.compare(employeeId, faculty.getEmployeeId()); // Vergleicht die Mitarbeiter-IDs
    }
}
