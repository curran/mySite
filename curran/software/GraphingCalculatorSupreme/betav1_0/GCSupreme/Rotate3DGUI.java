package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rotate3DGUI extends JPanel implements ActionListener,host
{
	int width;
	int height;

	private JButton render,Graph,Rotate;
	private JPanel appletPanel, tools;
	Rotate3DPanel rotatedGraph;
	private JLabel YEqualsLabel;
	private JTextField function_field;
	private JTextField DomainMin_field;
	private JTextField DomainMax_field;
	private JLabel axesLabel;
	private JTextField axes_field;
	private JLabel equalsLabel;
	private JTextField equals_field;
	Color GraphColor  = Color.red;

	public Rotate3DGUI(int w,int h)
	{
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		YEqualsLabel = new JLabel("Y = ");
		function_field = new JTextField(10);
		
		DomainMin_field = new JTextField(2);
		DomainMax_field = new JTextField(2);
		
		axesLabel = new JLabel("Rotate about ");
		axes_field = new JTextField(1);
		equalsLabel = new JLabel("=");
		equals_field = new JTextField(3);

		
		render = new JButton("Render");
		render.setMargin(new Insets(0,0,0,0));
		render.addActionListener(this);
		
		
		Graph = new JButton("Graph");
		Graph.setMargin(new Insets(0,0,0,0));
		Graph.addActionListener(this);

		Rotate = new JButton("Rotate");
		Rotate.setMargin(new Insets(0,0,0,0));
		Rotate.addActionListener(this);
		
		rotatedGraph = new Rotate3DPanel(new universe3D(window3D.getDefaultWindow3D(width,height)));
		
		tools.add(YEqualsLabel);
		tools.add(function_field);
		tools.add(new JLabel(" on ["));
		tools.add(DomainMin_field);
		tools.add(new JLabel(","));
		tools.add(DomainMax_field);
		tools.add(new JLabel("] "));
		
		tools.add(axesLabel);
		tools.add(axes_field);
		tools.add(equalsLabel);
		tools.add(equals_field);
		tools.add(Graph);
		tools.add(Rotate);
		tools.add(rotatedGraph.getWireframeCheckbox());

		//tools.add(render);
		
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(rotatedGraph);
		
		add(appletPanel);
		setSize(width,height);
		
			function_field.setText("sin(x)");
			DomainMin_field.setText("0");
			DomainMax_field.setText("10");
			axes_field.setText("x");
			equals_field.setText("0");
			Rotate.doClick();
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == render)
		{
			//rotatedGraph.renderFunction();
		}
		else if(event.getSource() == Graph)
		{
			rotatedGraph.set2DFunction(
				new Function(function_field.getText()), 
				randomColor(),
				Double.parseDouble(DomainMin_field.getText()),//double Dm,
				Double.parseDouble(DomainMax_field.getText())//double dM));
				);
		}
		else if(event.getSource() == Rotate)
		{
			rotatedGraph.setFunction(
				new Function(function_field.getText()), 
				randomColor(),
				axes_field.getText().toUpperCase(),//String Ax,
				Double.parseDouble(equals_field.getText()),//double AxisEq,
				Double.parseDouble(DomainMin_field.getText()),//double Dm,
				Double.parseDouble(DomainMax_field.getText())//double dM));
				);
		}
	}
	public Color randomColor()
	{
		return Color.getHSBColor((float)Math.random(),1,1);
	}
	public void acceptVisitor(Visitor V)
    {
    	rotatedGraph.acceptVisitor(V);
    }
}