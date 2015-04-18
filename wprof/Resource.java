package wprof;

public class Resource implements Comparable<Resource>
{
	public double st, end;
	public String url;
	public int father_idx;

	public Resource(double s, String u)
	{
		st = s;
		url = u;
		end = -1;
		father_idx = -1;
	}

	public void setFather(int f)
	{
		father_idx = f;
	}

	public void setEnd(double e)
	{
		end = e;
	}

	@Override
	public String toString()
	{
		String ret = "resource: "+ "  at: " + st + " - " + end; // + url 
		if(father_idx > 0)
			ret += " depend to "+father_idx;
		ret += "\n";
		return ret;
	}

	@Override
	public int compareTo(Resource o)
	{
		return Double.compare(st, o.st);
	}
}
