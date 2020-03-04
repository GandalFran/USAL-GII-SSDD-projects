package client;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NTPMarzulloClient extends NTPClient{
	
	@Override
	protected Pair selectBestPair(Pair [] pairs) {
		MarzulloInterval [] table = this.generateMarzulloTable(pairs);
		
		double best=0, cnt=0, bestStart=0, bestEnd=0;
		for(int i = 0; i< table.length; i++) {
			 MarzulloInterval interval  = table[i];
			cnt -= interval.getIntervalEnd();
			if(cnt > best) {
				best = cnt;
				bestStart = interval.getIntervalStart();
				bestEnd = table[i+1].getIntervalStart();
			}
		}
		
		Pair pair = this.calculatePair(bestStart, bestEnd);
		return pair;
	}
	
	private MarzulloInterval[] generateMarzulloTable(Pair [] pairs) {
		List <MarzulloInterval> table = new ArrayList<>();
		for(Pair p: pairs) {
			table.addAll(Arrays.asList(MarzulloInterval.buildMarzulloInterval(p)));
		}
		Collections.sort(table);
		return Arrays.copyOf(table.toArray(), table.size(), MarzulloInterval[].class);
	}
	
	private Pair calculatePair(double bestStart, double bestEnd) {
		double delay = bestEnd - bestStart;
		double offset = (bestStart + bestEnd)/2;
		return new Pair(delay, offset);
	}
}
