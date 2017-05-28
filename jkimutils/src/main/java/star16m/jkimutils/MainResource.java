package star16m.jkimutils;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class MainResource {

	public static final File PARENT_FILE = new File("resources");
	@GET
	@Path("/")
	public Response getMain() throws Exception {
		return Response.seeOther(UriBuilder.fromUri("/resource/main.html").build()).build();
	}
    @GET
    @Path("/resource/{path:.+}")
    public Response getPage(@PathParam("path") String path) throws Exception {
    	System.out.println("calls file[" + path + "]");
        try {
        	return Response.ok(new File(PARENT_FILE, path)).build();
        } catch (Exception e) {
        	System.err.println("Not found file [" + path + "]");
        	e.printStackTrace();
        	return Response.noContent().build();
        }
    }
}