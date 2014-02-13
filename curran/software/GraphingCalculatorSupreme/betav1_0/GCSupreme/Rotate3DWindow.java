package GraphingCalculatorSupreme;
import java.awt.*;
import javax.swing.*;
public class Rotate3DWindow extends JApplet
{
	private static void createAndShowGUI()
	{
        JFrame frame = new JFrame("Graphing Calculator Supreme");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int width = 600;
		final int height = 600;
		Rotate3DGUI RGUI = new Rotate3DGUI(width,height);
		JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        contentPane.add(RGUI);
        frame.setContentPane(contentPane);

        frame.setSize(width, height);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater
        (
        	new Runnable()
	        {
	            public void run()
	            {
	                createAndShowGUI();
	            }
	        }
	    );
    }
}