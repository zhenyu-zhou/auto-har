package wpt;

import java.io.*;
import java.lang.InterruptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

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

		String domain = "extensions.firebug@software.joehewitt.com.";

		// Set default Firebug preferences
		profile.setPreference(domain + "currentVersion", "3.0.0");
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
			
			// Load test page
			driver.get("https://www.facebook.com");

			// Wait till HAR is exported
			Thread.sleep(10000);

		} catch (InterruptedException err)
		{
			System.out.println(err);
		}

		driver.quit();
	}
}
