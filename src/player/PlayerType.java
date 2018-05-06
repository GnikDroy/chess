package player;

/**
 * @author gnik
 *
 */

public enum PlayerType {
	WHITE("W"), BLACK("B");

	private String value;

	PlayerType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}