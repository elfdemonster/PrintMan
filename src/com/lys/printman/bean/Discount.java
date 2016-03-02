package com.lys.printman.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Description: 优惠实体
 * Date: 2016年3月2日 
 * Copyright (c) 2016 AI 
 * 
 * @author Liuys5
 */
public class Discount 
 {

	private String discntCode;
	private String discntName;
	private String typeCode;
	
	// 优惠中指定的商品元素
	private Set<String> itemSet = new HashSet<String>();

	public String getDiscntCode()
	{
		return discntCode;
	}

	public void setDiscntCode(String discntCode)
	{
		this.discntCode = discntCode;
	}

	public String getDiscntName()
	{
		return discntName;
	}

	public void setDiscntName(String discntName)
	{
		this.discntName = discntName;
	}

	public String getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(String typeCode)
	{
		this.typeCode = typeCode;
	}

	public Set<String> getItemSet()
	{
		return itemSet;
	}

	public void setItemSet(Set<String> itemSet)
	{
		this.itemSet = itemSet;
	}

}
