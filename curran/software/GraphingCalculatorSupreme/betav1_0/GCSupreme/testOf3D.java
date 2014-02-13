package GraphingCalculatorSupreme;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class testOf3D extends JApplet
{
	static final int width = 600;
	static final int height = 600;
	private static void createAndShowGUI()
	{
        JFrame frame = new JFrame("Graphing Calculator Supreme");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Test3DGUI CGUI = new Test3DGUI(width,height);
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

class Test3DGUI extends JPanel implements ActionListener
{
	int width;
	int height;

	private JButton render,set;
	private JPanel appletPanel, tools;
	test3DPanel test3Dpanel;
	private JLabel ZEqualsLabel;
	private JTextField function_field;
	Color GraphColor  = Color.red;
	

	public Test3DGUI(int w,int h)
	{
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		ZEqualsLabel = new JLabel("Z = ");
		function_field = new JTextField(10);
		
		set = new JButton("Render");
		set.setMargin(new Insets(0,0,0,0));
		set.addActionListener(this);
		render = new JButton("render");
		render.setMargin(new Insets(0,0,0,0));
		render.addActionListener(this);
		
		test3Dpanel = new test3DPanel(new universe3D(window3D.getDefaultWindow3D(width,height)));
		
		//tools.add(ZEqualsLabel);
		//tools.add(function_field);
		tools.add(set);
		//tools.add(cartesian3DGraph.getWireframeCheckbox());
		//tools.add(render);
		
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(test3Dpanel);
		
		add(appletPanel);
		setSize(width,height);
		
			function_field.setText("-70/(sqrt(x^2+y^2)^2+2)");
			set.doClick();
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == set)
		{
			test3Dpanel.stopSpin();
			//System.out.println("no");
			test3Dpanel.doRayTrace();
			//test3Dpanel.setFunction(new Function(function_field.getText()), randomColor());
			test3Dpanel.stopSpin();
		}
	}
	public Color randomColor()
	{
		return Color.getHSBColor((float)Math.random(),1,1);
	}
}
class test3DPanel extends generic3DPanel
{
	Function F = new Function("x");
	Color Fcolor = Color.green;
	Color AxesColor = new Color(150,150,150);
	int res=20;
	int RenderRes = 40;

    public test3DPanel(universe3D U)
	{
		super(U);
	}
	public void setFunction(Function Func, Color C)
	{
		F = Func;
		Fcolor = C;
		currentUniverse.clearObjects();
		currentUniverse.addObject(new cartesianFunctionObject3D(F,res,currentUniverse.screen,Fcolor));
		
		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
		
		currentChangeinTheta = 0.004;
		currentChangeinPhi = 0.0;	
		globalPhi = Math.PI/2.0-.3;
		globalTheta = 0;
	}
	public void doRayTrace()
	{
		currentChangeinTheta = 0;
		currentChangeinPhi = 0;	
		
		currentUniverse.clearObjects();
		currentUniverse.setSwitches(universe3D.SWITCHES_RAYTRACE);
		
		
		
		//currentUniverse.addObject(new Sphere3D(new point3D(0,0,0),5.0));
		currentUniverse.addObject((new Objects3D()).getPlane3D());
		
		//displayUniverse(); //inherited from generic3DPanel
		
	}
}