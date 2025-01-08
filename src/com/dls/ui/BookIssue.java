package com.dls.ui;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.dls.contstant.GlobalResources;
import com.dls.entity.Book;
import com.dls.entity.Faculty;
import com.dls.entity.Student;
import com.dls.interfaces.BookControllerInterface;
import com.dls.interfaces.FacultyControllerInterface;
import com.dls.interfaces.StudentControllerInterface;

public class BookIssue extends JFrame implements ActionListener, ItemListener, DocumentListener {

	private final JTextField b1, b2, b3, b4, b5, personIDBox, statusBox;
	private final JLabel bookID, personID, heading2, close, subHeading, error1, error2, list, listEr, b11, b22, b33,
			b44, b55, pEr, status, result, FindPersonName;
	private final List qty;
	private final CheckboxGroup person;
	private final Checkbox studentCheckBox, facultyCheckbox;
	private final JButton btnSave, btnClear, btnFindBook, btnFindPerson;
	private ArrayList<Book> books = null;
	private LibraryMenu libraryMenu;
	private final BookControllerInterface bookController;
	private final StudentControllerInterface studentController;
	private final FacultyControllerInterface facultyController;
	private JButton exit;
	private JLabel lblPersonType;

	public BookIssue(LibraryMenu libraryMenu, BookControllerInterface bookController,
			StudentControllerInterface studentController, FacultyControllerInterface facultyController) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		this.bookController = bookController;
		this.studentController = studentController;
		this.facultyController = facultyController;
		this.libraryMenu = libraryMenu;
		libraryMenu.setVisible(false);
		getContentPane().setLayout(null);
		this.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		person = new CheckboxGroup();
		studentCheckBox = new Checkbox("Student", person, true);
		studentCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		facultyCheckbox = new Checkbox("Faculty", person, false);
		facultyCheckbox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		this.setUndecorated(false);
		statusBox = new JTextField("");
		statusBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		status = new JLabel("Book Id:");
		status.setFont(new Font("Times New Roman", Font.BOLD, 20));
		result = new JLabel(".");
		result.setFont(new Font("Times New Roman", Font.BOLD, 20));
		FindPersonName = new JLabel(".");
		btnFindBook = new JButton("   Find");
		btnFindPerson = new JButton("   Find");
		btnFindBook.setIcon(new ImageIcon(BookIssue.class.getResource("/resources/find_icon.png")));
		btnFindBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSave = new JButton("    Save");
		btnSave.setIcon(new ImageIcon(BookIssue.class.getResource("/resources/save_icon.png")));
		btnSave.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnClear = new JButton("   Clear");
		btnClear.setIcon(new ImageIcon(BookIssue.class.getResource("/resources/clear_icon.png")));
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 14));
		close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent me) {
				BookIssue.this.libraryMenu.setVisible(true);
				BookIssue.this.libraryMenu.setParentActive();
				BookIssue.this.dispose();
			}
		});

		personID = new JLabel("Person Id:");
		personID.setFont(new Font("Times New Roman", Font.BOLD, 20));
		bookID = new JLabel("Book Id:");
		bookID.setFont(new Font("Times New Roman", Font.BOLD, 20));
		heading2 = new JLabel("Digital Library Management System - Book Issue");
		subHeading = new JLabel("Fill the Details to Issue Books - Max 5 books");
		subHeading.setFont(new Font("Times New Roman", Font.BOLD, 21));
		heading2.setFont(new Font("Times New Roman", Font.BOLD, 26));
		error2 = new JLabel("");
		error2.setForeground(Color.red);
		error1 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		b11 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		b22 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		b33 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		b44 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		b55 = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		listEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		pEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
		pEr.setForeground(new Color(192, 192, 192));

		list = new JLabel("Quantity:");
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		getContentPane().setLayout(null);
		b1 = new JTextField("");
		b2 = new JTextField("");
		b3 = new JTextField("");
		b4 = new JTextField("");
		b5 = new JTextField("");
		personIDBox = new JTextField("");
		qty = new List(3, true);
		heading2.setBounds(18, 11, 569, 30);
		status.setBounds(30, 70, 86, 30);
		statusBox.setBounds(120, 69, 100, 35);
		btnFindBook.setBounds(230, 66, 116, 40);
		result.setBounds(447, 70, 132, 30);
		subHeading.setBounds(101, 117, 444, 30);
		personID.setBounds(30, 259, 100, 30);

		bookID.setBounds(447, 177, 80, 30);
		b1.setBounds(431, 218, 100, 30);
		b11.setBounds(541, 218, 30, 30);
		b2.setBounds(431, 259, 100, 30);
		b22.setBounds(541, 259, 30, 30);
		b3.setBounds(431, 300, 100, 30);
		b33.setBounds(541, 300, 30, 30);
		b4.setBounds(431, 341, 100, 30);
		b44.setBounds(541, 341, 30, 30);
		b5.setBounds(431, 382, 100, 30);
		b55.setBounds(541, 382, 30, 30);
		personIDBox.setBounds(145, 259, 100, 30);
		pEr.setBounds(255, 259, 30, 30);
		studentCheckBox.setBounds(170, 169, 121, 25);
		facultyCheckbox.setBounds(170, 218, 115, 25);
		list.setBounds(30, 352, 100, 30);
		listEr.setBounds(261, 352, 30, 30);
		qty.setBounds(145, 349, 100, 63);
		btnSave.setBounds(93, 436, 115, 40);
		btnClear.setBounds(230, 436, 115, 40);
		close.setBounds(460, 3, 32, 32);
		error1.setBounds(30, 300, 32, 32);
		error2.setBounds(60, 300, 500, 30);
		qty.add("1");
		qty.add("2");
		qty.add("3");
		qty.add("4");
		qty.add("5");
		getContentPane().add(btnClear);
		getContentPane().add(btnFindBook);
		getContentPane().add(result);
		getContentPane().add(status);
		getContentPane().add(statusBox);
		getContentPane().add(btnSave);
		getContentPane().add(pEr);
		getContentPane().add(b11);
		getContentPane().add(b22);
		getContentPane().add(b33);
		getContentPane().add(b44);
		getContentPane().add(b55);
//this.add(error1);
//this.add(error2);
		getContentPane().add(heading2);
		getContentPane().add(list);
		getContentPane().add(listEr);
//this.add(close);
		getContentPane().add(subHeading);
		getContentPane().add(qty);
		getContentPane().add(b1);
		getContentPane().add(b2);
		getContentPane().add(b3);
		getContentPane().add(b4);
		getContentPane().add(b5);
		getContentPane().add(bookID);
		getContentPane().add(personID);
		getContentPane().add(list);
		getContentPane().add(facultyCheckbox);
		getContentPane().add(studentCheckBox);
		getContentPane().add(personIDBox);
		b11.setVisible(false);
		b22.setVisible(false);
		b33.setVisible(false);
		b44.setVisible(false);
		b55.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);
		pEr.setVisible(false);
		listEr.setVisible(false);
		this.setSize(621, 522);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);
		this.setTitle("Book Issue - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());
		error1.setVisible(false);
		addListeners();
		qty.setMultipleMode(false);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblStatus.setBounds(365, 70, 86, 30);
		getContentPane().add(lblStatus);

		exit = new JButton("  Close");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookIssue.this.libraryMenu.setVisible(true);
				BookIssue.this.libraryMenu.setParentActive();
				BookIssue.this.dispose();
			}
		});
		exit.setIcon(new ImageIcon(BookIssue.class.getResource("/resources/close_icon.png")));
		exit.setHorizontalAlignment(SwingConstants.TRAILING);
		exit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		exit.setBounds(369, 436, 115, 40);
		getContentPane().add(exit);

		lblPersonType = new JLabel("Person Type:");
		lblPersonType.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPersonType.setBounds(30, 195, 125, 30);
		getContentPane().add(lblPersonType);

		FindPersonName.setFont(new Font("Times New Roman", Font.BOLD, 20));
		FindPersonName.setBounds(30, 300, 378, 30);
		getContentPane().add(FindPersonName);

		btnFindPerson.setIcon(new ImageIcon(BookIssue.class.getResource("/resources/find_icon.png")));
		btnFindPerson.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnFindPerson.setBounds(301, 255, 116, 40);
		getContentPane().add(btnFindPerson);

		qty.select(0);
		// qty.setBackground(new Color(225, 176, 98));
		this.setVisible(true);

	}

	public void addListeners() {
		btnSave.addActionListener(this);
		btnClear.addActionListener(this);
		qty.addItemListener(this);
		btnFindBook.addActionListener(this);
		statusBox.getDocument().addDocumentListener(this);
		btnFindPerson.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnClear) {
			b11.setVisible(false);
			b22.setVisible(false);
			b33.setVisible(false);
			b44.setVisible(false);
			b55.setVisible(false);
			b2.setVisible(false);
			b3.setVisible(false);
			b4.setVisible(false);
			b5.setVisible(false);
			pEr.setVisible(false);
			listEr.setVisible(false);
			error1.setVisible(false);
			error2.setVisible(false);
			listSelect();
			b1.setVisible(true);
			qty.setMultipleMode(false);
			qty.select(0);
			personIDBox.setText("");
			b1.setText("");
			statusBox.setText("");
			result.setText("");
		}

		if (ae.getSource() == btnSave) {
			int flag = 0;
			try {
				if (personIDBox.getText().trim().equals("")) {
					pEr.setVisible(true);
					throw new Exception("Person ID Required");
				} else {
					error1.setVisible(false);
					error2.setText("");
					pEr.setVisible(false);
				}

				int num = qty.getSelectedIndex() + 1;

				if (num == 1) {
					if (b1.getText().trim().equals("")) {
						flag++;
						b11.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b11.setVisible(false);
					}
					if (flag != 0) {
						throw new Exception("Entries required");
					}
					if (flag == 0) {
						int id = Integer.parseInt(b1.getText().trim());
						if (!(bookController.getBook(id).getStatus().trim().equalsIgnoreCase("A")
								|| bookController.getBook(id).getStatus().trim().equalsIgnoreCase("Available"))) {
							throw new Exception(b1.getText().trim() + " is not available currently");
						}
						books = new ArrayList<>();
						books.add(bookController.getBook(id));
					}
				}
				if (num == 2) {
					if (b1.getText().trim().equals("")) {
						flag++;
						b11.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b11.setVisible(false);
					}
					if (b2.getText().trim().equals("")) {
						flag++;
						b22.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b22.setVisible(false);
					}

					if (flag != 0) {
						throw new Exception("Entries required");
					}
					if (flag == 0) {
						int id = Integer.parseInt(b1.getText().trim());
						int id2 = Integer.parseInt(b2.getText().trim());
						if (!(bookController.getBook(id).getStatus().equals("A")
								|| bookController.getBook(id).getStatus().equals("Available")
								|| bookController.getBook(id).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b1.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id2).getStatus().equals("A")
								|| bookController.getBook(id2).getStatus().equals("Available")
								|| bookController.getBook(id2).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b2.getText().trim() + " is not available currently");
						}
						books = new ArrayList<>();
						books.add(bookController.getBook(id));
						books.add(bookController.getBook(id2));
					}
				}
				if (num == 3) {
					if (b1.getText().trim().equals("")) {
						flag++;
						b11.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b11.setVisible(false);
					}
					if (b2.getText().trim().equals("")) {
						flag++;
						b22.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b22.setVisible(false);
					}
					if (b3.getText().trim().equals("")) {
						flag++;
						b33.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b33.setVisible(false);
					}

					if (flag != 0) {
						throw new Exception("Entries required");
					}
					if (flag == 0) {
						int id1 = Integer.parseInt(b1.getText().trim());
						int id2 = Integer.parseInt(b2.getText().trim());
						int id3 = Integer.parseInt(b3.getText().trim());
						if (!(bookController.getBook(id1).getStatus().equals("A")
								|| bookController.getBook(id1).getStatus().equals("Available")
								|| bookController.getBook(id1).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b1.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id2).getStatus().equals("A")
								|| bookController.getBook(id2).getStatus().equals("Available")
								|| bookController.getBook(id2).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b2.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id3).getStatus().equals("A")
								|| bookController.getBook(id3).getStatus().equals("Available")
								|| bookController.getBook(id3).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b3.getText().trim() + " is not available currently");
						}
						books = new ArrayList<>();
						books.add(bookController.getBook(id1));
						books.add(bookController.getBook(id2));
						books.add(bookController.getBook(id3));

					}
				}
				if (num == 4) {
					if (b1.getText().trim().equals("")) {
						flag++;
						b11.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b11.setVisible(false);
					}
					if (b2.getText().trim().equals("")) {
						flag++;
						b22.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b22.setVisible(false);
					}
					if (b3.getText().trim().equals("")) {
						flag++;
						b33.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b33.setVisible(false);
					}
					if (b4.getText().trim().equals("")) {
						flag++;
						b44.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b44.setVisible(false);
					}

					if (flag != 0) {
						throw new Exception("Entries required");
					}

					if (flag == 0) {
						int id1 = Integer.parseInt(b1.getText().trim());
						int id2 = Integer.parseInt(b2.getText().trim());
						int id3 = Integer.parseInt(b3.getText().trim());
						int id4 = Integer.parseInt(b4.getText().trim());
						if (!(bookController.getBook(id1).getStatus().equals("A")
								|| bookController.getBook(id1).getStatus().equals("Available")
								|| bookController.getBook(id1).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b1.getText().trim() + " is not available currently");
						}

						if (!(bookController.getBook(id2).getStatus().equals("A")
								|| bookController.getBook(id2).getStatus().equals("Available")
								|| bookController.getBook(id2).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b2.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id3).getStatus().equals("A")
								|| bookController.getBook(id3).getStatus().equals("Available")
								|| bookController.getBook(id3).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b3.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id4).getStatus().equals("A")
								|| bookController.getBook(id4).getStatus().equals("Available")
								|| bookController.getBook(id4).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b4.getText().trim() + "is not available currently");
						}
						books = new ArrayList<>();
						books.add(bookController.getBook(id1));
						books.add(bookController.getBook(id2));
						books.add(bookController.getBook(id3));
						books.add(bookController.getBook(id4));

					}
				}
				if (num == 5) {
					if (b1.getText().trim().equals("")) {
						flag++;
						b11.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b11.setVisible(false);
					}
					if (b2.getText().trim().equals("")) {
						flag++;
						b22.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b22.setVisible(false);
					}
					if (b3.getText().trim().equals("")) {
						flag++;
						b33.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b33.setVisible(false);
					}
					if (b4.getText().trim().equals("")) {
						flag++;
						b44.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b44.setVisible(false);
					}
					if (b5.getText().trim().equals("")) {
						flag++;
						b55.setVisible(true);
					} else {
						if (flag > 0) {
							flag--;
						}
						b55.setVisible(false);
					}

					if (flag != 0) {
						throw new Exception("Entries required");
					}
					if (flag == 0) {
						int id1 = Integer.parseInt(b1.getText().trim());
						int id2 = Integer.parseInt(b2.getText().trim());
						int id3 = Integer.parseInt(b3.getText().trim());
						int id4 = Integer.parseInt(b4.getText().trim());
						int id5 = Integer.parseInt(b5.getText().trim());
						if (!(bookController.getBook(id1).getStatus().equals("A")
								|| bookController.getBook(id1).getStatus().equals("Available")
								|| bookController.getBook(id1).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b1.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id2).getStatus().equals("A")
								|| bookController.getBook(id2).getStatus().equals("Available")
								|| bookController.getBook(id2).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b2.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id3).getStatus().equals("A")
								|| bookController.getBook(id3).getStatus().equals("Available")
								|| bookController.getBook(id3).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b3.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id4).getStatus().equals("A")
								|| bookController.getBook(id4).getStatus().equals("Available")
								|| bookController.getBook(id4).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b4.getText().trim() + " is not available currently");
						}
						if (!(bookController.getBook(id5).getStatus().equals("A")
								|| bookController.getBook(id5).getStatus().equals("Available")
								|| bookController.getBook(id5).getStatus().equals("AVAILABLE"))) {
							throw new Exception(b5.getText().trim() + " is not available currently");
						}

						books = new ArrayList<>();

						books.add(bookController.getBook(id1));
						books.add(bookController.getBook(id2));
						books.add(bookController.getBook(id3));
						books.add(bookController.getBook(id4));
						books.add(bookController.getBook(id5));

					}
				}

				if (studentCheckBox.getState()) {

					studentController.issueBook(studentController.getStudent(personIDBox.getText().trim()), books);

				} else {
					facultyController.issueBook(facultyController.getFaculty(personIDBox.getText().trim()), books);
				}

				for (int i = 0; i < books.size(); i++) {
					Book bk = books.get(i);
					bk.setStatus("Issued");
					bookController.updateBook(bk);
				}

				b11.setVisible(false);
				b22.setVisible(false);
				b33.setVisible(false);
				b44.setVisible(false);
				b55.setVisible(false);
				b2.setVisible(false);
				b3.setVisible(false);
				b4.setVisible(false);
				b5.setVisible(false);
				pEr.setVisible(false);
				listEr.setVisible(false);
				error1.setVisible(false);
				error2.setVisible(false);
				listSelect();
				b1.setVisible(true);
				qty.setMultipleMode(false);
				qty.select(0);
				b1.setText("");
				statusBox.setText("");
				result.setText("");
				error1.setVisible(false);
				error2.setVisible(true);
				error2.setText("Books Issued to " + personIDBox.getText().trim() + " .");
				personIDBox.setText("");
				JOptionPane.showMessageDialog(this, "Successfully Issued Books!!", "Notification",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				error1.setVisible(true);
				error2.setVisible(true);
				error2.setText(e.getMessage());
				e.printStackTrace();
			}
		}

		if (ae.getSource() == btnFindBook) {
			try {
				if (statusBox.getText().trim().equals("")) {
					throw new Exception("Book ID required");
				}
				int id = Integer.parseInt(statusBox.getText().trim());
				result.setText(bookController.getBook(id).getStatus());
				error1.setVisible(false);
				error2.setText("");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				error1.setVisible(true);
				error2.setVisible(true);
				error2.setText(e.getMessage());
				e.printStackTrace();
			}
		}
		if (ae.getSource() == btnFindPerson) {
			try {
				String erroMessage = "";
				if (studentCheckBox.getState()) {
					erroMessage = "Student ID Required";
				} else {
					erroMessage = "Faculty ID Required";
				}
				if (personIDBox.getText().trim().equals("")) {
					pEr.setVisible(true);
					throw new Exception(erroMessage);
				} else {
					error1.setVisible(false);
					error2.setText("");
					pEr.setVisible(false);
				}
				String personName = "";
				if (studentCheckBox.getState()) {
					Student student = studentController.getStudent(personIDBox.getText().trim());
					personName = "Student Name : " + student.getName();

				} else {
					Faculty faculty = facultyController.getFaculty(personIDBox.getText().trim());
					personName = "Faculty Name : " + faculty.getName();
				}
				FindPersonName.setText(personName);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				error1.setVisible(true);
				error2.setVisible(true);
				error2.setText(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		int value = qty.getSelectedIndex();

		listSelect();
		if (value == 0) {
			b1.setVisible(true);
		}
		if (value == 1) {
			b1.setVisible(true);
			b2.setVisible(true);
		}
		if (value == 2) {
			b1.setVisible(true);
			b2.setVisible(true);
			b3.setVisible(true);

		}
		if (value == 3) {
			b1.setVisible(true);
			b2.setVisible(true);
			b3.setVisible(true);
			b4.setVisible(true);

		}
		if (value == 4) {
			b1.setVisible(true);
			b2.setVisible(true);
			b3.setVisible(true);
			b4.setVisible(true);
			b5.setVisible(true);

		}

	}

	public void listSelect() {
		b11.setVisible(false);
		b22.setVisible(false);
		b33.setVisible(false);
		b44.setVisible(false);
		b55.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);
		b1.setVisible(false);
		b1.setText("");
		b2.setText("");
		b3.setText("");
		b4.setText("");
		b5.setText("");
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) { // gets called when something gets inserted into the
		// document
		result.setText("");

	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) { // gets called when something gets removed from the document
		result.setText("");

	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) { // gets called when a
//set or set of attributes change
// In our application we don't want to do anything in this case
	}
}
