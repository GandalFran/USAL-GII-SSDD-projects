package Carrera4x100;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 9;
	private static final int MAX_SLEEP_MS = 11;

	private String nombre;
	private Semaphore testigo;

	public Atleta(String nombre, Semaphore testigo) {
		super();
		this.nombre = nombre;
		this.testigo = testigo;
	}

	@Override
	public void run() {
		try {
			this.testigo.acquire();
			this.mySleep();
			this.testigo.release();
		}catch(InterruptedException ex) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
	
	private int getSleepInterval() {
		return new Random().nextInt(Atleta.MAX_SLEEP_MS - Atleta.MIN_SLEEP_MS + 1) + Atleta.MIN_SLEEP_MS;
	}
	
	private void mySleep() throws InterruptedException{
		int sleepInterval = this.getSleepInterval();
		System.out.printf("\n[%s] running for %d ms. tiempo: %d", this.nombre, sleepInterval, System.currentTimeMillis());
		Thread.sleep(sleepInterval);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Semaphore getTestigo() {
		return testigo;
	}

	public void setTestigo(Semaphore testigo) {
		this.testigo = testigo;
	}
	
}
