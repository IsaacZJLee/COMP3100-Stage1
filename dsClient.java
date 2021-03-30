import java.io.*;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class dsClient {
        public static Server[] readXML(Server[] dsServerArray){
        try {
			File systemXML = new File("/home/rhynefong/Desktop/ds-sim/src/pre-compiled/ds-system.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(systemXML);

			doc.getDocumentElement().normalize();
			NodeList dsServers = doc.getElementsByTagName("server");
			dsServerArray = new Server[dsServers.getLength()];
        }
}