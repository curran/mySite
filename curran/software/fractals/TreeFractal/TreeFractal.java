import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

public class TreeFractal extends JApplet implements ActionListener
{
	private final int width = 600;
	private final int height = 600;
	
	private JButton increase,reset;
	
	private JPanel appletPanel, tools;

	private FractalPanel drawing;

	private JLabel angleLabel;
	private JTextField angle_field;
	private JLabel sizeratioLabel;
	private JTextField sizeratio_field;
	private JLabel lengthLabel;
	private JTextField length_field;
	
	//set up GUI
	public void init()
	{
		tools = new JPanel();
		tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		

		angleLabel = new JLabel("angle = ");
		angle_field = new JTextField(3);
		
		sizeratioLabel = new JLabel("length ratio = ");
		sizeratio_field = new JTextField(3);
		
		lengthLabel = new JLabel("initial length = ");
		length_field = new JTextField(3);
		
		increase = new JButton("Iterate");
		increase.setMargin(new Insets(0,0,0,0));
		increase.addActionListener(this);
		
		
		reset = new JButton("reset");
		reset.setMargin(new Insets(0,0,0,0));
		reset.addActionListener(this);

		tools.add(increase);
		tools.add(angleLabel);
		tools.add(angle_field);
		tools.add(sizeratioLabel);
		tools.add(sizeratio_field);
		tools.add(lengthLabel);
		tools.add(length_field);
		tools.add(reset);


		drawing = new FractalPanel();
		
		appletPanel = new JPanel();
		appletPanel.add(tools);
		appletPanel.add(drawing);
		
		getContentPane().add(appletPanel);
		setSize(width,height);
		
		angle_field.setText("45");
		sizeratio_field.setText(".58");
		length_field.setText("220");
		reset.doClick();
		
	}
		

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == increase)
		{
			int order = drawing.getOrder();
			order++;
			
			drawing.setOrder(order);
			repaint();
		}
		if(event.getSource() == reset)
		{
			drawing.setParams(Double.parseDouble(angle_field.getText()),
							Double.parseDouble(sizeratio_field.getText()),
							Double.parseDouble(length_field.getText()));
		}
	}
		
}

class FractalPanel extends JPanel
{
	private int order = 1;
	int height = 600, width = 600;
	double angle = 2;
	static double sizeRatio = .58;
	double initBottom = 100;
	double initHeight = 220;
	static double importantAngle = Math.toRadians(45);
	
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
	

	
	public FractalPanel ()
	{
		setBackground(Color.white);
		setPreferredSize(new Dimension(width,height));
	}
	
	public void setParams(double ang,double rat,double H)
	{
		importantAngle = Math.toRadians(ang);
		sizeRatio=rat;
		initHeight=H;
		order = 2;
		repaint();
	}
		
	public void setOrder(int o)
	{
		order = o;
	}
	
	public int getOrder()
	{
		return order;
	}

	public static void drawLines(Graphics2D g, int ord, double X,double Y,double X1,double Y1)
	{
		
		if(ord>0)
		{		
			g.drawLine((int)X,(int)Y,(int)X1,(int)Y1);
			double distance = Math.sqrt(Math.pow(Math.abs(X-X1),2)+Math.pow(Math.abs(Y-Y1),2));
			distance = sizeRatio*distance;
			double importantAngl = importantAngle+ord*0.2;
			
			double theta = Math.atan2((X1-X),(Y-Y1));
			
			
			
			double theta1a = -theta-importantAngle;
			double a1 = distance*Math.sin(theta1a);
			double b1 = distance*Math.cos(theta1a);
			drawLines(g,ord-1,X1,Y1,X1-a1,Y1-b1);
			
			double theta1b = -theta+importantAngle;
			double a2 = distance*Math.sin(theta1b);
			double b2 = distance*Math.cos(theta1b);
			drawLines(g,ord-1,X1,Y1,X1-a2,Y1-b2);
			
	
	
		
		}
	}
	
	

	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		Graphics2D bufGraphics = (Graphics2D) page;
        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
        
		drawLines(bufGraphics, order,width/2,height-initBottom,width/2,height-(initHeight+initBottom));
		
	}


}