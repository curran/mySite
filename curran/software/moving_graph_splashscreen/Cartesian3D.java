import java.awt.*; //necessary for Color, Graphics, Image, Point
import java.applet.*;//necessary for all applets
import java.awt.geom.*;//necessary for Point2D
import java.awt.Event;
import java.awt.event.*;
import java.io.*;

public class Cartesian3D extends Applet implements MouseListener, MouseMotionListener, Runnable
{
	
	Color bkgColor = Color.black;
	Color graphColor= Color.orange;
	Color axesColor = new Color(200,200,200);

	int    height = 400;
	int    width  = 300;
	double xMin =-10;
	double xMax = 10;
	double yMin =-10;
	double yMax = 10;
	double zMin =-10;
	double zMax = 10;
	
	double timeIncrement = .2;
	int delay = 30;//milliseconds of rest btw refreshes
	
	Space3D graphSpace = new Space3D(height,width,xMin,xMax,yMin,yMax,zMin,zMax);
	Graph3D Graph = new Graph3D(graphSpace,graphColor,axesColor);
	double initX,initY,Xrot,Yrot;//used for mouse countrol
	volatile Thread thread;
	boolean passTime = true;
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;//ON
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
	Image bufImg;
	Graphics2D bufGraphics;
	Graphics2D g2;
	public void init()
	{
		bufImg = createImage(width,height);
		bufGraphics = (Graphics2D) bufImg.getGraphics();

        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
        bufGraphics.setBackground(bkgColor);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		graphSpace.rotateSpace(Math.toRadians(-50),Math.toRadians(-10),0);
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
				Graph.incrementTime(timeIncrement);
				repaint();
				Thread.sleep(delay);
			}
		} catch (Exception e) {}
	}
	
	public void paint(Graphics g)
	{
			bufGraphics.clearRect(0,0,width,height);//clear the screen
			Graph.displayGraph(bufGraphics);
			//bufGraphics.drawString("z = sin((x*y)/6+t)+(x*y)/10*cos(t/10)",20,20);
			g.drawImage(bufImg,0,0,this);
			
			
	}//paint
	
	
	
	
	
	public void update(Graphics g)
	{
		paint(g);
    }
	
	public void mouseClicked(MouseEvent e)
		{
			if(!passTime)
				passTime = true;
			if(passTime)
				passTime = false;
				repaint();
		}
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
	  
	  //Graph.rotateAll(Yrot,Xrot,0);
	  graphSpace.rotateSpace(Yrot,Xrot,0);
	  Graph.setSpace(graphSpace);

	  repaint();
	  
	  e.consume();
	}
	public void mouseMoved(MouseEvent e){}
} //Starfield class

class Graph3D
{

	Color graphColor= Color.orange;
	Color axesColor = new Color(200,200,200);
	
	boolean showAxes = false;//true;
	int labelDist = -4;
	
	Space3D starSpace = new Space3D();
	
	int cartesianResolutionX = 30;//number of visible X,Y surface units (squares) defined between Xmin and Xmax
	int cartesianResolutionY = 30;
	double time = 0;

	point3D xMin3D;
	point3D xMax3D;
	point3D yMin3D;
	point3D yMax3D;
	point3D zMin3D;
	point3D zMax3D;
	
	point3D[] axes;
	
	double Xvalue,Yvalue;//used in creating the 3D points on the surface
	int X1,Y1,X2,Y2;//used in drawing the graph
	point3D cartesianCoords[][] = new point3D[cartesianResolutionX+1][cartesianResolutionY+1];
	
	public Graph3D(Space3D graphSpace, Color graphC, Color AxesC)
	{
		Color graphColor= graphC;
		Color axesColor = AxesC;
		
		
		setSpace(graphSpace);
		set3DPoints();
	}
	
	public void setSpace(Space3D graphSpace)
	{
		starSpace = graphSpace;
		establishAxes();
	}
	
	public void establishAxes()
	{
		xMin3D = new point3D(starSpace.xMin,0,0);
		xMax3D = new point3D(starSpace.xMax,0,0);
		yMin3D = new point3D(0,starSpace.yMin,0);
		yMax3D = new point3D(0,starSpace.yMax,0);
		zMin3D = new point3D(0,0,starSpace.zMin);
		zMax3D = new point3D(0,0,starSpace.zMax);
		
		point3D[] temp = {xMin3D,xMax3D,yMin3D,yMax3D,zMin3D,zMax3D};
		axes = temp;
	}
	
	public double Fof_X_Y(double x,double y,double t)
	{
		t = -t;
		//return Math.sin((x*y)/10+t)*4;
		//return Math.sqrt(100-(Math.pow(x,2)+Math.pow(y,2)));//hemisphere r = 10
		double d = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		double s = Math.sin(d*2+t);
		double f = Math.sin((x*y)/6+t);//ripples
		double l = (x*y)/10*Math.cos(t/10);//the shape
		return l+f;
	}
	
	public void set3DPoints()
	{
		for(int x=0; x <=cartesianResolutionX;x++)
		{
			for(int y = 0; y<=cartesianResolutionY;y++)
			{
				Xvalue = ((double)x/(double)cartesianResolutionX)*(starSpace.xMax-starSpace.xMin)+starSpace.xMin;
				Yvalue = ((double)y/(double)cartesianResolutionY)*(starSpace.yMax-starSpace.yMin)+starSpace.yMin;
				cartesianCoords[x][y] = new point3D(Xvalue,Yvalue,Fof_X_Y(Xvalue,Yvalue,time));
			}
		}
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
	public void displayGraph(Graphics2D g)
	{
		if(showAxes)//draw the axes
		{	drawAxes(g);	}
		drawGraph(g);
	}
	
	public void incrementTime(double timeIncrement)
	{
		time = time +timeIncrement;
		set3DPoints();
	}
	
	public void drawGraph(Graphics2D bufGraphics)
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
					if(Y1 != starSpace.PixHeight && Y2 != starSpace.PixHeight)
						bufGraphics.drawLine(X1,Y1,X2,Y2);
				}
				
				if(x<cartesianResolutionX)
				{
					X2 = (int)flattenedCartesianCoords[x+1][y].getX();
					Y2 = (int)flattenedCartesianCoords[x+1][y].getY();
					if(Y1 != starSpace.PixHeight && Y2 != starSpace.PixHeight)
						bufGraphics.drawLine(X1,Y1,X2,Y2);
				}
			}
		}
	}
	public void drawAxes(Graphics2D bufGraphics)
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
}//Graph3D

class point3D//stores and retrieves X,Y,Z coordinates
{
	double X=0,Y=0,Z=0;
	Color pntclr;
	public point3D(double x,double y,double z)//constructor
	{
		X=x;
		Y=y;
		Z=z;
	}
	
	public point3D() {}//this is a constructor too, so you could create a point3D object and fill it with points later
	
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
}//point3D class

class Space3D
{
	int    PixHeight;//height in pixels of the window
	int    PixWidth;
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	double zMin;
	double zMax;
	point3D observerPosition = new point3D(0,0,12);
	double spaceRotationAboutY = 0;
	double spaceRotationAboutX = 0;
	double spaceRotationAboutZ = 0;
	double perspective = 30;
	double zoomFactor = 1;
	point3D objPosition;
	
	double sizeRatio;
	Point2D.Double flattenedPoint;
	
	public Space3D(){}
	
	public Space3D(int h, int w, double Xm, double xM, double Ym, double yM, double Zm, double zM)
	{
		PixHeight = h;
		PixWidth = w;
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
		zMin = Zm;
		zMax = zM;
	}
	
	public void setWindow(int h, int w, double Xm, double xM, double Ym, double yM)
	{
		PixHeight = h;
		PixWidth = w;
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
	}
	
	public void rotateSpace(double aboutX,double aboutY,double aboutZ)
	{
		spaceRotationAboutX += aboutX;
		spaceRotationAboutY += aboutY;
		spaceRotationAboutZ += aboutZ;
	}
	
	public Point getFlattenedPoint(point3D pos)//converts a point is space to a pixel position
	{
		objPosition = new point3D(pos.getX(),pos.getY(),pos.getZ());
		objPosition.rotateAboutX(spaceRotationAboutX);
		objPosition.rotateAboutY(spaceRotationAboutY);
		//objPosition.rotateAboutZ(spaceRotationAboutZ);
		sizeRatio = zoomFactor*Math.pow(2,-(observerPosition.getZ()-objPosition.getZ())/perspective);

		//sizeRatio = zoomFactor*Math.pow((observerPosition.getZ()-objPosition.getZ()),-perspective);

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

}