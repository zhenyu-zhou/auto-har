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

public class WebPageTest
{
	public static void main(String[] args) throws IOException
	{
		FirefoxProfile profile = new FirefoxProfile();

		File firebug = new File("firebug-2.0.8-fx.xpi");
		File netExport = new File("netExport-0.9b7.xpi");

		String url_tra = null, url_split = null;

		if (args.length == 0)
		{
			url_tra = "http://www.facebook.com";
			url_split = "http://www.facebook.com";
		}
		else if (args.length == 2)
		{
			url_tra = args[0];
			url_split = args[1];
			if (url_tra.length() * url_split.length() == 0)
			{
				System.err.println("Invalid url");
				System.exit(1);
			}
		}
		else
		{
			System.err.println("Incorrect number of arguments");
			System.exit(2);	
		}

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

		Process p = Runtime.getRuntime().exec("pwd");
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String ret = "", line = null;
		while ((line = br.readLine()) != null)
		{
			ret += line;
		}

		ret = "/Users/zzy/Documents/eclipse/WPT/bin";
		
		// Set default NetExport preferences
		profile.setPreference(domain + "netexport.alwaysEnableAutoExport", true);
		profile.setPreference(domain + "netexport.showPreview", false);
		profile.setPreference(domain + "netexport.defaultLogDir", ret + "/har");

		WebDriver driver = new FirefoxDriver(profile);

		try
		{
			// Wait till Firebug is loaded
			Thread.sleep(5000);

			// Load test page http://optimus.cs.duke.edu/youtube/
			driver.get(url_tra);
			// driver.get("http://www.baidu.com");

			// Wait till HAR is exported
			Thread.sleep(10000);

			Thread.sleep(5000);

			// Load test page http://optimus.cs.duke.edu/youtube/
			driver.get(url_split);

			// Wait till HAR is exported
			Thread.sleep(10000);

		} catch (InterruptedException err)
		{
			System.out.println(err);
		}

		driver.quit();
	}
}
