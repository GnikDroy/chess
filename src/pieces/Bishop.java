package pieces;

import player.PlayerType;
import game.Coordinate;

/**
 * @author gnik
 *
 */
public class Bishop extends Piece{

    /**
     * Create a bishop
     * @param player The player the bishop belongs to
     */
    public Bishop(PlayerType player){
        super(player,PieceType.BISHOP);
    }

	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
		if (initPos.equals(finalPos)){return false;}
		
		int diffX=Math.abs(initPos.getX()-finalPos.getX());
		int diffY=Math.abs(initPos.getY()-finalPos.getY());
		if (diffX==diffY) return true;
		
		return false;
	}

	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		int pathLength=( Math.abs(initPos.getX()-finalPos.getX())+
						Math.abs(initPos.getY()-finalPos.getY()) )/2+1;
		Coordinate[] path=new Coordinate[pathLength];
		
		//Integer.signum(a) provides the sign of a number 1 if positive and -1 if negative.
		//In this case i am considering initPos as the first point and finalPos as second
		int i_X=Integer.signum(finalPos.getX()-initPos.getX());
		int i_Y=Integer.signum(finalPos.getY()-initPos.getY());

		for (int cnt=0;cnt<pathLength;cnt++)
		{
			path[cnt]=new Coordinate(initPos.getX()+i_X*cnt,initPos.getY()+i_Y*cnt);
		}

		
		return path;
	}


}
