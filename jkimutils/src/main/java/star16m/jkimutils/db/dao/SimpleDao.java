package star16m.jkimutils.db.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import star16m.jkimutils.db.DBManager;
import star16m.jkimutils.db.mapper.SimpleMapper;

public class SimpleDao {

	private ISimpleDataInfoBinder infoBinder;
	public SimpleDao(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		List<String> fieldList = new ArrayList<String>();
		for (Method method : methods) {
			if (method.getName().toUpperCase().startsWith("SET")) {
				fieldList.add(method.getName().substring(3).toLowerCase());
			}
		}
		this.infoBinder = new SimpleDataInfoBinder(clazz.getSimpleName(), fieldList.toArray(new String[0]));
	}

	public SimpleDao(ISimpleDataInfoBinder binder) {
		this.infoBinder = binder;
	}
	public List<Map<String, String>> select() {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper =  session.getMapper(SimpleMapper.class);
		List<Map<String, String>> list = mapper.select(this.infoBinder);
		session.close();
		return list;
	}
	public List<Map<String, String>> query(String sql) {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper =  session.getMapper(SimpleMapper.class);
		List<Map<String, String>> list = mapper.query(sql);
		session.close();
		return list;
	}
	public boolean insert(Object object) {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper =  session.getMapper(SimpleMapper.class);
		boolean insertValue = mapper.insert(this.infoBinder, object);
		session.commit();
		return insertValue;
	}
	public boolean delete(Object object) {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper =  session.getMapper(SimpleMapper.class);
		boolean deleteValue = mapper.delete(this.infoBinder, object);
		session.commit();
		return deleteValue;
	}
}
