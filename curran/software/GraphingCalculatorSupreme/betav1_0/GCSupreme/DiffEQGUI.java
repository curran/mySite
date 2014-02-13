package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DiffEQGUI extends JPanel implements ActionListener,host
{
	int width;
	int height;
	
	private JButton graphButton,parcicularSolutionButton;
	private JPanel appletPanel, tools;

	private DiffEQPanel DiffEQGraph;
	private window2D FunctionSpace;
	
	private JLabel YEqualsLabel;
	private JTextField function_field;

	//set up GUI
	public DiffEQGUI(int w,int h)
	{
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		YEqualsLabel = new JLabel("dy/dx = ");
		function_field = new JTextField(10);
		
		graphButton = new JButton("Graph");
		graphButton.setMargin(new Insets(0,0,0,0));
		graphButton.addActionListener(this);
		
		parcicularSolutionButton = new JButton("Draw Particular Solution");
		parcicularSolutionButton.setMargin(new Insets(0,0,0,0));
		parcicularSolutionButton.addActionListener(this);

		tools.add(YEqualsLabel);
		tools.add(function_field);
		tools.add(graphButton);
		tools.add(parcicularSolutionButton);
		
		FunctionSpace = new window2D(width,height);
		DiffEQGraph = new DiffEQPanel(FunctionSpace);
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(DiffEQGraph);
		
		
		add(appletPanel);
		setSize(width,height);
		
		function_field.setText("sinx+y");
	}
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == graphButton)
		{
			DiffEQGraph.setFunction(new Function(function_field.getText()));
		}
		else if(event.getSource() == parcicularSolutionButton)
		{
			try
			{
				String[] coords = (JOptionPane.showInputDialog("Solution through point(x,y):")).split(",");
				double x = Double.parseDouble(coords[0]);
				double y = Double.parseDouble(coords[1]);
				DiffEQGraph.drawParticularSolution(x,y);
			}
			catch(Exception e){}
		}
	}
	
	public void acceptVisitor(Visitor V)
    {
    	DiffEQGraph.acceptVisitor(V);
    }
}