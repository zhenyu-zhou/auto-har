package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test
{
	static int n = 0;

	static void f() throws IOException
	{
		n++;
		System.out.println(n);
		if (n > 5)
			throw new IOException();
	}
	static void g(ArrayList<Integer> a, int n)
	{
		a.add(5);
		n+=10;
	}

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
		/*try
		{
			LocalDate d1 = LocalDate.of(2015, Month.MARCH, 1);
			LocalDate d2 = LocalDate.of(2008, Month.FEBRUARY, 29);
			long p2 = ChronoUnit.DAYS.between(d1, d2);
			System.out.println(p2);
		} catch (DateTimeException dte)
		{
			System.out.println(dte);
			System.out.println("zzy: invalid");
		}*/

		/*String s = "-t8-Q6K39bs-3.10.js";
		System.out.println(s);
		int idx = s.lastIndexOf('-');
		System.out.println(s.substring(0, idx+1));
		idx = s.lastIndexOf('.');
		System.out.println(s.substring(idx));*/

		/*LocalDate d = LocalDate.of(2015, 4, 3);
		LocalDate now = LocalDate.now();
		System.out.println(ChronoUnit.DAYS.between(d, now));
		while(ChronoUnit.DAYS.between(d, now) >= 0)//(m < month || (m == month && d < day))
		{
			int day = d.getDayOfMonth();
			int month = d.getMonthValue();
			System.out.println(day+" nn "+month);
			d = d.plusDays(1);
		}*/

		/*for (int i = 0; i < 10; i++)
		{
			System.out.println("i: "+i);
			while (true)
			{
				try
				{
					f();
				} catch (IOException e)
				{
					continue;
				}
				break;
			}
		}*/
		
		/*ArrayList<Integer> a = new ArrayList<Integer>();
		int num = 9;
		g(a, num);
		System.out.println(a);
		System.out.println(num);*/
		
		List<Integer> arr = Arrays.asList(1, 2);
		System.out.println(arr);
	}
}
