/*
	Trivial applet that displays a string
*/

import java.awt.*;
import java.applet.Applet;

public class SlopeField extends Applet implements Runnable
{
	/*public double z(double x, double y,double t)//two drops
	{
		t = -t;
		double x1 = 5;
		double y1 = 5;
		double d = Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
		double witch = 100/(d*d+50);
		
		double r = Math.sin(d*2+t)*witch;
		double drop1= (r+1)/2;
		
		x1 = -5;
		y1 = -5;
		d = Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
		
		witch = 100/(d*d+10);
		
		r = Math.sin(d+2*t)*witch;
		double drop2 = (r+1)/2;
		
		return ((drop1+drop2)/1);
	}*/
	public double z(double x, double y,double t)
	{
		t /=2;
		//double a = Math.sin(x+t);
		//double b = Math.cos(y-t);
		double d = Math.sqrt(x*x+y*y);
		return Math.sin(d+t);
	}
	public double angleToSlope(double angle)
	{
		return Math.tan(angle+Math.PI);
	}
	int cartesianResX = 30;
	int cartesianResY = 30;
	
	Image BufImg;
	Graphics Buf_g;
	
	int width = 650;
	int height = 650;
	double xMin = -10;
	double xMax = 10;
	double yMin = -10;
	double yMax = 10;
	double tIncrement = 0.04;
	
	volatile Thread thread;
	point3D[][] graph;
	Point[][]cartesianSquares;
	int cartesianW,cartesianH;
	int xPix,yPix;
	double xVal,yVal;
	double t=0;
	

	public void updateGraphValues()
	{
		Buf_g.setColor(Color.black);
		Buf_g.fillRect(0,0,width,height);
		//Buf_g.clearRect(0,0,width,height);
		/*for(int x = 0; x<cartesianResX-1;x++)
		{
			for(int y = 0;y<cartesianResY-1;y++)
			{
				xVal = getXvalue(cartesianSquares[x][y].getX());
				yVal = getYvalue(cartesianSquares[x][y].getY());
				graph[x][y] = new point3D(xVal,yVal,z(xVal,yVal,t));
			}
		}*/
		int c = (int)(80.0*(-Math.cos(t/8)+1));
		for(int x = 0; x<cartesianResX-1;x++)
		{
			for(int y = 0;y<cartesianResY-1;y++)
			{
				//double c = (graph[(int)x][(int)y].getZ());
				
				
				
				//Buf_g.setColor(ColorF(c));
				
				
				//cartesianW = (int)cartesianSquares[x+1][y].getX()-(int)cartesianSquares[x][y].getX();
				//cartesianH = (int)cartesianSquares[x][y+1].getY()-(int)cartesianSquares[x][y].getY();
				
				
				xVal = getXvalue(cartesianSquares[x][y].getX());
				yVal = getYvalue(cartesianSquares[x][y].getY());
				//Buf_g.fillRect((int)cartesianSquares[x][y].getX(),(int)cartesianSquares[x][y].getY(),cartesianW,cartesianH);
				double L = getXvalue(cartesianSquares[x+1][y].getX()) - xVal;
				
				Buf_g.setColor(new Color(c,c,c));
				drawSlopeLine(xVal,yVal,L, Buf_g);
			}
		}
		//Buf_g.setColor(Color.white);
		
		for(double x=-20; x<=20;x++)
		{
			drawParticularSolution(0,x/2,Buf_g);		
		}
		
		c = (int)(-200.0*(Math.cos(t/8)));
		if(c>=0)
		{
			Buf_g.setColor(new Color(c,c,c));
			for(double x=-20; x<=20;x++)
			{
				Buf_g.fillOval(getXpixel(0)-2,getYpixel(x/2)-2,4,4);		
			}
			
		}
		/*
			for(int y=-5; y<=5;y++)
			{
			
				drawParticularSolution(y*2,x*2,Buf_g);
			}
		}*/
		t+=tIncrement;
	}
	
	public void drawParticularSolution(double initX, double initY,Graphics g)
	{
		double Length = .4;
		double theta,deltaX,deltaY,finalX,finalY,slope;
		double tempX = initX;
		double tempY = initY;
		while(isInWindow(tempX,tempY))
		{
			slope = z(tempX,tempY,t);
			theta = Math.atan2(slope,1);
			deltaX = (Math.cos(theta)*Length);
			deltaY = (Math.sin(theta)*Length);
			
			finalX = tempX+deltaX;
			finalY = tempY+deltaY;
			
			Buf_g.setColor(ColorF(slope));
			g.drawLine(getXpixel(tempX),getYpixel(tempY),getXpixel(finalX),getYpixel(finalY));
			tempX = finalX;
			tempY= finalY;
		}
		
		tempX = initX;
		tempY = initY;
		while(isInWindow(tempX,tempY))
		{
			slope = z(tempX,tempY,t);
			theta = Math.atan2(slope,1);
			deltaX = (Math.cos(theta)*Length);
			deltaY = (Math.sin(theta)*Length);
			
			finalX = tempX-deltaX;
			finalY = tempY-deltaY;
			
			Buf_g.setColor(ColorF(slope));
			g.drawLine(getXpixel(tempX),getYpixel(tempY),getXpixel(finalX),getYpixel(finalY));
			tempX = finalX;
			tempY= finalY;
		}
		
	}
	
	public boolean isInWindow(double x,double y)
	{
		return (x >= xMin && x<=xMax );//&& y >= yMin && y<=yMax);
	}
	
	public void drawSlopeLine(double x,double y,double length,Graphics g)
	{
		double theta = Math.atan2(z(x,y,t),1);
		length /=2.0;
		
		double deltaX = Math.cos(theta)*length;
		double deltaY = Math.sin(theta)*length;
		double x1 = x+deltaX;
		double y1 = y+deltaY;
		double x2 = x-deltaX;
		double y2 = y-deltaY;
		
		g.drawLine(getXpixel(x1),getYpixel(y1),getXpixel(x2),getYpixel(y2));
	}
	
	public Color ColorF(double z)
	{
		z /=3;
		z+=t/5;
		double r = Math.cos(z*Math.PI+Math.PI);
		double g = Math.sin(z*Math.PI-2.0);
		double b = Math.sin(z*Math.PI-.5)*4/5;
		
		if(r>1)
			r=1;
		if(r<0)
			r=0;
		if(g>1)
			g=1;
		if(g<0)
			g=0;
		if(b>1)
			b=1;
		if(b<0)
			b=0;
		
		return Color.getHSBColor((float)(z),1,1);
		//return new Color((int)(255*r),(int)(255*g),(int)(255*b));
	}
	
	public void init()
	{
		graph = new point3D[cartesianResX][cartesianResY];
		cartesianSquares = new Point[cartesianResX][cartesianResY];
		BufImg = createImage(width,height);
		Buf_g = BufImg.getGraphics();

		for(int x = 0; x<cartesianResX;x++)
		{
			for(int y = 0;y<cartesianResY;y++)
			{
				xPix = (int)(((double)x/cartesianResX)*width);
				yPix = height-(int)(((double)y/cartesianResY)*height);
				cartesianSquares[x][y] = new Point(xPix,yPix);
			}
		}
		
	}
	
	public	void start()
	{
		(thread	= new Thread(this)).start();
	}
	public void	stop()
	{
		thread = null;
	}
	
	public void run()
	{
		try
		{
			while (thread == Thread.currentThread())
			{
				
				repaint();
				Thread.sleep(4);
			}
		} catch (Exception e) {}
	}
	//public void refresh()
	//{	repaint();	}
	
	public void update(Graphics g)
	{
		paint(g);
    }
	public void paint(Graphics g)
	{
		updateGraphValues();
		g.drawImage(BufImg,0,0,this);
	}
	public int getXpixel(double xVal)
	{
		return (int)((xVal - xMin)*((double)width/(xMax - xMin)));
	}
	public int getYpixel(double yVal)
	{
		return height - (int)((yVal - yMin)*((double)height/(yMax - yMin)));
	}
	public double getXvalue(double xPix)
	{
		return((double)xPix*((double)(xMax - xMin)/width))+xMin;
	}
	public double getYvalue(double yPix)
	{
		return((double)yPix*((double)(yMax - yMin)/height))+yMin;
	}
}


class point3D
{
	double z,y,x;
	public point3D(double X, double Y, double Z)
	{
		x = X;
		y = Y;
		z = Z;
	}
	
	public point3D(){}
	
	public void setCoords(double X, double Y, double Z)
	{
		x = X;
		y = Y;
		z = Z;
	}
	
	public void setX(double X)
	{	x = X;	}
	public void setY(double Y)
	{	y = Y;	}
	public void setZ(double Z)
	{	z = Z;	}
	
	
	public double getX()
	{	return(x);	}
	public double getY()
	{	return(y);	}
	public double getZ()
	{	return(z);	}
	
	
}//point3D