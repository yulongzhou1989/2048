import java.util.Random;

public class Game2048 {
	
	final static int BOARD_SIZE = 4;
	final static int WIN_SCORE = 2048;
	private int [][] board;
	private int remainSpace;

	public boolean initialBoard() {
		board = new int [BOARD_SIZE][BOARD_SIZE];
		remainSpace = BOARD_SIZE * BOARD_SIZE;
		generateNewNum(2);
		return true;
	}
	
	public boolean resetBoard() {
		return initialBoard();
	}
	
	public boolean move (Directions dir) {
		if (dir == Directions.UP) {
			return calVertical(0, 1);
		} else if (dir == Directions.DOWN) {
			return calVertical(BOARD_SIZE - 1, -1);
		} else if (dir == Directions.LEFT) {
			return calHori(0, 1);
		} else {
			return calHori(BOARD_SIZE - 1, -1);
 		}
	}
	
	/**
	 * calculate horizontal result of one movement
	 * @param startCol: the start row of the calculation
	 * @param inc: 1 or -1. If it is 1, means left to right. If it is -1, means right to left
	 * @return true: if the game can continue; false: if the game should be stopped (win or lose)
	 */
	//8 8 4 4 <--  -->
	private boolean calHori(int startCol, int inc) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			boolean preHasMerged = false;
			for (int j = 0; j < BOARD_SIZE; j++) {
				int curCol = startCol + inc * j;
				//if this place is an empty space
				if (board[i][curCol] == 0) {
					continue;
				}
				
				//move the number by direction, if hit the wall or already merged number stop
				//if it is empty space, keep moving
				//if previous is a not merged number and it is same with current number, merge and stop
				for(int k = curCol; k != startCol; k -= inc) {
					if (!preHasMerged && board[i][k] == board[i][k - inc]){
						board[i][k - inc] <<= 1;
						board[i][k] = 0;
						remainSpace++;
						if (board[i][k - inc] == WIN_SCORE) {
							win();
							return false;
						}
						preHasMerged = true;
						break;
					} else if (board[i][k - inc] == 0) {
						board[i][k - inc] = board[i][k];
						board[i][k] = 0;
					} else {
						preHasMerged = false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * calculate vertical result of one movement
	 * @param startRow: the start row of the calculation
	 * @param inc: 1 or -1. If it is 1, means left to right. If it is -1, means right to left
	 * @return true: if the game can continue; false: if the game should be stopped (win or lose)
	 */
	private boolean calVertical(int startRow, int inc) {
		for (int j = 0; j < BOARD_SIZE; j++) {
			boolean preHasMerged = false;
			for (int i = 0; i < BOARD_SIZE; i++) {
				int curRow = startRow + inc * i;
				//if this place is an empty space
				if (board[curRow][j] == 0) {
					continue;
				}
				
				//move the number by direction, if hit the wall or already merged number stop
				//if it is empty space, keep moving
				//if previous is a not merged number and it is same with current number, merge and stop
				for(int k = curRow; k != startRow; k -= inc) {
					if (!preHasMerged && board[k][j] == board[k - inc][j]){
						board[k - inc][j] <<= 1;
						board[k][j] = 0;
						remainSpace++;
						if (board[k - inc][j] == WIN_SCORE) {
							win();
							return false;
						}
						preHasMerged = true;
						break;
					} else if (board[k - inc][j] == 0) {
						board[k - inc][j] = board[k][j];
						board[k][j] = 0;
					} else {
						preHasMerged = false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Generate new number on the board. Need to have the same possibility to generate 2 or 4 in 
	 * positions left
	 * @param count: how many new numbers need to be generated
	 */
	public void generateNewNum(int count) {
		if (count > remainSpace) { // need 
			lose();
			return;
		}
		Random rd = new Random();
		while (count-- > 0) {
			boolean generateNum2 = rd.nextInt(2) == 0;//50% to generate number 2, 50% generate number 4
			int num = rd.nextInt(remainSpace);
			for (int i = 0; i < BOARD_SIZE && num >= 0; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					if (board[i][j] == 0) {
						if (num-- == 0) {//found the position for new element
							board[i][j] = generateNum2 ? 2 : 4;
							remainSpace--;
							break;
						}
					}
				}
			}
		}
	}
	
	private void win() {
		System.out.println("Genius, you win the game!");
	}
	
	private void lose() {
		System.out.println("OOPS, you lose the game.");
	}
	
	public void printBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
		
		System.out.println("=========" + remainSpace + "===========");
	}
}
