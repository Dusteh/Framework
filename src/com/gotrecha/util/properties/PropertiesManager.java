package com.gotrecha.util.properties;

import com.google.common.collect.Maps;
import com.gotrecha.util.traits.UtilTrait;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by dustin on 4/26/14.
 */
public class PropertiesManager implements UtilTrait{
	private static final String APPLICATION_HOME = System.getProperty("GOTRECHA_APPLICATION_HOME");
	private static PropertiesManager instance;
	private final Logger log = log();
	private final DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();

	private final Map<String,String> properties = Maps.newHashMap();

	PropertiesManager(){
		log.info("Application home: "+APPLICATION_HOME);
		loadProperties();
	}

	public void loadProperties(){
		Document document = readProperties();
		NodeList propertiesElement = document.getElementsByTagName("property");
		log.info("Properties list length: "+propertiesElement.getLength());
		boolean initialized = false;
		if(propertiesElement.getLength() == 0){
			Node propertiesElem = document.getElementsByTagName("properties").item(0);
			Element property = document.createElement("property");
			initialized = true;
			property.setAttribute("id","dateInitialized");
			property.setIdAttribute("id", true);
//			property.setTextContent((new Date()).toString());
			property.appendChild(document.createTextNode((new Date()).toString()));
			propertiesElem.appendChild(property);
//			document.getDocumentElement().appendChild(property);
			propertiesElement = document.getElementsByTagName("property");

		}

		for(int i=0;i<propertiesElement.getLength();i++){
			Node elem = propertiesElement.item(i);
			if(elem != null){
				log.debug("Loaded property: "+elem.getAttributes().getNamedItem("id").getNodeValue()+" Value: "+elem.getTextContent());
				properties.put(elem.getAttributes().getNamedItem("id").getNodeValue(), elem.getTextContent());
			}
		}

		if(initialized){
			writeProperties();
		}

	}

	public static PropertiesManager getInstance(){
		return instance == null ? (instance = new PropertiesManager()) : instance;
	}

	public static synchronized String getProperty(String name){
		String value = getInstance().properties.get(name);
		return value == null? name : value;
	}

	public static synchronized boolean hasProperty(String name){
		return getInstance().properties.containsKey(name);
	}

	public static synchronized void setProperty(String name, String value){
		getInstance().set(name,value);
	}
	private void set(String name, String value){
		properties.put(name,value);
		writeProperties();
	}
	private void writeProperties(){
		if(APPLICATION_HOME != null){
			try {

				File propertiesFile = new File(APPLICATION_HOME+"/data", "properties.xml");
				if (!propertiesFile.exists()) {
					propertiesFile.getParentFile().mkdirs();
					propertiesFile.createNewFile();
				}

				Document document = builder.newDocumentBuilder().parse(propertiesFile);

				for (String name : properties.keySet()) {
					String value = properties.get(name);
					Element element = document.getElementById(name);
					if(element != null) {
						element.setNodeValue(value);
					}else{
						NodeList propertiesElement = document.getElementsByTagName("properties");
						Node node = propertiesElement.item(0);//There should be only one
						Element property = document.createElement("property");
						property.setAttribute("id",name);
						property.setIdAttribute("id", true);
//						property.setTextContent(value);
						property.appendChild(document.createTextNode(value));
						node.appendChild(property);
					}
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				transformerFactory.setAttribute("indent-number", 2);
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(propertiesFile);
				transformer.transform(source,result);

			}catch (Exception e){
				log.error("Failed to write properties file",e);
			}
		}
	}

	private Document readProperties(){
		try{
			File properties = new File(APPLICATION_HOME+"/data","properties.xml");
			Document document = null;
			if(!properties.exists()){
				log.info("Properties didn't exist creating");
				document = builder.newDocumentBuilder().newDocument();
				Element propElem = document.createElement("properties");

				document.appendChild(propElem);
//				document.getDocumentElement().appendChild(propElem);
//				document.adoptNode(propElem);
				log.info("Added properties node");

				File parent = properties.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
				properties.createNewFile();

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
//				transformerFactory.setAttribute("indent-number", 15);

				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(properties);
				transformer.transform(source,result);
			}else{
				log.info("Found properties");
				document = builder.newDocumentBuilder().parse(properties);
			}


			return document;
		}catch(Exception ex){
			log.error("Failed to read properties file",ex);
		}
		return null;
	}
}
