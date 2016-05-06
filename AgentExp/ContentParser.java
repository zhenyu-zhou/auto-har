package AgentExp;

import java.io.File;
import java.io.IOException;

import util.Util;

public class ContentParser
{
	static final String ROOT = "/Users/zzy/Documents/eclipse/WPT/src/AgentExp/";
	static final String PATH = "Raw_Contents", OUT = "Contents";
	static final String L1 = "<td>", L2 = "</td>";
	
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
			for(int i = 0; i < 2; i++)
			{
				idx = content.indexOf(L1);
				content = content.substring(idx+L1.length());
			}
			idx = content.indexOf(L1);
		}
	}
}
