package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import util.Util;

public class NonEmptyFileFinder
{
	// modify ROOT to fit in both HTML and scripts
	static final String ROOT = "/Users/zzy/Documents/script/html/";
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
		int totalPair = 0, activePair = 0, activePair_large = 0;
		boolean find = false, find_large = false;
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
			for(int i = 0; i < files.length; i++)
			{
				// System.out.println("name: "+files[i].getName());
				if(files[i].isDirectory() && files[i].getName().equals("result"))
				{
					resultDir = files[i];
					break;
				}
			}
			if(resultDir == null)
			{
				// System.err.println("No result folder in "+d.getAbsolutePath());
				continue;
			}
			total++;
			find = false; find_large = false;
			
			for(File r : resultDir.listFiles())
			{
				if(r.getName().equals(".DS_Store"))
					continue;
				totalPair++;
				String content = Util.readFile(r.getAbsolutePath());
				if(content.length() > 0)
				{
					int line = content.split("\n").length - 1;
					if(!find)
					{
						active++;
						find = true;
					}
					if(!find_large && line > MIN_LINE)
					{
						active_large++;
						find_large = true;
					}
					activePair++;
					if(line > MIN_LINE)
					{
						activePair_large++;
					}
					System.out.println("Not empty file: "+r.getAbsolutePath());
				}
			}
		}
		
		System.out.println("Total: "+total);
		System.out.println("Active: "+active);
		System.out.println("Active large: "+active_large);
		System.out.println("Total pair: "+totalPair);
		System.out.println("Active pair: "+activePair);
		System.out.println("Active pair large: "+activePair_large);
	}
}
