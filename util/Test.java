package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test
{
	public static void main(String[] args) throws IOException, AWTException
	{
		/*System.setProperty("webdriver.chrome.driver",
				"/Users/zzy/Downloads/chromedriver");

		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/?gws_rd=ssl");
		
		 Actions builder = new Actions(driver);
		 builder.sendKeys(Keys.chord(Keys.COMMAND, "s"));
		 builder.perform();
		 
		 Robot robot = new Robot();
		// press Ctrl+S the Robot's way
		// robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_S);
		// robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_S);
		// press Enter
		// robot.keyPress(KeyEvent.VK_ENTER);
		// robot.keyRelease(KeyEvent.VK_ENTER);*/
		String s = "41RouO+YEgL._AC_SY220_.jpg\n41RouO+YEgL._AC_SY220_.jpg\n1234\n909";
		if(s.contains("41RouO+YEgL._AC_SY220_.jpg"))
			System.out.println("hehe");
		System.out.println(s);
		s = s.replaceAll("41RouO+YEgL._AC_SY220_.jpg", "ee");
		System.out.println(s);
		s = "41RouO+YEgL_AC_SY220_.jpg";
		if(s.matches("41RouO+YEgL_AC_SY220_[.]jpg"))
			System.out.println("haha");
	}
}
