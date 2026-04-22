package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import models.tickets.Tickets;
import models.vehicls.Vehicle;
public class TicketsStorage{
public static void saveTickets(Tickets ticket,Vehicle v) throws SQLException {
    String url = "jdbc:mysql://localhost:3306/my_project";
    String user = "root";
    String password = "";
    
    // Using try-with-resources to ensure connection and statement are closed automatically
    String sql = "INSERT INTO tickets(ticket_id, type, fee, spot_id, licence_plate, entry_time) VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, ticket.getTicketId());
        pstmt.setString(2, ticket.getClass().getSimpleName());
        pstmt.setDouble(3, ticket.getFee());
        pstmt.setString(4, ticket.getVehicle().getSpot().getSpotID());
        pstmt.setInt(5, v.getLicensPlate());
        // Let JDBC handle the conversion of LocalDateTime to SQL Timestamp
        pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()));
        
        pstmt.executeUpdate();
    }
}
}