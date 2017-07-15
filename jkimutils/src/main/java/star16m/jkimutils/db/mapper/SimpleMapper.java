package star16m.jkimutils.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

public interface SimpleMapper {

	@SelectProvider(type=SimpleSQLProvider.class, method="select")
	public List<Map<String, String>> findAll(Map<String, String> parameter);
	
	@SelectProvider(type=SimpleSQLProvider.class, method="insert")
	public boolean insert(Map<String, String> parameter);
}
