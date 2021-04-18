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

	dsClient(int sID, String type, int limit, int bootupTime, float hourlyRate, int coreCount, int memory, int disk) {

	}

	public static int largestServer = 0;

	public static dsServer[] readXML(dsServer[] dsServerArray) {

		try {
			File systemXML = new File("/home/rhynefong/Desktop/ds-sim/src/pre-compiled/ds-system.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(systemXML);

			doc.getDocumentElement().normalize();
			NodeList dsServers = doc.getElementsByTagName("server");
			dsServerArray = new dsServer[dsServers.getLength()];

			for (int i = 0; i < dsServers.getLength(); i++) {
				Element dsServer = (Element) dsServers.item(i);
				String type = dsServer.getAttribute("type");
				int limit = Integer.parseInt(dsServer.getAttribute("limit"));
				int bootupTime = Integer.parseInt(dsServer.getAttribute("bootupTime"));
				float hourlyRate = Float.parseFloat(dsServer.getAttribute("hourlyRate"));
				int coreCount = Integer.parseInt(dsServer.getAttribute("coreCount"));
				int memory = Integer.parseInt(dsServer.getAttribute("memory"));
				int disk = Integer.parseInt(dsServer.getAttribute("disk"));
				System.out.println(type);

				dsServer tempArr = new dsServer(i, type, limit, bootupTime, hourlyRate, coreCount, memory, disk);
				dsServerArray[i] = tempArr;
			}

			largestServer = setLargestServer(dsServerArray);
		}

		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERROR DETECTED!!");
		}

		return dsServerArray;
	}
}
