import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.Event;
import java.awt.event.*;
import java.io.*;

public class SimpleCartesian3Dbig extends Applet implements MouseListener, MouseMotionListener
{
	public double Fof_X_Y(double x,double y)
	{
		return Math.sin(x*y/30)*4;
		//return Math.sqrt(100-(Math.pow(x,2)+Math.pow(y,2)));//hemisphere r = 10
		//return Math.sin(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)))*3;//drop in puddle
	}
	
	Color bkgColor = Color.black;
	Color graphColor= new Color(10,200,100);
	Color axesColor = new Color(255,255,255,200);
	
	int    height = 600;
	int    width  = 600;
	double xMin =-10;
	double xMax = 10;
	double yMin =-10;
	double yMax = 10;
	double zMin =-10;
	double zMax = 10;
	
	boolean showAxes = true;
	int labelDist = -4;
	
	int cartesianResolutionX = 20;//number of visible 3D units (squares) defined between Xmin and Xmax
	int cartesianResolutionY = 20;
	
	point3D xMin3D = new point3D(xMin,0,0);
	point3D xMax3D = new point3D(xMax,0,0);
	point3D yMin3D = new point3D(0,yMin,0);
	point3D yMax3D = new point3D(0,yMax,0);
	point3D zMin3D = new point3D(0,0,zMin);
	point3D zMax3D = new point3D(0,0,zMax);
	
	point3D[] axes = {xMin3D,xMax3D,yMin3D,yMax3D,zMin3D,zMax3D};
	
	
	static Space3D starSpace = new Space3D();//creates a Space3D object (see the Space3D class)
	double initX,initY,Xrot,Yrot;//used for mouse countrol
	int X1,Y1,X2,Y2;//used when drawing the graph
	double Xvalue,Yvalue;
	point3D cartesianCoords[][] = new point3D[cartesianResolutionX+1][cartesianResolutionY+1];
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;//VALUE_ANTIALIAS_ON for nti aliasing
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
	Image bufImg;
	Graphics2D bufGraphics;
	Graphics2D g2;
	public void init()
	{
		for(int x=0; x <=cartesianResolutionX;x++)
		{
			for(int y = 0; y<=cartesianResolutionY;y++)
			{
				Xvalue = ((double)x/(double)cartesianResolutionX)*(xMax-xMin)+xMin;
				Yvalue = ((double)y/(double)cartesianResolutionY)*(yMax-yMin)+yMin;
				cartesianCoords[x][y] = new point3D(Xvalue,Yvalue,Fof_X_Y(Xvalue,Yvalue));
			}
		}

		starSpace.setWindow(height,width,xMin,xMax,yMin,yMax);
		bufImg = createImage(width,height);
		bufGraphics = (Graphics2D) bufImg.getGraphics();
		
        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
		
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		bufGraphics.setColor(bkgColor);
		bufGraphics.fillRect(0,0,width,height);
		bufGraphics.setColor(graphColor);
		bufGraphics.drawString("rotate the graph with your mouse.",20,35);
	}	
	public void rotateAll(double Yrot,double Xrot,double Zrot)
	{
	    for(int x=0; x <=cartesianResolutionX;x++)
		{
			for(int y = 0; y<=cartesianResolutionY;y++)
			{
				cartesianCoords[x][y].rotateAboutY(Xrot);
	    		cartesianCoords[x][y].rotateAboutX(Yrot);
	    		//cartesianCoords[x][y].rotateAboutZ(Zrot);
			}
		}
	    for(int i = 0; i<6;i++)
	    {
	    	axes[i].rotateAboutY(Xrot);
	    	axes[i].rotateAboutX(Yrot);
	    	//axes[i].rotateAboutZ(Zrot);
	    }
	}
	
	public void paint(Graphics g)
	{
			if(showAxes)
			{	drawAxes();	}
			drawGraph();
			
			bufGraphics.drawString("z = sin(x*y/30)*4",20,20);

			g.drawImage(bufImg,0,0,this);
			
			bufGraphics.setColor(bkgColor);
			bufGraphics.fillRect(0,0,width,height);//clear the screen
	}
	
	public void drawGraph()
	{
		Point[][] flattenedCartesianCoords = new Point[cartesianResolutionX+1][cartesianResolutionY+1];
		for(int x=0; x <=cartesianResolutionX;x++)
		{
			for(int y = 0; y<=cartesianResolutionY;y++)
			{
				flattenedCartesianCoords[x][y] = starSpace.getFlattenedPoint(cartesianCoords[x][y]);
			}
		}
		bufGraphics.setColor(graphColor);
		for(int x=0; x <=cartesianResolutionX;x++)
		{
			for(int y = 0; y<=cartesianResolutionY;y++)
			{
				X1 = (int)flattenedCartesianCoords[x][y].getX();
				Y1 = (int)flattenedCartesianCoords[x][y].getY();
				if(y<cartesianResolutionY)
				{
					
					X2 = (int)flattenedCartesianCoords[x][y+1].getX();
					Y2 = (int)flattenedCartesianCoords[x][y+1].getY();
					
					if(Y1 != height && Y2 != height)
						bufGraphics.drawLine(X1,Y1,X2,Y2);
				}
				
				if(x<cartesianResolutionX)
				{
					X2 = (int)flattenedCartesianCoords[x+1][y].getX();
					Y2 = (int)flattenedCartesianCoords[x+1][y].getY();
					
					if(Y1 != height && Y2 != height)
						bufGraphics.drawLine(X1,Y1,X2,Y2);
				}
			}
		}
	}
	public void drawAxes()
	{
		bufGraphics.setColor(axesColor);
		bufGraphics.drawLine(
							(int)starSpace.getFlattenedPoint(xMin3D).getX(),
							(int)starSpace.getFlattenedPoint(xMin3D).getY(),
							(int)starSpace.getFlattenedPoint(xMax3D).getX(),
							(int)starSpace.getFlattenedPoint(xMax3D).getY()
							);
		bufGraphics.drawString("X",(int)starSpace.getFlattenedPoint(xMin3D).getX()+labelDist,
								   (int)starSpace.getFlattenedPoint(xMin3D).getY()+labelDist);
							
		bufGraphics.drawLine(
							(int)starSpace.getFlattenedPoint(yMin3D).getX(),
							(int)starSpace.getFlattenedPoint(yMin3D).getY(),
							(int)starSpace.getFlattenedPoint(yMax3D).getX(),
							(int)starSpace.getFlattenedPoint(yMax3D).getY()
							);
		bufGraphics.drawString("Y",(int)starSpace.getFlattenedPoint(yMax3D).getX()+labelDist,
								   (int)starSpace.getFlattenedPoint(yMax3D).getY()+labelDist);
					
							
		bufGraphics.drawLine(
							(int)starSpace.getFlattenedPoint(zMin3D).getX(),
							(int)starSpace.getFlattenedPoint(zMin3D).getY(),
							(int)starSpace.getFlattenedPoint(zMax3D).getX(),
							(int)starSpace.getFlattenedPoint(zMax3D).getY()
							);
		bufGraphics.drawString("Z",(int)starSpace.getFlattenedPoint(zMax3D).getX()+labelDist,
								   (int)starSpace.getFlattenedPoint(zMax3D).getY()+labelDist);
	}//drawAxes
	
	public void update(Graphics g)
	{
		paint(g);
    }
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e)
	{
		initX = e.getX();
		initY = e.getY();
		e.consume();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e)
	{
	  int x = e.getX();
	  int y = e.getY();
	  
	  Xrot = (initX-x)/300;
	  Yrot = (y-initY)/300;
	  
	  initX = x;
	  initY = y;
	  
	  rotateAll(Yrot,Xrot,0);
	  repaint();
	  e.consume();
	}
	public void mouseMoved(MouseEvent e){}
}//Cartesian3D

class point3D//stores and retrieves X,Y,Z coordinates
{
	double X=0,Y=0,Z=0;
	Color pntclr;
	public point3D(double x,double y,double z)
	{
		X=x;
		Y=y;
		Z=z;
	}
	
	public point3D() {}
	public void setCoords(double x,double y,double z)
	{
		X=x;
		Y=y;
		Z=z;
	}
	
	public void setX(double x)
	{	X=x;	}
	public void setY(double y)
	{	Y=y;	}
	public void setZ(double z)
	{	Z=z;	}
	public double getX()
	{	return X;	}
	public double getY()
	{	return Y;	}
	public double getZ()
	{	return Z;	}
	
	public void rotateAboutY(double theta)
	{
		double initAngle = Math.atan2(Z,X);
		double XZdist = Math.sqrt(Math.pow(Z,2)+Math.pow(X,2));
		double angle = initAngle + theta;
		Z=(XZdist*Math.sin(angle));
		X=(XZdist*Math.cos(angle));
	}
	
	public void rotateAboutX(double theta)
	{
		double initAngle = Math.atan2(Z,Y);
		double YZdist = Math.sqrt(Math.pow(Z,2)+Math.pow(Y,2));
		double angle = initAngle + theta;
		Z=(YZdist*Math.sin(angle));
		Y=(YZdist*Math.cos(angle));
	}
	
	public void setColor(Color clr)
	{	pntclr = clr;	}
	public Color getColor()
	{	return pntclr;	}
}//point3D

class Space3D
{
	int    PixHeight;//height in pixels of the window
	int    PixWidth;
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	point3D observerPosition = new point3D(0,0,20);
	double perspective = 30;
	double zoomFactor = 1;
	
	double sizeRatio;
	Point2D.Double flattenedPoint;
	
	public Space3D(){}
	public void setWindow(int h, int w, double Xm, double xM, double Ym, double yM)
	{
		PixHeight = h;
		PixWidth = w;
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
	}
	
	public Point getFlattenedPoint(point3D objPosition)//converts a point is space to a pixel position
	{
		sizeRatio = zoomFactor*Math.pow(2,-(observerPosition.getZ()-objPosition.getZ())/perspective);

		flattenedPoint = new Point2D.Double(objPosition.getX()*sizeRatio,objPosition.getY()*sizeRatio);
		
		return new Point(getXpixel(flattenedPoint.getX()),getYpixel(flattenedPoint.getY()));
	}
	
	public int getFlattenedSize(point3D objPosition,double actualSize)
	{
		sizeRatio = zoomFactor*Math.pow((observerPosition.getZ()-objPosition.getZ()),-perspective);
		return (int)Math.round(actualSize*sizeRatio);
	}
	
	public int getXpixel(double xVal)
	{	return (int)Math.round((xVal - xMin)*((double)PixWidth/(xMax - xMin)));	}
	public int getYpixel(double yVal)
	{	return PixHeight - (int)Math.round(((yVal - yMin)*((double)PixHeight/(yMax - yMin))));	}
	public double getXvalue(double xPix)
	{	return((double)xPix*((double)(xMax - xMin)/PixWidth))+xMin;	}
	public double getYvalue(double yPix)
	{	return((double)yPix*((double)(yMax - yMin)/PixHeight))+yMin;	}
}//Space3D