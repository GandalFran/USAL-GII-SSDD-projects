package client;


public class NTPMarzulloClient extends NTPClient{
	
	@Override
	protected Pair selectBestPair(Pair [] pairs) {
		return new Pair(1,2.3);
	}
	
}
