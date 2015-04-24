package wpt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Util;
import wpt.HARReader.LoadingTime;

public class HARReaderPhantomjs
{
	final static String[] DIR = {
			"/Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin/bg.har",
			"/Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin/out.har" };
	static String OUT_DIR = "/Users/zzy/Downloads/loading time/spdy/10/tra_s1";
	static ArrayList<String>[] table = null;

	static void loadTable(int num) throws IOException, JSONException
	{
		String tra_s = Util.readFile(DIR[num]);
		tra_s = tra_s.substring(tra_s.indexOf('{'));
		JSONObject json = new JSONObject(tra_s);
		json = (JSONObject) json.get("log");

		JSONArray array = (JSONArray) json.get("entries");

		String name = null;
		for (int i = 0; i < array.length(); i++)
		{
			JSONObject req = (JSONObject) array.get(i);
			try
			{
				name = ((JSONObject) req.get("request")).getString("url");
				// if(num == 1)
				// System.out.println(name);
				String[] tokens = name.split("/");
				name = tokens[tokens.length - 1];
				// int statusCode = ((JSONObject)
				// req.get("response")).getInt("status");
				// if(statusCode == 200) // HTTP OK
				table[num].add(name);
			} catch (JSONException e)
			{
				continue;
			}
		}
	}

	@SuppressWarnings("unchecked")
	static void init()
	{
		table = new ArrayList[2];
		table[0] = new ArrayList<String>();
		table[1] = new ArrayList<String>();
	}

	public static void main(String[] args) throws IOException, JSONException
	{
		init();
		loadTable(0);
		loadTable(1);
		table[0].removeAll(table[1]);
		// System.out.println("table 1: "+table[1]);
		if (table[0].size() > 0)
		{
			System.out.println("diff: " + table[0]);
			System.out.println();
		}
	}
}
