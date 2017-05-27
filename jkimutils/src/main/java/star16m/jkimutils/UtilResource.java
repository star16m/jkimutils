package star16m.jkimutils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public class UtilResource {

    @GET
    public String say(@QueryParam("name") String name) {
        return "Hello, " + name + "!";
    }
}