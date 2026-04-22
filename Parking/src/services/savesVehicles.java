package services;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.FileOutputStream;
import models.spots.Spots;
import models.vehicls.Vehicle;
public class savesVehicles {
	public static void saveData(Vehicle vehicle) throws SQLException {
		  String url = "jdbc:mysql://localhost:3306/my_project";
	      String user = "root";
	      String password = "";
	      Connection conn = DriverManager.getConnection(url, user, password);
          Statement st=conn.createStatement();
		        	String sql = "INSERT INTO vehicls (licence_plate, type) VALUES ("
		        	        + vehicle.getLicensPlate() + " , "
		        	        +"'"+ vehicle.getClass().getSimpleName() + "')";
		        	  int rs = st.executeUpdate(sql);
		        st.close();
		        conn.close();
		}
	
	}
