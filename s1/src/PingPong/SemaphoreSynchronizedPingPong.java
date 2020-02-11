package PingPong;

import java.util.concurrent.Semaphore;

public class SemaphoreSynchronizedPingPong extends ClassicPingPong{
	
	private Semaphore mySempahore;
	private Semaphore rivalSemaphore;
	
	public SemaphoreSynchronizedPingPong(String word, Semaphore mySemaphore, Semaphore rivalSemaphore, int numIterations) {
		super(word, numIterations);
		this.mySempahore = mySemaphore;
		this.rivalSemaphore = rivalSemaphore;
	}
	
	
	public static SemaphoreSynchronizedPingPong[] producePlayers(int numIterations) {
		Semaphore one = new Semaphore(0);
		Semaphore two = new Semaphore(1);
		return new SemaphoreSynchronizedPingPong [] {
				new SemaphoreSynchronizedPingPong("p", one, two, numIterations),
				new SemaphoreSynchronizedPingPong("P", two, one, numIterations)
		};
	}
	
	@Override
	public void run() {
		for(int i=0; i< this.numIterations; i++) {
			try {
				this.mySempahore.acquire();
				System.out.print(this.word);
				System.out.flush();
				this.rivalSemaphore.release();
			}catch(InterruptedException ex) {
				System.err.println("["+ Thread.currentThread().getId()+"] An error occurred in " + ex.toString());
			}
		}
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
