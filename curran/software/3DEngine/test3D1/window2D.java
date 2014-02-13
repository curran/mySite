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
	public static window2D getDefaultWindow(int w,int h)
	{	return new window2D(w,h,-10,10,-10,10);	}
	public int getXpixel(double xVal)
	{	return (int)((xVal - xMin)*((double)width/(xMax - xMin)));	}
	public int getYpixel(double yVal)
	{	return height - (int)(((yVal - yMin)*((double)height/(yMax - yMin))));	}
	public double getXvalue(double xPix)
	{	return((double)xPix*((double)(xMax - xMin)/width))+xMin;	}
	public double getYvalue(double yPix)
	{	return((double)yPix*((double)(yMax - yMin)/height))+yMin;	}
}