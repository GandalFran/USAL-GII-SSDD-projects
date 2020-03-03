package client;

import service.Atleta;
import service.Carrera100;
import service.Carrera100Proxy;

public class MainCarrera {

	public static void main(String [] args) {
		String serviceUri = args[0];
		int numAtletasToRun = Integer.parseInt(args[1]);
		String hostId = args[2];
		boolean isCarreraController = Boolean.parseBoolean(args[3]);
		MainCarrera.doCarrera(serviceUri, hostId, numAtletasToRun, isCarreraController);
	}
	
	private static Atleta[] buildAtletas(String hostId, int numAtletas, Carrera100Proxy carrera) {
		Atleta [] atletas = new Atleta [numAtletas];
		for(int i=0; i< numAtletas; i++) {
			atletas[i] = new Atleta(String.format("Atleta_%s_%d", hostId, i), carrera);
		}
		return atletas;
	}
	
	public static void doCarrera(String serviceUri, String hostId, int numAtletas, boolean isCarreraController) {
		Carrera100Proxy carrera = (Carrera100Proxy) Carrera100.buildProxy(serviceUri);
		Atleta [] atletas = MainCarrera.buildAtletas(hostId, numAtletas, carrera);
		if(isCarreraController) {
			carrera.reinicio();
		}
		for(Atleta a : atletas)
			a.start();
		String resultados = carrera.resultados();
		if(isCarreraController) {
			System.out.println(resultados);
		}
	}
}
