package GraphingCalculatorSupreme;
public class Ray
{
	point3D origin;
	vector3D directionVector;
	
	public Ray(point3D or,vector3D dir)
	{
		origin = or;
		directionVector = dir;
		
		// line = P = Po + t * V
	}
	public point3D getOrigin()
	{	return origin;	}
	
	public vector3D getDirectionVector()
	{	return directionVector;	}
	
}