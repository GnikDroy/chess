package game;

import java.util.List;


/**
 * @author gnik
 *
 */
public class MoveParser {

	/**
	 * @param moveList The list of moves of the game
	 * @return The parsed string of the moves
	 */
	public static String parse(List<Move> moveList){
		String parsedMoves="";
		for(Move move:moveList){
			parsedMoves+=parseMove(move)+" ";
		}
		return parsedMoves;
	}
	
	/**
	 * @param move The move object
	 * @return The parsed string of one move
	 */
	private static String parseMove(Move move){
		if (move.getInitCoordinate().equals(move.getFinalCoordinate())){return move.getPiece().toString();}
		return move.getInitCoordinate().getParsedCoordinate()+move.getFinalCoordinate().getParsedCoordinate();
	}
	
	
}
