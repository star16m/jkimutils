package star16m.jkimutils.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBTarget {
	public String name;
	public List<String> columnList;
	public Map<String, String> columnTypeMap;

	public DBTarget(String name) {
		this.name = name;
		this.columnList = new ArrayList<String>();
		this.columnTypeMap = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void add(String columnName, String columnType) {
		this.columnList.add(columnName);
		this.columnTypeMap.put(columnName, columnType);
	}
	
	public List<String> getColumnList() {
		return this.columnList;
	}
	public List<String> getColumnList(ColumnHandler handler) {
		List<String> handledColumnList = new ArrayList<String>();
		for (String c : this.columnList) {
			handledColumnList.add(handler.handle(c));
		}
		return handledColumnList;
	}
	public String getColumnType(String columnName) {
		return this.columnTypeMap.get(columnName);
	}
}