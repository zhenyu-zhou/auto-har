package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CDF
{
	// change between script_change or script_pair_change
	static final String PATH = "/Users/zzy/Documents/eclipse/WPT/src/script_pair_change";

	public static void main(String args[]) throws IOException
	{
		BufferedReader reader = null;
		ArrayList<CDFPair> arr = new ArrayList<CDFPair>();
		int total = 0;

		reader = new BufferedReader(new FileReader(new File(PATH)));
		String temp = null;
		while ((temp = reader.readLine()) != null)
		{
			String[] tokens = temp.split("[: ]");
			int k, v;
			k = Integer.parseInt(tokens[0]);
			v = Integer.parseInt(tokens[2]); // tokens[1] = ""
			// System.out.println(k + ": " + v);
			arr.add(new CDFPair(k, v));
			total += v;
		}
		reader.close();
		
		Collections.sort(arr);
		System.out.print("x = [0");
		for(int i = 0; i < arr.size(); i++)
		{
			System.out.print(", "+arr.get(i).key);
		}
		System.out.println("]");
		
		int accumulate = 0;
		System.out.print("y = [0");
		for(int i = 0; i < arr.size(); i++)
		{
			accumulate += arr.get(i).value;
			int k = arr.get(i).key;
			double p = accumulate/(double)total;
			System.out.print(", "+accumulate/(double)total);
			// System.out.println(k+": "+p);
		}
		System.out.println("]");
	}
	
	
	static class CDFPair implements Comparable<CDFPair>
	{
		int key, value;
		
		CDFPair(int k, int v)
		{
			key = k;
			value = v;
		}
		@Override
		public int compareTo(CDFPair p)
		{
			return Integer.compare(key, p.key);
		}
		@Override
		public String toString()
		{
			return key+": "+value;
		}
	}
}
