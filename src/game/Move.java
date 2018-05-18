package game;

import pieces.Piece;

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
	private Piece piece;
	
	/**
	 * This is the piece that was captured.
	 */
	private Piece capturedPiece=null;
	
	/**
	 * This is the coordinate of the captured piece 
	 */
	private Coordinate captureCoordinate=null;
	
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
			Piece piece) {
		this(initCoordinate,finalCoordinate,piece,null);
	}

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
	 * @param captureSquare The square of the piece that was captured.
	 */
	public Move(Coordinate initCoordinate, Coordinate finalCoordinate,
			Piece piece,Square captureSquare) {
		this.initCoordinate = initCoordinate;
		this.finalCoordinate = finalCoordinate;
		this.piece = piece;
		if(captureSquare!=null){
		this.capturedPiece=captureSquare.getPiece();
		this.captureCoordinate=captureSquare.getCoordinate();
		}
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
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Checks if the move was a capture
	 * @return boolean If the move was a capture move.
	 */
	public boolean isCapture(){
		if (capturedPiece==null){return false;}
		return true;
	}
	
	
	/**
	 * Returns the piece that was captured when the move was made.
	 * @return Returns the captured piece
	 */
	public Piece getCapturedPiece(){
		return capturedPiece;
	}
	/**
	 * Returns the coordinate of the capture.
	 * @return The coordinate were the capture occured.
	 */
	public Coordinate getCaptureCoordinate(){
		return captureCoordinate;
	}
}
