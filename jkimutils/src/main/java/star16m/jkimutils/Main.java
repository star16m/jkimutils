package star16m.jkimutils;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] arguments) throws Exception {
		int port = 9999;
		URI uri = UriBuilder.fromUri("http://localhost/").port(port).build();
		HttpServer server = HttpServerFactory.create(uri);
		server.start();
		System.out.println("server started at [" + port + "]");
		System.in.read();
		server.stop(0);
	}
}
