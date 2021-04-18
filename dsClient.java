import java.net.*;
import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class dsClient {
	// declaring dsCLient fields
	public static String host_name = "localhost";
	public static int server_Port = 50000;

	public static String username = "group_18";
	public static String HELO = "HELO";
	public static String AUTH = "AUTH";
	public static String AUTH_username = AUTH + " " + username;
	public static String OK = "OK";
	public static String REDY = "REDY";
	public static String JOBN = "JOBN"; // Job submission information
	public static String SCHD = "SCHD"; // Scheduling decision
	public static String JOBP = "JOBP";
	public static String JCPL = "JCPL"; // Job completion
	public static String QUIT = "QUIT";

	public static char[] charServerMsg; // contains current server message in the form of chars
	public static int charSMsgSize = 100;

	public static String stringServerMsg; // contains the current server message in the form of String
	public static byte[] byteServerMsg; // contains current server message in the form of bytes
	public static String[] fieldServerMsg; // contains current server message in the form of the individual Strings in
											// the array
	public static ArrayList<dsServer> dsServerArray; // stores information from the server

	public static File dsServerXML = new File("ds-system.xml");
    
    public static InputStreamReader inputStream;
    public static DataOutputStream outputStream;

	//Sending message to server
    public static void send(byte[] message){
        try {
            outputStream.write(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void readDSXML() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(dsServerXML);

            doc.getDocumentElement().normalize();
            NodeList servers = doc.getElementsByTagName("server");

            for (int i = 0; i < servers.getLength(); i++) { // reading xml file and passing data into the correct variables
                Element server = (Element) servers.item(i);
                for (int j = 0; j < Integer.parseInt(server.getAttribute("limit")); j++) {
                    String type = server.getAttribute("type");
                    int limit = Integer.parseInt(server.getAttribute("limit"));
                    int bootupTime = Integer.parseInt(server.getAttribute("bootupTime"));
                    float hourlyRate = Float.parseFloat(server.getAttribute("hourlyRate"));
                    int coreCount = Integer.parseInt(server.getAttribute("coreCount"));
                    int memory = Integer.parseInt(server.getAttribute("memory"));
                    int disk = Integer.parseInt(server.getAttribute("disk"));

                    /create server object to store all properties from the xml file/

                    dsServer server_obj = new dsServer(j, type, limit, bootupTime, hourlyRate, coreCount, memory, disk); 
                    dsServerArray.add(server_obj); // add server object to dsServerArray
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	
}
