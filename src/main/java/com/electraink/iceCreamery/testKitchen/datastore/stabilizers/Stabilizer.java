package com.electraink.iceCreamery.testKitchen.datastore.stabilizers;

import com.electraink.iceCreamery.testKitchen.datastore.*;

public class Stabilizer
{
	private /*DataMgr.Stabilizer*/ String stabilizerType = null;
	private String quantity = "";
	
	public Stabilizer()
	{
	}
	
	public void setStabilizerType(/*DataMgr.Stabilizer*/ String stabilizerType)
	{
		this.stabilizerType = stabilizerType;
	}
	public /*DataMgr.Stabilizer*/ String getEnum()
	{
		return stabilizerType;
	}
	public String getStabilizerName()
	{
		return stabilizerType;
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
