package GraphingCalculatorSupreme;
public class object3D
{
	polygon3D[] polygons;
	public object3D(){}
	public object3D(polygon3D[] P)
	{	polygons = P;	}
	public polygon3D[] getPolygons()
	{	return polygons;	}
	
	//for raytracing:
	public boolean intersectsRay(Ray R) //should be made abstract, for now is overridden by Plane3D
	{
		return false;
	}
}