package star16m.jkimutils.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.ibatis.session.SqlSession;

import star16m.jkimutils.Menu;
import star16m.jkimutils.contents.Contents;
import star16m.jkimutils.contents.Header;
import star16m.jkimutils.db.DBManager;
import star16m.jkimutils.db.mapper.MenuMapper;

@Path("/")
public class Main {

	public static final File RESOURCE_BASE_DIRECTORY = new File("resources");
	private static String MAIN_CONTENTS;
	static {
		File file = new File(RESOURCE_BASE_DIRECTORY, "main.html");
		BufferedReader br = null;
		String line = null;
		StringBuffer sb = new StringBuffer();
		String newLine = System.lineSeparator();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			while((line = br.readLine()) != null) {
				sb.append(line).append(newLine);
			}
			MAIN_CONTENTS = sb.toString();
			List<Menu> menuList = getMenuList();
			sb = new StringBuffer();
			for (Menu menu : menuList) {
				sb.append(menu.getHTMLString()).append(newLine);
			}
			MAIN_CONTENTS = MAIN_CONTENTS.replaceAll("#MENU#", sb.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	@GET
	@Path("/")
	public Response getRoot() throws Exception {
		return Response.seeOther(UriBuilder.fromUri("/main/").build()).build();
	}

	@GET
	@Path("/main/{page : .*}")
	public Response getMain(@PathParam("page") String page) throws Exception {
		System.out.println("call main -> page[" + page + "]");
		return Response.ok(MAIN_CONTENTS).build();
	}
	@GET
	@Path("/main/contents/{page : .*}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Contents getContents(@PathParam("page") String page) throws Exception {
		System.out.println("call contents -> page[" + page + "]");
		Contents contents = new Contents("haha", "title from contents", "this is description");
		contents.setHeader(Arrays.asList(new Header(0, "title-123"), new Header(1, "title-abc")));
		contents.setData(Arrays.asList(new String[] {"haha1", "hoho1"}, new String[] {"haha2", "hoho2"}));
		return contents;
	}

	private static List<Menu> getMenuList() throws Exception {
		DBManager dbManager = DBManager.getInstance();
		SqlSession session = dbManager.getSQLSession();
		MenuMapper<Menu> mapper = (MenuMapper<Menu>) session.getMapper(MenuMapper.class);
		List<Menu> list = session.selectList("star16m.jkimutils.db.mapper.MenuMapper.getAllItems", "Menu");
//		dbManager.select();
		
//		List<Menu> list = Arrays.asList(new Menu("haha", "/main/t1"), new Menu("aaaa", "/main/t2"));
		return list;
	}
}