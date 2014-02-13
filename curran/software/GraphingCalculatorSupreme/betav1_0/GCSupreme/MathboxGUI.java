package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class MathboxGUI	extends	JPanel implements host
{
	int width;
	int height;
	JTextArea textArea,displayArea;
	public MathboxGUI(int w,int h)
	{
		super(new GridBagLayout());
		GridBagLayout gridbag =	(GridBagLayout)getLayout();
		GridBagConstraints c = new GridBagConstraints();

		width = w;
		height = h;
		int Xindent = 30;
		int Yindent = 50;

		textArea = new JTextArea("type your math here");
		
		//textArea.setFont(new Font("Arial", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.getDocument().addDocumentListener(new MyDocumentListener());
		
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setPreferredSize(new	Dimension(width-Xindent, height/2-Yindent));
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		
		JScrollPane	displayScrollPane =	new	JScrollPane(displayArea);
		displayScrollPane.setPreferredSize(new Dimension(width-Xindent,	height/2-Yindent));
		
		
		c.gridx = 0;
		c.gridy	= 0;
		c.weightx =	1.0;
		c.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(areaScrollPane, c);
		add(areaScrollPane);
		
		c.gridx = 0;
		c.gridy	= 1;
		c.weightx =	0.0;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(displayScrollPane, c);
		add(displayScrollPane);
		
	
		//setSize(width,height);
		//setPreferredSize(new Dimension(width,height));
		
	}
	class MyDocumentListener implements DocumentListener
	{
		String newline = "\n";
		String output =	"", Fstr = "0";
		int lastLineBegin = 0;
		Function F = new Function("0");
	    public void insertUpdate(DocumentEvent e)
	    {
			Document doc = (Document)e.getDocument();
			int	changeLength = e.getLength();
			int	Tlen = doc.getLength();
			int	Tbeg = 0;
			if(Tlen>0)
				Tbeg = Tlen-1;
			try	{ 	output = doc.getText(Tbeg,Tlen-Tbeg);  }catch(Exception t){}
			if(output.equals(newline))
			{
				try {Fstr = doc.getText(lastLineBegin,Tlen-lastLineBegin-1).toLowerCase();}catch(Exception t){}
				lastLineBegin = Tlen;
				
				displayArea.append(Fstr+" = ");
				F = new Function(Fstr);
				
				
				if(Fstr.indexOf("x") != -1)
				{
					displayArea.append(newline);
					for(int x=-10;x<=10;x++)
						displayArea.append(F.Evaluate((double)x)+"\t X="+x+newline);
				}
				else
					displayArea.append(F.Evaluate(0)+newline+newline);
					
			}
			
	    }
	    public void removeUpdate(DocumentEvent e) {}
	    public void changedUpdate(DocumentEvent e) {}
	}
	public void acceptVisitor(Visitor V){}
}

