package GraphingCalculatorSupreme;
public abstract class Visitor
{
	public abstract void visitHost(host P);
	public void visitHost(GenericGraphPanel P){}
	public void visitHost(generic3DPanel P){}
}