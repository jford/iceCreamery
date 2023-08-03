package com.electraink.iceCreamery.testKitchen.datastore.flavors;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;

public class Flavorings implements Iterable<Flavoring>, Iterator<Flavoring>
{
	private Vector<Flavoring> flavoringList = new Vector<Flavoring>();
	private int start, end, cursor;

	public Flavorings()
	{
	}
	
	public void addFlavoring(Flavoring flavoring)
	{
		flavoringList.add(flavoring);
	}
	
	public void removeFlavoring(String flavoring)
	{
		Iterator<Flavoring> iFlavs = flavoringList.iterator();
		Flavoring flavorType = null;
		int count = 0;
		while(iFlavs.hasNext())
		{
			flavorType = iFlavs.next();
			if(flavorType.getEnum() == flavoring)
			{
				flavoringList.removeElementAt(count);
				break;
			}
			count++;
		}
	}
	
	public Vector<Flavoring> getFlavorings()
	{
		return flavoringList;
	}

	public Iterator<Flavoring> iterator()
	{
		start = 0;
		end = flavoringList.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Flavoring next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return flavoringList.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return flavoringList.size();
	}
}
