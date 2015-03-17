package util;

/**
 * A paper in google scholar
 * 
 * @author zzy
 */
public class GoogleScholarPaper implements Comparable<GoogleScholarPaper>
{
	/** Paper name */
	private String name;
	/** Number of citation */
	private int citation;
	/** The downloading url */
	private String url;
	/** Information about author */
	private String author;
	/** A brief abstract */
	private String abstra;
	/** Publication year of the paper */
	private int year;
	/** Number of version */
	private int version;

	public GoogleScholarPaper(String n, String u, String au, String ab, int c,
			int v)
	{
		name = n;
		citation = c;
		url = u;
		author = au;
		year = extractYear();
		abstra = ab;
		version = v;
	}

	private int extractYear()
	{
		String ret = "";
		int num = 0;
		for (int i = 0; i < author.length(); i++)
		{
			char c = author.charAt(i);
			if (Character.isDigit(c))
			{
				num++;
				ret += c;
				if (num == 4) break;
			}
			else
			{
				num = 0;
				ret = "";
			}
		}
		if (ret.length() == 4)
		{
			return Integer.parseInt(ret);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public String toString()
	{
		String ret = "<b>Name:</b> " + name + "\n";
		if (url != null) // citation type
			ret += "<b>Link:</b> " + url + "\n";
		ret += "<b>Author:</b> " + author + "\n";
		ret += "<b>Abstract:</b> " + abstra;
		if(!abstra.endsWith("\n"))
			ret	+= "\n";
		if (citation >= 0) ret += "<b>Cited by:</b> " + citation + "\n";
		if (year > 0) ret += "<b>Year:</b> " + year + "\n";
		if (version >= 0) ret += "<b>Version:</b> " + version + "\n";
		return ret;
	}

	public String getName()
	{
		return name;
	}

	public int getCitation()
	{
		return citation;
	}

	public String getUrl()
	{
		return url;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getAbstract()
	{
		return abstra;
	}

	public int getYear()
	{
		return year;
	}

	public int getVersion()
	{
		return version;
	}

	@Override
	public int compareTo(GoogleScholarPaper p)
	{
		double deltaV = 0, deltaY = 0, deltaC = 0;

		if (this.getVersion() >= 0 && p.getVersion() >= 0)
			deltaV = this.getVersion() - p.getVersion();
		else if (this.getVersion() >= 0)
			deltaV = this.version / 10.0;
		else if (p.getVersion() >= 0) deltaV = -p.getVersion() / 10.0;

		if (this.getYear() > 0 && p.getYear() > 0)
			deltaY = this.getYear() - p.getYear();

		if (this.getCitation() >= 0 && p.getCitation() >= 0)
			deltaC = this.getCitation() - p.getCitation();
		else if (this.getCitation() >= 0)
			deltaC = this.getCitation() / 10.0;
		else if (p.getCitation() >= 0) deltaC = -p.getCitation() / 10.0;

		double score = deltaV * 3 + deltaY * 1 + deltaC * 5;
		int ret = Double.compare(0, score);
		return ret;
	}
}
