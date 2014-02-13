package GraphingCalculatorSupreme;
import java.awt.*;//for Color
class window3D extends window2D
{
	double xMinW3D; //used new naming to separate 3D window from screen 2D window
	double xMaxW3D;
	double yMinW3D;
	double yMaxW3D;
	double zMinW3D;
	double zMaxW3D;
	
	public window3D(int w, int h, double Xm, double xM, double Ym, double yM,double Zm, double zM)
	{
		super(w,h);

		xMinW3D = Xm;
		xMaxW3D = xM;
		yMinW3D = Ym;
		yMaxW3D = yM;
		zMinW3D = Zm;
		zMaxW3D = zM;
	}
	public static window3D getDefaultWindow3D()
	{	return new window3D(0,0,-10,10,-10,10,-10,10);	}
	public static window3D getDefaultWindow3D(int w, int h)
	{	return new window3D(w,h,-10,10,-10,10,-10,10);	}

	public object3D[] getAxesObjects(int res,Color axesColor)
	{
		object3D[] axesObjects = new object3D[4];
		point3D xMin3D,xMax3D,yMin3D,yMax3D,zMin3D,zMax3D;
		
		polygon3D[] XaxesPolygons = new polygon3D[res];
		polygon3D[] YaxesPolygons = new polygon3D[res];
		polygon3D[] ZaxesPolygons = new polygon3D[res];
		
		for(int i = 0; i<res;i++)
		{
			xMin3D = new point3D(xMinW3D+((double)(xMaxW3D-xMinW3D)*((double)i/res)),0,0);
			xMax3D = new point3D(xMinW3D+((double)(xMaxW3D-xMinW3D)*((double)(i+1)/res)),0,0);
			yMin3D = new point3D(0,yMinW3D+((double)(yMaxW3D-yMinW3D)*((double)i/res)),0);
			yMax3D = new point3D(0,yMinW3D+((double)(yMaxW3D-yMinW3D)*((double)(i+1)/res)),0);
			zMin3D = new point3D(0,0,zMinW3D+((double)(zMaxW3D-zMinW3D)*((double)i/res)));
			zMax3D = new point3D(0,0,zMinW3D+((double)(zMaxW3D-zMinW3D)*((double)(i+1)/res)));
	
			
			point3D[] Xpoints = {xMin3D,xMax3D};
			point3D[] Ypoints = {yMin3D,yMax3D};
			point3D[] Zpoints = {zMin3D,zMax3D};
			
			XaxesPolygons[i] = new polygon3D(Xpoints,axesColor);
			YaxesPolygons[i] = new polygon3D(Ypoints,axesColor);
			ZaxesPolygons[i] = new polygon3D(Zpoints,axesColor);
		}
		
		axesObjects[0] = new object3D(XaxesPolygons);
		axesObjects[1] = new object3D(YaxesPolygons);
		axesObjects[2] = new object3D(ZaxesPolygons);
			
		double LabelDist = .5;
		point3D[] xMax3Da = {new point3D(xMaxW3D+LabelDist,0,0)};
		point3D[] yMax3Da = {new point3D(0,yMaxW3D+LabelDist,0)};
		point3D[] zMax3Da = {new point3D(0,0,zMaxW3D+LabelDist)};
		
		polygon3D[] AxisLabels =
		{
			new polygon3D("X",xMax3Da,axesColor),
			new polygon3D("Y",yMax3Da,axesColor),
			new polygon3D("Z",zMax3Da,axesColor)
		};
		axesObjects[3] = new object3D(AxisLabels);

		return axesObjects;

	}
}
	