package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import util.Util;

public class FileDiff
{
	// modify ROOT to fit in both HTML and scripts: html or js
	static final String ROOT = "/Users/zzy/Documents/script/js/";

	public static void main(String args[]) throws IOException
	{
		File dir = new File(ROOT);
		if (!dir.isDirectory())
		{
			System.err.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(1);
		}

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
			Runtime run = Runtime.getRuntime();
			String cmd = "mkdir "+d.getAbsolutePath()+"/result/";
			Process p = run.exec(cmd);
			
			for (int i = 0; i < files.length; i++)
			{
				if(files[i].isDirectory() || files[i].getName().equals(".DS_Store"))
					continue;
				for (int j = i + 1; j < files.length; j++)
				{
					if(files[j].isDirectory() || files[j].getName().equals(".DS_Store"))
						continue;
					
					cmd = "diff " + files[i].getAbsolutePath() + " "
							+ files[j].getAbsolutePath();/* + " > "
							+ d.getAbsolutePath() + "/result/"
							+ files[i].getName() + "_" + files[j].getName();*/
					// System.out.println(cmd);
					String outPath = d.getAbsolutePath() + "/result/"
							+ files[i].getName() + "_" + files[j].getName()+".txt";
					
					p = run.exec(cmd);
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(
							p.getInputStream()));
					String s, content = "";
					while ((s = stdInput.readLine()) != null)
					{
						content += s+"\n";
					}
					Util.writeFile(outPath, content);;
				}
			}
		}
	}
}
