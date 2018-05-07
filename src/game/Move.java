package game;

import pieces.PieceType;

/**
 * @author gnik
 * 
 */
public class Move {
	/**
	 * This is the initial coordinate of the move
	 */
	private Coordinate initCoordinate;

	/**
	 * This is the final coordinate of the move
	 */
	private Coordinate finalCoordinate;

	/**
	 * This is the type of piece
	 */
	private PieceType piece;

	/**
	 * Creates a move object. Promotions are represented as movement to the same
	 * square and the piece represented is the piece to be promoted to.
	 * 
	 * @param initCoordinate
	 *            The initial move coordinate.
	 * @param finalCoordinate
	 *            The coordinate of the final move.
	 * @param piece
	 *            The piece that was moved
	 */
	public Move(Coordinate initCoordinate, Coordinate finalCoordinate,
			PieceType piece) {
		this.initCoordinate = initCoordinate;
		this.finalCoordinate = finalCoordinate;
		this.piece = piece;
	}

	/**
	 * It returns the initial move coordinate
	 * 
	 * @return Coordinate The initial Coordinate
	 */
	public Coordinate getInitCoordinate() {
		return initCoordinate;
	}

	/**
	 * It returns the final move coordinate
	 * 
	 * @return Coordinate The final Coordinate
	 */
	public Coordinate getFinalCoordinate() {
		return finalCoordinate;
	}

	/**
	 * It returns the piece that was moved.
	 * 
	 * @return piece The piece that was moved.
	 */
	public PieceType getPiece() {
		return piece;
	}

}
