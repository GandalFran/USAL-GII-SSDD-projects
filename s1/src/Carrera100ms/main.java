package Carrera100ms;

public class main {

	public static void main(String[]args) {
		int numAtletas = 8;
		Atleta100m [] atletas = Carrera100m.buildAtletas(numAtletas);
		Carrera100m.correr(atletas);
	}
}
