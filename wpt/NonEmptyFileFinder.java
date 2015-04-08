package wpt;

import java.io.File;
import java.io.IOException;

import util.MapCount;
import util.Util;

public class NonEmptyFileFinder
{
	// modify ROOT to fit in both HTML and scripts: html or new
	static final String ROOT = "/Users/zzy/Documents/script/new/";
	static final int MIN_LINE = 5;

	public static void main(String args[]) throws IOException
	{
		File dir = new File(ROOT);
		if (!dir.isDirectory())
		{
			System.err.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(1);
		}

		// large: files no less then MIN_LINE lines
		int total = 0, active = 0, active_large = 0;
		int totalPair = 0, activePair = 0, activePair_large = 0, current_pair = 0, this_pair = 0;
		boolean find = false, find_large = false;
		MapCount<Integer> mc = new MapCount<Integer>(), mc_pair = new MapCount<Integer>();
		MapCount<Double> mc_pair_each = new MapCount<Double>();

		for (File d : dir.listFiles())
		{
			if (d.getName().equals(".DS_Store"))
				continue;
			if (!d.isDirectory())
			{
				System.err.println("Not a sub-directory: "
						+ d.getAbsolutePath());
				System.exit(1);
			}

			File[] files = d.listFiles();
			File resultDir = null;
			for (int i = 0; i < files.length; i++)
			{
				// System.out.println("name: "+files[i].getName());
				if (files[i].isDirectory()
						&& files[i].getName().equals("result"))
				{
					resultDir = files[i];
					break;
				}
			}
			if (resultDir == null)
			{
				// System.err.println("No result folder in "+d.getAbsolutePath());
				continue;
			}
			total++;
			find = false;
			find_large = false;
			current_pair = 0;
			this_pair = 0;

			for (File r : resultDir.listFiles())
			{
				if (r.getName().equals(".DS_Store"))
					continue;
				totalPair++;
				this_pair++;
				String content = Util.readFile(r.getAbsolutePath());
				if (content.length() > 0)
				{
					int line = content.split("\n").length;
					if (!find)
					{
						active++;
						mc.add(line);
						find = true;
					}
					if (!find_large && line > MIN_LINE)
					{
						active_large++;
						find_large = true;
					}
					activePair++;
					current_pair++;
					if (line > MIN_LINE)
					{
						activePair_large++;
						// current_pair++;
					}
					mc_pair.add(line);
					// System.out.println("Not empty file: "+r.getAbsolutePath());
				}
				// avoid open too many files
				if (totalPair % 100 == 0)
					System.gc();
			}
			if(find)
				mc_pair_each.add(current_pair/(double)this_pair);
		}

		System.out.println("Total: " + total);
		System.out.println("Active: " + active);
		System.out.println("Active large: " + active_large);
		System.out.println("Total pair: " + totalPair);
		System.out.println("Active pair: " + activePair);
		System.out.println("Active pair large: " + activePair_large);

		// output is too long, change between mc and mc_pair by need
		// warning: need to change two places: condition and inner statement
		for(double l : mc_pair_each.keySet())
		{
			System.out.println(l+": "+mc_pair_each.get(l));
		}

	}
}
