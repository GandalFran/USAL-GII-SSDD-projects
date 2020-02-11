package PingPong;

public class main {
	
	public static void main(String[]args) throws InterruptedException {
		int numIterations = 100;
		
		System.out.println("\n\nClassicPingPong (sin control)");
		for(ClassicPingPong player : ClassicPingPong.producePlayers(numIterations))
			player.start();
		Thread.sleep(2000);
		
		System.out.println("\n\nWaitNotifySynchronizedPingPong (mejora 1)");
		for(WaitNotifySyncrhonizedPingPong player : WaitNotifySyncrhonizedPingPong.producePlayers(numIterations))
			player.start();
		Thread.sleep(2000);

		System.out.println("\n\nSemaphoreSynchronizedPingPong (mejora 2)");
		for(SemaphoreSynchronizedPingPong player : SemaphoreSynchronizedPingPong.producePlayers(numIterations))
			player.start();
		Thread.sleep(2000);
	}
}
