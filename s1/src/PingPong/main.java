package PingPong;

public class main {

	public static void main(String[]args) {
		SynchronizedPingPong pp = new SynchronizedPingPong("p");
		SynchronizedPingPong pP = new SynchronizedPingPong("P");
				
		pp.start();
		pP.start();
	}
}
