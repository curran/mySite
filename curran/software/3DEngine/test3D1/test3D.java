import java.awt.*;
import java.awt.event.*;//for MouseListener etc
import javax.swing.*;//frame
import javax.swing.JPanel;
public class test3D extends JApplet
{
	static final int width = 600;
	static final int height = 600;
	static universe3D universe = new universe3D(window2D.getDefaultWindow(width,height));
	public static void displayGUI()
	{
		javax.swing.SwingUtilities.invokeLater
        (
        	new Runnable()
	        {
	            public void run()
	            {
	            	test3DPanel T = new test3DPanel(universe,width,height);
	            	
	            	JFrame frame = new JFrame("3D");
					JPanel contentPane = new JPanel(new BorderLayout());
					contentPane.setOpaque(true);
					contentPane.add(T);
					frame.setContentPane(contentPane);
					frame.setSize(width, height);
					frame.setVisible(true);
	            }
	        }
	    );
	}
    public static void main(String[] args)//for application functionality
    {
        displayGUI();
	}
	public void init()//for applet functionality
    {
        displayGUI();
	}
}
