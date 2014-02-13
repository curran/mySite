package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FunctionGUI extends JPanel implements ActionListener,host
{
	JButton graphButton,drawDerivButton,drawIntegralButton,riemannSumButton;
	JPanel appletPanel, tools;
	FunctionGraphPanel FunctionGraph;
	window2D FunctionSpace;
	JLabel YEqualsLabel;
	JTextField function_field;
	int width;
	int height;
	public FunctionGUI(int w,int h)
	{
		setLayout(new BorderLayout());
		//setLayout(new FlowLayout(FlowLayout.CENTER, 30, 3));
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));

		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
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

		tools.add(YEqualsLabel);
		tools.add(function_field);
		tools.add(graphButton);
		tools.add(drawDerivButton);
		tools.add(drawIntegralButton);
		tools.add(riemannSumButton);
		
		FunctionSpace = new window2D(width,height);
		FunctionGraph = new FunctionGraphPanel(FunctionSpace);
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(FunctionGraph);
		
		add(appletPanel);
		setSize(width,height);
		
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