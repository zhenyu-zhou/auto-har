package wpt;

import java.io.File;
import java.io.IOException;

import util.Util;

/**
 * Generate objects with different size
 * 
 * @author zzy
 *
 */
public class ObjectGenerator
{
	public static final String PATH = "/Users/zzy/try/benchmark_zzy/";
	// a string with 64 characters
	static final String S = "qwertyuioplkjhgfdsazxcvbnmqwertyuioplkjhgfdsazxcvbnmqwertyuioplk";
	public static final int MAX = 17;
	
	public static void main(String args[]) throws IOException
	{
		assert(S.length() == 64);
		
		File dir = new File(PATH);
		if(dir.exists())
			dir.delete();
		dir.mkdirs();
		
		for(int i = 1; i <= MAX; i++)
		{
			System.out.println("Generating: "+i);
			for(int j = 0; j < Math.pow(2, i); j++)
			{
				Util.writeFileAppend(PATH+i+".txt", S);
			}
		}
	}
}
