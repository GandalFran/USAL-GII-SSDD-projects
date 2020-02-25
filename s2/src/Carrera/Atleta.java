package Carrera;

import java.util.Random;


public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 1000;
	private static final int MAX_SLEEP_MS = 11000;
	
	private String dorsal;
	private Carrera100Proxy carrera;

	public Atleta(String dorsal) {
		super();
		this.dorsal = dorsal;
		this.carrera = new Carrera100Proxy();
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
