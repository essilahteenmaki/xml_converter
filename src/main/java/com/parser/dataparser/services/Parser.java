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
	
	public static void parseXml() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {			
			DocumentBuilder builder = factory.newDocumentBuilder();
			File file = createFile();
			Document document = builder.parse(file);
			createObjects(document);		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static File createFile() throws Exception {
		final String address = "LINK TO XML/XHTML";
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
		
		xmlFile.deleteOnExit();
		return xmlFile;
	}	
	

	public static void createObjects (Document doc ) {		
        Element element = doc.getDocumentElement();        
        NodeList items = element.getElementsByTagName("item");
        
		for (int i=0; i < items.getLength(); i++) {
			Node premiseNode = items.item(i);
			Element premise = (Element) premiseNode;
			NodeList properties = premise.getElementsByTagName("property"); 
			makeObjects(properties);
						
		}           		
	}
	
	public static String getString(Element property) {
		String value = "";
		if (property.getTextContent() != null) {
			value = property.getTextContent();
		}
		System.out.println(value);
		return value;
	}
	
	
	public static void makeObjects(NodeList properties) {
		List<Premise> premises = new ArrayList<Premise>();
		String value = "";
		String street = "";
		String city = "";
		String id = "";
		String m2 = "";
		String premisetype = "";
		String imglink = "";
		String premisedescription = "";
		
		for (int j=0; j < properties.getLength(); j++) {
			Node propertyNode = properties.item(j);
			Element property = (Element) propertyNode;
			
			if (property.hasAttributes()) {
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("street")) {
					street = getString(property);
				}
				
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("town")) {
					city = getString(property);
				}
				
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("totalaream2")) {
					m2 = getString(property);
				}
				
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("cust_itemcode")) {
					id = getString(property);
				}
							
				
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("realtytypeoption2")) {
					StringBuilder temp = new StringBuilder();
					NodeList types = property.getChildNodes();
					for (int k=0; k<types.getLength(); k++) {
						temp.append(types.item(k).getFirstChild().getTextContent() + " ");
					}
					premisetype = temp.toString();
					System.out.println(premisetype);
				}
				
	/*
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("text")) {
					NodeList textproperties = property.getChildNodes();
					String premisedescription = textproperties.item(1).getChildNodes().item(1).getTextContent();
					System.out.println("txtprops: " + textproperties.getLength());
					System.out.println("txtprops: " + property.getTextContent());
				}
	*/			
					
				if((property.getAttributes().getNamedItem("name").getTextContent()).equals("image")) {
					StringBuilder temp = new StringBuilder();
					NodeList imgproperties = property.getChildNodes();
					for (int l=0; l< imgproperties.getLength(); l++) {
						temp.append(imgproperties.item(l).getLastChild().getTextContent() + ",");
					}
					String images = temp.toString();
					System.out.println(images);
				}
			}			
			
			// make new premise with values		, id to int	
		}		
	}
	
	
//delete	
	public static String getValue(Element property, String item) {
		String value = "";		
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
	
	
}
