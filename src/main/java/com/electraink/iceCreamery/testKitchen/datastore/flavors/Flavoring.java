package com.electraink.iceCreamery.testKitchen.datastore.flavors;

import com.electraink.iceCreamery.testKitchen.datastore.*;

public class Flavoring
{
//	DataMgr.FlavorType flavorType = DataMgr.FlavorType.NONE;
	String flavorType = "NONE";
	String quantity = "";
	
	public Flavoring()
	{
		// A flavor object can contain one or more flavoring objects, to 
		// model ice cream flavors that are one of two types: 
		//   - simple (a single flavoring---chocolate, mint) 
		//   - compound (multiple flavorings---mint chip, rocky road)
	}
	
	public void setFlavorType(/*DataMgr.FlavorType*/ String flavorType)
	{
		this.flavorType = flavorType;
	}
	
	public /*DataMgr.FlavorType*/ String getEnum()
	{
		return flavorType;
	}
//	public String getFlavorName()
//	{
//		return DataMgr.getFlavorTypeName(flavorType);
//	}
	
	public void setQty(String quantity)
	{
		this.quantity = quantity;
	}
	public String getQty()
	{
		return quantity;
	}

}
