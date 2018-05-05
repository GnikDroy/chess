package game;

import pieces.Piece;
import game.PlayerType;

public class BoardManager {
	private Board board;
	private PlayerType currentPlayer = PlayerType.WHITE;

	public BoardManager() {
		this.board = new Board();
	}
	
	public void resetBoard(){
		board.resetBoard();
		currentPlayer=PlayerType.WHITE;
	}
	
	private void switchCurrentPlayer() {
		if (currentPlayer == PlayerType.WHITE) {
			currentPlayer = PlayerType.BLACK;
		} else {
			currentPlayer = PlayerType.WHITE;
		}

	}

	public PlayerType getCurrentPlayer() {
		return currentPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public boolean move(Coordinate c1, Coordinate c2) {
		
		Square s1 = board.getSquare(c1);
		Square s2 = board.getSquare(c2);
		
		// If the player tries to move a empty square.
				if (!s1.isOccupied()) {
					return false;
				} 
				
		//Only the current player can move the piece.
		if (currentPlayer == s1.getPiece().getPlayer()) {
			if(isValidCastling(s1,s2))
			{
				switchCurrentPlayer();
				castle(s1,s2);
				return true;
			}
			else if (isValidMove(s1, s2)) {
				switchCurrentPlayer();
				board.makeMove(s1, s2);
				return true;
			}
		}
		return false;
	}

	public boolean isPathClear(Coordinate[] path, Coordinate initPos,Coordinate finalPos) {
		Square[][] squares = board.getSquares();
		for (Coordinate coordinate : path) {
			if ((squares[coordinate.getX()][coordinate.getY()].isOccupied())
					&& (!coordinate.equals(initPos))
					&& (!coordinate.equals(finalPos))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if there is check for the player
	 * 
	 * @param player
	 * @return boolean
	 */
	public boolean isCheck(PlayerType player) {
		Square[][] squares = board.getSquares();
		// Gets the square of the king.
		Square squareOfKing = null;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square square = squares[x][y];
				if (square.isOccupied()) {
					if (square.getPiece().getType() == PieceType.KING
							&& square.getPiece().getPlayer() == player) {
						squareOfKing = square;
					}
				}
			}
		}
		// Checks if there is check
		boolean check = false;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square square = squares[x][y];
				if (square.isOccupied()) {
					if (square.getPiece().getPlayer() != player) {
						// This function depends on the isValidMovement function
						if (isValidMovement(square, squareOfKing))
							check = true;
					}
				}
			}
		}
		return check;
	}


	private boolean isValidPawnCapture(Square s1,Square s2) {
		//If the piece is not a pawn OR this is not a capture.
		if (!s2.isOccupied() || s1.getPiece().getType() != PieceType.PAWN) {
			return false;
		}
		Coordinate initPos=s1.getCoordinate();
		Coordinate finalPos=s2.getCoordinate();
		PlayerType player=s1.getPiece().getPlayer();

		// This is for normal pawn capture moves.
		if (Math.abs(initPos.getY() - finalPos.getY()) == 1
				&& Math.abs(initPos.getX() - finalPos.getX()) == 1) {
			// White can only move forward
			if (player == PlayerType.WHITE) {
				if (initPos.getY() < finalPos.getY()) {
					return true;
				}
			}
			// Black can only move backward in a sense.
			if (player == PlayerType.BLACK) {
				if (initPos.getY() > finalPos.getY()) {
					return true;
				}
			}

		}
		return false;
	}
	
	public boolean isValidCastling(Square s1, Square s2){
		//Check if the squares are occupied.
		if (!(s1.isOccupied() &&  s2.isOccupied())) {return false;}
		//You cannot castle if you are in check.

		if (isCheck(s1.getPiece().getPlayer())){return false;}

		//Check if the path is clear
		if (!isPathClear( s2.getPiece().getPath(s2.getCoordinate(), s1.getCoordinate()),s2.getCoordinate(),s1.getCoordinate() )){return false;}

		//Now check if the movement of the castling is fine
		if (s1.getPiece().getType()==PieceType.KING && s2.getPiece().getType()==PieceType.ROOK)
		{

			int col=0;
			if (s1.getPiece().getPlayer()==PlayerType.BLACK){col=7;}
			//The peices are in correct position for castling.

			if (s1.getCoordinate().equals(new Coordinate(4,col)) &&
				(s2.getCoordinate().equals(new Coordinate(0,col)) || s2.getCoordinate().equals(new Coordinate(7,col)) ))
			{

				//Check if there is check in any way between the king and rook
				for (Coordinate coordinate:s2.getPiece().getPath(s2.getCoordinate(), s1.getCoordinate())){
					if(s1.equals(board.getSquare(coordinate))){continue;}
					if (moveMakesCheck(s1,board.getSquare(coordinate)) ){
						return false;
					}
				}

				return true;
			}
		}
		return false;
	}
	
	public void castle(Square s1,Square s2)
	{
		int offset;
		if (Math.signum(s2.getCoordinate().getX()-s1.getCoordinate().getX())==1)
		{offset=2;}
		else{offset=-2;}
		int kingX=s1.getCoordinate().getX()+offset;
		int rookX=kingX-offset/2;
		board.makeMove(s1.getCoordinate(),new Coordinate(kingX,s1.getCoordinate().getY()));
		board.makeMove(s2.getCoordinate(),new Coordinate(rookX,s2.getCoordinate().getY()));
	}
	public boolean isValidMovement(Square s1, Square s2) {
		//I am not checking if the squares have valid coordinate because, they should have it.
		// If the player tries to move a empty square.
		if (!s1.isOccupied()) {
			return false;
		} 
		// If it is moving to the same square.
		//This is also checked by every piece but still for safety
		if (s1.equals(s2)) {
			return false;
		}
		// If the player tries to take his own piece.
		if (s2.isOccupied()) {
			if (s1.getPiece().getPlayer() == s2.getPiece().getPlayer())
				return false;
		}
		
		//Check all movements here. Normal Moves, Pawn Captures, Castling, EnPassant.
		// If the piece cannot move to the square. No such movement.
		if ( !s1.getPiece().isValidMove(s1.getCoordinate(), s2.getCoordinate())
			&& !isValidPawnCapture(s1,s2) ) {
			return false;
		}
		//Pawns cannot capture forward.
		if(s1.getPiece().getType()==PieceType.PAWN && s2.isOccupied() && !isValidPawnCapture(s1,s2))
		{
			return false;
		}
		
		// If piece is blocked by other pieces
		Coordinate[] path = s1.getPiece().getPath(s1.getCoordinate(),
				s2.getCoordinate());
		if (!isPathClear(path, s1.getCoordinate(), s2.getCoordinate())) {
			return false;
		}
		return true;
	}
	
	public boolean moveMakesCheck(Square s1, Square s2)
	{
		Piece temporaryPiece = s2.getPiece();
		s2.setPiece(s1.getPiece());
		s1.releasePiece();
		if (isCheck(s2.getPiece().getPlayer())) {
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);
			
			return true;
		} else {
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);
		}
		return false;
	}
	
	public boolean isValidMove(Square s1,Square s2)
	{	
		
		if(!isValidMovement(s1,s2)){return false;}
		if(moveMakesCheck(s1,s2)){return false;}	
		return true;
	}
	
	public static void main(String args[])
	{
		BoardManager b=new BoardManager();
		b.move(new Coordinate(4,1), new Coordinate(4,3));
		b.move(new Coordinate(0,6), new Coordinate(0,5));
		b.move(new Coordinate(5,0), new Coordinate(3,2));
		b.move(new Coordinate(0,5), new Coordinate(0,4));
		b.move(new Coordinate(6,0), new Coordinate(5,2));
		b.move(new Coordinate(0,4), new Coordinate(0,3));
		boolean t=b.move(new Coordinate(4,0), new Coordinate(7,0));
		
		b.getBoard().printBoard();
	}

}
