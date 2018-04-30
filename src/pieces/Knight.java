package pieces;

import game.PieceType;
import game.PlayerType;
import game.Coordinate;

public class Knight extends Piece {

    public Knight(PlayerType player){
        super(player,PieceType.KNIGHT);
    }
    

	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
		if (initPos.equals(finalPos)){return false;}
		
		int diffX=Math.abs(initPos.getX()-finalPos.getX());
		int diffY=Math.abs(initPos.getY()-finalPos.getY());
		if ((diffX+diffY)==3 && diffX!=0 && diffY!=0)
		{return true;}
		
		return false;
	}

	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		//The knight can jump over pieces.
		return new Coordinate[]{initPos,finalPos};
	}

}
