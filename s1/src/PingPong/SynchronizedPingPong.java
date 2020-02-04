package PingPong;

import java.util.concurrent.Semaphore;

public class SynchronizedPingPong extends Thread{

	private final static Semaphore initialSemaphore = new Semaphore(-1);
	private final static Semaphore sP = new Semaphore(1);
	private final static Semaphore sp = new Semaphore(0);
	private final static int MAX_ITERATIONS = 3000;
	
	private String word;
	
	public SynchronizedPingPong(String word) {
		super();
		this.word = word;
	}
	
	@Override
	public void run() {
		
		for(int i=0; i< this.MAX_ITERATIONS; i++) {
			try {
				
				if(this.word.equals("p")){
					sp.acquire();
				}else if(this.word.equals("P")) {
					sP.acquire();
				}

				System.out.print(this.word);
				System.out.flush();

				if(this.word.equals("p")) {
					sP.release();
				}else if(this.word.equals("P")) {
					sp.release();
				}

				
			}catch(InterruptedException ex) {
				
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
