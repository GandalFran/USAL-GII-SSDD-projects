package Carrera;


public class Carrera100Proxy {
	
	private static Carrera100 carrera = new Carrera100();
	
	public void reinicio() {
		Carrera100Proxy.carrera.reinicio();
	}
	
	public void preparado() {
		Carrera100Proxy.carrera.preparado();

	}
	
	public void listo() {
		Carrera100Proxy.carrera.listo();
	}
	
	public long llegada(String dorsal) {
		return Carrera100Proxy.carrera.llegada(dorsal);
	
	}
	
	public String resultados() {
		return Carrera100Proxy.carrera.resultados();
	}
}
