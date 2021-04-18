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

	// Sending message to server
	public static void send(byte[] message) {
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

                    //create server object to store all properties from the xml file/

                    dsServer server_obj = new dsServer(j, type, limit, bootupTime, hourlyRate, coreCount, memory, disk); 
                    dsServerArray.add(server_obj); // add server object to dsServerArray
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void runJobs(){
        try {
            dsServerArray = new ArrayList<>(); // initialising the new dsServerArray

            //sends HELO to server
            byteServerMsg = HELO.getBytes();  
            send(byteServerMsg);

            // expecting Server to reply OK

           // authorizing client
            byteServerMsg = AUTH_username.getBytes();
            send(byteServerMsg);

            // expecting Server to reply OK

            readDSXML(); // fetching the list of servers available
            dsServer largestServerType = allToLargest(dsServerArray); // fetching the largest server from the list

            // Client signals server for a job 
            byteServerMsg = REDY.getBytes();
            send(byteServerMsg);

            // server sends JOBN

            inputStream.skip(OK.length() * 2); // ignoring the first two OK commands sent by the server
            charServerMsg = new char[charSMsgSize]; // intialising charServerMsg of size "charSMsgSize"
            inputStream.read(charServerMsg); // storing message from the server into charServerMsg

	Boolean jobFinished = false;
	while (!jobFinished) {
                if ((stringServerMsg = String.valueOf(charServerMsg)).contains("NONE")){ //stop the job process when server sends NONE
                    jobFinished = true;
                     
                }
                else if (stringServerMsg.contains(JOBN) || stringServerMsg.contains(JOBP)) {
                    fieldServerMsg = stringServerMsg.split(" "); // if job already exists, assign the job into different fields of JOBN
                   
                    dsJob ScheduleJob = new dsJob(fieldServerMsg); // initialising new scheduling job
                    ScheduleJob.printJobDetails();

                    /* SCHEDULE JOB */
                    String scheduleString = SCHD + " " + ScheduleJob.id + " " + largestServerType.type + " " + largestServerType.sID+"\n";
                    byteServerMsg = scheduleString.getBytes();
                    send(byteServerMsg);

                    // Send REDY for the next job
                    byteServerMsg = REDY.getBytes();
                    send(byteServerMsg);

                    inputStream.skip(OK.length()); // skip OK to proceed to the next job
                    charServerMsg = new char[charSMsgSize];
                    inputStream.read(charServerMsg); // read message from server

                } else if (stringServerMsg.contains(JCPL)) { 
                    System.out.printf("Job completed for %s%n.", stringServerMsg); //prints the details of the server and job type

                    // Signal REDY message to other jobs waiting 
                    byteServerMsg = REDY.getBytes();
                    send(byteServerMsg);

                    // reset charServerMsg to read the next job
                    charServerMsg = new char[charSMsgSize];
                    inputStream.read(charServerMsg); // read message from server
                }
            }
        } catch (EOFException e) {
            System.out.println("EOF(End of File) Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } 
    }
	//this function identifies the largest server based by Core
    public static dsServer allToLargest(ArrayList<dsServer> s) { 
        dsServer largestServerType = s.get(0);

        for (int i = 1; i < s.size(); i++) {
            if (s.get(i).coreCount >= largestServerType.coreCount) {
                largestServerType = s.get(i);
            }
        }
        return largestServerType;
    }

    public static void main(String[] args) throws IOException {
        Socket s = new Socket(host_name, server_Port); // creating a new socket connection to server 
        inputStream = new InputStreamReader(s.getInputStream());
        outputStream = new DataOutputStream(s.getOutputStream());
        
        runJobs();
        
        System.out.println("CLOSING CONNECTION...");
        
        byteServerMsg = QUIT.getBytes();
        send(byteServerMsg);
        
        System.out.println("CONNECTION CLOSED.");

        outputStream.close();
        s.close();
    }
	
	    //this function identifies the largest server based by Core
    public static dsServer allToLargest(ArrayList<dsServer> s) { 
        dsServer largestServerType = s.get(0);

        for (int i = 1; i < s.size(); i++) {
            if (s.get(i).coreCount >= largestServerType.coreCount) {
                largestServerType = s.get(i);
            }
        }
        return largestServerType;
    }

	
}
