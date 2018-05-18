package pieces;

import player.PlayerType;
import game.Coordinate;


/**
 * @author gnik
 *
 */
public abstract class Piece {
    private PieceType type;
    private PlayerType player;

    /**
     * Initialize a piece with a playerType.
     * @param player The player the piece belongs to 
     * @param type The piece type
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
     * Returns the playerType.
     * @return PlayerType player
     */
    public PlayerType getPlayer(){return player;} 
    
    /**
     * Returns the type of piece.
     * @return PieceType Piece
     */
    public PieceType getType(){return type;}
    
    
    
    /**
     * Checks if the move is a valid move by the piece.
     * @param initPos Initial Coordinate 
     * @param finalPos Final Coordinate
     * @return boolean If the move is Valid
     */
    public abstract boolean isValidMove(Coordinate initPos,Coordinate finalPos);
    
    
    /**
     * Return the path for movement.
     * @param initPos The initial Coordinate
     * @param finalPos The final Coordinate
     * @return Coordinate[] The Path for the movement 
     */
    public abstract Coordinate[] getPath(Coordinate initPos,Coordinate finalPos);
    

}
