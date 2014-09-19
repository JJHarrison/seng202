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

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

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

    /*
    public void getUserID() {
    	int id;
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
    	    statement = connect.createStatement();
    	    resultSet = statement.executeQuery("select * from FITR.USER");
    	    while (resultSet.next()) {
    		id = resultSet.getInt("user_id");
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
        }
        */

    /**
     * Checks the database to see if the user is already a participant of the user table.
     * The check is performed via an sql query to see if the unique user_id of the user is found.
     * This function will return true if the user is a participant of the database.
     * @param user The user that will be searched for inside the mysql database
     * @return True if the user is a participant of the database, false otherwise.
     */
    public boolean isUserStored(User user){
    	boolean isThere = false;
    	// the query to be sent to the database to find the user_id
    	String query = String.format("SELECT * FROM FITR.USER where user_id = \"%s\"", user.getUserId());    	
    	try {
			connect = DriverManager.getConnection(url, admin, password);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				//if the user is inside the database, set isThere to true
				isThere = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
    	return isThere;
    }
    
    /**
     * This will update the users weight, height and bmi inside the database.
     * @param user This profile will be updated in the datebase
     */
    public void updateUserProfile(User user){
    	String query = String.format("UPDATE FITR.USER SET "
    			+ "weight = ?,"
    			+ "height = ?,"
    			+ "bmi = ?"
    			+ " where user_id = \"%s\"", user.getUserId());
    	try{
    		connect = DriverManager.getConnection(url, admin, password);
    		preparedStatement = connect.prepareStatement(query);
    		preparedStatement.setDouble(1, user.getWeight());
    		preparedStatement.setDouble(2, user.getHeight());
    		preparedStatement.setDouble(3, user.getBMI());   		
    		preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
    	    try {
    		if (preparedStatement != null) {
    		    preparedStatement.close();
    		}
    		if (connect != null) {
    		    connect.close();
    		}
    	    } catch (SQLException e) {
    		e.printStackTrace();
    	    }
    	}    	
    }
    
    /**
     * Checks the database to see if the event is already a participant of the event table.
     * The check is performed via a sql query to see if the event_name, start_time and unique
     * user_id are present in the table. This function will return true if the event is inside the 
     * database
     * @param user The user for gathering the user_id
     * @param event The event that will be checks for participation in the database
     * @return True if the event is a participant of the database, false otherwise.
     */
    public boolean isEventStored(User user, Event event){
    	boolean isThere = false;
    	// query to see if the event is stored in the database
    	String query = "select user_user_id, event_name, start_time from fitr.event";
    	String eventName;
    	int userID;
    	// timestamp on the event to compare with the one from the database
    	Timestamp checkTime = new Timestamp(event.getStartTime().getTimeInMillis());
    	Timestamp eventStart;
    	try {
			connect = DriverManager.getConnection(url, admin, password);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				eventName = resultSet.getString("event_name");
				eventStart = resultSet.getTimestamp("start_time");
				userID = resultSet.getInt("user_user_id");
				// check to see if the event is contained in the databae
				if(eventStart.equals(checkTime) && (userID == user.getUserId())){
					// if the event is inside the database change isThere to true
					isThere = true;
				}
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
    	return isThere;
    }
    
    /**
     * Will check the database to see if the users profile has already been added to the database.
     * If the user is not a participant of the database their user profile information will be added to the database.
     * If the user is already a participant of the database it will skip that step.
     * Following this ALL events of the users profile will be checked for participation in the database and will
     * be added if they are not inside the database.
     * @param user The users profile that will be uploaded
     */
    public void writeUser(User user){
    	if(! isUserStored(user)){
    		// if the user is not in the database it will add it
    		writeUserProfile(user);
    	}
    	else{
    		// if the user is in the database it will update it
    		updateUserProfile(user);
    	}
    	// add all events associated with the users profile to the database
    	for(Event event : user.getEvents().getAllEvents()){
			writeEvent(user, event);
		}
    }
      
    /**
     * Writes a single event to the database.
     * First a check is made to see if the event is already stored using isEventStored()
     * If the event a participant of the database it will be added to the event table.
     * All datapoints associated with the event will be added to the datapoint table.
     * @param user The user who the events belong to.
     * @param event The event that will be uploaded to the database.
     */
    public void writeEvent(User user, Event event){
    	if(! isEventStored(user, event)){
    		try{
    			// if the event is not in the database, upload it
    			writeEventInfo(user, event);
    			// adds each of the datapoints that the event contained.
    			for(DataPoint point : event.getDataPoints()){
    				writeDataPoint(user, event, point);
    			}
    		}
    		catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
        
    /**
     * Writes the users profile to the database
     * @param user The user whose profile will be added to the database
     * @throws Exception
     */
    public void writeUserProfile(User user){
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
    	    preparedStatement = connect
    		    .prepareStatement(
    			    "INSERT into fitr.user VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
    			    Statement.RETURN_GENERATED_KEYS);
    	    preparedStatement.setInt(1, user.getUserId()); // add user_id
    	    preparedStatement.setString(2, user.getName()); //add name to the db
    	    preparedStatement.setTimestamp(3, new Timestamp(user.getDateofBirth().getTimeInMillis())); //add DOB to the db
    	    preparedStatement.setDouble(4, user.getWeight()); //add weight to the db
    	    preparedStatement.setDouble(5, user.getHeight()); //add height to the db
    	    preparedStatement.setString(6, "password"); //DEEEEEEEEEEEEELLLLLLLLLLLLLLLLLLLLLLLLLLLTTTTTTTTTTTTTTTTTTTTEEEEEEEEEEEEEEEEEEEEEE
    	    preparedStatement.setString(7, user.genderForDB()); //add gender to the db
    	    preparedStatement.setDouble(8, user.getBMI()); //add bmi to the db
    	    preparedStatement.executeUpdate(); //execute the query/upload the db
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    		if (preparedStatement != null) {
    		    preparedStatement.close();
    		}
    		if (connect != null) {
    		    connect.close();
    		}
    	    } catch (SQLException e) {
    		e.printStackTrace();
    	    }
    	}
        }
  
    /**
     * Writes the event information to the database.
     * @param user The user who the event belongs to
     * @param event The event thats information will be added to the database
     */
    public void writeEventInfo(User user, Event event) {
    	try {
    	    connect = DriverManager.getConnection(url, admin, password);
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
    	    preparedStatement.setInt(5, 1337); // num_points
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
    		if (preparedStatement != null) {
    		    preparedStatement.close();
    		}
    		if (connect != null) {
    		    connect.close();
    		}
    	    } catch (SQLException e) {
    		e.printStackTrace();
    	    }
    	}
        }
    
   
   /**
    * Writes a datapoint to the database. 
    * @param event The event that the point belongs to.
    * @param point The point that will be added to the database.
    */
    public void writeDataPoint(User user, Event event, DataPoint point) {
	try {
	    connect = DriverManager.getConnection(url, admin, password);
	    preparedStatement = connect
		    .prepareStatement(
			    "INSERT into fitr.datapoint VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			    Statement.RETURN_GENERATED_KEYS);
	    preparedStatement.setInt(1, user.getUserId()); // user_id to be added to the db
	    preparedStatement.setString(2, event.getEventName()); // event_name to be added to the db
	    preparedStatement.setTimestamp(3, new Timestamp(event
		    .getStartTime().getTimeInMillis())); // event_startime to be added to the db
	    preparedStatement.setTimestamp(4, new Timestamp(point.getDate()
		    .getTimeInMillis())); // the timepoint that will be added to the db
	    preparedStatement.setInt(5, point.getHeartRate()); // the heartrate that will be added to the db
	    preparedStatement.setDouble(6, point.getLatitude()); // the latitude that will be added to the db
	    preparedStatement.setDouble(7, point.getLongitude()); // the longitude that will be added to the db
	    preparedStatement.setDouble(8, point.getAltitude()); // the altitude that will be added to the db
	    preparedStatement.setDouble(9, point.getSpeed()); // the speed that will be added to the db
	    preparedStatement.setDouble(10, point.getDistance()); // the distance that will be added to the db
	    preparedStatement.executeUpdate(); // execute the query/upload to the database
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (preparedStatement != null) {
		    preparedStatement.close();
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
    	
    	//mocky.setUserID(2);
		try {
			dbw.writeUser(mocky);
			//dbw.updateUserProfile(mocky);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	    }
}
