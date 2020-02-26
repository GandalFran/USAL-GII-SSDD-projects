package service;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BrokenBarrierException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;


@Singleton
@Path("/carrera100")
public class Carrera100 {

	private static final int NUM_HOSTS = 2;

	private int numAtletas;
	private long tiempoInicio;
	private Map<String, Long> tiemposLlegada;

	private CyclicBarrier barreraListo;
	private CyclicBarrier barreraRegistro;
	private CyclicBarrier barreraPreparado;
	private Semaphore semaforoEsperarResultados;
	private Semaphore semaforoRegistrarAtletas;

	public Carrera100() {
		this.numAtletas = 0;
		this.tiempoInicio = 0;
		this.tiemposLlegada = new ConcurrentHashMap<>();
		this.semaforoEsperarResultados = new Semaphore(0);
		this.semaforoRegistrarAtletas = new Semaphore(1);
		this.barreraRegistro = new CyclicBarrier(this.NUM_HOSTS);
	}
	
	public static Carrera100 buildProxy(String hostUri) {
		return new Carrera100Proxy(hostUri);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/status")
	public String hola() {
		return "{\"status\": \"ok\"}";
	}
	
	
	@GET
	@Path("/reinicio")
	public void reinicio(@QueryParam(value="atletas") String atletas) {
		this.tiempoInicio = 0;
		this.tiemposLlegada.clear();
		this.semaforoEsperarResultados = new Semaphore(0);
		try {
			this.barreraRegistro.await();
			this.semaforoRegistrarAtletas.acquire();
			this.numAtletas += Integer.parseInt(atletas);
			this.semaforoRegistrarAtletas.release();
			this.barreraRegistro.await();
			this.barreraListo = new CyclicBarrier(this.numAtletas);
			this.barreraPreparado = new CyclicBarrier(this.numAtletas);
			this.barreraRegistro.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
		this.barreraRegistro.reset();
	}
	
	@POST
	@Path("/preparado")
	public void preparado() {
		try {
			this.barreraPreparado.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
	}
	
	@POST
	@Path("/listo")
	public void listo() {
		try {
			this.barreraListo.await();
		} catch (InterruptedException | BrokenBarrierException e) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
	}
	
	@GET
	@Path("/llegada")
	@Produces(MediaType.TEXT_PLAIN)
	public String llegada(@QueryParam(value="dorsal") String dorsal) {
		long time = 0;
		time = System.currentTimeMillis();
		this.tiemposLlegada.put(dorsal, time);
		this.semaforoEsperarResultados.release(1);
		return new Long(time).toString();
	}
	
	@GET
	@Path("/resultados")
	@Produces(MediaType.TEXT_PLAIN)
	public String resultados() {
		try {
			this.semaforoEsperarResultados.acquire(this.numAtletas);
		} catch (InterruptedException e) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
		
		StringBuilder sb = new StringBuilder();
		for(String atleta : this.sortmap(this.tiemposLlegada).keySet()) {
			long time = this.tiemposLlegada.get(atleta) - this.tiempoInicio;
			sb.append(atleta).append(" - ").append(time).append("\n");
		}
		
		return sb.toString();
	}
	
	// source: https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
	private Map<String, Long> sortmap(Map<String, Long> map) {
		return map;
		/*
		 * Commented because it doesn't work in java 7
		return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    	*/
    }
}
