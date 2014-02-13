package GraphingCalculatorSupreme;
import javax.swing.*;
public class Function
{
	Expression FfromInput;
	public Function(String func)
	{
		try
		{
			FfromInput = new Expression(func,Expression.unknown);//generates the function tree here, and never agin
		}
		catch (Exception e)
		{
			FfromInput = new Expression("0",Expression.constant);
		}
	}
	public double Evaluate()
	{
		return FfromInput.Evaluate();
	}
	public double Evaluate(double x)
	{
		FfromInput.valueofVariableX = x;
		return FfromInput.Evaluate();
	}
	public double Evaluate(double x,double y)
	{
		FfromInput.valueofVariableX = x;
		FfromInput.valueofVariableY = y;
		return FfromInput.Evaluate();
	}
}