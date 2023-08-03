package com.electraink.iceCreamery.testKitchen.datastore.flavors;

import java.util.Vector;

public class Flavors 
{
	Vector<Flavor> flavors = new Vector<Flavor>();
	
	public Flavors()
	{
		
	}
	public void addFlavor(Flavor flav)
	{
		flavors.add(flav);
	}
	public Vector<Flavor> getFlavors()
	{
		return flavors;
	}
}
