import java.util.Random;

public class GameRunner {
	
	public static void main(String [] args) {
		GameRunner gr = new GameRunner();
		gr.play(20);
	}
	
	Game2048 g2048;
	
	public GameRunner() {
		g2048 = new Game2048();
	}
	
	public void play(int rounds) {
		g2048.initialBoard();
		Random rd = new Random();
		Directions [] dirs = Directions.values();
		
		while (rounds > 0) {
			Directions dir = dirs[rd.nextInt(dirs.length)];
			System.out.println("DIR : " + dir.toString() + ",ROUND: " + rounds);
			g2048.printBoard();
			
			if (!g2048.move(dir)) {//game stop
				g2048.printBoard();
				System.out.println("Game Over.");
				break;
			} else {
				g2048.printBoard();
				g2048.generateNewNum(1);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			g2048.printBoard();
			
			rounds--;
		}
		
		System.out.println("Game Over.");
	}
}
