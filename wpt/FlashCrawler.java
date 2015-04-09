package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class FlashCrawler
{
	static final String[] SRC = {

		};

static final String ROOT = "/Users/zzy/Documents/script/flash/";
static HashSet<String> dup = new HashSet<String>();
static MapCount<String> mc = new MapCount<String>();

public static void main(String args[]) throws IOException
{
	Calendar cal = Calendar.getInstance();
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DATE);
	System.out.println(month+"."+day);

	for (int i = 0; i < SRC.length; i++)
	{
		System.out.println("Processing... "+i+" "+(i+1.0)/SRC.length*100+"%: "+SRC[i]);
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
		if (dup.contains(name))
		{
			System.err.println("Duplicated name: " + name);
			// continue;
			if(mc.containsKey(name))
			{
				mc.add(name);
				name += "zzy"+mc.get(name);
			}
			else
			{
				mc.add(name); mc.add(name);
				name += "zzy2";
			}
		}
		else
		{
			dup.add(name);
		}

		HttpUtil.downloadFile(ROOT + name + "/" + fullname, SRC[i]);
	}
}
}
