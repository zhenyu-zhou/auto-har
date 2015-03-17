package util;

import java.util.TreeSet;

/**
 * Get the top n elements by natrual order.
 * @author zzy
 * @param <T> The type of elements maintained by this set
 * @see TreeSet
 */
public class TopNSet<T> extends TreeSet<T>
{
	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = 5089927445124049335L;
	/**
	 * The maximum size of this set.
	 */
	private int num;
	
	/**
	 * Create a new set
	 * @param n
	 * 		The size of this set. More elements will be cut.
	 */
	public TopNSet(int n)
	{
		super();
		num = n;
	}
	
	public void setNum(int n)
	{
		num = n;
	}
	
	@Override
	public boolean add(T t)
	{
		boolean ret = super.add(t);
		if(this.size() > num)
			this.pollLast();
		return ret;
	}
}
