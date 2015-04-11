package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;

public class ScriptCrawlerTest
{
	static final String[] SRC = { "http://z-ecx.images-amazon.com/images/G/01/AUIClients/AmazonUI-aa934ce2b8aed108efc75d1fcbe9d30f93c29757.rendering_engine-not-trident.min._V2_.css" };
	static final String ROOT = "/Users/zzy/Documents/script/js/";

	static HashSet<String> dup = new HashSet<String>();
	
	public static void main(String args[]) throws IOException
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		// System.out.println(month+" "+day);

		for (int i = 0; i < SRC.length; i++)
		{
			String[] tokens = SRC[i].split("[/\\\\]+");
			String fullname = tokens[tokens.length - 1];
			int index = fullname.lastIndexOf(".");
			if (index < 0)
			{
				System.err.println("zzy no ext name: " + fullname);
				continue;
			}
			String name = fullname.substring(0, index);
			String ext = fullname.substring(index);
			fullname = name + "-" + month + "." + day + ext;
			System.out.println(name);
			// System.out.println(ext);
			if(dup.contains(name))
			{
				System.err.println("Duplicated name: "+name);
				continue;
			}
			dup.add(name);
			
			HttpUtil.downloadFile(ROOT+name+"/"+fullname, SRC[i]);
		}
	}
}
