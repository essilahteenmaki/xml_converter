package com.parser.dataparser.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	
	public static void parse() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {			
		DocumentBuilder builder = factory.newDocumentBuilder();
		File file = createFile();
		Document document = builder.parse(file);
		//test(document);
		test2(document);		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	

	public static void test (Document doc ) {
		List<Premise> premises = new ArrayList<Premise>();		
        Element element = doc.getDocumentElement();        
        NodeList nodeList = element.getElementsByTagName("item");
        
        for (int i = 0; i<nodeList.getLength(); i++) {
        	Node node = nodeList.item(i);
            Element nodeElement = (Element) node;
    		NodeList nL = nodeElement.getElementsByTagName("property");
    		String address = nL.item(3).getTextContent();
    		int id = Integer.parseInt(nL.item(1).getTextContent().trim());
    		String city = nL.item(5).getTextContent();
    		String m2 = nL.item(10).getTextContent();
    		/*
    		int temp = nL.item(24).getChildNodes().getLength();
    		for (int j = 0; j < temp; j++) {
    			String premisetype = nL.item(24).getChildNodes().item(j).getLastChild().getTextContent();
    		}
    		*/
			premises.add(new Premise(id, address, city, m2));
        }

		for (Premise premise : premises) {
			System.out.println(premise.toString());
		}
        		
	}
	
	public static void test2 (Document doc ) {
		List<Premise> premises = new ArrayList<Premise>();		
        Element element = doc.getDocumentElement();        
        NodeList items = element.getElementsByTagName("item");
        
		for (int i=0; i < items.getLength(); i++) {
			Node premiseNode = items.item(i);
			Element premise = (Element) premiseNode;
			NodeList properties = premise.getElementsByTagName("property"); 
					
			for (int j=0; j < properties.getLength(); j++) {
				//System.out.println(properties.getLength());
				Node propertyNode = properties.item(j);
				Element property = (Element) propertyNode;
				String address = getValue(property, "street");
				String city = getValue(property, "town");
				String m2 = getValue(property, "totalaream2");
				String premisetype = getSubvalues(property, "realtytypeoption2");
				String imglink = getSubvalues(property, "image");
				//Integer id = Integer.parseInt(getValue(property, "cust_itemcode"));
				
				try {
					/*
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("street")) {
						String street = property.getTextContent();
						System.out.println(street);
					}
					
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("town")) {
						String city = property.getTextContent();
						System.out.println(city);
					}
					
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("totalaream2")) {
						String m2 = property.getTextContent();
						System.out.println(m2);
					}
					
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("cust_itemcode")) {
						Integer id = Integer.parseInt(property.getTextContent().trim());
						System.out.println(id);
					}
					
					
					
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("realtytypeoption2")) {
						StringBuilder temp = new StringBuilder();
						NodeList types = property.getChildNodes();
						for (int k=0; k<types.getLength(); k++) {
							temp.append(types.item(k).getFirstChild().getTextContent() + " ");
						}
						String premisetype = temp.toString();
						System.out.println(premisetype);
					}
					
*/
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("text")) {
						NodeList textproperties = property.getChildNodes();
						String premisedescription = textproperties.item(1).getChildNodes().item(1).getTextContent();
						System.out.println("txtprops: " + textproperties.getLength());
						System.out.println("txtprops: " + property.getTextContent());
					}
	/*				
					if((property.getAttributes().getNamedItem("name").getTextContent()).equals("image")) {
						StringBuilder temp = new StringBuilder();
						NodeList imgproperties = property.getChildNodes();
						for (int l=0; l< imgproperties.getLength(); l++) {
							temp.append(imgproperties.item(l).getLastChild().getTextContent() + ",");
						}
						String images = temp.toString();
						System.out.println(images);
					}
					
	*/				
					
				} catch (NullPointerException ne) {
					ne.printStackTrace();
				}					
			}
						
		}
 
        
/*


		for (Premise premise : premises) {
			System.out.println(premise.toString());
		}
*/    		
	}
	
	public static String getValue(Element property, String item) {
		String value = "";		
		if (property != null) {
			try {
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals(item)) {
					String tempvalue = property.getTextContent().trim();
					byte[] bytes = tempvalue.getBytes(StandardCharsets.UTF_8);
					value = new String(bytes, StandardCharsets.UTF_8);
					System.out.println(value);
				}
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}
	
	public static String getSubvalues(Element property, String item) {
		String value = "";				
		if((property.getAttributes().getNamedItem("name").getTextContent()).equals(item)) {
			StringBuilder temp = new StringBuilder();
			NodeList subproperties = property.getChildNodes();
			
				if (item.equals("realtytypeoption2")) {
					for (int i=0; i<subproperties.getLength(); i++) {
						temp.append(subproperties.item(i).getFirstChild().getTextContent() + " ");
					}
				}
				
				if(item.equals("image")) {
					for (int l=0; l< subproperties.getLength(); l++) {
						temp.append(subproperties.item(l).getLastChild().getTextContent() + ",");
					}					
				}
			
			value = temp.toString();
			System.out.println(value);
		}
		return value;
	}
	
	
	
	//w. java.io
	public static File createFile() throws Exception {
		final String address = "xx";
		URL url = new URL(address);
		File xmlFile = File.createTempFile("tempxmlfile", null); 
		
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		OutputStream os = new FileOutputStream(xmlFile);
		String line;
		
		while ((line = br.readLine()) != null) {
			os.write(line.getBytes());
		}
		os.close();
		br.close();
		
		return xmlFile;
	}	
	
}
