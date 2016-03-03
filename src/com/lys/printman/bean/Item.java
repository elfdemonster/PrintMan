package com.lys.printman.bean;

/**
 * 
 * Description: 元素  所有商品都是购物小票中的元素
 * Date: 2016年3月2日 
 * Copyright (c) 2016 LYS 
 * 
 * @author Liuys5
 */
public class Item {

	private String tdCode;
	private String name;
	private Integer count;
	private String unit;
	private Integer unitPrice;
	private String typeCode;

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("名称：").append(this.getName()).append("，");
		sb.append("数量：").append(this.getCount()).append(this.getUnit())
				.append("，");
		return sb.toString();
	}

	
	public String getTdCode()
	{
		return tdCode;
	}

	
	public void setTdCode(String tdCode)
	{
		this.tdCode = tdCode;
	}

	
	public String getName()
	{
		return name;
	}

	
	public void setName(String name)
	{
		this.name = name;
	}

	
	public Integer getCount()
	{
		return count;
	}

	
	public void setCount(Integer count)
	{
		this.count = count;
	}

	
	public String getUnit()
	{
		return unit;
	}

	
	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	
	public Integer getUnitPrice()
	{
		return unitPrice;
	}

	
	public void setUnitPrice(Integer unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public String getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(String typeCode)
	{
		this.typeCode = typeCode;
	}

}
