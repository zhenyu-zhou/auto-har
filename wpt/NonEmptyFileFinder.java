package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import util.Util;

public class NonEmptyFileFinder
{
	static final String ROOT = "/Users/zzy/Documents/script/new/";
	
	public static void main(String args[]) throws IOException
	{
		File dir = new File(ROOT);
		if (!dir.isDirectory())
		{
			System.err.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(1);
		}
		
		int total = 0, active = 0;
		int totalPair = 0, activePair = 0;
		boolean find = false;
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
				System.err.println("No result folder in "+d.getAbsolutePath());
				continue;
			}
			total++;
			find = false;
			
			for(File r : resultDir.listFiles())
			{
				if(r.getName().equals(".DS_Store"))
					continue;
				totalPair++;
				String content = Util.readFile(r.getAbsolutePath());
				if(content.length() > 0)
				{
					if(!find)
					{
						active++;
						find = true;
					}
					activePair++;
					System.out.println("Not empty file: "+r.getAbsolutePath());
				}
			}
		}
		
		System.out.println("Total: "+total);
		System.out.println("Active: "+active);
		System.out.println("Total pair: "+totalPair);
		System.out.println("Active pair: "+activePair);
	}
}
