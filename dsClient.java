import java.io.*;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class dsClient extends dsServer {

	dsClient(int sID, String type, int limit, int bootupTime, float hourlyRate, int coreCount, int memory, int disk) {
		super(sID, type, limit, bootupTime, hourlyRate, coreCount, memory, disk);

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
