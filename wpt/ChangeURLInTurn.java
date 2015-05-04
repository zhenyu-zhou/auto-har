package wpt;

import java.io.File;
import java.io.IOException;

import util.Util;

public class ChangeURLInTurn
{
	public static String PATH = "/Users/zzy/Documents/amazon/", DIR = "index_files/", INDEX = "index.html";
	public static final String RECORD = "record.zzy", TAG = "zzy\n", BAK = "index.bak", REPLACEMENT = "zlqqlz";
	public static String file_list;
	
	static void dfs(File f)
	{
		for(File ff : f.listFiles())
		{
			if(ff.isDirectory())
				dfs(ff);
			else if(ff.isFile() && !ff.getName().startsWith("."))
				file_list += ff.getName()+"\n";
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
        if(args.length == 1)
        {
                PATH = args[0];
        }
        
        File dir = new File(PATH+DIR);
        if(!dir.exists() || !dir.isDirectory())
        {
        	System.err.println("Wrong dir");
        	System.exit(-1);
        }
        
        File[] files = dir.listFiles();
        File record = new File(PATH+RECORD);
        // for debug
        // record.delete();
        
        Runtime run = Runtime.getRuntime();
        if(!record.exists()) // the first time
        {
        	file_list = TAG;
        	dfs(dir);
        	Util.writeFile(PATH+RECORD, file_list);
        	run.exec("cp "+PATH+INDEX+" "+PATH+BAK);
        }
        else
        	file_list = Util.readFile(PATH+RECORD);
        
        if(file_list.endsWith(TAG))
        {
        	System.out.println("Finish!");
        	System.exit(1);
        }
        
        run.exec("rm "+PATH+INDEX);
        run.exec("cp "+PATH+BAK+" "+PATH+INDEX);
        Thread.sleep(100);
        
        int index1 = file_list.indexOf(TAG);
        int index2 = file_list.indexOf('\n', index1+TAG.length());
        String name = file_list.substring(index1+TAG.length(), index2).trim();
        file_list = file_list.substring(0, index1)+name+"\n"+TAG+file_list.substring(index2+1);
        Util.writeFile(PATH+RECORD, file_list);
        
        System.out.println("Replace file: "+name);
        String content = Util.readFile(PATH+INDEX);
        content = content.replaceAll(name, REPLACEMENT);
        Util.writeFile(PATH+INDEX, content);
	}
}
