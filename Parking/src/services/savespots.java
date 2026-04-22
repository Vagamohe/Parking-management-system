package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import models.spots.Spots;
public class savespots {
	public static void saveData(SpotManagement sp) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/my_project";
	      String user = "root";
	      String password = "";
	      Connection conn = DriverManager.getConnection(url, user, password);
        Statement st=conn.createStatement();
        
		        for (Spots s : sp.getSpots()) {
		        	if (s.isOccupied()==false) {
		        		String sql = "INSERT INTO spots VALUES ('"
		        		        + s.getSpotID() + "', '"
		        		        + s.getClass().getSimpleName() + "', "
		        		        + (s.isOccupied() ? 1 : 0) + ")";
			        	  int rs = st.executeUpdate(sql);
		        	}
		        }
		        st.close();
		        conn.close();
		}
}