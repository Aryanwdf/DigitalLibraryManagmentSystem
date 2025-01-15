package com.dls.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Die `DatabaseConfig`-Klasse liest die `application.properties`-Datei, welche die
 * Konfigurationsinformationen für die Datenbankverbindung enthält. Diese Klasse
 * wird verwendet, um die Verbindungsparameter zu einer MySQL-Datenbank zu konfigurieren.
 * 
 * Die Klasse lädt die Verbindungsdetails aus der Datei und stellt die notwendigen
 * Parameter zur Verfügung, die in der gesamten Anwendung für den Aufbau der
 * Verbindung verwendet werden.
 * 
 * @author Aryan Arora
 */
public class DatabaseConfig {

    // Eine statische Instanz von Properties, die verwendet wird, um die Konfiguration zu speichern
    static Properties prop = new Properties();

    // Statischer Block zum Laden der Konfigurationsdatei beim Initialisieren der Klasse
    static {
        try {
            // Die Konfigurationsdatei wird von einem FileInputStream geladen
            FileInputStream input = new FileInputStream("application.properties");
            prop.load(input); // Die Datei wird geladen und die Eigenschaften werden in das Properties-Objekt übernommen
        } catch (IOException e) {
            e.printStackTrace(); // Fehlerbehandlung für Datei-Ladeprobleme
        }
    }

    // Konfigurationsparameter aus der geladenen Properties-Datei
    public final static String DRIVER_NAME = prop.getProperty("db.driver"); // Der JDBC-Treibername
    public final static String DB_HOST = prop.getProperty("db.host"); // Der Hostname der Datenbank
    public final static String DB_PORT = prop.getProperty("db.port"); // Der Port, über den die Datenbank erreichbar ist
    public final static String DB_NAME = prop.getProperty("db.name"); // Der Name der Datenbank
    public final static String DB_USER_NAME = prop.getProperty("db.username"); // Der Benutzername für die Datenbankanmeldung
    public final static String DB_PASSWORD = prop.getProperty("db.password"); // Das Passwort für die Datenbankanmeldung
    public final static String SSL_MODE = prop.getProperty("db.sslmode"); // Die SSL-Option für die Verbindung

    // Der vollständige Verbindungsstring, der aus den oben genannten Parametern zusammengesetzt wird
    public final static String CONNECTION_STRING = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + SSL_MODE;
}
