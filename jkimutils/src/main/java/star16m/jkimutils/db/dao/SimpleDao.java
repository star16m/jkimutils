package star16m.jkimutils.db.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import star16m.jkimutils.db.DBManager;
import star16m.jkimutils.db.mapper.SimpleMapper;

public class SimpleDao {

	private Map<String, String> parameter = new HashMap<String, String>();
	public SimpleDao(Class<?> clazz) {
		this.parameter.put("TABLE", clazz.getSimpleName());
		Method[] methods = clazz.getDeclaredMethods();
		List<String> fieldList = new ArrayList<String>();
		for (Method method : methods) {
			if (method.getName().toUpperCase().startsWith("SET")) {
				fieldList.add(method.getName().substring(3).toLowerCase());
			}
		}
		this.parameter.put("FIELDS", fieldList.toString().replaceAll("[\\[\\]]", ""));
	}

	public List<Map<String, String>> findAll() {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper =  session.getMapper(SimpleMapper.class);
		List<Map<String, String>> list = mapper.findAll(this.parameter);
		session.close();
		return list;
	}
}
