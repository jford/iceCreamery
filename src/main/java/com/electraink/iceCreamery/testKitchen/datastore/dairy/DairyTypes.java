package com.electraink.iceCreamery.testKitchen.datastore.dairy;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.Flavoring;

public class DairyTypes implements Iterable<DairyType>, Iterator<DairyType>
{
	private Vector<DairyType> dairyTypes = new Vector<DairyType>();
	private int start, end, cursor;
	
	public DairyTypes()
	{
	}
	
	public void addDairyType(DairyType dairyType)
	{
		dairyTypes.add(dairyType);
	}

	public void removeDairyType(String dairyType)
	{
		Iterator<DairyType> iTypes = dairyTypes.iterator();
		DairyType type = null;
		int count = 0;
		while(iTypes.hasNext())
		{
			type = iTypes.next();
			if(type.getEnum() == dairyType)
			{
				dairyTypes.removeElementAt(count);
				break;
			}
			count++;
		}
	}

	public Iterator<DairyType> iterator()
	{
		start = 0;
		end = dairyTypes.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public DairyType next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return dairyTypes.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return dairyTypes.size();
	}
	
	public boolean hasDairyType(DairyType type)
	{
		boolean matchFound = false;
		
		if(dairyTypes != null)
		{
			Iterator<DairyType> iDairy = dairyTypes.iterator();
			while(iDairy.hasNext())
			{
				DairyType containedType = (DairyType)iDairy.next();
				if(containedType == type)
				{
					matchFound = true;
					break;
				}
			}
		}
		return matchFound;
	}
}
