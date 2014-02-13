import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * The view part (in the model/view/controller paradim) of the Reversi
 * application. It displays the board, the state of the game described in the
 * model (the positions of the pieces), and draws the cursor as a piece which
 * the user can "drop" onto the board.
 * 
 * @author curran
 * 
 */
@SuppressWarnings("serial")
public class View extends JPanel {

	/**
	 * The back buffer used for drawing the board
	 */
	BufferedImage buffer = new BufferedImage(Parameters.boardSize,
			Parameters.boardSize, BufferedImage.TYPE_INT_ARGB);

	/**
	 * Initializes the view size, cursor, and mouse listeners.
	 */
	public View() {
		// set the preferred size of the panel (so the overall frame will be
		// sized correctly when frame.pack() is called in the main GUI)
		setPreferredSize(new Dimension(Parameters.boardSize,
				Parameters.boardSize));

		// set the cursor to be nothing
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
				new Point(), ""));

		// add the listeners which updates the position of the cursor (which is
		// drawn in the paint() method) when necessary
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent arg0) {
				repaint();
			}
		});

		// to get rid of the painted cursor when the mouse leaves the panel
		addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent arg0) {
				repaint();
			}
		});
	}

	/**
	 * Paints the buffered image of the board, and paints the cursor on top of
	 * it.
	 */
	public void paint(Graphics g) {
		// draws the board
		g.drawImage(buffer, 0, 0, this);

		// draw the cursor as a piece
		Point mousePosition = getMousePosition();
		if (mousePosition != null) {
			// turn on anti-aliasing
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			drawPiece(g, Model.nextMoveColor, mousePosition.x, mousePosition.y);
		}

	}

	/**
	 * Draws a reversi piece
	 * 
	 * @param g
	 *            the Graphics to draw the piece on
	 * @param colorCode
	 *            the color of the piece
	 * @param centerPixelX
	 *            the x coordinate of the center if the piece
	 * @param centerPixelY
	 *            the y coordinate of the center if the piece
	 */
	private void drawPiece(Graphics g, int colorCode, int centerPixelX,
			int centerPixelY) {
		Color color = Model.getColor(colorCode);
		if (color != null) {
			// draw the outline of the opposite color
			g.setColor(Model.getColor(Model.swapColorCode(colorCode)));
			g.fillOval(centerPixelX - Parameters.pieceSize / 2, centerPixelY
					- Parameters.pieceSize / 2, Parameters.pieceSize,
					Parameters.pieceSize);

			// draw the top of the piece
			g.setColor(color);
			g.fillOval(centerPixelX - Parameters.pieceSize / 2 + 1,
					centerPixelY - Parameters.pieceSize / 2 + 1,
					Parameters.pieceSize - 2, Parameters.pieceSize - 2);
		}
	}

	/**
	 * Re-draws the board based on the current state of the model
	 */
	public void drawBoard() {
		Graphics g = buffer.getGraphics();
		// turn on anti-aliasing
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// fill in the background with the background color
		g.setColor(Parameters.backgroundColor);
		g.fillRect(0, 0, Parameters.boardSize, Parameters.boardSize);

		// draw the grid of lines
		g.setColor(Parameters.linesColor);
		for (double gridX = 0; gridX < Parameters.gridSize + 1; gridX++) {
			int pixelX = (int) ((gridX / (Parameters.gridSize)) * (Parameters.boardSize - 1));
			for (double gridY = 0; gridY < Parameters.gridSize + 1; gridY++) {
				int pixelY = (int) ((gridY / (Parameters.gridSize)) * (Parameters.boardSize - 1));
				// draw the line from top to bottom
				g.drawLine(pixelX, 0, pixelX, Parameters.boardSize);
				// draw the line from left to right
				g.drawLine(0, pixelY, Parameters.boardSize, pixelY);
			}
		}

		// draw the pieces on the board
		int piecesOffset = (int) (0.5 / Parameters.gridSize * Parameters.boardSize);
		for (int gridX = 0; gridX < Parameters.gridSize; gridX++) {
			int centerPixelX = (int) (((double) gridX / (Parameters.gridSize)) * (Parameters.boardSize - 1))
					+ piecesOffset;
			for (int gridY = 0; gridY < Parameters.gridSize; gridY++) {
				int centerPixelY = (int) (((double) gridY / (Parameters.gridSize)) * (Parameters.boardSize - 1))
						+ piecesOffset;
				drawPiece(g, Model.board[gridX][gridY], centerPixelX,
						centerPixelY);
			}
		}
		repaint();
	}
}
