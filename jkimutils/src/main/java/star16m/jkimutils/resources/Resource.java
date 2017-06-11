package star16m.jkimutils.resources;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/resources")
public class Resource {
	@GET
	@Path("{path:.+}")
	public Response getResource(@PathParam("path") String path) throws Exception {
		System.out.println("calls resours[" + path + "]");
		try {
			File file = new File(Main.RESOURCE_BASE_DIRECTORY, path);
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
}
