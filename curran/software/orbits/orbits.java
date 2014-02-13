import java.applet.*;
import java.awt.*;
//Curran Kelleher
//Vortex
//10/23/04

public class orbits extends Applet implements Runnable
{
	int    ApH = 500;
	int    ApW = 500;
	
	
	double xMin = -10;
	double xMax = 30;
	double yMin = -10;
	double yMax = 20;
	
	Image BufferImg;
	Graphics Buffer;
	
	volatile Thread thread;
		
	double rateOfTime = 0.0005;
	int delay = 20;//milliseconds
	double currentTime;
	Planet sun;
	Planet earth,moon,moon2,moon3, moon4;
	
	Color bkgColor = Color.black;
	boolean isFirstTime = true;
	
	public void init()
	{
		initPlanets();
		BufferImg = createImage(ApW,ApH);
		Buffer = BufferImg.getGraphics();
	}
	
	public	void start()
	{
		thread	= new Thread(this);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
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
			}
		} catch (Exception e) {}
	}

	public void paint(Graphics g)
	{
		//sun.drawAtTime(0,g);
		MovePlanets(g);
	}
	
	
	public void MovePlanets(Graphics g)
	{
		Buffer.setColor(bkgColor);
		Buffer.fillRect(0,0,ApW,ApH);
		drawPlanets(currentTime,Buffer);
		try {Thread.sleep(delay);} catch (InterruptedException e){}
		g.drawImage(BufferImg, 0, 0, this);
		currentTime=currentTime+rateOfTime;
	}
	
	public void update(Graphics g)
	{
		paint(g);
    }
	
	public void initPlanets()
	{
		int CenterX = ApW/2;
		int CenterY = ApH/2;
		int x = (int)(ApH*0.17);
		int Ssize = 100;
		//               pixel       pixel     pix
		//n = new Planet(OrbitCtrX, OrbitCtrY, size, speed, Orbit radius,color);
		sun = new Planet(CenterX,   CenterY  ,Ssize,   0,     0                ,Color.red);
		earth=new Planet(CenterX,   CenterY  ,Ssize/2, 30,    x+Ssize/2       ,Color.orange);
		moon =new Planet(CenterX,   CenterY  ,Ssize/4, 80,   x/2+Ssize/4       ,Color.green);
		moon2=new Planet(CenterX,   CenterY  ,Ssize/8, 120,    x/4+Ssize/8       ,Color.blue);
		moon3=new Planet(CenterX,   CenterY  ,Ssize/16,150,    x/16+Ssize/16       ,Color.cyan);
		moon4=new Planet(CenterX,   CenterY  ,Ssize/32,200,    x/32+Ssize/32       ,Color.white);
	}
	
	public void drawPlanets(double pointInTime,Graphics g)
	{
		sun.drawAtTime(pointInTime,g);
		earth.drawAtTime(pointInTime,g);
		
		int moonX = earth.Xcenter(pointInTime) ,moonY = earth.Ycenter(pointInTime);
		moon.setCenter(moonX,moonY);
		moon.drawAtTime(pointInTime,g);
		
		int moon2X = moon.Xcenter(pointInTime) ,moon2Y = moon.Ycenter(pointInTime);
		moon2.setCenter(moon2X,moon2Y);
		moon2.drawAtTime(pointInTime,g);
		
		int moon3X = moon2.Xcenter(pointInTime) ,moon3Y = moon2.Ycenter(pointInTime);
		moon3.setCenter(moon3X,moon3Y);
		moon3.drawAtTime(pointInTime,g);
		
		int moon4X = moon3.Xcenter(pointInTime) ,moon4Y = moon3.Ycenter(pointInTime);
		moon4.setCenter(moon4X,moon4Y);
		moon4.drawAtTime(pointInTime,g);
		
	}
	


}//class

class Planet
{
	int size;
	double speed;
	double orbitRadius,pointInTime;
	int beginXpix = 250,beginYpix = 250;
	int endXpix = 250,endYpix = 250;
	int ctrOfOrbitX, ctrOfOrbitY;
	Color planetColor;
	
	int    ApH = 500;
	int    ApW = 500;
	double xMin = -10;
	double xMax = 30;
	double yMin = -10;
	double yMax = 20;
	
	
	
	public Planet(int OrbitCtrX, int OrbitCtrY, int si,double sp,double Rad, Color colr)
	{
		ctrOfOrbitX = OrbitCtrX;
		ctrOfOrbitY = OrbitCtrY;
		size = si;//the height and width in pixels
		speed = sp;
		orbitRadius = Rad;
		planetColor = colr;
		setUpPlanet();
	}
	
	public void setUpPlanet()
	{
		
	}
	
	public void drawAtTime(double pit, Graphics g)
	{
		
		

		
		endXpix = Xcenter(pit) - size/2;
		endYpix = Ycenter(pit) - size/2;
				
		g.setColor(planetColor);
		g.fillOval(endXpix,endYpix,size,size);
	}

	
	public int Xcenter(double pointInTime)
	{
		return (int)(f_Time_X(pointInTime));
	}
	
	public int Ycenter(double pointInTime)
	{
		return (int)(f_Time_Y(pointInTime));
	}
	
	public void setCenter(int x, int y)
	{
		ctrOfOrbitX = x;
		ctrOfOrbitY = y;
	}
	
	public double polarFunction(double r)
	{
		return orbitRadius;
	}
	
	public double f_Time_X(double t)
	{
		return Math.cos(speed*t)*polarFunction(t) + ctrOfOrbitX;
	}
	
	public double f_Time_Y(double t)
	{
		return -Math.sin(speed*t)*polarFunction(t) + ctrOfOrbitY;
	}

	public int getXpixel(double xVal)
	{
		return (int)((xVal - xMin)*((double)ApW/(xMax - xMin)));
	}
	public int getYpixel(double yVal)
	{
		return ApH - (int)((yVal - yMin)*((double)ApH/(yMax - yMin)));
	}
}//Planet class