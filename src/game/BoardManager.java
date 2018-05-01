package game;
import pieces.Piece;

public class BoardManager {
	private Board board;
	
	public BoardManager(){
		this.board=new Board();
	}
	
	public Board getBoard(){return board;}
	
	public boolean move(Square s1,Square s2)
	{
		if (isValidMove(s1,s2)){
			board.makeMove(s1, s2);
			return true;
		}
		return false;
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
	
	private boolean isValidPawnCapture(Coordinate initPos,Coordinate finalPos,PlayerType player)
	{
		if (initPos.equals(finalPos)){return false;}
		
		//This is for normal pawn capture moves.
		if (Math.abs(initPos.getY()-finalPos.getY())==1 &&
			Math.abs(initPos.getX()-finalPos.getX())==1)
			{
				//White can only move forward
				if (player==PlayerType.WHITE){
					if(initPos.getY()<finalPos.getY()){
						return true;
					}
				}
				//Black can only move backward in a sense.
				if(player==PlayerType.BLACK){
					if(initPos.getY()>finalPos.getY()){
						return true;
					}
				}
		
			}
		return false;
	}
	private boolean isValidCastling(Square s1,Square s2){
		return false;
		}
	public boolean isValidMove(Square s1,Square s2)
	{
		if (!isWithinBounds(s1,s2)) return false; //If the move is out of bounds
		if (!s1.isOccupied()) return false; //If the player tries to move a empty square.
		//Moving to the square does not need to be checked since it is checked by every piece
		
		 //If the player tries to take his own piece.
		if (s2.isOccupied())
		{
			if (s1.getPiece().getPlayer()==s2.getPiece().getPlayer()) return false;
		}
		
		//Check for pawn captures.
		if (s2.isOccupied() && s1.getPiece().getType()==PieceType.PAWN)
			{	
				if (isValidPawnCapture(s1.getCoordinate(), s2.getCoordinate(),s1.getPiece().getPlayer())){return true;}
			}
		//Check for enpassant.
		//Check for castling
		
		
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
		Piece temporaryPiece=s2.getPiece();
		s2.setPiece(s1.getPiece());
		s1.releasePiece();
		if ( isCheck(s2.getPiece().getPlayer()) ){
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);
			return false;
		}
		else{
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);				
		}
		
		return true;
	}
	

	
}
