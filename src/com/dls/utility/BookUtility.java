package com.dls.utility;

import com.dls.entity.Book;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse `BookUtility` enthält Hilfsmethoden zur Verarbeitung von Bücherlisten.
 * Diese Klasse stellt eine Methode bereit, um zu prüfen, ob eine Liste von Büchern Duplikate enthält.
 */
public class BookUtility {

    /**
     * Überprüft, ob die übergebene Liste von Büchern Duplikate enthält.
     * Ein Duplikat wird anhand der `ID` des Buches erkannt. Wenn zwei oder mehr Bücher die gleiche ID
     * haben, gilt dies als Duplikat.
     * 
     * @param books Eine Liste von `Book`-Objekten, die überprüft werden soll.
     * @return `true`, wenn Duplikate in der Liste enthalten sind, andernfalls `false`.
     */
    public static boolean isContainDuplicateValue(ArrayList<Book> books) {
        // Ein Set wird verwendet, um nur eindeutige IDs zu speichern
        Set<Integer> bookIdSet = new HashSet<>();

        // Iteration über die Liste der Bücher
        for (Book book : books) {
            // Hinzufügen der Buch-ID zum Set. Ein Set kann nur eindeutige Werte speichern.
            bookIdSet.add(book.getId());
        }

        // Wenn die Größe des Sets (eindeutige IDs) nicht mit der Größe der Liste übereinstimmt,
        // bedeutet das, dass es Duplikate gibt.
        return bookIdSet.size() != books.size();
    }
}
