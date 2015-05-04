package util;

import java.util.Hashtable;
import java.util.Set;

/**
 * The set with number of each element
 * 
 * @author zzy
 *
 * @param <T> The type of element
 */
public class MapCount<T>
{
	private Hashtable<T, Integer> count = new Hashtable<T, Integer>();
	
	/** add an element
	 * 
	 * @param t
	 * 		添加的元素
	 * @author zzy
	 */
	public void add(T t)
	{
		if(!count.containsKey(t))
		{
			count.put(t, 1);
		}
		else
		{
			int n = count.get(t);
			n++;
			count.put(t, n);
		}
	}
	
	public boolean containsKey(T t)
	{
		return count.containsKey(t);
	}
	
	public Set<T> keySet()
	{
		return count.keySet();
	}
	
	public int get(T t)
	{
		return count.get(t);
	}
	
	public void remove(T t)
	{
		count.remove(t);
	}
	
	public void clear()
	{
		count.clear();
	}
	@Override
	public String toString()
	{
		String ret = "";
		for(T t : count.keySet())
		{
			ret += t+": "+count.get(t)+"\n";
		}
		return ret;
	}
}