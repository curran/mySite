package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ParametricGUI extends JPanel implements ActionListener,host
{
	private JButton graphButton;//,drawDerivButton,drawIntegralButton,riemannSumButton;
	private JPanel appletPanel, tools;
	private ParametricGraphPanel ParametricGraph;
	private window2D FunctionSpace;
	private JLabel YEqualsLabel;
	private JTextField Yfunction_field;
	
	private JLabel XEqualsLabel;
	private JTextField Xfunction_field;
	
	private JLabel TMaxEqualsLabel;
	private JTextField TMax_field;
	
	private JLabel TStepEqualsLabel;
	private JTextField TStep_field;



	int width;
	int height;
	
	public ParametricGUI(int w,int h)
	{
		setLayout(new BorderLayout());
		//setLayout(new FlowLayout(FlowLayout.CENTER, 30, 3));
		width = w;
		height = h;
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));

		tools.setBackground(Color.white);
		tools.setOpaque(true);
		
		YEqualsLabel = new JLabel("Y(t) = ");
		Yfunction_field = new JTextField(10);
		
		XEqualsLabel = new JLabel("X(t) = ");
		Xfunction_field = new JTextField(10);
		
		TMaxEqualsLabel = new JLabel("   t max = ");
		TMax_field = new JTextField(3);
		
		TStepEqualsLabel = new JLabel("   t step = ");
		TStep_field = new JTextField(3);
		
		graphButton = new JButton("Graph");
		graphButton.setMargin(new Insets(0,0,0,0));
		graphButton.addActionListener(this);

		tools.add(YEqualsLabel);
		tools.add(Yfunction_field);
		tools.add(XEqualsLabel);
		tools.add(Xfunction_field);
		tools.add(graphButton);
		tools.add(TMaxEqualsLabel);
		tools.add(TMax_field);
		tools.add(TStepEqualsLabel);
		tools.add(TStep_field);

		FunctionSpace = new window2D(width,height);
		ParametricGraph = new ParametricGraphPanel(FunctionSpace);
		
		appletPanel = new JPanel();
		appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
		appletPanel.add(tools);
		appletPanel.add(ParametricGraph);
		
		add(appletPanel);
		setSize(width,height);
		
		Yfunction_field.setText("9*sin(t*8)");
		Xfunction_field.setText("9*cos(t*7)*sin(3t)");
		TMax_field.setText("pi");
		TStep_field.setText("0.007");
		
	}//constructor
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == graphButton)
		{
			//ParametricGraph.setFunction(new Function(function_field.getText()));
			String XfStr = ExpressionStringOperator.replaceTwithX(Xfunction_field.getText());
			String YfStr = ExpressionStringOperator.replaceTwithX(Yfunction_field.getText());
			
			Function XFunc = new Function(XfStr);
			Function YFunc = new Function(YfStr);
			
			try
			{
				ParametricGraph.Toolbox.Tend =  new Function(TMax_field.getText()).Evaluate();
				ParametricGraph.Toolbox.Tstep = new Function(TStep_field.getText()).Evaluate();
			}
			catch(Exception e){}
			
			ParametricGraph.setEquation (XFunc,YFunc);
		}
	}//actionPerformed
	public void acceptVisitor(Visitor V)
    {
    	ParametricGraph.acceptVisitor(V);
    }
}