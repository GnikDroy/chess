package pieces;

import player.PlayerType;
import game.Coordinate;

/**
 * @author gnik
 *
 */
public class Queen extends Piece{

    /**
     * Create a Queen
     * @param player The player the queen belongs to
     */
    public Queen(PlayerType player){
        super(player,PieceType.QUEEN);
    }


	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
		if (initPos.equals(finalPos)){return false;}
		
		//This is the bishop move.
		int diffX=Math.abs(initPos.getX()-finalPos.getX());
		int diffY=Math.abs(initPos.getY()-finalPos.getY());
		if (diffX==diffY) return true;
		
		//This is the rook move.
		if (initPos.getX()==finalPos.getX() ||
				initPos.getY()==finalPos.getY())
			{return true;}
		
		return false;
	}

	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		Coordinate[] path;
		//If it is a rook move
		if (initPos.getX()==finalPos.getX() ||
				initPos.getY()==finalPos.getY())
		{
			int pathLength=Math.abs(initPos.getX()-finalPos.getX())
					+Math.abs(initPos.getY()-finalPos.getY())+1;
			path=new Coordinate[pathLength];
			for (int cnt=0;cnt<pathLength;cnt++)
			{
				if ((initPos.getX()==finalPos.getX())){
					path[cnt]=new Coordinate(initPos.getX(),Math.min(initPos.getY(),finalPos.getY())+cnt);
				}
				else{
					path[cnt]=new Coordinate(Math.min(initPos.getX(),finalPos.getX())+cnt,initPos.getY());
				}
			}
			
		}
		else
		{
			//If it a bishop move.
			int pathLength=( Math.abs(initPos.getX()-finalPos.getX())+
					Math.abs(initPos.getY()-finalPos.getY()) )/2+1;
			path=new Coordinate[pathLength];
	
			//Integer.signum(a) provides the sign of a number 1 if positive and -1 if negative.
			//In this case i am considering initPos as the first point and finalPos as second
			int i_X=Integer.signum(finalPos.getX()-initPos.getX());
			int i_Y=Integer.signum(finalPos.getY()-initPos.getY());
	
			for (int cnt=0;cnt<pathLength;cnt++)
			{
				path[cnt]=new Coordinate(initPos.getX()+i_X*cnt,initPos.getY()+i_Y*cnt);
			}
		}
	
		
		
		
		
		return path;
	}

}
