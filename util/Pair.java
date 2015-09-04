package util;

/**
 * A pair consisted of two elements as an entity.
 * 
 * @author zzy
 * 
 * @param <M>
 *            The type of the first element. Used as key.
 * @param <N>
 *            The type of the second element. Used for sorting. Should be a
 *            number (double, int, etc.)
 */
public class Pair<M, N extends Number> implements Comparable<Pair<M, N>>
{
	private M m;
	private N n;

	public Pair(M m_, N n_)
	{
		m = m_;
		n = n_;
	}

	@Override
	public int compareTo(Pair<M, N> p)
	{

		if ((m.equals(p.m))) { return 0; }

		int ret = -1;
		// TopNSet doesn't allow duplicated elements
		if (n instanceof Double)
		{
			ret = Double.compare((Double) n, (Double) (p.n));
			if (ret != 0) return ret;
			return -1;
		}
		else if (n instanceof Integer)
		{
			ret = Integer.compare((Integer) n, (Integer) (p.n));
			if (ret != 0) return ret;
			return -1;
		}
		else
			// TODO: other instance such as short?
			return -1;
	}

	@Override
	public String toString()
	{
		return m + ": " + n;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Pair<?, ?>))
			return false;
		Pair p = (Pair)o;
		if(m.equals(p.m) && n.equals(p.n))
			return true;
		return false;
	}

	public M getFirst()
	{
		return m;
	}
	
	public N getSecond()
	{
		return n;
	}
	
	public void set(M m_, N n_)
	{
		m = m_;
		n = n_;
	}
}
