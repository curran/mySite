import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

class generic3DPanel extends JPanel implements MouseListener, MouseMotionListener,Runnable
{
	int width,height;
	Color bkgColor = Color.black;
	double mouseX,mouseY;//for mouse movements
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
    Graphics2D bufGraphics;
    Image bufImg = null;
    int delay = 20;//milliseconds of rest btw refreshes when free-spinning
	volatile Thread SpinThread;
	static boolean Spin = true;
	double currentChangeinTheta=0,currentChangeinPhi=0;
    
    universe3D currentUniverse;
    static double globalTheta = 0;
	static double globalPhi = Math.PI/2.0;//the initial rotation

	public generic3DPanel(universe3D U,int w,int h)
	{
		width = w;
		height = h;
		currentUniverse = U;
		initObjects();
		boolean[] switches = {true,false,true};
		//DisplaySwitches[0] = zsort or not
		//DisplaySwitches[1] = true -> black outline
		//DisplaySwitches[2])// = true -> fill with objColor
		currentUniverse.setSwitches(switches);
		setBackground(bkgColor);
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();//to init the graphics
	}
	public void initObjects()
	{
		object3D[] objects = new object3D[0];
	}
	public void startSpin()
	{
		SpinThread	= new Thread(this);
		SpinThread.start();
	}
	public void	stopSpin()
	{
		SpinThread = null;
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
					rotationInstance ROT = new rotationInstance(globalTheta,globalPhi);
					currentUniverse.displayWireframe(bufGraphics,ROT);
					repaint();
				}
				Thread.sleep(delay);
			}
		} catch (Exception e) {System.out.println(e);}
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
			currentUniverse.displayWireframe(bufGraphics,ROT);
			
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
		
		globalTheta += currentChangeinTheta;
		globalPhi   += currentChangeinPhi;
		mouseX = e.getX();
		mouseY = e.getY();
		
		rotationInstance ROT = new rotationInstance(globalTheta,globalPhi);
		currentUniverse.displayWireframe(bufGraphics,ROT);
		repaint();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {Spin=true;}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e){}
}
	