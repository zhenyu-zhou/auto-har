package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class IconCrawler
{
	static final String[] SRC = {
			// amazon
			// icon
			"http://g-ecx.images-amazon.com/images/G/01/gno/sprites/global-sprite_bluebeacon-32-v1._V327533540_.png",
			"http://g-ecx.images-amazon.com/images/G/01/amazonui/loading/loading-2x._V1_.gif",
			"http://g-ecx.images-amazon.com/images/G/01/ui/loadIndicators/loadIndicator-large._V192195480_.gif",
			"http://g-ecx.images-amazon.com/images/G/01/gno/images/general/navAmazonLogoFooter._V169459313_.gif",
			"http://g-ecx.images-amazon.com/images/G/01/amazonui/loading/loading-2x._V391853216_.gif",
			"http://g-ecx.images-amazon.com/images/G/01/amazonui/loading/loading-4x._V391853216_.gif",
			"http://g-ecx.images-amazon.com/images/G/01/productAds/ad_feedback_icon_1Xsprite.png",
			"https://images-na.ssl-images-amazon.com/images/G/01/da/adchoices/ac-topright-sprite.png",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/cs/orders/images/amazon-gcs-100._V192250695_.gif",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/checkout/signin-banner._V356015500_.gif",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/cs/ya/images/shipment_large_lt._V192250661_.gif",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/common/buttons/sign-in-secure._V192194766_.gif",
			"https://images-na.ssl-images-amazon.com/images/G/01/authportal/common/images/amznbtn-sprite03._V395592492_.png",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/cs/orders/images/btn-close._V192250694_.gif",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/communities/social/snwicons_v2._V369764580_.png",
			"http://g-ecx.images-amazon.com/images/G/01/nav2/images/gui/searchSprite._CB310247676_.png",
			"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/cs/css/images/amznbtn-sprite03._V387356454_.png",
			"https://images-na.ssl-images-amazon.com/images/G/01/authportal/common/images/amazon_logo_no-org_mid._V153387053_.png",
			// ads?
			/*
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
			"http://ecx.images-amazon.com/images/I/419sHzDdd9L._AC_SY220_.jpg",
			*/
			// baidu
			"http://www.baidu.com/img/bd_logo1.png",
			"http://www.baidu.com/img/baidu_jgylogo3.gif",
			"http://s1.bdstatic.com/r/www/cache/static/global/img/icons_3bfb8e45.png",
			"http://s1.bdstatic.com/r/www/cache/static/global/img/quickdelete_9c14b01a.png",
			// facebook
			"https://fbcdn-dragon-a.akamaihd.net/hphotos-ak-xap1/t39.2365-6/851565_602269956474188_918638970_n.png",
			"https://fbcdn-dragon-a.akamaihd.net/hphotos-ak-xap1/t39.2365-6/851585_216271631855613_2121533625_n.png",
			"https://fbcdn-dragon-a.akamaihd.net/hphotos-ak-xap1/t39.2365-6/851558_160351450817973_1678868765_n.png",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yS/r/CqMoYkNKHFk.png",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y2/r/67IXCwLd_ai.png",
			// google
			"https://www.google.com/images/nav_logo195.png",
			"https://www.google.com/images/srpr/logo11w.png",
			"https://ssl.gstatic.com/gb/images/i1_71651352.png",
			"https://www.google.com/textinputassistant/tia.png",
			"https://www.gstatic.com/android/market_images/web/play_logo_x2.png",
			"https://ssl.gstatic.com/gb/images/v1_376447c3.png",
			"https://ssl.gstatic.com/android/market_images/web/loading_dark_small.gif",
			// qq
			"http://mat1.gtimg.com/www/images/qq2012/sogouSearchLogo20140629.png",
			"http://mat1.gtimg.com/www/images/qq2012/erweimaNewsPic.png",
			"http://mat1.gtimg.com/www/images/qq2012/erweimaVideoPic2.png",
			"http://mat1.gtimg.com/www/images/qq2012/erweimaWeishi.png",
			"http://mat1.gtimg.com/www/images/qq2012/loading.gif",
			"http://mat1.gtimg.com/www/images/qq2012/alphabg.png",
			"http://mat1.gtimg.com/www/images/qq2012/qqlogo_1x.png",
			"http://mat1.gtimg.com/www/images/qq2012/qqbg_1.6.1.png",
			"http://mat1.gtimg.com/www/images/qq2012/sogouSearchBtn.png",
			"http://mat1.gtimg.com/www/images/qq2012/loginGrayIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/dyIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/mailIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/qzoneIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/weiboIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/chanelNavBeta2_141013.jpg",
			"http://mat1.gtimg.com/www/images/qq2012/navMoreActived.png",
			"http://mat1.gtimg.com/www/images/qq2012/erweimaNews1.1.png",
			"http://mat1.gtimg.com/www/images/qq2012/adScrollBtn01.png",
			"http://mat1.gtimg.com/www/images/qq2012/adScrollBtn02.png",
			"http://mat1.gtimg.com/www/images/qq2012/shzyhxjzg141011.png",
			"http://mat1.gtimg.com/www/images/qq2012/bkysp20140623.png",
			"http://mat1.gtimg.com/www/images/qq2012/youchangshantie.jpg",
			"http://mat1.gtimg.com/www/images/qq2012/2022da150210.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/iconNew.png",
			"http://mat1.gtimg.com/www/images/qq2012/tabBg01_0702.png",
			"http://mat1.gtimg.com/www/images/qq2012/tabBg02_0702.png",
			"http://mat1.gtimg.com/www/images/qq2012/qqbg_1.5.5.png",
			"http://mat1.gtimg.com/www/images/qq2012/productNavBg1.5.516.png",
			"http://mat1.gtimg.com/www/images/qq2012/productNavBg1.4.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/astroIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/yunshi.png",
			"http://mat1.qq.com/www/images/allskin/wmlogo.gif",
			"http://mat1.qq.com/www/images/200709/home_b.gif",
			"http://mat1.gtimg.com/www/images/qq2012/cxrz5.png",
			"http://mat1.gtimg.com/www/images/qq2012/ebsIcon2.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/sethome.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/fankuiIcon.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/picFocusCurrent.png",
			"http://mat1.gtimg.com/www/images/qq2012/temp/picFocusDefault.png",
			"http://mat1.gtimg.com/www/images/qq2012/focusBtnLeftDefault.png",
			"http://mat1.gtimg.com/www/images/qq2012/focusBtnRightDefault.png",
			"http://mat1.gtimg.com/www/images/qq2012/alphabg_01.png",
			"http://mat1.gtimg.com/www/images/qq2012/guide_k_01.png",
			"http://mat1.gtimg.com/www/images/qq2012/autoPriceNum2.png",
			"http://mat1.gtimg.com/www/images/qq2012/weather/20120906/mai.png",
			"http://strip.taobaocdn.com/tfscom/T1ODhdFBRXXXb1upjX.jpg",
			// taobao
			"http://gtms01.alicdn.com/tps/i1/T1SwHiFnlkXXc.QAHh-202-55.png",
			// twitter
			"https://abs.twimg.com/b/front_page/v1/US_CA_3.jpg",
			"https://abs.twimg.com/b/front_page/v1/US_CA_4.jpg",
			// wiki
			"https://upload.wikimedia.org/wikipedia/meta/6/6d/Wikipedia_wordmark_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/3/3b/Wiktionary-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/a/aa/Wikinews-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/c/c8/Wikiquote-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/7/74/Wikibooks-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/0/00/Wikidata-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/8/8c/Wikispecies-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/2/27/Wikisource-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/a/af/Wikiversity-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/7/74/Wikivoyage-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/9/90/Commons-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/1/16/MediaWiki-logo_sister_1x.png",
			"https://upload.wikimedia.org/wikipedia/meta/f/f2/Meta-logo_sister_1x.png",
			"https://bits.wikimedia.org/images/wikimedia-button.png",
			"https://upload.wikimedia.org/wikipedia/meta/0/08/Wikipedia-logo-v2_1x.png",
			"https://upload.wikimedia.org/wikipedia/commons/b/bd/Bookshelf-40x201_6.png",
			// yahoo
			"https://s.yimg.com/dh/ap/default/141209/firefox_logo_2.png",
			"https://s.yimg.com/dh/ap/default/150403/pc_icons_btns_sprite_0403_6pm.png",
			"https://s.yimg.com/rz/l/yahoo_en-US_f_p_142x37.png",
			"https://www.yahoo.com/sy/dh/ap/default/140602/gettheapp.jpg",
			"https://s.yimg.com/cv/eng/externals/131110/a/adchoice_1.4.png",
			"https://s.yimg.com/dh/ap/default/121210/27_IE6.png",
			"https://s.yimg.com/rq/darla/i/fdb1.gif",
			// youtube
			"https://i.ytimg.com/i/-9-kyTW8ZkZNDHQJ6FgpwQ/1.jpg",
			"https://i.ytimg.com/i/L2dVYeD8YGfMWPXDV-QjUQ/1.jpg",
			"https://i.ytimg.com/i/Egdi0XIXXZ-qJOFPf4JSKw/1.jpg",
			"https://i.ytimg.com/i/OpNcN46UbXVtpKMrmU4Abg/1.jpg",
			"https://i.ytimg.com/i/3yA8nDwraeOfnYfBWun83g/1.jpg",
			"https://yt3.ggpht.com/-EpAxQmb6CHc/AAAAAAAAAAI/AAAAAAAAAAA/z6SA8TlQle4/s88-c-k-no/photo.jpg",
			"https://yt3.ggpht.com/-XlhRvNh5BL4/AAAAAAAAAAI/AAAAAAAAAAA/uCLjcidRS0E/s88-c-k-no/photo.jpg",
			"https://i.ytimg.com/i/YfdidRxbB8Qhf0Nx7ioOYw/1.jpg",
			
	};

	static final String ROOT = "/Users/zzy/Documents/script/icon/";
	static HashSet<String> dup = new HashSet<String>();
	static MapCount<String> mc = new MapCount<String>();

	public static void main(String args[]) throws IOException
	{
		Calendar cal = Calendar.getInstance();
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
			fullname = name + "-" + month + "." + day + ext;
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
