package front;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import exceptions.SpotNotAvailableException;
import exceptions.TicketNotFoundException;
import models.spots.CompactSpot;
import models.spots.ElectricSpot;
import models.spots.LargeSpot;
import models.spots.RegularSpot;
import models.spots.Spots;
import models.tickets.Tickets;
import models.vehicls.Vehicle;
import services.EntryRegistration;
import services.ExitRegistration;
import services.SpotManagement;

public class main_interface extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/my_project";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "";

	private SpotManagement spotMgmt;
	private EntryRegistration entryReg;
	private final ExitRegistration exitReg = new ExitRegistration();
	private static final Color BG = new Color(245, 246, 250);
	private static final Color SIDEBAR_BG = new Color(28, 32, 46);
	private static final Color HEADER_BG = new Color(20, 23, 35);
	private static final Color ACCENT = new Color(99, 179, 237);
	private static final Color WHITE = Color.WHITE;
	private static final Color CARD_DARK = new Color(44, 62, 80);
	private static final Color CARD_BLUE = new Color(52, 152, 219);
	private static final Color CARD_RED = new Color(231, 76, 60);
	private static final Color CARD_GREEN = new Color(39, 174, 96);
	private static final Color TEXT_DARK = new Color(44, 62, 80);
	private static final Color TEXT_MID = new Color(100, 116, 139);

	private static final int SIDEBAR_W = 180;
	private static final int HEADER_H = 58;
	private JPanel contentPane;
	private JPanel header;
	private JPanel sidebar;
	private JPanel content;
	private JLabel clock;
	private JButton[] navBtns;

	public main_interface() {
		try {
			spotMgmt = new SpotManagement();
			entryReg = new EntryRegistration(spotMgmt);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Cannot connect to database:\n" + ex.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		ImageIcon icon = new ImageIcon(
				"C:\\Users\\DATA NETR\\eclipse-workspace\\Parking\\src\\front\\bmw-420d-2-removebg-preview.png");
		setIconImage(icon.getImage());
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int W = screen.width;
		int H = screen.height;
		setTitle("Parking Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, W, H);
		setMinimumSize(new Dimension(900, 600));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(BG);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		header = new JPanel();
		header.setBackground(HEADER_BG);
		header.setLayout(null);
		header.setBounds(0, 0, W, HEADER_H);
		contentPane.add(header);

		JLabel appTitle = new JLabel("🚗  Parking Management System");
		appTitle.setForeground(WHITE);
		appTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
		appTitle.setBounds(SIDEBAR_W + 10, 0, 420, HEADER_H);
		header.add(appTitle);

		clock = new JLabel();
		clock.setForeground(new Color(160, 174, 192));
		clock.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		clock.setHorizontalAlignment(SwingConstants.RIGHT);
		clock.setBounds(W - 270, 20, 250, 18);
		header.add(clock);
		setVisible(true);
		new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy   HH:mm:ss")));
			}
		}).start();
		sidebar = new JPanel();
		sidebar.setBackground(SIDEBAR_BG);
		sidebar.setLayout(null);
		sidebar.setBounds(0, HEADER_H, SIDEBAR_W, H - HEADER_H);
		contentPane.add(sidebar);

		JPanel logoStrip = new JPanel();
		logoStrip.setBackground(new Color(15, 17, 28));
		logoStrip.setLayout(null);
		logoStrip.setBounds(0, 0, SIDEBAR_W, 48);
		sidebar.add(logoStrip);

		JLabel logoLbl = new JLabel("P  MANAGER");
		logoLbl.setForeground(ACCENT);
		logoLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		logoLbl.setBounds(18, 14, 150, 20);
		logoStrip.add(logoLbl);
		content = new JPanel();
		content.setBackground(BG);
		content.setLayout(null);
		content.setBounds(SIDEBAR_W, HEADER_H, W - SIDEBAR_W, H - HEADER_H);
		contentPane.add(content);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int fw = getContentPane().getWidth();
				int fh = getContentPane().getHeight();
				header.setBounds(0, 0, fw, HEADER_H);
				clock.setBounds(fw - 270, 20, 250, 18);
				sidebar.setBounds(0, HEADER_H, SIDEBAR_W, fh - HEADER_H);
				content.setBounds(SIDEBAR_W, HEADER_H, fw - SIDEBAR_W, fh - HEADER_H);
				content.revalidate();
				content.repaint();
			}
		});
		String[] navLabels = { "📊   Dashboard", "🚘   Vehicle Entry", "🏁   Vehicle Exit", "🔍   Search",
				"🔓   Logout" };
		int[] navY = { 60, 110, 160, 210, 380 };
		navBtns = new JButton[navLabels.length];

		for (int i = 0; i < navLabels.length; i++) {
			JButton btn = makeSidebarBtn(navLabels[i]);
			btn.setBounds(0, navY[i], SIDEBAR_W, 40);
			sidebar.add(btn);
			navBtns[i] = btn;
			final int idx = i;
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					highlightNav(navBtns, idx);
					handleNav(idx);
				}
			});
		}

		highlightNav(navBtns, 0);
		showDashboard();
	}

	private void handleNav(int idx) {
		switch (idx) {
		case 0:
			showDashboard();
			break;
		case 1:
			showVehicleEntry();
			break;
		case 2:
			showVehicleExit();
			break;
		case 3:
			showSearch();
			break;
		case 4: {
			int ok = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
					JOptionPane.YES_NO_OPTION);
			if (ok == JOptionPane.YES_OPTION) {
				dispose();
				new Admin_login();
			}
			break;
		}
		}
	}

	private void showDashboard() {
		content.removeAll();

		int cw = Math.max(content.getWidth(), 640);
		int ch = Math.max(content.getHeight(), 480);

		addPageTitle(content, "Dashboard", "Live overview of your parking lot");

		java.util.List<Spots> allSpots = spotMgmt.getSpots();
		long total = allSpots.size();
		long occupied = allSpots.stream().filter(Spots::isOccupied).count();
		long available = total - occupied;
		int pct = total == 0 ? 0 : (int) ((occupied * 100.0) / total);
		double earnings = queryTotalEarnings();
		int cardCount = 4;
		int cardMargin = 20;
		int cardW = (cw - cardMargin * (cardCount + 1)) / cardCount;
		int cardH = 110;
		int cardY = 85;

		Object[][] cardData = { { "Vehicles Parked", String.valueOf(occupied), "🚗", CARD_DARK },
				{ "Spots Occupied", occupied + " / " + total, "📍", CARD_BLUE },
				{ "Available Spots", String.valueOf(available), "✅", CARD_GREEN },
				{ "Total Earnings", String.format("%.2f", earnings), "💰", CARD_RED } };

		for (int i = 0; i < cardData.length; i++) {
			int cx = cardMargin + i * (cardW + cardMargin);
			JPanel card = makeStatCard((String) cardData[i][0], (String) cardData[i][1], (String) cardData[i][2],
					(Color) cardData[i][3]);
			card.setBounds(cx, cardY, cardW, cardH);
			content.add(card);
		}
		int barY = cardY + cardH + 16;
		int barW = cw - 2 * cardMargin - 60;

		JLabel capLbl = new JLabel("Lot Capacity");
		capLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		capLbl.setForeground(TEXT_DARK);
		capLbl.setBounds(cardMargin, barY, 200, 20);
		content.add(capLbl);

		JLabel pctLbl = new JLabel(pct + "%");
		pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		pctLbl.setForeground(pct > 80 ? CARD_RED : CARD_GREEN);
		pctLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		pctLbl.setBounds(cw - cardMargin - 55, barY, 55, 20);
		content.add(pctLbl);

		JPanel barBg = new JPanel();
		barBg.setBackground(new Color(220, 225, 235));
		barBg.setLayout(null);
		barBg.setBounds(cardMargin, barY + 24, barW, 12);
		content.add(barBg);

		if (pct > 0) {
			JPanel fill = new JPanel();
			fill.setBackground(pct > 80 ? CARD_RED : CARD_GREEN);
			fill.setBounds(0, 0, (int) (barW * pct / 100.0), 12);
			barBg.add(fill);
		}
		int tblY = barY + 50;
		int tblH = ch - tblY - 20;

		JLabel tblLbl = new JLabel("Active Tickets");
		tblLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		tblLbl.setForeground(TEXT_DARK);
		tblLbl.setBounds(cardMargin, tblY, 300, 20);
		content.add(tblLbl);

		String[] cols = { "Ticket ID", "Spot", "Ticket Type", "Entry Time" };
		JTable table = makeTable(queryActiveTickets(), cols);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(cardMargin, tblY + 26, cw - 2 * cardMargin, Math.max(tblH, 120));
		sp.setBorder(new LineBorder(new Color(210, 215, 230)));
		content.add(sp);

		content.revalidate();
		content.repaint();
	}

	private void showVehicleEntry() {
		content.removeAll();

		int cw = Math.max(content.getWidth(), 640);
		int margin = 20;
		int formW = cw - 2 * margin;

		addPageTitle(content, "Vehicle Entry", "Register a new vehicle entering the lot");

		JPanel form = makeFormCard(margin, 90, formW, 290);
		content.add(form);

		// Row 1
		formLabel(form, "License Plate (numeric)", 30, 22);
		final JTextField plateField = formTextField(form, 30, 48, 200);

		formLabel(form, "Vehicle Model", 260, 22);
		final JComboBox<String> vehicleCombo = new JComboBox<>(new String[] { "Car", "Motorcycle", "Truck", "Van" });
		styleCombo(vehicleCombo);
		vehicleCombo.setBounds(260, 48, 160, 32);
		form.add(vehicleCombo);
		formLabel(form, "Spot Type", 30, 100);
		final JComboBox<String> spotCombo = new JComboBox<>(new String[] { "Regular", "Compact", "Electric", "Large" });
		styleCombo(spotCombo);
		spotCombo.setBounds(30, 126, 160, 32);
		form.add(spotCombo);

		formLabel(form, "Ticket Type", 220, 100);
		final JComboBox<String> ticketCombo = new JComboBox<>(
				new String[] { "HOURLY (50DZ/hr)", "DAILY (200DZ/day)", "MONTHLY (4000DZ/mo)" });
		styleCombo(ticketCombo);
		ticketCombo.setBounds(220, 126, 210, 32);
		form.add(ticketCombo);

		final JLabel availLbl = new JLabel();
		availLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		availLbl.setForeground(TEXT_MID);
		availLbl.setBounds(460, 110, 220, 55);
		availLbl.setText(getSpotAvailabilitySummary());
		form.add(availLbl);

		JButton submitBtn = makeBtn("✔  Register Entry", CARD_GREEN);
		submitBtn.setBounds(30, 220, 200, 38);
		form.add(submitBtn);

		JButton clearBtn = makeBtn("Clear", new Color(170, 180, 195));
		clearBtn.setForeground(TEXT_DARK);
		clearBtn.setBounds(242, 220, 100, 38);
		form.add(clearBtn);
		final JPanel resultBox = makeFormCard(margin, 395, formW, 72);
		resultBox.setBackground(new Color(236, 252, 243));
		resultBox.setBorder(new LineBorder(CARD_GREEN));
		resultBox.setVisible(false);
		content.add(resultBox);

		final JLabel ticketIdLbl = new JLabel();
		ticketIdLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		ticketIdLbl.setForeground(new Color(22, 120, 70));
		ticketIdLbl.setBounds(12, 8, formW - 24, 24);
		resultBox.add(ticketIdLbl);

		JLabel saveLbl = new JLabel("⚠  Note this Ticket ID — you will need it to process the vehicle exit.");
		saveLbl.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		saveLbl.setForeground(TEXT_MID);
		saveLbl.setBounds(12, 36, formW - 24, 18);
		resultBox.add(saveLbl);

		final JLabel status = new JLabel(" ");
		status.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		status.setBounds(margin, 480, formW, 18);
		content.add(status);

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plateField.setText("");
				resultBox.setVisible(false);
				status.setText(" ");
			}
		});

		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultBox.setVisible(false);
				String plateStr = plateField.getText().trim();
				if (plateStr.isEmpty()) {
					setStatus(status, "⚠  Please enter a license plate.", CARD_RED);
					return;
				}
				int plate;
				try {
					plate = Integer.parseInt(plateStr);
				} catch (NumberFormatException ex) {
					setStatus(status, "⚠  License plate must be a number (e.g. 12345).", CARD_RED);
					return;
				}
				String vehicleType = (String) vehicleCombo.getSelectedItem();
				String ticketType = ((String) ticketCombo.getSelectedItem()).split(" ")[0];
				Class<? extends Spots> spotClass = resolveSpotClass((String) spotCombo.getSelectedItem());
				try {
					Vehicle vehicle = createVehicle(vehicleType, plate);
					Tickets ticket = entryReg.registerEntry(vehicle, spotClass, ticketType);
					if (ticket == null) {
						setStatus(status, "⚠  Vehicle " + plate + " is already registered in the lot.", CARD_RED);
					} else {
						String spotId = (vehicle.getSpot() != null) ? vehicle.getSpot().getSpotID() : "N/A";
						ticketIdLbl.setText("✔  Entry registered!   Ticket ID: " + ticket.getTicketId()
								+ "   |   Spot: " + spotId + "   |   Type: " + ticketType);
						resultBox.setVisible(true);
						plateField.setText("");
						availLbl.setText(getSpotAvailabilitySummary());
						status.setText(" ");
					}
				} catch (SpotNotAvailableException ex) {
					setStatus(status, "⚠  No available " + spotCombo.getSelectedItem() + " spot.", CARD_RED);
				} catch (ClassNotFoundException ex) {
					setStatus(status, "⚠  Vehicle class 'models.vehicls." + vehicleType + "' not found.", CARD_RED);
				} catch (Exception ex) {
					setStatus(status, "⚠  " + ex.getMessage(), CARD_RED);
					ex.printStackTrace();
				}
				content.revalidate();
				content.repaint();
			}
		});

		content.revalidate();
		content.repaint();
	}

	private void showVehicleExit() {
		content.removeAll();

		int cw = Math.max(content.getWidth(), 640);
		int margin = 20;
		int formW = cw - 2 * margin;

		addPageTitle(content, "Vehicle Exit", "Process a vehicle leaving the lot");

		JPanel form = makeFormCard(margin, 90, formW, 310);
		content.add(form);

		formLabel(form, "Ticket ID", 30, 22);
		final JTextField ticketField = formTextField(form, 30, 48, 200);

		JButton lookupBtn = makeBtn("🔍  Lookup", ACCENT);
		lookupBtn.setBounds(242, 48, 140, 32);
		form.add(lookupBtn);
		final JPanel detailBox = new JPanel();
		detailBox.setBackground(new Color(240, 245, 252));
		detailBox.setLayout(null);
		detailBox.setBorder(new LineBorder(new Color(190, 210, 235)));
		detailBox.setBounds(30, 100, formW - 60, 100);
		detailBox.setVisible(false);
		form.add(detailBox);

		final JLabel detailLbl = new JLabel();
		detailLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		detailLbl.setForeground(TEXT_MID);
		detailLbl.setBounds(10, 8, formW - 80, 20);
		detailBox.add(detailLbl);

		final JLabel feeLbl = new JLabel();
		feeLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
		feeLbl.setForeground(CARD_GREEN);
		feeLbl.setBounds(10, 34, formW - 80, 52);
		detailBox.add(feeLbl);

		final JButton exitBtn = makeBtn("✔  Confirm Exit & Collect Fee", CARD_RED);
		exitBtn.setBounds(30, 230, 280, 38);
		exitBtn.setEnabled(false);
		form.add(exitBtn);

		final JLabel status = new JLabel(" ");
		status.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		status.setBounds(margin, 415, formW, 18);
		content.add(status);

		final int[] resolvedId = { 0 };

		ActionListener doLookup = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detailBox.setVisible(false);
				exitBtn.setEnabled(false);
				resolvedId[0] = 0;
				String ts = ticketField.getText().trim();
				if (ts.isEmpty()) {
					setStatus(status, "⚠  Enter a Ticket ID.", CARD_RED);
					return;
				}
				int tid;
				try {
					tid = Integer.parseInt(ts);
				} catch (NumberFormatException ex) {
					setStatus(status, "⚠  Ticket ID must be numeric.", CARD_RED);
					return;
				}
				try {
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM tickets WHERE ticket_id = " + tid);
					if (!rs.next()) {
						setStatus(status, "⚠  Ticket #" + tid + " not found.", CARD_RED);
						conn.close();
						return;
					}
					if (rs.getTimestamp("exit_time") != null) {
						setStatus(status, "⚠  Ticket #" + tid + " is already closed.", CARD_RED);
						conn.close();
						return;
					}
					String ticketType = rs.getString("type");
					String spotId = rs.getString("spot_id");
					int feeRate = rs.getInt("fee");
					LocalDateTime entry = rs.getTimestamp("entry_time").toLocalDateTime();
					detailLbl.setText("Ticket Type: " + ticketType.replace("Ticket", "") + "   |   Spot: " + spotId
							+ "   |   Entry: " + entry.format(DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy"))
							+ "   |   " + formatDuration(entry));
					feeLbl.setText("Estimated Fee:  $" + estimateFee(feeRate, entry) + "  (rate: $" + feeRate + " / "
							+ (feeRate == 50 ? "hr" : feeRate == 200 ? "day" : "month") + ")");
					detailBox.setVisible(true);
					exitBtn.setEnabled(true);
					resolvedId[0] = tid;
					status.setText(" ");
					form.revalidate();
					form.repaint();
					conn.close();
				} catch (SQLException ex) {
					setStatus(status, "⚠  DB error: " + ex.getMessage(), CARD_RED);
				}
			}
		};

		lookupBtn.addActionListener(doLookup);
		ticketField.addActionListener(doLookup);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double collected = exitReg.ExitRegistration(resolvedId[0]);
					setStatus(status, "✔  Vehicle exited (ticket #" + resolvedId[0] + ").   Fee collected: DZD"
							+ String.format("%.2f", collected), CARD_GREEN);
					ticketField.setText("");
					detailBox.setVisible(false);
					exitBtn.setEnabled(false);
					spotMgmt.refreshSpots();
				} catch (TicketNotFoundException ex) {
					setStatus(status, "⚠  " + ex.getMessage(), CARD_RED);
				} catch (Exception ex) {
					setStatus(status, "⚠  " + ex.getMessage(), CARD_RED);
					ex.printStackTrace();
				}
			}
		});

		content.revalidate();
		content.repaint();
	}

	// ══════════════════════════════════════════════════════
	// SEARCH
	// ══════════════════════════════════════════════════════
	private void showSearch() {
		content.removeAll();

		int cw = Math.max(content.getWidth(), 640);
		int ch = Math.max(content.getHeight(), 480);
		int margin = 20;
		int innerW = cw - 2 * margin;

		addPageTitle(content, "Search", "Find tickets by plate number or spot ID");

		final JTextField searchField = new JTextField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		searchField.setBounds(margin, 88, innerW - 160, 36);
		searchField
				.setBorder(new CompoundBorder(new LineBorder(new Color(200, 210, 225)), new EmptyBorder(0, 10, 0, 10)));
		content.add(searchField);

		JButton searchBtn = makeBtn("🔍  Search", ACCENT);
		searchBtn.setBounds(cw - margin - 150, 88, 150, 36);
		content.add(searchBtn);

		final JLabel resultCount = new JLabel(" ");
		resultCount.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		resultCount.setForeground(TEXT_MID);
		resultCount.setBounds(margin, 134, innerW, 18);
		content.add(resultCount);

		String[] cols = { "Ticket ID", "Plate", "Vehicle Type", "Spot", "Ticket Type", "Entry Time", "Status" };
		final DefaultTableModel model = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTable table = makeTable(null, cols);
		table.setModel(model);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(margin, 158, innerW, ch - 180);
		sp.setBorder(new LineBorder(new Color(210, 215, 230)));
		content.add(sp);

		ActionListener doSearch = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				Object[][] rows = queryTicketsSearch(searchField.getText().trim());
				for (Object[] row : rows) {
					model.addRow(row);
				}
				String q = searchField.getText().trim();
				resultCount.setText(rows.length + " record(s)" + (q.isEmpty() ? "" : " matching " + q + ""));
			}
		};

		searchBtn.addActionListener(doSearch);
		searchField.addActionListener(doSearch);
		doSearch.actionPerformed(null); // initial load

		content.revalidate();
		content.repaint();
	}

	private Object[][] queryActiveTickets() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT ticket_id, spot_id, type, entry_time FROM tickets "
					+ "WHERE exit_time IS NULL ORDER BY entry_time DESC LIMIT 60");
			java.util.List<Object[]> list = new java.util.ArrayList<Object[]>();
			while (rs.next()) {
				list.add(new Object[] { rs.getInt("ticket_id"), rs.getString("spot_id"),
						rs.getString("type").replace("Ticket", ""), rs.getTimestamp("entry_time").toLocalDateTime()
								.format(DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy")) });
			}
			conn.close();
			return list.toArray(new Object[0][]);
		} catch (SQLException e) {
			return new Object[0][];
		}
	}

	private Object[][] queryTicketsSearch(String query) {
		String like = "%" + query + "%";
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			PreparedStatement ps = conn.prepareStatement("SELECT t.ticket_id, v.licence_plate, v.type AS vtype, "
					+ "t.spot_id, t.type AS ttype, t.entry_time, t.exit_time "
					+ "FROM tickets t LEFT JOIN vehicls v ON v.licence_plate = t.licence_plate "
					+ "WHERE (? = '' OR CAST(v.licence_plate AS CHAR) LIKE ? OR t.spot_id LIKE ?)"
					+ "AND t.exit_time IS NULL " + "ORDER BY t.entry_time DESC LIMIT 100");
			ps.setString(1, query);
			ps.setString(2, like);
			ps.setString(3, like);
			ResultSet rs = ps.executeQuery();
			java.util.List<Object[]> list = new java.util.ArrayList<Object[]>();
			while (rs.next()) {
				boolean active = rs.getTimestamp("exit_time") == null;
				list.add(new Object[] { rs.getInt("ticket_id"), rs.getObject("licence_plate"), rs.getString("vtype"),
						rs.getString("spot_id"),
						rs.getString("ttype") != null ? rs.getString("ttype").replace("Ticket", "") : "—",
						rs.getTimestamp("entry_time") != null ? rs.getTimestamp("entry_time").toLocalDateTime()
								.format(DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy")) : "—",
						active ? "Active ✅" : "Exited 🏁" });
			}
			conn.close();
			return list.toArray(new Object[0][]);
		} catch (SQLException e) {
			return new Object[0][];
		}
	}

	private double queryTotalEarnings() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT fee, entry_time, exit_time FROM tickets WHERE exit_time IS NOT NULL");
			double total = 0;
			while (rs.next()) {
				int rate = rs.getInt("fee");
				LocalDateTime entry = rs.getTimestamp("entry_time").toLocalDateTime();
				LocalDateTime exit = rs.getTimestamp("exit_time").toLocalDateTime();
				double minutes = Duration.between(entry, exit).toMinutes();
				double hours = minutes / 60;
				double days = minutes / 1440; // 60 * 24
				double months = minutes / 43200;
				switch (rate) {

				case 50:
					if (hours >= 24) {
						total += 200 * days;
					} else {
						total += 50 * hours;
					}
					break;

				case 200:
					if (days > 29) {
						total += 4000 * months;
					} else {
						total += 200 * days;
					}
					break;

				case 4000:
					total += 4000 * months;
					break;

				default:
					total += 0;
				}
			}
			conn.close();
			return total;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private Vehicle createVehicle(String type, int plate) throws Exception {
		Class<?> cls = Class.forName("models.vehicls." + type);
		return (Vehicle) cls.getDeclaredConstructor(int.class, String.class).newInstance(plate, type);
	}

	private Class<? extends Spots> resolveSpotClass(String label) {
		if ("Compact".equals(label))
			return CompactSpot.class;
		if ("Electric".equals(label))
			return ElectricSpot.class;
		if ("Large".equals(label))
			return LargeSpot.class;
		return RegularSpot.class;
	}

	private String getSpotAvailabilitySummary() {
		java.util.List<Spots> spots = spotMgmt.getSpots();
		long r = spots.stream().filter(s -> s instanceof RegularSpot && !s.isOccupied()).count();
		long c = spots.stream().filter(s -> s instanceof CompactSpot && !s.isOccupied()).count();
		long e = spots.stream().filter(s -> s instanceof ElectricSpot && !s.isOccupied()).count();
		long l = spots.stream().filter(s -> s instanceof LargeSpot && !s.isOccupied()).count();
		return "<html>Regular: <b>" + r + "</b>  Compact: <b>" + c + "</b><br>Electric: <b>" + e + "</b>  Large: <b>"
				+ l + "</b></html>";
	}

	private String estimateFee(int rate, LocalDateTime entry) {
		LocalDateTime now = LocalDateTime.now();
		if (rate == 50) {
			long h = Math.max(1, ChronoUnit.HOURS.between(entry, now));
			return String.format("%.0f", (double) rate * h);
		} else if (rate == 200) {
			long d = Math.max(1, ChronoUnit.DAYS.between(entry.toLocalDate(), LocalDate.now()));
			return String.format("%.0f", (double) rate * d);
		} else if (rate == 4000) {
			long m = Math.max(1, ChronoUnit.MONTHS.between(entry, now));
			return String.format("%.0f", (double) rate * m);
		}
		return "0";
	}

	private String formatDuration(LocalDateTime entry) {
		long totalMins = ChronoUnit.MINUTES.between(entry, LocalDateTime.now());
		if (totalMins < 60)
			return "Duration: " + totalMins + " min";
		if (totalMins < 1440)
			return "Duration: " + (totalMins / 60) + "h " + (totalMins % 60) + "m";
		return "Duration: " + (totalMins / 1440) + " day(s)";
	}

	private JButton makeSidebarBtn(String text) {
		JButton b = new JButton(text);
		b.setBackground(SIDEBAR_BG);
		b.setForeground(new Color(160, 174, 192));
		b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setHorizontalAlignment(SwingConstants.LEFT);
		b.setMargin(new Insets(0, 16, 0, 0));
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if (!b.getForeground().equals(ACCENT))
					b.setBackground(new Color(38, 43, 60));
			}

			public void mouseExited(MouseEvent e) {
				if (!b.getForeground().equals(ACCENT))
					b.setBackground(SIDEBAR_BG);
			}
		});
		return b;
	}

	private void highlightNav(JButton[] btns, int active) {
		for (int i = 0; i < btns.length; i++) {
			btns[i].setBackground(i == active ? new Color(45, 51, 70) : SIDEBAR_BG);
			btns[i].setForeground(i == active ? ACCENT : new Color(160, 174, 192));
		}
	}

	private JButton makeBtn(String text, Color bg) {
		JButton b = new JButton(text);
		b.setBackground(bg);
		b.setForeground(WHITE);
		b.setFont(new Font("Segoe UI", Font.BOLD, 13));
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				b.setBackground(bg.darker());
			}

			public void mouseExited(MouseEvent e) {
				b.setBackground(bg);
			}
		});
		return b;
	}

	private JPanel makeStatCard(String title, String value, String icon, Color bg) {
		JPanel card = new JPanel();
		card.setBackground(bg);
		card.setLayout(null);

		JLabel ico = new JLabel(icon);
		ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
		ico.setForeground(new Color(255, 255, 255, 130));
		ico.setBounds(8, 8, 36, 28);
		card.add(ico);

		JLabel valLbl = new JLabel(value);
		valLbl.setFont(new Font("Segoe UI", Font.BOLD, value.length() > 6 ? 20 : 26));
		valLbl.setForeground(WHITE);
		valLbl.setBounds(8, 34, 200, 36);
		card.add(valLbl);

		JLabel titLbl = new JLabel(title);
		titLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		titLbl.setForeground(new Color(255, 255, 255, 190));
		titLbl.setBounds(8, 74, 200, 18);
		card.add(titLbl);

		return card;
	}

	private JPanel makeFormCard(int x, int y, int w, int h) {
		JPanel p = new JPanel();
		p.setBackground(WHITE);
		p.setLayout(null);
		p.setBounds(x, y, w, h);
		p.setBorder(new CompoundBorder(new LineBorder(new Color(215, 220, 235)), new EmptyBorder(5, 5, 5, 5)));
		return p;
	}

	private JTable makeTable(Object[][] rows, String[] cols) {
		DefaultTableModel model = new DefaultTableModel(rows == null ? new Object[0][cols.length] : rows, cols) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTable t = new JTable(model);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		t.setRowHeight(26);
		t.setShowGrid(false);
		t.setIntercellSpacing(new Dimension(0, 0));
		t.setBackground(WHITE);
		t.setSelectionBackground(new Color(232, 240, 254));
		t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		t.getTableHeader().setBackground(new Color(248, 250, 252));
		t.getTableHeader().setForeground(TEXT_MID);
		t.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(210, 215, 230)));
		return t;
	}

	private void addPageTitle(JPanel p, String title, String sub) {
		JLabel t = new JLabel(title);
		t.setFont(new Font("Segoe UI", Font.BOLD, 22));
		t.setForeground(TEXT_DARK);
		t.setBounds(20, 16, 500, 32);
		p.add(t);

		JLabel s = new JLabel(sub);
		s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		s.setForeground(TEXT_MID);
		s.setBounds(20, 50, p.getWidth() - 40, 20);
		p.add(s);
	}

	private void formLabel(JPanel p, String text, int x, int y) {
		JLabel l = new JLabel(text);
		l.setFont(new Font("Segoe UI", Font.BOLD, 12));
		l.setForeground(TEXT_MID);
		l.setBounds(x, y, 280, 18);
		p.add(l);
	}

	private JTextField formTextField(JPanel p, int x, int y, int w) {
		JTextField f = new JTextField();
		f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		f.setBounds(x, y, w, 32);
		f.setBorder(new CompoundBorder(new LineBorder(new Color(200, 210, 225)), new EmptyBorder(0, 9, 0, 9)));
		p.add(f);
		return f;
	}

	private void styleCombo(JComboBox<?> c) {
		c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		c.setBackground(WHITE);
	}

	private void setStatus(JLabel lbl, String text, Color color) {
		lbl.setText(text);
		lbl.setForeground(color);
	}
}