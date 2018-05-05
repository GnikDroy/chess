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
			
			if (isValidMove(s1, s2)) {
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
						// This function depends on the isValidMove function
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

	public boolean isValidMovement(Square s1, Square s2) {
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
	
	public boolean isValidMove(Square s1,Square s2)
	{	
		if(!isValidMovement(s1,s2)){return false;}
		// If this move makes check for the moving player.
		// If the player is in check he must move out of check.
		Piece temporaryPiece = s2.getPiece();
		s2.setPiece(s1.getPiece());
		s1.releasePiece();
		if (isCheck(s2.getPiece().getPlayer())) {
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);
			return false;
		} else {
			s1.setPiece(s2.getPiece());
			s2.setPiece(temporaryPiece);
		}
		return true;
	}

}
