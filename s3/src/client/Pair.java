package client;

public class Pair {
	
	private long delay;
	private double offset;
	
	public Pair(long delay, double offset) {
		super();
		this.offset = offset;
		this.delay = delay;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (delay != other.delay)
			return false;
		if (offset != other.offset)
			return false;
		return true;
	}
	
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
}
