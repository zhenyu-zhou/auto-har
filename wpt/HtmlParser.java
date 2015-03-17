package wpt;

import java.io.*;
import java.util.*;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import util.Util;

// http://xiaobing259-163-com.iteye.com/blog/1479597
public class HtmlParser extends ParserCallback
{
	protected String base;
	protected static Vector<String> links = new Vector<String>();
	protected static Vector<String> scripts = new Vector<String>();
	final static String PATH = "/Users/zzy/Documents/pages_CPT/youtube.webarchive"; // "/Users/zzy/Downloads/a/1.html";
	protected static boolean inScript = false;
	protected static HashSet<String> domains = new HashSet<String>();

	// https://books.google.com/books?id=emcgAeq_oXgC&pg=PA324&lpg=PA324&dq=javax.swing.text.html.parser+script&source=bl&ots=Aa2pjTHAyW&sig=SG3ukGgrzDzD2UoFmB9Ss16WeaM&hl=en&sa=X&ei=WXT2VMGkOIO1sASs6YCQDQ&ved=0CB0Q6AEwADgK#v=onepage&q=javax.swing.text.html.parser%20script&f=false
	@Override
	public void handleComment(char[] data, int pos)
	{
		if (inScript)
		{
			scripts.add(new String(data));
		}
	}

	@Override
	public void handleEndTag(HTML.Tag t, int pos)
	{
		if (t == HTML.Tag.SCRIPT)
		{
			inScript = false;
		}
	}

	@Override
	public void handleText(char[] data, int pos)
	{
	}

	@Override
	public void handleError(String errorMsg, int pos)
	{
	}

	@Override
	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos)
	{
		handleStartTag(t, a, pos);
	}

	@Override
	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos)
	{
		String src = (String) a.getAttribute(HTML.Attribute.SRC);
		if (src != null)
		{
			links.addElement(src);
		}
		src = (String) a.getAttribute(HTML.Attribute.DATA);
		if (src != null)
		{
			links.addElement(src);
		}

		if (t == HTML.Tag.SCRIPT)
		{
			inScript = true;
		}
	}

	private static String getDomain(String url)
	{
		String[] tokens = url.split("[/\\\\]+");
		for (int i = 0; i < tokens.length; i++)
		{
			if (tokens[i].contains("com") || tokens[i].contains("net")
					|| tokens[i].contains("org") || tokens[i].contains("cn"))
			{
				if (!(tokens[i].contains("&") || tokens[i].contains(":") || tokens[i]
						.contains("=")))
					return tokens[i];
				else
				{
					String[] seg = tokens[i].split("[&:=]");
					for (int j = 0; j < seg.length; j++)
					{
						if (seg[j].contains("com") || seg[j].contains("net")
								|| tokens[i].contains("org")
								|| tokens[i].contains("cn"))
							return seg[j];
					}
				}
			}
		}

		System.err.println("Domain not found: " + url);
		return null;
	}

	private static void startParse(String sHtml)
	{
		try
		{
			ParserDelegator ps = new ParserDelegator();
			HTMLEditorKit.ParserCallback parser = new HtmlParser();
			ps.parse(new StringReader(sHtml), parser, true);

			// System.out.println(scripts.size());
			/*for (int i = 0; i < scripts.size(); i++)
			{
				// System.out.println(scripts.get(i));
				Pattern p = Pattern.compile("url\\(\\s*(['" + '"'
						+ "]?+)(.*?)\\1\\s*\\)"); // expression
				Matcher m = p.matcher(scripts.get(i));
				while (m.find())
				{
					String url = m.group();
					String[] tokens = url.split("'");
					url = tokens[1].trim();
					// System.out.println(url);
					links.add(url);
				}
			}*/

			// System.out.println(links.size());
			for (int i = 0; i < links.size(); i++)
			{
				// TODO
				if(links.get(i).contains(".js") || links.get(i).contains(".css"))
				{
					System.out.println("Script: "+links.get(i));
				}
				// System.out.println(links.get(i));
				String d = getDomain(links.get(i));
				if (d != null)
					domains.add(d);
			}

			// System.out.println(domains.size());
			Iterator<String> p = domains.iterator();
			while (p.hasNext())
			{
				String d = p.next();
				// System.out.println(d);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		try
		{
			System.out.println("File: " + PATH);
			String sHtml = Util.readFile(PATH);
			System.out.println("Read complete");
			startParse(sHtml);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
