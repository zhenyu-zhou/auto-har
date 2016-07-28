package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.SharedSecrets;
import difflib.*;

/**
 * The utility class. Contain many useful methods.
 * 
 * @author zzy
 * 
 */
@SuppressWarnings("restriction")
public class Util
{
	private static final String[] STOP = { "the", "a", "and", "to" };
	private static final String[] STOP_CH = { "张三", "李四" };
	/**
	 * The path of model, which contains the word vectors
	 */
	@SuppressWarnings("unused")
	private static final String MODEL_PATH = "I:/featureVector.txt";
	public static Hashtable<String, ArrayList<Double>> word_vec = null;

	/**
	 * Decide whether a word is actually an English word
	 * 
	 * @param w
	 *            The given word
	 * @author zzy
	 * @return <code>true</code> if the given word is an English word
	 */
	public static boolean isWord(String w)
	{
		for (int i = 0; i < w.length(); ++i)
		{
			char c = w.charAt(i);
			// Use Java API - 2014.5.7
			if (!Character.isAlphabetic(c))
				// if ((((c > 'z') || (c < 'a'))) && (((c > 'Z') || (c < 'A'))))
				return false;
		}
		return true;
	}

	/**
	 * Decide whether a word is actually a stop word
	 * 
	 * @param w
	 *            The given word
	 * @author zzy
	 * @return <code>true</code> if the given word is a stop word
	 * @see #isZhStopWord(String)
	 */
	// TODO: Use stop words list
	@Deprecated
	public static boolean isStopWord(String w)
	{
		for (int i = 0; i < STOP.length; ++i)
		{
			if (STOP[i].equals(w))
				return true;
		}
		return false;
	}

	/**
	 * Decide whether a word is actually a Chinese stop word
	 * 
	 * @param w
	 *            The given word
	 * @author zzy
	 * @return <code>true</code> if the given word is a stop word
	 * @see #isStopWord(String)
	 */
	// TODO: Use stop words list
	public static boolean isZhStopWord(String w)
	{
		for (int i = 0; i < STOP_CH.length; ++i)
		{
			if (STOP_CH[i].equals(w))
				return true;
		}
		return false;
	}

	/**
	 * Decide whether a sentence contains Chinese character
	 * 
	 * @param s
	 *            The given sentence
	 * @author zzy
	 * @return <code>true</code> if the given sentence contains Chinese
	 *         character
	 */
	public static boolean containChinese(String s)
	{
		if (s == null)
			return false;
		String test = "[\\u4E00-\\u9FA5]+";
		Pattern p = Pattern.compile(test);
		Matcher m = p.matcher(s);
		boolean result = m.find();
		return result;
	}

	/**
	 * Read a web page and get its content
	 * 
	 * @param addr
	 *            The address of the page
	 * @author zzy
	 * @return The content of the page
	 * @throws IOException
	 * @see {@link #readPage(addr, charset)}
	 */
	public static String readPage(String addr) throws IOException
	{
		URL url = new URL(addr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// zzy: the new property seems more powerful - 2014.4.25
		// connection.setRequestProperty("User-Agent",
		// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		connection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:x.x.x) Gecko/20041107 Firefox/x.x");
		InputStream is = connection.getInputStream();
		byte[] buff = new byte[1024];
		String pageContent = "";
		while (true)
		{
			int readed = is.read(buff);
			if (readed == -1)
			{
				break;
			}

			byte[] temp = new byte[readed];
			System.arraycopy(buff, 0, temp, 0, readed);
			pageContent = pageContent + new String(temp, "GB2312");
		}
		is.close();
		return pageContent;
	}

	/**
	 * Read a page and get its content with a given character set. Especially
	 * for utf-8
	 * 
	 * @param addr
	 *            The address of the page
	 * @param charset
	 *            The name of character set
	 * @author zzy
	 * @return The content of the page
	 * @throws IOException
	 * @see {@link #readPage(addr)}
	 */
	public static String readPage(String addr, String charset)
			throws IOException
	{
		// System.out.println("addr: "+addr);
		String ret = "";
		URL url = new URL(addr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// connection.setRequestProperty("User-Agent",
		// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		connection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:x.x.x) Gecko/20041107 Firefox/x.x");
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, charset);
		BufferedReader br = new BufferedReader(isr);
		String temp = null;
		while ((temp = br.readLine()) != null)
		{
			ret += temp + "\n";
		}
		return ret;
	}

	/**
	 * To sleep for a random time. Just for avoiding crawler detecting.
	 * 
	 * @param low
	 *            The minimum time to sleep
	 * @param high
	 *            The maximum time to sleep
	 * @author zzy
	 */
	public static void randomSleep(int low, int high)
	{
		System.out.println("Begin to sleep");
		Random r = new Random();
		int time = r.nextInt(high) + low;
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Finish sleeping");
	}

	/**
	 * Use the goagent proxy.
	 * 
	 * @author zzy
	 * @see #stopProxy()
	 */
	public static void startProxy()
	{
		System.out.println("INFO: Start proxy");
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "8087");
	}

	/**
	 * Stop using the goagent proxy.
	 * 
	 * @author zzy
	 * @see #startProxy()
	 */
	public static void stopProxy()
	{
		System.out.println("INFO: Stop proxy");
		System.setProperty("http.proxySet", "false");
		System.getProperties().remove("http.proxyHost");
		System.getProperties().remove("http.proxyPort");
	}

	/**
	 * Write the content to a certain file.
	 * 
	 * @param path
	 *            The path of the file
	 * @param content
	 *            The content needed to written
	 * @author zzy
	 * @throws IOException
	 */
	public static void writeFile(String path, String content)
			throws IOException
	{
		writeFile(new File(path), content);
	}

	/**
	 * Write the content to a certain file.
	 * 
	 * @param outFile
	 *            The output file
	 * @param content
	 *            The content needed to written
	 * @author zzy
	 * @throws IOException
	 */
	public static void writeFile(File outFile, String content)
			throws IOException
	{
		if (outFile.getParentFile() != null
				&& !outFile.getParentFile().exists())
			outFile.getParentFile().mkdirs();
		if (outFile.exists())
			outFile.delete();
		outFile.createNewFile();
		OutputStream os = new FileOutputStream(outFile);
		os.write(content.getBytes());
		os.close();
	}

	/**
	 * Append the content to a certain file
	 * 
	 * @param path
	 *            The path of the file
	 * @param content
	 *            The content needed to written
	 * @author zzy
	 * @throws IOException
	 */
	public static void writeFileAppend(String path, String content)
			throws IOException
	{
		writeFileAppend(new File(path), content);
	}

	/**
	 * Append the content to a certain file
	 * 
	 * @param outFile
	 *            The output file
	 * @param content
	 *            The content needed to written
	 * @author zzy
	 * @throws IOException
	 */
	public static void writeFileAppend(File outFile, String content)
			throws IOException
	{
		if (outFile.getParentFile() != null
				&& !outFile.getParentFile().exists())
			outFile.getParentFile().mkdirs();
		if (!outFile.exists())
			outFile.createNewFile();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile, true)));
		out.write(content);
		out.close();
	}

	/**
	 * Read the content of a certain file
	 * 
	 * @param path
	 *            The path of the file
	 * @author zzy
	 * @return The content of the file
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException
	{
		return readFile(new File(path));
	}

	/**
	 * Read the content of a certain file
	 * 
	 * @param f
	 *            The input file
	 * @author zzy
	 * @return The content of the file
	 * @throws IOException
	 */
	public static String readFile(File f) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String temp = null, content = "";
		while ((temp = reader.readLine()) != null)
		{
			content += temp + "\n";
		}
		reader.close();
		return content;
	}

	/**
	 * Read the content of a certain file with certain character set
	 * 
	 * @param path
	 *            The path of the file
	 * @param charset
	 *            The given character set
	 * @author zzy
	 * @see #readFile(String)
	 * @return The content of the file
	 * @throws IOException
	 */
	public static String readFile(String path, String charset)
			throws IOException
	{
		return readFile(new File(path));
	}

	/**
	 * Read the content of a certain file with certain character set
	 * 
	 * @param f
	 *            The input file
	 * @param charset
	 *            The given character set
	 * @author zzy
	 * @see #readFile(String, String)
	 * @return The content of the file
	 * @throws IOException
	 */
	public static String readFile(File f, String charset) throws IOException
	{
		FileInputStream in = new FileInputStream(f);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				charset));
		String temp = null, content = "";
		while ((temp = reader.readLine()) != null)
		{
			content += temp + "\n";
		}
		reader.close();
		return content;
	}

	/**
	 * Convert the given pair set to array by its <s>first</s> element
	 * 
	 * @since 2014.4.8 - hold the second element as well, used as weight when
	 *        comparing papers
	 * @author zzy
	 * @param set
	 *            The given set
	 * @return The result array
	 */
	// TODO: useless?
	public static ArrayList<Pair<String, Double>> PairSet2Array(
			TreeSet<Pair<String, Double>> set)
	{
		Iterator<Pair<String, Double>> p = set.iterator();
		ArrayList<Pair<String, Double>> ret = new ArrayList<Pair<String, Double>>();

		while (p.hasNext())
		{
			Pair<String, Double> pair = p.next();
			ret.add(pair);
		}

		return ret;
	}

	/**
	 * Calculate the weighted average of the given array. If the second
	 * parameter is ture, weight defines as the smaller (natrual order) element
	 * has larger weight.
	 * 
	 * @param arr
	 *            The given array
	 * @param is_nature_order
	 *            <b>True</b> if using nature order
	 * @author zzy
	 * @return The average in double format
	 */
	public static double weight_avg(ArrayList<Double> arr,
			boolean is_nature_order)
	{
		Collections.sort(arr);
		if (is_nature_order)
			Collections.reverse(arr);
		double ret = 0;
		int weight = 0;
		for (int i = 0; i < arr.size(); i++)
		{
			ret += (i + 1) * arr.get(i);
			weight += i + 1;
		}
		ret /= weight;
		return ret;
	}

	/**
	 * Remove the image of the html page
	 * 
	 * @param content
	 *            The content of the html page
	 * @author zzy
	 * @return The content after removing images
	 */
	public static String removeImg(String content)
	{
		int index = content.indexOf("<img");
		String ret = "";
		while (index != -1)
		{
			ret += content.substring(0, index);
			content = content.substring(index);
			index = content.indexOf("/>");
			content = content.substring(index + "/>".length());
			index = content.indexOf("<img");
		}
		ret += content;
		return ret;
	}

	/**
	 * Get the google search result
	 * 
	 * @param query
	 *            The key word to search
	 * @param isHTML
	 *            <b>True</b> if the return string need to be in HTML format
	 * @author zzy
	 * @return A JSON Array representing the result
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONArray googleSearch(String query, boolean isEn)
			throws IOException, JSONException
	{
		String addr = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&"
				+ "q=" + query.replace(" ", "%20") + "&userip=USERS-IP-ADDRESS";
		// System.out.println("a: "+addr);
		URL url = new URL(addr);
		URLConnection connection = url.openConnection();

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null)
		{
			builder.append(line);
		}

		JSONObject json = new JSONObject(builder.toString());
		JSONArray ja = json.getJSONObject("responseData").getJSONArray(
				"results");

		return ja;
	}

	/**
	 * Pattern for URL
	 */
	@Deprecated
	public static final Pattern URL = Pattern
			.compile(new StringBuilder()
					.append("((?:(http|https|Http|Https|rtsp|Rtsp):")
					.append("\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)")
					.append("\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_")
					.append("\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?")
					.append("((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+")
					// named host
					.append("(?:")
					// plus top level domain
					.append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])")
					.append("|(?:biz|b[abdefghijmnorstvwyz])")
					.append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])")
					.append("|d[ejkmoz]")
					.append("|(?:edu|e[cegrstu])")
					.append("|f[ijkmor]")
					.append("|(?:gov|g[abdefghilmnpqrstuwy])")
					.append("|h[kmnrtu]")
					.append("|(?:info|int|i[delmnoqrst])")
					.append("|(?:jobs|j[emop])")
					.append("|k[eghimnrwyz]")
					.append("|l[abcikrstuvy]")
					.append("|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])")
					.append("|(?:name|net|n[acefgilopruz])")
					.append("|(?:org|om)")
					.append("|(?:pro|p[aefghklmnrstwy])")
					.append("|qa")
					.append("|r[eouw]")
					.append("|s[abcdeghijklmnortuvyz]")
					.append("|(?:tel|travel|t[cdfghjklmnoprtvwz])")
					.append("|u[agkmsyz]")
					.append("|v[aceginu]")
					.append("|w[fs]")
					.append("|y[etu]")
					.append("|z[amw]))")
					.append("|(?:(?:25[0-5]|2[0-4]")
					// or ip address
					.append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]")
					.append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]")
					.append("[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}")
					.append("|[1-9][0-9]|[0-9])))")
					.append("(?:\\:\\d{1,5})?)")
					// plus option port number
					.append("(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~")
					// plus option query params
					.append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?")
					.append("(?:\\b|$)").toString());
	static String url_regex = "(\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

	/**
	 * Determine whether the string is URL
	 * 
	 * @author zzy
	 * @param s
	 *            The given string
	 * @return True if the string is URL
	 */
	@Deprecated
	public static boolean isURL(String s)
	{
		Pattern p = Pattern.compile(url_regex);
		Matcher matcher = p.matcher(s);
		return matcher.matches();
	}

	/**
	 * Extract all URLs from a given string
	 * 
	 * @author zzy
	 * @param s
	 *            The given string
	 * @return The ArrayList of all URLs
	 */
	@Deprecated
	public static ArrayList<String> getURLs(String s)
	{
		ArrayList<String> ret = new ArrayList<String>();

		// Pattern p = Pattern.compile(url_regex);
		Matcher matcher = URL.matcher(s);
		while (matcher.find())
		{
			ret.add(matcher.group());
		}
		return ret;
	}

	/**
	 * Extract all URLs from a given string
	 * 
	 * @author zzy
	 * @param s
	 *            The given string
	 * @return The HashSet of all URLs
	 */
	public static HashSet<String> extractUrls(String input)
	{
		HashSet<String> result = new HashSet<String>();

		Pattern pattern = Pattern
				.compile("\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)"
						+ "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov"
						+ "|mil|biz|info|mobi|name|aero|jobs|museum"
						+ "|travel|[a-z]{2}))(:[\\d]{1,5})?"
						+ "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?"
						+ "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?"
						+ "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)"
						+ "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?"
						+ "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*"
						+ "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

		Matcher matcher = pattern.matcher(input);
		while (matcher.find())
		{
			String temps = matcher.group();
			String[] tokens = temps.split("[/]+");
			if (temps.startsWith("/")
					|| !(tokens[tokens.length - 1].contains(".")))
				continue;
			System.out.println(tokens);
			int len = tokens.length;
			if (tokens[0].contains("http"))
				len--;
			if (len < 2)
				continue;
			result.add(temps);
		}

		return result;
	}

	/**
	 * Convert a file into separate lines
	 * 
	 * @author zzy
	 * @param filename
	 *            Path to the file
	 * @return The List of all lines
	 * @throws IOException
	 */
	private static List<String> fileToLines(String filename) throws IOException
	{
		List<String> lines = new LinkedList<String>();
		String line = "";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		try
		{
			while ((line = in.readLine()) != null)
			{
				lines.add(line);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			in.close();
		}
		return lines;
	}

	/**
	 * Determine whether the two files are the same
	 * 
	 * @author zzy
	 * @param path1
	 * @param path2
	 *            Path to the files
	 * @return True if the files are the same
	 * @throws IOException
	 */
	public static boolean isFileSame(String path1, String path2)
			throws IOException
	{
		return isFileSameWithThre(path1, path2, 0);
	}

	/**
	 * Determine whether the two files are the same with a threshold, which
	 * stands for the max lines can be different
	 * 
	 * @author zzy
	 * @param path1
	 * @param path2
	 *            Path to the files
	 * @param thre
	 *            The max number of lines can be different
	 * @return True if the files have no more than thre lines of difference
	 * @throws IOException
	 */
	public static boolean isFileSameWithThre(String path1, String path2,
			int thre) throws IOException
	{
		List<String> original = fileToLines(path1);
		List<String> revised = fileToLines(path2);

		Patch patch = DiffUtils.diff(original, revised);
		List<Delta> delta = patch.getDeltas();
		if (delta.size() <= thre)
		{
			return true;
		}
		return false;
	}

	/**
	 * Get the file descriptor as an integer from a given FileDescriptor
	 * 
	 * @author zzy
	 * @param fd
	 *            The given FileDescriptor instance
	 * @return The integer value
	 */
	@Deprecated
	public static int getFD(FileDescriptor fd)
	{
		return SharedSecrets.getJavaIOFileDescriptorAccess().get(fd);
	}

	/**
	 * Get the file descriptor as an integer from an input stream
	 * 
	 * @author zzy
	 * @param is
	 *            The given InputStream instance
	 * @return The integer value
	 * @throws IOException
	 */
	@Deprecated
	public static int getFD(InputStream is) throws IOException
	{
		return getFD(((FileInputStream) is).getFD());
	}

	/**
	 * Get the file descriptor as an integer from an output stream
	 * 
	 * @author zzy
	 * @param os
	 *            The given OutputStream instance
	 * @return The integer value
	 * @throws IOException
	 */
	@Deprecated
	public static int getFD(OutputStream os) throws IOException
	{
		return getFD(((FileOutputStream) os).getFD());
	}

	// http://www.programcreek.com/java-api-examples/index.php?source_dir=android-ssl-master/mitm/src/main/java/uk/ac/cam/gpe21/droidssl/mitm/socket/SocketUtils.java
	private static final Class<?> SSL_SOCKET_IMPL;
	private static final Field SSL_SOCKET_INPUT;
	private static final Field FD;
	static
	{
		try
		{
			SSL_SOCKET_IMPL = Class.forName("sun.security.ssl.SSLSocketImpl");

			SSL_SOCKET_INPUT = SSL_SOCKET_IMPL.getDeclaredField("sockInput");
			SSL_SOCKET_INPUT.setAccessible(true);

			FD = FileDescriptor.class.getDeclaredField("fd");
			FD.setAccessible(true);
		} catch (NoSuchFieldException | ClassNotFoundException ex)
		{
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Get socket file descriptor for any types of socket, including SSL ones
	 * 
	 * @param socket
	 *            The given socket
	 * @return The file descriptor of the given socket
	 * @throws IOException
	 */
	public static int getFileDescriptor(Socket socket) throws IOException
	{
		try
		{
			Object in;
			if (socket instanceof SSLSocket)
			{
				/*
				 * Socket.getInputStream() on an SSLSocket returns the 
				 * InputStream which reads the decrypted data from a buffer in 
				 * memory - in this case, we read the private 
				 * SSLSocketImpl.sockInput field with reflection to get at the 
				 * InputStream which is backed by a file descriptor. 
				 */
				in = SSL_SOCKET_INPUT.get(socket);
			}
			else
			{
				in = socket.getInputStream();
			}

			if (!(in instanceof FileInputStream))
				throw new IOException(
						"sockInput is not an instance of FileInputStream");

			FileInputStream fin = (FileInputStream) in;
			FileDescriptor fd = fin.getFD();

			return FD.getInt(fd);
		} catch (IllegalAccessException ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Generate the string for echo command to print Example: echo he says:
	 * "hello" -> echo "he says: \"hello\""
	 * 
	 * @author zzy
	 * @param s
	 *            The original string, not including echo. Example: he says:
	 *            "hello"
	 * @return The string after escaping
	 */
	public static String echoCMD(String s)
	{
		s = s.replaceAll("!", "\"'!'\"");
		s = "\"" + s + "\"";
		return s;
	}
	
	/**
	 * Call a method with its name using reflection
	 * 
	 * @author zzy
	 * @param caller
	 * 		The caller instance
	 * @param m
	 * 		The name of the method
	 * @param para
	 * 		The parameter list. Could be set to null if there are no parameters.
	 * @return
	 * 		The return value of the method, if any
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object runMethod(Object caller, String m, Object[] para)
	{
		try
		{
			Class c = Class.forName(caller.getClass().getName());

			if (para == null)
			{
				Method method = c.getMethod(m);
				return method.invoke(caller);
			}
			else
			{
				Class[] para_type = new Class[para.length];
				for (int i = 0; i < para.length; i++)
				{
					para_type[i] = getClass(para[i]);
				}
				Method method = c.getMethod(m, para_type);
				return method.invoke(caller, para);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get the class of the given object
	 * 
	 * @author zzy
	 * @param o
	 * 		The given object
	 * @return
	 * 		The class of given object, including primitive types
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private static Class getClass(Object o) throws IllegalArgumentException,
			IllegalAccessException
	{
		Class c = o.getClass();

		// Take care of the primitive types: TYPE field in the wrapper class
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields)
		{
			if (f.getName().equals("TYPE"))
			{
				c = (Class) f.get(o);
				break;
			}
		}

		return c;
	}

	/**
	 * Get an instance from the given class by name
	 * 
	 * @author zzy
	 * @param name
	 * 		The class name
	 * @param para
	 * 		The parameter for constructor. Could be null if no parameters.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getObject(String name, Object[] para)
	{
		try
		{
			Class c = Class.forName(name);
			if (para == null)
			{
				Constructor con = c.getConstructor();
				return con.newInstance();
			}
			else
			{
				Class[] para_type = new Class[para.length];
				for (int i = 0; i < para.length; i++)
				{
					para_type[i] = getClass(para[i]);
				}
				Constructor con = c.getConstructor(para_type);
				return con.newInstance(para);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Print debug information
	 * 
	 * @author zzy
	 * @param s
	 * 		Content to print
	 * @param logger
	 * 		Logger for the class
	 * @param debug
	 * 		Whether to input
	 */
	public static void debugPrint(String s, boolean debug)
	{
		if (debug)
		{
			System.err.println(s);
		}
	}

}