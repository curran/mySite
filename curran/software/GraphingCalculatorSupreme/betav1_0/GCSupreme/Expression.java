package GraphingCalculatorSupreme;
class Expression
{
	public static final int oneSided = 0,constant = 1, nested = 2, unknown = 3;
	public static final int plus = 4, minus = 5, dividedBy = 6, times = 7, exp = 8;
	public static final int           sin=0,cos=1,tan=2,sqrt=3,ln=4,abs=5;
	//									  0     1     2      3    4     5
	public static final String[] ops = {"sin","cos","tan","sqrt","ln","abs"};
	private int typeOfExpression,oneSidedOpType,operand;
	private double valueofConstant;
	
	public static double valueofVariableX,valueofVariableY;
	private char Variable;
	
	private boolean ConstantIsVariable = false;
	private Expression leftSide, rightSide,insideFunc;
	
	private ExpressionStringOperator ThisFuncOperator = new ExpressionStringOperator();
					
	public Expression(String F,int type)
	{
		typeOfExpression = type;
		boolean go = true;
		while(go)
		{
			switch (typeOfExpression)
			{
				case unknown:
				{
					F = ThisFuncOperator.preformat(F);
					typeOfExpression = ThisFuncOperator.determineType(F);
					break;
				}
				case oneSided:
				{
					//find type of operation
					for(int i = 0; i<ops.length;i++)
					{
						if(F.indexOf(ops[i]) != -1)//if it is there
						{
							oneSidedOpType = i;
							i = ops.length;
						}
					}
					
					char[] ExpressionStringC = F.toCharArray();
					int B = F.indexOf("(");
					int Plevel = 1,E=B;
					while(Plevel != 0)
					{
						E++;
						if(ExpressionStringC[E] == '(')
							Plevel++;
						if(ExpressionStringC[E] == ')')
							Plevel--;
					}
					String InsideString = F.substring(B+1,E);
					d.println("oneSided: "+InsideString);
					insideFunc = new Expression(InsideString,unknown);
					//d.println("oneSidedOpType "+oneSidedOpType);
					go = false;
					break;
				}
				case constant:
				{
					d.println("F"+F);
					try
					{
						valueofConstant = Double.parseDouble(F);
					}
					catch(Exception E)
					{
						if(F.equals("x"))
						{
							ConstantIsVariable = true;
							Variable = 'x';
						}
						else if(F.equals("y"))
						{
							ConstantIsVariable = true;
							Variable = 'y';
						}
						else if(F.equals("pi"))
							valueofConstant = 3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117068;
						else if(F.equals("e"))
							valueofConstant = 2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525166427;
						else if(F.equals("infinity"))
							valueofConstant = Double.parseDouble("Infinity");
					}
					go = false;
					break;
				}
				case nested:
				{
					ThisFuncOperator.probe(F);
					leftSide = new Expression(ThisFuncOperator.getleftSide(),unknown);
					operand = ThisFuncOperator.getOperand();
					rightSide = new Expression(ThisFuncOperator.getrightSide(),unknown);
					go = false;
					break;
				}
			}
		}
	}
	public double Evaluate()
	{
		double result = 0;
		switch (typeOfExpression)
		{
			case constant:
			{
				if(ConstantIsVariable)
				{
					if(Variable == 'x')
						result = Expression.valueofVariableX;
					else if(Variable == 'y')
						result = Expression.valueofVariableY;
				}
				else
					result = valueofConstant;
				break;
			}
			case nested:
			{
				double L = leftSide.Evaluate();
				double R = rightSide.Evaluate();
				switch(operand)
				{
					case plus:
						result = L+R;
						break;
					case minus:
						result = L-R;
						break;
					case dividedBy:
						result = L/R;
						break;
					case times:
						result = L*R;
						break;
					case exp:
						result = Math.pow(L,R);
						break;
					
				}//operand switch
				break;
			}//nested case
			case oneSided:
			{
				double insideNum = insideFunc.Evaluate();
				switch(oneSidedOpType)
				{
					case sin:
						result = Math.sin(insideNum);
						break;
					case cos:
						result = Math.cos(insideNum);
						break;
					case tan:
						result = Math.tan(insideNum);
						break;
					case sqrt:
						result = Math.sqrt(insideNum);
						break;
					case ln:
						result = Math.log(insideNum);
						break;
					case abs:
						result = Math.abs(insideNum);
						break;
				}
			}		
		}
		d.println(result);
		return result;
	}
}
class ExpressionStringOperator
{
	String LeftSideStr, RightSideStr;
	int operandType;
	public ExpressionStringOperator(){}

	public void probe(String s)
	{
		String ExStr = removeOutsideParentheses(s);
		d.println("ExStr "+ExStr);
		
		String ExStr_blocked = blockStuffInParentheses(ExStr);
		d.println("ExStr_blocked "+ExStr_blocked);

		char[] ExStr_blockedC = ExStr_blocked.toCharArray();

		d.println("probing "+ExStr);
		d.println("        "+ExStr_blocked);
		int indexOfOperand = 0;
		char OperandChar;
		//for(int i = 0; i<ExpressionString.length();i++)
		for(int i = ExStr_blockedC.length-1; i>=0;i--)
		{
			OperandChar = ExStr_blockedC[i];
			switch(OperandChar)
			{
				case '+':
					operandType = Expression.plus;
					indexOfOperand = i;
					i = -1;//ExpressionString.length();
					break;
				case '-':
					operandType = Expression.minus;
					indexOfOperand = i;
					i = -1;//ExpressionString.length();
					break;
				case '/':
					operandType = Expression.dividedBy;
					indexOfOperand = i;
					i = -1;//ExpressionString.length();
					break;
				case '*':
					operandType = Expression.times;
					indexOfOperand = i;
					i = -1;//ExpressionString.length();
					break;
				case '^':
					operandType = Expression.exp;
					indexOfOperand = i;
					i = -1;//ExpressionString.length();
					break;	
			}
		}
		
		d.println("Operand "+ExStr.charAt(indexOfOperand));
		d.println("Operand "+ExStr_blockedC[indexOfOperand]);
		//indexOfOperand++;
		
		LeftSideStr = ExStr.substring(0,indexOfOperand);
		d.println();
		d.println("LeftSideStr "+LeftSideStr);
		d.println("Operand "+ExStr.charAt(indexOfOperand));
		RightSideStr = ExStr.substring(indexOfOperand+1,ExStr.length());
		d.println("RightSideStr "+RightSideStr);
		d.println();
	}//probe
	public String getleftSide()
	{	return LeftSideStr;	}
	public String getrightSide()
	{	return RightSideStr;	}
	public int getOperand()
	{	return operandType;	}
	public int determineType(String InQ)
	{
		d.println("InQ "+InQ);
		String inQuestion = InQ;
		inQuestion = removeOutsideParentheses(inQuestion);
		inQuestion = blockStuffInParentheses(inQuestion);
		char[] InQC = inQuestion.toCharArray();
		
		int typ = 0;
		char OperandChar;
		int operandType = -1;

		for(int i = 0; i<inQuestion.length();i++)
		{
			OperandChar = inQuestion.charAt(i);
			switch(OperandChar)
			{
				case '+':
					operandType = Expression.plus;
					i = inQuestion.length();
					break;
				case '-':
					operandType = Expression.minus;
					i = inQuestion.length();
					break;
				case '/':
					operandType = Expression.dividedBy;
					i = inQuestion.length();
					break;
				case '*':
					operandType = Expression.times;
					i = inQuestion.length();
					break;
				case '^':
					operandType = Expression.times;
					i = inQuestion.length();
					break;
			}
		}
		if(operandType ==-1)//if there are no operands
			typ = Expression.constant;
		else
			typ = Expression.nested;
		
		if(typ == Expression.constant)//check for oneSided operations (only if there are no other operands outside of parentheses)
		{
			for(int i = 0; i<Expression.ops.length;i++)//test for all one sided things
			{
				if(inQuestion.indexOf(Expression.ops[i]) != -1)//if it is there
				{
					typ = Expression.oneSided;
					i = Expression.ops.length;
				}
			}
		}
		d.println(inQuestion +" is type "+typ);
		return typ;
	}
	public static String preformat(String S)
	{
		d.println("preformatting "+S);
		String Fstr = removeOutsideParentheses(S);
		char[] FstrC = Fstr.toCharArray();
		String Fstr_blocked = blockStuffInParentheses(Fstr);
		char[] Fstr_blockedC = Fstr_blocked.toCharArray();
		String orderOfOperationsS = "^*/";
		char[] orderOfOperations = orderOfOperationsS.toCharArray();
		
		if(FstrC[0] == '-')//replace lone '-' in front with '0-...'
		{
			Fstr = "0"+Fstr;
			FstrC = Fstr.toCharArray();
			Fstr_blocked = blockStuffInParentheses(Fstr);
			Fstr_blockedC = Fstr_blocked.toCharArray();
		}

		for(int i = 0; i<Expression.ops.length;i++)//put parentheses in front of one sided operations if they aren't already there
		{
			int inx = Fstr.indexOf(Expression.ops[i]);
			if(inx != -1)//if it is there
			{
				if(FstrC[inx+Expression.ops[i].length()] != '(')//if ( is not right after sin
				{
					int PBegin = inx+Expression.ops[i].length();
					
					int PEnd = PBegin;
					boolean b = isOneOfThese(FstrC[PEnd],"0123456789.x()sincotablnqrpeIfy-");
					while(b && PEnd!=FstrC.length-1)
					{
						PEnd++;
						b = isOneOfThese(FstrC[PEnd],"0123456789.x()sincotablnqrpeIfy");
						if(FstrC[PEnd]== '-'&&isOneOfThese(FstrC[PEnd+1],"0123456789.x(sctaleIi"))//for negative things
							b=true;
					}
					if(PEnd==FstrC.length-1)
						PEnd++;
					d.println("new one sided parentheses: "+Fstr);
					
					d.println("left: "+Fstr.substring(0,PBegin));
					d.println("middle: "+Fstr.substring(PBegin,PEnd));
					d.println("right: "+Fstr.substring(PEnd,Fstr.length()));
					
					
					Fstr = Fstr.substring(0,PBegin)+"("+Fstr.substring(PBegin,PEnd)+")"+Fstr.substring(PEnd,Fstr.length());
					
					FstrC = Fstr.toCharArray();
					Fstr_blocked = blockStuffInParentheses(Fstr);
					Fstr_blockedC = Fstr_blocked.toCharArray();
					
					
					
					d.println("done: "+Fstr);
				}
			}
		}
		for(int i = 0;i<Fstr_blockedC.length-1;i++)//insert '*'s where they are omitted
		{
			//sin cos tan ln sqrt abs Infinity
			if(isOneOfThese(Fstr_blockedC[i],"0123456789xyei")&& isOneOfThese(Fstr_blockedC[i+1],"xyepsct"))// like 5x or 2pi or 6e or 6tan(5)
			{
				boolean B = true;
				if(Fstr_blockedC[i+1] == 't' && Fstr_blockedC[i+2] != 'a')//to pass 5tan(4) but not sqrt --> sqr*t
					B=false;
			/*	if(FstrC[i+1] == 't' && FstrC[i+2] != 'a')//to pass 5tan(4) but not sqrt --> ab*s
					B=false;*/
				if(B)
				{	
					i++;
					Fstr = Fstr.substring(0,i)+"*"+ Fstr.substring(i,FstrC.length);
					d.println("insert '*'s: "+Fstr);
					FstrC = Fstr.toCharArray();
					Fstr_blocked = blockStuffInParentheses(Fstr);
					Fstr_blockedC = Fstr_blocked.toCharArray();
				}
			}
		}
		int ind;
		for(int i = 0; i<orderOfOperations.length;i++)//insert parentheses according to order of operations
		{
			int opIndex = Fstr_blocked.indexOf(orderOfOperations[i]);
			while(opIndex!=-1)
			{
				d.println("opIndex: "+opIndex+" "+Fstr_blockedC[opIndex]);
				
				
				
				d.println("StartingFrom "+orderOfOperations[i]+" at index "+opIndex); 
				String TestStr = "0123456789.xsincotablnqrpeIfy-_";
				boolean go = true;
				int ParenthBegin = opIndex-1;
				go = isOneOfThese(Fstr_blockedC[ParenthBegin],TestStr);
				while(go)
				{
					ParenthBegin--;
					if(ParenthBegin==-1)
						go = false;
					else
						go = isOneOfThese(Fstr_blockedC[ParenthBegin],TestStr);
				}
				ParenthBegin++;
				go = true;
				
				int ParenthEnd = opIndex+1;
				go = isOneOfThese(Fstr_blockedC[ParenthEnd],TestStr);
				while(go)
				{
					ParenthEnd++;
					if(ParenthEnd==Fstr_blockedC.length)
						go = false;
					else
						go = isOneOfThese(Fstr_blockedC[ParenthEnd],TestStr);
				}
				

				d.println("ParenthBegin "+FstrC[ParenthBegin]);	
				d.println("ParenthEnd "+FstrC[ParenthEnd-1]);
				
				boolean ok = true;
				if(ParenthBegin == 0 && ParenthEnd == FstrC.length)
					ok = false;
				if(ok)
				{
					String left = Fstr.substring(0,ParenthBegin);
					//d.println("left "+left);
						
					String middle=Fstr.substring(ParenthBegin,ParenthEnd);
						
					//d.println("middle "+middle);
						
					String right=Fstr.substring(ParenthEnd,Fstr.length());
						
					//d.println("right "+right);
						
					Fstr = left+"("+middle+")"+right;
					FstrC = Fstr.toCharArray();
					Fstr_blocked = blockStuffInParentheses(Fstr);
					Fstr_blockedC = Fstr_blocked.toCharArray();
					d.println(Fstr+" new parentheses");
				}
				else
					d.println("Parentheses not suitable");

				opIndex = Fstr_blocked.indexOf(orderOfOperations[i],ParenthEnd);
			}
		}
		return Fstr;
	}
	public static String blockStuffInParentheses(String S)
	{
		d.println("blocking str "+S);
		StringBuffer Temp = new StringBuffer();
		int len = (S.toCharArray()).length;
		for(int i = 0; i<len;i++)
		{
			if(S.charAt(i) == '(')
			{
				int Plevel = 1;
				while(Plevel != 0)
				{
					i++;
					Temp.append("_");
					if(S.charAt(i) == '(')
						Plevel++;
					if(S.charAt(i) == ')')
						Plevel--;
				}
				Temp.append("_");
			}
			else
				Temp.append(S.charAt(i));
		}
		d.println(" blocked str "+Temp.toString());
		return Temp.toString();
	}
	public static String removeOutsideParentheses(String FStrng)
	{
		String result = FStrng;
		char[] resultC = result.toCharArray();
		boolean go = true;
		while(go)
		{
			if(result.charAt(0) == '(')
			{
				d.println("removing parentheses: "+FStrng);
				int Plevel = 1,i=0;
				while(Plevel != 0)
				{
					i++;
					if(resultC[i] == '(')
					{
						d.println("( at "+i);
						Plevel++;
					}
					if(resultC[i] == ')')
					{
						d.println(") at "+i);
						Plevel--;
					}
					if(i != resultC.length-1)// if the parentheses are not at the ends of the string
						go = false;
					else
						go = true;
				}
				if(go)//only if the parentheses are at the ends of the string
				{
					result = result.substring(1,i);
				}
			}
			else
				go = false;
		}
		//if(i == FStrngC.length-1)//if the closing parentheses is the last char of the string
		d.println("no more parentheses: "+result);
		return result;
	}
	public static boolean isOneOfThese(char c,String S)
	{
		boolean is = false;
		String numbers = S;
		char[] nums = numbers.toCharArray();
		for(int i = 0; i<nums.length;i++)
		{
			if(c == nums[i])
			{
				is = true;
				i = nums.length;
			}
		}
		return is;
	}
	public static String replaceTwithX(String S)
	{
		char[] newStr = (S.toLowerCase()).toCharArray();
		StringBuffer newStringBuffer = new StringBuffer();
		for(int i = 0; i<newStr.length;i++)
		{
			if(newStr[i] == 't')
			{
				if(i == newStr.length-1)//if it is at the end
					newStr[i] = 'x';
				else if(newStr[i+1] != 'a')//do not replace 't' in "tan"
					newStr[i] = 'x';
			}
			newStringBuffer.append(newStr[i]);
		}
		return newStringBuffer.toString();
	}
}
	
class d
{
	static boolean print = false;
	public static void println(String S)
	{
		if(print)
		System.out.println(S);
	}
	public static void println(double S)
	{
		if(print)
		System.out.println(S);
	}
	public static void println()
	{
		if(print)
		System.out.println();
	}
}