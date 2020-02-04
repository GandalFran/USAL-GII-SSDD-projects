import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import s0.Fruta;
import s0.Manzana;
import s0.Naranja;
import s0.ZumoException;

public class main {

	
	public static void main(String [] args) {
		test(new Manzana(),new Naranja());
		test(new Manzana(1.5f,true,10), new Naranja(1.5f,true));
		test();
		
		
	}
	
	public static void test(Fruta manzana, Fruta naranja) {
		System.out.printf("\nEl peso de la manzana es %.2f",manzana.getKilos());

		System.out.printf("\nHaciendo zumo de naranja");
		try {
			naranja.hacerZumo();
		}catch(ZumoException ex) {
			System.out.printf("\nNo se pudo hacer el zumo");
		}
		System.out.printf("\nFinalizado proceso de hacer zumo de naranja\n\n");
	}

	public static void test() {
		Map<String,Fruta> nevera = new ConcurrentHashMap<String,Fruta>();
		nevera.put("manzanaVerde", new Manzana());
		nevera.put("naranajaDeZumo", new Naranja());
		
		System.out.printf("\nManzana %s : %s", "manzanaVerde", nevera.get("manzanaVerde").toString());
		System.out.printf("\nManzana %s : %s", "naranajaDeZumo", nevera.get("naranajaDeZumo").toString());
	}
	
}
