package Carrera4x100;
import java.util.concurrent.Semaphore;

public class Carrera{
	
	private final static Semaphore TESTIGO = new Semaphore(1);
	
	public static Atleta [] buildAtletas(int numAtletas) {
		Atleta [] atletas = new Atleta[numAtletas];
		for(int id = 0; id < numAtletas; id++)
			atletas[id] = new Atleta(String.format("Atleta %d",id), TESTIGO);
		return atletas;
	}
	
	public static void correr(Atleta [] atletas) {
		for(Atleta a : atletas)
			a.start();
	}
}
