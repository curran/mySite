package GraphingCalculatorSupreme;
public class MakeWindowSquareVisitor extends Visitor
{
	public void visitHost(host P){System.out.println("nothing");}
	public void visitHost(GenericGraphPanel P)
	{
		if(P.bufGraphics != null)
		{
			P.bufGraphics.clearRect(0,0,P.functionSpace.getWidth(),P.functionSpace.getHeight());
			P.Toolbox.functionSpace.makeWindowSquare();
			P.Toolbox.refresh();
			P.repaint();
		}
	}
	
	public void visitHost(generic3DPanel P)
	{
		if(P.bufGraphics != null)
		{
			P.bufGraphics.clearRect(0,0,P.currentUniverse.screen.getWidth(),P.currentUniverse.screen.getHeight());
			P.currentUniverse.screen.makeWindowSquare();
			P.repaint();
		}
		else
		{
			P.currentUniverse.screen.makeWindowSquare();
		}
	}
}