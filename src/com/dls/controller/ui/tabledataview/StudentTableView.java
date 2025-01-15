package com.dls.ui.tabledataview;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dls.entity.Student;
import com.dls.exception.CustomException;
import com.dls.interfaces.StudentControllerInterface;

public class StudentTableView extends AbstractTableModel {

	ArrayList<Student> StudentList;
	private String title[] = { "Student ID ", "Student Name ", "Year of Admission", "Student Contact No" };

	public StudentTableView(StudentControllerInterface studentController) {
		try {
			var studentSet = studentController.getAllStudents();
			StudentList = new ArrayList<>(studentSet);
		} catch (CustomException ex) {
			StudentList = new ArrayList<>();
			System.out.println(ex);
		}
	}

	@Override
	public int getRowCount() {
		return StudentList.size();
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Student student = StudentList.get(row);
		switch (col) {
		case 0:
			return Integer.toString(student.getStudentId());
		case 1:
			return student.getName();
		case 2:
			return Integer.toString(student.getYear());
		case 3:
			return Long.toString(student.getContact());
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
