package pieces;

import game.PieceType;
import game.PlayerType;
import game.Coordinate;

public abstract class Piece {
    private PieceType type;
    private PlayerType player;
    private boolean isTaken=false;

    public Piece(PlayerType player,PieceType type){
        this.type=type;
        this.player=player;
    }
    
    public String toString(){
    	return player.toString()+type.toString();
    }
    

    public void take(){isTaken=true;}
    public boolean isTaken(){return isTaken;}
    public PlayerType getPlayer(){return player;} 
    public PieceType getType(){return type;}
    public void setType(PieceType type){this.type=type;}
    
    public abstract boolean isValidMove(Coordinate initPos,Coordinate finalPos);
    public abstract Coordinate[] getPath(Coordinate initPos,Coordinate finalPos);

}
