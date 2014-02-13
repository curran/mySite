package GraphingCalculatorSupreme;
import java.awt.*;//for Color class
public class toolbox3D
{
	public toolbox3D(){}

	public static object3D makeRectangularPrism(point3D centerPoint,double Rw,double Rh,double Rd,double theta,double phi,Color PColor)
	{
		Color PrismColor = PColor;
		double Cx = centerPoint.getX();
		double Cy = centerPoint.getY();
		double Cz = centerPoint.getZ();
		double w=Rw/2.0,h=Rh/2.0,d=Rd/2.0;//all / 2
		
		polygon3D[] Faces = new polygon3D[6];
		point3D[] points = new point3D[8];
		double XPlus = Cx + w;
		double YPlus = Cy + d;
		double ZPlus = Cz + h;
		double XMins = Cx - w;
		double YMins = Cy - d;
		double ZMins = Cz - h;
		
		points[0] = new point3D(XMins,YPlus,ZPlus);
		points[1] = new point3D(XMins,YPlus,ZMins);
		points[2] = new point3D(XMins,YMins,ZPlus);
		points[3] = new point3D(XMins,YMins,ZMins);
		points[4] = new point3D(XPlus,YPlus,ZPlus);
		points[5] = new point3D(XPlus,YPlus,ZMins);
		points[6] = new point3D(XPlus,YMins,ZPlus);
		points[7] = new point3D(XPlus,YMins,ZMins);
		
		point3D[] face1points = {points[0],points[2],points[3],points[1]};
		Faces[0] = new polygon3D(face1points,PrismColor);
		
		point3D[] face2points = {points[2],points[6],points[7],points[3]};
		Faces[1] = new polygon3D(face2points,PrismColor);
		
		point3D[] face3points = {points[4],points[6],points[7],points[5]};
		Faces[2] = new polygon3D(face3points,PrismColor);
		
		point3D[] face4points = {points[0],points[4],points[5],points[1]};
		Faces[3] = new polygon3D(face4points,PrismColor);
		
		point3D[] face5points = {points[0],points[4],points[6],points[2]};
		Faces[4] = new polygon3D(face5points,PrismColor);
		
		point3D[] face6points = {points[1],points[5],points[7],points[3]};
		Faces[5] = new polygon3D(face6points,PrismColor);
		
		//double Xr,double Yr,double Xr
		if(theta!=0 || phi !=0)
		{
			rotationInstance ROTAT = new rotationInstance(theta,phi);
			polygon3D[] rotatedFaces = new polygon3D[6];
			for(int o = 0; o<Faces.length;o++)//for every face
			{
				point3D[] pnts = Faces[o].getPoint3DArray();
				point3D[] temppnts = new point3D[pnts.length];
				for(int p = 0; p<pnts.length;p++)//for every point of every polygon
				{
					ROTAT.setCenterOfRotation(centerPoint);
					temppnts[p] = ROTAT.getRotatedPoint3D(pnts[p]);//rotate each point of each polygon
				}
				rotatedFaces[o] = new polygon3D(temppnts,Faces[o].getColor());
			}
			Faces = rotatedFaces;
		}
		return new object3D(Faces);
	}
	
	public static double getDistFromOrigin(point3D P)
	{
		double x = P.getX();
		double y = P.getY();
		double z = P.getZ();
		
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public static point3D rotateAboutYequals(point3D P,double Yequals,double theta)
	{
		double X = P.getX(),Y = P.getY(),Z = P.getZ();
		
		double initAngle = Math.atan2(Z,Y-Yequals);
		double YZdist = Math.sqrt(Math.pow(Z,2)+Math.pow(Y-Yequals,2));
		double angle = initAngle + theta;
		Z=(YZdist*Math.sin(angle));
		Y=(YZdist*Math.cos(angle))+Yequals;
		
		return new point3D(X,Y,Z);
	}
	
	public static point3D rotateAboutXequals(point3D P,double Xequals,double theta)
	{
		double X = P.getX(),Y = P.getY(),Z = P.getZ();
		
		double initAngle = Math.atan2(Z,X-Xequals);
		double XZdist = Math.sqrt(Math.pow(Z,2)+Math.pow(X-Xequals,2));
		double angle = initAngle + theta;
		Z=(XZdist*Math.sin(angle));
		X=(XZdist*Math.cos(angle)+Xequals);
		
		return new point3D(X,Y,Z);
	}
	
	public static polygon3D getRotatedPolygon(rotationInstance ROTATION,polygon3D polygon)
	{
		/*polygon3D poly = new polygon3D(polygon);
		point3D[] pointS = poly.getPoint3DArray();
		polygon3D newpoly = new polygon3D();
		newpoly.points = new point3D[pointS.length];
		for(int p = 0; p<pointS.length;p++)//for every point of the polygon
		{
			newpoly.points[p] = ROTATION.getRotatedPoint3D(pointS[p]);//rotate each point of each polygon
		}
		
		newpoly.AvgPoint = ROTATION.getRotatedPoint3D(poly.getAvgPoint());
		newpoly.normal = ROTATION.getRotatedPoint3D(poly.getNormal());
		newpoly.objColor = poly.getColor();
		newpoly.shadedObjColor = poly.getShadedObjColor();
		return newpoly;*/
		
		point3D[] pointS = polygon.getPoint3DArray();
		polygon3D newpoly = new polygon3D(polygon);
		newpoly.points = new point3D[pointS.length];
		for(int p = 0; p<pointS.length;p++)//for every point of the polygon
		{
			newpoly.points[p] = ROTATION.getRotatedPoint3D(pointS[p]);//rotate each point of each polygon
		}
		
		newpoly.AvgPoint = ROTATION.getRotatedPoint3D(polygon.getAvgPoint());
		newpoly.normal = ROTATION.getRotatedPoint3D(polygon.getNormal());
		return newpoly;
	}
}