package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cartesian3DGUI extends JPanel implements ActionListener,host
{
	int width;
	int height;

	private JButton render,set;
	private JPanel appletPanel, tools;
	Cartesian3DPanel cartesian3DGraph;
	private JLabel ZEqualsLabel;
	private JTextField function_field;
	Color GraphColor  = Color.red;
	

	public Cartesian3DGUI(int w,int h)
	{
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		ZEqualsLabel = new JLabel("Z = ");
		function_field = new JTextField(10);
		
		set = new JButton("Graph");
		set.setMargin(new Insets(0,0,0,0));
		set.addActionListener(this);
		render = new JButton("render");
		render.setMargin(new Insets(0,0,0,0));
		render.addActionListener(this);
		
		cartesian3DGraph = new Cartesian3DPanel(new universe3D(window3D.getDefaultWindow3D(width,height)));
		
		tools.add(ZEqualsLabel);
		tools.add(function_field);
		tools.add(set);
		tools.add(cartesian3DGraph.getWireframeCheckbox());
		//tools.add(render);
		
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(cartesian3DGraph);
		
		add(appletPanel);
		setSize(width,height);
		
			function_field.setText("-70/(sqrt(x^2+y^2)^2+2)");
			set.doClick();
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == render)
		{
			cartesian3DGraph.renderFunction();
		}
		else if(event.getSource() == set)
		{
			cartesian3DGraph.setFunction(new Function(function_field.getText()), randomColor());
		}
	}
	public Color randomColor()
	{
		return Color.getHSBColor((float)Math.random(),1,1);
	}
	public void acceptVisitor(Visitor V)
    {
    	cartesian3DGraph.acceptVisitor(V);
    }
}