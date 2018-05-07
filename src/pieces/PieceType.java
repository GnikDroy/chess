package pieces;

/**
 * @author gnik
 *
 */
public enum PieceType {
	KING("k"), KNIGHT("n"), ROOK("r"), QUEEN("q"), BISHOP("b"), PAWN("p");

	private String value;

	PieceType(String value) {
		this.value = value;

	}

	@Override
	public String toString() {
		return this.value;
	}
	public static PieceType fromString(String value){
	    for (PieceType piece :PieceType.values()) {
	        if (piece.value.equalsIgnoreCase(value)) {
	          return piece;
	        }
	      }
	      return null;
	}

}