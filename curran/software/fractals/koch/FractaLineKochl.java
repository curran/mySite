import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

public class FractaLineKochl extends JApplet implements ActionListener
{
	boolean flipKoch = true;
	
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
		
		angle_field.setText("150");
		sizeratio_field.setText(".578");
		length_field.setText("600");
		reset.doClick();
		drawing.setFlip(flipKoch);
		
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
			flipKoch = !flipKoch;
			drawing.setFlip(flipKoch);
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
	double initBottom = 0;
	double initHeight = 600;
	static double importantAngle = Math.toRadians(150);
	boolean flipKoch = true;
	
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
	

	
	public FractalPanel ()
	{
		setBackground(Color.white);
		setPreferredSize(new Dimension(width,height));
	}
	public void setFlip(boolean f) {flipKoch = f;}
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
	
	public static void TraverseKoch(Graphics2D g, double x1,double y1,double x2,double y2,int ord)
    {
        if (ord==0)
        {
            g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
        }
        else
        {
        	//ord--;
            double dx=(x2-x1), dy=(y2-y1);
            double cx= x1 + dx/2.0 - dy/3.4;
            double cy= y1 + dy/2.0 + dx/3.4;
            int X = 0,Y = 1;
            double [][] newpoints = new double[2][5];
            
            newpoints[X][0] = x1;
            newpoints[Y][0] = y1;
            
            newpoints[X][1] = x1+dx/3.0;
            newpoints[Y][1] = y1+dy/3.0;

            double n = 0.8660254/3.0;
            
            newpoints[X][2] = x1 + dx/2.0 - dy*n;
            newpoints[Y][2] = y1 + dy/2.0 + dx*n;
            
            newpoints[X][3] = x1+dx*2.0/3.0;
            newpoints[Y][3] = y1+dy*2.0/3.0;
            
            newpoints[X][4] = x2;
            newpoints[Y][4] = y2;
            for(int i = 0;i<4;i++)
            {
            	TraverseKoch(g, newpoints[X][i], newpoints[Y][i],newpoints[X][i+1],newpoints[Y][i+1], ord-1);
            }
            /*TraverseKoch(g, x1, y1, x1+dx/3.0, y1+dy/3.0, ord-1);
            TraverseKoch(g, x1+dx/3.0, y1+dy/3.0, cx, cy, ord-1);
            TraverseKoch(g, cx, cy, x1+dx*2.0/3.0, y1+dy*2.0/3.0, ord-1);
            TraverseKoch(g, x1+dx*2.0/3.0, y1+dy*2.0/3.0,x2,y2,ord-1);*/
        }
    }
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		Graphics2D bufGraphics = (Graphics2D) page;
        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
        
		drawLines(bufGraphics, order,width/2,height-initBottom,width/2,height-(initHeight+initBottom));
		int distIn = 40;
		int distUp = 600-450;
		bufGraphics.setColor(Color.green);
		
		int X1 = 600-distIn;
		int	X2 = distIn;
		
		if(flipKoch)
		{
			int temp = X2;
			X2 = X1;
			X1 = temp;
		}
		
		//TraverseKoch(bufGraphics,distIn,600-distUp,600-distIn,600-distUp,(order-2));
		if(order<8)
			TraverseKoch(bufGraphics,X1,distUp,X2,distUp,(order-2));
		else
			TraverseKoch(bufGraphics,X1,distUp,X2,distUp,(8-2));
		
	}


}