package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class TimeSizePairReader
{
	static final String[] PATH = {
			"/Users/zzy/try/linux_3.13.11/out_port",
			"/Users/zzy/try/linux_3.13.11/out_multi_1000_disable" };
	static TreeMap<Integer, ArrayList<Integer>>[] table = null;
	static String csv = "size, enable, disable\n";
	static TreeMap<Integer, Double>[] avg = null;

	public static void load(int num) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				PATH[num])));
		String temp = null;
		while ((temp = reader.readLine()) != null)
		{
			int time = Integer.parseInt(temp);
			temp = reader.readLine();
			int size = Integer.parseInt(temp);
			if (table[num].containsKey(size))
			{
				table[num].get(size).add(time);
			}
			else
			{
				ArrayList<Integer> tempa = new ArrayList<Integer>();
				tempa.add(time);
				table[num].put(size, tempa);
			}
		}
		reader.close();

		// System.out.println(table);
		System.out.print("X = [0");
		for (int k : table[num].keySet())
		{
			System.out.print(", " + k);
		}
		System.out.println("]");
		System.out.print("Y = [0");
		for (int k : table[num].keySet())
		{
			ArrayList<Integer> tempa = table[num].get(k);
			double sum = 0;
			for (int i = 0; i < tempa.size(); i++)
			{
				sum += tempa.get(i);
			}
			sum /= tempa.size();
			System.out.print(", " + sum);
			avg[num].put(k, sum);
		}
		System.out.println("]");
	}

	public static void init()
	{
		table = new TreeMap[2];
		table[0] = new TreeMap<Integer, ArrayList<Integer>>();
		table[1] = new TreeMap<Integer, ArrayList<Integer>>();
		avg = new TreeMap[2];
		avg[0] = new TreeMap<Integer, Double>();
		avg[1] = new TreeMap<Integer, Double>();
	}
	
	public static void main(String[] args) throws IOException
	{
		init();
		load(0);
		load(1);
		for (int k : table[0].keySet())
		{
			csv += k+", "+avg[0].get(k)+", "+avg[1].get(k)+"\n";
		}
		Util.writeFile("/Users/zzy/try/z.csv", csv);
	}
}
