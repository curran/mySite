
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * The top-level GUI and main class for the reversi application
 * 
 * @author curran
 * 
 */
@SuppressWarnings("serial")
public class ReversiGUI extends JApplet {

	public static void launchGUI() {
		// set the default system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// create the GUI frame
		JFrame frame = new JFrame();

		// put the reversi GUI in the frame
		frame.setContentPane(Model.view);

		// set the menu bar
		frame.setJMenuBar(Model.controller.createMenuBar());

		// make the frame non-resizable
		frame.setResizable(false);

		// resize the frame to the preferred size of the board
		frame.pack();

		// center the frame on the screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screen.width / 2 - frame.getWidth() / 2,
				screen.height / 2 - frame.getHeight() / 2);

		// set the frame to exit the application when closed
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// set up the initial board configuration
		Model.startNewGame();

		// show the frame
		frame.setVisible(true);
	}

	/**
	 * This method is invoked when Reversi is run as a Java Applet.
	 */
	public void init() {
		JButton button = new JButton("Launch Reversi!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				launchGUI();
			}
		});
		add(button);
	}

	/**
	 * creates the reversi GUI and displays it in a frame
	 * 
	 * @param args
	 *            command line arguments not used
	 */
	public static void main(String[] args) {
		launchGUI();
	}
}
