package star16m.jkimutils.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

import star16m.jkimutils.Menu;

public class SimpleSQLProvider extends SQL {

	public String select(Map<String, String> parameter) {
		if (parameter == null || !parameter.containsKey("TABLE")) {
			throw new RuntimeException();
		}
		String tableName = parameter.get("TABLE");
		String fields = "*";
		if (parameter.containsKey("FIELDS")) {
			fields = parameter.get("FIELDS");
		}
		String where = "";
		if (parameter.containsKey("WHERE")) {
			where = " WHERE " + parameter.get("WHERE");
		}
		return "SELECT " + fields + " FROM " + tableName + where;
	}
	
	public String insert(Map<String, String> parameter) {
		if (parameter == null || !parameter.containsKey("TABLE")) {
			throw new RuntimeException();
		}
		String tableName = parameter.get("TABLE");
		String fields = "*";
		if (parameter.containsKey("FIELDS")) {
			fields = parameter.get("FIELDS");
		}
		String where = "";
		if (parameter.containsKey("WHERE")) {
			where = " WHERE " + parameter.get("WHERE");
		}
		return "SELECT " + fields + " FROM " + tableName + where;
	}
//	@Select("SELECT name, link FROM Menu")
//    public List<Menu> findAll();
//	
//	@Select("SELECT * FROM ${tableName} WHERE NAME ${key} '%' || #{value} || '%'")
//	public List<Map<String, Object>> getItemBy(@Param("tableName") String tableName, @Param("key") String key, @Param("value") String value);
//	
//	@Insert("INSERT INTO Menu(name, link) values (#{name}, #{link})")
//	public boolean insert(Menu menu);
//
//	@Delete("DELETE FROM Menu WHERE NAME like '%' || #{name} || '%'")
}
