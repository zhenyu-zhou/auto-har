package wpt;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import util.LoadingTime;

public class ChromeTimging
{
	//static String script = "oFso = new ActiveXObject(\"Scripting.FileSystemObject\");  oFile = oFso.OpenTextFile(\"/Users/zzy/try/chrome.txt\",2,true); oFile.WriteLine(\"菩提本无树，明镜亦非台，本来无一物，何处惹尘埃!\");      oFile.Close();";

	public static void getPerformance() throws InterruptedException
	{
		// Optional, if not specified, WebDriver will search your path for
		// chromedriver.
		System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");
		
		LoadingTime lt = new LoadingTime();
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://www.google.com/");
		// Thread.sleep(5000); // Let the user actually see something!
		/*WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();*/
		
		// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html
		lt.domainLookupStart = (Long)driver.executeScript("return window.performance.timing.domainLookupStart;");
		lt.domainLookupEnd = (Long)driver.executeScript("return window.performance.timing.domainLookupEnd;");
		lt.connectStart = (Long)driver.executeScript("return window.performance.timing.connectStart;");
		lt.secureConnectionStart = (Long)driver.executeScript("return window.performance.timing.secureConnectionStart;");
		lt.connectEnd = (Long)driver.executeScript("return window.performance.timing.connectEnd;");
		lt.requestStart = (Long)driver.executeScript("return window.performance.timing.requestStart;");
		lt.responseStart = (Long)driver.executeScript("return window.performance.timing.responseStart;");
		lt.responseEnd = (Long)driver.executeScript("return window.performance.timing.responseEnd;");
		
		long dns = lt.domainLookupEnd - lt.domainLookupStart;
		long ssl;
		if(lt.secureConnectionStart < 0)
			ssl = -1;
		else
			ssl = lt.connectEnd - lt.secureConnectionStart;
		long connect = lt.connectEnd - lt.connectStart;
		long request = lt.responseStart - lt.requestStart;
		long response = lt.responseEnd - lt.responseStart;

		// Object o = driver.executeScript("return chrome.loadTimes()");
		Thread.sleep(5000); // Let the user actually see something!
		driver.quit();
		
		// ((JavascriptExecutor) driver).executeAsyncScript(
	}

	public static void main(String args[]) throws InterruptedException
	{
		getPerformance();
	}
}
