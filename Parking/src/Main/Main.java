package Main;
import models.vehicls.*;
import models.spots.*;
import models.tickets.*;
import services.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import exceptions.*;
import front.Admin_login;
import front.main_interface;
public class Main {
	public static void main(String []args) throws SQLException, IOException  {
		Scanner input=new Scanner (System.in);
		/* Initialize SpotManagement and load saved spots if available */
		SpotManagement sp=new SpotManagement();
        /* Add default spots if none exist */
        /* Initialize entry and exit services */
        EntryRegistration entry = new EntryRegistration(sp);
        ExitRegistration exit=new ExitRegistration();
        /* Main menu loop */
        	new Admin_login();

	}
}