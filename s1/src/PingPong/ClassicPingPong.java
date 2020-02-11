package PingPong;

import java.util.concurrent.Semaphore;

public class ClassicPingPong extends Thread{	

	protected String word;
	protected int numIterations;
	
	public ClassicPingPong(String word, int numIterations) {
		super();
		this.word = word;
		this.numIterations = numIterations;
	}
	
	public static ClassicPingPong[] producePlayers(int numIterations) {
		return new ClassicPingPong [] {
				new ClassicPingPong("p", numIterations),
				new ClassicPingPong("P", numIterations)
		};
	}
	
	@Override
	public void run() {
		for(int i=0; i< this.numIterations; i++) {
			System.out.print(this.word);
			System.out.flush();
		}
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}
