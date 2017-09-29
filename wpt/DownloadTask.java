package wpt;

import java.util.concurrent.Callable;

import util.HttpUtil;

public class DownloadTask implements Callable<Void>
{
	private final String path, url;
	
	public DownloadTask(String p, String u)
	{
		path = p;
		url = u;
	}

	@Override
	public Void call() throws Exception
	{
		HttpUtil.downloadFile(path, url);
		return null;
	}

}
