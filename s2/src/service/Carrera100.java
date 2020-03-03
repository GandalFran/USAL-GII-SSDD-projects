package service;

import java.util.Map;
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

	private int numAtletas;
	private long tiempoInicio;
	private Map<String, Long> tiemposLlegada;

	private CyclicBarrier barreraListo;
	private CyclicBarrier barreraPreparado;
	private Semaphore semaforoEsperarResultados;

	private final static int DEFAULT_NUM_ATLETAS = 6;
	
	public Carrera100() {
		this.numAtletas = DEFAULT_NUM_ATLETAS;
		this.tiempoInicio = 0;
		this.tiemposLlegada = new ConcurrentHashMap<>();
		this.semaforoEsperarResultados = new Semaphore(0);
		this.barreraListo = new CyclicBarrier(this.numAtletas);
		this.barreraPreparado = new CyclicBarrier(this.numAtletas);
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
	
	
	@POST
	@Path("/reinicio")
	public void reinicio() {
		this.tiempoInicio = 0;
		this.tiemposLlegada.clear();
		this.semaforoEsperarResultados = new Semaphore(0);
		this.barreraListo = new CyclicBarrier(this.numAtletas);
		this.barreraPreparado = new CyclicBarrier(this.numAtletas);
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
		this.tiempoInicio = System.currentTimeMillis();
	}
	
	@GET
	@Path("/llegada")
	@Produces(MediaType.TEXT_PLAIN)
	public String llegada(@QueryParam(value="dorsal") String dorsal) {
		Long time = new Long(System.currentTimeMillis());
		this.tiemposLlegada.put(dorsal, time);
		this.semaforoEsperarResultados.release(1);
		return time.toString();
	}
	
	@GET
	@Path("/resultados")
	@Produces(MediaType.TEXT_PLAIN)
	public String resultados() {
		try {
			this.semaforoEsperarResultados.acquire(this.numAtletas);
			this.semaforoEsperarResultados.release(this.numAtletas);
		} catch (InterruptedException e) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
		
	
		StringBuilder sb = new StringBuilder();
		sb.append("\n+==============================================================+===============+===============+===========+").append("\n");
		sb.append(String.format("| %60s | %13s | %13s | %9s |", "dorsal", "inicio", "fin", "tiempo")).append("\n");
		sb.append("+==============================================================+===============+===============+===========+").append("\n");
		for(String atleta : this.tiemposLlegada.keySet()) {
			long timeInterval = this.tiemposLlegada.get(atleta).longValue() - this.tiempoInicio;
			float time = ((float)timeInterval)/1000;
			sb.append(String.format("| %60s | %13d | %13d | %9f |", atleta, this.tiempoInicio, this.tiemposLlegada.get(atleta).longValue(), time )).append("\n");
		}
		sb.append("+==============================================================+===============+===============+===========+").append("\n");
		
		return sb.toString();
	}
}
