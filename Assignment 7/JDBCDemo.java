import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;
import java.util.*;

public class JDBCDemo {
	
	private static final String USERNAME = "jofeece467";
    private static final String PASSWORD = "China123!";
    private static final String HOST = "localhost"; // Host
    private static final String PORT = "1521"; // Default port
    private static final String SID = System.getenv("ORACLE_SID"); // Oracle SID

    public static void main (String args[]) throws SQLException
    {
    	
    	Scanner keyboard = new Scanner(System.in);
    	
    	System.out.println("Make a selection:");
    	System.out.println("1) List the names and addresses of all guests living in London, alphabetically ordered by name");
    	System.out.println("2) What is the average price of a room?");
    	System.out.println("3) List the details of all rooms at the Grosvenor Hotel, including the name of the guests staying in the room, if the room is occupied.");
    	System.out.println("4) List the number of rooms in each hotel in London.");
    	System.out.println("5) Update the price of all rooms by 5%.");
    	int choice = keyboard.nextInt();
    	while (choice != 0) {
    		switch (choice) {
    		        case 1: choice = 1;
    		                choiceOne();
    		                break;
    		        case 2: choice = 2;
    		                choiceTwo();
    		                break;
    		        case 3: choice = 3;
    		              	choiceThree();
    		                break;
    		        case 4: choice = 4;
    		             	choiceFour();
    		                break;
    		        case 5: choice = 5;
    		                choiceFive();
    		                break;
    		}



System.out.print("Next choice?: ");
choice = keyboard.nextInt();
    		}    	
    	System.out.println("\n GOOD BYE!");

	}
    
    
    public static void choiceOne() throws SQLException{

    			OracleDataSource ods = new OracleDataSource();
    	        ods.setUser(USERNAME);
    	        ods.setPassword(PASSWORD);
    	        ods.setURL("jdbc:oracle:thin:" + "@" + HOST
    	                                       + ":" + PORT
    	                                       + ":" + SID);

    	        Connection conn = ods.getConnection();

    	// Statement
    	        CallableStatement cstmt;
    	        ResultSet cursor;

    	        cstmt = conn.prepareCall
    	                     ("begin open ? for SELECT guestName, guestAddress FROM Guest WHERE guestAddress LIKE '%London%'order by guestName; end;");

    	        //Cursor
    	        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
    	        cstmt.execute();
    	        cursor = ((OracleCallableStatement)cstmt).getCursor(1);

    	        System.out.println("\n\nSelection 1");
    	        System.out.println("Guest Name      Guest Address");
    	        System.out.println("----------      -------------");


    	         //Use the cursor like a normal ResultSet
    	        while (cursor.next ()) {
    	        System.out.printf("%s        %s \n",cursor.getString(1), cursor.getString(2));}

    }
    
    public static void choiceTwo() throws SQLException{
    	


    	OracleDataSource ods = new OracleDataSource();
        ods.setUser(USERNAME);
        ods.setPassword(PASSWORD);
        ods.setURL("jdbc:oracle:thin:" + "@" + HOST
                                       + ":" + PORT
                                       + ":" + SID);

        Connection conn = ods.getConnection();

        CallableStatement cstmt;
        ResultSet cursor;

        cstmt = conn.prepareCall
                     ("begin open ? for SELECT AVG(price) from Room; end;");

        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
        cstmt.execute();
        cursor = ((OracleCallableStatement)cstmt).getCursor(1);

        System.out.println("\n\nSelection 2: ");
        System.out.println("The average price is: ");
        System.out.println("--------------------  ");

        while (cursor.next ()) {
        System.out.printf("%f \n", cursor.getFloat(1));} 

System.out.println("\nGOOD BYE!");	
    	
    }
    
    public static void choiceThree() throws SQLException{


    	OracleDataSource ods = new OracleDataSource();
        ods.setUser(USERNAME);
        ods.setPassword(PASSWORD);
        ods.setURL("jdbc:oracle:thin:" + "@" + HOST
                                       + ":" + PORT
                                       + ":" + SID);

        Connection conn = ods.getConnection();

        CallableStatement cstmt;
        ResultSet cursor;

        cstmt = conn.prepareCall
                     ("begin open ? for SELECT r.roomNo, r.HotelNo, r.type, r.price, g.guestName FROM Room r INNER JOIN hotel h on r.hotelNo = h.hotelNo INNER JOIN Booking b on h.hotelNo = b.hotelNo and r.roomNo = b.roomNo INNER JOIN Guest g on b.guestNo = g.guestNo; end;");


        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
        cstmt.execute();
        cursor = ((OracleCallableStatement)cstmt).getCursor(1);
	System.out.println("\n\nSelection 3: ");
        System.out.println("\n\nRoomNo  HotelNo   Type     Price   Guest ");
        System.out.println("------  -------   ----     -----   ----- ");

        while (cursor.next ()) {
        System.out.printf("%d    %d        %s    %d      %s \n",cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5));}

    	
    	
    	
    	
    }
    
    public static void choiceFour() throws SQLException{

    	OracleDataSource ods = new OracleDataSource();
        ods.setUser(USERNAME);
        ods.setPassword(PASSWORD);
        ods.setURL("jdbc:oracle:thin:" + "@" + HOST
                                       + ":" + PORT
                                       + ":" + SID);

        Connection conn = ods.getConnection();

        CallableStatement cstmt;
        ResultSet cursor;

        cstmt = conn.prepareCall
                     ("begin open ? for SELECT h.hotelNo, count(r.roomNo) as Roomcount from Hotel h, Room r where r.hotelNo = h.hotelNo AND city like '%London%' group by h.hotelNo; end;");

        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
        cstmt.execute();
        cursor = ((OracleCallableStatement)cstmt).getCursor(1);

        System.out.println("\n\nSelection 4: ");
        System.out.println("Hotel No     Room Count:");
        System.out.println("--------     -----------");


        while (cursor.next ()) {
        System.out.printf("%d             %d \n",cursor.getInt(1), cursor.getInt(2));}
}
    
    
    public static void choiceFive() throws SQLException{

    	OracleDataSource ods = new OracleDataSource();
        ods.setUser(USERNAME);
        ods.setPassword(PASSWORD);
        ods.setURL("jdbc:oracle:thin:" + "@" + HOST
                                       + ":" + PORT
                                       + ":" + SID);

        Connection conn = ods.getConnection();

        CallableStatement cstmt;
        ResultSet cursor;

        cstmt = conn.prepareCall
                     ("begin UPDATE Room SET price = price*1.05; end;");


        
        cstmt.execute();
       

        System.out.println("\n\nSelection 5");
        System.out.println("Prices Updated");
        System.out.println("----------");

        
        
    	
    }
    
    

}

 

