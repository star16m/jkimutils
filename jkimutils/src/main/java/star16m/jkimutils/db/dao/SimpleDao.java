package star16m.jkimutils.db.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import star16m.jkimutils.Application.ApplicationConfig;
import star16m.jkimutils.db.DBManager;
import star16m.jkimutils.db.mapper.SimpleMapper;

public class SimpleDao {

	private ApplicationConfig applicationConfig;
	private ISimpleDataInfoBinder infoBinder;

	public SimpleDao(ApplicationConfig applicationConfig, ISimpleDataInfoBinder infoBinder) {
		this.applicationConfig = applicationConfig;
		this.infoBinder = infoBinder;
	}
	public SimpleDao(ApplicationConfig applicationConfig, Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		List<String> fieldList = new ArrayList<String>();
		for (Method method : methods) {
			if (method.getName().toUpperCase().startsWith("SET")) {
				fieldList.add(method.getName().substring(3).toLowerCase());
			}
		}
		this.applicationConfig = applicationConfig;
		this.infoBinder = new SimpleDataInfoBinder(clazz.getSimpleName().toUpperCase(), fieldList.toArray(new String[0]));
	}

	public void create() {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		mapper.create(this.infoBinder);
		session.close();
	}
	
	public void drop() {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		mapper.drop(this.infoBinder);
		session.close();
	}

	public List<Map<String, String>> select() {
		return select(null);
	}

	public List<Map<String, String>> select(String criteria) {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		List<Map<String, String>> list = mapper.select(this.infoBinder, criteria);
		session.close();
		return list;
	}

	public List<Map<String, String>> query(String sql) {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		List<Map<String, String>> list = mapper.query(sql);
		session.close();
		return list;
	}

	public boolean insert(Object object) {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		boolean insertValue = mapper.insert(this.infoBinder, object);
		session.commit();
		return insertValue;
	}
	public boolean insert(String ... values) {
		System.out.println("values.length:" + values.length);
		System.out.println("field.size():" + this.infoBinder.getFieldList().size());
		if (values == null || values.length != this.infoBinder.getFieldList().size()) {
			throw new IllegalArgumentException();
		}
		Map<String, String> valueMap = new HashMap<String, String>();
		for (int i = 0; i < values.length; i++) {
			valueMap.put(this.infoBinder.getFieldList().get(i), values[i]);
		}
		System.out.println("valueMap:::" + valueMap);
		return insert(valueMap);
	}

	public boolean delete(Object object) {
		DBManager dbManager = DBManager.getInstance(this.applicationConfig);
		SqlSession session = dbManager.getSQLSession();
		SimpleMapper mapper = session.getMapper(SimpleMapper.class);
		boolean deleteValue = mapper.delete(this.infoBinder, object);
		session.commit();
		return deleteValue;
	}
	
}
