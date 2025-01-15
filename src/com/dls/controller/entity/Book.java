package com.dls.entity; // Deklariert das Paket, zu dem diese Klasse gehört.

import java.text.Format; // Importiert die Format-Klasse für die Formatierung von Datumswerten.
import java.text.SimpleDateFormat; // Importiert die SimpleDateFormat-Klasse für die Formatierung von Datumswerten in spezifischen Mustern.
import java.util.Date; // Importiert die Date-Klasse, die zur Darstellung von Datums- und Zeitwerten verwendet wird.

/**
 * Die Book-Klasse repräsentiert ein Buch mit verschiedenen Eigenschaften wie Name, Autor, ISBN, usw.
 * Sie implementiert Comparable, um Objekte der Klasse Book miteinander zu vergleichen.
 */
public class Book implements Comparable<Object> {

    private String name; // Name des Buchs.
    private int id; // Eindeutige ID des Buchs.
    private String isbn; // ISBN-Nummer des Buchs.
    private String author; // Autor des Buchs.
    private String publisher; // Verlag des Buchs.
    private String remark; // Bemerkungen zu dem Buch.
    private String status; // Status des Buchs (z. B. verfügbar, ausgeliehen).
    private int pages; // Gesamtanzahl der Seiten im Buch.
    private int year; // Veröffentlichungsjahr des Buchs.
    private double price; // Preis des Buchs.
    private Date date; // Datum des Buch-Eintrags.

    /**
     * Überschreibt die equals-Methode, um Bücher anhand ihrer ID zu vergleichen.
     * 
     * @param object Das zu vergleichende Objekt.
     * @return true, wenn die IDs gleich sind; andernfalls false.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Book)) { // Überprüft, ob das Objekt ein Buch ist.
            return false; // Gibt false zurück, wenn es kein Buch ist.
        }
        Book book = (Book) object; // Castet das Objekt zu einem Buch.
        return this.id == book.id; // Vergleicht die IDs der Bücher.
    }

    /**
     * Überschreibt die toString-Methode, um die ID des Buchs als String zurückzugeben.
     * 
     * @return Die ID des Buchs als String.
     */
    @Override
    public String toString() {
        return Integer.toString(id); // Konvertiert die ID in einen String und gibt sie zurück.
    }

    /**
     * Überschreibt die compareTo-Methode, um Bücher anhand ihrer ID zu vergleichen.
     * 
     * @param object Das zu vergleichende Objekt.
     * @return Ein negativer, null oder positiver Wert, je nach Vergleichsergebnis.
     */
    @Override
    public int compareTo(Object object) {
        Book book = (Book) object; // Castet das Objekt zu einem Buch.
        return Integer.compare(id, book.getId()); // Vergleicht die IDs der Bücher.
    }

    // Getter und Setter für die verschiedenen Eigenschaften des Buchs.

    public String getName() { // Gibt den Namen des Buchs zurück.
        return name;
    }

    public void setStatus(String status) { // Setzt den Status des Buchs.
        this.status = status;
    }

    public String getStatus() { // Gibt den Status des Buchs zurück.
        return this.status;
    }

    public int getYear() { // Gibt das Veröffentlichungsjahr des Buchs zurück.
        return this.year;
    }

    public void setYear(int year) { // Setzt das Veröffentlichungsjahr des Buchs.
        this.year = year;
    }

    public void setName(String name) { // Setzt den Namen des Buchs.
        this.name = name;
    }

    public int getId() { // Gibt die ID des Buchs zurück.
        return id;
    }

    public void setId(int id) { // Setzt die ID des Buchs.
        this.id = id;
    }

    public String getISBN() { // Gibt die ISBN-Nummer des Buchs zurück.
        return isbn;
    }

    public void setISBN(String isbn) { // Setzt die ISBN-Nummer des Buchs.
        this.isbn = isbn;
    }

    public String getAuthor() { // Gibt den Autor des Buchs zurück.
        return author;
    }

    public void setAuthor(String author) { // Setzt den Autor des Buchs.
        this.author = author;
    }

    public String getPublisher() { // Gibt den Verlag des Buchs zurück.
        return publisher;
    }

    public void setPublisher(String publisher) { // Setzt den Verlag des Buchs.
        this.publisher = publisher;
    }

    public String getRemark() { // Gibt die Bemerkungen zum Buch zurück.
        return remark;
    }

    public void setRemark(String remark) { // Setzt die Bemerkungen zum Buch.
        this.remark = remark;
    }

    public int getTotalPages() { // Gibt die Gesamtanzahl der Seiten im Buch zurück.
        return pages;
    }

    public void setTotalPages(int pages) { // Setzt die Gesamtanzahl der Seiten im Buch.
        this.pages = pages;
    }

    public double getPrice() { // Gibt den Preis des Buchs zurück.
        return price;
    }

    public void setPrice(double price) { // Setzt den Preis des Buchs.
        this.price = price;
    }

    /**
     * Gibt das Eintragsdatum des Buchs als String im Format yyyy-MM-dd zurück.
     * 
     * @return Das formatierte Eintragsdatum als String.
     */
    public String getEntryDateStr() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd"); // Erstellt ein Datumsformat.
        return formatter.format(date); // Formatiert das Datum und gibt es als String zurück.
    }

    public Date getEntryDate() { // Gibt das Eintragsdatum des Buchs zurück.
        return this.date;
    }

    public void setEntryDate(Date date) { // Setzt das Eintragsdatum des Buchs.
        this.date = date;
    }
}
