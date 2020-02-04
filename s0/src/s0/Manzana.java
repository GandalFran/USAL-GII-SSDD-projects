package s0;

public class Manzana extends Fruta {

	private int pepitas;

	public Manzana() {
		super();
		this.pepitas = pepitas;
	}
	
	public Manzana(float kilos, int pepitas) {
		super(kilos);
		this.pepitas = pepitas;
	}
	
	public Manzana(float kilos, boolean hechaZumo, int pepitas) {
		super(kilos, hechaZumo);
		this.pepitas = pepitas;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
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

	@Override
	public float getKilos() {
		// TODO Auto-generated method stub
		return super.getKilos();
	}

	@Override
	public void setKilos(float kilos) {
		// TODO Auto-generated method stub
		super.setKilos(kilos);
	}

	@Override
	public boolean isHechaZumo() {
		// TODO Auto-generated method stub
		return super.isHechaZumo();
	}

	@Override
	public void setHechaZumo(boolean hechaZumo) {
		// TODO Auto-generated method stub
		super.setHechaZumo(hechaZumo);
	}

	public int getPepitas() {
		return pepitas;
	}

	public void setPepitas(int pepitas) {
		this.pepitas = pepitas;
	}
	
}
