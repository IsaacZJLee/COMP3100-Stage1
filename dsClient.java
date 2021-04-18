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

	
	
}
