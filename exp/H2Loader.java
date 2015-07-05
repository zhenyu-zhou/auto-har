package exp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.chrome.ChromeDriver;

import util.Util;
import wpt.ObjectGenerator;

public class H2Loader
{
	static final String BASE_URL = "https://skylynx.cs.duke.edu:8080/benchmark_zzy/";
	
	public static void getPerformance(String url, int num) throws InterruptedException, IOException
	{
		System.out.println("URL: "+url);
		System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");
		
		String outPath = ObjectGenerator.PATH+"pcap/"+num+".pcap";
		// System.out.println("out path: "+outPath);
		Runtime run = Runtime.getRuntime();
		String cmd = "sudo tcpdump -w "+outPath+" src 152.3.144.156";// + " 2>&1";
		// System.out.println("cmd: "+cmd);
		Process p = run.exec(new String[]{"sh", "-c", cmd});
		
		ChromeDriver driver = new ChromeDriver();
		driver.get(url);
		
		// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html
		// skip DNS
		long connectStart = (Long) driver
				.executeScript("return window.performance.timing.connectStart;");
		long navigationStart = (Long) driver
				.executeScript("return window.performance.timing.navigationStart;");
		long loadEventEnd = (Long) driver
				.executeScript("return window.performance.timing.loadEventEnd;");
		
		long total_time = loadEventEnd - connectStart;
		System.out.println("Time: "+total_time);
		
		Thread.sleep(5000);
		driver.quit();
		run.exec("sudo pkill tcpdump");
		p.destroy();
		String out_tshark = ObjectGenerator.PATH+"tshark/"+num+".txt";
		cmd = "sudo /usr/local/bin/tshark" +" -r "+outPath+" -q -z io,phs";// > " + out_tshark;
		// System.out.println("cmd: "+cmd);
		p = run.exec(new String[]{"sh", "-c", cmd});
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String s, content = "";
		while ((s = stdInput.readLine()) != null)
		{
			content += s+"\n";
		}
		// System.out.println("content: "+content);
		Util.writeFile(out_tshark, content);
		p.destroy();
	}


	public static void main(String[] args) throws InterruptedException, IOException
	{
		for(int i = 1; i <= ObjectGenerator.MAX; i++) //ObjectGenerator.MAX
		{
			getPerformance(BASE_URL+i+".txt", i);
		}
	}
}
