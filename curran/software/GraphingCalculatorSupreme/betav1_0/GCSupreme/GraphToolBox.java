package GraphingCalculatorSupreme;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import sun.dc.pr.PRException;
public class GraphToolBox
{
	window2D functionSpace;
	Function persistantFunction = new Function("0");
	Graphics2D persistantGraphics;
	boolean AxesDrawn = false;
	boolean DerivDrawn = false;
	boolean SlopeFieldDrawn = false;
		boolean SlopeFieldPersistantFullColor = false;
	boolean ParticularSolutionDrawn = false;
		double[] ParticularSolutionPoint = new double[2];
	boolean parametricEQDrawn = false;
	
	boolean[] RiemannSumDrawn = {false,false,false};
	static final int             RRS=0,LRS=1,TRS=2;
	double persistantRS_a;
	double persistantRS_b;
	int    persistantRS_n;
	
	//T variables used as parametric graph window
	double Tstart = 0;
	double Tend = Math.PI*20;
	double Tstep = 0.01;
	
	Function[] persistantParametricEquation = new Function[2];
	
	Rectangle2D.Float screenRectangle;
	
	public GraphToolBox(window2D funcSpace,Graphics2D g)
	{
		functionSpace = funcSpace;
		persistantGraphics = g;
		screenRectangle = new Rectangle2D.Float(0,0,(float)functionSpace.width, (float)functionSpace.height);
	}
	
	
	
	public void refresh()
	{
		try
		{
			drawFunction();
			if(AxesDrawn)
				drawAxes();
			if(DerivDrawn)
				drawDerivative();
			for(int i=0; i<3;i++)
				if(RiemannSumDrawn[i])
					doRiemannSum(persistantRS_a,persistantRS_b,persistantRS_n,i);
			if(SlopeFieldDrawn)
			{
				setSlopeField();
				drawSlopeField(SlopeFieldPersistantFullColor);
			}
			if(ParticularSolutionDrawn)
				 drawParticularSolution(ParticularSolutionPoint[0],ParticularSolutionPoint[1]);
				 //only draws it if it the point is on the screen.
			if(parametricEQDrawn)
				drawParametricEquation();
		}
		catch (Exception E){}
	}

	public void drawAxes()
	{
		persistantGraphics.setColor(settings.getAxesColor());
		GdrawLine(functionSpace.getXpixel(0),functionSpace.getYpixel(functionSpace.yMin),functionSpace.getXpixel(0),functionSpace.getYpixel(functionSpace.yMax));
		GdrawLine(functionSpace.getXpixel(functionSpace.xMin),functionSpace.getYpixel(0),functionSpace.getXpixel(functionSpace.xMax),functionSpace.getYpixel(0));
		AxesDrawn = true;
	}
	public void setGraphics(Graphics2D g)
	{
		persistantGraphics = g;
	}
	public void setFunction(Function f)
	{
		persistantFunction = f;
		clearPersistantThings();
	}
	public void drawFunction()
	{
		persistantGraphics.setColor(settings.getGraphColor());
		int OldY = functionSpace.getYpixel(persistantFunction.Evaluate(functionSpace.getXvalue(0)));
		for(int X = 1; X<functionSpace.getWidth()-1; X++)//draw the grPANEL_HEIGHT
		{
			int NewY = functionSpace.getYpixel(persistantFunction.Evaluate(functionSpace.getXvalue(X)));
			
			GdrawLine(X-1, OldY, X, NewY);
			OldY = NewY;
		}
	}
	public void setParametricEquation(Function Fx, Function Fy)
	{
		clearPersistantThings();
		persistantParametricEquation[0] = Fx;
		persistantParametricEquation[1] = Fy;
	}
	public void drawParametricEquation()
	{
		persistantGraphics.setColor(settings.getGraphColor());
		
		int OldX = functionSpace.getXpixel(persistantParametricEquation[0].Evaluate(Tstart));
		int OldY = functionSpace.getYpixel(persistantParametricEquation[1].Evaluate(Tstart));
		int X = 0;
		int Y = 0;
		
		boolean go = true;

		for(double t = Tstart; (t-Tstep<= Tend)&& go; t+=Tstep)
		{
			if(t > Tend)
			{
				t = Tend;
				go = false;
			}
			
			X = functionSpace.getXpixel(persistantParametricEquation[0].Evaluate(t));
			Y = functionSpace.getYpixel(persistantParametricEquation[1].Evaluate(t));
			
			GdrawLine(OldX, OldY, X, Y);
			OldX = X;
			OldY = Y;
		}
		parametricEQDrawn = true;
	}
	public void drawDerivative()
	{
		persistantGraphics.setColor(settings.getDerivativeColor());
		double onePixelXValue = functionSpace.getXvalue(3) - functionSpace.getXvalue(2);
		double[] funcYPixArray= new double[functionSpace.getWidth()];
		for(int X = 0; X<functionSpace.getWidth(); X++)
		{
			funcYPixArray[X] = persistantFunction.Evaluate(functionSpace.getXvalue(X));
		}
		
		for(int X = 1; X<functionSpace.getWidth()-1; X++)
		{
			GdrawLine(X-1, functionSpace.getYpixel((funcYPixArray[X]-funcYPixArray[X-1])/onePixelXValue), X, functionSpace.getYpixel((funcYPixArray[X+1]-funcYPixArray[X])/onePixelXValue));
		}
		DerivDrawn = true;
	}//draw derivative
	public String[] doRiemannSum(double a, double b, int n,int type)
	{
		Color RColor = new Color(0,0,200,100);
		Color LColor = new Color(0,200,0,100);
		Color TColor = new Color(200,0,0,100);
		double w = (b-a)/(double)n;
		double[] Xvalues = new double[n+1];
		double[] Yvalues = new double[n+1];
		int[] XpixValues = new int[n+1];
		int[] YpixValues = new int[n+1];
		int[] PolygonXpixValues = new int[5];
		int[] PolygonYpixValues = new int[5];
		boolean isBiggerThanPixel = true; //do not draw border if the rectangle space is smaller than one pixel
		if(w<(functionSpace.xMax - functionSpace.xMin)/functionSpace.getWidth()) //w > 1 pixel
			isBiggerThanPixel = false;
		double approxArea = 0;
		for(int x = 0; x<= n; x++)
		{
			Xvalues[x] = a+w*x;
			Yvalues[x] = persistantFunction.Evaluate(Xvalues[x]);
			XpixValues[x] = functionSpace.getXpixel(Xvalues[x]);
			YpixValues[x] = functionSpace.getYpixel(Yvalues[x]);
		}
		int zeroYpix = functionSpace.getYpixel(0);
		for(int i = 0; i< n; i++)
		{
			PolygonYpixValues[1] = PolygonYpixValues[2] = YpixValues[i];
			switch(type)
			{
				case RRS:
					PolygonYpixValues[1] = PolygonYpixValues[2] = YpixValues[i];
					approxArea += Math.abs(Yvalues[i]);
					persistantGraphics.setColor(RColor);
					break;
				case LRS:
					PolygonYpixValues[1] = PolygonYpixValues[2] = YpixValues[i+1];
					approxArea += Math.abs(Yvalues[i+1]);
					persistantGraphics.setColor(LColor);
					break;
				case TRS:
					PolygonYpixValues[1] = YpixValues[i];
					PolygonYpixValues[2] = YpixValues[i+1];
					approxArea += Math.abs((Yvalues[i]+Yvalues[i+1])/2.0);
					persistantGraphics.setColor(TColor);
					break;
			}
			
			PolygonYpixValues[0] = PolygonYpixValues[4] = PolygonYpixValues[3] = zeroYpix;
			PolygonXpixValues[0] = PolygonXpixValues[4] = PolygonXpixValues[1] = XpixValues[i];
			PolygonXpixValues[2] = PolygonXpixValues[3] = XpixValues[i+1];
			persistantGraphics.fillPolygon(PolygonXpixValues,PolygonYpixValues,5);
			persistantGraphics.setColor(Color.black);
			if(isBiggerThanPixel)
				persistantGraphics.drawPolyline(PolygonXpixValues,PolygonYpixValues,5);
		}
		
		approxArea *= w;
		String informativeString = "";
		switch(type)
		{
			case RRS:
				informativeString = "Right Riemann Sum: ";
				break;
			case LRS:
				informativeString = "Left Riemann Sum:  ";
				break;
			case TRS:
				informativeString = "Trapezoidal Sum:   ";
				break;
		}
		String[] results = {informativeString,approxArea+""};
		
		RiemannSumDrawn[type] = true;
		persistantRS_a = a;
		persistantRS_b = b;
		persistantRS_n = n;
		
		return results;
	}
	int[] SlopeFieldPoints;
	int cartesianResX = 30;
	int cartesianResY = 30;
	public void setSlopeField()
	{
		
		double xVal=0,yVal=0,L = (functionSpace.xMax-functionSpace.xMin)/cartesianResX;
		SlopeFieldPoints = new int[cartesianResX*cartesianResY*4];
		for(int x = 0; x<cartesianResX;x++)
		{
			for(int y = 0;y<cartesianResY;y++)
			{
				xVal = functionSpace.xMin+((double)x/cartesianResX)*(functionSpace.xMax-functionSpace.xMin);
				yVal = functionSpace.yMin+((double)y/cartesianResY)*(functionSpace.yMax-functionSpace.yMin);

				double theta = Math.atan2(persistantFunction.Evaluate(xVal,yVal),1);
				double length = L/2;
				double deltaX = Math.cos(theta)*length;
				double deltaY = Math.sin(theta)*length;
				
				SlopeFieldPoints[y*4*cartesianResX+4*x] = functionSpace.getXpixel(xVal+deltaX);
				SlopeFieldPoints[y*4*cartesianResX+4*x+1] = functionSpace.getYpixel(yVal+deltaY);
				SlopeFieldPoints[y*4*cartesianResX+4*x+2] = functionSpace.getXpixel(xVal-deltaX);
				SlopeFieldPoints[y*4*cartesianResX+4*x+3] = functionSpace.getYpixel(yVal-deltaY);
			}
		}
	}
	public void drawSlopeField(boolean fullColor)
	{
		
		if(fullColor)
			persistantGraphics.setColor(settings.getGraphColor());
		else
			persistantGraphics.setColor(settings.getSlopeFieldColor());
		for(int x = 0; x<cartesianResX;x++)
		{
			for(int y = 0;y<cartesianResY;y++)
			{
				GdrawLine(SlopeFieldPoints[y*4*cartesianResX+4*x],
							SlopeFieldPoints[y*4*cartesianResX+4*x+1],
							SlopeFieldPoints[y*4*cartesianResX+4*x+2],
							SlopeFieldPoints[y*4*cartesianResX+4*x+3]);
			}
		}
		SlopeFieldDrawn = true;
		SlopeFieldPersistantFullColor = fullColor;
	}//slopefield
	
	public void drawParticularSolution(double initX, double initY)
	{
		persistantGraphics.setColor(settings.getIntegralColor());
		double Length = (functionSpace.getXvalue(1)-functionSpace.getXvalue(0));//= one pixel
		double theta,deltaX,deltaY,finalX=0,finalY=0,slope;
		double tempX = initX;
		double tempY = initY;
		
		for(int i = 0;i<2;i++)//0 = forwards and 1 = backwards
		{
			while(tempX >= functionSpace.xMin && tempX<=functionSpace.xMax && tempY >= functionSpace.yMin-100 && tempY<=functionSpace.yMax+100)
			{
				slope = persistantFunction.Evaluate(tempX,tempY);
				theta = Math.atan2(slope,1);
				deltaX = (Math.cos(theta)*Length);
				deltaY = (Math.sin(theta)*Length);
				if(i==0)
				{
					finalX = tempX+deltaX;
					finalY = tempY+deltaY;
				}
				else if(i==1)
				{
					finalX = tempX-deltaX;
					finalY = tempY-deltaY;
				}
				GdrawLine(functionSpace.getXpixel(tempX),functionSpace.getYpixel(tempY),functionSpace.getXpixel(finalX),functionSpace.getYpixel(finalY));
				tempX = finalX;
				tempY= finalY;
			}//while
			tempX = initX;
			tempY = initY;
		}//for 2
		//draw the point through which the solution is through
		int CircleDiamInPixels = 4;
		
		persistantGraphics.setColor(settings.getGraphColor());
		GfillOval(functionSpace.getXpixel(initX)-CircleDiamInPixels,functionSpace.getYpixel(initY)-CircleDiamInPixels,2*CircleDiamInPixels,2*CircleDiamInPixels);
		ParticularSolutionDrawn = true;
		ParticularSolutionPoint[0] = initX;
		ParticularSolutionPoint[1] = initY;
	}
	public void GdrawLine(int a, int b, int c, int d)//don't draw if it is off the screen
	{
		Line2D.Double line = new Line2D.Double  (a,b,c,d);
		if(line.intersects(screenRectangle))
		{
			try 
			{
				persistantGraphics.draw(line); 
			}
			catch (Exception e){System.out.println(a+", "+b+", "+c+", "+d+", ");}
		}
	}
	public void GfillOval(int a, int b, int c, int d)
	{
		if(screenRectangle.contains(a,b))
			persistantGraphics.fillOval(a,b,c,d);
	}
	public void clearPersistantThings()
	{
		AxesDrawn = false;
		DerivDrawn = false;
		RiemannSumDrawn[0]=RiemannSumDrawn[1]=RiemannSumDrawn[2]=false;
		SlopeFieldDrawn = false;
		ParticularSolutionDrawn = false;
		parametricEQDrawn = false;
	}
}