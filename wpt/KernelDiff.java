package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import util.Pair;

public class KernelDiff
{
	static final String[] DIR = {
			"/Users/zzy/try/linux_3.13.11/measure_pages/out_enable4",
			"/Users/zzy/try/linux_3.13.11/measure_pages/out_disable2" };
	static Hashtable<String, Pair<Integer, Integer>>[] table = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void loadTable(int num) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				DIR[num])));
		String temp = null;
		while ((temp = reader.readLine()) != null)
		{
			String name = temp;
			int time = Integer.parseInt(reader.readLine());
			int size = Integer.parseInt(reader.readLine());
			table[num].put(name, new Pair(time, size));
		}
		reader.close();
	}

	@SuppressWarnings("unchecked")
	static void init()
	{
		table = new Hashtable[2];
		table[0] = new Hashtable<String, Pair<Integer, Integer>>();
		table[1] = new Hashtable<String, Pair<Integer, Integer>>();
	}

	public static void main(String args[]) throws IOException
	{
		init();
		loadTable(0);
		loadTable(1);
		for (String k : table[0].keySet())
		{
			// int diff = table[0].get(k) - table[1].get(k);
			// System.out.println(k);
			// System.out.println(diff);
			System.out.println(table[0].get(k).getSecond() + " "
					+ table[0].get(k).getFirst() + " "
					+ table[1].get(k).getFirst());
			System.out.println(table[0].get(k).getFirst()-table[1].get(k).getFirst());
		}
	}
}
