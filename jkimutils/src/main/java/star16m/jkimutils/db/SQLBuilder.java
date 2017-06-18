package star16m.jkimutils.db;

import java.util.ArrayList;
import java.util.List;

public abstract class SQLBuilder {
	public abstract String getCreateTableSQL(DBTarget tableInfo);
	public abstract String getInsertSQL(DBTarget tableInfo);
	public abstract String getDeleteSQL(DBTarget tableInfo);
	public abstract String getUpdateSQL(DBTarget tableInfo);
	public abstract String getSelectSQL(DBTarget tableInfo);
	public abstract String getDropTableSQL(DBTarget tableInfo);
	public abstract String getColumnType(String columnName);
	
	private static final String PREPARED_PARAM = "?";
	protected static ColumnHandler PREPARED_KEY_COLUMN_HANDLER = new ColumnHandler() {
		@Override
		public String handle(String columnName) {
			return PREPARED_PARAM;
		}
	};
	protected static ColumnHandler COLUMN_VALUE_HANDLER = new ColumnHandler() {
		@Override
		public String handle(String columnName) {
			return columnName + " = " + PREPARED_PARAM;
		}
	};
//	private List<TableInfo> getFieldList(Class<?> clazz) {
//		Field[] fields = clazz.getDeclaredFields();
//		List<TableInfo> simpleColumnList = new ArrayList<TableInfo>();
//		for (Field f : fields) {
//			String fieldType = getColumnType(f.getType().getName());
//			System.out.println(fieldType);
//			if (fieldType != null && fieldType.length() > 0) {
//				simpleColumnList.add(new TableInfo(f.getName(), fieldType));
//			}
//		}
//		return simpleColumnList;
//	}

//	public String CREATE(Class<?> clazz) {
//		List<SimpleField> simpleColumnList = getFieldList(clazz);
//		List<String> fieldNameList = new ArrayList<String>();
//		for(SimpleField f : simpleColumnList) {
//			fieldNameList.add(f.getName() + " " + f.getType());
//		}
//		return String.format(DB.getCreateTableSQL(), clazz.getSimpleName().toUpperCase(), String.join(", ", fieldNameList.toArray(new String[0])));
//	}
//	public String INSERT(Class<?> clazz) {
//		List<SimpleField> simpleColumnList = getFieldList(clazz);
//		List<String> fieldNameList = new ArrayList<String>();
//		for(SimpleField f : simpleColumnList) {
//			fieldNameList.add(f.getName());
//		}
//		return String.format(DB.getInsertSQL(), clazz.getSimpleName().toUpperCase(), String.join(", ", fieldNameList.toArray(new String[0])), getPreparedSymbols(fieldNameList));
//	}
}
