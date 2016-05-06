package AgentExp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.AlexaSites;

public class HTMLHeader {

	static final String LABEL = "Content-Type: ", LABEL2 = "content-type: ";
	private static final int BUFFER_SIZE = 4096;
	
    public static String getContentType(String hostname, String agent) throws IOException {
        int port = 80;

        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        
        String ret = "", content = "";
        
        System.out.println("Host: "+hostname+", "+"agent: "+agent);

        try {
            socket = new Socket(hostname, port);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println("GET / HTTP/1.1");
            writer.println("Host: " + hostname);
            writer.println("Accept: */*");
            String agents = "User-Agent: "+agent; // Java
            writer.println(agents); // Be honest.
            writer.println(""); // Important, else the server will expect that there's more into the request.
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for (String line; (line = reader.readLine()) != null;) {
                if (line.isEmpty()) break; // Stop when headers are completed. We're not interested in all the HTML.
                // System.out.println(line);
                content += line+"\n";
                if(line.contains(LABEL) || line.contains(LABEL2))
                {
                	ret = line;
                	break;
                }
            }
            if(ret == "")
            	System.err.println("not found!!!" + content);
            
        } catch(Exception e) {}
        finally {
            if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {} 
            if (writer != null) { writer.close(); }
            if (socket != null) try { socket.close(); } catch (IOException logOrIgnore) {} 
        }
        return ret;
    }
    
    public static void getContentType2(String surl) throws IOException
    {
    	URL url = new URL(surl);
    	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	URLConnection conn = url.openConnection();
    	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
    	
    	//get all headers
    	Map<String, List<String>> map = conn.getHeaderFields();
    	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
    		System.out.println("Key : " + entry.getKey() + 
                     ",Value : " + entry.getValue());
    	}
    	
    	//get header by 'key'
    	String server = conn.getHeaderField("Content-Type");
    	System.out.println("aaa: "+server);
    }
    
    public static void downloadFile(String fileURL)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            // String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            // FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            /* int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // outputStream.write(buffer, 0, bytesRead);
            	System.out.println(new String(buffer));
            }

            // outputStream.close();*/
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    
    public static void main(String[] args) throws IOException
    {
    	ArrayList<String> agents = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(
				new File(AgentParser.ROOT+AgentParser.OUT)));
		String temp = null;
		while ((temp = reader.readLine()) != null)
		{
			agents.add(temp);
		}
		reader.close();
		/* for(String s : agents)
		{
			System.out.println("agents: "+s);
		} */
		
    	boolean find = false;
    	for(String s : AlexaSites.HOSTS)
    	{
    		for(String a : agents)
    		{
    			// System.out.println(getContentType(s, "Java"));
    			String result = getContentType(s, a);
    			if(!(result.equals("") || result.contains("text/plain") || result.contains("text/html")))
    			{
    				System.err.println(result);
    				find = true;
    			}
    			else
    				System.out.println(result);
    		}
    	}
    	
    	if(find)
    		System.out.println("find!!!");
    	else
    		System.out.println("not find");
    	
    	// getContentType2("http://stackoverflow.com/questions/37031711/how-to-find-owner-socket-of-sk-buff-in-linux-kernel/37056550#37056550");
    	// downloadFile("http://stackoverflow.com/questions/37031711/how-to-find-owner-socket-of-sk-buff-in-linux-kernel/37056550#37056550");
    }

}