package GraphingCalculatorSupreme;
public class vector3D extends point3D
{
	//constructors
	public vector3D(double x,double y,double z)
	{
		super(x,y,z);
	}
	public vector3D(point3D p)
	{
		super(p);
	}
	
	
	//vector math
	public vector3D add(vector3D a)
	{
		return new vector3D(a.getX()+getX(),
							a.getY()+getY(),
							a.getZ()+getZ()
							);
	}
	public vector3D scaleBy(double a)
	{
		return new vector3D(a*getX(),
							a*getY(),
							a*getZ()
							);
	}
	
}