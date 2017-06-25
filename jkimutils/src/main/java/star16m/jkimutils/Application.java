package star16m.jkimutils;

import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 *
 */
public class Application {
	private static ApplicationConfig config;
	
	public static String APP_PORT        = "app.conf.port";
	public static String APP_DRIVER_URL  = "app.conf.driver.url";
	public static String APP_DRIVER_NAME = "app.conf.driver.name";
	public static String APP_USER_NAME   = "app.conf.user.name";
	public static String APP_PASSWORD    = "app.conf.password";
	
	private static final String APP_CONF_PORT        = "app.conf.port";
	private static final String APP_CONF_DRIVER_URL  = "app.conf.driver.url";
	private static final String APP_CONF_DRIVER_NAME = "app.conf.driver.name";
	private static final String APP_CONF_USER_NAME   = "app.conf.user.name";
	private static final String APP_CONF_PASSWORD    = "app.conf.password";
	
	public static class ApplicationConfig {
		private int port;
		private String driverUrl;
		private String driverName;
		private String userName;
		private String password;
		private ApplicationConfig(int port, String driverUrl, String driverName, String userName, String password) {
			this.port = port;
			this.driverUrl = driverUrl;
			this.driverName = driverName;
			this.userName = userName;
			this.password = password;
		}
		public int getPort() {
			return port;
		}
		public String getDriverUrl() {
			return this.driverUrl;
		}
		public String getDriverName() {
			return this.driverName;
		}
		public String getUserName() {
			return this.userName;
		}
		public String getPassword() {
			return this.password;
		}
	}
	
	public static void setup(Properties prop) {
		String port = prop.getProperty(APP_CONF_PORT, String.valueOf(9999));
		if (port.replaceAll("\\d+", "").length() != 0) {
			port = String.valueOf(9999);
		}
		String driverUrl = prop.getProperty(APP_CONF_DRIVER_URL, "");
		String driverName = prop.getProperty(APP_CONF_DRIVER_NAME, "");
		String userName = prop.getProperty(APP_CONF_USER_NAME, "");
		String password = prop.getProperty(APP_CONF_PASSWORD, "");
		config = new ApplicationConfig(Integer.parseInt(port), driverUrl, driverName, userName, password);
	}
	public static ApplicationConfig getConfig() {
		return config;
	}
	private static void run() throws Exception {
		int port = getConfig().getPort();
		URI uri = UriBuilder.fromUri("http://localhost/").port(port).build();
		HttpServer server = HttpServerFactory.create(uri);
		server.start();
		System.out.println("server started at [" + port + "]");
		System.in.read();
		server.stop(0);
	}
	public static void main(String[] arguments) throws Exception {
		Properties prop = new Properties();
		prop.put(APP_CONF_PORT, "9999");
		prop.put(APP_CONF_DRIVER_URL, "jdbc:h2:file:C:\\data\\tmp\\tmp");
		prop.put(APP_CONF_DRIVER_NAME, "org.h2.Driver");
		prop.put(APP_CONF_USER_NAME, "sa");
		prop.put(APP_CONF_PASSWORD, "");
		
		Application.setup(prop);
		Application.run();
	}
}
