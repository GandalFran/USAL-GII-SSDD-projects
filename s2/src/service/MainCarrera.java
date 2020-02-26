package service;

public class MainCarrera {

	public static void main(String [] args) {
		int numAtletasToRun = Integer.parseInt(args[0]);
		String hostUri = args[1];
		String hostId = args[2];
		//String hostUri = "http://localhost:8081/s2/carrera100";
		MainCarrera.doCarrera(hostUri, hostId, numAtletasToRun);
	}
	
	private static Atleta[] buildAtletas(String hostId, int numAtletas, Carrera100Proxy carrera) {
		Atleta [] atletas = new Atleta [numAtletas];
		for(int i=0; i< numAtletas; i++) {
			atletas[i] = new Atleta(String.format("Atleta_%s_%d", hostId, i), carrera);
		}
		return atletas;
	}
	
	public static void doCarrera(String hostUri, String hostId, int numAtletas) {
		Carrera100Proxy carrera = (Carrera100Proxy) Carrera100.buildProxy(hostUri);
		Atleta [] atletas = MainCarrera.buildAtletas(hostId, numAtletas, carrera);
		carrera.reinicio(String.format("%d",numAtletas));
		for(Atleta a : atletas)
			a.start();
		String resultados = carrera.resultados();
		System.out.println(resultados);
	}
}
