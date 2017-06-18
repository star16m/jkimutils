package star16m.jkimutils.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.h2.jdbcx.JdbcDataSource;

import star16m.jkimutils.Menu;
import star16m.jkimutils.Menu2;

public class DBManager<T> {
	private final static String DRIVER_URL = "jdbc:h2:file:C:\\data\\tmp\\tmp";
	private final static String DRIVER_NAME = "org.h2.Driver";
	private final static String USER_NAME = "sa";
	private final static String PASSWORD = "";
	
	private Class<T> clazz;
	private Connection connection;
	private SQLBuilder sqlBuilder;
	private DBTarget targetInfo;

	private DBManager(Class<T> clazz, Connection connection, SQLBuilder sqlBuilder) throws Exception {
		this.connection = connection;
		this.clazz = clazz;
		this.sqlBuilder = sqlBuilder;
		this.targetInfo = getTargetInfo();
	}
	private DBTarget getTargetInfo() {
		DBTarget tableInfo = new DBTarget(clazz.getSimpleName().toUpperCase());
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			String fieldType = sqlBuilder.getColumnType(f.getType().getName());
			System.out.println(fieldType);
			if (fieldType != null && fieldType.length() > 0) {
				tableInfo.add(f.getName().toUpperCase(), fieldType);
			}
		}
		
		Constructor<?>[] cons = this.clazz.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			System.out.println(cons);
		}
		### THIS### RESET DEFAULT INVISIBLE CONSTRUCTOR
		return tableInfo;
	}
	public static void main(String[] args) throws Exception {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(DRIVER_URL);
		dataSource.setUser(USER_NAME);
		dataSource.setPassword(PASSWORD);
		DBManager<Menu> db = new DBManager<Menu>(Menu.class, dataSource.getConnection(), new H2SQLBuilder());
		db.create();
		Menu menu1 = new Menu("test menu1", "testlink1");
		Menu menu2 = new Menu("test menu2", "testlink2");
		db.insert(menu1);
		db.insert(menu2);
		List<Menu> results = db.select();
		for (int i=0; i<results.size(); i++) {
			Menu map = results.get(i);
			System.out.println("[" + map.getName() + "] -> " + map.getLink());
		}
		System.out.println("Total size:" + results.size());
		System.out.println("======= delete");
		db.delete(menu2);
		results = db.select();
		for (int i=0; i<results.size(); i++) {
			Menu map = results.get(i);
			System.out.println("[" + map.getName() + "] -> " + map.getLink());
		}
		
		db.update(menu1, new Menu("hahha renamed menu1", menu1.getLink()));
		results = db.select();
		for (int i=0; i<results.size(); i++) {
			Menu map = results.get(i);
			System.out.println("[" + map.getName() + "] -> " + map.getLink());
		}
		System.out.println("===========================");
		Menu2 m = new Menu2("11", "hahaha", "hhhh");
		DBManager<Menu2> db2 = new DBManager<Menu2>(Menu2.class, dataSource.getConnection(), new H2SQLBuilder());
		db2.create();
		System.out.println("create menu2");
		db2.insert(m);
		System.out.println("insert menu2");
		List<Menu2> r = db2.select();
		System.out.println("select menu2");
		for (int i=0; i<r.size(); i++) {
			Menu2 map = r.get(i);
			System.out.println("[" + map + "] -> ");
		}
	}
	public boolean create() throws Exception {
		
		String query = sqlBuilder.getCreateTableSQL(this.targetInfo);
		System.out.println(query);
		int result = new QueryRunner().update(this.connection, query);
		return result > 0;
	}
	public int insert(T i) throws Exception {
		String query = sqlBuilder.getInsertSQL(this.targetInfo);
		List<Object> fieldValues = new ArrayList<Object>();
		Field[] fields = i.getClass().getDeclaredFields();
        for (Field f: fields) {
        	f.setAccessible(true);
        	fieldValues.add(f.get(i));
        }
		
		int result = new QueryRunner().update(this.connection, query, fieldValues.toArray());
		return result;
	}
	public int delete(T i) throws Exception {
		String query = sqlBuilder.getDeleteSQL(this.targetInfo);
		List<Object> fieldValues = new ArrayList<Object>();
		Field[] fields = i.getClass().getDeclaredFields();
        for (Field f: fields) {
        	f.setAccessible(true);
        	fieldValues.add(f.get(i));
        }
		
		int result = new QueryRunner().update(this.connection, query, fieldValues.toArray());
		return result;
	}
	public int update(T o, T n) throws Exception {
		String query = sqlBuilder.getUpdateSQL(this.targetInfo);
		List<Object> fieldValues = new ArrayList<Object>();
        Field[] nfields = n.getClass().getDeclaredFields();
        for (Field f: nfields) {
        	f.setAccessible(true);
        	fieldValues.add(f.get(n));
        }
        Field[] ofields = o.getClass().getDeclaredFields();
        for (Field f: ofields) {
        	f.setAccessible(true);
        	fieldValues.add(f.get(o));
        }
        System.out.println("update:" + query);
        for (Object v : fieldValues) {
        	System.out.println("rename -> " + v);
        }
        int result = new QueryRunner().update(this.connection, query, fieldValues.toArray());
		return result;
	}
	public List<T> select() throws Exception {
		return new QueryRunner().query(this.connection, "SELECT * FROM " + this.targetInfo.getName(), new BeanListHandler<T>(clazz));
	}

}
