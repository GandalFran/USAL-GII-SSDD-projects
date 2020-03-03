package client;

public class MarzulloInterval implements Comparable<MarzulloInterval>{

	private double intervalEnd;
	private double intervalStart;	
	
	public MarzulloInterval(double intervalStart, double intervalEnd) {
		super();
		this.intervalEnd = intervalEnd;
		this.intervalStart = intervalStart;
	}

	public static MarzulloInterval[] buildMarzulloInterval(Pair p) {
		return new MarzulloInterval [] {
				new MarzulloInterval(p.getDelay()+p.getOffset(),+1),
				new MarzulloInterval(p.getDelay()-p.getOffset(),-1)
		};
	}

	public double getIntervalEnd() {
		return intervalEnd;
	}

	public void setIntervalEnd(double intervalEnd) {
		this.intervalEnd = intervalEnd;
	}

	public double getIntervalStart() {
		return intervalStart;
	}

	public void setIntervalStart(double intervalStart) {
		this.intervalStart = intervalStart;
	}

	@Override
	public int compareTo(MarzulloInterval o) {
		return Double.compare(this.intervalStart, o.intervalStart);		
	}
	
}
