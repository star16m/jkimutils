package star16m.jkimutils;

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

import star16m.jkimutils.contents.Contents;
import star16m.jkimutils.contents.Header;

@Path("/")
public class MainResource {

	public static final File PARENT_FILE = new File("resources");
	private static String MAIN_CONTENTS;
	static {
		File file = new File(PARENT_FILE, "main.html");
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
	@Path("/main/{type : .*}")
	public Response getMain(@PathParam("type") String type) throws Exception {
		System.out.println("call main -> type[" + type + "]");
		return Response.ok(MAIN_CONTENTS).build();
	}
	@GET
	@Path("/contents/{type : .*}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Contents getContents(@PathParam("type") String type) throws Exception {
		System.out.println("call contents -> type[" + type + "]");
		Contents contents = new Contents("haha", "title from contents", "this is description");
		contents.setHeader(Arrays.asList(new Header(0, "title-123"), new Header(1, "title-abc")));
		contents.setData(Arrays.asList(new String[] {"haha1", "hoho1"}, new String[] {"haha2", "hoho2"}));
		return contents;
	}
	@GET
	@Path("/resources/{path:.+}")
	public Response getPage(@PathParam("path") String path) throws Exception {
		System.out.println("calls resours[" + path + "]");
		try {
			File file = new File(PARENT_FILE, path);
			if (file.exists()) {
				return Response.ok(file).build();
			} else {
				System.err.println("Not found file [" + path + "]");
				return Response.status(404).build();
			}
		} catch (Exception e) {
			System.err.println("Not found file [" + path + "]");
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	private static List<Menu> getMenuList() throws Exception {
		List<Menu> list = Arrays.asList(new Menu("haha", "/main/t1"), new Menu("aaaa", "/main/t2"));
		return list;
	}
	@GET
	@Path("/webjars/{path:.+}")
	public Response getWebJars(@PathParam("path") String path) throws Exception {
		System.out.println("calls webjars[" + path + "]");
		return Response.ok(getClass().getResourceAsStream("/META-INF/resources/webjars/" + path)).build();
	}
}