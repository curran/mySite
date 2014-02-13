public class Parameters
{
	public static final int MODE_OUTPUTIMAGES = 0, MODE_RENDERONSCREEN = 1;
	
	public static int mode = MODE_RENDERONSCREEN;
	public static int sideCells = 40;//number of cells on one side of the grid
	public static int numIterations = 100;
	public static double bias = .35;//percent of cells that are initially on (==1,==white)
	
	private Parameters(){}
	public static double generateInitialCellState()
	{
		double s = Math.random()-1+bias;
		if(s<0)
			s = -1;
		else
			s = 1;
		return s;
	}
	
	public static double applyRule(Cell[] surroundingCells)
	{
		double futureState = 0;
		int temp = 0;
		for(int i=0;i<8;i++)
		{
			temp+=surroundingCells[i].getState();
			//System.out.println("State: "+surroundingCells[i].getState());
		}
		if(temp < 0)
			futureState = -1;
		else
			futureState = 1;
		//futureState = Math.atan(temp*9000)/Math.PI*2;
			
		return futureState;
	}
}