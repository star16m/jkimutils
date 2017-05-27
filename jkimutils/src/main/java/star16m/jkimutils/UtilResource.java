package star16m.jkimutils;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/")
public class UtilResource {

	public static final File PARENT_FILE = new File("webapp");
    @GET
    @Path("{path:.+}")
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