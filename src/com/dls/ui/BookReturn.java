package com.dls.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dls.contstant.GlobalResources;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.entity.Student;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;
import com.dls.ui.tabledataview.BookIssueTableView;

public class BookReturn extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

	public enum Direction {
		FORWARD, BACKWARD
	};

	private final JTable booksTable;
	private final JTextField searchBox;
	private final JLabel heading, error, errorIcon, search, totalCount, list, close, found;
	private final JScrollPane scrollPane;
	private BookIssueTableView bookIssueTableView;
	private final IssueDetails issueDetails;
	private final Container container;
	private final ReturnBook returnBookPanel;
	private LibraryMenu libraryMenu;
	private final BookControllerInterface bookController;
	private final StudentControllerInterface studentController;
	private final FacultyControllerInterface facultyController;
	ArrayList<Book> bookIssueArrayList;
	boolean isStudentReturn;
	int personIdReturn;

	public BookReturn(LibraryMenu libraryMenu, BookControllerInterface bookController,
			StudentControllerInterface studentController, FacultyControllerInterface facultyController) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.bookController = bookController;
		this.studentController = studentController;
		this.facultyController = facultyController;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.libraryMenu = libraryMenu;
		returnBookPanel = new ReturnBook();
		found = new JLabel("");
		found.setForeground(Color.red);
		found.setVisible(true);
		issueDetails = new IssueDetails();
		this.setUndecorated(false);
		close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent me) {
				BookReturn.this.libraryMenu.setVisible(true);
				BookReturn.this.libraryMenu.setParentActive();
				BookReturn.this.dispose();
			}
		});

		heading = new JLabel("Digital Library Management System - Book Return");
		heading.setFont(new Font("Times New Roman", Font.BOLD, 26));
		getContentPane().setLayout(null);
		error = new JLabel("");
		errorIcon = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		container = this.getContentPane();
		container.setBackground(new Color(240, 240, 240));
		bookIssueTableView = new BookIssueTableView(bookController);
		search = new JLabel("Search Book:");
		search.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/find_icon.png")));
		search.setFont(new Font("Times New Roman", Font.BOLD, 20));
		totalCount = new JLabel("");
		totalCount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		list = new JLabel("Total Records: ");
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchBox = new JTextField("");
		booksTable = new JTable(bookIssueTableView);
		booksTable.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookIssueTableView.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookIssueTableView.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.booksTable.getTableHeader().setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		this.setAreaTypeTableRowHeight();
		this.scrollPane = new JScrollPane(booksTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		search.setBounds(10, 70, 162, 30);
		searchBox.setBounds(182, 70, 328, 35);
		found.setBounds(310, 100, 80, 10);
		list.setBounds(10, 123, 132, 30);
		totalCount.setBounds(152, 123, 100, 30);
		scrollPane.setBounds(10, 165, 350, 240);
		issueDetails.setBounds(380, 154, 615, 250);
		returnBookPanel.setBounds(10, 420, 990, 170);
		errorIcon.setBounds(10, 590, 32, 32);
		error.setBounds(40, 590, 800, 32);
		error.setForeground(Color.red);
		error.setFont(new Font("Times New Roman", Font.BOLD, 15));
		heading.setBounds(80, 10, 590, 40);
		close.setBounds(980, 3, 32, 32);
		container.add(list);
		container.add(search);
		container.add(searchBox);
		container.add(totalCount);
		container.add(this.scrollPane);
		container.add(heading);
		container.add(found);
		container.add(returnBookPanel);
		container.add(issueDetails);
		errorIcon.setVisible(false);
		this.setSize(1020, 647);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);
		this.setTitle("Book Return - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());
		updateBookCount();
		addListeners();
		issueDetails.setDefaults(false);
		found.setVisible(true);
		this.setTitle("Book Return");
		this.setVisible(true);
	}

	public void refreshAll() {
		bookIssueTableView = new BookIssueTableView(bookController);
		booksTable.setModel(bookIssueTableView);
		setAreaTypeTableRowHeight();
		booksTable.repaint();
		booksTable.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookIssueTableView.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookIssueTableView.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.booksTable.getTableHeader().setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		this.setAreaTypeTableRowHeight();
		updateBookCount();
	}

	private void setAreaTypeTableRowHeight() {
		for (int x = 0; x < this.booksTable.getRowCount(); x++) {
			this.booksTable.setRowHeight(x, 25);
		}
	}

	public void addListeners() {

		booksTable.getSelectionModel().addListSelectionListener(this);
		searchBox.getDocument().addDocumentListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	public void updateBookCount() {
		this.totalCount.setText(String.valueOf(bookIssueTableView.getRowCount()));
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) { // gets called when something gets inserted into the
		// document
		this.search(this.searchBox.getText().trim(), 0, Direction.FORWARD, false);
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) { // gets called when something gets removed from the document
		this.search(this.searchBox.getText().trim(), 0, Direction.BACKWARD, false);
	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) { // gets called when a
//set or set of attributes change
// In our application we don't want to do anything in this case
	}

	private void search(String electronicUnitName, int fromIndex, Direction direction, boolean isSelected) {
		if (electronicUnitName.length() == 0) {
			found.setText("");
			this.booksTable.clearSelection();
			return;
		}
		if (booksTable.getRowCount() == 0) {
			return;
		}
		if (direction == Direction.FORWARD) {
			for (int x = fromIndex; x < bookIssueTableView.getRowCount(); x++) {
				if (((String) this.bookIssueTableView.getValueAt(x, 0)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					found.setText("");
					return;
				} else if (((String) this.bookIssueTableView.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					found.setText("");
					return;
				}
				found.setText("Not Found");
			}
		}
		if (direction == Direction.BACKWARD) {
			for (int x = fromIndex; x >= 0; x--) {
				if (((String) this.bookIssueTableView.getValueAt(x, 0)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					found.setText("");
					return;
				} else if (((String) this.bookIssueTableView.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					found.setText("");
					return;
				}

				found.setText("Not Found");
			}
		}
		if (!isSelected) {

			found.setText("");
			this.booksTable.clearSelection();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (issueDetails == null) {
			return;
		}
		int selectedRowIndex = this.booksTable.getSelectedRow();
		if (selectedRowIndex < 0 || selectedRowIndex >= this.booksTable.getRowCount()) {
			issueDetails.setDefaults(false);
		} else {
			try {
				String id = (String) booksTable.getValueAt(selectedRowIndex, 0);
				String books = (String) booksTable.getValueAt(selectedRowIndex, 1);
				showDetails(id, books);
				errorIcon.setVisible(false);
				error.setText("");

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				errorIcon.setVisible(true);
				error.setText(e.getMessage());
				issueDetails.setDefaults(false);
			}
		}

	}

	public void showDetails(String id, String books) throws Exception {
		Student student = null;
		System.out.println(id);
		Faculty faculty = null;
		bookIssueArrayList = new ArrayList<>();
		String[] values = id.split(" : ");
		personIdReturn = Integer.parseInt(values[1]);
		if (values[0].trim().contains("Student")) {
			student = studentController.getStudent(values[1].trim());
			isStudentReturn = true;
		} else {
			faculty = facultyController.getFaculty(values[1].trim());
			isStudentReturn = false;
		}

		String[] bookIds = books.split(",");
		for (String str : bookIds) {
			bookIssueArrayList.add(bookController.getBook(Integer.parseInt(str)));
		}

		if (student == null) {
			issueDetails.setFacultyDetails(faculty, bookIssueArrayList);
		} else {
			issueDetails.setStudentDetails(student, bookIssueArrayList);
		}

	}

	public class IssueDetails extends JPanel {

		JLabel name, id, b1, b2, b3, b4, b5, person, books, contact;
		private BookReturn br;

		IssueDetails() {
			this.setLayout(null);
			br = BookReturn.this;
			name = new JLabel("");
			name.setFont(new Font("Times New Roman", Font.BOLD, 15));
			id = new JLabel("");
			id.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b1 = new JLabel("");
			b1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b2 = new JLabel("");
			b2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b3 = new JLabel("");
			b3.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b4 = new JLabel("");
			b4.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b5 = new JLabel("");
			b5.setFont(new Font("Times New Roman", Font.BOLD, 15));

			contact = new JLabel("");
			contact.setFont(new Font("Times New Roman", Font.BOLD, 15));
			person = new JLabel("Issued to : ");
			person.setFont(new Font("Times New Roman", Font.BOLD, 20));
			books = new JLabel("Book List : ");
			books.setFont(new Font("Times New Roman", Font.BOLD, 20));
			person.setBounds(10, 15, 500, 20);
			name.setBounds(10, 40, 500, 20);
			id.setBounds(10, 60, 500, 20);
			contact.setBounds(10, 80, 500, 20);
			books.setBounds(10, 110, 500, 20);
			b1.setBounds(10, 140, 500, 20);
			b2.setBounds(10, 160, 500, 20);
			b3.setBounds(10, 180, 500, 20);
			b4.setBounds(10, 200, 500, 20);
			b5.setBounds(10, 220, 500, 20);

			this.add(b1);
			this.add(b4);
			this.add(b3);
			this.add(b2);
			this.add(b5);
			this.add(person);
			this.add(name);
			this.add(id);
			this.add(contact);
			this.add(books);
			this.setVisible(true);
			this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Issue Details");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
			this.setBorder(tb);
		}

		public void setDefaults(boolean value) {
			id.setVisible(value);
			b1.setVisible(value);
			b2.setVisible(value);
			b3.setVisible(value);
			b4.setVisible(value);
			b5.setVisible(value);
			name.setVisible(value);
			contact.setVisible(false);
			b1.setText("");
			b2.setText("");
			b3.setText("");
			b4.setText("");
			b5.setText("");
			name.setText("");
			id.setText("");
			contact.setText("");
		}

		public void setStudentDetails(Student s, ArrayList<Book> bookIssueArrayList) {
			setDefaults(false);
			id.setVisible(true);
			name.setVisible(true);
			contact.setVisible(true);
			id.setText("StudentId : " + s.getStudentId());
			name.setText("Name      : " + s.getName());
			contact.setText("Contact   : " + s.getContact());
			setBooks(bookIssueArrayList);
		}

		public void setFacultyDetails(Faculty s, ArrayList<Book> bookIssueArrayList) {

			setDefaults(false);
			id.setVisible(true);
			name.setVisible(true);
			contact.setVisible(true);
			id.setText("FacultyId : " + s.getEmployeeId());
			name.setText("Name      : " + s.getName());
			contact.setText("Department: " + s.getDepartment());
			setBooks(bookIssueArrayList);
		}

		public void setBooks(ArrayList<Book> bookIssueArrayList) {
			int i = 1;
			for (Book book : bookIssueArrayList) {
				switch (i) {
				case 1:
					b1.setVisible(true);
					b1.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
					break;
				case 2:
					b2.setVisible(true);
					b2.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
					break;
				case 3:
					b3.setVisible(true);
					b3.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
					break;
				case 4:
					b4.setVisible(true);
					b4.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
					break;
				case 5:
					b5.setVisible(true);
					b5.setText(i + ". (Book Id) - " + book.getId() + " : " + book.getName());
					break;
				default:
					break;
				}
				i++;
			}

		}
	}

	class ReturnBook extends JPanel implements ActionListener, ItemListener {

		private final JButton returnButton, saveButton, close;
		private final JLabel personIdLabel, b1, b2, b3, b4, b5, b111, b222, b333, b444, b555, idEr, qtyEr, qtyLabel;
		private JTextField b11, b22, b33, b44, b55, personIdTextBox;
		private final BookReturn br;
		private final JComboBox qty;
		ArrayList<Book> books = null;
		int num = 0;

		ReturnBook() {
			br = BookReturn.this;
			qtyLabel = new JLabel("Quantity:");
			qtyLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
			String values[] = { "Select", "1", "2", "3", "4", "5" };
			qty = new JComboBox(values);
			qtyEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			this.setLayout(null);
			returnButton = new JButton("Return");
			saveButton = new JButton("  Save");
			close = new JButton("  Close");
			personIdLabel = new JLabel("PersonId:");
			personIdLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
			idEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b1 = new JLabel("Book 1:");
			b1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b2 = new JLabel("Book 2:");
			b2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b3 = new JLabel("Book 3:");
			b3.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b4 = new JLabel("Book 4:");
			b4.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b5 = new JLabel("Book 5:");
			b5.setFont(new Font("Times New Roman", Font.BOLD, 15));
			b111 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b222 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b333 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b444 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b555 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			b11 = new JTextField("");
			b22 = new JTextField("");
			b33 = new JTextField("");
			b44 = new JTextField("");
			b55 = new JTextField("");
			b55 = new JTextField("");
			personIdTextBox = new JTextField("");
			returnButton.setBounds(20, 25, 115, 40);
			returnButton.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/return_book.png")));
			returnButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
			saveButton.setBounds(150, 20, 115, 40);
			close.setBounds(300, 20, 115, 40);
			saveButton.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
			saveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
			close.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/close_icon.png")));
			close.setFont(new Font("Times New Roman", Font.BOLD, 14));
			personIdLabel.setBounds(10, 70, 80, 30);
			personIdTextBox.setBounds(90, 70, 100, 30);
			idEr.setBounds(190, 70, 30, 30);
			qtyLabel.setBounds(234, 70, 70, 30);
			qty.setBounds(300, 70, 80, 30);
			qtyEr.setBounds(380, 70, 30, 30);
			b1.setBounds(10, 110, 50, 30);
			b11.setBounds(70, 110, 100, 30);
			b111.setBounds(160, 110, 30, 30);
			b2.setBounds(200, 110, 60, 30);
			b22.setBounds(260, 110, 100, 30);
			b222.setBounds(360, 110, 30, 30);
			b3.setBounds(400, 110, 60, 30);
			b33.setBounds(460, 110, 100, 30);
			b333.setBounds(560, 110, 30, 30);
			b4.setBounds(600, 110, 60, 30);
			b44.setBounds(660, 110, 100, 30);
			b444.setBounds(760, 110, 30, 30);
			b5.setBounds(800, 110, 60, 30);
			b55.setBounds(860, 110, 100, 30);
			b555.setBounds(960, 110, 30, 30);
			this.add(b1);
			this.add(b2);
			this.add(b3);
			this.add(b4);
			this.add(b5);
			this.add(b11);
			this.add(b22);
			this.add(b33);
			this.add(b44);
			this.add(b55);
			this.add(b111);
			this.add(b222);
			this.add(b333);
			this.add(b444);
			this.add(b555);
			this.add(close);
			this.add(returnButton);
			this.add(saveButton);
			this.add(personIdTextBox);
			this.add(personIdLabel);
			this.add(idEr);
			this.add(qtyLabel);
			this.add(qty);
			this.add(qtyEr);
			this.setVisible(true);
			this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Return Book");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
			this.setBorder(tb);
			this.setDefault();
			returnButton.addActionListener(this);
			saveButton.addActionListener(this);
			close.addActionListener(this);
			this.qty.setSelectedIndex(0);
			this.qtyLabel.setVisible(true);
			// this.qty.setBackground(new Color(225, 176, 98));
			this.qty.setEnabled(false);
			qty.addItemListener(this);
		}

		public void setDefault() {
			b111.setVisible(false);
			b222.setVisible(false);
			b333.setVisible(false);
			b444.setVisible(false);
			b555.setVisible(false);
			idEr.setVisible(false);
			b11.setBackground(new Color(240, 240, 240));
			b22.setBackground(new Color(240, 240, 240));
			b33.setBackground(new Color(240, 240, 240));
			b44.setBackground(new Color(240, 240, 240));
			b55.setBackground(new Color(240, 240, 240));
			personIdLabel.setText("Person Id:");
			personIdTextBox.setBackground(new Color(240, 240, 240));
			this.qty.setSelectedIndex(0);
			qtyEr.setVisible(false);
			personIdTextBox.setEditable(false);
			this.qty.setSelectedIndex(0);
			this.qty.setBackground(new Color(225, 176, 98));
			this.qty.setEnabled(false);
			b11.setEditable(false);
			b22.setEditable(false);
			b33.setEditable(false);
			b44.setEditable(false);
			b55.setEditable(false);
			close.setVisible(false);
			saveButton.setVisible(false);
			personIdTextBox.setText("");
			b11.setText("");
			b22.setText("");
			b33.setText("");
			b44.setText("");
			b55.setText("");
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == close) {
				returnButton.setVisible(true);
				setDefault();
				br.searchBox.setEditable(true);
				br.booksTable.clearSelection();
				br.booksTable.setEnabled(true);
				br.issueDetails.setDefaults(false);
			}

			if (ae.getSource() == returnButton) {
				int selectedRowIndex = br.booksTable.getSelectedRow();
				if (selectedRowIndex == -1) {
					JOptionPane.showMessageDialog(this, "Select a record to return.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				returnButton.setVisible(false);
				setDefault();
				this.qty.setSelectedIndex(bookIssueArrayList.size());
				close.setVisible(true);
				saveButton.setVisible(true);
				br.searchBox.setEditable(false);
				br.booksTable.clearSelection();
				br.booksTable.setEnabled(false);
				br.issueDetails.setDefaults(false);
				personIdTextBox.setText(Integer.toString(personIdReturn));
				if (isStudentReturn) {
					personIdLabel.setText("Student Id:");
				} else {
					personIdLabel.setText("Faculty Id:");
				}
				int i = 1;
				for (Book book : bookIssueArrayList) {
					switch (i) {
					case 1:
						b11.setText(Integer.toString(book.getId()));
						break;
					case 2:
						b22.setText(Integer.toString(book.getId()));
						break;
					case 3:
						b33.setText(Integer.toString(book.getId()));
						break;
					case 4:
						b44.setText(Integer.toString(book.getId()));
						break;
					case 5:
						b55.setText(Integer.toString(book.getId()));
						break;
					default:
						break;
					}
					i++;
				}
			}

			if (ae.getSource() == saveButton) {
				try {
					Student student = null;
					Faculty faculty = null;
					if (isStudentReturn) {
						student = br.studentController.getStudent(Integer.toString(personIdReturn));
					} else {
						faculty = br.facultyController.getFaculty(Integer.toString(personIdReturn));
					}

					if (isStudentReturn) {
						br.studentController.returnBook(student, bookIssueArrayList);
					} else {
						br.facultyController.returnBook(faculty, bookIssueArrayList);
					}

					for (Book book : bookIssueArrayList) {
						book.setStatus("Available");
						br.bookController.updateBook(book);
					}

					br.bookIssueTableView.fireTableDataChanged();
					br.booksTable.repaint();
					br.setAreaTypeTableRowHeight();
					br.refreshAll();
					returnButton.setVisible(true);
					setDefault();
					br.searchBox.setEditable(true);
					br.booksTable.clearSelection();
					br.booksTable.setEnabled(true);
					br.issueDetails.setDefaults(false);
					br.error.setText("Successfully Returned!!");
					br.error.setForeground(Color.blue);
					br.errorIcon.setVisible(false);
					JOptionPane.showMessageDialog(this, "Successfully Returned!!", "Notification",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					br.errorIcon.setVisible(true);
					br.error.setText(e.getMessage());
					br.error.setForeground(Color.red);
				}
			}
		}

		@Override
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource() == qty) {
			}
		}

	}
}
