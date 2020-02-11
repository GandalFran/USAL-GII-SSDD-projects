package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("service")
public class Service {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("test")
	public String hola() {
		return "hello";
	}
	
}
