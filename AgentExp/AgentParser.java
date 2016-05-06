package AgentExp;

import java.io.*;

import util.Util;

public class AgentParser
{
	public static final String ROOT = "/Users/zzy/Documents/eclipse/WPT/src/AgentExp/";
	public static final String PATH = "Raw_Agents", OUT = "Agents";
	static final String L1 = "<td class=\"white\">", L2 = "</td>";
	
	public static void main(String[] args) throws IOException
	{
		File f = new File(ROOT+OUT);
		if(f.exists())
			f.delete();
		
		String content = Util.readFile(ROOT+PATH);
		int idx = content.indexOf(L1);
		// content = content.substring(idx+L1.length());
		// System.out.println(content.substring(0, content.indexOf(L2)));
		while(idx > 0)
		{
			content = content.substring(idx+L1.length());
			String temp = content.substring(0, content.indexOf(L2));
			System.out.println(temp);
			Util.writeFileAppend(ROOT+OUT, temp+"\n");
			for(int i = 0; i < 3; i++)
			{
				idx = content.indexOf(L1);
				content = content.substring(idx+L1.length());
			}
			idx = content.indexOf(L1);
		}
	}
}
