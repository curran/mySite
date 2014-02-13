import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The controller part (in the model/view/controller paradim) of the Reversi
 * application. It is the bottleneck for all user actions which change the
 * model.
 * 
 * @author curran
 * 
 */
public class Controller {
	/**
	 * Upon construction, this Controller adds the mouse listener, which makes
	 * moves when the used clicks on the board, to the view (which is publicly
	 * globally accessible in the Model)
	 */
	public Controller() {
		Model.view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Model.placePieceOnBoard(e.getX(), e.getY());
			}
		});
	}

	/**
	 * Creates the menu bar for the GUI Frame. The menu bar contains a "Game"
	 * menu, which contains a "New Game" menu item, which resets the game when
	 * clicked.
	 * 
	 * @return the menu bar for the reversi GUI frame
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Game");
		JMenuItem newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Model.startNewGame();
			}
		});
		fileMenu.add(newGameMenuItem);
		menuBar.add(fileMenu);
		return menuBar;
	}
}
