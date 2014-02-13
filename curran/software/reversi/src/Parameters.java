import java.awt.Color;

/**
 * A class containing all hard-coded values of the reversi application.
 * 
 * @author curran
 * 
 */
public class Parameters {
	/**
	 * The number of cells per side of the square grid in which the reversi
	 * pieces will be placed
	 */
	public static int gridSize = 8;

	/**
	 * The total width and height of the square board (in pixels)
	 */
	public static int boardSize = gridSize * 50 + 1;

	/**
	 * The diameter (in pixels) of the circular reversi pieces
	 */
	public static int pieceSize = (boardSize - 1) / gridSize - 3;

	/**
	 * The background color of the board
	 */
	public static Color backgroundColor = Color.green;

	/**
	 * The color of the lines used to draw the grid
	 */
	public static Color linesColor = Color.black;
}
