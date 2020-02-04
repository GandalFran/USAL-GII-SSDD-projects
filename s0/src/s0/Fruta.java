package s0;

public class Fruta {

	private float kilos;
	private boolean hechaZumo;
	

	public Fruta() {
		this.kilos = 0.0f;
		this.hechaZumo = false;
	}
	
	public Fruta(float kilos) {
		super();
		this.kilos = kilos;
		this.hechaZumo = false;
	}

	public Fruta(float kilos, boolean hechaZumo) {
		super();
		this.kilos = kilos;
		this.hechaZumo = hechaZumo;
	}
	
	public void hacerZumo() throws ZumoException{
		if(this.hechaZumo)
			throw new ZumoException();
		else
			this.hechaZumo = true;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	public float getKilos() {
		return kilos;
	}
	
	public void setKilos(float kilos) {
		this.kilos = kilos;
	}
	
	public boolean isHechaZumo() {
		return hechaZumo;
	}
	
	public void setHechaZumo(boolean hechaZumo) {
		this.hechaZumo = hechaZumo;
	}
}
