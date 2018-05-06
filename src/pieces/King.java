package pieces;


import player.PlayerType;
import game.Coordinate;

/**
 * @author gnik
 *
 */
public class King extends Piece{
    /**
     * Create a King
     * @param player The player the king belongs to
     */
    public King(PlayerType player){
        super(player,PieceType.KING);
    }


	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
			if (initPos.equals(finalPos)){return false;}
			
			//You have not checked for castling
			if (   Math.abs(finalPos.getX()-initPos.getX())<=1 
			    && Math.abs(finalPos.getY()-initPos.getY())<=1 )
			{
						return true;
			}
			
			return false;
	}

	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		return new Coordinate[]{initPos,finalPos};
	}


}
