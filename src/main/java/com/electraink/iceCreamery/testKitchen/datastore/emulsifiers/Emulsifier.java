package com.electraink.iceCreamery.testKitchen.datastore.emulsifiers;

import com.electraink.iceCreamery.testKitchen.datastore.*;

public class Emulsifier
{
	private /*DataMgr.Emulsifier*/ String emulsifierType;
	private String quantity = "";
	
	public Emulsifier()
	{
	}
	
	public void setEmulsifier(/*DataMgr.Emulsifier*/ String emulsifierType)
	{
		this.emulsifierType = emulsifierType;
	}
	
	public /*DataMgr.Emulsifier*/ String getEnum()
	{
		return emulsifierType;
	}
	public String getEmulsifierName()
	{
		return emulsifierType;
	}
	
	public void setQuantity(String quantity)
	{
		this.quantity = quantity;
	}
	public String getQuantity()
	{
		return quantity;
	}
}
