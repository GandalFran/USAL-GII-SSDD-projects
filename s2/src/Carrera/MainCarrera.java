package Carrera;

public class MainCarrera {

	private static final int NUM_ATLETAS = 4;
	
	public static void main(String [] args) {
		MainCarrera.doCarrera();
	}
	
	private static Atleta[] buildAtletas() {
		Atleta [] atletas = new Atleta [NUM_ATLETAS];
		for(int i=0; i< NUM_ATLETAS; i++) {
			atletas[i] = new Atleta(String.format("Atleta%d", i));
		}
		return atletas;
	}
	
	public static void doCarrera() {
		Atleta [] atletas = MainCarrera.buildAtletas();
		Carrera100Proxy carrera = new Carrera100Proxy();
		carrera.reinicio();
		for(Atleta a : atletas)
			a.start();
		String resultados = carrera.resultados();
		System.out.println(resultados);
	}
}
