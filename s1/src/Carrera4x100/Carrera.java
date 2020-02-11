package Carrera4x100;
import java.util.concurrent.Semaphore;

public class Carrera{
	
	public static Atleta [] buildAtletas(int numAtletas) {
		Atleta [] atletas = new Atleta[numAtletas];
		Semaphore testigo = new Semaphore(1);
		for(int id = 0; id < numAtletas; id++)
			atletas[id] = new Atleta(String.format("Atleta %d",id), testigo);
		return atletas;
	}
	
	public static void correr(Atleta [] atletas) {
		for(Atleta a : atletas)
			a.start();
	}
}
