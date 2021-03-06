package star16m.jkimutils.db.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import star16m.jkimutils.db.dao.ISimpleDataInfoBinder;

public class SimpleSQLProvider {

	private static String SELECT_FORMAT = "SELECT %s FROM %s WHERE 1=1 %s";
	private static String INSERT_FORMAT = "INSERT INTO %s ( %s ) VALUES ( %s )";
	private static String DELETE_FORMAT = "DELETE FROM %s WHERE %s";
	private static String CREATE_FORMAT = "CREATE TABLE IF NOT EXISTS %s ( %s )";
	private static String DROP_FORMAT   = "DROP TABLE IF EXISTS %s";
	public String select(Map<String, Object> params) {
		ISimpleDataInfoBinder binder = (ISimpleDataInfoBinder) params.get("binder");
		String where = "";
		if (params.containsKey("criteria") && params.get("criteria") != null && ((String)params.get("criteria")).length() > 0) {
			where = "AND " + (String) params.get("criteria");
		}
		String fields = String.join(", ", binder.getFieldList());
		return String.format(SELECT_FORMAT, fields, binder.getTableName(), where);
	}
	public String query(Map<String, Object> params) {
		String sql = (String) params.get("sql");
		return sql;
	}
	
	public String insert(Map<String, Object> params) {
		ISimpleDataInfoBinder binder = (ISimpleDataInfoBinder) params.get("binder");
		String fields = String.join(", ", binder.getFieldList());
		List<String> valueList = new ArrayList<String>();
		for (String field : binder.getFieldList()) {
			valueList.add("#{data." + field + "}");
		}
		return String.format(INSERT_FORMAT, binder.getTableName(), fields, String.join(", ", valueList));
	}
	public String delete(Map<String, Object> params) {
		ISimpleDataInfoBinder binder = (ISimpleDataInfoBinder) params.get("binder");
		List<String> valueList = new ArrayList<String>();
		for (String field : binder.getFieldList()) {
			valueList.add(field + " = #{data." + field + "}");
		}
		return String.format(DELETE_FORMAT, binder.getTableName(), String.join(" AND ", valueList));
	}
	public String create(Map<String, Object> params) {
		ISimpleDataInfoBinder binder = (ISimpleDataInfoBinder) params.get("binder");
		List<String> valueList = new ArrayList<String>();
		for (String field : binder.getFieldList()) {
			valueList.add(field + " varchar ");
		}
		return String.format(CREATE_FORMAT, binder.getTableName(), String.join(", ", valueList));
	}
	
	public String drop(Map<String, Object> params) {
		ISimpleDataInfoBinder binder = (ISimpleDataInfoBinder) params.get("binder");
		return String.format(DROP_FORMAT, binder.getTableName());
	}
}
