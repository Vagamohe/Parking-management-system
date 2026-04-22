package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import exceptions.TicketNotFoundException;
public class ExitRegistration {
	
public double ExitRegistration(int ticketid)
        throws TicketNotFoundException, IOException, SQLException {

    String url = "jdbc:mysql://localhost:3306/my_project";
    String user = "root";
    String password = "";

    Connection conn = DriverManager.getConnection(url, user, password);
    Statement st = conn.createStatement();

    if (ticketid == 0)
        return 0;

    String sql = "SELECT * FROM tickets WHERE ticket_id = " + ticketid;
    ResultSet s = st.executeQuery(sql);

    if (!s.next()) {
        throw new TicketNotFoundException("the ticket wasn't found");
    }

    int fee = s.getInt("fee");
    String spotid = s.getString("spot_id");
    LocalDateTime entryTime = s.getTimestamp("entry_time").toLocalDateTime();
    LocalDateTime exitTime = LocalDateTime.now();
    int licence_plate=s.getInt("licence_plate");
    // update exit time in DB
    String update = "UPDATE tickets SET exit_time = NOW() WHERE ticket_id = " + ticketid;
    st.executeUpdate(update);
    double result = 0;

    double minutes = Duration.between(entryTime, exitTime).toMinutes();
    double hours=minutes/60;
    double days = minutes / 1440;   // 60 * 24
    double months = minutes / 43200;
    switch (fee) {
        case 50: 
            if (hours >= 24) {
                result += 200 * days;
            } else {
                result += 50 * hours;
            }
            break;

        case 200: 
            if (days > 29) {
                result += 4000 * months;
            } else {
                result += 200 * days;
            }
            break;

        case 4000:
            result += 4000 * months;
            break;

        default:
            result += 0;
    }
    // free spot
    String sql1 = "UPDATE spots SET Occupied = false WHERE spotId = '" + spotid+"'";
    st.executeUpdate(sql1);
    String sql2 = "DELETE FROM vehicls"
    		+ " WHERE licence_plate = "+licence_plate;
    st.executeUpdate(sql1);
    st.executeUpdate(sql2);
    st.close();
    conn.close();

    return result;
}
}