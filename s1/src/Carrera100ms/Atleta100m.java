package Carrera100ms;

import java.util.Random;
import java.util.concurrent.Semaphore;

import Carrera4x100.Atleta;

public class Atleta100m extends Atleta{

	private Semaphore meta;
	private Semaphore inicio;
	private Semaphore carreraNotifier;
	
	public Atleta100m(String dorsal, Semaphore testigo, Semaphore meta, Semaphore carreraNotifier) {
		super(dorsal, testigo);
		this.meta = meta;
		this.inicio = testigo;
		this.carreraNotifier = carreraNotifier;
	}

	@Override
	public void run() {
		try {
			this.inicio.acquire();
			this.correr();
			Carrera100m.meta(this);
		} catch (InterruptedException ex) {
			System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
		}
	}
	
	public Semaphore getMeta() {
		return meta;
	}

	public void setMeta(Semaphore meta) {
		this.meta = meta;
	}

	public Semaphore getInicio() {
		return inicio;
	}

	public void setInicio(Semaphore inicio) {
		this.inicio = inicio;
	}
	
	public Semaphore getCarreraNotifier() {
		return carreraNotifier;
	}

	public void setCarreraNotifier(Semaphore carreraNotifier) {
		this.carreraNotifier = carreraNotifier;
	}

}
