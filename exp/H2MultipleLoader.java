package exp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import util.Util;
import wpt.ObjectGenerator;

public class H2MultipleLoader
{
	static final String BASE_URL = "https://skylynx.cs.duke.edu:8080/benchmark_zzy/";
	static final String PATH = "/Users/zzy/try/benchmark_zzy/multiple/";
	static long[] time = new long[ObjectGenerator.MAX+1];
	
	static void print(String s, String path) throws IOException
	{
		System.out.println(s);
		if(!s.endsWith("\n"))
			s += "\n";
		Util.writeFile(path, s);
	}
	
	public static void getPerformance(String url, int num) throws InterruptedException, IOException
	{
		System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");

		Runtime run = Runtime.getRuntime();
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(40000, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(2000000, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().setScriptTimeout(60000, TimeUnit.SECONDS);
		
		driver.get(url);
		
		// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html
		// skip DNS
		long connectStart = (Long) driver
				.executeScript("return window.performance.timing.connectStart;");
		long loadEventEnd = (Long) driver
				.executeScript("return window.performance.timing.loadEventEnd;");
		
		long total_time = loadEventEnd - connectStart;
		time[num] = total_time;
		System.out.println(num+": "+total_time);
		
		Thread.sleep(3000);
		driver.quit();
	}
	
	public static void getMultiplePerformance(int n1, int n2) throws IOException, InterruptedException
	{
		ChromeDriver d1 = new ChromeDriver(), d2 = new ChromeDriver();
		String url1 = BASE_URL+n1+".txt", url2 = BASE_URL+n2+".txt";
		d1.manage().timeouts().pageLoadTimeout(40000, TimeUnit.SECONDS);
		d1.manage().timeouts().implicitlyWait(2000000, TimeUnit.MILLISECONDS);
		d1.manage().timeouts().setScriptTimeout(60000, TimeUnit.SECONDS);
		d2.manage().timeouts().pageLoadTimeout(40000, TimeUnit.SECONDS);
		d2.manage().timeouts().implicitlyWait(2000000, TimeUnit.MILLISECONDS);
		d2.manage().timeouts().setScriptTimeout(60000, TimeUnit.SECONDS);
		
		d1.get(url1);
		d2.get(url2);
		
		// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html
		// skip DNS
		long connectStart = (Long) d1
				.executeScript("return window.performance.timing.connectStart;");
		long loadEventEnd = (Long) d1
				.executeScript("return window.performance.timing.loadEventEnd;");
		long connectStart2 = (Long) d2
				.executeScript("return window.performance.timing.connectStart;");
		long loadEventEnd2 = (Long) d2
				.executeScript("return window.performance.timing.loadEventEnd;");
		
		long time1 = loadEventEnd - connectStart;
		long time2 = loadEventEnd2 - connectStart2;
		
		print(new Long(time1-time[n1]).toString(), PATH+n1+"_"+n2+".txt");
		print(new Long(time2-time[n2]).toString(), PATH+n2+"_"+n1+".txt");
		
		Thread.sleep(3000);
		d1.quit(); d2.quit();
	}

	public static void main(String[] args) throws InterruptedException, IOException
	{
		File time_file = new File(ObjectGenerator.PATH+"time.txt");
		if(time_file.exists())
		{
			System.err.println("Delete the old time file");
			time_file.delete();
		}
		
		int upper = ObjectGenerator.MAX;
		for(int i = 1; i <= upper; i++) 
		{
			getPerformance(BASE_URL+i+".txt", i);
		}
		
		for(int i = 1; i <= upper; i++)
		{
			for(int j = i+1; j <= upper; j++)
			{
				getMultiplePerformance(i, j);
			}
		}
	}
}
