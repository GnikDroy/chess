package game;

import pieces.Piece;

/**
 * @author gnik
 *
 */
public class Square {
	/**
	 * The coordinate of the square
	 */
	private Coordinate coordinate;
	/**
	 * The piece object
	 */
	private Piece piece = null;

	/**
	 * Creates a new square.
	 * @param coordinate The coordinate of the square
	 * @param piece The piece if the square contains any.
	 */
	public Square(Coordinate coordinate, Piece piece) {
		this.coordinate = coordinate;
		this.piece = piece;
	}

	/**
	 * This is the alternative way to construct a square. Use if no piece is present
	 * This calls Square(Coordinate,null)
	 * @param coordinate Coordinate of the square
	 */
	public Square(Coordinate coordinate) {
		this(coordinate, null);
	}

	/**
	 * This returns the coordinate of the square.
	 * @return coordinate Coordinate of square
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * This gets the piece the square is in. 
	 * @return piece Piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Checks if two square have the same coordinate
	 * @param square Square
	 * @return boolean If the squares are equal.
	 */
	public boolean equals(Square square) {
		if (square.getCoordinate().equals(coordinate))
			return true;
		return false;
	}

	/**
	 * Checks if the square contains a piece
	 * @return boolean If square contains a piece 
	 */
	public boolean isOccupied() {
		if (piece == null) {
			return false;
		}
		return true;
	}

	/**
	 * Gets a string representation of the square
	 * @return string String representation of the square
	 */
	public String getPieceString() {
		if (piece == null) {
			return "  ";
		}
		return piece.toString();
	}

	/**
	 * Removes any piece that is currently in the square
	 */
	public void releasePiece() {
		piece = null;
	}

	/**
	 * Sets a piece to the square
	 * @param piece The piece object
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}
