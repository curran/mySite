/*
TO DO WITH THIS:
draw and label 3D axes
password protect it
come up with password algorithm programming
add file menus
allow change of windows using popup controls
look into paypal process/situation
figure out automatic emailing of passwords triggered by a purchase
research software laws
*/
package GraphingCalculatorSupreme;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;//for actionListener
public class GraphingCalculatorSupreme extends JApplet
{
	static int heightMargin =100;
	
	static Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
	static int width  = (int)scr.getWidth();
	static int height = (int)scr.getHeight()-heightMargin;
	
	static CalculatorTabs CALCULATOR = new CalculatorTabs(width,height);
	static GraphingCalculatorSupreme GCSupreme = new GraphingCalculatorSupreme();
	
	public static void main(String[] args)//for application functionality
    {
        GCSupreme.showLicenseAgreement();
    }
	public void init()//for applet functionality
	{
		GCSupreme.showLicenseAgreement();
	}
	private void createAndShowGUI()
	{
		try
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch( Exception e )
		{}
		
	    JFrame frame = new JFrame("Graphing Calculator Supreme");
	    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    frame.setJMenuBar(createMenuBar());
	    
			JPanel contentPane = new JPanel(new BorderLayout());
	    contentPane.setOpaque(true);
	    contentPane.add(CALCULATOR);
	    frame.setContentPane(contentPane);
	
	    frame.setSize(width, height+heightMargin);
	    frame.setVisible(true);
	    frame.toFront();
	    
	    frame.addComponentListener
	    ( 
			new ComponentAdapter()
			{
				public void componentResized( ComponentEvent e )
				{
					Dimension D = e.getComponent().getSize();
					int w = settings.getMediatedSize((int)D.getWidth());
					int h = settings.getMediatedSize((int)D.getHeight()-heightMargin);
					ChangeWindowVisitor changeWindow = new ChangeWindowVisitor(w,h);
					CALCULATOR.acceptVisitor(changeWindow);
				}
			}
		);

		sendDefaultWindowVisitor();
		sendSquareWindowVisitor();//to square off the 3D views
    }
    public void showLicenseAgreement()
	{
    	JFrame licenseFrame = new JFrame("GRAPHING CALCULATOR SUPREME License Argeement");
    	JTextArea licenseText = new JTextArea();
    	licenseText.setEditable(false);
    	licenseText.setLineWrap(true);
    	licenseText.setWrapStyleWord(true);
    	String newline = "\n";
    	licenseText.setText
    	(
    		"GRAPHING CALCULATOR SUPREME BETA V1.0\n"+
			"A graphing calculator and math visualization program for entry level calculus."+newline+newline+
			"Graphing Calculator Supreme BETA version 1.0, Copyright (C) 2005 Curran Kelleher"+newline+
		    "Graphing Calculator Supreme comes with ABSOLUTELY NO WARRANTY. This is free"+newline+
		    "software, and you are welcome to redistribute it under certain conditions;"+newline+
		    "see the file license.txt included with this program for details."
    	);
    	
    	
    	licenseFrame.getContentPane().add(licenseText, BorderLayout.CENTER);
    	
    	JButton IAgree = new JButton("I AGREE");
		IAgree.setMargin(new Insets(0,0,0,0));
		IAgree.addActionListener(new IAgreeButtonListener(licenseFrame));
			
    	licenseFrame.getContentPane().add(licenseText, BorderLayout.CENTER);
    	licenseFrame.getContentPane().add(IAgree, BorderLayout.SOUTH);
    	
    	
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenCenterX = (int)screenSize.getWidth()/2;
    	int screenCenterY = (int)screenSize.getHeight()/2;
    	int frameSizeX = 500;
    	int frameSizeY = 200;
    	
	    licenseFrame.setBounds(screenCenterX-frameSizeX/2,screenCenterY-frameSizeY/2,frameSizeX,frameSizeY);
	    licenseFrame.setVisible(true);
	}
	private class IAgreeButtonListener implements ActionListener
	{
		JFrame licenseFrame;
		public IAgreeButtonListener(JFrame lf)
		{
			this.licenseFrame = lf;
		}
		public void actionPerformed(ActionEvent e)
		{
			licenseFrame.dispose();
			javax.swing.SwingUtilities.invokeLater
	        (
	        	new Runnable()
		        {
		            public void run()
		            {
		                GCSupreme.createAndShowGUI();
		            }
		        }
		    );
		}
	}

	
	
    private JMenuBar createMenuBar()
    {
		JMenuBar menuBar = new JMenuBar();
//		menuBar.add( createFileMenu() );
//		menuBar.add( createEditMenu() );
		menuBar.add( createWindowMenu() );
		return ( menuBar );
	}
	
	private JMenu createWindowMenu()
	{
		JMenu menu = new JMenu("Window");
		menu.setMnemonic('W');

		JMenuItem menuItem = new JMenuItem("Default Window");
		menuItem.setMnemonic('D');
		menuItem.setEnabled(true);
		menuItem.addActionListener(new WindowMenu_DefaultWindowActionListener());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Square Window");
		menuItem.setMnemonic('D');
		menuItem.setEnabled(true);
		menuItem.addActionListener(new WindowMenu_SquareWindowActionListener());
		menu.add(menuItem);

		return (menu);
	}
	//private JMenu createMenu(

	private final class WindowMenu_DefaultWindowActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent a )
		{
			sendDefaultWindowVisitor();
		}
	}
	
	private final class WindowMenu_SquareWindowActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent a )
		{
			sendSquareWindowVisitor();
		}
	}
	
	public void sendDefaultWindowVisitor()
	{
		double[] d = settings.getDefaultWindowDimArray();
		ChangeWindowVisitor changeWindow = new ChangeWindowVisitor(d[0],d[1],d[2],d[3]);
		CALCULATOR.acceptVisitor(changeWindow);
	}
	public void sendSquareWindowVisitor()
	{
		MakeWindowSquareVisitor squareWindow = new MakeWindowSquareVisitor();
		CALCULATOR.acceptVisitor(squareWindow);
	}
}