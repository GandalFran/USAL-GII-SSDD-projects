package client;

public class Pair {
	
	private double delay;
	private double offset;
	
	public Pair(double delay, double offset) {
		super();
		this.offset = offset;
		this.delay = delay;
	}
	
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay) {
		this.delay = delay;
	}
}
