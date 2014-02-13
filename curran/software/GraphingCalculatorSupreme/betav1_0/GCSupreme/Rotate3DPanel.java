package GraphingCalculatorSupreme;
import java.awt.event.*;//for MouseListener etc
import java.awt.*;

public class Rotate3DPanel extends generic3DPanel
{
	Function F = new Function("x");
	Color Fcolor = Color.green;
	Color AxesColor = new Color(150,150,150);
	int res=settings.getRes();
	boolean Function2Dset = false;

    public Rotate3DPanel(universe3D U)
	{
		super(U);
	}
	public void setFunction(Function Func, Color C,String Ax,double AxisEq,double Dm,double dM)
	{
		F = Func;
		if(!Function2Dset)//to keep the color of the displayed function
			Fcolor = C;
			
		currentUniverse.clearObjects();
		
		currentUniverse.addObject(new rotationFunctionObject3D(F,res,Fcolor,Ax,AxisEq,Dm,dM));
		
		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
		
		currentChangeinTheta = 0;
		currentChangeinPhi = -0.004;
		globalPhi = 2*Math.PI;
		globalTheta = 3.0*Math.PI/2.0;
		
		Function2Dset = false;
	}
	public void set2DFunction(Function Func, Color C,double Dm,double dM)
	{
		F = Func;
		Fcolor = C;
		currentUniverse.clearObjects();
		
		currentUniverse.addObject(new FunctionObject3D(F,res,Fcolor,Dm,dM));
		
		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
		
		currentChangeinTheta = 0;
		currentChangeinPhi = 0;	
		globalPhi = 2*Math.PI;
		globalTheta = 3.0*Math.PI/2.0;
		
		Function2Dset = true;
	}
}