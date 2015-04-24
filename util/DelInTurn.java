package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DelInTurn
{
	static final String DIR = "/Users/zzy/try/del/", RECORD = "record.zzy", EXT = ".zzy";

	public static void main(String args[]) throws IOException
	{	
		File dir = new File(DIR);
		if (!dir.isDirectory())
		{
			System.err.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(-1);
		}
		// first line: the name of file modified last time
		File record = new File(DIR+RECORD);
		String last = null;
		int index = -1;
		File orif = null, newf = null;
		if (record.exists())
		{
			BufferedReader reader = new BufferedReader(new FileReader(record));
			last = reader.readLine();
			reader.close();
			record.delete();
			orif = new File(DIR+last+EXT);
			newf = new File(DIR+last);
			orif.renameTo(newf);
			index = Arrays.asList(dir.listFiles()).indexOf(newf);
			if(index == dir.listFiles().length - 1)
			{
				System.out.println("Finish!");
				System.exit(1);
			}
		}

		// even if -1, it works
		index++;
		while(dir.listFiles()[index].isDirectory())
			index++;
		if(index == dir.listFiles().length - 1)
		{
			System.out.println("Finish!");
			System.exit(1);
		}
		String name = dir.listFiles()[index].getName();
		orif = new File(DIR+name);
		newf = new File(DIR+name+EXT);
		orif.renameTo(newf);
		
		record.createNewFile();
		Util.writeFile(record.getAbsolutePath(), orif.getName());
		System.out.println("Rename file: "+orif.getName());
	}
}
