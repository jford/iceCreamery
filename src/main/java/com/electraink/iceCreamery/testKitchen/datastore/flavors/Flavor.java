package com.electraink.iceCreamery.testKitchen.datastore.flavors;

import java.util.*;

public class Flavor
{
	private String name = "";
	private Flavorings flavorings = null;
	
	public Flavor()
	{
		// A flavor object can contain one or more flavoring objects, to 
		// model ice cream flavors that are one of two types: 
		//   - simple (a single flavoring---chocolate, mint) 
		//   - compound (multiple flavorings---mint chip, rocky road)
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	
	public void setFlavorings(Flavorings flavorings)
	{
		this.flavorings = flavorings;
	}
	public Flavorings getFlavorings()
	{
		return flavorings;
	}
}
