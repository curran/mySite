import java.awt.*;
public class polygon3D
{
	point3D[] points;//x,y,z
	Color objColor,shadedObjColor;
	//for polygons only:
	point3D normal;
	point3D AvgPoint;
	public polygon3D(polygon3D O)
	{
		points = O.getPoint3DArray();
		objColor = O.getColor();
		shadedObjColor = O.getShadedObjColor();
		AvgPoint = O.getAvgPoint();
		normal = O.getNormal();
	}
	public polygon3D(point3D[] pnts, Color C)
	{
		points = pnts;
		objColor = C;
		//calculate Average Point
		double AvgPointX = 0;
		double AvgPointY = 0;
		double AvgPointZ = 0;
		
		for(int i=0;i<points.length;i++)
		{
			AvgPointX+=points[i].getX();
			AvgPointY+=points[i].getY();
			AvgPointZ+=points[i].getZ();
		}
		AvgPointX/=points.length;
		AvgPointY/=points.length;
		AvgPointZ/=points.length;
		
		AvgPoint = new point3D(AvgPointX,AvgPointY,AvgPointZ);
		
		//calculate Normal
		double nX,nY,nZ;
		point3D vectorA = new point3D(
									points[0].getX() - points[1].getX(),
									points[0].getY() - points[1].getY(),
									points[0].getZ() - points[1].getZ()
										);
		point3D vectorB = new point3D(
									points[2].getX() - points[1].getX(),
									points[2].getY() - points[1].getY(),
									points[2].getZ() - points[1].getZ()
										);
		
		nX = (vectorA.getY() * vectorB.getZ())-(vectorB.getY() * vectorA.getZ());
		nY = (vectorA.getZ() * vectorB.getX())-(vectorB.getZ() * vectorA.getX());
		nZ = (vectorA.getX() * vectorB.getY())-(vectorB.getX() * vectorA.getY());
		
		//double distance = 1;//Math.sqrt(nX*nX+nY*nY+nZ*nZ);
		normal = new point3D(nX,nY,nZ);
	}
	public point3D[] getPoint3DArray()
	{	return points;	}
	public point3D getAvgPoint()
	{	return AvgPoint;	}
	public double getAvgZ()//used for ZSorting
	{	return AvgPoint.getZ();	}
	public point3D getNormal()//used for shading
	{	return normal;	}
	public Color getColor()
	{	return objColor;	}
	public Color getShadedObjColor()
	{	return shadedObjColor;	}
	public void updateShadedObjColor()
	{
		double thetaX = Math.atan2(normal.getX(),normal.getZ());
		double thetaY = Math.atan2(normal.getY(),normal.getZ());
		double K = 10/Math.PI;
		double x = thetaX*K;
		double y = thetaY*K;
		//inputs from -10 to 10
		double sharpW0 = 50/(Math.pow(x-1,2)+Math.pow(y-1,2)+.3);
		double brightness0 = 4/(Math.pow(x-1,2)+Math.pow(y-1,2)+4);
		
		double sharpW1 = 50/(Math.pow(x-11,2)+Math.pow(y-11,2)+.3);
		double brightness1 = 4/(Math.pow(x-11,2)+Math.pow(y-11,2)+4);
		
		double sharpW2 = 50/(Math.pow(x+9,2)+Math.pow(y+9,2)+.3);
		double brightness2 = 4/(Math.pow(x+9,2)+Math.pow(y+9,2)+4);
		
		double sharpW3 = 50/(Math.pow(x+9,2)+Math.pow(y-11,2)+.3);
		double brightness3 = 4/(Math.pow(x+9,2)+Math.pow(y-11,2)+4);
		
		double sharpW4 = 50/(Math.pow(x-11,2)+Math.pow(y+9,2)+.3);
		double brightness4 = 4/(Math.pow(x-11,2)+Math.pow(y+9,2)+4);
		
		double sharpW = sharpW0 + sharpW1 + sharpW2 + sharpW3 + sharpW4 ;
		double brightness = brightness0 + brightness1 + brightness2 + brightness3 + brightness4;
		
		int Opaqueness = 255;// out of 255
		int[] initColorComponents = {objColor.getRed(),objColor.getGreen(),objColor.getBlue()};
		int[] colorComponents = new int[3];
		for(int i=0;i<3;i++)
		{
			colorComponents[i] = (int)((double)initColorComponents[i]*(brightness+.1)+(sharpW));
			if (colorComponents[i] >255)
				colorComponents[i] = 255;
			if (colorComponents[i] <0)
				colorComponents[i] = 0;
		}
		shadedObjColor = new Color(colorComponents[0],colorComponents[1],colorComponents[2]);
	}
}