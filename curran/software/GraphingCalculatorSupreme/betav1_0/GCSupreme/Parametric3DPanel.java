package GraphingCalculatorSupreme;
import java.awt.event.*;//for MouseListener etc
import java.awt.*;

public class Parametric3DPanel extends generic3DPanel
{
	Function F = new Function("x");
	Color Fcolor = Color.green;
	Color AxesColor = new Color(150,150,150);
	int res=2;
	int RenderRes = 40;

    public Parametric3DPanel(universe3D U)
	{
		super(U);
	}
//	public void setFunction(Function Func, Color C)
//	{
//		F = Func;
//		Fcolor = C;
//		currentUniverse.clearObjects();
//		currentUniverse.addObject(new cartesianFunctionObject3D(F,res,currentUniverse.screen,Fcolor));
//		
//		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
//		
//		currentChangeinTheta = 0.004;
//		currentChangeinPhi = 0.0;	
//		globalPhi = Math.PI/2.0-.3;
//		globalTheta = 0;
//	}
	
	public void set3DEquation(Function Xf,Function Yf,Function Zf, Color C)
	{
		currentUniverse.clearObjects();
		currentUniverse.addObject(new parametric3DFunctionObject3D(Xf,Yf,Zf,currentUniverse.screen,Fcolor));
		
		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
		
		currentChangeinTheta = 0.004;
		currentChangeinPhi = 0.0;	
		globalPhi = Math.PI/2.0-.3;
		globalTheta = 0;
	}
}

class parametric3DFunctionObject3D extends object3D
{
	window3D graphSpace;
	Function XofT,YofT,ZofT;
	Color funcColor;
	public parametric3DFunctionObject3D(Function Xf,Function Yf,Function Zf,window3D wind,Color Colr)
	{
		super(new polygon3D[0]);
		XofT = Xf;
		YofT = Yf;
		ZofT = Zf;
		
		graphSpace = wind;
		funcColor = Colr;
		setPolygons();
	}
	public void setPolygons()
	{
		double Tstart = 0;
		double Tend = 2*Math.PI;
		double Tstep = 0.04;
		
		double OldX = 0;
		double OldY = 0;
		double OldZ = 0;
		double X = 0;
		double Y = 0;
		double Z = 0;
		
		polygon3D[] P3DLines;
		int lineCount = 0;
		
		for(double t = Tstart; t<= Tend; t+=Tstep)//count
		{
			lineCount++;
		}
		
		P3DLines = new polygon3D[lineCount];
		int i=0;
		for(double t = Tstart-Tstep; t<= Tend; t+=Tstep)
		{
			X = XofT.Evaluate(t);
			Y = YofT.Evaluate(t);
			Z = ZofT.Evaluate(t);
			
			if(t>=Tstart)
			{
				point3D[] Xpoints = {new point3D(OldX,OldY,OldZ),new point3D(X,Y,Z)};
			
				P3DLines[i] = new polygon3D(Xpoints,funcColor);
				
				i++;
			}
			
			OldX = X;
			OldY = Y;
			OldZ = Z;
			
			
		
		}
		polygons = P3DLines;
	}//setPolygons
}