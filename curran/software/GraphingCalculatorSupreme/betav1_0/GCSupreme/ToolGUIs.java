package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
  * A factory class for creating tool GUIs (graph, 3D visualization, etc.)
  */
public class ToolGUIs
{
	int width;
	int height;
	public ToolGUIs(int w,int h)
	{
		width = w;
		height = h;
	}
	public ToolGUI getFunctionGUI()
	{
		return new FuncGUI(width,height);
	}
	
	private class FuncGUI extends ToolGUI implements ActionListener
	{
		private JButton graphButton,drawDerivButton,drawIntegralButton,riemannSumButton;
		private FunctionGraphPanel FunctionGraph;
		
		private window2D FunctionSpace;
		
		private JLabel YEqualsLabel;
		
		private JTextField function_field;
		public FuncGUI(int w,int h)
		{
			super(w,h);
			
			YEqualsLabel = new JLabel("Y = ");
			function_field = new JTextField(10);
			
			graphButton = new JButton("Graph");
			graphButton.setMargin(new Insets(0,0,0,0));
			graphButton.addActionListener(this);
	
			drawDerivButton = new JButton("Draw Derivitive");
			drawDerivButton.setMargin(new Insets(0,0,0,0));
			drawDerivButton.addActionListener(this);
			
			drawIntegralButton = new JButton("Draw Integral");
			drawIntegralButton.setMargin(new Insets(0,0,0,0));
			drawIntegralButton.addActionListener(this);
			
			riemannSumButton = new JButton("Riemann Sum");
			riemannSumButton.setMargin(new Insets(0,0,0,0));
			riemannSumButton.addActionListener(this);
	
			addGUIElement(YEqualsLabel);
			addGUIElement(function_field);
			addGUIElement(graphButton);
			addGUIElement(drawDerivButton);
			addGUIElement(drawIntegralButton);
			addGUIElement(riemannSumButton);
			
			FunctionGraph = new FunctionGraphPanel(new window2D(width,height));
			
			setVisualPanel(FunctionGraph);
			
			
			
			//String finit = "25/(x^2+5)";
			String finit = "sin(x*(150/(x^2+5)))*(25/(x^2+5))";
			
			function_field.setText(finit);
			
			FunctionGraph.repaint();
			
			//graphButton.doClick();
		}//constructor
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == graphButton)
			{
				FunctionGraph.setFunction(new Function(function_field.getText()));
			}
			else if(event.getSource() == drawDerivButton)
			{
				Function f = new Function(function_field.getText());
				FunctionGraph.setFunction(f);
				FunctionGraph.drawDerivative();
			}
			else if(event.getSource() == riemannSumButton)
			{
				Function f = new Function(function_field.getText());
				FunctionGraph.setFunction(f);
				try
				{
					double a = Double.parseDouble(JOptionPane.showInputDialog("a = "));
					double b = Double.parseDouble(JOptionPane.showInputDialog("b = "));
					int n = Integer.parseInt(JOptionPane.showInputDialog("n = "));
					FunctionGraph.doRiemannSum(a,b,n);
				}
				catch(Exception e){}
			}
			else if(event.getSource() == drawIntegralButton)
			{
				Function f = new Function(function_field.getText());
				FunctionGraph.setFunction(f);
				try
				{
					String[] coords = (JOptionPane.showInputDialog("Solution through point(x,y):")).split(",");
					double x = Double.parseDouble(coords[0]);
					double y = Double.parseDouble(coords[1]);
					FunctionGraph.drawIntegral(x,y);
				}
				catch(Exception e){}
			}
		}//actionPerformed
		public void acceptVisitor(Visitor V)
	    {
	    	FunctionGraph.acceptVisitor(V);
	    }
	}
}
abstract class ToolGUI extends JPanel implements host
{
	/** objects required for all GUIs */
	private JPanel appletPanel, tools;
	
	int width;
	int height;
	
	public ToolGUI(int w,int h)
	{
		setLayout(new BorderLayout());
		//setLayout(new FlowLayout(FlowLayout.CENTER, 30, 3));
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		
		
		add(appletPanel);
		setSize(width,height);
	}
	public void addGUIElement(JComponent E)
	{
		tools.add(E);
	}
	public void setVisualPanel(JComponent E)
	{
		appletPanel.add(E);
	}
	public abstract void acceptVisitor(Visitor V);
}