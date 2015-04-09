package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class CSSCrawler
{
	static final String[] SRC = {
		"http://z-ecx.images-amazon.com/images/G/01/AUIClients/NavAuiAssets-dc70fff419ccb09c3875f29a31cce545c3a51506.min._V2_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/browser-scripts/wcs-ya-homepage-beaconized/wcs-ya-homepage-beaconized-530192997._V1_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/authportal/flex/reduced-nav/ap-flex-reduced-nav-2.1._V343920894_.css",
		"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/tmpMasterDPMergedCSS-US/tmpMasterDPMergedCSS-US-10142221368._V1_.css",
		"http://z-ecx.images-amazon.com/images/G/01/x-locale/redirect-overlay/redirect-overlay-nav-20140702._V348573361_.css",
		"http://z-ecx.images-amazon.com/images/G/01/AUIClients/AmazonGatewayAuiAssets-a786a388ae95ec26afaeb1d9694929a6e7d98b0c.weblab-GW_FRESH_SP_V1_44083-T1.min._V2_.css",
		"http://z-ecx.images-amazon.com/images/G/01/AUIClients/AmazonUI-aa934ce2b8aed108efc75d1fcbe9d30f93c29757.rendering_engine-not-trident.min._V2_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/browser-scripts/wcs-ya-order-history-beaconized/wcs-ya-order-history-beaconized-207125090._V1_.css",
		"http://z-ecx.images-amazon.com/images/G/01/x-locale/redirect-overlay/redirect-overlay-lte-ie9._V336734515_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/authportal/common/css/ap-checkout-frn._V319237959_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/authportal/common/css/ap_global._V318885489_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/x-locale/common/errors-alerts/error-styles-ssl._V219086192_.css",
		"https://images-na.ssl-images-amazon.com/images/G/01/orderApplication/css/authPortal/sign-in._V392399058_.css",
		"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/us-site-wide-css-beacon/site-wide-6800426958._V1_.css",
		"http://g-ecx.images-amazon.com/images/G/01/AUIClients/RetailSearchAssets-d49897e93c01440b3a4708055e5552e9914ea238.renderskin-pc.search-results-aui.min._V2_.css",
		"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/search-css/search-css-2679208869._V1_.css",
		"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/clickWithinSearchPageStatic/clickWithinSearchPageStatic-1948474073._V1_.css",
		"http://g-ecx.images-amazon.com/images/G/01/AUIClients/RetailSearchAssets-45732a89af7b039052e401220a7440b5c500fc67.renderskin-pc.min._V2_.css",
		"http://g-ecx.images-amazon.com/images/G/01/AUIClients/AmazonUI-70c8a390b71e1f111edf98815d8acb7f70399874.rendering_engine-not-trident.min._V2_.css",
		"http://su.bdimg.com/static/message/css/message_33dce38c.css",
		"http://su.bdimg.com/static/newsplus/css/news_min_fullflow_992c3266.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yB/r/t4TeAztttHv.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y0/r/RnFbanWnAeY.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yH/r/wNsM5hThrWL.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yn/r/xfu5hFgG8lE.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yi/r/URzxzs8nONJ.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yh/r/6xXAEB0hanw.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yH/r/47U7eV_bULF.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yq/r/xzmuaa7IitV.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yY/r/e1qsnO4PnD_.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yg/r/k5J7DpXaX3K.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yc/r/iSOz5RcSpbV.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yN/r/N8rToar1rLV.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yz/r/H3hgBh6EmzG.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y8/r/sCmVwwP8rP0.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yZ/r/fZc82hCdFtX.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yr/r/cDw4Jfb0YxX.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yo/r/8jZTRyKa0YD.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yI/r/0F6F6wwJ4NC.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yz/r/38nA8I_OnH9.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yP/r/ITDfmU_-brn.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yR/r/qrZJCzc-c9i.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yF/r/18MvgpeONMP.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y6/r/Akr678__ONP.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y8/r/XrCEpkx89Qd.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yX/r/Xk_3pkQzGDs.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yj/r/NkvCndPO9j2.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y4/r/7gDvLJpRn1t.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yE/r/zHorSIne8Fc.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yl/r/lnBWoYQF0au.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yP/r/qwZ1HUcyKNv.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yZ/r/jfHan2dGhVx.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yP/r/3j7QJpaVjD4.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yT/r/9VH_JUXAn_G.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yP/r/fgpvCqXtUmv.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yG/r/_kgFdK2MeQQ.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yT/r/bUkl1MIQ2Qr.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yA/r/F-KO8EU63Jm.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yS/r/nKv4iAfbjke.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/ya/r/BKWeEtyF6JH.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yt/r/95SAfeb07vM.css",
		"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y5/r/7wQ62oL_URv.css",
		"https://wallet.google.com/inapp/frontend/css/play.css",
		"https://ssl.gstatic.com/ac/kids/wallet-3844e0e74e688e5d85ce7581b1f89859.css",
		"http://mat1.gtimg.com/www/css/qq2012/hot_word_sogou.css",
		"http://g.tbcdn.cn/tb/global/3.3.35/global-min.css",
		"http://a.tbcdn.cn/??app/tms/others/global/searchbox_new.css",
		"http://g.tbcdn.cn/tb/press/2.2.2/layouts/990-20-50-10.css",
		"http://atp.alicdn.com/tmse/1/dpl/??oversea-homepage-first-nav/v4/oversea-homepage-first-nav.css",
		"http://atp.alicdn.com/tmse/1/dpl/??5538/oversea-homepage-first-nav/oversea-homepage-first-nav/v4/skin/default.css",
		"http://g.tbcdn.cn/tbc/search-suggest/1.3.6/new_suggest-min.css",
		"http://g.tbcdn.cn/sea/play/0.0.15/pages/overlay/page/index-min.css",
		"http://g.tbcdn.cn/kg/countdown/2.0.0/index.css",
		"http://g.tbcdn.cn/tbc/favorite/1.0.8/index-min.css",
		"http://g.tbcdn.cn/sea/discover/0.0.4/p/discover/index.css",
		"https://abs.twimg.com/a/1425532665/css/t1/twitter_core.bundle.css",
		"https://abs.twimg.com/a/1425532665/css/t1/twitter_logged_out.bundle.css",
		"https://abs.twimg.com/a/1425532665/css/t1/twitter_more_1.bundle.css",
		"https://abs.twimg.com/a/1425532665/css/t1/twitter_more_2.bundle.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/myy/advance_base_0.0.39.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/myy/font_0.0.8.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/theme/yglyphs-legacy_0.0.4.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/myy/advance_grid_0.0.10.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/myy/video_styles_0.0.19.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/sda/fp_sda_0.0.1.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/sda/sda_advance_0.0.4.css",
		"https://s.yimg.com/zz/combo?nn/lib/metro/g/header/header_0.0.2.css",
		"https://s.yimg.com/zz/combo?/os/stencil/3.1.0/styles-ltr.css",
		"https://s.ytimg.com/yts/cssbin/www-player-2x-vflk_k92d.css",
		"https://s.ytimg.com/yts/cssbin/www-core-2x-vfl-V2wnC.css",
		"https://s.ytimg.com/yts/cssbin/www-home-c4-2x-vflPCaSo0.css",
		"https://s.ytimg.com/yts/cssbin/www-pageframe-2x-vflau09JF.css",
		"https://s.ytimg.com/yts/cssbin/www-guide-2x-vfl6w5bHK.css",
		"https://s.ytimg.com/yts/cssbin/www-pageframedelayloaded-2x-vflLhLBXe.css",
		"https://s.ytimg.com/yts/cssbin/www-watch-transcript-2x-vfl9-k0Vc.css",
		"https://s.ytimg.com/yts/cssbin/www-results-2x-vfl9Z2RlL.css",
		"http://s.ytimg.com/yts/cssbin/www-createchannel-2x-vflGeg32u.css",
		"http://s.ytimg.com/yts/cssbin/www-feedprivacydialog-2x-vflOUKZaj.css",
		"http://s.ytimg.com/yts/cssbin/www-ypc-2x-vflQu3wia.css"
		};

static final String ROOT = "/Users/zzy/Documents/script/css/";
static HashSet<String> dup = new HashSet<String>();
static MapCount<String> mc = new MapCount<String>();

public static void main(String args[]) throws IOException
{
	Calendar cal = Calendar.getInstance();
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DATE);
	System.out.println(month+"."+day);

	for (int i = 0; i < SRC.length; i++)
	{
		System.out.println("Processing... "+i+" "+(i+1.0)/SRC.length*100+"%: "+SRC[i]);
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
			if(mc.containsKey(name))
			{
				mc.add(name);
				name += "zzy"+mc.get(name);
			}
			else
			{
				mc.add(name); mc.add(name);
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
