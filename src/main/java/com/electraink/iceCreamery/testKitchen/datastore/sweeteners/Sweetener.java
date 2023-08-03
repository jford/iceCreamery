package com.electraink.iceCreamery.testKitchen.datastore.sweeteners;

import com.electraink.iceCreamery.testKitchen.datastore.*;

public class Sweetener
{
	private /*DataMgr.Sweetener*/ String sweetenerType = null;
	private String quantity = "";
	
	public Sweetener()
	{
	}
	
	public void setSweetenerType(/*DataMgr.Sweetener*/ String sweetenerType)
	{
		this.sweetenerType = sweetenerType;
	}
	public /*DataMgr.Sweetener*/ String getEnum()
	{
		return sweetenerType;
	}
	public String getSweetenerName()
	{
		return sweetenerType;
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
