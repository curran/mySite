package GraphingCalculatorSupreme;
import java.awt.event.*;//for MouseListener etc
public class DiffEQPanel extends GenericGraphPanel implements MouseListener,MouseMotionListener
{
	public DiffEQPanel(window2D funcSpace)
	{
		super(funcSpace);
		turnOnAntiAlias();
		addMouseListener (this);
		addMouseMotionListener (this);
	}
	public void setFunction(Function func)
	{
		bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
		Toolbox.setFunction(func);
		Toolbox.drawAxes();
		Toolbox.setSlopeField();
		Toolbox.drawSlopeField(true);
		repaint();
	}
	public void drawParticularSolution(double x,double y)
	{
		//bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
		//Toolbox.drawAxes(bufGraphics);
		//Toolbox.drawSlopeField(bufGraphics,false);
		Toolbox.drawParticularSolution(x,y);
		repaint();
	}
	public void mousePressed(MouseEvent e)
	{
		turnOffAntiAlias();
		mouseDragged(e);
	}
	public void mouseDragged(MouseEvent e)
	{
		//turnOffAntiAlias();
		bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
		Toolbox.drawAxes();
		Toolbox.drawSlopeField(false);
		Toolbox.drawParticularSolution(functionSpace.getXvalue(e.getX()),functionSpace.getYvalue(e.getY()));
		repaint();
	}
	public void mouseReleased(MouseEvent e)
	{
		turnOnAntiAlias();
		mouseDragged(e);
	}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event){}
	public void mouseMoved(MouseEvent event) {}
	public void mouseClicked(MouseEvent event){repaint();}
}