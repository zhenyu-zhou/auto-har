package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class FlashCrawler
{
	static final String[] SRC = {
			"http://s1.bdstatic.com/r/www/cache/static/plugins/env_5202315f.swf",
			"https://www.gstatic.com/play/store/web/swf/4musicplayer.swf",
			"http://wa.gtimg.com/201405/15/Ot_D_201405154539.swf",
			"http://g.tbcdn.cn/tbc/search-suggest/1.3.6/storage.swf",
			"http://acjs.aliyun.com/flash/JSocket.swf",
			"https://s0.2mdn.net/2992003/14-0722_NET_WEB_simple_300x250_nozip.swf",
			"https://s0.2mdn.net/3717852/TWC_2014_1201_Shoppers_Triple_30_300x250.swf",
			"https://s0.2mdn.net/1297440/EveryDay_MorePoints_v3_300x250_40K.swf",
			"http://s1.bdstatic.com/r/www/cache/static/baiduia/JSocket_9a52fc3e.swf",
			"https://s.yimg.com/cv/eng/hbo/150409/b/970x60.swf",
			"https://s.yimg.com/rx/builds/5.40.0.1427999551/assets/player.swf" };

	static final String ROOT = "/Users/zzy/Documents/script/flash/";
	static HashSet<String> dup = new HashSet<String>();
	static MapCount<String> mc = new MapCount<String>();

	public static void main(String args[]) throws IOException
	{
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
			if (dup.contains(name))
			{
				System.err.println("Duplicated name: " + name);
				// continue;
				if (mc.containsKey(name))
				{
					mc.add(name);
					name += "zzy" + mc.get(name);
				}
				else
				{
					mc.add(name);
					mc.add(name);
					name += "zzy2";
				}
			}
			else
			{
				dup.add(name);
			}

			HttpUtil.downloadFile(ROOT + name + "/" + fullname, SRC[i]);
		}
	}
}
