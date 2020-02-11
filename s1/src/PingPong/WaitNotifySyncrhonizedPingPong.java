package PingPong;

public class WaitNotifySyncrhonizedPingPong extends ClassicPingPong{

	public WaitNotifySyncrhonizedPingPong(String word, int numIterations) {
		super(word, numIterations);
	}

	public static WaitNotifySyncrhonizedPingPong[] producePlayers(int numIterations) {
		return new WaitNotifySyncrhonizedPingPong [] {
				new WaitNotifySyncrhonizedPingPong("p", numIterations),
				new WaitNotifySyncrhonizedPingPong("P", numIterations)
		};
	}
	
	@Override
	public void run() {
		synchronized(this.getClass()) {
			for(int i=0; i< this.numIterations; i++) {
				System.out.print(this.word);
				System.out.flush();
				this.getClass().notifyAll();
				try {
					this.getClass().wait();
				}catch(InterruptedException ex) {
					System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
				}
			}
			this.getClass().notifyAll();
		}
	}
	
}
