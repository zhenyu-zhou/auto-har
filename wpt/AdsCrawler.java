package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class AdsCrawler
{
	static final String[] SRC = {
			"http://g-ecx.images-amazon.com/images/G/01/kindle/merch/2015/campaign/FTVS/GoT/FTVS-GoT-GW-Static2-4500x900._UX1500_SX1500_V309315233_.jpg",
			"http://g-ecx.images-amazon.com/images/G/01/AMAZON_FASHION/2015/EDITORIAL/SPRING_2/WOMEN/CLOTHING/SHOPS/DRESS/PERIPH/V3/0305_W_SWM_V2_2._V328014512_.png",
			"http://ecx.images-amazon.com/images/I/51aB76ZnoQL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51Tdmu3B3RL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/5145FNNM1BL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51F2PKoiIXL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51LJ5Suh1UL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51b4wghf9cL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51jhxb1s9dL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/514lM1aOhRL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/416m9dLYscL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41yR7yObl1L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41V5KqNudLL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51Y9SbTKJoL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51GtRZK9JaL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/517blCGZzDL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51BWO%2BLoJ2L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51dEHEkZATL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51sf7y8LKpL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51eKtdNNwRL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41RouO%2BYEgL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51y0vhWnKoL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41nk4I%2BmeKL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51Jv813HYkL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/514POUv7J0L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41mHhxbPKXL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51Mv49tQTEL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41bc1ZpWjHL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41Blpj1s5SL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41dT8iGo5UL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51%2ByvgTCGWL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41f0l9CPtDL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/411bYPHReSL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/21HDJy1roBL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51eb9jcY5-L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/31tjqSA8vHL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/419Eg%2BwL9YL._AC_SY220_.jpg",
			"http://g-ecx.images-amazon.com/images/G/01/marketing/prime/gateway/primebb_listen._UX440_SX440_V330663157_.png",
			"http://g-ecx.images-amazon.com/images/G/01/img15/home-improvement/billboards/17884_us_home-improvement_spring-cleaning_b_1320x600._UX440_SX440_V309739619_.png",
			"http://ecx.images-amazon.com/images/I/41y0AZedlsL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41rMfUQjRcL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41Misifb8UL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41qIULouRhL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51ZylErOGCL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41XpOa2CxkL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/51IL4qWeIdL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41mGjx2MGOL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/31XwFB6neLL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/31SiuDywhyL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/310MWsgh6RL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/419Ldr5R77L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/414eRMmAaGL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/510r-7NMXzL._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41GWmH2up3L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/41K6vDXQQ7L._AC_SY220_.jpg",
			"http://ecx.images-amazon.com/images/I/419sHzDdd9L._AC_SY220_.jpg", };

	static final String ROOT = "/Users/zzy/Documents/script/ads/";
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

			HttpUtil.downloadFileWithTimeout(ROOT + name + "/" + fullname, SRC[i], 5000);
		}
	}
}
