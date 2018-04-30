package game;


public class Rules {
	private Board board;
	
	public Rules(){
		this.board=new Board();
	}
	
	public Board getBoard(){return board;}
	
	public static void main(String args[]){
		Board b=new Board();
		b.makeMove(b.getSquare(new Coordinate(1,1)),b.getSquare(new Coordinate(1,2)));

		b.makeMove(b.getSquare(new Coordinate(1,2)),b.getSquare(new Coordinate(1,1)));
		b.makeMove(b.getSquare(new Coordinate(1,6)),b.getSquare(new Coordinate(1,4)));
		b.printBoard();
	}
	
	public boolean isPathClear(Coordinate[] path,Coordinate initPos,Coordinate finalPos)
	{
		Square[][] squares=board.getSquares();
		for (Coordinate coordinate:path)
		{
			if ( (squares[coordinate.getX()][coordinate.getY()].isOccupied()) &&
					(!coordinate.equals(initPos)) && (!coordinate.equals(finalPos)) )
			{
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Checks if there is check for the player
	 * @param player
	 * @return boolean 
	 */
	public boolean isCheck(PlayerType player)
	{
		Square[][] squares=board.getSquares();
		//Gets the square of the king.
		Square squareOfKing=null;
		for (int x=0;x<8;x++){
			for (int y=0;y<8;y++){
				Square square=squares[x][y];
				if (square.isOccupied()){
					if (square.getPiece().getType()==PieceType.KING && square.getPiece().getPlayer()==player){
						squareOfKing=square;
					}
				}
			}
		}
		//Checks if there is check
		boolean check=false;
		for (int x=0;x<8;x++){
			for (int y=0;y<8;y++){
				Square square=squares[x][y];
				if (square.isOccupied()){
					if(square.getPiece().getPlayer()!=player){
						//This function depends on the isValidMove function
						if (isValidMove(square,squareOfKing)) check=true;
					}
				}
			}
		}
	
		return check;
	}
		
	public boolean isWithinBounds(Square s1,Square s2){
		if (s1.getCoordinate().isValidCoordinate() && s2.getCoordinate().isValidCoordinate()) return true;
		return false;
	}
	
	public boolean isValidMove(Square s1,Square s2)
	{
		if (!isWithinBounds(s1,s2)) return false; //If the move is out of bounds
		if (!s1.isOccupied()) return false; //If the player tries to move a empty square.
		if (s1.equals(s2)) return false; //If the player tries to move to the same square.
		//you don't need to check this here actually.
		
		if (s2.isOccupied())
		{
			if (s1.getPiece().getPlayer()==s2.getPiece().getPlayer()) return false; //If the player tries to take his own piece.
		}
		
		//If the piece cannot move to the square. No such movement.
		if (!s1.getPiece().isValidMove(s1.getCoordinate(),s2.getCoordinate()))
		{
			return false;
		}
		//If piece is blocked by other pieces 
		Coordinate[] path=s1.getPiece().getPath(s1.getCoordinate(), s2.getCoordinate());
		if (!isPathClear(path,s1.getCoordinate(),s2.getCoordinate())){
			return false;
		}
		// If this move makes check for the moving player.
		//If the player is in check he must move out of check.
		
		return true;
	}
	

	
}
