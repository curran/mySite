

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.text.DecimalFormat;

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

public class MSpiralClick extends Applet
{
	Point point1 = null, point2 = null;
	int numIterations = 90;
	int excapeIterations;
	
	int    PANEL_HEIGHT = 600;
	int    PANEL_WIDTH = 800;
	double xMin = -3;
	double xMax = 3;
	double yMin = -3.0*(6.0/8.0);
	double yMax = 3.0*(6.0/8.0);
	
	Image bufImg;
	Graphics bufGraphics;
	Image bufImgL;
	Graphics bufGraphicsL;
	boolean redoFractal = true;
	DecimalFormat format;
	int sidex,sidey;//used with the zooming square
	
	public void init()
	{
		bufImg = createImage(PANEL_WIDTH,PANEL_HEIGHT);
		bufGraphics = bufImg.getGraphics();
		bufImgL = createImage(PANEL_WIDTH,PANEL_HEIGHT);
		bufGraphicsL = bufImgL.getGraphics();
		
		LineListener listener = new LineListener();
		addMouseListener (listener);
		addMouseMotionListener (listener);
		
		setBackground(Color.black);
		bufGraphicsL.setColor(Color.gray);
		
		format = new DecimalFormat("##.###");
	}
	
	public void paint(Graphics page)
	{
		page.setColor(Color.green);//for the square
		
		if(redoFractal)
		{//x= reals,y=imag
		
			complexNumber c;
			
			complexNumber z = new complexNumber(0,0);
			boolean inSet = false;
			//bufGraphicsL.setColor(Color.gray);
			for(int yP = 0; yP<PANEL_HEIGHT;yP++)
			{
				for(int xP = 0; xP<PANEL_WIDTH;xP++)
				{
					z=new complexNumber(0,0);
					c=new complexNumber(getXvalue(xP),getYvalue(yP));
					int x1=0,y1=0,x2=0,y2=0;
					x1 = xP;
					y1 = yP;
					for(int i=0;i<numIterations;i++)
					{
						//z=z^2+c ; the Mandlebrot set
						z = complexNumberMath.add(complexNumberMath.square(z),c);
						x2 = getXpixel(z.realComponent);
						y2 = getYpixel(z.imaginaryComponent);
						double d = Math.sqrt(z.realComponent*z.realComponent+z.imaginaryComponent*z.imaginaryComponent);
						if(d> 2)
						{
							excapeIterations = i;
							i=numIterations;//to escape the loop
							inSet = false;
						}
						else
							inSet = true;
						
						//bufGraphicsL.drawLine(x1,y1,x2,y2);
						x1=x2;
						y1=y2;
					}
					if(inSet)
					{
						bufGraphics.setColor(Color.black);
						bufGraphics.drawLine(xP,yP,xP,yP);
					}
					else
					{
						int r = (int)(255.0/2.0*((Math.sin((double)excapeIterations/numIterations*20)+1)));
						int g = (int)(255.0/2.0*((Math.sin((double)excapeIterations/numIterations*20+.5)+1)));
						int b = (int)(255.0/2.0*((Math.sin((double)excapeIterations/numIterations*20+1)+1)));
						
						bufGraphics.setColor(new Color(r,g,b));
						bufGraphics.drawLine(xP,yP,xP,yP);
					}

					
				}//for every point
				if((yP)%5 == 0)
				page.drawImage(bufImg,0,0,this);
				
			}//closing "for"
			redoFractal=false;
			bufGraphicsL.drawImage(bufImg,0,0,this);
		}
		
		page.drawImage(bufImgL,0,0,this);
		
		if(point1 != null && point2 != null)
			page.drawRect(point1.x,point1.y,sidex,sidey);
	}//end paintComponent
	public void drawSpiral(int x,int y)
	{
		bufGraphicsL.drawImage(bufImg,0,0,this);
		complexNumber z=new complexNumber(0,0);
		complexNumber c=new complexNumber(getXvalue(x),getYvalue(y));
		int x1=0,y1=0,x2=0,y2=0;
		x1 = x;
		y1 = y;
		boolean Escape = false;
		bufGraphicsL.drawString("iteration  real , imaginary",20,40);
		for(int i=0;i<numIterations;i++)
		{
			//z=z^2+c ; the Mandlebrot set
			z = complexNumberMath.add(complexNumberMath.square(z),c);
			x2 = getXpixel(z.realComponent);
			y2 = getYpixel(z.imaginaryComponent);
			if(z.realComponent < -2 || z.realComponent > 2 || z.imaginaryComponent < -2 || z.imaginaryComponent > 2)
			{
				excapeIterations = i;
				i=numIterations;//to escape the loop
				Escape = true;
			}
			bufGraphicsL.setColor(Color.gray);
			bufGraphicsL.drawLine(x1,y1,x2,y2);
			x1=x2;
			y1=y2;
			int n = i+1;
			bufGraphicsL.setColor(Color.black);
			bufGraphicsL.drawString(n+"  "+format.format(z.realComponent)+" , "+format.format(z.imaginaryComponent),20,40+16*(i+1));
		}
		if(Escape)
			bufGraphicsL.drawString("Excape Iterations: "+excapeIterations,20,20);
		else
			bufGraphicsL.drawString("No Escape",20,20);
		repaint();
	}
		
	
	public void update(Graphics g)
	{
		paint(g);
    }
	
	private class LineListener implements MouseListener,MouseMotionListener
	{
		public void mousePressed(MouseEvent event)
		{
			point1 = event.getPoint();
			drawSpiral(event.getX(),event.getY());
		}
		public void mouseDragged(MouseEvent event)
		{
			point2 = event.getPoint();
			sidex = (int)Math.max(point2.getX()-point1.getX(),point2.getY()-point1.getY());
			sidey = (int)((double)sidex*((double)PANEL_HEIGHT/(double)PANEL_WIDTH));
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
				double xMa = getXvalue(x1+sidex);
				double yMa = getYvalue(y1);
				double ym = getYvalue(y1+sidey);
				
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
		public void mouseExited(MouseEvent event){}
		public void mouseMoved(MouseEvent event)
		{
			drawSpiral(event.getX(),event.getY());
		}
	}
	
	public int getXpixel(double xVal)
	{	return (int)((xVal - xMin)*((double)PANEL_WIDTH/(xMax - xMin)));	}
	public int getYpixel(double yVal)
	{	return PANEL_HEIGHT - (int)((yVal - yMin)*((double)PANEL_HEIGHT/(yMax - yMin)));	}
	
	public double getXvalue(double xPix)
	{	
		return (xPix/(double)PANEL_WIDTH)*(xMax - xMin) +xMin;
	}
	public double getYvalue(double yPix)
	{	
		return ((PANEL_HEIGHT-yPix)/(double)PANEL_HEIGHT)*(yMax - yMin)+yMin;
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
}