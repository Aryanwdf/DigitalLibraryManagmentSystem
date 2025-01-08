package com.dls.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import com.dls.exception.CustomException;
import com.dls.interfaces.BookControllerInterface;
import com.dls.ui.tabledataview.BookTableView;

public class BookMaintenance extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

	private JButton addBook, delete, update;
	public static boolean isAdding = false;
	private JTable booksTable;
	private JScrollPane scrollPane;
	private BookTableView bookModel;
	private JTextField searchBox;
	private JLabel search, list, totalCount, close, heading, error, errorIcon;
	private Container c;
	private BookDetailPanel bookDetailsPanel;
	private LibraryMenu libraryMenu;
	private BookControllerInterface bookController;

	public enum Direction {
		FORWARD, BACKWARD
	};

	public BookMaintenance(LibraryMenu libraryMenu, BookControllerInterface bookController) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.bookController = bookController;
		this.libraryMenu = libraryMenu;
		this.setUndecorated(false);
		close = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent me) {
				BookMaintenance.this.libraryMenu.setVisible(true);
				BookMaintenance.this.libraryMenu.setParentActive();
				BookMaintenance.this.dispose();

			}
		});

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		heading = new JLabel("Digital Library Management System - Book Maintenance");
		heading.setFont(new Font("Times New Roman", Font.BOLD, 26));
		bookDetailsPanel = new BookDetailPanel();
		getContentPane().setLayout(null);
		c = this.getContentPane();
		c.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		search = new JLabel("Search Book:");
		search.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/find_icon.png")));
		search.setFont(new Font("Times New Roman", Font.BOLD, 20));

		totalCount = new JLabel("");
		totalCount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		list = new JLabel("Total Book Count : ");
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchBox = new JTextField("");

		addBook = new JButton("  Add");
		addBook.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/add_icon.png")));
		addBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
		error = new JLabel("");
		errorIcon = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

		delete = new JButton("  Delete");
		delete.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/trash_icon.png")));
		delete.setFont(new Font("Times New Roman", Font.BOLD, 14));

		update = new JButton(" Update");
		update.setIcon(new ImageIcon(BookMaintenance.class.getResource("/resources/edit_icon.png")));
		update.setFont(new Font("Times New Roman", Font.BOLD, 14));
		bookModel = new BookTableView(bookController);
		booksTable = new JTable(bookModel);
		booksTable.setBackground(Color.white);
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColumnName(2));
		this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColumnName(3));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.setAreaTypeTableRowHeight();
		this.scrollPane = new JScrollPane(booksTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(
				new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
		search.setBounds(10, 70, 175, 30);
		addBook.setBounds(600, 66, 115, 40);
		update.setBounds(743, 66, 115, 40);
		delete.setBounds(883, 66, 115, 40);
		searchBox.setBounds(195, 70, 354, 30);
		list.setBounds(10, 129, 196, 30);
		totalCount.setBounds(195, 131, 100, 30);
		scrollPane.setBounds(10, 170, 1000, 220);
		bookDetailsPanel.setBounds(10, 400, 1000, 200);
		errorIcon.setBounds(10, 610, 32, 32);
		error.setBounds(40, 610, 800, 32);
		error.setForeground(Color.red);
		error.setFont(new Font("Times New Roman", Font.BOLD, 15));
		close.setBounds(980, 3, 32, 32);
		heading.setBounds(185, 11, 715, 40);
		c.add(list);
		c.add(search);
		c.add(searchBox);
		c.add(totalCount);
		c.add(this.scrollPane);
		c.add(addBook);
		c.add(delete);
		c.add(update);
		c.add(bookDetailsPanel);
		c.add(heading);
		errorIcon.setVisible(false);
		this.setSize(1020, 640);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);

		this.setTitle("Books Maintenance - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());
		this.addListeners();
		try {
			updateBookCount();
		} catch (CustomException ex) {
		}
		// this.setModal(true);
		this.setVisible(true);
	}

	public void addListeners() {
		addBook.addActionListener(this);
		delete.addActionListener(this);
		update.addActionListener(this);
		booksTable.getSelectionModel().addListSelectionListener(this);
		searchBox.getDocument().addDocumentListener(this);
	}

	public void repaintTable() {
		bookModel = new BookTableView(bookController);
		booksTable.setModel(bookModel);
		setAreaTypeTableRowHeight();
		booksTable.repaint();
		booksTable.setBackground(Color.white);
		this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColumnName(0));
		this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColumnName(1));
		this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColumnName(2));
		this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColumnName(3));
		this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		this.booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		this.setAreaTypeTableRowHeight();
		try {
			this.updateBookCount();
		} catch (CustomException ex) {
		}

	}

	private void setAreaTypeTableRowHeight() {
		for (int x = 0; x < this.booksTable.getRowCount(); x++) {
			this.booksTable.setRowHeight(x, 25);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if (o == addBook) {
			try {
				booksTable.clearSelection();
				bookDetailsPanel.clear();
				isAdding = true;
				bookDetailsPanel.setDefaults(true);
				bookDetailsPanel.editFields();
				this.setState(false);
				bookDetailsPanel.idBox.setText("");
				bookDetailsPanel.idBox.setEditable(false);
				bookDetailsPanel.idBox.setBackground(new Color(GlobalResources.COLOR_CODE_R,
						GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));
				Date date = new Date();
				bookDetailsPanel.entryDateBox.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
				bookDetailsPanel.yearBox.setText("");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (o == update) {
			int selectedRowIndex = this.booksTable.getSelectedRow();
			if (selectedRowIndex == -1) {
				JOptionPane.showMessageDialog(this, "Select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				isAdding = false;
				bookDetailsPanel.setDefaults(true);
				bookDetailsPanel.editFields();
				this.setState(false);
				bookDetailsPanel.idBox.setEditable(false);
				bookDetailsPanel.idBox.setBackground(new Color(GlobalResources.COLOR_CODE_R,
						GlobalResources.COLOR_CODE_G, GlobalResources.COLOR_CODE_B));

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (o == delete) {
			int selectedRowIndex = this.booksTable.getSelectedRow();
			if (selectedRowIndex == -1) {
				JOptionPane.showMessageDialog(this, "Select an Entry to delete.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Book book = null;
			try {
				int id = Integer.parseInt((String) bookModel.getValueAt(selectedRowIndex, 0));
				book = bookController.getBook(id);
			} catch (CustomException CustomException) {
				// this case won't arise
			}

			int selection = JOptionPane.showConfirmDialog(this,
					"Delete : \n ID = " + book.getId() + "\nName=" + book.getName(), "Confirmation",
					JOptionPane.YES_NO_OPTION);

			if (selection == JOptionPane.YES_OPTION) {
				try {

					this.bookController.deleteBook(book);
					this.bookModel.fireTableDataChanged();
					this.setAreaTypeTableRowHeight();
					this.booksTable.repaint();
					errorIcon.setVisible(false);
					error.setText("book deleted!!");
					error.setForeground(Color.blue);
					updateBookCount();
					this.repaintTable();
					bookDetailsPanel.repaint();
					bookDetailsPanel.clear();
					searchBox.setText("");
				} catch (CustomException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) {
		try {
			// gets called when something gets inserted into the
			// document
			this.search(this.searchBox.getText().trim(), 0, Direction.FORWARD, false);
		} catch (CustomException ex) {
		}
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) {
		try {
			// gets called when something gets removed from the document
			this.search(this.searchBox.getText().trim(), 0, Direction.BACKWARD, false);
		} catch (CustomException ex) {
		}
	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) { // gets called when a
//set or set of attributes change
// In our application we don't want to do anything in this case
	}

	public void setState(boolean b) {
		addBook.setEnabled(b);
		update.setEnabled(b);
		delete.setEnabled(b);
		searchBox.setEnabled(b);
		searchBox.setText("");
		booksTable.setEnabled(b);
		error.setText("");
		errorIcon.setVisible(false);
	}

	private void updateBookCount() throws CustomException {
		this.totalCount.setText(String.valueOf(bookModel.getRowCount()));
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (bookDetailsPanel == null) {
			return;
		}
		int selectedRowIndex = this.booksTable.getSelectedRow();
		if (selectedRowIndex < 0 || selectedRowIndex >= this.booksTable.getRowCount()) {
			bookDetailsPanel.setDefaults(false);
		} else {
			try {
				Book book = bookController
						.getBook(Integer.parseInt((String) bookModel.getValueAt(selectedRowIndex, 0)));
				bookDetailsPanel.setBookDetails(book);
			} catch (CustomException CustomException) {

				bookDetailsPanel.setDefaults(false);
			}
		}

	}

	public void search(String id) throws CustomException {
		if (id.length() == 0) {
			this.booksTable.clearSelection();
			return;
		}

		for (int x = 0; x < bookModel.getRowCount(); x++) {
			if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase().startsWith(id.toUpperCase())) {
				this.booksTable.setRowSelectionInterval(x, x);
				this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
				return;
			}
		}
		this.booksTable.clearSelection();
	}

	private void search(String electronicUnitName, int fromIndex, Direction direction, boolean isSelected)
			throws CustomException {
		if (electronicUnitName.length() == 0) {

			this.booksTable.clearSelection();
			return;
		}

		if (booksTable.getRowCount() == 0) {
			return;
		}

		if (direction == Direction.FORWARD) {
			for (int x = fromIndex; x < bookModel.getRowCount(); x++) {
				if (((String) this.bookModel.getValueAt(x, 0)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 2)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				}

			}
		}
		if (direction == Direction.BACKWARD) {
			for (int x = fromIndex; x >= 0; x--) {
				if (((String) this.bookModel.getValueAt(x, 0)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 1)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				} else if (((String) this.bookModel.getValueAt(x, 2)).toUpperCase()
						.contains(electronicUnitName.toUpperCase())) {
					this.booksTable.setRowSelectionInterval(x, x);
					this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x, x, false));
					return;
				}

			}
		}
		if (!isSelected) {

			this.booksTable.clearSelection();
		}
	}

	class BookDetailPanel extends JPanel implements ActionListener {

		JLabel name, publisher, isbn, price, author, pages, remark, status, entryDate, id, year;
		JTextField nameBox, publisherBox, isbnBox, idBox, remarkBox, statusBox, entryDateBox, pagesBox, authorBox,
				priceBox, yearBox;
		JButton save, close;
		JLabel nameEr, publisherEr, isbnEr, idEr, remarkEr, statusEr, entryDateEr, pagesEr, authorEr, priceEr, yearEr;
		private final BookMaintenance bookMaintenance;

		BookDetailPanel() {
			this.setLayout(null);
			bookMaintenance = BookMaintenance.this;
			nameEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			isbnEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			publisherEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			priceEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			authorEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			remarkEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			pagesEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			entryDateEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			idEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			statusEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			yearEr = new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
			save = new JButton("Save");
			close = new JButton("Close");
			name = new JLabel("Name:");
			name.setFont(new Font("Times New Roman", Font.BOLD, 15));
			year = new JLabel("Year:");
			year.setFont(new Font("Times New Roman", Font.BOLD, 15));
			isbn = new JLabel("ISBN:");
			isbn.setFont(new Font("Times New Roman", Font.BOLD, 15));
			publisher = new JLabel("Publisher:");
			publisher.setFont(new Font("Times New Roman", Font.BOLD, 15));
			price = new JLabel("Price:");
			price.setFont(new Font("Times New Roman", Font.BOLD, 15));
			author = new JLabel("Author:");
			author.setFont(new Font("Times New Roman", Font.BOLD, 15));
			remark = new JLabel("Remark:");
			remark.setFont(new Font("Times New Roman", Font.BOLD, 15));
			pages = new JLabel("Pages:");
			pages.setFont(new Font("Times New Roman", Font.BOLD, 15));
			entryDate = new JLabel("Entry Date:");
			entryDate.setFont(new Font("Times New Roman", Font.BOLD, 15));
			id = new JLabel("ID:");
			id.setFont(new Font("Times New Roman", Font.BOLD, 15));
			status = new JLabel("Status:");
			status.setFont(new Font("Times New Roman", Font.BOLD, 15));
			nameBox = new JTextField("");
			nameBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			publisherBox = new JTextField("");
			publisherBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			idBox = new JTextField("");
			idBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			yearBox = new JTextField("");
			yearBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			isbnBox = new JTextField("");
			isbnBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			remarkBox = new JTextField("");
			remarkBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			statusBox = new JTextField("");
			statusBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			entryDateBox = new JTextField("");
			entryDateBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			pagesBox = new JTextField("");
			pagesBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			authorBox = new JTextField("");
			authorBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			priceBox = new JTextField("");
			authorBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			name.setBounds(10, 20, 70, 30);
			nameBox.setBounds(90, 20, 500, 30);
			nameEr.setBounds(590, 20, 30, 30);
			id.setBounds(10, 60, 70, 30);
			idBox.setBounds(90, 60, 100, 30);
			idEr.setBounds(190, 60, 30, 30);
			pages.setBounds(250, 60, 70, 30);
			pagesBox.setBounds(320, 60, 100, 30);
			pagesEr.setBounds(330, 60, 30, 30);
			isbn.setBounds(450, 60, 70, 30);
			isbnBox.setBounds(520, 60, 120, 30);
			isbnEr.setBounds(640, 60, 30, 30);
			publisher.setBounds(10, 100, 70, 30);
			publisherBox.setBounds(90, 100, 300, 30);
			publisherEr.setBounds(390, 100, 30, 30);
			status.setBounds(10, 140, 70, 30);
			statusBox.setBounds(90, 140, 100, 30);
			statusEr.setBounds(190, 140, 30, 30);
			entryDate.setBounds(230, 140, 100, 30);
			entryDateBox.setBounds(320, 140, 100, 30);
			entryDateEr.setBounds(420, 140, 30, 30);
			remark.setBounds(500, 140, 70, 30);
			remarkBox.setBounds(570, 140, 100, 30);
			remarkEr.setBounds(670, 140, 30, 30);
			year.setBounds(750, 140, 70, 30);
			yearBox.setBounds(820, 140, 100, 30);
			yearEr.setBounds(920, 140, 30, 30);
			author.setBounds(450, 100, 70, 30);
			authorBox.setBounds(520, 100, 300, 30);
			authorEr.setBounds(720, 100, 30, 30);
			price.setBounds(660, 20, 70, 30);
			priceBox.setBounds(740, 20, 100, 30);
			priceEr.setBounds(840, 20, 30, 30);
			save.setBounds(870, 20, 115, 40);
			close.setBounds(870, 70, 115, 40);
			save.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
			save.setFont(new Font("Times New Roman", Font.BOLD, 14));
			close.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/close_icon.png")));
			close.setFont(new Font("Times New Roman", Font.BOLD, 14));
			this.add(save);
			this.add(name);
			this.add(nameBox);
			this.add(price);
			this.add(priceBox);
			this.add(author);
			this.add(authorBox);
			this.add(publisher);
			this.add(publisherBox);
			this.add(remark);
			this.add(remarkBox);
			this.add(isbn);
			this.add(isbnBox);
			this.add(pages);
			this.add(pagesBox);
			this.add(entryDate);
			this.add(entryDateBox);
			this.add(status);
			this.add(statusBox);
			this.add(id);
			this.add(idBox);
			this.add(nameEr);
			this.add(priceEr);
			this.add(authorEr);
			this.add(publisherEr);
			this.add(remarkEr);
			this.add(isbnEr);
			this.add(pagesEr);
			this.add(entryDateEr);
			this.add(statusEr);
			this.add(idEr);
			this.add(year);
			this.add(yearBox);
			this.add(yearEr);
			this.add(close);
			this.save.addActionListener(this);
			close.addActionListener(this);
			this.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			this.setVisible(true);
			setDefaults(false);
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Book Details");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 18));
			this.setBorder(tb);
		}

		void setDefaults(boolean value) {

			this.nameBox.setEditable(value);
			this.nameBox.setBackground(new Color(240, 240, 240));
			idBox.setEditable(value);
			publisherBox.setEditable(value);
			priceBox.setEditable(value);
			isbnBox.setEditable(value);
			authorBox.setEditable(value);
			pagesBox.setEditable(value);
			entryDateBox.setEditable(value);
			remarkBox.setEditable(value);
			statusBox.setEditable(value);
			yearBox.setEditable(value);
			save.setVisible(value);
			close.setVisible(value);
			idBox.setBackground(new Color(240, 240, 240));
			publisherBox.setBackground(new Color(240, 240, 240));
			priceBox.setBackground(new Color(240, 240, 240));
			isbnBox.setBackground(new Color(240, 240, 240));
			pagesBox.setBackground(new Color(240, 240, 240));
			authorBox.setBackground(new Color(240, 240, 240));
			entryDateBox.setBackground(new Color(240, 240, 240));
			remarkBox.setBackground(new Color(240, 240, 240));
			statusBox.setBackground(new Color(240, 240, 240));
			yearBox.setBackground(new Color(240, 240, 240));
			idEr.setVisible(false);
			nameEr.setVisible(false);
			publisherEr.setVisible(false);
			priceEr.setVisible(false);
			pagesEr.setVisible(false);
			isbnEr.setVisible(false);
			remarkEr.setVisible(false);
			statusEr.setVisible(false);
			entryDateEr.setVisible(false);
			authorEr.setVisible(false);
			yearEr.setVisible(false);

		}

		public void setUpdateBookEntry() {
			int flag = 0;
			if (nameBox.getText().trim().equals("")) {
				flag++;
				nameEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				nameEr.setVisible(false);
			}
			if (publisherBox.getText().trim().equals("")) {

				flag++;
				publisherEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				publisherEr.setVisible(false);
			}
			if (isbnBox.getText().trim().equals("")) {
				flag++;
				isbnEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				isbnEr.setVisible(false);
			}
			if (statusBox.getText().trim().equals("")) {
				flag++;
				statusEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				statusEr.setVisible(false);
			}
			if (remarkBox.getText().trim().equals("")) {
				flag++;
				remarkEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				remarkEr.setVisible(false);
			}
			if (entryDateBox.getText().trim().equals("")) {
				flag++;
				entryDateEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				entryDateEr.setVisible(false);
			}
			if (authorBox.getText().trim().equals("")) {
				flag++;
				authorEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				authorEr.setVisible(false);
			}

			if (yearBox.getText().trim().equals("")) {
				flag++;
				yearEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				yearEr.setVisible(false);
			}
			if (priceBox.getText().trim().equals("")) {
				flag++;
				priceEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				priceEr.setVisible(false);
			}

			if (pagesBox.getText().trim().equals("")) {
				flag++;
				pagesEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				pagesEr.setVisible(false);
			}

			if (flag == 0) {
				try {
					Book book = new Book();
					book.setName(nameBox.getText().trim());
					book.setId(Integer.parseInt(idBox.getText().trim()));
					book.setYear(Integer.parseInt((yearBox.getText().trim())));
					book.setPrice(Double.parseDouble((priceBox.getText().trim())));
					book.setTotalPages(Integer.parseInt(pagesBox.getText().trim()));
					book.setPublisher(publisherBox.getText().trim());
					book.setISBN(isbnBox.getText().trim());
					book.setEntryDate(new SimpleDateFormat("YYYY-MM-DD").parse(entryDateBox.getText().trim()));
					book.setAuthor(authorBox.getText().trim());
					book.setRemark(remarkBox.getText().trim());
					book.setStatus(statusBox.getText().trim());
					bookController.updateBook(book);
					bookMaintenance.bookModel.fireTableDataChanged();
					bookMaintenance.setAreaTypeTableRowHeight();
					bookMaintenance.booksTable.repaint();
					bookMaintenance.updateBookCount();
					bookMaintenance.repaintTable();
					this.repaint();
					bookMaintenance.search(idBox.getText().trim(), 0, Direction.FORWARD, false);
					bookMaintenance.error.setText("Book updated!!");
					bookMaintenance.error.setForeground(Color.blue);
					bookMaintenance.errorIcon.setVisible(false);
					bookMaintenance.setState(true);
					JOptionPane.showMessageDialog(this, "Successfully Book Details Updated!!", "Notification",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					bookMaintenance.errorIcon.setVisible(true);
					bookMaintenance.error.setText(e.getMessage());
					bookMaintenance.error.setForeground(Color.red);
				}
			}
		}

		public void setNewBookEntry() {
			int flag = 0;
			if (nameBox.getText().trim().equals("")) {
				flag++;
				nameEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				nameEr.setVisible(false);
			}
			if (publisherBox.getText().trim().equals("")) {

				flag++;
				publisherEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				publisherEr.setVisible(false);
			}
			if (isbnBox.getText().trim().equals("")) {
				flag++;
				isbnEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				isbnEr.setVisible(false);
			}
			if (statusBox.getText().trim().equals("")) {
				flag++;
				statusEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				statusEr.setVisible(false);
			}
			if (remarkBox.getText().trim().equals("")) {
				flag++;
				remarkEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				remarkEr.setVisible(false);
			}
			if (entryDateBox.getText().trim().equals("")) {
				flag++;
				entryDateEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				entryDateEr.setVisible(false);
			}
			if (authorBox.getText().trim().equals("")) {
				flag++;
				authorEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				authorEr.setVisible(false);
			}

			if (yearBox.getText().trim().equals("")) {
				flag++;
				yearEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				yearEr.setVisible(false);
			}
			if (priceBox.getText().trim().equals("")) {
				flag++;
				priceEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				priceEr.setVisible(false);
			}

			if (pagesBox.getText().trim().equals("")) {
				flag++;
				pagesEr.setVisible(true);
			} else {
				if (flag > 0) {
					flag--;
				}
				pagesEr.setVisible(false);
			}

			if (flag == 0) {
				try {

					Book book = new Book();
					book.setName(nameBox.getText().trim());
					// book.setId(Integer.parseInt(idBox.getText().trim()));
					book.setYear(Integer.parseInt(yearBox.getText().trim()));
					book.setPrice(Double.parseDouble(priceBox.getText().trim()));
					book.setTotalPages(Integer.parseInt(pagesBox.getText().trim()));
					book.setPublisher(publisherBox.getText().trim());
					book.setISBN(isbnBox.getText().trim());
					book.setEntryDate(new SimpleDateFormat("YYYY-MM-DD").parse(entryDateBox.getText().trim()));
					book.setAuthor(authorBox.getText().trim());
					book.setRemark(remarkBox.getText().trim());
					book.setStatus(statusBox.getText().trim());
					bookMaintenance.bookController.addBook(book);
					bookMaintenance.bookModel.fireTableDataChanged();
					bookMaintenance.setAreaTypeTableRowHeight();
					bookMaintenance.booksTable.repaint();
					bookMaintenance.updateBookCount();
					bookMaintenance.repaintTable();
					this.repaint();
					bookMaintenance.search(idBox.getText().trim(), 0, Direction.FORWARD, false);
					bookMaintenance.error.setText("Book Added!!");
					bookMaintenance.error.setForeground(Color.blue);
					bookMaintenance.errorIcon.setVisible(false);
					clear();
					bookMaintenance.setState(true);
					JOptionPane.showMessageDialog(this, "Successfully New Book Added!!", "Notification",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					bookMaintenance.errorIcon.setVisible(true);
					bookMaintenance.error.setText(e.getMessage());
					bookMaintenance.error.setForeground(Color.red);
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			Object o = ae.getSource();
			if (o == save) {
				editFields();
				if (bookMaintenance.isAdding) {
					setNewBookEntry();
				} else {
					setUpdateBookEntry();
				}
			}

			if (o == close) {
				setDefaults(false);
				clear();
				booksTable.clearSelection();
				bookMaintenance.setState(true);
			}
		}

		public void clear() {
			nameBox.setText("");
			publisherBox.setText("");
			yearBox.setText("");
			priceBox.setText("");
			isbnBox.setText("");
			pagesBox.setText("");
			statusBox.setText("");
			remarkBox.setText("");
			authorBox.setText("");
			entryDateBox.setText("");
			idBox.setText("");
		}

		public void editFields() {
			this.nameBox.setBackground(Color.white);
			idBox.setBackground(Color.white);
			publisherBox.setBackground(Color.white);
			priceBox.setBackground(Color.white);
			isbnBox.setBackground(Color.white);
			pagesBox.setBackground(Color.white);
			authorBox.setBackground(Color.white);
			entryDateBox.setBackground(Color.white);
			remarkBox.setBackground(Color.white);
			statusBox.setBackground(Color.white);
			yearBox.setBackground(Color.white);
		}

		public void setBookDetails(Book book) {
			idBox.setText(Integer.toString(book.getId()));
			nameBox.setText(book.getName());
			publisherBox.setText(book.getPublisher());
			isbnBox.setText(book.getISBN());
			statusBox.setText(book.getStatus());
			remarkBox.setText(book.getRemark());
			authorBox.setText(book.getAuthor());
			priceBox.setText(Double.toString(book.getPrice()));
			pagesBox.setText(String.valueOf(book.getTotalPages()));
			entryDateBox.setText(book.getEntryDateStr());
			yearBox.setText(String.valueOf(book.getYear()));

		}

	}

}
