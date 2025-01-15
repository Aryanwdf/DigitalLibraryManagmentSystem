// Importiert benötigte Klassen für GUI-Komponenten und Ereignisbehandlung
package com.dls.ui;

import java.awt.Checkbox; // Checkbox-Komponente
import java.awt.CheckboxGroup; // CheckboxGroup für die Gruppierung der Checkboxen
import java.awt.Color; // Farbdefinitionen
import java.awt.Container; // Container für das Layout
import java.awt.Dimension; // Dimensionen (z. B. Fenstergröße)
import java.awt.Font; // Schriftart
import java.awt.Toolkit; // Toolkit für Bildschirmgröße
import java.awt.event.ActionEvent; // Ereignis für Aktionen
import java.awt.event.ActionListener; // Listener für Aktionen
import java.awt.event.ItemEvent; // Ereignis für Änderungen von Items
import java.awt.event.ItemListener; // Listener für Items
import java.awt.event.WindowAdapter; // Ereignisbehandlung beim Schließen des Fensters
import java.awt.event.WindowEvent; // Ereignis beim Fensterereignis

import javax.swing.ImageIcon; // Klasse für Bildsymbole
import javax.swing.JButton; // Schaltflächen-Komponente
import javax.swing.JDialog; // Dialogfenster
import javax.swing.JFrame; // Hauptfenster
import javax.swing.JLabel; // Label für Texte und Bilder
import javax.swing.JOptionPane; // Dialogboxen für Nachrichten
import javax.swing.JPanel; // Panel für Layout
import javax.swing.JScrollPane; // ScrollPane für scrollbare Inhalte
import javax.swing.JTable; // Tabelle
import javax.swing.JTextField; // Textfeld für Eingaben
import javax.swing.ListSelectionModel; // Auswahlmodell für Listen
import javax.swing.ScrollPaneConstants; // Konstanten für ScrollPane
import javax.swing.UIManager; // Benutzeroberflächen-Management
import javax.swing.border.EtchedBorder; // Rand mit eingraviertem Design
import javax.swing.border.TitledBorder; // Rand mit Titel
import javax.swing.event.DocumentEvent; // Ereignis für Dokumentänderungen
import javax.swing.event.DocumentListener; // Listener für Dokumentänderungen
import javax.swing.event.ListSelectionEvent; // Ereignis für Auswahl in Listen
import javax.swing.event.ListSelectionListener; // Listener für Auswahländerungen
import javax.swing.table.AbstractTableModel; // Basismodell für Tabellen

// Import von benutzerdefinierten Ressourcen und Entitäten
import com.dls.contstant.GlobalResources; // Globale Ressourcen
import com.dls.entity.Faculty; // Fakultätsentität
import com.dls.entity.Person; // Person-Entität
import com.dls.entity.Student; // Student-Entität
import com.dls.exception.CustomException; // Benutzerdefinierte Ausnahmen
import com.dls.interfaces.FacultyControllerInterface; // Interface für Fakultätssteuerung
import com.dls.interfaces.StudentControllerInterface; // Interface für Studentensteuerung
import com.dls.ui.tabledataview.FacultyTableView; // Fakultätstabelle-Ansicht
import com.dls.ui.tabledataview.StudentTableView; // Studententabelle-Ansicht

// Hauptklasse für die Personverwaltung
public class PersonMaintenance extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

	// Deklaration von Komponenten
	private final CheckboxGroup personCheckBoxGroup; // Gruppe von Checkboxen
	private final Checkbox studentCheckBox, facultyCheckBox; // Checkboxen für Student und Fakultät
	private JButton addPerson, deletePerson, updatePerson, save, cancel; // Schaltflächen für Aktionen
	private static boolean isAdding = false; // Status, ob eine Person hinzugefügt wird
	private JPanel detailsPanel; // Panel für Details
	private JTable personTable; // Tabelle zur Anzeige von Personen
	private JScrollPane scrollPane; // ScrollPane für Tabelle
	private StudentTableView studentTableView; // Ansicht für Studenten
	private FacultyTableView facultyTableView; // Ansicht für Fakultäten
	private JTextField searchBox, txtName, txtContactNo, txtId, txtOther; // Textfelder für Eingaben
	private JLabel search, list, totalCount, close, error, errorIcon, lblName, lblcontactNo, lblId, lblOther,
			lblNameErr, lblContactNoErr, lblOtherErr; // Labels für UI-Elemente
	private Container c; // Container für das Layout
	private LibraryMenu libraryMenu; // Verweis auf das Library Menu
	private StudentControllerInterface studentController; // Steuerung für Studenten
	private FacultyControllerInterface facultyController; // Steuerung für Fakultäten
	private boolean isStudent; // Boolean zur Unterscheidung zwischen Student und Fakultät
	private String recordCount = "Total Student Count : "; // Anzeige der Gesamtzahl der Studenten

	// Enumeration für Richtungen
	public enum Direction {
		FORWARD, BACKWARD
	};

	// Konstruktor der Klasse
	public PersonMaintenance(LibraryMenu lm, StudentControllerInterface studentController,
			FacultyControllerInterface facultyController) {
		// Setzt das Look-and-Feel auf das Systemstandard-Design
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Zeigt eine Fehlermeldung bei Fehlern
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Initialisiert die Steuerung und das Menu
		this.isStudent = true;
		this.studentController = studentController;
		this.facultyController = facultyController;
		this.libraryMenu = lm;

		// Setzt Fenster-Optionen
		this.setUndecorated(false); // Keine Dekoration für das Fenster
		close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON))); // Schaltfläche für Schließen
		this.addWindowListener(new WindowAdapter() { // Ereignisbehandlung beim Fenster-Schließen
			@Override
			public void windowClosing(WindowEvent me) {
				PersonMaintenance.this.libraryMenu.setVisible(true); // Zeigt das Hauptmenu wieder
				PersonMaintenance.this.libraryMenu.setParentActive(); // Setzt das Hauptmenu aktiv
				PersonMaintenance.this.dispose(); // Schließt das Fenster
			}
		});
// Till Here

		this.personCheckBoxGroup = new CheckboxGroup();
		this.studentCheckBox = new Checkbox("Student", personCheckBoxGroup, true);
		this.facultyCheckBox = new Checkbox("Faculty", personCheckBoxGroup, false);
		studentCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
		facultyCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
		studentCheckBox.setBounds(333, 124, 200, 35);
		facultyCheckBox.setBounds(558, 124, 200, 35);
		getContentPane().setLayout(null);
		getContentPane().add(studentCheckBox);
		getContentPane().add(facultyCheckBox);

		JLabel lblDigitalLibraryManagement = new JLabel("Digital Library Management System - Person Management\r\n");
		lblDigitalLibraryManagement.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblDigitalLibraryManagement.setBounds(157, 11, 687, 30);
		getContentPane().add(lblDigitalLibraryManagement);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		c = this.getContentPane();
		c.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		search = new JLabel("Search Student:");
		search.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/find_icon.png")));
		search.setFont(new Font("Times New Roman", Font.BOLD, 20));

		totalCount = new JLabel("");
		totalCount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		list = new JLabel(recordCount);
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchBox = new JTextField("");

		addPerson = new JButton("  Add");
		addPerson.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/add_icon.png")));
		addPerson.setFont(new Font("Times New Roman", Font.BOLD, 14));
		error = new JLabel("");
		errorIcon = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

		deletePerson = new JButton("  Delete");
		deletePerson.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/trash_icon.png")));
		deletePerson.setFont(new Font("Times New Roman", Font.BOLD, 14));

		updatePerson = new JButton(" Update");
		updatePerson.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/edit_icon.png")));
		updatePerson.setFont(new Font("Times New Roman", Font.BOLD, 14));
		studentTableView = new StudentTableView(studentController);
		personTable = new JTable(studentTableView);
		personTable.setBackground(Color.white);
		this.personTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.personTable.getColumnModel().getColumn(0).setHeaderValue(studentTableView.getColumnName(0));
		this.personTable.getColumnModel().getColumn(1).setHeaderValue(studentTableView.getColumnName(1));
		this.personTable.getColumnModel().getColumn(2).setHeaderValue(studentTableView.getColumnName(2));
		this.personTable.getColumnModel().getColumn(3).setHeaderValue(studentTableView.getColumnName(3));
		this.personTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		this.personTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		this.personTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		this.personTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.personTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.personTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.setAreaTypeTableRowHeight();
		this.scrollPane = new JScrollPane(personTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		search.setBounds(10, 70, 175, 30);
		addPerson.setBounds(600, 66, 115, 40);
		updatePerson.setBounds(743, 66, 115, 40);
		deletePerson.setBounds(883, 66, 115, 40);
		searchBox.setBounds(195, 70, 354, 30);
		list.setBounds(10, 129, 196, 30);
		totalCount.setBounds(211, 131, 84, 30);
		scrollPane.setBounds(10, 170, 1000, 243);
		errorIcon.setBounds(10, 610, 32, 32);
		error.setBounds(40, 610, 800, 32);
		error.setForeground(Color.red);
		error.setFont(new Font("Times New Roman", Font.BOLD, 15));
		close.setBounds(980, 3, 32, 32);
		c.add(list);
		c.add(search);
		c.add(searchBox);
		c.add(totalCount);
		c.add(this.scrollPane);
		c.add(addPerson);
		c.add(deletePerson);
		c.add(updatePerson);

		detailsPanel = new JPanel();
		detailsPanel.setBounds(10, 424, 988, 188);
		getContentPane().add(detailsPanel);
		detailsPanel.setLayout(null);

		lblName = new JLabel("Student Name:");
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblName.setBounds(20, 25, 157, 26);
		detailsPanel.add(lblName);

		lblcontactNo = new JLabel("Student Contact No:");
		lblcontactNo.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblcontactNo.setBounds(20, 72, 166, 26);
		detailsPanel.add(lblcontactNo);

		lblId = new JLabel("Student Id:");
		lblId.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblId.setBounds(514, 25, 155, 26);
		detailsPanel.add(lblId);

		lblOther = new JLabel("Student YOA:");
		lblOther.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblOther.setBounds(514, 72, 191, 26);
		detailsPanel.add(lblOther);

		txtName = new JTextField();
		txtName.setBounds(196, 24, 220, 30);
		txtName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		detailsPanel.add(txtName);
		txtName.setColumns(10);

		txtContactNo = new JTextField();
		txtContactNo.setColumns(10);
		txtContactNo.setBounds(196, 71, 220, 30);
		txtContactNo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		detailsPanel.add(txtContactNo);

		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(715, 24, 220, 30);
		txtId.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtId.setEnabled(false);
		detailsPanel.add(txtId);

		txtOther = new JTextField();
		txtOther.setColumns(10);
		txtOther.setBounds(715, 71, 220, 30);
		txtOther.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		detailsPanel.add(txtOther);

		lblNameErr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		lblNameErr.setForeground(new Color(192, 192, 192));
		lblNameErr.setBounds(426, 25, 30, 30);
		lblNameErr.setVisible(false);
		detailsPanel.add(lblNameErr);

		lblContactNoErr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		lblContactNoErr.setForeground(new Color(192, 192, 192));
		lblContactNoErr.setBounds(426, 68, 30, 30);
		lblContactNoErr.setVisible(false);
		detailsPanel.add(lblContactNoErr);

		lblOtherErr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		lblOtherErr.setForeground(new Color(192, 192, 192));
		lblOtherErr.setBounds(945, 72, 30, 30);
		lblOtherErr.setVisible(false);
		detailsPanel.add(lblOtherErr);

		save = new JButton();
		save.setText("  Save");
		cancel = new JButton();
		cancel.setText(" Cancel");
		save.setBounds(349, 134, 115, 40);
		cancel.setBounds(497, 134, 115, 40);
		save.setIcon(new ImageIcon(PersonMaintenance.class.getResource("/resources/save_icon.png")));
		save.setFont(new Font("Times New Roman", Font.BOLD, 14));
		cancel.setIcon(new ImageIcon(PersonMaintenance.class.getResource("/resources/close_icon.png")));
		cancel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		detailsPanel.add(save);
		detailsPanel.add(cancel);

		errorIcon.setVisible(false);
		this.setSize(1020, 658);
		TitledBorder tb = new TitledBorder(new EtchedBorder(), "Details");
		tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
		detailsPanel.setBorder(tb);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);

		this.setTitle("Person Maintenance - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());
		this.addListeners();
		this.setDefaults(false);
		try {
			updateRecordCount();
		} catch (CustomException ex) {
		}
		// this.setModal(true);
		this.setVisible(true);
	}
// Till Here
	public void addListeners() {
		addPerson.addActionListener(this);
		deletePerson.addActionListener(this);
		updatePerson.addActionListener(this);
		save.addActionListener(this);
		cancel.addActionListener(this);
		personTable.getSelectionModel().addListSelectionListener(this);
		searchBox.getDocument().addDocumentListener(this);
		studentCheckBox.addItemListener(getCheckboxItemListener("Student"));
		facultyCheckBox.addItemListener(getCheckboxItemListener("Faculty"));
	}

	// Method to create an ItemListener for a given category
	private ItemListener getCheckboxItemListener(final String categoryName) {
		// Return an ItemListener for the given category
		return new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && categoryName.compareTo("Student") == 0) {
					isStudent = true;
				} else {
					isStudent = false;
				}
				System.out.println(isStudent);
				repaintTable();
			}

		};
	}

	public void repaintTable() {
		try {
			AbstractTableModel model;
			String str = "";
			if (isStudent) {
				studentTableView = new StudentTableView(studentController);
				model = studentTableView;
				list.setText("Total Student Count :");
				search.setText("Search Student:");
				lblcontactNo.setText("Student Contact No:");
				lblName.setText("Student Name:");
				lblId.setText("Student Id:");
				lblOther.setText("Student YOA:");
			} else {
				facultyTableView = new FacultyTableView(facultyController);
				model = facultyTableView;
				list.setText("Total Faculty Count :");
				search.setText("Search Faculty:");
				lblcontactNo.setText("Faculty Contact No:");
				lblName.setText("Faculty Name:");
				lblId.setText("Faculty Id:");
				lblOther.setText("Faculty Department:");
			}
			setDefaults(false);
			TitledBorder tb = new TitledBorder(new EtchedBorder(), isStudent ? "Student" : "Faculty" + " Details");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
			detailsPanel.setBorder(tb);
			personTable.setModel(model);
			setAreaTypeTableRowHeight();
			personTable.repaint();
			personTable.setBackground(Color.white);
			this.personTable.getColumnModel().getColumn(0).setPreferredWidth(80);
			this.personTable.getColumnModel().getColumn(0).setHeaderValue(model.getColumnName(0));
			this.personTable.getColumnModel().getColumn(1).setHeaderValue(model.getColumnName(1));
			this.personTable.getColumnModel().getColumn(2).setHeaderValue(model.getColumnName(2));
			this.personTable.getColumnModel().getColumn(3).setHeaderValue(model.getColumnName(3));
			this.personTable.getColumnModel().getColumn(1).setPreferredWidth(400);
			this.personTable.getColumnModel().getColumn(2).setPreferredWidth(300);
			this.personTable.getColumnModel().getColumn(3).setPreferredWidth(200);
			this.personTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			this.personTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.personTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
			this.setAreaTypeTableRowHeight();
			this.updateRecordCount();
		} catch (CustomException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void setAreaTypeTableRowHeight() {
		for (int x = 0; x < this.personTable.getRowCount(); x++) {
			this.personTable.setRowHeight(x, 25);
		}
	}

	private void updateRecordCount() throws CustomException {
		AbstractTableModel model = isStudent ? studentTableView : facultyTableView;
		this.totalCount.setText(String.valueOf(model.getRowCount()));
	}

	void setDefaults(boolean value) {
		txtName.setEditable(value);
		txtOther.setEditable(value);
		txtContactNo.setEditable(value);
		txtId.setText("");
		txtName.setText("");
		txtOther.setText("");
		txtContactNo.setText("");
		searchBox.setText("");
		save.setEnabled(value);
		cancel.setEnabled(value);
		lblContactNoErr.setVisible(value);
		lblNameErr.setVisible(value);
		lblOtherErr.setVisible(value);
		if (isStudent) {

		} else {

		}
	}

	void setValues(Person person) {
		Student student;
		Faculty faculty;
		if (isStudent) {
			student = (Student) person;
			txtId.setText(Integer.toString(student.getStudentId()));
			txtName.setText(student.getName());
			txtContactNo.setText(Long.toString(student.getContact()));
			txtOther.setText(Integer.toString(student.getYear()));
		} else {
			faculty = (Faculty) person;
			txtId.setText(Integer.toString(faculty.getEmployeeId()));
			txtName.setText(faculty.getName());
			txtContactNo.setText(Long.toString(faculty.getContact()));
			txtOther.setText(faculty.getDepartment());
		}

	}

	void setEditable() {
		txtName.setEditable(true);
		txtOther.setEditable(true);
		txtContactNo.setEditable(true);
		save.setEnabled(true);
		cancel.setEnabled(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		int selectedRowIndex = this.personTable.getSelectedRow();
		if (selectedRowIndex < 0 || selectedRowIndex >= this.personTable.getRowCount()) {
			setDefaults(false);
		} else {
			try {
				if (isStudent) {
					Student student = studentController
							.getStudent((String) studentTableView.getValueAt(selectedRowIndex, 0));
					setValues(student);
				} else {
					Faculty faculty = facultyController
							.getFaculty((String) facultyTableView.getValueAt(selectedRowIndex, 0));
					setValues(faculty);
				}

			} catch (CustomException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				setDefaults(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				setDefaults(false);
			}
		}

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			search(searchBox.getText().trim(), 0, Direction.FORWARD, false);
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			search(searchBox.getText().trim(), 0, Direction.BACKWARD, false);
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addPerson) {
			try {
				personTable.clearSelection();
				isAdding = true;
				setDefaults(true);
				txtId.setText("");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		} else if (ae.getSource() == updatePerson) {
			int selectedRowIndex = this.personTable.getSelectedRow();
			if (selectedRowIndex == -1) {
				JOptionPane.showMessageDialog(this, "Select a record to edit.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				isAdding = false;
				setEditable();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		} else if (ae.getSource() == deletePerson) {
			int selectedRowIndex = this.personTable.getSelectedRow();
			if (selectedRowIndex == -1) {
				JOptionPane.showMessageDialog(this, "Select an record to delete.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Person person = null;
			try {
				String id = (String) personTable.getValueAt(selectedRowIndex, 0);
				if (isStudent) {
					person = studentController.getStudent(id);
				} else {
					person = facultyController.getFaculty(id);
				}

			} catch (CustomException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = "Are you sure? You want to delete the selected " + (isStudent ? "Student" : "Faculty");
			int selection = JOptionPane.showConfirmDialog(this, message, "Delete", JOptionPane.YES_NO_OPTION);

			if (selection == JOptionPane.YES_OPTION) {
				try {
					if (isStudent) {
						Student student = (Student) person;
						studentController.deleteStudent(student);
						studentTableView.fireTableDataChanged();
					} else {
						Faculty faculty = (Faculty) person;
						facultyController.deleteFaculty(faculty);
						facultyTableView.fireTableDataChanged();
					}
					this.setAreaTypeTableRowHeight();
					this.personTable.repaint();
					errorIcon.setVisible(false);
					error.setText("Record deleted!!");
					error.setForeground(Color.blue);
					updateRecordCount();
					repaintTable();
					setDefaults(false);
				} catch (CustomException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			return;
		} else if (ae.getSource() == save) {
			int flag = 0;
			if (txtName.getText().trim().length() == 0) {
				flag++;
				lblNameErr.setVisible(true);
			} else {
				lblNameErr.setVisible(false);
			}
			if (txtContactNo.getText().trim().length() == 0) {
				flag++;
				lblContactNoErr.setVisible(true);
			} else {
				lblContactNoErr.setVisible(false);
			}
			if (txtOther.getText().trim().length() == 0) {
				flag++;
				lblOtherErr.setVisible(true);
			} else {
				lblOtherErr.setVisible(false);
			}
			if (flag == 0) {
				try {
					if (isStudent) {
						Student student = new Student();
						student.setName(txtName.getText().trim());
						student.setContact(Long.parseLong(txtContactNo.getText().trim()));
						student.setYear(Integer.parseInt(txtOther.getText().trim()));
						if (isAdding) {
							studentController.addStudent(student);
							JOptionPane.showMessageDialog(this, "Successfully New Student added!", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							student.setStudentId(Integer.parseInt(txtId.getText().trim()));
							studentController.updateStudent(student);
							JOptionPane.showMessageDialog(this, "Successfully Student details updated!", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
						}
						studentTableView.fireTableDataChanged();
						setAreaTypeTableRowHeight();
						personTable.repaint();
						errorIcon.setVisible(false);
						error.setText("Record Added!!");
						error.setForeground(Color.blue);
						updateRecordCount();
						repaintTable();
						setDefaults(false);
					} else {
						Faculty faculty = new Faculty();
						faculty.setName(txtName.getText().trim());
						faculty.setContact(Long.parseLong(txtContactNo.getText().trim()));
						faculty.setDepartment(txtOther.getText().trim());
						if (isAdding) {
							facultyController.addFaculty(faculty);
							JOptionPane.showMessageDialog(this, "Successfully New Faculty added!", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							faculty.setEmployeeId(Integer.parseInt(txtId.getText().trim()));
							facultyController.updateFaculty(faculty);
							JOptionPane.showMessageDialog(this, "Successfully Faculty details updated!", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
						}
						facultyTableView.fireTableDataChanged();
						setAreaTypeTableRowHeight();
						personTable.repaint();
						errorIcon.setVisible(false);
						error.setText("Record Added!!");
						error.setForeground(Color.blue);
						updateRecordCount();
						repaintTable();
						setDefaults(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

		} else if (ae.getSource() == cancel) {
			personTable.clearSelection();
			setDefaults(false);
		}

	}

	private void search(String electronicUnitName, int fromIndex, Direction direction, boolean isSelected)
			throws CustomException {
		if (electronicUnitName.length() == 0) {

			this.personTable.clearSelection();
			return;
		}

		if (personTable.getRowCount() == 0) {
			return;
		}
		AbstractTableModel model;
		if (isStudent) {
			model = studentTableView;
		} else {
			model = facultyTableView;
		}

		if (direction == Direction.FORWARD) {
			for (int x = fromIndex; x < model.getRowCount(); x++) {
				if (((String) model.getValueAt(x, 0)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(this.personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 1)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 2)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 3)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				}

			}
		}
		if (direction == Direction.BACKWARD) {
			for (int x = fromIndex; x >= 0; x--) {
				if (((String) model.getValueAt(x, 0)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 1)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 2)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				} else if (((String) model.getValueAt(x, 3)).toUpperCase().contains(electronicUnitName.toUpperCase())) {
					personTable.setRowSelectionInterval(x, x);
					personTable.scrollRectToVisible(personTable.getCellRect(x, x, false));
					return;
				}

			}
		}
		if (!isSelected) {
			this.personTable.clearSelection();
		}
	}
}
