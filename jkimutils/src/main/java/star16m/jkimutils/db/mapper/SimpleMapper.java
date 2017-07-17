package star16m.jkimutils.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import star16m.jkimutils.db.dao.ISimpleDataInfoBinder;

public interface SimpleMapper {

	@SelectProvider(type = SimpleSQLProvider.class, method = "query")
	public List<Map<String, String>> query(@Param("sql") String sql);

	@SelectProvider(type = SimpleSQLProvider.class, method = "select")
	public List<Map<String, String>> select(@Param("binder") ISimpleDataInfoBinder infoBinder, @Param("criteria") String criteria);
	
	@InsertProvider(type = SimpleSQLProvider.class, method = "insert")
	public boolean insert(@Param("binder") ISimpleDataInfoBinder infoBinder, @Param("data") Object data);

	@DeleteProvider(type = SimpleSQLProvider.class, method = "delete")
	public boolean delete(@Param("binder") ISimpleDataInfoBinder infoBinder, @Param("data") Object data);
	
	@UpdateProvider(type = SimpleSQLProvider.class, method = "create")
	public void create(@Param("binder") ISimpleDataInfoBinder infoBinder);
	
	@UpdateProvider(type = SimpleSQLProvider.class, method = "drop")
	public void drop(@Param("binder") ISimpleDataInfoBinder infoBinder);
}
