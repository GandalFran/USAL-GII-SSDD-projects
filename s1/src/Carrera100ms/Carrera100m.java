package Carrera100ms;

import java.util.concurrent.Semaphore;

import Carrera4x100.Atleta;
import Carrera4x100.Carrera;


public class Carrera100m extends Carrera{
	
	public static Atleta100m [] buildAtletas(int numAtletas) {
		Atleta100m [] atletas = new Atleta100m[numAtletas];
		Semaphore inicio = new Semaphore(0);
		Semaphore meta = new Semaphore(1);
		Semaphore carreraNotifier = new Semaphore(0);
		
		for(int id = 0; id < numAtletas; id++)
			atletas[id] = new Atleta100m(String.format("Atleta %d",id), inicio, meta, carreraNotifier);
		return atletas;
	}
	
	public static void meta(Atleta100m atleta){
		try {
			atleta.getMeta().acquire();
		} catch (InterruptedException ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
		System.out.printf("\n[%s] tiempo: %d", atleta.getDorsal(), System.currentTimeMillis());
		System.out.flush();
		atleta.getMeta().release();
		atleta.getCarreraNotifier().release(1);
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
			atletas[0].getCarreraNotifier().acquire(atletas.length);
		} catch (InterruptedException ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
}
