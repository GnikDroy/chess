package pieces;


import player.PlayerType;
import game.Coordinate;

/**
 * @author gnik
 *
 */
public class Pawn extends Piece {
	
    /**
     * Create a pawn
     * @param player The player the piece belongs to 
     */
    public Pawn(PlayerType player){
        super(player,PieceType.PAWN);
    }

	@Override
	public boolean isValidMove(Coordinate initPos,Coordinate finalPos) {
		if (initPos.equals(finalPos)){return false;}
		
		//This is for normal pawn moves.
		if (Math.abs(initPos.getY()-finalPos.getY())==1 &&
			Math.abs(initPos.getX()-finalPos.getX())==0)
			{
				//White can only move forward
				if (this.getPlayer()==PlayerType.WHITE){
					if(initPos.getY()<finalPos.getY()){
						return true;
					}
				}
				//Black can only move backward in a sense.
				if(this.getPlayer()==PlayerType.BLACK){
					if(initPos.getY()>finalPos.getY()){
						return true;
					}
				}
		
			}
		
		//This is for first pawn move.
		if (Math.abs(initPos.getY()-finalPos.getY())==2 &&
				Math.abs(initPos.getX()-finalPos.getX())==0 &&
				(initPos.getY()==1 || initPos.getY()==6)     )
			{
			
				//White can only move forward
				if (this.getPlayer()==PlayerType.WHITE){
					if(initPos.getY()<finalPos.getY()){
						return true;
					}
				}
				//Black can only move backward in a sense.
				if(this.getPlayer()==PlayerType.BLACK){
					if(initPos.getY()>finalPos.getY()){
						return true;
					}
				}
					
			}
			
			
		//This if for normal pawn captures.
		//this is for Enpassant.
			
		return false;
	}
	
	@Override
	public Coordinate[] getPath(Coordinate initPos,Coordinate finalPos) {
		return new Coordinate[]{initPos,finalPos};
	}


}
