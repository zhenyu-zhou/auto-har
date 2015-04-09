package wpt;

import java.io.*;
import java.util.concurrent.*;
import java.lang.InterruptedException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test
{
	public static void main(String[] args) throws IOException
	{
		FirefoxProfile profile = new FirefoxProfile();

		File firebug = new File("/Users/zzy/Documents/eclipse/WPT/bin/firebug-3.0.0-alpha.9.xpi"); // ("firebug-2.0.8-fx.xpi");
		File netExport = new File("/Users/zzy/Documents/eclipse/WPT/bin/netExport-0.9b7.xpi");

		try
		{
			profile.addExtension(firebug);
			profile.addExtension(netExport);
		} catch (IOException err)
		{
			System.out.println(err);
		}

		// Set default Firefox preferences
		profile.setPreference("app.update.enabled", false);

		String domain = "extensions.firebug.";

		// Set default Firebug preferences
		profile.setPreference(domain + "currentVersion", "2.0");
		profile.setPreference(domain + "allPagesActivation", "on");
		profile.setPreference(domain + "defaultPanelName", "net");
		profile.setPreference(domain + "net.enableSites", true);

		// Set default NetExport preferences
		profile.setPreference(domain + "netexport.alwaysEnableAutoExport", true);
		profile.setPreference(domain + "netexport.showPreview", false);
		profile.setPreference(domain + "netexport.defaultLogDir",
				"/Users/zzy/Documents/har/");

		WebDriver driver = new FirefoxDriver(profile);

		try
		{
			// Wait till Firebug is loaded
			Thread.sleep(5000);

			System.out.println("heh");
			
			// Load test page http://optimus.cs.duke.edu/youtube/
			// driver.get("http://www.facebook.com");
			driver.get("http://www.baidu.com");

			// Wait till HAR is exported
			Thread.sleep(10000);

		} catch (InterruptedException err)
		{
			System.out.println(err);
		}

		driver.quit();
	}
}
