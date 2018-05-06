package pieces;

/**
 * @author gnik
 *
 */
public enum PieceType {
	KING("K"), KNIGHT("N"), ROOK("R"), QUEEN("Q"), BISHOP("B"), PAWN("P");

	private String value;

	PieceType(String value) {
		this.value = value;

	}

	@Override
	public String toString() {
		return this.value;
	}
}