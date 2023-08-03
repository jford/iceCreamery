package com.electraink.iceCreamery.testKitchen.datastore.emulsifiers;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.Flavoring;

public class Emulsifiers implements Iterable<Emulsifier>, Iterator<Emulsifier>
{
	private Vector<Emulsifier> emulsifiers = new Vector<Emulsifier>();
	private int start, end, cursor;
	
	public Emulsifiers()
	{
	}
	
	public void addEmulsifier(Emulsifier emulsifier)
	{
		emulsifiers.add(emulsifier);
	}

	public void removeEmulsifier(String emulsifier)
	{
		Iterator<Emulsifier> iTypes = emulsifiers.iterator();
		Emulsifier type = null;
		int count = 0;
		while(iTypes.hasNext())
		{
			type = iTypes.next();
			if(type.getEnum() == emulsifier)
			{
				emulsifiers.removeElementAt(count);
				break;
			}
			count++;
		}
	}

	public Iterator<Emulsifier> iterator()
	{
		start = 0;
		end = emulsifiers.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Emulsifier next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return emulsifiers.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return emulsifiers.size();
	}
}
