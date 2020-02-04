package Carrera4x100;

import java.util.Random;

public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 9;
	private static final int MAX_SLEEP_MS = 11;

	private String nombre;
	
	public Atleta(String nombre){
		this.nombre = nombre;
	}

	@Override
	public void run() {
		try {
			Carrera.TESTIGO.acquire();
			this.mySleep();
			Carrera.TESTIGO.release();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int getSleepInterval() {
		return new Random().nextInt(Atleta.MAX_SLEEP_MS - Atleta.MIN_SLEEP_MS + 1) + Atleta.MIN_SLEEP_MS;
	}
	
	private void mySleep() throws InterruptedException{
		int sleepInterval = this.getSleepInterval();
		System.out.printf("\n[%s] running for %d ms", this.nombre, sleepInterval);
		Thread.sleep(sleepInterval);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
