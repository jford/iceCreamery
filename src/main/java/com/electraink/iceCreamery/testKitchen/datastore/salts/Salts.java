package com.electraink.iceCreamery.testKitchen.datastore.salts;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
// import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;

public class Salts  implements Iterable<Salt>, Iterator<Salt>
{
	Vector<Salt> salts = new Vector<Salt>();
	private int start, end, cursor;
	
	public Salts()
	{
	}
	
	public void addSalt(Salt salt)
	{
		salts.add(salt);
	}

	public Vector<Salt> getSalts()
	{
		return salts;
	}
	
	public void removeSalt(String salt)
	{
		Iterator<Salt> iTypes = salts.iterator();
		Salt type = null;
		int count = 0;
		while(iTypes.hasNext())
		{
			type = iTypes.next();
			if(type.getEnum() == salt)
			{
				salts.removeElementAt(count);
				break;
			}
			count++;
		}
	}
	
	public Iterator<Salt> iterator()
	{
		start = 0;
		end = salts.size();
		cursor = start;
		return this;
	}

	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Salt next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return salts.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return salts.size();
	}

}
