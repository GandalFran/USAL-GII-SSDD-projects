package Carrera100ms;

import java.util.concurrent.Semaphore;


public class Carrera{
	
	public static final Semaphore META = new Semaphore(1);
	
	public static void correr(Atleta [] atletas) {
		Semaphore goal = new Semaphore(1);
		Semaphore pistoletazo = new Semaphore(0);
		
		for(Atleta a : atletas) {
			a.setGoal(goal);
			a.setStartSemaphore(pistoletazo);
			a.start();
		}

		try {
			System.out.println("preparados");
			Thread.sleep(1000);
			System.out.println("listos");
			Thread.sleep(1000);
			System.out.println("ya");
			pistoletazo.release(atletas.length);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
