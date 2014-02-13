package GraphingCalculatorSupreme;
public class window2D
{
	int    height;//height in pixels of the window
	int    width;
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	public window2D(int w, int h, double Xm, double xM, double Ym, double yM)
	{
		height = h;
		width = w;
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
	}
	public window2D(int w, int h)//default window
	{
		height = h;
		width = w;
		double[] defatltWindowArray = settings.getDefaultWindowDimArray();
		
		
		
		xMin = defatltWindowArray[0];
		xMax = defatltWindowArray[1];
		yMin = defatltWindowArray[2];
		yMax = defatltWindowArray[3];
	}
	public void set(double Xm, double xM, double Ym, double yM)
	{
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
	}
	public void makeWindowSquare()
	{
		if(((double)(height)/(yMax - yMin))<((double)(width)/(xMax - xMin)))
		{
			double newWindowWidth = (((yMax - yMin)*(double)width)/(double)(height));///2.0;
			double Xdiff = (newWindowWidth-(xMax - xMin))/2.0;
			xMax += Xdiff;
			xMin -= Xdiff;
		}
		else
		{
			double newWindowHeight = (((xMax - xMin)*(double)height)/(double)(width));///2.0;
			double Ydiff = (newWindowHeight-(yMax - yMin))/2.0;
			yMax += Ydiff;
			yMin -= Ydiff;
		}
		
		
		
		
//		xMin = Xm;
//		xMax = xM;
//		yMin = Ym;
//		yMax = yM;
	}
	public window2D get()
	{	return this;	}
	public static window2D getDefaultWindow(int w,int h)
	{	return new window2D(w,h,-10,10,-10,10);	}
	public int getWidth()
	{	return width;	}
	public int getHeight()
	{	return height;	}
	
	double FudgeFactor = 0.99; //for some headroom, otherwise, values at the window minimums will go off the paintable screen
	
	public int getXpixel(double xVal)
	{	
		xVal = xVal*FudgeFactor;
		return (int)((xVal - xMin)*((double)(width)/(xMax - xMin)));
	}
	public int getYpixel(double yVal)
	{	
		yVal = yVal*FudgeFactor;
		return height - (int)(((yVal - yMin)*((double)(height)/(yMax - yMin))));
	}
	public double getXvalue(double xPix)
	{	return (((double)xPix*((double)(xMax - xMin)/width))+xMin)/FudgeFactor;	}
	public double getYvalue(double yPix)
	{	return (((height-yPix)/(double)height)*(yMax - yMin)+yMin)/FudgeFactor;	}
}
