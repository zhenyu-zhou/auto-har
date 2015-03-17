package util;

import java.util.ArrayList;


public class PaperDivider
{
	public static String getAbstract(String content)
	{
		int index1 = content.indexOf("abstract");
		int index2 = content.indexOf("keywords");
		String ret = content.substring(index1+"abstract ".length(), index2);
		if(ret.endsWith("author "))
		{
			ret = ret.substring(0, ret.length()-"author ".length());
		}
		return ret;
	}
	
	private static String getReferenceTxt(String content)
	{
		int index = content.indexOf("REFERENCES");
		String ret = content.substring(index+"REFERENCES".length());
		return ret.trim();
	}
	
	// TODO: other reference format???!!! eg. [1] 1)
	public static ArrayList<String> getReferences(String content)
	{
		String ref = getReferenceTxt(content);
		
		ArrayList<String> ret = new ArrayList<String>();
		String[] tokens = null;
		
		if(ref.startsWith("1."))
			tokens = ref.split("[0-9]+\\.");
		else if(ref.startsWith("[1]"))
			tokens = ref.split("\\[[0-9]+\\]");
		
		if(tokens == null)
		{
			System.err.println("Invalid reference format");
			return null;
		}
		
		for(int i = 0; i < tokens.length; i++)
		{
			String temp = tokens[i].trim();
			if(temp.length() == 0)
				continue;
			// TODO: 8th... is it necessary???
			if(!Character.isLetter(temp.charAt(0)))
				continue;
			temp.replace("\n", "");
			ret.add(temp);
		}
		return ret;
	}
	
	public static String getTitle(String content)
	{
		String ret = "";
		String[] lines = content.split("\n");
		int num = 0;
		
		for(int i = 0; i < lines.length; i++)
		{
			// assume the tiltle has 2-line length
			if(num == 2) break;
			lines[i] = lines[i].trim();
			if(lines[i].length() == 0) continue;
			ret += lines[i]+" ";
			num ++;
		}
		return ret;
	}
}
