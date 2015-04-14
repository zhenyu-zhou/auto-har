package wpt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTest
{
	//static String script = "oFso = new ActiveXObject(\"Scripting.FileSystemObject\");  oFile = oFso.OpenTextFile(\"/Users/zzy/try/chrome.txt\",2,true); oFile.WriteLine(\"菩提本无树，明镜亦非台，本来无一物，何处惹尘埃!\");      oFile.Close();";

	public static void testGoogleSearch() throws InterruptedException
	{
		// Optional, if not specified, WebDriver will search your path for
		// chromedriver.
		System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");

		ChromeDriver driver = new ChromeDriver();
		driver.get("http://www.google.com/");
		// Thread.sleep(5000); // Let the user actually see something!
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();
		Long b = (Long)driver.executeScript("return window.performance.timing.secureConnectionStart;");
		System.out.println(b);
		b = (Long)driver.executeScript("return window.performance.timing.connectEnd;");
		System.out.println(b);
		b += 5;
		System.out.println(b);
		Thread.sleep(5000); // Let the user actually see something!
		driver.quit();
		
		// ((JavascriptExecutor) driver).executeAsyncScript(
	}

	public static void main(String args[]) throws InterruptedException
	{
		testGoogleSearch();
	}
}
