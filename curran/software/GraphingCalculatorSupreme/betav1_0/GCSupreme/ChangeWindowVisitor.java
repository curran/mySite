package GraphingCalculatorSupreme;
public class ChangeWindowVisitor extends Visitor
{
	final int setWindowMaxMin = 0;
	final int setWindowWidthHeight = 1;
	
	
	int w,h,type;
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	public ChangeWindowVisitor(double Xm, double xM, double Ym, double yM)
	{
		xMin = Xm;
		xMax = xM;
		yMin = Ym;
		yMax = yM;
		type = setWindowMaxMin;
	}
	
	public ChangeWindowVisitor(int W,int H)
	{
		w = W;
		h = H;
		type = setWindowWidthHeight;
	}
	
	public void visitHost(host P)
	{}
	
	public void visitHost(GenericGraphPanel P)
	{
		switch(type)
		{
			case setWindowWidthHeight:
				if(P.bufGraphics != null)
				{
					P.bufGraphics.clearRect(0,0,P.functionSpace.getWidth(),P.functionSpace.getHeight());
					P.Toolbox.functionSpace.width = w;
					P.Toolbox.functionSpace.height = h;
					P.Toolbox.refresh();
					P.repaint();
				}
				break;
			case setWindowMaxMin:
				if(P.bufGraphics != null)
				{
					P.bufGraphics.clearRect(0,0,P.functionSpace.getWidth(),P.functionSpace.getHeight());
					P.Toolbox.functionSpace.set(xMin,xMax,yMin,yMax);
					P.Toolbox.refresh();
					P.repaint();
				}
				break;
				
		}
			
	}
	public void visitHost(generic3DPanel P)
	{
		switch(type)
		{
			case setWindowWidthHeight:
				P.currentUniverse.screen.width = w;
				P.currentUniverse.screen.height = h;
				P.repaint();
				break;
		}
				
	}
	
	
}