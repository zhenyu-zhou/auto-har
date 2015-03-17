package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AmazonBook implements Comparable<AmazonBook>
{
	/** Book name */
	private String name;
	/** Book author */
	private String author;
	/** Book price */
	private String price_s;
	private double price;
	/** Unit of price */
	private String unit;
	/** Book URL */
	private String url;
	/** Publication date */
	private String date_s;
	private Calendar calendar;
	private int year, month;
	/** Display verbosely? */
	private static final boolean IS_DEBUG = false;
	/** The exchange rate between RMB and US dollar */
	private static final double EX_RATE = 6.3;

	public AmazonBook(String n, String a, String p, String u, String d)
	{
		int index = -1;
		for(int i = 0; i < a.length(); i++)
		{
			if(a.charAt(i) == '(')
				index = i;
		}
		
		name = n;
		author = a.substring(0, index);
		price_s = p;
		url = u;
		date_s = a.substring(index+1, a.length()-1);
		
		parsePrice();
		parseDate();
		if(calendar != null)
		{
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
		}
	}

	private void parseDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date;
		try
		{
			date = sdf.parse(date_s);
		}
		catch (ParseException e)
		{
			System.err.println("Invalid date format: " + date_s);
			calendar = null;
			e.printStackTrace();
			return;
		}
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	private void parsePrice()
	{
		String tempp = "";
		for (int i = 0; i < price_s.length(); i++)
		{
			char c = price_s.charAt(i);
			if (Character.isWhitespace(c))
				continue;
			else if (Character.isDigit(c) || c == '.')
				tempp += c;
			else if (unit == null)
				unit = new Character(c).toString();
			else
				// should only have ONE unit
				System.err.println("Unexpected price format: " + c);
		}
		price = Double.parseDouble(tempp);
	}

	@Override
	public String toString()
	{
		String ret = "<b>Name:</b> " + name + "\n";
		ret += "<b>Author:</b> " + author + "\n";
		ret += "<b>Price:</b> " + price_s + "\n";
		ret += "<b>Link:</b> " + url + "\n";
		ret += "<b>Publication date:</b> " + date_s + "\n";
		if (IS_DEBUG)
		{
			ret += "Price in number: " + price + "\n";
			ret += "Unit: " + unit + "\n";
			if(calendar != null)
			{
				ret += "Year: " + year + "\n";
				ret += "Month: " + month + "\n";
			}
		}
		return ret;
	}

	public String getName()
	{
		return name;
	}

	public String getAuthor()
	{
		return author;
	}

	public double getPrice()
	{
		return price;
	}

	public String getUnit()
	{
		return unit;
	}

	public String getDate()
	{
		return date_s;
	}
	public int getYear()
	{
		return year;
	}
	public int getMonth()
	{
		return month;
	}

	@Override
	public int compareTo(AmazonBook b)
	{
		int deltaY = this.getYear() - b.getYear();
		int deltaM = this.getMonth() - b.getMonth();
		double p1 = this.getPrice(), p2 = b.getPrice();
		// TODO: just assume only two kinds of currency
		if(this.getUnit().equals("$"))
			p1 *= EX_RATE;
		if(b.getUnit().equals("$"))
			p2 *= EX_RATE;
		double deltaP = p1-p2;
		double score = deltaP*(-0.2) + deltaY*1+deltaM*0.1;
		int ret = Double.compare(0, score);
		return ret;
	}
}
