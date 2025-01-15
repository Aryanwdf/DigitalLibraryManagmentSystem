package com.dls.ui.tabledataview;

import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

public class BookIssueTableView extends AbstractTableModel {

    private HashMap<String, String> booksIssued;
    ArrayList<String> personIDs, bookIDs;
    private String title[] = {"Person ID ", "Book ID "};

    public BookIssueTableView(BookControllerInterface bookController) {
        try {
            booksIssued = bookController.booksIssued();
            if (booksIssued != null) {
                personIDs = new ArrayList<>();
                bookIDs = new ArrayList<>();
                for (Object k : booksIssued.keySet()) {
                    personIDs.add((String) k);
                }
                for (Object k : booksIssued.values()) {
                    bookIDs.add((String) k);
                }
            }
        } catch (CustomException CustomException) {
            booksIssued = new HashMap<>();
        }
    }

    @Override
    public int getColumnCount() {
        return title.length;
    }

    @Override
    public int getRowCount() {
        return booksIssued.size();
    }

    @Override
    public String getColumnName(int c) {
        return title[c];
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return personIDs.get(row).trim();
        }
        if (col == 1) {
            return bookIDs.get(row).trim();
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
