package game;

public class Game {
	
	public Game(){
		
	}
	
	public void startGame(){
	}
	
	public static void main(String args[]){

		BoardManager boardManager = new BoardManager();
		boardManager.getBoard().printBoard();
		boardManager.move(new Coordinate(4,1), new Coordinate(4,3));
		System.out.println(boardManager.move(new Coordinate(5,0), new Coordinate(4,1)));
		boardManager.getBoard().printBoard();
		

	}


}
