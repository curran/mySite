package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Parametric3DGUI extends JPanel implements ActionListener,host
{
	int width;
	int height;

	private JButton render,set;
	private JPanel appletPanel, tools;
	Parametric3DPanel parametric3DGraph;
	private JLabel XEqualsLabel,YEqualsLabel,ZEqualsLabel;
	private JTextField Xfunction_field,Yfunction_field,Zfunction_field;
	Color GraphColor  = Color.red;
	

	public Parametric3DGUI(int w,int h)
	{
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		XEqualsLabel = new JLabel("X = ");
		Xfunction_field = new JTextField(10);
		
		YEqualsLabel = new JLabel("Y = ");
		Yfunction_field = new JTextField(10);
		
		ZEqualsLabel = new JLabel("Z = ");
		Zfunction_field = new JTextField(10);
		
		set = new JButton("Graph");
		set.setMargin(new Insets(0,0,0,0));
		set.addActionListener(this);
		render = new JButton("render");
		render.setMargin(new Insets(0,0,0,0));
		render.addActionListener(this);

		tools.add(XEqualsLabel);
		tools.add(Xfunction_field);
		
		tools.add(YEqualsLabel);
		tools.add(Yfunction_field);
		
		tools.add(ZEqualsLabel);
		tools.add(Zfunction_field);

		tools.add(set);
		//tools.add(render);
		
		parametric3DGraph = new Parametric3DPanel(new universe3D(window3D.getDefaultWindow3D(width,height)));
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(parametric3DGraph);
		
		add(appletPanel);
		setSize(width,height);
		
			Xfunction_field.setText("10*sin(t*3)");
			Yfunction_field.setText("10*cos(t)");
			Zfunction_field.setText("10*sin(t*2)");
			
			set.doClick();
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == set)
		{
			Function Xf = new Function(ExpressionStringOperator.replaceTwithX(Xfunction_field.getText()));
			Function Yf = new Function(ExpressionStringOperator.replaceTwithX(Yfunction_field.getText()));
			Function Zf = new Function(ExpressionStringOperator.replaceTwithX(Zfunction_field.getText()));
			
			parametric3DGraph.set3DEquation(Xf,Yf,Zf,randomColor());
		}
	}
	public Color randomColor()
	{
		return Color.getHSBColor((float)Math.random(),1,1);
	}
	public void acceptVisitor(Visitor V)
    {
    	parametric3DGraph.acceptVisitor(V);
    }
}