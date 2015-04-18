package wprof;

public class Computation implements Comparable<Computation>
{
	public double st, end;
	
	public Computation(double s, double e)
	{
		if(s > e)
			System.err.println("WARNING: time disorder: "+s+"-"+e);
		st = s;
		end = e;
	}
	@Override
	public String toString()
	{
		return "Computation: "+st+" - "+end+"\n";
	}
	@Override
	public int compareTo(Computation o)
	{
		return Double.compare(st, o.st);
	}
}
