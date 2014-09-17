package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import user.User;
import user.User.Gender;
import data.model.DataPoint;
import data.model.Event;
import data.persistant.Persistent;

public class DBWriter {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String url = "jdbc:mysql://localhost:3306/fitr";
    private String admin = "fitr.admin";
    private String password = "password";

    // private PreparedStatement readStatement = null;

    public void getUserID() {
    	int id;
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
    	    statement = connect.createStatement();
    	    resultSet = statement.executeQuery("select * from FITR.USER");
    	    while (resultSet.next()) {
    		id = resultSet.getInt("user_id");
    		System.out.println(id);
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
        }

    public boolean isUserStored(User user){
    	boolean isThere = false;
    	String query = String.format("SELECT * FROM FITR.USER where user_id = \"%s\"", user.getName());
    	
    	try {
			connect = DriverManager.getConnection(url, admin, password);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				isThere = true;
				System.out.println("User exists in database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return isThere;
    }
    
    public void writeUserProfile(User user) throws Exception {
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
    	    statement = connect.createStatement();
    	    preparedStatement = connect
    		    .prepareStatement(
    			    "INSERT into fitr.user (user_id, username, dateofbirth, weight, height, password, gender, bmi) "
    				    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
    			    Statement.RETURN_GENERATED_KEYS);
    	    preparedStatement.setInt(1, user.getUserId());
    	    preparedStatement.setString(2, user.getName());
    	    preparedStatement.setTimestamp(3, new Timestamp(user.getDateofBirth().getTimeInMillis()));
    	    preparedStatement.setDouble(4, user.getWeight());
    	    preparedStatement.setDouble(5, user.getHeight());
    	    preparedStatement.setString(6, "password");
    	    preparedStatement.setString(7, user.genderForDB());
    	    preparedStatement.setDouble(8, user.getBMI());
    	    preparedStatement.executeUpdate();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    		if (resultSet != null) {
    		    resultSet.close();
    		}
    		if (statement != null) {
    		    statement.close();
    		}
    		if (connect != null) {
    		    connect.close();
    		}
    	    } catch (SQLException e) {
    		e.printStackTrace();
    	    }
    	}
        }

    public void writeUser(User user){
    	if(! isUserStored(user)){
    		try {
				writeUserProfile(user);
				//
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    			
			
    	
 
        
        

    
    public boolean isEventStored(Event event){
    	boolean isThere = false;
    	String query = String.format("SELECT * FROM FITR.EVENT where event_name = \"%s\"", event.getEventName());;
    	try {
			connect = DriverManager.getConnection(url, admin, password);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				isThere = true;
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return isThere;
    }
    
    public void writeEventInfo(User user, Event event) {
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
    	    statement = connect.createStatement();
    	    preparedStatement = connect
    		    .prepareStatement(
    			    "INSERT into fitr.event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    			    Statement.RETURN_GENERATED_KEYS);
    	    preparedStatement.setInt(1, user.getUserId()); // user_id
    	    preparedStatement.setString(2, event.getEventName()); // event_name
    	    preparedStatement.setTimestamp(3, new Timestamp(event
    		    .getStartTime().getTimeInMillis())); // start_time
    	    preparedStatement.setTimestamp(4, new Timestamp(event
    		    .getFinishTime().getTimeInMillis())); // end_time
    	    preparedStatement.setInt(5, 123); // num_points
    	    preparedStatement.setDouble(6, event.getDistance()); // distance
    	    preparedStatement.setDouble(7, event.getMaxSpeed()); // max_speed
    	    preparedStatement.setDouble(8, event.getAverageSpeed()); // average_speed
    	    preparedStatement.setInt(9, 100); // total_heart_rate
    	    preparedStatement.setInt(10, event.getAverageHeartRate()); // average_heart_rate
    	    preparedStatement.executeUpdate();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    		if (resultSet != null) {
    		    resultSet.close();
    		}
    		if (statement != null) {
    		    statement.close();
    		}
    		if (connect != null) {
    		    connect.close();
    		}
    	    } catch (SQLException e) {
    		e.printStackTrace();
    	    }
    	}
        }
    
    public void writeEvent(User user, Event event){
    	if(! isEventStored(event)){
    		try{
    			writeEventInfo(user, event);
    			for(DataPoint point : event.getDataPoints()){
    				writeDataPoint(event, point);
    			}
    		}
    		catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
    
    public void writeDataPoint(Event event, DataPoint point) {
	try {
	    connect = DriverManager.getConnection(url, admin, password);
	    statement = connect.createStatement();
	    preparedStatement = connect
		    .prepareStatement(
			    "INSERT into fitr.datapoint VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
			    Statement.RETURN_GENERATED_KEYS);
	    preparedStatement.setString(1, event.getEventName()); // event_name
	    preparedStatement.setTimestamp(2, new Timestamp(event
		    .getStartTime().getTimeInMillis()));
	    preparedStatement.setTimestamp(3, new Timestamp(point.getDate()
		    .getTimeInMillis()));
	    preparedStatement.setInt(4, point.getHeartRate());
	    preparedStatement.setDouble(5, point.getLatitude());
	    preparedStatement.setDouble(6, point.getLongitude());
	    preparedStatement.setDouble(7, point.getAltitude());
	    preparedStatement.setDouble(8, point.getSpeed());
	    preparedStatement.setDouble(9, point.getDistance());
	    preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
		if (connect != null) {
		    connect.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
 }
    
        
        
        
        
        
        
        
        
    public static void main(String[] args) {
    	DBWriter dbw = new DBWriter();
    	User mocky = User.mockUser();
    	
		try {
			Persistent.newUser(mocky);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		ArrayList<DataPoint> points;

		

		// set up data points
		points = new ArrayList<DataPoint>();
		Calendar c3 = new GregorianCalendar(2005, // Year
			5, // Month
			10, // Day
			23, // Hour
			42, // Minute
			28); // Second
		Calendar c4 = new GregorianCalendar(2005, // Year
			5, // Month
			10, // Day
			23, // Hour
			43, // Minute
			5); // Second

		// p1 = new DataPoint(c3, 120, 30.2553368, -97.83891084, 50.0, null);
		DataPoint p1 = new DataPoint.Builder().date(c3).heartRate(120)
			.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
			.prevDataPoint(null).build();
		// p2 = new DataPoint(c4, 125, 30.25499189, -97.83913958, 51.0, p1);
		DataPoint p2 = new DataPoint.Builder().date(c4).heartRate(125)
			.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
			.prevDataPoint(p1).build();

		points.add(p1);
		points.add(p2);
		Event e = new Event("My Event", points);

		try {
		     dbw.writeUserProfile(mocky);
		    // dbw.writeEvent(e);
		    // dbw.writeDataPoint(e, p2);
			// System.out.println(dbw.isEventStored(e));
			//dbw.writeEventInfo(e);
		    // dbw.getUserID();
			dbw.writeEvent(mocky, e);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	    }
}
