package services;

import java.util.Random;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Singleton
@Path("/ntp")
public class NTPServer {
	
	private static final int MIN_SLEEP_MS = 1000;
	private static final int MAX_SLEEP_MS = 3000;

	private Random generator;

	public NTPServer() {
		this.generator = new Random();
	}
	
	public static NTPServer buildProxy(String serviceUri) {
		return new NTPServerProxy(serviceUri);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/status")
	public String hola() {
		return "{\"status\": \"ok\"}";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/pedirTiempo")
	public String pedirTiempo() {
		long time1 = System.currentTimeMillis(); 
		try {
			Thread.sleep(this.getSleepInterval());
		} catch (InterruptedException e) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
		long time2 = System.currentTimeMillis();
		
		return String.format("%d_%d", time1, time2);
	}
	
	
	private int getSleepInterval() {
		return this.generator.nextInt(NTPServer.MAX_SLEEP_MS - NTPServer.MIN_SLEEP_MS + 1) + NTPServer.MIN_SLEEP_MS;
	}
	
	
}
