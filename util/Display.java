package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Display
{
	private static final String TAG = "Display.print(";
	
	/**
	 * Print an object with "name: value"
	 * 
	 * @param obj
	 * 		The object need to be displayed
	 */
	public static void print(Object obj)
	{
		StackTraceElement ste = new java.lang.Throwable().getStackTrace()[1];
		String lineStr = print(ste);
		String name = extract(lineStr);
		String last = toStringSupportArray(obj);
		System.out.println(name + ": " + last);
	}

	private static String toStringSupportArray(Object obj)
	{
		if (obj == null) { return null; }
		if (obj instanceof int[]) return Arrays.toString((int[]) obj);
		if (obj instanceof long[]) return Arrays.toString((long[]) obj);
		if (obj instanceof double[]) return Arrays.toString((double[]) obj);
		if (obj instanceof boolean[]) return Arrays.toString((boolean[]) obj);
		if (obj instanceof char[]) return Arrays.toString((char[]) obj);
		if (obj instanceof byte[]) return Arrays.toString((byte[]) obj);
		if (obj instanceof short[]) return Arrays.toString((short[]) obj);
		if (obj instanceof float[]) return Arrays.toString((float[]) obj);
		if (obj instanceof Object[]) { return Arrays.toString((Object[]) obj); }
		return obj.toString();
	}

	private static String extract(String lineStr)
	{
		lineStr = lineStr.trim();
		int start = lineStr.indexOf(TAG) + TAG.length();
		int end = lineStr.lastIndexOf(");");
		return lineStr.substring(start, end);
	}

	private static String print(StackTraceElement ste)
	{
		String fileName = ste.getClassName();
		fileName = "src/" + fileName.replace(".", "/") + ".java";
		int line = ste.getLineNumber();
		return getStrByLine(fileName, line);
	}

	private static String getStrByLine(String fileName, int num)
	{
		File file = new File(fileName);
		BufferedReader reader = null;
		String path;
		if (!file.exists())
		{
			String innerName = file.getName();
			int dollar = innerName.lastIndexOf("$");
			if (dollar == -1) { throw new RuntimeException("No such file!"); }
			String fullName = file.getAbsolutePath();
			fullName = fullName.replace("\\", "/");
			path = fullName.substring(0, fullName.lastIndexOf("/"));
			file = new File(path + "/" + file.getName().substring(0, dollar)
					+ ".java");
		}
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				if (line == num) { return tempString; }
				++line;
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null) try
			{
				reader.close();
			}
			catch (IOException localIOException3)
			{
			}
		}
		return null;
	}

}
