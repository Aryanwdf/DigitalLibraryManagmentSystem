package com.dls.entity; // Deklariert das Paket, zu dem diese Klasse gehört

// Die Klasse Student erweitert die Klasse Person und implementiert das Comparable-Interface
public class Student extends Person implements Comparable<Object> {

    private int year; // Deklariert ein privates Attribut für das Studienjahr des Studenten
    private int studentId; // Deklariert ein privates Attribut für die ID des Studenten

    // Standardkonstruktor, der keine Parameter hat
    public Student() {
        super(); // Ruft den Konstruktor der Basisklasse Person auf
    }

    // Konstruktor mit Parametern, um die Attribute der Klasse zu initialisieren
    public Student(String name, long contact, int year, int studentId) {
        super(name, contact); // Ruft den Konstruktor der Basisklasse auf, um Name und Kontakt zu initialisieren
        this.year = year; // Initialisiert das Attribut year mit dem übergebenen Wert
        this.studentId = studentId; // Initialisiert das Attribut studentId mit dem übergebenen Wert
    }

    // Getter-Methode für das Attribut year
    public int getYear() {
        return year; // Gibt den Wert des Attributs year zurück
    }

    // Setter-Methode für das Attribut year
    public void setYear(int year) {
        this.year = year; // Setzt den Wert des Attributs year auf den übergebenen Wert
    }

    // Getter-Methode für das Attribut studentId
    public int getStudentId() {
        return studentId; // Gibt den Wert des Attributs studentId zurück
    }

    // Setter-Methode für das Attribut studentId
    public void setStudentId(int studentId) {
        this.studentId = studentId; // Setzt den Wert des Attributs studentId auf den übergebenen Wert
    }

    // Überschreibt die equals-Methode, um die Gleichheit zweier Student-Objekte zu prüfen
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Student)) { // Überprüft, ob das übergebene Objekt vom Typ Student ist
            return false; // Gibt false zurück, wenn das Objekt kein Student ist
        }
        Student student = (Student) object; // Castet das Objekt zu einem Student
        return this.studentId == student.studentId; // Vergleicht die studentId der beiden Objekte
    }

    // Überschreibt die toString-Methode, um die studentId als String zurückzugeben
    @Override
    public String toString() {
        return Integer.toString(studentId); // Gibt die studentId als String zurück
    }

    // Überschreibt die compareTo-Methode, um zwei Studenten anhand ihrer studentId zu vergleichen
    @Override
    public int compareTo(Object object) {
        Student student = (Student) object; // Castet das Objekt zu einem Student
        return Integer.compare(studentId, student.getStudentId()); // Vergleicht die studentId der beiden Objekte
    }
}
