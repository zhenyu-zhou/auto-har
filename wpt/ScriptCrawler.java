package wpt;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import util.HttpUtil;
import util.MapCount;

public class ScriptCrawler
{
	static final String ROOT = "/Users/zzy/Documents/script/new/";
	static final String[] SRC = {
			"http://www.google-analytics.com/analytics.js",
			"https://apis.google.com/js/api.js",
			"https://ssl.gstatic.com/accounts/o/2006856415-postmessagerelay.js",
			"https://oauth.googleusercontent.com/gadgets/js/core:rpc:shindig.random:shindig.sha1.js",
			"http://pagead2.googlesyndication.com/pagead/js/lidar.js",
			"https://s0.2mdn.net/879366/dfa7banner_flash_inpage_rendering_lib_200_64.js",
			"https://c.betrad.com/surly.js",
			"http://c.betrad.com/a/n/273/27745.js",
			"https://pixel.adsafeprotected.com/rjss/st/33642/3584127/skeleton.js",
			"http://c.betrad.com/amazon/js/page.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/GenericObservableJS-01e038760277ea2c820d295a81fb0bf7973a9d36.min._V2_.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/AmazonGatewayAuiAssets-7ff56b3a4ab0eb51eb936544e05dd4f8d7b9a027.weblab-GW_FRESH_SP_V1_44083-T1.min._V2_.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/AmazonUI-32c78c544514cec375cd0b74419d160e07483468.rendering_engine-not-trident.min._V2_.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/Nav-236382bbb028d6df36c297e8d48833e054d6baad._V2_.js",
			"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/DA-us/DA-us-4122292015._CB329094140_.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/NavAuiAssets-b44a725c6d7b36bd230e678cca06ffb1efd18cd9.min._V2_.js",
			"http://z-ecx.images-amazon.com/images/G/01/browser-scripts/csm-all/csm-all-min-2162140038._V1_.js",
			"http://z-ecx.images-amazon.com/images/G/01/AUIClients/P13NClientSideLoggingAuiJS-6c6c05fb326f27b899f398ee2154f0d36b41edc8.min._V2_.js",
			"http://dew9ckzjyt2gn.cloudfront.net/sf/DAsf-1.5.js",
			"http://su.bdimg.com/static/superplus/js/lib/jquery-1.10.2_d88366fd.js",
			"http://s1.bdstatic.com/r/www/cache/static/global/js/all_instant_search1_4e1989ed.js",
			"http://su.bdimg.com/static/superplus/js/sbase_6ae84319.js",
			"http://su.bdimg.com/static/superplus/js/min_super_32f4e80e.js",
			"http://su.bdimg.com/static/xcard/js/xmancard_043d4a6e.js",
			"http://su.bdimg.com/static/tipsplus/js/min_tips_88ae85a8.js",
			"http://su.bdimg.com/static/activity/js/activity_start_f7a2402b.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yA/r/ERU-2HnHMyd.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yZ/r/Z087mEOIS5Q.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yC/r/dhKnITzOAfI.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yh/r/3cZSwe9tZb6.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/ye/r/_1q0FGCQsBQ.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yu/r/Nr162AUZSW5.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yF/r/nqOXbI1916I.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yJ/r/1WcvPyn_85Q.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y9/r/D-_vypIzjIX.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yM/r/eWXQZf8ZnrV.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y9/r/aF2SQJZIUWE.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yS/r/f9coq4KOjWv.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yj/r/M87aic1_DN2.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yq/r/36lFP8LxECM.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yT/r/Ji8gyblpcLp.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yK/r/EVO61kewNyk.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y_/r/08YHtzUknGv.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yf/r/JvK_C44obT2.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yd/r/Qal89YYmYf_.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yc/r/kTSRiZ7IPDz.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yX/r/G0exRDT48jq.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yE/r/67aORTdbb0o.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yO/r/k4ZOTju1TgT.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y0/r/lDSGQMW2c_3.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yB/r/US_IuySJnmi.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yC/r/MVevh_n5s5c.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yz/r/EcVzMsUR1Ku.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yW/r/B5MaKomU95P.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/ys/r/18SWwEdpi_k.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yI/r/UfpUQNMAMco.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/ye/r/angQ2bk7B8U.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yU/r/FpUaS7yeNhL.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yN/r/kwAOu8WsuA6.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yr/r/31MQLYkBfpl.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y5/r/rpiBNPYNO4R.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yM/r/q4rBOoawRY9.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yg/r/b4KwsplVeXd.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y7/r/Q6-aRrSxfMb.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yI/r/S2AKFeeoHrW.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/ys/r/HMomlvcfLEG.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yy/r/scXqO8fw8Nq.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yx/r/IoNQwQj6Inx.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yl/r/h9CSm_nrIzf.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yO/r/NTTPoVhwD8d.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yb/r/R98ZM3xCJ7A.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y7/r/bSj-6O9BZig.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yN/r/ig9wl4uhWEj.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y1/r/YWCaL0-TlZC.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yd/r/-t8-Q6K39bs.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yZ/r/xpF8fVokej_.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yw/r/oUJqA-_inkT.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yn/r/705iapzaGgE.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yy/r/qWACa2ACTqC.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yS/r/F1hTT9_rRI4.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/y0/r/Tltc63jRvKW.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yD/r/b3E1Dbg9AlL.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yI/r/iO7vm3_BByT.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yh/r/ckDCl5D_Azo.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yL/r/l87QIQ2F96R.js",
			"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yG/r/fFDIu2q2EgI.js",
			"http://www.google.com/extern_chrome/7f6ee556b910ab39.js",
			"https://ssl.google-analytics.com/ga.js",
			"http://www.gstatic.com/commerce/inapp/gwt/2bf705b8e4bdee452799fface5d52bfa/4DA6D98697F2FE76E18CCC7579552E3D.cache.js",
			"https://apis.google.com/js/platform.js",
			"http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js",
			"http://www.gstatic.com/feedback/session_load.js",
			"http://www.google.com/tools/feedback/chat_load.js",
			"https://wallet.google.com/inapp/lib/buy.js",
			"http://cdn.tanx.com/t/tanxclick.js",
			"http://cdn.tanx.com/t/tanxssp/main.js",
			"http://cdn.tanx.com/t/tanxssp.js",
			"http://qzonestyle.gtimg.cn/qzone/biz/ac/comm/gdtlib.20141222.js",
			"http://qzonestyle.gtimg.cn/qzone/biz/ac/comm/ver.20141105.js",
			"http://static.paipaiimg.com/js/adAlterData3.js",
			"http://qzs.qq.com/qzone/biz/gdt/comm/etg_showcase.js",
			"http://qzonestyle.gtimg.cn/qzone/biz/gdt/display/modules/mod/stat_32327a0.js",
			"http://qzonestyle.gtimg.cn/qzone/biz/gdt/display/modules/mod/match_9bd4cf7.js",
			"http://qzs.qq.com/qzone/biz/comm/js/qbs.js",
			"http://static.paipaiimg.com/js/version/2014/10/pp.etg.wingdt2.20141027.js",
			"http://js.data.auto.qq.com/car_public/template/serial_py.js",
			"http://jqmt.qq.com/cdn_dianjiliu.js",
			"http://ra.gtimg.com/sc/mo_ping-min.js",
			"http://pingjs.qq.com/ping.js",
			"http://ra.gtimg.com/web/crystal/v2.3Beta02Build020/crystal-min.js",
			"http://mat1.gtimg.com/www/asset/lib/jquery/jquery/jquery-1.11.1.min.js",
			"https://abs.twimg.com/c/swift/en/bundle/frontpage.0e9a5e77700f5eddbbd977f00fa24567e9ee0330.js",
			"https://abs.twimg.com/c/swift/en/init.7a56c9a1649b04168e618643df6c480a8ef58368.js",
			"https://s0.2mdn.net/879366/dfa7banner_flash_inpage_rendering_lib_200_67.js",
			"https://s.yimg.com/lq/lib/3pm/cs_0.2.js",
			"https://s.yimg.com/zz/combo?yui:platform/intl/0.1.4/Intl.min.js&yui:platform/intl/0.1.4/locale-data/jsonp/en-US.js",
			"https://s.yimg.com/zz/combo?yui:3.18.0/datatype-xml-parse/datatype-xml-parse-min.js&yui:3.18.0/io-xdr/io-xdr-min.js&yui:3.18.0/escape/escape-min.js&os/mit/td/stencil-3.1.0/stencil-imageloader/stencil-imageloader-min.js&os/mit/td/dust-helpers-0.0.140/moment/moment-min.js&os/mit/td/dust-helpers-0.0.140/intl-messageformat/intl-messageformat-min.js&os/mit/td/dust-helpers-0.0.140/dust-helper-intl/dust-helper-intl-min.js&os/mit/td/dust-helpers-0.0.140/intl-helper/intl-helper-min.js&os/mit/td/dust-helpers-0.0.140/dust/dust-min.js&os/mit/td/stencil-3.1.0/stencil-selectbox/stencil-selectbox-min.js&os/mit/td/stencil-3.1.0/stencil-source/stencil-source-min.js&os/mit/td/stencil-3.1.0/stencil-tooltip/stencil-tooltip-min.js&os/mit/td/ape-applet-0.0.202/ape-applet-templates-reload/ape-applet-templates-reload-min.js&os/mit/td/ape-af-0.0.318/af-content/af-content-min.js&os/mit/td/ape-applet-0.0.202/af-applet-savesettings/af-applet-savesettings-min.js&os/mit/td/ape-af-0.0.318/media-rapid-tracking/media-rapid-tracking-min.js&/nn/lib/metro/g/myy/advance_desktop_0.0.4.js&/nn/lib/metro/g/myy/video_manager_0.0.83.js",
			"https://s.yimg.com/zz/combo?yui:3.18.0/autocomplete-list/lang/autocomplete-list_en.js&yui:3.18.0/widget-base/widget-base-min.js&yui:3.18.0/autocomplete-list/autocomplete-list-min.js&yui:3.18.0/text-data-wordbreak/text-data-wordbreak-min.js&yui:3.18.0/event-mousewheel/event-mousewheel-min.js&yui:3.18.0/event-key/event-key-min.js&yui:3.18.0/event-hover/event-hover-min.js&yui:3.18.0/event-outside/event-outside-min.js&yui:3.18.0/event-move/event-move-min.js&yui:3.18.0/event-flick/event-flick-min.js&yui:3.18.0/querystring-parse/querystring-parse-min.js",
			"https://s.yimg.com/zz/combo?os/mit/td/stencil-3.1.0/stencil-fx/stencil-fx-min.js&os/mit/td/stencil-3.1.0/stencil-fx-collapse/stencil-fx-collapse-min.js&os/mit/td/ape-af-0.0.318/gallery-storage-lite/gallery-storage-lite-min.js&yui:3.18.0/anim-base/anim-base-min.js&yui:3.18.0/anim-color/anim-color-min.js&yui:3.18.0/anim-xy/anim-xy-min.js&yui:3.18.0/anim-curve/anim-curve-min.js&yui:3.18.0/anim-easing/anim-easing-min.js&yui:3.18.0/anim-node-plugin/anim-node-plugin-min.js&yui:3.18.0/anim-scroll/anim-scroll-min.js&os/mit/td/stencil-3.1.0/stencil-gallery/stencil-gallery-min.js&os/mit/td/ape-location-1.0.7/af-locations/af-locations-min.js&os/mit/td/ape-location-1.0.7/ape-location-templates-location-list/ape-location-templates-location-list-min.js&os/mit/td/ape-location-1.0.7/ape-location-templates-location-panel/ape-location-templates-location-panel-min.js&os/mit/td/stencil-3.1.0/stencil-toggle/stencil-toggle-min.js&os/mit/td/ape-location-1.0.7/af-location-panel/af-location-panel-min.js&os/mit/td/ape-location-1.0.7/ape-location-lang-strings_en-us/ape-location-lang-strings_en-us-min.js&os/mit/td/ape-pipe-0.0.62/comet/comet-min.js&os/mit/td/ape-pipe-0.0.62/af-comet/af-comet-min.js&os/mit/td/ape-pipe-0.0.62/af-pipe/af-pipe-min.js",
			"https://s.yimg.com/zz/combo?yui:3.18.0/io-form/io-form-min.js&yui:3.18.0/io-upload-iframe/io-upload-iframe-min.js&yui:3.18.0/queue-promote/queue-promote-min.js&yui:3.18.0/io-queue/io-queue-min.js&yui:3.18.0/async-queue/async-queue-min.js&yui:3.18.0/jsonp-url/jsonp-url-min.js",
			"https://s.yimg.com/rx/builds/4.84.0.1424402566/videoplayer-nextgen-flash-min.js",
			"https://s.yimg.com/rq/darla/2-8-6/js/g-r-min.js",
			"https://s.yimg.com/zz/combo?yui:/3.18.0/yui/yui-min.js&/ss/rapid-3.24.js&/os/mit/td/aperollup-min-87c1a6e1_desktop.js&/os/mit/td/td-applet-custom-header-0.1.392/r-min.js",
			"https://s.yimg.com/aj/lh-0.9.js",
			"https://s.yimg.com/zz/combo?os/mit/media/p/content/content-aft-min-6769898.js&/os/mit/media/p/content/yaft2-aftnoad-min-e5cc02b.js",
			"https://s.yimg.com/rx/builds/4.84.0.1424402566/assets/streamsense.min.js",
			"https://pagead2.googlesyndication.com/pagead/osd.js",
			"http://pagead2.googlesyndication.com/pagead/show_ads.js",
			"https://pagead2.googlesyndication.com/pagead/js/r20150225/r20150224/show_ads_impl.js",
			"https://s0.2mdn.net/879366/dfa7banner_flash_inpage_rendering_lib_200_58.js",
			"http://c.betrad.com/a/n/321/8575.js",
			"http://s.ytimg.com/yts/jsbin/spf-vfloWUwJA/spf.js",
			"http://s.ytimg.com/yts/jsbin/www-en_US-vflss31LC/base.js"
	};

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
			System.out.println("Processing... "+i+" "+(i+1.0)/SRC.length*100+"%");
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
			// System.out.println(fullname);
			// System.out.println(ext);
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
