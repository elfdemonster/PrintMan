package com.lys.printman.print;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import com.lys.printman.bean.Discount;
import com.lys.printman.bean.Item;
import com.lys.printman.test.TestMain;

/**
 * 
 * Description: 打印主体类
 * Date: 2016年3月2日 
 * Copyright (c) 2016 LYS 
 * 
 * @author Liuys5
 */
public class Print {

	@SuppressWarnings("rawtypes")
	public String printOrder(String str) throws DocumentException
	{
		StringBuilder receiptInfo = new StringBuilder("***<没钱赚商店>购物清单***");
		receiptInfo.append("\n");
		if (null != str || !"".equals(str))
		{
			JSONObject json = getJson(str);
			Iterator it = json.keys();

			DecimalFormat df = new DecimalFormat("#0.00");

			// 总价
			Double total = 0.00;
			// 优惠总额
			Double totalDis = 0.00;
			// 买二赠一类优惠信息
			StringBuilder disInfo = new StringBuilder("买二赠一商品：");
			disInfo.append("\n");
			// 是否出现买二赠一类优惠的标记
			boolean disInfoFlag = false;
			
			while (it.hasNext())
			{
				String key = it.next().toString();
				Integer count = json.getInt(key);

				// 获取商品信息，改成对应的获取方法 此处为测试代码 
				Item item = TestMain.itemMap.get(key);
				
				item.setCount(count);

				// 单个商品的小票信息
				StringBuilder itemInfo = new StringBuilder(item.toString());
				itemInfo.append("单价：")
						.append(df.format(item.getUnitPrice() / 100.00))
						.append("（元），");

				// 单个类型商品 无优惠下的总价
				Double itemTotal = item.getUnitPrice() * item.getCount()
						/ 100.00;

				// 打折类型优惠信息
				StringBuilder disoffInfo = new StringBuilder();

				// 判断商品享受的优惠
				String discountCode = checkItemDis(key);
				
				// 有优惠时的处理
				if(null != discountCode)
				{
					// 95 折优惠
					if ("5disoff".equals(discountCode))
					{
						disoffInfo.append("节省：").append(itemTotal * 0.05)
								.append("（元）");
						totalDis += itemTotal * 0.05;
						itemTotal -= itemTotal * 0.05;

					} else
					{
						// 买二赠一
						disInfoFlag = true;

						Integer disCount = count / 3;
						totalDis += item.getUnitPrice() * disCount / 100.00;
						itemTotal -= item.getUnitPrice() * disCount
								/ 100.00;
						disInfo.append("名称：").append(item.getName())
								.append("，数量").append(disCount)
								.append(item.getUnit()).append("\n");
					}
				}
				

				itemInfo.append("小计：").append(df.format(itemTotal))
						.append("（元）");
				if (disoffInfo.length() > 0)
				{
					itemInfo.append(",").append(disoffInfo);
				}
				itemInfo.append("\n");

				receiptInfo.append(itemInfo);

				total += itemTotal;
			}

			if (disInfoFlag)
			{
				receiptInfo.append("----------------------").append("\n");
				receiptInfo.append(disInfo);
			}

			receiptInfo.append("----------------------").append("\n");
			receiptInfo.append("总计：").append(df.format(total)).append("（元）");
			if (totalDis > 0.00)
			{
				receiptInfo.append("\n").append("节省：")
						.append(df.format(totalDis)).append("（元）");
			}
		}

		receiptInfo.append("\n");
		receiptInfo.append("**********************");

		return receiptInfo.toString();
	}
	
	/**
	 * 
	 * Description: 判断商品所属优惠
	 * @param key
	 * @return
	 * 
	 * Date: 2016年3月2日 
	 * @author Liuys5
	 */
	@SuppressWarnings("rawtypes")
	private String checkItemDis(String key)
	{
		String discountCode = null;
		// 优惠配置信息，改成对应的获取方法 此处为测试代码 
		Set<String> diskeySet = TestMain.discntMap.keySet();
		Iterator itm = diskeySet.iterator();
		while (itm.hasNext())
		{
			Discount dis = TestMain.discntMap.get(itm.next());
			if (dis.getItemSet().contains(key))
			{
				discountCode = dis.getDiscntCode();
				
				// 买二赠一优惠中有商品，则无需后续判断
				if("btgof".equals(discountCode))
				{
					break;
				}
			}
		}
		
		return discountCode;
	}

	/**
	 * 
	 * Description: 解析传入的数据串
	 * @param str
	 * @return
	 * 
	 * Date: 2016年3月2日 
	 * @author Liuys5
	 */
	private JSONObject getJson(String str)
	{
		str = str.substring(2, str.length() - 2);

		String[] twodim = str.split("','");

		JSONObject json = new JSONObject();
		for (int i = 0; i < twodim.length; i++)
		{
			String key = "";
			String tmp = twodim[i];
			if (tmp.contains("-"))
			{
				String[] vtmp = tmp.split("-");
				key = vtmp[0];
				put(json, vtmp[0], Integer.valueOf(vtmp[1]));
			} else
			{
				key = tmp;
				put(json, key, 1);
			}
		}

		return json;
	}

	private void put(JSONObject json, String key, Integer value)
	{
		if (json.has(key))
		{
			json.put(key, json.getInt(key) + value);
		} else
		{
			json.put(key, value);
		}
	}

}
