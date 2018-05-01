package game;

public class Game {
	
	
	public void startGame(){
		
	}
	public static void main(String args[]){
		BoardManager b=new BoardManager();
		b.move(b.getBoard().getSquare(new Coordinate(5,6)),b.getBoard().getSquare(new Coordinate(5,4)));
		b.move(b.getBoard().getSquare(new Coordinate(6,6)),b.getBoard().getSquare(new Coordinate(6,4)));
		b.move(b.getBoard().getSquare(new Coordinate(4,1)),b.getBoard().getSquare(new Coordinate(4,3)));
		b.move(b.getBoard().getSquare(new Coordinate(3,0)),b.getBoard().getSquare(new Coordinate(7,4)));
		b.move(b.getBoard().getSquare(new Coordinate(4,3)),b.getBoard().getSquare(new Coordinate(5,4)));
		
		b.move(b.getBoard().getSquare(new Coordinate(1,6)),b.getBoard().getSquare(new Coordinate(1,5)));

		System.out.println(b.isCheck(PlayerType.BLACK));

		b.getBoard().printBoard();
	}


}
