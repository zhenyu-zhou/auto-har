package wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import wprof.Computation;
import wprof.Resource;

/**
 * Generate dependency graphs from WProf log
 * 
 * @author zzy
 *
 */
public class DependencyGraphGenerator
{
	static final String PATH = "/Users/zzy/Documents/google_wprof";
	static ArrayList<Resource> resources = new ArrayList<Resource>();
	static ArrayList<Computation> computations = new ArrayList<Computation>();

	public static void main(String args[]) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(
				new File(PATH)));
		String temp = null;

		while ((temp = reader.readLine()) != null)
		{
			// System.out.println("line: "+temp);
			String[] tokens = temp.split("[{}:\"]", 0);
			// System.out.println(tokens[0]);
			ArrayList<String> arr = delEmpty(tokens);
			if (arr == null)
				continue;
			// System.out.println(arr);
			if (arr.get(0).equals("Resource"))
			{
				int index = arr.indexOf("requestTime");
				if (index > 0)
				{
					double s = Double.parseDouble(arr.get(index + 1));
					index = arr.indexOf("url");
					String url = null;
					if (index > 0)
					{
						url = arr.get(index + 1);
						if (url.contains("http") || url.contains("data"))
						{
							url += ":" + arr.get(index + 2);
						}
					}
					resources.add(new Resource(s, url));
				}
			}
			else if (arr.get(0).equals("Computation"))
			{
				int index = arr.indexOf("startTime");
				if (index > 0)
				{
					double s = Double.parseDouble(arr.get(index + 1));
					double e = Double.parseDouble(arr.get(index + 3));
					computations.add(new Computation(s, e));
				}
			}
			else //ReceivedChunk
			{
				int index = arr.indexOf("receivedTime");
				if(index > 0)
				{
					double e = Double.parseDouble(arr.get(index + 1));
					resources.get(resources.size()-1).setEnd(e);
				}
			}

		}
		reader.close();

		Collections.sort(resources);
		Collections.sort(computations);

		for(int i = 0; i < resources.size(); i++)
		{
			Resource r = resources.get(i);
			int j = -1, k = -1;
			for (j = 0; j < computations.size(); j++)
			{
				if(computations.get(j).st >= r.st)
				{
					break;
				}
			}
			if(j == computations.size())
				continue;
			Computation c = computations.get(j);
			for(k = i+1; k < resources.size(); k++)
			{
				if(resources.get(k).st >= c.end && resources.get(k).father_idx < 0)
				{
					break;
				}
			}
			if(k == resources.size())
				continue;
			resources.get(k).setFather(i);
		}
		
		System.out.println(resources);
		System.out.println(computations);
	}

	static ArrayList<String> delEmpty(String[] tokens)
	{
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++)
		{
			tokens[i] = tokens[i].replace(",", "");
			if (tokens[i] != null && tokens[i].trim().length() > 0)
				ret.add(tokens[i]);
		}
		if (!(ret.get(0).equals("Computation") || ret.get(0).equals("Resource") || ret
				.get(0).equals("ReceivedChunk")))
			return null;
		return ret;
	}
}
