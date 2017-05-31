package star16m.jkimutils;

import java.io.File;
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

@Path("/")
public class MainResource {

	public static final File PARENT_FILE = new File("resources");

	@GET
	@Path("/")
	public Response getMain() throws Exception {
		return Response.seeOther(UriBuilder.fromUri("main.html").build()).build();
	}

	@GET
	@Path("/{path:.+}")
	public Response getPage(@PathParam("path") String path) throws Exception {
		System.out.println("calls file[" + path + "]");
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
	@GET
	@Path("/menu/list")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Menu> getMenuList() throws Exception {
		List<Menu> list = Arrays.asList(new Menu("haha", "main.html"), new Menu("aaaa", "main3.html"));
		return list;
	}
	@GET
	@Path("/webjars/{path:.+}")
	public Response getWebJars(@PathParam("path") String path) throws Exception {
		return Response.ok(getClass().getResourceAsStream("/META-INF/resources/webjars/" + path)).build();
	}
}