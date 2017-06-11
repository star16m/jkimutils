package star16m.jkimutils.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/webjars")
public class WebJars {
	@GET
	@Path("{path:.+}")
	public Response getWebJars(@PathParam("path") String path) throws Exception {
		System.out.println("calls webjars[" + path + "]");
		return Response.ok(getClass().getResourceAsStream("/META-INF/resources/webjars/" + path)).build();
	}
}
