package client;

import java.util.HashMap;
import java.util.Map;

import services.NTPServer;
import services.NTPServerProxy;

public class NTPClient {
	
	
	public Map<NTPServer, Pair> estimate(NTPServer[] servers, int numIterations) {
		long time0, time1, time2, time3;
		Map<NTPServer, Pair> bestPairs = new HashMap<>();
				
		// for each server
		for(NTPServer server : servers) {
			// for each iteration
			Pair [] pairs = new Pair [numIterations];
			for(int currIteration=0; currIteration<numIterations; currIteration++) {
				// get times
				time0 = System.currentTimeMillis();
				long [] response = NTPServerProxy.parsePedirTiempoResponse(server.pedirTiempo());
				time1 = response[0]; 
				time2 = response[1]; 
				time3 = System.currentTimeMillis();
				pairs[currIteration] = new Pair(this.calculateDelay(time0, time1, time2, time3), this.calculateOffset(time0, time1, time2, time3));
			}
			bestPairs.put(server, this.selectBestPair(pairs));
		}
		
		return bestPairs;
	} 
	
	private double calculateOffset(long time0, long time1, long time2, long time3) {
		return ((double)(time1-time0+time2-time3))/2;
	}
	
	private long calculateDelay(long time0, long time1, long time2, long time3) {
		return (time1-time0+time3-time2);
	}
	
	protected Pair selectBestPair(Pair [] pairs) {
		Pair bestPair = new Pair(Long.MAX_VALUE, 0);
		
		for(Pair p: pairs) {
			if(p.getDelay() < bestPair.getDelay()) {
				bestPair = p;
			}
		}
		return bestPair;
	}
	
}
