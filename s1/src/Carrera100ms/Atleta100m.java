package Carrera100ms;

import java.util.Random;
import java.util.concurrent.Semaphore;

import Carrera4x100.Atleta;

public class Atleta100m extends Atleta{

	private Semaphore meta;
	private Semaphore inicio;
	
	public Atleta100m(String dorsal, Semaphore testigo, Semaphore meta) {
		super(dorsal, testigo);
		this.meta = meta;
		this.inicio = testigo;
	}

	@Override
	public void run() {
		try {
			this.inicio.acquire();
			this.correr();
			Carrera.meta(this);
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


		

}
