package Carrera100ms;

import java.util.concurrent.Semaphore;

import Carrera4x100.Atleta;
import Carrera4x100.Carrera;


public class Carrera100m extends Carrera{
	
	public static Atleta100m [] buildAtletas(int numAtletas) {
		Atleta100m [] atletas = new Atleta100m[numAtletas];
		Semaphore inicio = new Semaphore(0);
		Semaphore meta = new Semaphore(1);
		
		for(int id = 0; id < numAtletas; id++)
			atletas[id] = new Atleta100m(String.format("Atleta %d",id), inicio, meta);
		return atletas;
	}
	
	public static void Carrera(Atleta100m atleta){
		atleta.getMeta().acquire();
		System.out.printf("\n[%s] tiempo: %d", atleta.getDorsal(), System.currentTimeMillis());
		System.out.flush();
		atleta.getMeta().release();
	}
	
	public static void correr(Atleta100m [] atletas) {
		try {
			System.out.println("preparados");
			Thread.sleep(1000);
			System.out.println("listos");
			Thread.sleep(1000);
			System.out.println("ya");
			for(Atleta100m a : atletas)
				a.start();
			atletas[0].getInicio().release(atletas.length);
		} catch (InterruptedException ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
}
