package util;

public class CourseraClass
{
	/** Title of the class */
	private String title;
	/** Brief description of the class */
	private String content;
	/** URL of the class */
	private String url;

	public CourseraClass(String t, String c, String u)
	{
		title = t;
		content = c;
		url = u;
	}

	@Override
	public String toString()
	{
		String ret = "<b>Title:</b> " + title + "\n";
		ret += "<b>Link:</b> " + url + "\n";
		ret += "<b>Abstract:</b> " + content + "\n";
		return ret;
	}

	public String getTitle()
	{
		return title;
	}

	public String getContent()
	{
		return content;
	}

	public String getUrl()
	{
		return url;
	}
}
