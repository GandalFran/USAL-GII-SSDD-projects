package Carrera100ms;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Atleta extends Thread{

	private static final int MIN_SLEEP_MS = 9;
	private static final int MAX_SLEEP_MS = 11;

	private String dorsal;
	private Semaphore goal;
	private Semaphore startSemaphore;
	
	public Atleta(String nombre){
		this.dorsal = nombre;
	}

	@Override
	public void run() {
		try {
			this.startSemaphore.acquire();
			this.correr();
			this.goal.acquire();
			System.out.println(this.dorsal+" tarda " + System.currentTimeMillis());
			this.goal.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int getSleepInterval() {
		return new Random().nextInt(Atleta.MAX_SLEEP_MS - Atleta.MIN_SLEEP_MS + 1) + Atleta.MIN_SLEEP_MS;
	}
	
	private void correr(){
		int sleepInterval = this.getSleepInterval();
		try {
			Thread.sleep(sleepInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}

	public void setStartSemaphore(Semaphore startSemaphore) {
		this.startSemaphore = startSemaphore;
	}

	public void setGoal(Semaphore goal) {
		this.goal = goal;
	}
	

}
