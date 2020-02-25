package Carrera;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;


public class Carrera100 {

	private static final int NUM_ATLETAS = 4;

	private long tiempoInicio;
	private Map<String, Long> tiemposLlegada;

	private CyclicBarrier barreraListo;
	private CyclicBarrier barreraPreparado;
	private Semaphore semaforoEsperarResultados;

	public Carrera100() {
		this.tiempoInicio = 0;
		this.tiemposLlegada = new ConcurrentHashMap<>();
		this.semaforoEsperarResultados = new Semaphore(0);
		this.barreraListo = new CyclicBarrier(NUM_ATLETAS);
		this.barreraPreparado = new CyclicBarrier(NUM_ATLETAS);
	}
	
	public void reinicio() {
		this.tiempoInicio = 0;
		this.tiemposLlegada.clear();
		this.semaforoEsperarResultados = new Semaphore(0);
		this.barreraListo = new CyclicBarrier(NUM_ATLETAS);
		this.barreraPreparado = new CyclicBarrier(NUM_ATLETAS);
	}
	
	public void preparado() {
		try {
			this.barreraPreparado.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
	}
	
	public void listo() {
		try {
			this.barreraListo.await();
		} catch (InterruptedException | BrokenBarrierException e) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + e.toString());
		}
	}
	
	public long llegada(String dorsal) {
		long time = 0;
		time = System.currentTimeMillis();
		this.tiemposLlegada.put(dorsal, time);
		this.semaforoEsperarResultados.release(1);
		return time;
	}
	
	public String resultados() {
		try {
			this.semaforoEsperarResultados.acquire(NUM_ATLETAS);
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
	
	// src: https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-valuesa
	private Map<String, Long> sortmap(Map<String, Long> map) {
		return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
