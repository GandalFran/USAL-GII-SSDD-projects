package service;

import java.util.Random;


public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 9000;
	private static final int MAX_SLEEP_MS = 11000;
	
	private String dorsal;
	private Carrera100 carrera;

	public Atleta(String dorsal, Carrera100 carrera) {
		super();
		this.dorsal = dorsal;
		this.carrera = carrera;
	}

	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}
	
	@Override
	public void run() { 
		try {
			this.carrera.preparado();
			this.carrera.listo();
			int sleepIntervalMillis = this.getSleepInterval();
			Thread.sleep(sleepIntervalMillis);
			this.carrera.llegada(this.dorsal);
		} catch (InterruptedException ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
	
	private int getSleepInterval() {
		return new Random().nextInt(Atleta.MAX_SLEEP_MS - Atleta.MIN_SLEEP_MS + 1) + Atleta.MIN_SLEEP_MS;
	}
	

}
