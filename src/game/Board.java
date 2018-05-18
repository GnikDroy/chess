package game;

import pieces.*;
import player.PlayerType;



/**
 * @author gnik
 *
 */
public class Board {

	private Square[][] squares=new Square[8][8];

	
	/**
	 * This initialized the board to it's original position.
	 */

	public Board()
	{
		setSquares();
		setWhitePieces();
		setBlackPieces();
		
	}
	
	/**
	 * Resets the board to it's original position
	 */
	public void resetBoard(){
		setSquares();
		setWhitePieces();
		setBlackPieces();
	}
	
	/**
	 * Initializes all the squares
	 */
	private void setSquares(){
		for (int x=0;x<8;x++)
		{
			for (int y=0;y<8;y++)
			{
				squares[x][y]=new Square(new Coordinate(x,y));
			}
		}
		
	}

	/**
	 * Initializes and assigns all white Pieces.
	 */
	private void setWhitePieces(){
		squares[2][0].setPiece(new Bishop(PlayerType.WHITE));
		squares[5][0].setPiece(new Bishop(PlayerType.WHITE));
		squares[1][0].setPiece(new Knight(PlayerType.WHITE));
		squares[6][0].setPiece(new Knight(PlayerType.WHITE));
		squares[0][0].setPiece(new Rook(PlayerType.WHITE));
		squares[7][0].setPiece(new Rook(PlayerType.WHITE));
		squares[3][0].setPiece(new Queen(PlayerType.WHITE));
		squares[4][0].setPiece(new King(PlayerType.WHITE));
		squares[0][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[1][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[2][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[3][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[4][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[5][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[6][1].setPiece(new Pawn(PlayerType.WHITE));
		squares[7][1].setPiece(new Pawn(PlayerType.WHITE));
		
	}
	
	/**
	 * Initializes and sets all Black Pieces.
	 */
	private void setBlackPieces(){
		squares[2][7].setPiece(new Bishop(PlayerType.BLACK));
		squares[5][7].setPiece(new Bishop(PlayerType.BLACK));
		squares[1][7].setPiece(new Knight(PlayerType.BLACK));
		squares[6][7].setPiece(new Knight(PlayerType.BLACK));
		squares[0][7].setPiece(new Rook(PlayerType.BLACK));
		squares[7][7].setPiece(new Rook(PlayerType.BLACK));
		squares[3][7].setPiece(new Queen(PlayerType.BLACK));
		squares[4][7].setPiece(new King(PlayerType.BLACK));
		squares[0][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[1][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[2][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[3][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[4][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[5][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[6][6].setPiece(new Pawn(PlayerType.BLACK));
		squares[7][6].setPiece(new Pawn(PlayerType.BLACK));

	}
	
	
	/**
	 * Returns all the squares on the board. 
	 * @return Square[][] A dimensional array of all the Squares
	 */
	public Square[][] getSquares(){
		return squares;
	}
	
	
	/**
	 * This gets the square with a specific coordinate.
	 * @param coordinate Coordinate of the square
	 * @return Square The Square object
	 */
	public Square getSquare(Coordinate coordinate){
		Square result=null;
		for (int x=0;x<8;x++)
		{
			for (int y=0;y<8;y++){
				if (squares[x][y].getCoordinate().equals(coordinate))
				{
					result=squares[x][y];
				}
			}
		}
		return result;
	}
	
	/**
	 * Makes a Move from initial Coordinate to final coordinate
	 * @param initCoordinate The initial Coordinate.
	 * @param finalCoordinate The final Coordinate.
	 */
	public void makeMove(Coordinate initCoordinate,Coordinate finalCoordinate)
	{
		makeMove(getSquare(initCoordinate),getSquare(finalCoordinate));
	}
	
	/**
	 * This set the piece in the specified coordinate
	 * @param coordinate Coordinate of the piece
	 * @param piece The piece object to be set.
	 */
	public void setPiece(Coordinate coordinate,Piece piece){
		getSquare(coordinate).setPiece(piece);
	}
	/**
	 * Captures the piece that is present in the square.
	 * @param square The square of the piece
	 */
	public void capturePiece(Square square){
		if(square.isOccupied())
		{
			square.releasePiece();
		}
	}
	
	/**
	 * This makes a move from a square to another. **Move may be invalid**
	 * @param initSquare The initial Square.
	 * @param finalSquare The final Square
	 */
	public void makeMove(Square initSquare,Square finalSquare){
		//Has a piece been captured;
		if(finalSquare.isOccupied())
		{
			capturePiece(finalSquare);
		}
		//Make the move
		finalSquare.setPiece(initSquare.getPiece());
		initSquare.releasePiece();
	}
	
	/**
	 * This prints the board in the command line.
	 */
	public void printBoard(){
		for (int y=7;y>=0;y--){
			for(int x=0;x<8;x++)
			{
				System.out.print(squares[x][y].getPieceString()+" ");
			}
			System.out.print('\n');
		}
	}
	


}
