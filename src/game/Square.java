package game;

import pieces.Piece;
public class Square {
	private Coordinate coordinate;
	private Piece piece=null;
	public Square(Coordinate coordinate,Piece piece)
	{
		this.coordinate=coordinate;
		this.piece=piece;
	}
	public Square(Coordinate coordinate)
	{
		this(coordinate,null);
	}
	
	public Coordinate getCoordinate(){return coordinate;}
	public Piece getPiece(){return piece;}
	
	public boolean equals(Square s){
		if (s.getCoordinate().equals(coordinate)) return true;
		return false;
	}
	public boolean isOccupied(){
		if (piece==null){
			return false;
		}
		return true;
	}
	
	public String getPieceString(){
		if (piece==null){
			return "  ";
		}
		return piece.toString();
	}
	public void releasePiece(){this.piece=null;}
	public void setPiece(Piece piece){
		this.piece=piece;
	}

}
