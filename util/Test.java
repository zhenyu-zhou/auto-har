package util;

public class Test
{
	public static void main(String[] args)
	{
		Pair<Integer, Integer> p = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> p3 = new Pair<Integer, Integer>(1, 1);
		System.out.println(p.equals(p));
		System.out.println(p.equals(p2));
		System.out.println(p.equals(p3));
	}
}
