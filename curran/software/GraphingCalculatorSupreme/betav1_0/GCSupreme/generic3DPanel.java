package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

public class generic3DPanel extends JPanel implements MouseListener, MouseMotionListener,Runnable
{
	private int width,height;
	Color bkgColor = Color.black;
	double mouseX,mouseY;//for mouse movements
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
    Graphics2D bufGraphics;
    Image bufImg = null;
    int delay = 20;//milliseconds of rest btw refreshes when free-spinning
	/*volatile*/ Thread SpinThread;
	boolean Spin = true;
	double currentChangeinTheta=0,currentChangeinPhi=0;
    
    universe3D currentUniverse;
    double globalTheta = 0;
	double globalPhi = Math.PI/2.0;//the initial rotation

	public generic3DPanel(universe3D U)
	{
		currentUniverse = U.getClone();
		width = currentUniverse.screen.width;
		height = currentUniverse.screen.height;
		initObjects();
		setBackground(bkgColor);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(width,height));

		repaint();//to init the graphics
	}
	public void initObjects()
	{
		object3D[] objects = new object3D[0];
	}
	public void startSpin()
	{
		if(SpinThread == null)
		{
			SpinThread	= new Thread(this);
			SpinThread.start();
		}
		else
			Spin = true;
	}
	public void	stopSpin()
	{
		//SpinThread = null;
		Spin = false;
	}
	
	public void run()
	{
		try
		{
			while (SpinThread == Thread.currentThread())
			{
				if(Spin)
				{
					globalTheta+=currentChangeinTheta;
					globalPhi+=currentChangeinPhi;
					displayUniverse();
					Thread.sleep(delay);
				}
				else
					Thread.sleep(200);//time between checks for if Spin == true
					//"Spin" is made false while the tab containing this is deselected
				
			}
		} catch (Exception e) {}
	}
	public void displayUniverse()
	{
		rotationInstance ROT = new rotationInstance(globalTheta,globalPhi);
		currentUniverse.display(bufGraphics,ROT);
		repaint();
	}
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		if(bufImg==null)
		{
			bufImg = createImage(width,height);
			bufGraphics = (Graphics2D) bufImg.getGraphics();
	        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
	        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
	        bufGraphics.setBackground(bkgColor);
	        
	        rotationInstance ROT = new rotationInstance(globalTheta,globalPhi);
			currentUniverse.display(bufGraphics,ROT);
			
			startSpin();			
	    }
	    else
			page.drawImage(bufImg,0,0,this);
	}
	public void update(Graphics g)
	{
		paintComponent(g);
    }
	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseDragged(e);
	}
	public void mouseDragged(MouseEvent e)
	{
		Spin=false;
		currentChangeinTheta = (mouseX - e.getX())/250;
		currentChangeinPhi = (mouseY - e.getY())/250;

		double twoPi = 2*Math.PI;
		
		//put globalPhi inside range of 0 to 2Pi
		while(globalPhi < 0)
		{
			globalPhi += twoPi;
		}
		while(globalPhi > twoPi)
		{
			globalPhi -= twoPi;
		}

		if(globalPhi>Math.PI)//make it so mouse movements never make the thing go the wrong direction
			currentChangeinTheta = -currentChangeinTheta;
			
		globalTheta += currentChangeinTheta;
		
		globalPhi   += currentChangeinPhi;
		mouseX = e.getX();
		mouseY = e.getY();
		
		rotationInstance ROT = new rotationInstance(globalTheta,globalPhi);
		currentUniverse.display(bufGraphics,ROT);
		repaint();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {Spin=true;}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e){}
	
	public void acceptVisitor(Visitor V)
    {
    	V.visitHost(this);
    }
    
    public JCheckBox getWireframeCheckbox()
    {
    	JCheckBox wireframeCheckBox;
    	
    	wireframeCheckBox = new JCheckBox("Wireframe");
        wireframeCheckBox.setMnemonic(KeyEvent.VK_W);
        wireframeCheckBox.setSelected(false);
        wireframeCheckBox.addItemListener(new wireframeCheckBoxItemListener());
        return wireframeCheckBox;
    }
    private class wireframeCheckBoxItemListener implements ItemListener
    {
    	public void itemStateChanged(ItemEvent e)
    	{
    		if(e.getStateChange() == ItemEvent.SELECTED)
    			currentUniverse.setSwitches(universe3D.SWITCHES_WIREFRAME);
    		else
    			currentUniverse.setSwitches(universe3D.SWITCHES_SHADE);
    	}
    }
}
	