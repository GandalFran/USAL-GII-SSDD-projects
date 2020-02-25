package service;

public class MainCarrera {

	public static void main(String [] args) {
		/* TODO: 
		 * String hostUri = args[1];
		 */
		int numAtletas = 4;
		String hostUri = "http://localhost:8081/s2/carrera100";
		MainCarrera.doCarrera(hostUri, numAtletas);
	}
	
	private static Atleta[] buildAtletas(int numAtletas, Carrera100Proxy carrera) {
		Atleta [] atletas = new Atleta [numAtletas];
		for(int i=0; i< numAtletas; i++) {
			atletas[i] = new Atleta(String.format("Atleta%d", i), carrera);
		}
		return atletas;
	}
	
	public static void doCarrera(String hostUri, int numAtletas) {
		Carrera100Proxy carrera = (Carrera100Proxy) Carrera100.buildProxy(hostUri);
		Atleta [] atletas = MainCarrera.buildAtletas(numAtletas, carrera);
		carrera.reinicio();
		for(Atleta a : atletas)
			a.start();
		String resultados = carrera.resultados();
		System.out.println(resultados);
	}
}
