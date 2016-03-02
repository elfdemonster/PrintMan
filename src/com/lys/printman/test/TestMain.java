package com.lys.printman.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.lys.printman.bean.Discount;
import com.lys.printman.bean.Item;
import com.lys.printman.print.Print;
import com.lys.printman.util.XMLUtil;


public class TestMain {

	public static Map<String, Item> itemMap;
	public static Map<String, Discount> discntMap;
	
	@SuppressWarnings("rawtypes")
	public static void main (String[] s)
	{
		
		String str = "["
		            + "'ITEM000001',"
				    + "'ITEM000001',"
		            + "'ITEM000001',"
				    + "'ITEM000001',"
		            + "'ITEM000001',"
		            + "'ITEM000001',"
				    + "'ITEM000003-2',"
		            + "'ITEM000005',"
				    + "'ITEM000005',"
		            + "'ITEM000005'"
		            + "]";
		
		Print p = new Print();
		try
		{
			TestMain.getItems();
			List elements = XMLUtil.getElements("/conf/disList/discounts");
			for (Iterator iter = elements.iterator(); iter.hasNext();)
			{
				Element element = (Element) iter.next();
				TestMain.getDiscounts(element);
				System.out.println(p.printOrder(str));
			}
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void getItems() throws DocumentException
	{
		if (null == itemMap)
		{
			itemMap = new HashMap<String, Item>();
			List elements = XMLUtil.getElements("/conf/items/item");
			if (null != elements)
			{
				for (Iterator iter = elements.iterator(); iter.hasNext();)
				{
					Element element = (Element) iter.next();
					Item item = new Item();
					item.setTdCode(element.attribute("tdCode").getText());
					item.setName(element.attribute("name").getText());
					item.setTypeCode(element.attribute("typeCode").getText());
					item.setUnit(element.attribute("unit").getText());
					item.setUnitPrice(Integer.valueOf(element.attribute(
							"unitPrice").getText()));

					itemMap.put(item.getTdCode(), item);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void getDiscounts(Element discnts) throws DocumentException
	{
		discntMap = new HashMap<String, Discount>();
		List elements = discnts.elements("discount");
		if (null != elements)
		{
			for (Iterator iter = elements.iterator(); iter.hasNext();)
			{
				Element element = (Element) iter.next();
				Discount dis = new Discount();
				dis.setDiscntCode(element.attribute("discntCode").getText());
				dis.setDiscntName(element.attribute("discntName").getText());
				dis.setTypeCode(element.attribute("typeCode").getText());

				List items = element.elements();
				for (Iterator iterin = items.iterator(); iterin.hasNext();)
				{
					Element elementin = (Element) iterin.next();
					dis.getItemSet().add(
							elementin.attribute("tdCode").getText());
				}

				discntMap.put(dis.getDiscntCode(), dis);
			}
		}
	}
}
