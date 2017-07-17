package star16m.jkimutils.db.dao;

import java.util.ArrayList;
import java.util.List;

public class SimpleDataInfoBinder implements ISimpleDataInfoBinder {

	private String tableName;
	private List<String> fieldNameList;
	public SimpleDataInfoBinder(String tableName, String ... fields) {
		this.tableName = tableName.toUpperCase();
		if (fields != null && fields.length > 0) {
			this.fieldNameList = new ArrayList<String>();
			for (String field : fields) {
				this.fieldNameList.add(field.toLowerCase());
			}
		}
	}
	
	public String getTableName() {
		return this.tableName;
	}

	public List<String> getFieldList() {
		return this.fieldNameList;
	}
}
