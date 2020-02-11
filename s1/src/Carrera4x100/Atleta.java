package Carrera4x100;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 9;
	private static final int MAX_SLEEP_MS = 11;

	protected String dorsal;
	private Semaphore testigo;

	public Atleta(String dorsal, Semaphore testigo) {
		super();
		this.dorsal = dorsal;
		this.testigo = testigo;
	}

	@Override
	public void run() {
		try {
			this.testigo.acquire();
			this.correr();
			System.out.printf("\n[%s] tiempo: %d", this.dorsal, System.currentTimeMillis());
			this.testigo.release();
		}catch(InterruptedException ex) {	
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
	
	private int getSleepInterval() {
		return new Random().nextInt(Atleta.MAX_SLEEP_MS - Atleta.MIN_SLEEP_MS + 1) + Atleta.MIN_SLEEP_MS;
	}
	
	protected void correr() throws InterruptedException{
		int sleepInterval = this.getSleepInterval();
		Thread.sleep(sleepInterval);
	}
	
	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}

	public Semaphore getTestigo() {
		return testigo;
	}

	public void setTestigo(Semaphore testigo) {
		this.testigo = testigo;
	}
	
}
