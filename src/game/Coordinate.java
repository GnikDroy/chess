package game;

/**
 * @author gnik
 * 
 */
public class Coordinate {
	int positionX;
	int positionY;

	/**
	 * Initializes a new coordinate with the x and y values.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 */
	public Coordinate(int x, int y) {
		positionX = x;
		positionY = y;

	}

	/**
	 * Creates a coordinate from a UCI move string
	 * @param coordinate The coordinate in string format (UCI)
	 */
	public Coordinate(String coordinate){
		positionX=(char)coordinate.toCharArray()[0]-97;
		positionY=Integer.parseInt(coordinate.substring(1,2))-1;
		
	}
	/**
	 * Checks if a Coordinate is valid or not
	 * 
	 * @return boolean If coordinate is valid
	 */
	public boolean isValid() {
		if ((positionX >= 0 && positionX < 8)
				&& (positionY >= 0 && positionY < 8)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the x coordinate
	 * 
	 * @return int The x coordinate
	 */
	public int getX() {
		return positionX;
	}

	/**
	 * Gets the y coordinate
	 * 
	 * @return int The y coordinate.
	 */
	public int getY() {
		return positionY;
	}

	/**
	 * Sets the x coordinate
	 * 
	 * @param x
	 *            The x coordinate
	 */
	public void setX(int x) {
		positionX = x;
	}

	/**
	 * Sets the y coordinate
	 * 
	 * @param y
	 *            The y coordinate
	 */
	public void setY(int y) {
		positionY = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return Integer.toString(positionX) + "," + Integer.toString(positionY);
	}

	/**
	 * Checks if two coordinates are equal. The x and y coordinates should be
	 * equal.
	 * 
	 * @param coordinate
	 *            A coordinate
	 * @return boolean If the coordinates have equal value
	 */
	public boolean equals(Coordinate coordinate) {
		if ((positionX == coordinate.getX())
				&& (positionY == coordinate.getY())) {
			return true;
		}
		return false;
	}

	/**
	 * This converts the coordinate to UCI chess notation
	 * @return String The string representation of the square in UCI form
	 */
	public String getParsedCoordinate() {
		String parsedString = "";
		parsedString = (char) (positionX + 97)
				+ Integer.toString(positionY + 1);
		return parsedString;
	}
}