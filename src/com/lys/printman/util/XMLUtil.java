package com.lys.printman.util;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


public class XMLUtil {

	private static String fileName = "conf.xml";
	
	private static Document doc;
	
	public static Document getDocument() throws DocumentException
	{
		if(null == doc)
		{
			SAXReader saxReader = new SAXReader();

			InputStream in = XMLUtil.class.getClassLoader().getResourceAsStream(fileName);
			doc = saxReader.read(in);
		}
		
		
		return doc;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List getElements(String path) throws DocumentException
	{
        Document doc = getDocument();
		
		return doc.selectNodes(path);
	}
}
