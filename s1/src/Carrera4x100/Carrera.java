package Carrera4x100;
import java.util.concurrent.Semaphore;

public class Carrera{
	
	public static final Semaphore TESTIGO = new Semaphore(1);
	
	public static void correr(Atleta [] atletas) {
		System.out.println("corriendo");
		try {
			TESTIGO.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Atleta a : atletas)
			a.start();

		TESTIGO.release();
	}
	
}
