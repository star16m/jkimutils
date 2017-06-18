package star16m.jkimutils.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H2SQLBuilder extends SQLBuilder {
	private static final Map<String, String> FIELD_MAP = new HashMap<String, String>();
	static {
		FIELD_MAP.put("int", "INT");
		FIELD_MAP.put("long", "INT");
		FIELD_MAP.put("double", "DOUBLE");
		FIELD_MAP.put("float", "FLOAT");
		FIELD_MAP.put("boolean", "BOOLEAN");
		FIELD_MAP.put("java.lang.Integer", "INT");
		FIELD_MAP.put("java.lang.Long", "INT");
		FIELD_MAP.put("java.lang.Double", "DOUBLE");
		FIELD_MAP.put("java.lang.Float", "FLOAT");
		FIELD_MAP.put("java.lang.Boolean", "BOOLEAN");
		FIELD_MAP.put("java.lang.String", "varchar(255)");
	};
	public String getCreateTableSQL(DBTarget tableInfo) {
		List<String> columnFullDeclared = new ArrayList<String>();
		for (String column : tableInfo.getColumnList()) {
			columnFullDeclared.add(column + " " + tableInfo.getColumnType(column));
		}
		return String.format("CREATE TABLE IF NOT EXISTS %s ( %s )", tableInfo.getName(), String.join(", ", columnFullDeclared.toArray(new String[0])));
	}
	public String getInsertSQL(DBTarget tableInfo) {
		return String.format("INSERT INTO %s ( %s ) VALUES ( %s )", tableInfo.getName(), String.join(", ", tableInfo.getColumnList().toArray(new String[0])), String.join(", ", tableInfo.getColumnList(PREPARED_KEY_COLUMN_HANDLER).toArray(new String[0])));
	}
	public String getDeleteSQL(DBTarget tableInfo) {
		return String.format("DELETE FROM %s WHERE %s ", tableInfo.getName(), String.join(" AND ", tableInfo.getColumnList(COLUMN_VALUE_HANDLER).toArray(new String[0])));
	}
	public String getUpdateSQL(DBTarget tableInfo) {
		return String.format("UPDATE %s SET %s WHERE %s", tableInfo.getName(), String.join(", ", tableInfo.getColumnList(COLUMN_VALUE_HANDLER).toArray(new String[0])), String.join(" AND ", tableInfo.getColumnList(COLUMN_VALUE_HANDLER).toArray(new String[0])));
	}
	public String getSelectSQL(DBTarget tableInfo) {
		return "SELECT * FROM %s WHERE %s";
	}
	public String getDropTableSQL(DBTarget tableInfo) {
		return "DROP TABLE IF EXISTS %s";
	}
	public String getColumnType(String columnName) {
		return FIELD_MAP.get(columnName);
	}
}
