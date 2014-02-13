package GraphingCalculatorSupreme;
import java.awt.*;
import javax.swing.*;
public class Cartesian3DWindow extends JApplet
{
	static final int width = 600;
	static final int height = 600;
	private static void createAndShowGUI()
	{
        JFrame frame = new JFrame("Graphing Calculator Supreme");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Cartesian3DGUI CGUI = new Cartesian3DGUI(width,height);
		JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        contentPane.add(CGUI);
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