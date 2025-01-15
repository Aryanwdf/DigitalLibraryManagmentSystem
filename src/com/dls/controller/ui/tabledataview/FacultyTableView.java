package com.dls.ui.tabledataview;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dls.entity.Faculty;
import com.dls.exception.CustomException;
import com.dls.interfaces.FacultyControllerInterface;

public class FacultyTableView extends AbstractTableModel {

	ArrayList<Faculty> facultyList;
	private String title[] = { "Faculty ID ", "Faculty Name ", "Faculty Department", "Faculty Contact No" };

	public FacultyTableView(FacultyControllerInterface facultyController) {
		try {
			var facultySet = facultyController.getAllFaculties();
			facultyList = new ArrayList<>(facultySet);
		} catch (CustomException ex) {
			facultyList = new ArrayList<>();
			System.out.println(ex);
		}
	}

	@Override
	public int getRowCount() {
		return facultyList.size();
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Faculty faculty = facultyList.get(row);
		switch (col) {
		case 0:
			return Integer.toString(faculty.getEmployeeId());
		case 1:
			return faculty.getName();
		case 2:
			return faculty.getDepartment();
		case 3:
			return Long.toString(faculty.getContact());
		default:
			break;
		}
		return null;
	}

	@Override
	public String getColumnName(int c) {
		return title[c];
	}

}
