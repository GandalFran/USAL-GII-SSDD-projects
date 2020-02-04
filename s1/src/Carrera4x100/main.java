package Carrera4x100;


public class main {

	private final static int NUM_RUNNERS = 4;
	
	public static void main(String[]args) {
		Atleta [] atletas = new Atleta [NUM_RUNNERS];
		
		for(int i=0; i<NUM_RUNNERS; i++) 
			atletas[i] = new Atleta("Atleta"+i);
		
		Carrera.correr(atletas);
	}
}
