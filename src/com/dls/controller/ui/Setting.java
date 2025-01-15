package com.dls.ui;

import com.dls.contstant.GlobalResources;
import com.dls.interfaces.AdminControllerInterface;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Setting extends JFrame implements ItemListener {
	private LibraryMenu libraryMenu;
	private JLabel heading;
	private CheckboxGroup checkBoxGroup;
	private ChangeAdminPassword cap;
	private ChangeAdminUsername cau;
	private Checkbox usernameCheckBox, passwordCheckBox;
	private Container container;
	public Setting(LibraryMenu libraryMenu, AdminControllerInterface adminController) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		container = getContentPane();
		container.setLayout(null);
		cau = new ChangeAdminUsername(adminController);
		cap = new ChangeAdminPassword(adminController);

		this.libraryMenu = libraryMenu;
		libraryMenu.setVisible(false);
		heading = new JLabel("Change Username/Password");
		heading.setFont(new Font("Times New Roman", Font.BOLD, 26));
		checkBoxGroup = new CheckboxGroup();
		usernameCheckBox = new Checkbox("Change Username", checkBoxGroup, true);
		passwordCheckBox = new Checkbox("Change Password", checkBoxGroup, false);
		this.heading.setBounds(75, 11, 427, 40);
		this.usernameCheckBox.setBounds(39, 67, 182, 30);
		this.passwordCheckBox.setBounds(275, 67, 203, 30);
		this.cau.setBounds(10, 100, 480, 230);
		this.cap.setBounds(10, 100, 480, 230);
		this.usernameCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.passwordCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.container.add(usernameCheckBox);
		this.container.add(passwordCheckBox);
		this.container.add(cau);
		this.container.add(heading);
		this.cau.setVisible(true);
		this.cap.setVisible(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Setting.this.libraryMenu.setVisible(true);
				Setting.this.dispose();
				Setting.this.libraryMenu.setParentActive();
			}
		});

		this.setSize(522, 394);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2);
		this.setResizable(false);
		this.setTitle("Setting - " + GlobalResources.APPLICATION_NAME);
		this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.LOGO)).getImage());
		usernameCheckBox.addItemListener(this);
		passwordCheckBox.addItemListener(this);
		this.setResizable(false);
		container.setBackground(new Color(240, 240, 240));
		this.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {

		if (ie.getSource() == usernameCheckBox) {
			cau.setVisible(true);
			cap.setVisible(false);
			this.remove(cap);
			getContentPane().add(cau);
		}

		if (ie.getSource() == passwordCheckBox) {
			this.remove(cau);
			getContentPane().add(cap);
			cau.setVisible(false);
			cap.setVisible(true);
		}
		cau.repaint();
		cap.repaint();
		cap.refreshALL();
		cau.refreshALL();
		this.repaint();
	}

	class ChangeAdminPassword extends JPanel implements ActionListener {
		private JPasswordField oldPassword, newPassword, reTypePassword;
		private JButton save, exit;
		private JLabel oldPasswordLabel, newPasswordLabel, reTypePasswordLabel;
		private Container c;
		private AdminControllerInterface adminController;

		ChangeAdminPassword(AdminControllerInterface adminController) {
			this.setLayout(null);
			this.adminController = adminController;
			oldPassword = new JPasswordField();
			newPassword = new JPasswordField();
			reTypePassword = new JPasswordField();
			oldPasswordLabel = new JLabel("Current Password:");
			newPasswordLabel = new JLabel("New Password:");
			reTypePasswordLabel = new JLabel("ReType Password:");
			oldPasswordLabel.setOpaque(true);
			newPasswordLabel.setOpaque(true);
			reTypePasswordLabel.setOpaque(true);
			newPasswordLabel.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			oldPasswordLabel.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			reTypePasswordLabel.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			newPasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			oldPasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			reTypePasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			oldPassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			newPassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			reTypePassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			save = new JButton("Save");
			exit = new JButton("Clear");
			setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			oldPasswordLabel.setBounds(20, 30, 170, 30);
			newPasswordLabel.setBounds(20, 75, 170, 30);
			reTypePasswordLabel.setBounds(20, 115, 170, 30);
			oldPassword.setBounds(200, 30, 200, 30);
			newPassword.setBounds(200, 75, 200, 30);
			reTypePassword.setBounds(200, 115, 200, 30);
			save.setBounds(70, 170, 115, 40);
			exit.setBounds(260, 170, 115, 40);
			save.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
			save.setFont(new Font("Times New Roman", Font.BOLD, 14));
			exit.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/clear_icon.png")));
			exit.setFont(new Font("Times New Roman", Font.BOLD, 14));
			add(newPasswordLabel);
			add(oldPasswordLabel);
			add(reTypePassword);
			add(reTypePasswordLabel);
			add(newPassword);
			add(oldPassword);
			add(save);
			add(exit);
			save.addActionListener(this);
			exit.addActionListener(this);
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Change Password");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
			this.setBorder(tb);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == save) {
				try {
					if (adminController.checkPassword(String.valueOf(oldPassword.getText()).trim())) {
						if (String.valueOf(newPassword.getText()).trim().equals("") == false && confirm(
								String.valueOf(newPassword.getText()), String.valueOf(reTypePassword.getText()))) {
							adminController.updatePassword(String.valueOf(newPassword.getText()).trim());
							JOptionPane.showMessageDialog(this, "Successfully Updated!!", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
							oldPassword.setText("");
							newPassword.setText("");
							reTypePassword.setText("");
						} else {
							JOptionPane.showMessageDialog(this, "new Password & ReType Password are Miss matched!!",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Invalid Current Password !!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			} else if (ae.getSource() == exit) {
				refreshALL();
			}
		}

		public boolean confirm(String newPassword, String reTypePassword) {
			char[] n = newPassword.toCharArray();
			char[] r = reTypePassword.toCharArray();
			if (n.length == r.length) {
				for (int i = 0; i < n.length; i++) {
					if (n[i] != r[i]) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}

		public void refreshALL() {
			oldPassword.setText("");
			newPassword.setText("");
			reTypePassword.setText("");
		}
	}

	class ChangeAdminUsername extends JPanel implements ActionListener {
		private JTextField oldUsername, newUsername;
		private JButton save, exit;
		private JLabel oldUsernameLabel, newUsernameLabel;
		private Container c;
		private AdminControllerInterface adminController;

		public ChangeAdminUsername(AdminControllerInterface adminController) {
			this.setLayout(null);

			this.adminController = adminController;
			oldUsername = new JTextField();
			newUsername = new JTextField();
			oldUsernameLabel = new JLabel("Current Username:");
			newUsernameLabel = new JLabel("New Username:");
			oldUsernameLabel.setOpaque(true);
			newUsernameLabel.setOpaque(true);
			oldUsername.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			newUsername.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			oldUsernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			newUsernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
			newUsernameLabel.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			oldUsernameLabel.setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			save = new JButton("Save");
			exit = new JButton("Clear");
			save.setBounds(70, 170, 115, 40);
			exit.setBounds(260, 170, 115, 40);
			oldUsernameLabel.setBounds(20, 30, 170, 30);
			newUsernameLabel.setBounds(20, 75, 170, 30);
			oldUsername.setBounds(200, 30, 200, 30);
			newUsername.setBounds(200, 75, 200, 30);
			save.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/save_icon.png")));
			save.setFont(new Font("Times New Roman", Font.BOLD, 14));
			exit.setIcon(new ImageIcon(BookReturn.class.getResource("/resources/clear_icon.png")));
			exit.setFont(new Font("Times New Roman", Font.BOLD, 14));
			setBackground(new Color(GlobalResources.COLOR_CODE_R, GlobalResources.COLOR_CODE_G,
					GlobalResources.COLOR_CODE_B));
			add(newUsernameLabel);
			add(oldUsernameLabel);
			add(newUsername);
			add(oldUsername);
			add(save);
			add(exit);

			save.addActionListener(this);
			exit.addActionListener(this);
			TitledBorder tb = new TitledBorder(new EtchedBorder(), "Change Username");
			tb.setTitleFont(new Font("Times New Roman", Font.BOLD, 17));
			this.setBorder(tb);

		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == save) {
				try {
					if (adminController.checkUsername(oldUsername.getText().trim())
							&& newUsername.getText().trim().equals("") == false) {
						adminController.updateUsername(newUsername.getText().trim());
						JOptionPane.showMessageDialog(this, "Successfully Updated!!", "Notification",
								JOptionPane.INFORMATION_MESSAGE);
						oldUsername.setText("");
						newUsername.setText("");

					} else {
						JOptionPane.showMessageDialog(this, "Invalid Current Username !!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (ae.getSource() == exit) {
				refreshALL();
			}

		}

		public void refreshALL() {
			oldUsername.setText("");
			newUsername.setText("");
		}
	}

}
