package wpt;

import java.io.IOException;
import java.util.Calendar;

import util.HttpUtil;

public class HTMLCrawler
{
	static final String[] SRC = {
			"https://www.google.com/?gws_rd=ssl/index.html",
			"https://www.facebook.com/index.html",
			"https://www.youtube.com/index.html",
			"http://www.baidu.com/index.html",
			"https://www.yahoo.com/index.html",
			"http://en.wikipedia.org/wiki/Main_Page/index.html",
			"http://www.amazon.com/index.html",
			"https://twitter.com/index.html",
			"http://www.taobao.com/market/global/index_new.php",
			"http://www.qq.com/index.html",
			"https://www.google.co.in/?gws_rd=ssl/index.html",
			"https://www.linkedin.com/index.html",
			"https://www.live.com/index.html",
			"http://www.sina.com/index.html", "http://www.weibo.com/login.php",
			"http://www.yahoo.co.jp/index.html",
			"http://www.tmall.com/index.html",
			"https://www.google.co.jp/?gws_rd=ssl/index.html",
			"http://www.google.de/?gws_rd=ssl/index.html",
			"http://www.ebay.com/index.html",
			"https://www.blogger.com/index.html",
			"http://www.hao123.com/index.html",
			"http://www.reddit.com/index.html",
			"http://www.bing.com/index.html",
			"https://www.google.co.uk/?gws_rd=ssl/index.html" };

	static final String[] SITE = { "Google", "Facebook", "Youtube", "Baidu",
			"Yahoo", "Wiki", "Amazon", "Twitter", "Taobao", "QQ",
			"Google_India", "Linkedin", "Live", "Sina", "Weibo", "Yahoo_Japan",
			"Tmall", "Google_Japan", "Google_German", "Ebay", "Blogger",
			"Hao123", "Reddit", "Bing", "Google_UK" };
	static final String ROOT = "/Users/zzy/Documents/script/html/";

	public static void main(String args[]) throws IOException
	{
		if (SRC.length != SITE.length)
		{
			System.err.println("No corresponding site!");
			System.exit(1);
		}

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);

		System.out.println(month + "." + day);

		for (int i = 0; i < SRC.length; i++)
		{
			System.out.println("Processing... " + i + " " + (i + 1.0)
					/ SRC.length * 100 + "%: " + SRC[i]);
			String[] tokens = SRC[i].split("[/\\\\]+");
			String fullname = tokens[tokens.length - 1];
			int index = fullname.lastIndexOf(".");
			if (index < 0)
			{
				System.err.println("zzy no ext name: " + fullname);
				continue;
			}
			String name = fullname.substring(0, index);
			String ext = fullname.substring(index);
			fullname = name + "-" + year + "." + month + "." + day + ext;

			HttpUtil.downloadFileWithTimeout(ROOT + SITE[i] + "/" + fullname, SRC[i], 5000);
		}
	}
}
