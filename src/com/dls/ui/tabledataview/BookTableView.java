package com.dls.ui.tabledataview;

import com.dls.entity.Book;
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import java.util.*;
import javax.swing.table.*;

public class BookTableView extends AbstractTableModel {

    private HashMap<String, Book> booksLibrary = null;
    private List<String> bookIDs = null;
    private String title[] = {"ID", "Name", "Author", "Publisher"};

    public BookTableView(BookControllerInterface bookController) {
        try {
            booksLibrary = bookController.buildBooksLibrary();
            bookIDs = new ArrayList<>(booksLibrary.keySet());
        } catch (CustomException customException) {
            booksLibrary = new HashMap<>();
            System.out.println(customException.getMessage());
        }
    }

    @Override
    public int getColumnCount() {
        return title.length;
    }

    @Override
    public int getRowCount() {
        return booksLibrary.size();
    }

    @Override
    public String getColumnName(int c) {
        return title[c];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Book book = booksLibrary.get(bookIDs.get(row));

        if (col == 0) {
            return Integer.toString(book.getId());
        }
        if (col == 1) {
            return book.getName();
        }
        if (col == 2) {
            return book.getAuthor();
        }
        if (col == 3) {
            return book.getPublisher();
        }
        return null;
    }

    @Override
    public Class getColumnClass(int c) {
        try {
            if (c == 0) {
                return Class.forName("java.lang.String");
            }
            if (c == 1) {
                return Class.forName("java.lang.String");
            }
            if (c == 2) {
                return Class.forName("java.lang.String");
            }
            if (c == 3) {
                return Class.forName("java.lang.String");
            }

        } catch (Exception exception) {
            System.out.println("model.BookModel : Class getColumnClass(int c)" + exception.getMessage());
            //remove after testing
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }

}
