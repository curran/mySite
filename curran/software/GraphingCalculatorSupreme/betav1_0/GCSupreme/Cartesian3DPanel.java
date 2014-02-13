package GraphingCalculatorSupreme;
import java.awt.event.*;//for MouseListener etc
import java.awt.*;

public class Cartesian3DPanel extends generic3DPanel
{
	Function F = new Function("x");
	Color Fcolor = Color.green;
	Color AxesColor = new Color(150,150,150);
	int res=20;
	int RenderRes = 40;

    public Cartesian3DPanel(universe3D U)
	{
		super(U);
	}
	public void setFunction(Function Func, Color C)
	{
		F = Func;
		Fcolor = C;
		currentUniverse.clearObjects();
		currentUniverse.addObject(new cartesianFunctionObject3D(F,res,currentUniverse.screen,Fcolor));
		
		currentUniverse.addObjects(currentUniverse.screen.getAxesObjects(res,AxesColor));
		
		currentChangeinTheta = 0.004;
		currentChangeinPhi = 0.0;	
		globalPhi = Math.PI/2.0-.3;
		globalTheta = 0;
	}
	public void renderFunction()
	{
		currentUniverse.clearObjects();
		currentUniverse.setSwitches(universe3D.SWITCHES_RENDER);
		currentUniverse.addObject(new cartesianFunctionObject3D(F,RenderRes,currentUniverse.screen,Fcolor));
		currentChangeinTheta = 0;
		currentChangeinPhi = 0;	
	}
}