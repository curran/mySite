package GraphingCalculatorSupreme;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class CalculatorTabs extends JPanel
{
	int width ;
	int height;
	
	JTabbedPane CalculatorPane;

	//FunctionGUI FGUI;// = new FunctionGUI(width,height);
	DiffEQGUI DIFFGUI;// = new DiffEQGUI(width,height);
	MathboxGUI MGUI;// = new MathboxGUI(width,height);
	ParametricGUI PGUI;
	
	Parametric3DGUI P3DGUI;
	Cartesian3DGUI CGUI;// = new Cartesian3DGUI(width,height);
	Rotate3DGUI R3DGUI;// = new Rotate3DGUI(width,height);
	
	private List TabsChildren = new ArrayList();
	
    public CalculatorTabs(int w,int h)
	{
		super(new GridLayout(1,1));

		width = settings.getMediatedSize(w);
		height = settings.getMediatedSize(h);
		
        CalculatorPane = new JTabbedPane();
        CalculatorPane.addChangeListener(new GCTabChangeListener()); 
        ImageIcon icon = null;//createImageIcon("images/middle.gif");

		//FGUI = new FunctionGUI(width,height);
		DIFFGUI = new DiffEQGUI(width,height);
		MGUI = new MathboxGUI(width,height);
		PGUI = new ParametricGUI(width,height);
		
		CGUI = new Cartesian3DGUI(width,height);
		R3DGUI = new Rotate3DGUI(width,height);
		P3DGUI = new Parametric3DGUI(width,height);
		
		
		
		ToolGUIs toolGUIs = new ToolGUIs(width,height);
		ToolGUI FGUI = toolGUIs.getFunctionGUI();
		
		TabsChildren.add(FGUI); //where the Visitors will go
		TabsChildren.add(DIFFGUI);
		TabsChildren.add(MGUI);
		TabsChildren.add(PGUI);
		TabsChildren.add(CGUI);
		TabsChildren.add(R3DGUI);
		TabsChildren.add(P3DGUI);
		
		
        CalculatorPane.addTab("Function", icon, FGUI,
                          "Graph,Differentiate,Integrate,Riemann Sums");
        CalculatorPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        CalculatorPane.addTab("Differential Equations", icon, DIFFGUI,
                          "Visualize and solve Differential Equations");
        CalculatorPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        
        CalculatorPane.addTab("Parametric", icon, PGUI,
                          "Graph Parametric Equations");
        CalculatorPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        CalculatorPane.addTab("3D Rotation", icon, R3DGUI,
                          "Rotate a function about a line");
        CalculatorPane.setMnemonicAt(3, KeyEvent.VK_4);

        
        CalculatorPane.addTab("3D Cartesian", icon, CGUI,
                          "Graph 3D Cartesian functions");
        CalculatorPane.setMnemonicAt(4, KeyEvent.VK_5);
        
        
        
        CalculatorPane.addTab("3D Parametric", icon, P3DGUI,
                          "Graph 3D Parametric Equations");
        CalculatorPane.setMnemonicAt(5, KeyEvent.VK_6);
        
        
        CalculatorPane.addTab("Calculator", icon, MGUI,
                          "Calculator and table generator");
        CalculatorPane.setMnemonicAt(6, KeyEvent.VK_7);

        add(CalculatorPane);
        CalculatorPane.setTabLayoutPolicy(CalculatorPane.SCROLL_TAB_LAYOUT);
        
        
    }
    public void acceptVisitor(Visitor V)
    {
    	Iterator it = TabsChildren.iterator();
    	while (it.hasNext())
    	{
            host hoste = (host) it.next();
            hoste.acceptVisitor(V);
        }
    }
    
    private class GCTabChangeListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			
			Component seletedComponent = CalculatorPane.getSelectedComponent();

			if(seletedComponent != R3DGUI)
				R3DGUI.rotatedGraph.Spin=false;
			else
				R3DGUI.rotatedGraph.Spin=true;
			
			if(seletedComponent != CGUI)
				CGUI.cartesian3DGraph.Spin=false;
			else
				CGUI.cartesian3DGraph.Spin=true;
			
			if(seletedComponent != P3DGUI)
				P3DGUI.parametric3DGraph.Spin=false;
			else
				P3DGUI.parametric3DGraph.Spin=true;
		}
	}
}
	