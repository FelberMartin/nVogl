package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Connection {
	private java.sql.Connection connection;
	private static final String TABLENAME = "Game";
	
	@SuppressWarnings("deprecation")
	public Connection() {
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			
			connection = DriverManager.getConnection("jdbc:hsqldb:data");
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}
	
	public void close() { // closes the connection
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTable() { // creates a new table named with "Game"
		String create_str;
		create_str =	"create table " + TABLENAME + " ( " + 
						"playerName VARCHAR(20) NOT NULL PRIMARY KEY, " +
						"highscore INTEGER) ";
		
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(create_str);
			st.close();
			System.out.println("Table created!");
		} catch (SQLException ex) { 
			System.err.println("SQLException: " + ex.getMessage());
		}
	}
	
	private  void  insert(String playerName, int highscore) { //inserts player name and highscore into table named "name"
		String sql_query = ("INSERT INTO " + TABLENAME + " (playerName, highscore) VALUES (" + playerName + ", " + highscore + ")");
		
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(sql_query);
			st.close();
			System.out.println("Data inserted!");
		} catch (Exception ex) {
			System.err.print("SQLException: " + ex.getMessage());
		}
	}
	
	private void createPlayer(String playername) {
		String sql_query = ("INSERT INTO " + TABLENAME + " (playerName, highscore) VALUES ('" + playername + "', 0)");
		
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(sql_query);
			st.close();
			System.out.println("Data inserted!");
		} catch (Exception ex) {
			System.err.print("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void getData() { 
		
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+ TABLENAME );
			
			while (rs.next()) {
				System.out.println(rs.getInt("highscore") + "	" + rs.getString("playerName"));
			}
			
			rs.close();
			st.close();
			System.out.println("Data printed");
		} catch (Exception ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}	
	}
	
	
	public int signUp (String playerName) { 
		int highscore = 0;
		Statement st = null;
		ResultSet rs = null;
		boolean found = false;
		try {
			st = connection.createStatement();
			rs = st.executeQuery("SELECT highscore FROM " + TABLENAME + " WHERE playername = '" + playerName + "'");
			
			while (rs.next()){
				highscore = rs.getInt("highscore"); // high score der Variablen �bergeben 
				found = true;
            }
			if (!found) { // falls es den Spieler noch nicht gibt
				createPlayer(playerName); // neuen Spieler erzeugen
			}
			
				rs.close();
				st.close();

		} catch (Exception ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
			return -1;
		}
		return highscore;
	} 													
	
	public void setHighscore ( String playerName, int score)  { // Setzt Highscore von Spieler auf score
		String sql_query = ("UPDATE " + TABLENAME + " SET highscore =" + score + " WHERE playername = '" + playerName + "'");
		
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(sql_query);
			st.close();
			System.out.println("Data inserted! New Highscore: " + score);
		} catch (Exception ex) {
			System.err.print("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public HashMap<String, Integer> getHighscoreList () { // return Hashmap mit allen playernames und ihren Highscores 
		HashMap<String, Integer> map = new HashMap<String, Integer>(); 
		
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+TABLENAME);
			
			while (rs.next()) {
				map.put( rs.getString("playerName") , rs.getInt("highscore") );  // Daten in HashMap �berschreiben
			}
			rs.close();
			st.close();
			System.out.println("Data transmitted to HashMap");
		} catch (Exception ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}	
//		map.put( "name", 1 ); 
//		map.put( "email", 1 );
		return map;
	}
	
}
