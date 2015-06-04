package wpt;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import util.AlexaSites;
import util.LoadingTime;

public class ChromeTiming
{
	static ArrayList<LoadingTime> http_time = new ArrayList<LoadingTime>(),
			https_time = new ArrayList<LoadingTime>();

	public static void getPerformance(String url) throws InterruptedException
	{
		// Optional, if not specified, WebDriver will search your path for
		// chromedriver.
		System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");
		
		LoadingTime lt = new LoadingTime();
		ChromeDriver driver = new ChromeDriver();
		driver.get(url);
		// Thread.sleep(5000); // Let the user actually see something!
		/*WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();*/
		
		// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html
		lt.domainLookupStart = (Long) driver
				.executeScript("return window.performance.timing.domainLookupStart;");
		lt.domainLookupEnd = (Long) driver
				.executeScript("return window.performance.timing.domainLookupEnd;");
		lt.connectStart = (Long) driver
				.executeScript("return window.performance.timing.connectStart;");
		lt.secureConnectionStart = (Long) driver
				.executeScript("return window.performance.timing.secureConnectionStart;");
		lt.connectEnd = (Long) driver
				.executeScript("return window.performance.timing.connectEnd;");
		lt.requestStart = (Long) driver
				.executeScript("return window.performance.timing.requestStart;");
		lt.responseStart = (Long) driver
				.executeScript("return window.performance.timing.responseStart;");
		lt.responseEnd = (Long) driver
				.executeScript("return window.performance.timing.responseEnd;");

		long dns = lt.domainLookupEnd - lt.domainLookupStart;
		long ssl;
		if (lt.secureConnectionStart < 0)
			ssl = -1;
		else
			ssl = lt.connectEnd - lt.secureConnectionStart;
		lt.connect = lt.connectEnd - lt.connectStart;
		lt.request = lt.responseStart - lt.requestStart;
		lt.response = lt.responseEnd - lt.responseStart;
		
		if(url.contains("https"))
			https_time.add(lt);
		else if(url.contains("http"))
			http_time.add(lt);

		// Object o = driver.executeScript("return chrome.loadTimes()");
		// Thread.sleep(5000); // Let the user actually see something!
		driver.quit();

		// ((JavascriptExecutor) driver).executeAsyncScript(
	}

	public static void main(String args[]) throws InterruptedException
	{
		for(int i = 0; i < AlexaSites.SITES.length; i++)
		{
			System.out.println("Loading: "+((double)(i+1)/AlexaSites.SITES.length)*100 + "%");
			System.out.println(AlexaSites.SITES[i]);
			getPerformance(AlexaSites.SITES[i]);
		}
		
		System.out.println("http:");
		double connect = 0, request = 0, response = 0;
		for(int i = 0; i < http_time.size(); i++)
		{
			connect += http_time.get(i).connect;
			request += http_time.get(i).request;
			response += http_time.get(i).response;
		}
		connect /= http_time.size();
		request /= http_time.size();
		response /= http_time.size();
		System.out.println("Connect: "+connect);
		System.out.println("Request: "+request);
		System.out.println("Response: "+response);
		
		ArrayList<Long> requesta = new ArrayList<Long>(),
				connecta = new ArrayList<Long>(),
				responsea = new ArrayList<Long>();
		System.out.println("https:");
		connect = 0; request = 0; response = 0;
		for(int i = 0; i < https_time.size(); i++)
		{
			connect += https_time.get(i).connect;
			request += https_time.get(i).request;
			response += https_time.get(i).response;
			connecta.add(https_time.get(i).connect);
			requesta.add(https_time.get(i).request);
			responsea.add(https_time.get(i).response);
		}
		connect /= https_time.size();
		request /= https_time.size();
		response /= https_time.size();
		System.out.println("Connect: "+connect);
		System.out.println("Request: "+request);
		System.out.println("Response: "+response);
		
		Collections.sort(connecta);
		Collections.sort(requesta);
		Collections.sort(responsea);
		
		// System.out.println(connecta);
		
		System.out.println("10%");
		System.out.println("Connect: "+connecta.get(connecta.size()/10));
		System.out.println("Request: "+requesta.get(requesta.size()/10));
		System.out.println("Response: "+responsea.get(responsea.size()/10));
		
		System.out.println("95%");
		System.out.println("Connect: "+connecta.get((int)(connecta.size()*0.95)));
		System.out.println("Request: "+requesta.get((int)(requesta.size()*0.95)));
		System.out.println("Response: "+responsea.get((int)(responsea.size()*0.95)));
	}
}
