package game;

import pieces.*;
import player.PlayerType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gnik
 *
 */
public class BoardManager {
	/**
	 * The board object
	 */
	private Board board;
	
	/**
	 * Current Player which is to move.
	 * Default is PlayerType.WHITE
	 */
	private PlayerType currentPlayer = PlayerType.WHITE;

	/**
	 * This is the list that holds all the moves made by the user.
	 */
	private List<Move> moveList=new ArrayList<Move>();
	
	/**
	 * Constructs a new BoardManager object
	 */
	public BoardManager() {
		this.board = new Board();
	}

	/**
	 * Resets the board to it's initial state
	 */
	public void resetBoard() {
		board.resetBoard();
		currentPlayer = PlayerType.WHITE;
	}

	/**
	 * Switches the player currently to move.
	 */
	private void switchCurrentPlayer() {
		if (currentPlayer == PlayerType.WHITE) {
			currentPlayer = PlayerType.BLACK;
		} else {
			currentPlayer = PlayerType.WHITE;
		}

	}

	/**
	 * Return the player who is to move
	 * @return PlayerType The player
	 */
	public PlayerType getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Returns a list of moves that the player has made.
	 * @return List The list of moves
	 */
	public List<Move> getMoveList(){
		return moveList;
	}
	
	/**
	 * Returns the board object
	 * @return board The board object
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Promotes a pawn to a newer piece.
	 * Calls isValidPromotion function first
	 * @param square Promotion Square
	 * @param pieceType The type of piece to promote to. If none is provided it defaults to Queen.
	 * @return boolean If the promotion was made
	 */
	public boolean promote(Square square, PieceType pieceType) {
		if (isValidPromotion(square)) {
			Piece piece;
			moveList.add(new Move(square.getCoordinate(),square.getCoordinate(),pieceType));
			if (pieceType == PieceType.BISHOP) {
				piece = new Bishop(square.getPiece().getPlayer());
			} else if (pieceType == PieceType.KNIGHT) {
				piece = new Knight(square.getPiece().getPlayer());
			} else if (pieceType == PieceType.ROOK) {
				piece = new Rook(square.getPiece().getPlayer());
			} else {
				piece = new Queen(square.getPiece().getPlayer());
			}
			square.setPiece(piece);
			return true;
		}
		return false;
	}

	/**
	 * Checks if the square contains a pawn that can be promoted.
	 * @param square Square of the pawn
	 * @return boolean If the pawn can be promoted
	 */
	public boolean isValidPromotion(Square square) {
		if (!square.isOccupied() == true) {
			return false;
		}
		if (square.getPiece().getType() == PieceType.PAWN) {
			int col = 7;
			if (square.getPiece().getPlayer() == PlayerType.BLACK) {
				col = 0;
			}
			if (square.getCoordinate().equals(
					new Coordinate(square.getCoordinate().getX(), col))) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Makes a move from initial coordinate to final one.
	 * It calls isValidMove(),isValidCastling() and isValidEnpassant()
	 * @param initCoordinate Initial Coordinate
	 * @param finalCoordinate Final Coordinate
	 * @return boolean If the move was made
	 */
	public boolean move(Coordinate initCoordinate, Coordinate finalCoordinate) {
		if (!(initCoordinate.isValid() && finalCoordinate.isValid())) {
			return false;
		}
		Square s1 = board.getSquare(initCoordinate);
		Square s2 = board.getSquare(finalCoordinate);

		// If the player tries to move a empty square.
		if (!s1.isOccupied()) {
			return false;
		}

		// Only the current player can move the piece.
		if (currentPlayer == s1.getPiece().getPlayer()) {
			if (isValidCastling(s1, s2)) {
				switchCurrentPlayer();
				moveList.add(new Move(s1.getCoordinate(),s2.getCoordinate(),s1.getPiece().getType()));
				castle(s1, s2);
				return true;
			} else if (isValidEnpassant(s1, s2)) {
				moveList.add(new Move(s1.getCoordinate(),s2.getCoordinate(),s1.getPiece().getType()));
				enpassant(s1, s2);
				return true;
			} else if (isValidMove(s1, s2)) {
				switchCurrentPlayer();
				moveList.add(new Move(s1.getCoordinate(),s2.getCoordinate(),s1.getPiece().getType()));
				board.makeMove(s1, s2);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the move is valid enpassant move.
	 * @param s1 Initial Square
	 * @param s2 Final Square
	 * @return boolean If enpassant valid
	 */
	public boolean isValidEnpassant(Square s1, Square s2) {
		return false;
	}

	
	/**
	 * Makes a Enpassant move
	 * @param initSquare Initial Square
	 * @param finalSquare Final Square
	 */
	public void enpassant(Square initSquare, Square finalSquare) {

	}

	/**
	 * Checks if the given move makes check for the moving player
	 * @param initSquare Initial Square
	 * @param finalSquare Final Square
	 * @return boolean If the move makes check.
	 */
	public boolean moveMakesCheck(Square initSquare, Square finalSquare) {
		Piece temporaryPiece = finalSquare.getPiece();
		finalSquare.setPiece(initSquare.getPiece());
		initSquare.releasePiece();
		if (isCheck(finalSquare.getPiece().getPlayer())) {
			initSquare.setPiece(finalSquare.getPiece());
			finalSquare.setPiece(temporaryPiece);

			return true;
		} else {
			initSquare.setPiece(finalSquare.getPiece());
			finalSquare.setPiece(temporaryPiece);
		}
		return false;
	}

	/**
	 * Checks if there is check for the player
	 * 
	 * @param player Is this player in check
	 * @return boolean If the player is in check
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

	/**
	 * Checks if the move is valid pawn capture move
	 * @param initSquare Initial Square
	 * @param finalSquare Final Square
	 * @return boolean If the pawn capture is valid
	 */
	private boolean isValidPawnCapture(Square initSquare, Square finalSquare) {
		// If the piece is not a pawn OR this is not a capture.
		if (!finalSquare.isOccupied() || initSquare.getPiece().getType() != PieceType.PAWN) {
			return false;
		}
		Coordinate initPos = initSquare.getCoordinate();
		Coordinate finalPos = finalSquare.getCoordinate();
		PlayerType player = initSquare.getPiece().getPlayer();

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

	/**
	 * Checks if it is valid Castling move
	 * @param kingSquare The square of the king
	 * @param rookSquare The square of the rook
	 * @return boolean If this is valid Castling
	 */
	public boolean isValidCastling(Square kingSquare, Square rookSquare) {
		// Check if the squares are occupied.
		if (!(kingSquare.isOccupied() && rookSquare.isOccupied())) {
			return false;
		}
		// You cannot castle if you are in check.

		if (isCheck(kingSquare.getPiece().getPlayer())) {
			return false;
		}

		// Check if the path is clear
		if (!isPathClear(
				rookSquare.getPiece().getPath(rookSquare.getCoordinate(),
						kingSquare.getCoordinate()),
				rookSquare.getCoordinate(), kingSquare.getCoordinate())) {
			return false;
		}

		// Now check if the movement of the castling is fine
		if (kingSquare.getPiece().getType() == PieceType.KING
				&& rookSquare.getPiece().getType() == PieceType.ROOK) {

			int col = 0;
			if (kingSquare.getPiece().getPlayer() == PlayerType.BLACK) {
				col = 7;
			}
			// The peices are in correct position for castling.

			if (kingSquare.getCoordinate().equals(new Coordinate(4, col))
					&& (rookSquare.getCoordinate().equals(
							new Coordinate(0, col)) || rookSquare
							.getCoordinate().equals(new Coordinate(7, col)))) {

				// Check if there is check in any way between the king and rook
				int offset;
				if (Math.signum(rookSquare.getCoordinate().getX()
						- kingSquare.getCoordinate().getX()) == 1) {
					offset = 2;
				} else {
					offset = -2;
				}
				int kingX = kingSquare.getCoordinate().getX() + offset;
				for (Coordinate coordinate : rookSquare.getPiece()
						.getPath(
								kingSquare.getCoordinate(),
								new Coordinate(kingX, kingSquare
										.getCoordinate().getY()))) {
					if (kingSquare.equals(board.getSquare(coordinate))) {
						// This removes a nasty null pointer exception
						continue;
					}
					if (moveMakesCheck(kingSquare, board.getSquare(coordinate))) {
						return false;
					}
				}

				return true;
			}
		}
		return false;
	}

	/**
	 * Makes a castle move.<p>
	 * It calls the isValidCastling() first.
	 * @param kingSquare The square of the King
	 * @param rookSquare The square of the Rook
	 */
	public void castle(Square kingSquare, Square rookSquare) {
		int offset;
		if (Math.signum(rookSquare.getCoordinate().getX()
				- kingSquare.getCoordinate().getX()) == 1) {
			offset = 2;
		} else {
			offset = -2;
		}
		int kingX = kingSquare.getCoordinate().getX() + offset;
		int rookX = kingX - offset / 2;
		board.makeMove(kingSquare.getCoordinate(), new Coordinate(kingX,
				kingSquare.getCoordinate().getY()));
		board.makeMove(rookSquare.getCoordinate(), new Coordinate(rookX,
				rookSquare.getCoordinate().getY()));
	}

	/**
	 * Checks if there are any obstacles between the pieces.
	 * @param path The path between the pieces
	 * @param initCoordinate Initial Coordinate to ignore
	 * @param finalCoordinate Final Coordinate to ignore
	 * @return boolean Is path clear
	 */
	public boolean isPathClear(Coordinate[] path, Coordinate initCoordinate,
			Coordinate finalCoordinate) {
		Square[][] squares = board.getSquares();
		for (Coordinate coordinate : path) {
			if ((squares[coordinate.getX()][coordinate.getY()].isOccupied())
					&& (!coordinate.equals(initCoordinate))
					&& (!coordinate.equals(finalCoordinate))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the piece can make a valid movement to the square.
	 * @param initSquare Initial Square
	 * @param finalSquare Final Square
	 * @return boolean If movement is valid
	 */
	public boolean isValidMovement(Square initSquare, Square finalSquare) {
		// I am not checking if the squares have valid coordinate because, they
		// should have it.
		// If the player tries to move a empty square.
		if (!initSquare.isOccupied()) {
			return false;
		}
		// If it is moving to the same square.
		// This is also checked by every piece but still for safety
		if (initSquare.equals(finalSquare)) {
			return false;
		}
		// If the player tries to take his own piece.
		if (finalSquare.isOccupied()) {
			if (initSquare.getPiece().getPlayer() == finalSquare.getPiece().getPlayer())
				return false;
		}

		// Check all movements here. Normal Moves, Pawn Captures, Castling,
		// EnPassant.
		// If the piece cannot move to the square. No such movement.
		if (!initSquare.getPiece().isValidMove(initSquare.getCoordinate(), finalSquare.getCoordinate())
				&& !isValidPawnCapture(initSquare, finalSquare)) {
			return false;
		}
		// Pawns cannot capture forward.
		if (initSquare.getPiece().getType() == PieceType.PAWN && finalSquare.isOccupied()
				&& !isValidPawnCapture(initSquare, finalSquare)) {
			return false;
		}

		// If piece is blocked by other pieces
		Coordinate[] path = initSquare.getPiece().getPath(initSquare.getCoordinate(),
				finalSquare.getCoordinate());
		if (!isPathClear(path, initSquare.getCoordinate(), finalSquare.getCoordinate())) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the given move is valid and safe.
	 * Calls the isValidMovement() and moveMakesCheck().
	 * @param initSquare The initial Square
	 * @param finalSquare The final Square
	 * @return boolean Whether move is valid
	 */
	public boolean isValidMove(Square initSquare, Square finalSquare) {

		if (!isValidMovement(initSquare, finalSquare)) {
			return false;
		}
		if (moveMakesCheck(initSquare, finalSquare)) {
			return false;
		}
		return true;
	}

}
