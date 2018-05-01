package pieces;

import game.PieceType;
import game.PlayerType;
import game.Coordinate;

/**
 * @author gnik
 *
 */
public abstract class Piece {
    private PieceType type;
    private PlayerType player;
    private boolean isTaken=false;

    /**
     * Initialize a piece with a playerType.
     * @param player
     * @param type
     */
    public Piece(PlayerType player,PieceType type){
        this.type=type;
        this.player=player;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	return player.toString()+type.toString();
    }
    

    /**
     * Sets the isTaken to true.
     */
    public void take(){isTaken=true;}
    
    /**
     * Returns if the piece is taken or not.
     * @return boolean
     */
    public boolean isTaken(){return isTaken;}
    
    
    /**
     * Returns the playerType.
     * @return PlayerType
     */
    public PlayerType getPlayer(){return player;} 
    
    /**
     * Returns the type of piece.
     * @return PieceType
     */
    public PieceType getType(){return type;}
    
    
    /**
     * Sets the type of piece. Useful for promotions
     * @param type
     */
    public void setType(PieceType type){this.type=type;}
    
    /**
     * Checks if the move is a valid move by the piece.
     * @param initPos
     * @param finalPos
     * @return
     */
    public abstract boolean isValidMove(Coordinate initPos,Coordinate finalPos);
    
    
    /**
     * Return the path for movement.
     * @param initPos
     * @param finalPos
     * @return
     */
    public abstract Coordinate[] getPath(Coordinate initPos,Coordinate finalPos);
    

}
