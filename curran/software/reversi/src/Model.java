import java.awt.Color;

/**
 * The model part (in the model/view/controller paradim) of the Reversi
 * application. It contains the data describing the state of the game,
 * implements the rules of the game, and updates the view when the state
 * changes.
 * 
 * @author curran
 * 
 */
public class Model {
	/**
	 * The constant used to represent the state of a board space as being empty
	 */
	public static final int COLOR_CODE_EMPTY = 0;
	/**
	 * The constant used to represent the state of a board space which has a
	 * white piece in it
	 */
	public static final int COLOR_CODE_WHITE = 1;
	/**
	 * The constant used to represent the state of a board space which has a
	 * black piece in it
	 */
	public static final int COLOR_CODE_BLACK = 2;

	/**
	 * The state of the board - all positions in the 2D array are filled with
	 * either COLOR_CODE_EMPTY,COLOR_CODE_WHITE,or COLOR_CODE_BLACK
	 */
	static int[][] board = new int[Parameters.gridSize][Parameters.gridSize];

	/**
	 * The color of the next move - always either COLOR_CODE_WHITE or
	 * COLOR_CODE_BLACK. This is used by the view to display the correct color
	 * as the cursor.
	 */
	static int nextMoveColor = COLOR_CODE_WHITE;

	/**
	 * The view for the program - it displays the board and cursor
	 */
	public static View view = new View();

	/**
	 * The controller for the program - it is the bottleneck for all user
	 * actions which change the model.
	 */
	public static Controller controller = new Controller();

	/**
	 * Calculates the grid position for the specified pixel position, then calls
	 * makeMove() with those calculated values
	 */
	public static void placePieceOnBoard(int xPixel, int yPixel) {
		// calculate the grid position from the pixel position
		int gridX = (int) (Parameters.gridSize * ((double) xPixel / Parameters.boardSize));
		int gridY = (int) (Parameters.gridSize * ((double) yPixel / Parameters.boardSize));

		makeMove(gridX, gridY);
	}

	/**
	 * Places a piece of color Model.nextMoveColor at the specified position in
	 * the board grid, flips all of the pieces that should be flipped as a
	 * result of the move, then updates the display. If there is already a piece
	 * at the specified grid position, then nothing happens.
	 */
	private static void makeMove(int gridX, int gridY) {
		if (board[gridX][gridY] == COLOR_CODE_EMPTY) {
			// place the piece in the empty cell
			board[gridX][gridY] = nextMoveColor;

			// flip the pieces that should be flipped as a result of this move
			flipPiecesForMove(gridX, gridY);

			// swap the next move color
			nextMoveColor = swapColorCode(nextMoveColor);

			// update the display
			view.drawBoard();
		}
	}

	/**
	 * Implements the flipping rules of the reversi game - flips the pieces that
	 * should be flipped as a result of a new move with the specified position
	 * in the board grid and the color contained in Model.nextMoveColor
	 */
	private static void flipPiecesForMove(int gridX, int gridY) {
		for (int directionX = -1; directionX <= 1; directionX++)
			for (int directionY = -1; directionY <= 1; directionY++)
				if (directionX != 0 || directionY != 0)
					flipInDirection(directionX, directionY, gridX, gridY);
	}

	/**
	 * Flips the pieces in a specified direction
	 * 
	 * @param directionX
	 *            -1,0,or 1 - the x direction in which to flip
	 * @param directionY
	 *            -1,0,or 1 - the y direction in which to flip
	 * @param gridX
	 *            the board grid x position of the new piece
	 * @param gridY
	 *            the board grid y position of the new piece
	 */
	private static void flipInDirection(int directionX, int directionY,
			int gridX, int gridY) {
		// start checking in the direction specified
		for (int distance = 1;; distance++) {
			int nextX = gridX + directionX * distance;
			int nextY = gridY + directionY * distance;

			// stop testing if we've gone off the edge of the board
			if (nextX >= Parameters.gridSize || nextY >= Parameters.gridSize
					|| nextX < 0 || nextY < 0)
				return;

			// continue testing if the current piece is the opposite color
			if (board[nextX][nextY] == swapColorCode(nextMoveColor))
				continue;
			else if (distance > 1 && board[nextX][nextY] == nextMoveColor) {
				// flip the inside pieces once we've found a closed in string of
				// pieces of the opposite color
				for (int d = 1; d < distance; d++)
					board[gridX + directionX * d][gridY + directionY * d] = nextMoveColor;
				return;
			} else
				// if we are here then it means that the first piece we hit was
				// either the same color as the new piece, or we hit an empty
				// space, so we should stop searching.
				return;
		}
	}

	/**
	 * Returns the opposite color of the specified color code, or
	 * COLOR_CODE_EMPTY if it is passed COLOR_CODE_EMPTY.
	 */
	public static int swapColorCode(int colorCode) {
		return colorCode == COLOR_CODE_EMPTY ? colorCode
				: colorCode == COLOR_CODE_WHITE ? COLOR_CODE_BLACK
						: COLOR_CODE_WHITE;
	}

	/**
	 * Gets the color for the specified color code.
	 * 
	 * @param colorCode
	 *            either Model.COLOR_CODE_EMPTY, Model.COLOR_CODE_WHITE, or
	 *            Model.COLOR_CODE_BLACK
	 * @return black for COLOR_CODE_BLACK, white for COLOR_CODE_WHITE, and null
	 *         for COLOR_CODE_EMPTY
	 */
	public static Color getColor(int colorCode) {
		switch (colorCode) {
		case COLOR_CODE_BLACK:
			return Color.black;
		case COLOR_CODE_WHITE:
			return Color.white;
		default:
			return null;
		}
	}

	/**
	 * Resets the board to it's initial configuration, and sets
	 * Model.nextMoveColor to be white
	 */
	public static void startNewGame() {
		// set the first move to be white
		nextMoveColor = COLOR_CODE_WHITE;

		// clear the grid
		for (int gridX = 0; gridX < Parameters.gridSize; gridX++)
			for (int gridY = 0; gridY < Parameters.gridSize; gridY++)
				board[gridX][gridY] = COLOR_CODE_EMPTY;

		// place the initial pieces on the board
		int gridOffset = Parameters.gridSize / 2 - 1;
		for (int x = 0; x <= 1; x++) {
			nextMoveColor = swapColorCode(nextMoveColor);
			for (int y = 1; y >= 0; y--)
				makeMove(x + gridOffset, y + gridOffset);
		}
	}
}
