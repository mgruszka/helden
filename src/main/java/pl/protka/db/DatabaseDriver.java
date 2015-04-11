package pl.protka.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class DatabaseDriver {

	private static DatabaseDriver dbDriver = null;
	private static Connection conn = null;
	
	private static String relation_box_table = "relation_box";
	private static String relation_text_table = "relation_text";
	private static String country_box_table = "country_box";
	private static String country_text_table = "country_text";
	private static String city_box_table = "city_box";
	private static String city_text_table = "city_text";
	private static String uni_box_table = "uni_box";
	private static String uni_text_table = "uni_text";
	

	private DatabaseDriver() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		/*
		 * conn = DriverManager
		 * .getConnection("jdbc:mysql://87.206.242.153/helden?" +
		 * "user=root&password=asus1234");
		 */

		conn = DriverManager.getConnection("jdbc:mysql://10.0.0.4/helden?"
				+ "user=root&password=asus1234");
	}

	public static DatabaseDriver getInstance() {
		if (dbDriver == null) {
			try {
				dbDriver = new DatabaseDriver();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dbDriver;
	}
	
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		DatabaseDriver dbdriver = DatabaseDriver.getInstance();

		//dbdriver.savePerson("gruszka-pajac3", "2000-2-14", null,1);
		
		//Map<Integer,String> lista = dbdriver.getPeople();		
		//System.out.println(lista);
		
		// System.out.println(dbdriver.getPersonID("gruszka-pajac2"));
		
		dbdriver.saveUniFromText(2, "university2");
		
		dbdriver.close();

	}

	public int getPersonID(String name) {
		int id = -1;
		try {
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT ID FROM person where name like ?");
			preparedStatement.setString(1, name);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return id;
	}


	public List<String> getPeopleNotCrowled(CrawledSource source) {

		String sql = "SELECT name from person where " + source.dbFiled + " = 0";
		PreparedStatement preparedStatement;
		List<String> names = new LinkedList<String>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				names.add(name);
			}
			preparedStatement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return names;

	}

	
	//TO DO
	public int savePerson(PersonEntity person){
		
		return 0;
	}
	
	
	//TO DO
	public void setCrawled(int personID, CrawledSource source){
		
	}
	
	
	//nieaktualne jeszcze
	public int savePerson2(String name, String birthDate, String deathDate,
			int crawled) {

		int id = -1;
		int crawledDB = 0;

		String sql = "SELECT ID,crawled from person where name like ?";
		PreparedStatement preparedStatement;

		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt(1);
				crawledDB = resultSet.getInt(2);
			}
			preparedStatement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		if (crawledDB != 0) {
			// character crawled and has ID, so return it
			System.out.println("savePerson: " + name + " already crowled.");
			System.out.println("Person id is: " + id);
		} else {

			if (id == -1) {
				// character doesnt exist in DB, insert it and return new ID
				try {
					sql = "INSERT INTO person (name, birth_date, death_date,crawled)"
							+ "VALUES (?, ?, ?,?)";
					preparedStatement = conn.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, birthDate);
					preparedStatement.setString(3, deathDate);
					preparedStatement.setInt(4, crawled);
					preparedStatement.execute();
					ResultSet rs = preparedStatement.getGeneratedKeys();

					if (rs.next()) {
						id = rs.getInt(1);
					}
					preparedStatement.close();
					System.out.println("Successfully added person with id: "
							+ id);
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					System.out.println("Unable to save Person " + name);
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
			} else {
				// character exist in DB but wasnt crawled, update it
				try {
					sql = "UPDATE person set ID = ?, name = ?, birth_date = ?, death_date = ?, crawled = ? where ID = ?";
					preparedStatement = conn.prepareStatement(sql);
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3, birthDate);
					preparedStatement.setString(4, deathDate);
					preparedStatement.setInt(5, crawled);
					preparedStatement.setInt(6, id);
					preparedStatement.executeUpdate();
					
					preparedStatement.close();
					System.out.println("Successfully updated person with id: "
							+ id);
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					System.out.println("Unable to update person " + name);
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
			}
		}
		return id;

	}

	
	public void saveCountryFromBox(int personID, String countryName){
		saveForPerson(personID,country_box_table,countryName);
	}
	
	public void saveCountryFromText(int personID, String countryName){
		saveForPerson(personID,country_text_table,countryName);
	}
	
	public void saveCityFromBox(int personID, String cityName) {
		saveForPerson(personID,city_box_table,cityName);
	}
	
	public void saveCityFromText(int personID, String cityName) {
		saveForPerson(personID,city_text_table,cityName);
	}
	
	public void saveUniFromBox(int personID, String university) {
		saveForPerson(personID,uni_box_table,university);
	}
	
	public void saveUniFromText(int personID, String university) {
		saveForPerson(personID,uni_text_table,university);
	}
		
	
	private void saveForPerson(int personID, String tableName, String value) {

		try {
			String sql = "INSERT INTO " + tableName
					+ " (PERSON_ID, name)"
					+ "VALUES (?, ?)";
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, personID);
			preparedStatement.setString(2, value);
			System.out.println("Executing query: " + preparedStatement.toString());
			preparedStatement.execute();	
			preparedStatement.close();
			System.out.println("Successfully added: " + value + " to table: " + tableName);

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("Unable to saved: " + value + " to table: " + tableName);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	
	
	public void saveRelationFromBox(int personID, int personID2)  {
		saveRelation(personID,personID2,relation_box_table);
	}
	
	public void saveRelationFromText(int personID, int personID2)  {
		saveRelation(personID,personID2,relation_text_table);
	}
	
	private void saveRelation(int personID, int personID2, String tableName) {

		try {
			String sql = "INSERT INTO " + tableName
					+ " (PERSON_ID, PERSON_ID2) VALUES (?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, personID);
			preparedStatement.setInt(2, personID2);
			System.out.println("Executing query: " + preparedStatement.toString());
			preparedStatement.execute();
			preparedStatement.close();
			System.out.println("Successfully added relation: " + personID
					+ " with " + personID2 + " to table " + tableName);

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("Unable to save relation: " + personID
					+ " with " + personID2 + " to table " + tableName);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public Map<Integer,String> getPeople() {

		String sql = "SELECT ID,name from person";
		PreparedStatement preparedStatement;
		Map<Integer,String> people = new LinkedHashMap<Integer,String>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int ID = resultSet.getInt("ID");
				String name = resultSet.getString("name");
				people.put(ID,name);
			}
			preparedStatement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return people;
	}
	
	
/*	public List<Relation> getRelations(){
		
		List<Relation> relations = new ArrayList<Relation>();
		String sql = "SELECT PERSON_ID,PERSON_ID2 from relations";
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int ID = resultSet.getInt("PERSON_ID");
				int ID2 = resultSet.getInt("PERSON_ID2");
				Relation r = new Relation();
				r.setID1(ID);
				r.setID2(ID2);
				relations.add(r);
			}
			preparedStatement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return relations;
	}*/
	
	

	/*
	 * public void listPeople(){ while (resultSet.next()) { String user =
	 * resultSet.getString("myuser"); String website =
	 * resultSet.getString("webpage"); String summary =
	 * resultSet.getString("summary"); Date date = resultSet.getDate("datum");
	 * String comment = resultSet.getString("comments");
	 * System.out.println("User: " + user); System.out.println("Website: " +
	 * website); System.out.println("Summary: " + summary);
	 * System.out.println("Date: " + date); System.out.println("Comment: " +
	 * comment); } }
	 */



}

