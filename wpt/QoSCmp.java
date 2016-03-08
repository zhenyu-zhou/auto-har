package wpt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import util.Util;

public class QoSCmp
{
	static final String DIR = "/Users/zzy/try/crawler/";
	static final int MAX_HIGHLY_ACTIVE = 1;
	static int st_mon = -1, st_day = -1;//, month = -1, day = -1;
	static LocalDate now = null;

	static boolean cmp(File dir, ArrayList<Integer> change_period, ArrayList<Double> change_percentage)
	{
		// System.out.println("In folder: " + dir.getAbsolutePath());
		String preffix = null, ext = null;
		for (File f : dir.listFiles())
		{
			String s = f.getName();
			if (f.getName().startsWith("."))
				continue;
			// System.out.println(f.getAbsolutePath());
			int idx = s.lastIndexOf('-');
			preffix = dir.getAbsolutePath()+"/"+s.substring(0, idx+1);
			idx = s.lastIndexOf('.');
			ext = s.substring(idx);
			break;
		}
		
		// int m = st_mon, d = st_day;
		LocalDate d = LocalDate.of(2015, st_mon, st_day);
		now = LocalDate.of(2015, 4, 13);
		boolean change = false, inSeq = true;
		int len = 0, min_len = Integer.MAX_VALUE, total_period = 0, change_times = 0;
		while(ChronoUnit.DAYS.between(d, now) > 0)//(m < month || (m == month && d < day))
		{
			change = false;
			int day = d.getDayOfMonth();
			int month = d.getMonthValue();
			String n1 = preffix+month+"."+day+ext;
			d = d.plusDays(1);
			day = d.getDayOfMonth(); month = d.getMonthValue();
			String n2 = preffix+month+"."+day+ext;
			boolean find = true;
			while(ChronoUnit.DAYS.between(d, now) >= 0)
			{
				try{
					find = true;
					change = !Util.isFileSameWithThre(n1, n2, MAX_HIGHLY_ACTIVE);
					total_period++;
				}
				catch(FileNotFoundException fnfe)
				{
					// System.err.println("File not found!");
					change = false;
					find = false;
					total_period--;
					d = d.plusDays(1);
					day = d.getDayOfMonth(); month = d.getMonthValue();
					n2 = preffix+month+"."+day+ext;
					continue;
				}
				break;
			}
			if(!find)
				break;
			
			if(change)
			{
				change_times++;
			}
			
			if(change && inSeq)
			{
				inSeq = false;
				if(len+1 < min_len)
					min_len = len+1;
				len = 0;
			}
			else if(change && !inSeq)
			{
				min_len = 1;
				break;
			}
			else if(!change && !inSeq)
			{
				len = 1;
			}
			else if(!change && inSeq)
			{
				len ++;
			}
		}
		
		if(min_len < Integer.MAX_VALUE)
		{
			// System.out.println("change!!");
			change_period.add(min_len);
			change_percentage.add( ((double)change_times)/total_period);
			return true;
		}
		
		return false;
	}

	static int analysis(File dir)
	{
		System.out.println("In folder: " + dir.getName());
		if(dir.getName().equals("js"))
		{
			st_mon = 3; st_day = 10;
		}
		else if(dir.getName().equals("html"))
		{
			st_mon = 3; st_day = 21;
		}
		else
		{
			st_mon = 4; st_day = 10;
		}
		ArrayList<Integer> change_period = new ArrayList<Integer>();
		ArrayList<Double> change_percentage = new ArrayList<Double>();
		for (File sub_dir : dir.listFiles())
		{
			if (!sub_dir.isDirectory() || sub_dir.getName().startsWith("."))
				continue;
			cmp(sub_dir, change_period, change_percentage);
			// System.out.println("in for: "+change_period);
		}
		
		// .DS_STORE
		System.out.println("Change number: "+change_period.size()+"/"+(dir.listFiles().length-1));
		System.out.println(change_period);
		System.out.println(change_percentage);
		return change_period.size();
	}

	public static void main(String args[]) throws IOException
	{
		System.err.println("FIXME: add year 2016!!");
		
		/* now = LocalDate.now();
		
		File dir = new File(DIR);
		if (!dir.isDirectory())
		{
			System.err.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(1);
		}

		for (File sub_dir : dir.listFiles())
		{
			// in folder alexa/: the html files, no timestamp
			if (!sub_dir.isDirectory() || sub_dir.getName().equals("alexa")
					|| sub_dir.getName().startsWith("."))
			{
				continue;
			}
			analysis(sub_dir);
		} */
	}
}
