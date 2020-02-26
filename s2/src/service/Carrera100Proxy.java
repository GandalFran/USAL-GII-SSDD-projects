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
	public void reinicio(String atletas) {
		this.service.path("reinicio").queryParam("atletas", atletas).request().get();
	}

	@Override
	public void preparado() {		
		this.service.path("preparado").request().post(null);
	}

	@Override
	public void listo() {
		this.service.path("listo").request().post(null);
	}

	@Override
	public String llegada(String dorsal) {
		return this.service.path("llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
	}

	@Override
	public String resultados() {
		return this.service.path("resultados").request(MediaType.TEXT_PLAIN).get(String.class);
	}
}
