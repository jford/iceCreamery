package com.electraink.iceCreamery.testKitchen.datastore.dairy;

import com.electraink.iceCreamery.testKitchen.datastore.*;

public class DairyType
{
//	private DataMgr.DairyType dairyType = null;
	private String dairyType;
	private String quantity = "";
	
	public DairyType()
	{
	}
	
	public void setDairyType(/*DataMgr.DairyType*/ String dairyType)
	{
		this.dairyType = dairyType;
	}
	public /*DataMgr.DairyType*/ String getEnum()
	{
		return dairyType;
	}
	
	public String getDairyTypeName()
	{
		return dairyType;
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
