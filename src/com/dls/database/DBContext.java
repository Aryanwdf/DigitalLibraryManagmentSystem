package com.dls.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.dls.exception.CustomException;

/**
 * Die Klasse `DBContext` stellt eine Methode zum Herstellen einer Verbindung mit der Datenbank zur Verfügung.
 * Sie verwendet die Verbindungseigenschaften aus der `DatabaseConfig`-Datei, um eine Verbindung mit der
 * Datenbank aufzubauen.
 * 
 * Diese Klasse bietet eine statische Methode, um eine Verbindung zur Datenbank zu erhalten, und verwaltet
 * die Verbindungsdetails, die in der Konfigurationsdatei definiert sind.
 * 
 * @author Aryan Arora
 */
public class DBContext {

    // Eine statische Instanz der Verbindung, die während der Laufzeit der Anwendung verwendet wird.
    private static Connection connection;

    /**
     * Setzt die Verbindung zur Datenbank. Diese Methode wird verwendet, um eine bereits bestehende
     * Verbindung zu setzen, falls diese von außen bereitgestellt wird.
     * 
     * @param connection Die zu setzende Connection-Instanz.
     */
    public static void setConnection(Connection connection) {
        DBContext.connection = connection;
    }

    /**
     * Ruft die Verbindung zur Datenbank ab. Falls keine Verbindung besteht, wird eine neue Verbindung
     * mithilfe der in der `DatabaseConfig`-Datei definierten Eigenschaften erstellt.
     * 
     * @return Die `Connection` zur Datenbank.
     * @throws CustomException Wenn ein Fehler beim Herstellen der Verbindung auftritt.
     */
    public static Connection getConnection() throws CustomException {
        try {
            // Laden des JDBC-Treibers
            Class.forName(DatabaseConfig.DRIVER_NAME);

            // Herstellen der Verbindung zur Datenbank unter Verwendung der Verbindungseigenschaften
            connection = DriverManager.getConnection(DatabaseConfig.CONNECTION_STRING, DatabaseConfig.DB_USER_NAME,
                    DatabaseConfig.DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            // Wenn ein Fehler auftritt, werfen wir eine benutzerdefinierte Ausnahme mit einer Fehlernachricht
            throw new CustomException("Database connection failed: " + e.getMessage());
        }

        // Rückgabe der hergestellten Verbindung
        return connection;
    }
}
