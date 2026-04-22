package front;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Admin_login {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	Connection conn;
	Statement st;
	JLabel lblStatus;
	JFrame frame;
	JButton btnLogin;

	public Admin_login() {

		try {
			String url = "jdbc:mysql://localhost:3306/my_project";
			conn = DriverManager.getConnection(url, "root", "");
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		frame = new JFrame();
		ImageIcon icon = new ImageIcon("C:\\Users\\DATA NETR\\eclipse-workspace\\Parking\\src\\front\\bmw-420d-2-removebg-preview.png");
		frame.setIconImage(icon.getImage());
		frame.setTitle("Parking Management System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 420, 560);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(15, 23, 42));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(160, 30, 80, 80);
		try {
			java.awt.image.BufferedImage img = javax.imageio.ImageIO
					.read(new java.net.URL("https://cdn-icons-png.flaticon.com/512/1048/1048313.png"));
			lblLogo.setIcon(new ImageIcon(img.getScaledInstance(72, 72, Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			lblLogo.setText("\uD83C\uDD7F");
			lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 40));
			lblLogo.setForeground(Color.WHITE);
		}
		contentPane.add(lblLogo);
		JLabel lblTitle = new JLabel("Parking System");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(250, 204, 21));
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitle.setBounds(60, 125, 290, 35);
		contentPane.add(lblTitle);
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(148, 163, 184));
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblUsername.setBounds(60, 185, 290, 15);
		contentPane.add(lblUsername);
		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(30, 41, 59));
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setCaretColor(Color.WHITE);
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsername.setBorder(BorderFactory.createLineBorder(new Color(99, 102, 241), 1));
		txtUsername.setBounds(60, 205, 290, 38);
		txtUsername.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		contentPane.add(txtUsername);
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(148, 163, 184));
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPassword.setBounds(60, 260, 290, 15);
		contentPane.add(lblPassword);
		txtPassword = new JPasswordField();
		txtPassword.setBackground(new Color(30, 41, 59));
		txtPassword.setForeground(Color.WHITE);
		txtPassword.setCaretColor(Color.WHITE);
		txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtPassword.setBorder(BorderFactory.createLineBorder(new Color(99, 102, 241), 1));
		txtPassword.setBounds(60, 280, 290, 38);
		txtPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		contentPane.add(txtPassword);
		lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblStatus.setBounds(60, 335, 290, 20);
		contentPane.add(lblStatus);
		btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(99, 102, 241));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnLogin.setFocusPainted(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.setBounds(60, 370, 290, 42);
		contentPane.add(btnLogin);
		frame.setVisible(true);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setBackground(new Color(79, 70, 229));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(new Color(99, 102, 241));
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
				try {
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, txtUsername.getText().trim());
					ps.setString(2, new String(txtPassword.getPassword()));
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						lblStatus.setForeground(new Color(74, 222, 128));
						lblStatus.setText("Login successful!");
						Timer timer = new Timer(300, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
								new main_interface();
							}
						});

						timer.setRepeats(false);
						timer.start();
					} else {
						lblStatus.setForeground(new Color(248, 113, 113));
						lblStatus.setText("Wrong username or password.");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					lblStatus.setForeground(new Color(248, 113, 113));
					lblStatus.setText("Database connection error.");
				}
			}
		});

	}

	public void login_method() {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, txtUsername.getText().trim());
			ps.setString(2, new String(txtPassword.getPassword()));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				lblStatus.setForeground(new Color(74, 222, 128));
				lblStatus.setText("Login successful!");
				System.exit(0);
			} else {
				lblStatus.setForeground(new Color(248, 113, 113));
				lblStatus.setText("Wrong username or password.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			lblStatus.setForeground(new Color(248, 113, 113));
			lblStatus.setText("Database connection error.");
		}
	}
}
