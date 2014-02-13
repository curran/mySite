import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;

/*public class Fractal extends JApplet
{
	int WIDTH = 500,HEIGHT = 500;
	public void init()
	{
		repaint();
	}
	
	public void paint(Graphics g)
	{
		getContentPane().add (new FractalPanel());
		setSize (WIDTH,HEIGHT);
	}
}*/

public class Fractal extends Applet
{
	Point point1 = null, point2 = null;
	int numIterations = 90;
	int excapeIterations;
	
	int    PANEL_HEIGHT = 600;
	int    PANEL_WIDTH = 600;
	double xMin = -3;
	double xMax = 3;
	double yMin = -3;
	double yMax = 3;
	
	Image bufImg;
	Graphics bufGraphics;
	boolean redoFractal = true;
	int side;//used with the zooming square
	
	public void init()
	{
		bufImg = createImage(PANEL_WIDTH,PANEL_HEIGHT);
		bufGraphics = bufImg.getGraphics();
		
		LineListener listener = new LineListener();
		addMouseListener (listener);
		addMouseMotionListener (listener);
		
		setBackground(Color.black);
	}
	
	public void paint(Graphics page)
	{
		page.setColor(Color.green);//for the square
		
		
		if(redoFractal)
		{
		
			complexNumber c;
			
			complexNumber z = new complexNumber(0,0);
			boolean inSet = false;
			
			for(int yP = 0; yP<PANEL_HEIGHT;yP++)
			{
				for(int xP = 0; xP<PANEL_WIDTH;xP++)
				{
					z=new complexNumber(0,0);
					c=new complexNumber(getXvalue(xP),getYvalue(yP));
					for(int i=0;i<numIterations;i++)
					{
						//z=(z^2+c)^2
						z = complexNumberMath.square(complexNumberMath.square(complexNumberMath.add(complexNumberMath.square(z),c)));
						if(z.realComponent < -2 || z.realComponent > 2 || z.imaginaryComponent < -2 || z.imaginaryComponent > 2)
						{
							excapeIterations = i;
							i=numIterations;//to escape the loop
							inSet = false;
						}
						else
							inSet = true;
					}
					if(inSet)
					{
						bufGraphics.setColor(Color.black);
						bufGraphics.drawLine(xP,yP,xP,yP);
					}
					else
					{
						int r = (int)(255.0/2.0*((Math.sin((double)excapeIterations/numIterations*20)+1)));
						bufGraphics.setColor(new Color(r,0,0));
						bufGraphics.drawLine(xP,yP,xP,yP);
					}
				}//for every point
				if(yP%5==0)
					page.drawImage(bufImg,0,0,this);
			}//closing "for"
			redoFractal=false;
		}
		
		page.drawImage(bufImg,0,0,this);
		
		if(point1 != null && point2 != null)
			page.drawRect(point1.x,point1.y,side,side);
	}//end paintComponent
	
	public void update(Graphics g)
	{
		paint(g);
    }
	
	private class LineListener implements MouseListener,MouseMotionListener
	{
		public void mousePressed(MouseEvent event)
		{
			point1 = event.getPoint();
		}
		public void mouseDragged(MouseEvent event)
		{
			point2 = event.getPoint();
			side = (int)Math.max(point2.getX()-point1.getX(),point2.getY()-point1.getY());
				repaint();
		}
		
		
		public void mouseClicked(MouseEvent event){repaint();}
		public void mouseReleased(MouseEvent event)
		{
			try
			{
				double x1=point1.getX();
				double y1=point1.getY();
				double xm = getXvalue(x1);
				double xMa = getXvalue(x1+side);
				double yMa = getYvalue(y1);
				double ym = getYvalue(y1+side);
				
				xMin = xm;
				xMax = xMa;
				yMax = yMa;
				yMin = ym;
				
				redoFractal=true;
				point1 = null;
				point2 = null;
				repaint();
			}
			catch(Exception e)
			{}
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseMoved(MouseEvent event) {}
	}
	
	public int getXpixel(double xVal)
	{	return (int)((xVal - xMin)*((double)PANEL_WIDTH/(xMax - xMin)));	}
	public int getYpixel(double yVal)
	{	return PANEL_HEIGHT - (int)((yVal - yMin)*((double)PANEL_HEIGHT/(yMax - yMin)));	}
	
//	Xpix/((double)PANEL_WIDTH/(xMax - xMin)) +xMin = xVal
	
	
//	yPix = PANEL_HEIGHT - (int)((yVal - yMin)*((double)PANEL_HEIGHT/(yMax - yMin)));
//	yVal = (PANEL_HEIGHT-yPix)/((double)PANEL_HEIGHT/(yMax - yMin))+yMin
	public double getXvalue(double xPix)
	{	
		return (xPix/(double)PANEL_WIDTH)*(xMax - xMin) +xMin;
		//return xPix/((double)PANEL_WIDTH/(xMax - xMin)) +xMin;
		//((double)xPix*((double)(xMax - xMin)/PANEL_WIDTH))+xMin;
	}
	public double getYvalue(double yPix)
	{	return ((PANEL_HEIGHT-yPix)/(double)PANEL_HEIGHT)*(yMax - yMin)+yMin;
	//((double)yPix*((double)(yMax - yMin)/PANEL_HEIGHT))+yMin
	}
}

class complexNumber
{
	double realComponent,imaginaryComponent;
	public complexNumber(double real, double imaginary)
	{
		realComponent=real;
		imaginaryComponent=imaginary;
	}
}

class complexNumberMath
{
	static public complexNumber add(complexNumber Z1,complexNumber Z2)
	{
		return new complexNumber(Z1.realComponent + Z2.realComponent,Z1.imaginaryComponent+Z2.imaginaryComponent);
	}
	
	static public complexNumber square(complexNumber Z)
	{//a= Z1.realComponent, b=Z1.imaginaryComponent
		//a^2 - b^2 + 2*i*a*b
		double a = Z.realComponent;
		double b = Z.imaginaryComponent;
		return new complexNumber(a*a-b*b,2*a*b);
	}
	
	/*static public complexNumber multiply(complexNumber Z)
	{//a= Z1.realComponent, b=Z1.imaginaryComponent
		//a^2 - b^2 + 2*i*a*b
		double a = Z.realComponent;
		double b = Z.imaginaryComponent;
		return new complexNumber(a*a-b*b,2*a*b);
	}*/
}