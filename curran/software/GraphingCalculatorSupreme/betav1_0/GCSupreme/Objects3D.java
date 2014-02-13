package GraphingCalculatorSupreme;
import java.awt.*;
public class Objects3D
{
	//factory class for creating simple 3D objects
	public object3D getPlane3D()
	{
		return new Plane3D(new vector3D(0,1,0),new point3D(1,1,1));
	}
	
	class Plane3D extends object3D
	{
		vector3D normal;
		point3D pointOnPlane;
		private Plane3D(vector3D norm,point3D pointontheplane)
		{
			super();
			normal = norm;
			pointOnPlane = pointontheplane;
			
			polygons = new polygon3D[1];
			point3D[] points = {
								new point3D(-10,-10,0),
								new point3D(-10,10,0),
								new point3D(10,10,0),
								new point3D(10,-10,0)
							};
			polygons[0] = new polygon3D(points,Color.blue);
		}
		
		public boolean intersectsRay(Ray R)
		{
			boolean doesIntersect = false;

			double X = pointOnPlane.getX();
			double Y = pointOnPlane.getY();
			double Z = pointOnPlane.getZ();
			
			double A = normal.getX();
			double B = normal.getY();
			double C = normal.getZ();

			double D = A*X+B*Y+C*Z;
			
			point3D Po = R.getOrigin();
			vector3D V = R.getDirectionVector();
			
			double x = Po.getX();
			double y = Po.getY();
			double z = Po.getZ();
			
			double a = V.getX();
			double b = V.getY();
			double c = V.getZ();

//			
//			                  D - (  A * x +  B * y +  C * z  ) 
//                     t =   ---------------------------------------
//                                  (  A * a +  B * b +  C * c  ) 
            double t = (D - (  A * x +  B * y +  C * z  ))/(  A * a +  B * b +  C * c  ) ;
			
			point3D intersection = (point3D)((new vector3D(Po)).add(V.scaleBy(t)));
			
			System.out.println("intersection"+intersection.getX());
			
			if(t>0)
				doesIntersect = true;
			
			return doesIntersect;
		}
	}
//	public object3D getSphere3D()
//	{
//		Plane3D newplane = new Plane3D(new vector3D(0,0,1),new point3D(0,0,0));
//		return newplane;
//	}
//	class Sphere3D
//{
//	public Sphere3D(point3D centr, double rad)
//	{}
//}
		
}