package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.AlexaSites;
import util.HttpUtil;
import util.MapCount;

public class AlexaTop100Crawler
{
	static final String[] SRC = AlexaSites.SITES;

	static final String ROOT = "/Users/zzy/Documents/script/alexa/";
	static HashSet<String> dup = new HashSet<String>();
	static MapCount<String> mc = new MapCount<String>();

	public static void main(String args[]) throws IOException
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		System.out.println(month + "." + day);

		for (int i = 0; i < SRC.length; i++)
		{
			System.out.println("Processing... " + i + " " + (i + 1.0)
					/ SRC.length * 100 + "%: " + SRC[i]);
			String[] tokens = SRC[i].split("[/\\\\]+");
			String name = tokens[1];
			HttpUtil.downloadFile(ROOT + name + "/" + "index.html", SRC[i]);
		}
	}
}
