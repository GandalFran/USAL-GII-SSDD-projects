package service;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;


public class Carrera100Proxy extends Carrera100{
	
	private WebTarget service;
	
	public Carrera100Proxy(String hostUri) {
		this.service = ClientBuilder.newClient().target(UriBuilder.fromUri(hostUri).build());
	}
	
	@Override
	public void reinicio() {
		try {
			this.service.path("reinicio").request().post(null);
		} catch (Exception ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}

	@Override
	public void preparado() {
		try {
			this.service.path("preparado").request().post(null);
		} catch (Exception ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}

	@Override
	public void listo() {
		try {
			this.service.path("listo").request().post(null);
		} catch (Exception ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}

	@Override
	public String llegada(String dorsal) {
		try {
			return this.service.path("llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
		} catch (Exception ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
			return null;
		}
	}

	@Override
	public String resultados() {
		try {
			return this.service.path("resultados").request(MediaType.TEXT_PLAIN).get(String.class);
		} catch (Exception ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
			return null;
		}
	}
}
