package services;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.vehicls.*;
import java.util.List;

import exceptions.SpotNotAvailableException;
import models.spots.CompactSpot;
import models.spots.ElectricSpot;
import models.spots.LargeSpot;
import models.spots.RegularSpot;
import models.spots.Spots;
import models.tickets.Tickets;

/* SpotManagement class : manages parking spots, availability, and reservations */
public class SpotManagement{
	private List<Spots> spots=new ArrayList<>();// List of all parking spots
	Spots spot1=null;// Temporary spot reference 
	/* Constructor SpotManagement : initializes the spots list */
	public SpotManagement() throws SQLException {
		String sql = "SELECT * FROM spots;";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_project", "root", "");
	         PreparedStatement ps = conn.prepareStatement(sql)) {

        ResultSet rs=ps.executeQuery(sql);
        spots=new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("spotId");
            String type = rs.getString("type");
            boolean occupied = rs.getBoolean("occupied");
        
            switch (type) {
                case "RegularSpot":
                    spot1 = new models.spots.RegularSpot(id);
                    break;
                case "CompactSpot":
                    spot1 = new models.spots.CompactSpot(id);
                    break;
                case "ElectricSpot":
                    spot1 = new models.spots.ElectricSpot(id);
                    break;
                case "LargeSpot":
                    spot1 = new models.spots.LargeSpot(id);
                    break;
                default:
                    continue;
            }

            spot1.setOccupied(occupied);
            spots.add(spot1);
        }

	    }
    }
	/* Add a spot to the list */
    public void addSpot(Spots spot) {
    	spots.add(spot);
    }
    // Find an available spot of a specific type (type class type of spot (e.g., RegularSpot),available spot of type T or null if none found)
    public <T extends Spots> T findAvailableSpotByType(Class<T> type) {
        for (Spots spot : spots) {
        	    if (type.isInstance(spot) && !spot.isOccupied()) {
        		     return (T) spot;
             }
        }
        return null;
      }
    // Calculate occupancy rate of the parking lot (percentage of occupied spots):
    public double getOccupancyRate() {
        if (spots.isEmpty())
        	return 0;
        long occupied = 0;
		for (Spots s : spots) {
            if (s.isOccupied()==true) {
                occupied++;
            }
        }
        return (double) occupied / spots.size() * 100;
    }
    // Reserve a spot for a vehicle
    public void ReserveSpotbytype(Vehicle vehicle,Spots spot) throws SpotNotAvailableException {
    	if (spot != null && spot.isOccupied()==false) {
    		//available==false
            spot.occupy(vehicle);
            vehicle.setSpot(spot);
            spot.setOccupied(true);
        } else {
            throw new SpotNotAvailableException("SpotNotAvailableException");
        }
    }
    /* Get the list of all spots */
	public List<Spots> getSpots() {
        return spots;
    }
	public void refreshSpots() throws SQLException {
	    // Clear your internal list and re-run the SQL query that populates it
	    this.spots.clear(); 
	    // ... run your DB logic here ...
	    this.spots.addAll(queryFromDatabase()); 
	}
	private List<Spots> queryFromDatabase() throws SQLException {

	    List<Spots> list = new ArrayList<>();

	    String sql = """
	        SELECT s.spotId, s.type,
       CASE 
           WHEN t.ticket_id IS NOT NULL THEN 1 
           ELSE 0 
       END AS occupied
FROM spots s
LEFT JOIN tickets t 
ON s.spotId = t.spot_id AND t.exit_time IS NULL;
	    """;

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_project", "root", "");
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            String spotId = rs.getString("spotId");
	            String type   = rs.getString("type");
	            boolean occupied = rs.getInt("occupied") == 1;

	            Spots spot;

	            switch (type.toLowerCase()) {
	                case "CompactSpot" -> spot = new CompactSpot(spotId);
	                case "ElectricSpot" -> spot = new ElectricSpot(spotId);
	                case "LargeSpot" -> spot = new LargeSpot(spotId);
	                default -> spot = new RegularSpot(spotId);
	            }

	            spot.setOccupied(occupied);

	            list.add(spot);
	        }
	    }

	    return list;
	}
}