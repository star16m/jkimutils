package star16m.jkimutils.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import star16m.jkimutils.Application;
import star16m.jkimutils.Menu;
import star16m.jkimutils.contents.Contents;
import star16m.jkimutils.contents.Header;
import star16m.jkimutils.db.dao.SimpleDao;
import star16m.jkimutils.db.dao.SimpleDataInfoBinder;

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
			// generate main contents
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				sb.append(line).append(newLine);
			}
			MAIN_CONTENTS = sb.toString();
			// generate menu contents
			SimpleDao menuDao = new SimpleDao(Application.getConfig(), new SimpleDataInfoBinder("Menu", "NAME", "LINK"));
			menuDao.drop();
			menuDao.create();
			menuDao.insert(new Menu("ttt", "http://localhost:9999/haha"));
			menuDao.insert(new Menu("AAA", "http://localhost:9999/AAA"));
			menuDao.insert(new Menu("BBB", "http://localhost:9999/BBB"));
			List<Map<String, String>> menuList = menuDao.select();
			menuList = menuDao.query("select * from menu");
			sb = new StringBuffer();
			for (Map<String, String> map : menuList) {
				sb.append("<li><a href='" + map.get("LINK") + "'>" + map.get("NAME") + "</a></li>").append(newLine);
			}
			MAIN_CONTENTS = MAIN_CONTENTS.replaceAll("#MENU#", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
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
		return Response.ok(MAIN_CONTENTS).build();
	}

	@GET
	@Path("/main/contents/{page : .*}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Contents getContents(@PathParam("page") String page) throws Exception {
		Contents contents = new Contents("haha", "title from contents", "this is description");
		contents.setHeader(Arrays.asList(new Header(0, "title-123"), new Header(1, "title-abc")));
		contents.setData(Arrays.asList(new String[] { "haha1", "hoho1" }, new String[] { "haha2", "hoho2" }));
		return contents;
	}

}