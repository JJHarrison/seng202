package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.User;
import data.model.DataPoint;
import data.model.Event;

/**
 * This class provides functionality for writing objects to the mysql database uing sql queries.
 * It is used for taking a serialized user profile and writing the information associated with the user,
 * the information associated with the events and the datapoints each event contains.
 * 
 * @author James
 *
 */
public class DBWriter {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStatement2 = null;
	private ResultSet resultSet = null;
	// change to "jdbc:sqlite: <your database path here> " to setup 
	private String url = "jdbc:sqlite:E:/fitr_database/fitr.db";

	/**
	 * Will check the database to see if the users profile has already been
	 * added to the database. If the user is not a participant of the database
	 * their user profile information will be added to the database. If the user
	 * is already a participant of the database it will skip that step.
	 * Following this ALL events of the users profile will be checked for
	 * participation in the database and will be added if they are not inside
	 * the database.
	 * 
	 * @param user The users profile that will be uploaded
	 */
	public void writeUser(User user) throws SQLException{
		if (!isUserStored(user)) {
			// if the user is not in the database it will add it
			writeUserProfile(user);
		} else {
			// if the user is in the database it will update it
			updateUserProfile(user);
		}
		// write all events associated with that user to the db
		for (Event event : user.getEvents().getAllEvents()) {
			writeEvent(user, event);
		}
			
			
		// add all events associated with the users profile to the database
		
	}

	/**
	 * Checks the database to see if the user is already a participant of the
	 * user table. The check is performed via an sql query to see if the unique
	 * user_id of the user is found. This function will return true if the user
	 * is a participant of the database.
	 * 
	 * @param user The user that will be searched for inside the mysql database
	 * @return True if the user is a participant of the database, false otherwise.
	 * @throws SQLException 
	 */
	private boolean isUserStored(User user) throws SQLException {
		boolean isThere = false;
		// the query to be sent to the database to find the user_id
		String query = String.format("SELECT * FROM USER where user_id = \"%s\"", user.getUserId());
		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			// if the user is inside the database, set isThere to true
				isThere = true;
			}
		
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connect != null) {
			connect.close();
		}
 		return isThere;
	}

	/**
	 * Writes the users profile to the database
	 * 
	 * @param user The user whose profile will be added to the database
	 * @throws SQLException 
	 */
	private void writeUserProfile(User user) throws SQLException {
		connect = DriverManager.getConnection(url);
		preparedStatement = connect.prepareStatement("INSERT into user VALUES (?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, user.getUserId()); // add user_id
		preparedStatement.setString(2, user.getName()); // add name to the db
		preparedStatement.setLong(3, user.getDateofBirth().getTimeInMillis()); // 
		preparedStatement.setDouble(4, user.getWeight()); // add weight to the db
		preparedStatement.setDouble(5, user.getHeight()); // add height to the db
		preparedStatement.setDouble(6, user.getBMI()); // add gender to the db
		preparedStatement.setString(7, user.genderForDB()); // add bmi to the db
		preparedStatement.executeUpdate(); // execute the query/upload the db
		
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connect != null) {
			connect.close();
		}
	}
	
	/**
	 * This will update the users weight, height and bmi inside the database.
	 * 
	 * @param user This profile will be updated in the datebase
	 * @throws SQLException 
	 */
	private void updateUserProfile(User user) throws SQLException {
		String query = String.format("UPDATE USER SET " + "weight = ?," + "height = ?," + "bmi = ?"
				+ " where user_id = \"%s\"", user.getUserId());
		connect = DriverManager.getConnection(url);
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.setDouble(1, user.getWeight());// update the users weight in the db
		preparedStatement.setDouble(2, user.getHeight());// update the users height in the db
		preparedStatement.setDouble(3, user.getBMI());// update the users bmi in the db
		preparedStatement.executeUpdate();
		
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connect != null) {
			connect.close();
		}

	}

	/**
	 * Checks the database to see if the event is already a participant of the
	 * event table. The check is performed via a sql query to see if the
	 * event_name, start_time and unique user_id are present in the table. This
	 * function will return true if the event is inside the database
	 * 
	 * @param user The user for gathering the user_id
	 * @param event The event that will be checks for participation in the database
	 * @return True if the event is a participant of the database, false otherwise.
	 * @throws SQLException 
	 */
	private boolean isEventStored(User user, Event event) throws SQLException {
		boolean isThere = false;
		// query to see if the event is stored in the database
		String query = String.format("select * from event where userID = \"%s\""
				+ " and event_name = \"%s\""
				+ " and start_time = %s", 
				user.getUserId(),
				event.getEventName(),
				event.getStartTime().getTimeInMillis());

		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
		// Check to see if the event is stored, will set isThere to true if it is
		if(resultSet.next()) {
			isThere = true;
		}
		
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connect != null) {
			connect.close();
		}
		return isThere;
	}

	/**
	 * Writes a single event to the database. First a check is made to see if the event is already
	 * stored using isEventStored(). If the event is a participant of the database it will be added
	 * to the event table. All datapoints associated with the event will be added to the datapoint
	 * table.
	 * 
	 * @param user The user who the events belong to.
	 * @param event The event that will be uploaded to the database.
	 * @throws SQLException 
	 */
	private void writeEvent(User user, Event event) throws SQLException {
		if (!isEventStored(user, event)) {
			connect = DriverManager.getConnection(url);
			connect.setAutoCommit(false);
			// Write the information about the event to the db
			writeEventInfo(user, event, connect);			
			// Write all datapoints of that event to the db
			for (DataPoint point : event.getDataPoints()) {
				writeDataPoint(user, event, point, connect);
			}
			// commit the query to the db
			connect.commit();
			connect.setAutoCommit(true);
			
			if (connect != null) {
				connect.close();
			}
		}
	}
	


	/**
	 * Writes the event information to the database.
	 * 
	 * @param user The user who the event belongs to
	 * @param event The event thats information will be added to the database
	 * @throws SQLException 
	 */
	private void writeEventInfo(User user, Event event, Connection connect) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"INSERT into event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, user.getUserId()); // user_id
		preparedStatement.setString(2, event.getEventName()); // event_name
		preparedStatement.setLong(3, event.getStartTime().getTimeInMillis());
		preparedStatement.setLong(4, event.getFinishTime().getTimeInMillis());
		preparedStatement.setDouble(5, event.getDistance()); // num_points
		preparedStatement.setDouble(6, event.getMaxSpeed()); // distance
		preparedStatement.setDouble(7, event.getAverageSpeed()); // max_speed
		preparedStatement.setInt(8, event.getMaxHeartRate()); // max_heart_rate
		preparedStatement.setInt(9, event.getAverageHeartRate()); // average_speed
		preparedStatement.setDouble(10, event.getCaloriesBurned()); // average_heart_rate
		preparedStatement.executeUpdate();
		
		if (preparedStatement != null) {
			preparedStatement.close();
		}
	}

	/**
	 * Writes a datapoint to the database.
	 * 
	 * @param event The event that the point belongs to.
	 * @param point The point that will be added to the database.
	 * @throws SQLException 
	 */
	private void writeDataPoint(User user, Event event, DataPoint point, Connection connect) throws SQLException {
		preparedStatement2 = connect.prepareStatement("INSERT into datapoint VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement2.setString(1, user.getUserId()); // user_id to be added to the db
		preparedStatement2.setString(2, event.getEventName()); // event_name to be added to the db
		preparedStatement2.setLong(3, event.getStartTime().getTimeInMillis()); // event_startime 
		preparedStatement2.setLong(4, point.getDate().getTimeInMillis()); // the timepoint 
		preparedStatement2.setInt(5, point.getHeartRate()); // the heart rate 
		preparedStatement2.setDouble(6, point.getLatitude()); // the latitude 
		preparedStatement2.setDouble(7, point.getLongitude()); // the longitude 
		preparedStatement2.setDouble(8, point.getAltitude()); // the altitude 
		preparedStatement2.setDouble(9, point.getSpeed()); // the speed 
		preparedStatement2.setDouble(10, point.getDistance()); // the distance 
		preparedStatement2.setDouble(11, point.getCalories()); // the calories 
		preparedStatement2.setDouble(12, point.getStressLevel()); // the stress 
		preparedStatement2.executeUpdate(); // execute the query/upload to the database
		
		if (preparedStatement2 != null) {
			preparedStatement2.close();
		}
	}
	
	

	
	public static void main(String[] args) { 
		DBWriter dbw = new DBWriter();
		User mocky = User.mockUser();
		mocky.setUserId("9");
		long startTime = System.currentTimeMillis();

		try {
			dbw.writeUser(mocky);
		} catch (SQLException e) {
//			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();

		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		//19607
		//559
	}
}
