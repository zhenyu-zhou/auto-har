package wpt;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.json.*;

import util.Util;

/**
 * Format: File_name (space) loading time difference (space) start time difference
 * Unit for time: ms
 * If the second number is positive, then traditional loading takes longer time.
 * If the third number is positive, then traditional loading starts late.
 * 
 * @author zzy
 *
 */

public class HARReader
{
	// final static String[] NAME = {"tra", "s1b"};

	// traditional, split
	final static String[] DIR =
	{ "/Users/zzy/Downloads/loading time/spdy/10/tra.har",
			"/Users/zzy/Downloads/loading time/spdy/10/s1.har" };
	/*static String[] DIR = {
			"/Users/zzy/Documents/eclipse/WPT/bin/har/www.google.com+2015-02-15+17-06-33.har",
			"/Users/zzy/Documents/eclipse/WPT/bin/har/www.google.com+2015-02-15+17-04-41.har" };*/
	 static String OUT_DIR =
	 "/Users/zzy/Downloads/loading time/spdy/10/tra_s1";
	//static String OUT_DIR = "/Users/zzy/Documents/eclipse/WPT/bin/har/result";
	static Hashtable<String, LoadingTime>[] table = null;
	static String out = "";
	// false: HAR by firebug
	// true: HAR by pingdom
	static boolean isAuto = false;

	static void loadTable(int num) throws IOException, JSONException
	{
		System.out.print(DIR[num] + ": ");
		out += DIR[num] + ": ";
		
		String tra_s = Util.readFile(DIR[num]);
		JSONObject json = new JSONObject(tra_s);
		json = (JSONObject) json.get("log");

		// total loading time
		if (isAuto)
		{
			JSONObject time_obj = (JSONObject) ((JSONObject) ((JSONArray) json
					.get("pages")).get(0)).get("pageTimings");
			int total_time = time_obj.getInt("onContentLoad")+time_obj.getInt("onLoad");
			System.out.println(total_time);
			out += total_time+"\n";
		}

		JSONArray array = (JSONArray) json.get("entries");

		String date_s = null, name = null;
		int time = -1, date = -1, st_date = -1;
		for (int i = 0; i < array.length(); i++)
		{
			JSONObject req = (JSONObject) array.get(i);
			try
			{
				date_s = (String) req.get("startedDateTime");
				date = parseDate(date_s);
				// TODO: in order?? or find the minimum value as overall start
				// time??
				if (i == 0)
				{
					st_date = date;
				}
				time = req.getInt("time");
				name = ((JSONObject) req.get("request")).getString("url");
				// if(num == 1)
				// System.out.println(name);
				String[] tokens = name.split("/");
				name = tokens[tokens.length - 1];
				// if(num == 1)
				// System.out.println(name+", "+time+", "+date+": "+date_s);
				table[num].put(name, new LoadingTime(date - st_date, time));
			} catch (JSONException e)
			{
				// System.err.println(req);
				// System.exit(1);
				continue;
			}

		}
		
		//DIR[num] = DIR[num].replaceAll("//", "/");
		//File tempf = new File(DIR[num]);
		//tempf.delete();
	}

	public static void main(String[] args) throws IOException, JSONException
	{
		if (args.length == 3)
		{
			DIR[0] = args[0];
			DIR[1] = args[1];
			String[] tokens = DIR[0].split("/");
			String name1 = tokens[tokens.length - 1];
			tokens = DIR[1].split("/");
			String name2 = tokens[tokens.length - 1];
			OUT_DIR = args[2] + "/" + name1 + "_" + name2;
			System.out.println(OUT_DIR);
			isAuto = true;
		}
		else if (!(args.length == 0))
		{
			System.err.println("Invalid argument number!");
			System.exit(1);
		}

		/*System.out.println(DIR[0]);
		System.out.println(DIR[1]);
		out += DIR[0] + "\n" + DIR[1] + "\n";*/
		init();
		loadTable(0);
		loadTable(1);

		/*for (String k : table[0].keySet())
		{
			System.out.println(k + ": " + table[1].get(k));
		}*/
		int deltaT = 9999, deltaS = 9999;

		for (String k : table[0].keySet())
		{
			if (table[1].containsKey(k))
			{
				deltaT = table[0].get(k).time - table[1].get(k).time;
				deltaS = table[0].get(k).st - table[1].get(k).st;
			}
			else if (k.equals("index_tra.html"))
			{
				String k2 = "index.html";
				// WARNING: url without index.html, eg.
				// http://optimus.cs.duke.edu/youtube/
				if (!table[1].containsKey(k2))
					k2 = "youtube";
				deltaT = table[0].get(k).time - table[1].get(k2).time;
				deltaS = table[0].get(k).st - table[1].get(k2).st;
			}
			else
			{
				// System.err.println(k);
			}
			System.out.println(k + " " + deltaT + " " + deltaS);
			out += k + " " + deltaT + " " + deltaS + "\n";
		}

		Util.writeFile(OUT_DIR, out);
	}

	static int parseDate(String d)
	{
		int ret = 0, len = d.length();
		if (!isAuto)
		{
			ret += Integer.parseInt(d.substring(len - 13, len - 11));
			ret *= 60; // h -> min
			ret += Integer.parseInt(d.substring(len - 10, len - 8));
			ret *= 60; // min -> s
			ret += Integer.parseInt(d.substring(len - 7, len - 5));
			ret *= 1000; // s -> ms
			ret += Integer.parseInt(d.substring(len - 4, len - 1));
		}
		else
		{
			ret += Integer.parseInt(d.substring(len - 18, len - 16));
			ret *= 60; // h -> min
			ret += Integer.parseInt(d.substring(len - 15, len - 13));
			ret *= 60; // min -> s
			ret += Integer.parseInt(d.substring(len - 12, len - 10));
			ret *= 1000; // s -> ms
			ret += Integer.parseInt(d.substring(len - 9, len - 6));
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	static void init()
	{
		table = new Hashtable[2];
		table[0] = new Hashtable<String, LoadingTime>();
		table[1] = new Hashtable<String, LoadingTime>();
	}

	static class LoadingTime
	{
		int st, time;

		LoadingTime(int s, int t)
		{
			st = s;
			time = t;
		}

		@Override
		public String toString()
		{
			String ret = "";
			ret += st + ", " + time;
			return ret;
		}
	}

}
