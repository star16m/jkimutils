package star16m.jkimutils.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

public class DBManager {
	private final static String DRIVER_URL = "jdbc:h2:file:";
	private final static String DRIVER_NAME = "org.h2.Driver";
	private final static String USER_NAME = "sa";
	private final static String PASSWORD = "";
	private Connection connection;
	private DBManager dbManager;
	private static String dropTableStatement = "DROP TABLE %s;";
    private static String createTableStatement = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static String INSERT_TABLE_STATEMENT = "INSERT INTO %s ( %s ) VALUES ( %s )";
	
	public DBManager(String driverPath) throws Exception {
		if (driverPath == null) {
			throw new FileNotFoundException("Not found driver path.");
		}
		File driverPathDirectory = new File(driverPath);
		if (!driverPathDirectory.exists() || !driverPathDirectory.isDirectory()) {
			throw new FileNotFoundException("Not found driver path. [" + driverPath + "]");
		}
		File driverFile = new File(driverPathDirectory, "jkimdb");
		System.out.println("Driver file as follows. [" + driverFile.getAbsolutePath() + "]");
		Class.forName(DRIVER_NAME);
		try {
			this.connection = DriverManager.getConnection(DRIVER_URL + driverFile.getAbsolutePath(), USER_NAME,	PASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection Error.\n" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		DBManager db = new DBManager("C:\\data\\tmp");
		db.create("jkim_menu", "name", "link");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "haha-menu");
		map.put("link", "hoho-link");
		db.insert("jkim_menu", map);
	}
	public boolean create(String tableName, String ... columns) throws Exception {
		List<String> columnList = new ArrayList<String>();
		for (String c : columns) {
			columnList.add(c + " varchar(255)");
		}
		String query = String.format(createTableStatement, tableName, String.join(", ", columnList.toArray(new String[0])));
		PreparedStatement stmt = this.connection.prepareStatement(query);
        stmt.executeUpdate();
        stmt = this.connection.prepareStatement("show columns from " + tableName);
        stmt.close();
		return true;
	}
	public boolean insert(String tableName, Map<String, String> insertValue) throws Exception {
		
	}
	public Map<String, Object> select() throws Exception {
		MapHandler mapHandler = new MapHandler();
		QueryRunner runner = new QueryRunner();

		Map<String, Object> resultMap = runner.query(this.connection, "SELECT * FROM jkim_menu", mapHandler);
		return resultMap;
	}

}
