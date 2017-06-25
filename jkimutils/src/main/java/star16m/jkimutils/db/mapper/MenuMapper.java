package star16m.jkimutils.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import star16m.jkimutils.Menu;

public interface MenuMapper<T> {

	@Select(value="SELECT * FROM ${tableName}")
    public List<T> getAllItems(@Param("tableName") String tableName);
	
	@Select("SELECT * FROM Menu WHERE NAME like '%' || #{name} || '%'")
	public List<Menu> getItemBy(@Param("name") String name);
	
	@Insert("INSERT INTO Menu(name, link) values (#{name}, #{link})")
	public void insert(Menu menu);

	@Delete("DELETE FROM Menu WHERE NAME like '%' || #{name} || '%'")
	public void delete(@Param("name") String name);
}
