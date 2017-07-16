package star16m.jkimutils.db.dao;

import java.util.Arrays;
import java.util.List;

public class SimpleDataInfoBinder implements ISimpleDataInfoBinder {

	private String tableName;
	private List<String> fieldNameList;
	public SimpleDataInfoBinder(String tableName, String ... fields) {
		this.tableName = tableName;
		this.fieldNameList =  Arrays.asList(fields);
	}
	
	public String getTableName() {
		return this.tableName;
	}

	public List<String> getFieldList() {
		return this.fieldNameList;
	}
}
