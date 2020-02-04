package PingPong;

public class ClassicPingPong extends Thread{

	private final static int MAX_ITERATIONS = 3000;
	
	private String word;
	
	public ClassicPingPong(String word) {
		super();
		this.word = word;
	}
	
	@Override
	public void run() {
		synchronized(this.getClass()) {
			for(int i=0; i< this.MAX_ITERATIONS; i++) {
				System.out.print(this.word);
				System.out.flush();
				this.getClass().notifyAll();
				try {
					this.getClass().wait();
				}catch(InterruptedException ex) {
					
				}
			}
			this.notifyAll();
		}
		
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
