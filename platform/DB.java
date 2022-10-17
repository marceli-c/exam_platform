package platform;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DB {

	private final String url="jdbc:mysql://localhost:3306/quizy";
	private final String log="admin";
	private final String has="admin";
	
	private Connection con;
	private Statement statement;
	
	DB() throws SQLException{
		
		con=DriverManager.getConnection(url,log,has);
		statement=con.createStatement();
		
	}
	
	private ResultSet ResultSet(String query) throws SQLException {
		return statement.executeQuery(query);
	}
	
	public String getString(String query,String column) throws SQLException {
		String result="";
		ResultSet ResultSet=ResultSet(query);
		while(ResultSet.next()) {
			result+=ResultSet.getString(column);
		}
		return result;
	}
	public int getInt(String query,String column) throws SQLException{
		int result=0;
		ResultSet ResultSet=ResultSet(query);
		while(ResultSet.next()) {
			result=ResultSet.getInt(column);
		}
		return result;
		
	}
	public void executeQuery(String query) throws SQLException {
		statement.execute(query);
	}
	public boolean getBoolean(String query,String column) throws SQLException {
		ResultSet ResultSet=ResultSet(query);
		while(ResultSet.next()) {
			return ResultSet.getBoolean(column);
			
		}
		return false;
	}
	public List<String> getStringArray(String query,String column) throws SQLException{
		List<String> list=new ArrayList<String>();
		ResultSet ResultSet=ResultSet(query);
		while(ResultSet.next()) {
			list.add(ResultSet.getString(column));
		}
		return list;
	}
	public List<Integer> getIntegerArray(String query,String column) throws SQLException{
		List<Integer> list=new ArrayList<Integer>();
		ResultSet ResultSet=ResultSet(query);
		while(ResultSet.next()) {
			list.add(ResultSet.getInt(column));
		}
		return list;
	}
	
}
