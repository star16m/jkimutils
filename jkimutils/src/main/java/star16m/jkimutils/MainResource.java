package star16m.jkimutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class MainResource {

	public static final File PARENT_FILE = new File("resources");
	private static String MAIN_FILE = null;
	@GET
	@Path("/")
	public Response getMain() throws Exception {
		return Response.seeOther(UriBuilder.fromUri("main.html").build()).build();
	}
    @GET
    @Path("/{path:.+}")
    public Response getPage(@PathParam("path") String path) throws Exception {
    	if (path != null && path.matches("main\\d*.html")) {
    		String temp = path.replaceAll(".+main", "").replaceAll("\\.html", "");
//    		if (MAIN_FILE == null) {
    			synchronized (MainResource.class) {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PARENT_FILE, "main.html")), Charset.forName("UTF-8")));
					StringBuffer sb = new StringBuffer();
					String l = null;
					while ((l = br.readLine()) != null) {
						sb.append(l);
					}
					br.close();
					MAIN_FILE = sb.toString();
				}
//    		}
    		return Response.ok(MAIN_FILE.replaceAll("#CONTENTS#", temp + "contents")).build();
    	}
    	System.out.println("calls file[" + path + "]");
        try {
        	return Response.ok(new File(PARENT_FILE, path)).build();
        } catch (Exception e) {
        	System.err.println("Not found file [" + path + "]");
        	e.printStackTrace();
        	return Response.noContent().build();
        }
    }
    @GET
	@Path("/webjars/{path:.+}")
    public Response getWebJars(@PathParam("path") String path) throws Exception {
    	return Response.ok(getClass().getResourceAsStream("/META-INF/resources/webjars/" + path)).build();
    }
}