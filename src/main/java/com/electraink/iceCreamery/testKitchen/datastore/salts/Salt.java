package com.electraink.iceCreamery.testKitchen.datastore.salts;

public class Salt 
{
	private String saltType = null;
	private String quantity = "";
	
	public Salt()
	{
	}
	
	public void setSaltType(String saltType)
	{
		this.saltType = saltType;
	}
	public String getEnum()
	{
		return saltType;
	}
	public String getSaltName()
	{
		return saltType;
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
