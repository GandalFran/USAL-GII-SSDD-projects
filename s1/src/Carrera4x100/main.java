package Carrera4x100;


public class main {
	
	public static void main(String[]args) {
		int numAtletas = 4;
		Atleta [] atletas = Carrera.buildAtletas(numAtletas);
		Carrera.correr(atletas);
	}
}
