package pieces;


import game.PieceType;
import game.PlayerType;
import game.Coordinate;


public class Rook extends Piece{

    public Rook(PlayerType player){
        super(player,PieceType.ROOK);
    }


	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
		if (initPos.equals(finalPos)){return false;}
		
		if (initPos.getX()==finalPos.getX() ||
			initPos.getY()==finalPos.getY())
		{return true;}
		return false;
	}

	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		int pathLength=Math.abs(initPos.getX()-finalPos.getX())
						+Math.abs(initPos.getY()-finalPos.getY())+1;
		Coordinate[] path=new Coordinate[pathLength];
		for (int cnt=0;cnt<pathLength;cnt++)
		{
			if ((initPos.getX()==finalPos.getX())){
				path[cnt]=new Coordinate(initPos.getX(),Math.min(initPos.getY(),finalPos.getY())+1);
			}
			else{
				path[cnt]=new Coordinate(Math.min(initPos.getX(),finalPos.getX())+1,initPos.getY());
			}
		}
		return path;
	}



}
