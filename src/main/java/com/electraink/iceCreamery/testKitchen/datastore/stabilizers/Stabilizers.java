package com.electraink.iceCreamery.testKitchen.datastore.stabilizers;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.Flavoring;

public class Stabilizers implements Iterable<Stabilizer>, Iterator<Stabilizer>
{
	Vector<Stabilizer> stabilizers = new Vector<Stabilizer>();
	private int start, end, cursor;
	
	public Stabilizers()
	{
	}
	
	public void addStabilizer(Stabilizer stabilizer)
	{
		stabilizers.add(stabilizer);
	}

	public Vector<Stabilizer> getStabilizers()
	{
		return stabilizers;
	}
	
	public void removeStabilizer(String stabilizer)
	{
		Iterator<Stabilizer> iTypes = stabilizers.iterator();
		Stabilizer type = null;
		int count = 0;
		while(iTypes.hasNext())
		{
			type = iTypes.next();
			if(type.getEnum() == stabilizer)
			{
				stabilizers.removeElementAt(count);
				break;
			}
			count++;
		}
	}

	public Iterator<Stabilizer> iterator()
	{
		start = 0;
		end = stabilizers.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Stabilizer next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return stabilizers.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return stabilizers.size();
	}
}
