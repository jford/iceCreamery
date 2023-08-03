package com.electraink.iceCreamery.testKitchen.datastore.sweeteners;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.Flavoring;

public class Sweeteners implements Iterable<Sweetener>, Iterator<Sweetener>
{
	Vector<Sweetener> sweeteners = new Vector<Sweetener>();
	private int start, end, cursor;
	
	public Sweeteners()
	{
	}
	public void addSweetener(Sweetener sweetener)
	{
		sweeteners.add(sweetener);
	}

	public Vector<Sweetener> getSweeteners()
	{
		return sweeteners;
	}
	
	public void removeSweetener(String sweetener)
	{
		Iterator<Sweetener> iTypes = sweeteners.iterator();
		Sweetener type = null;
		int count = 0;
		while(iTypes.hasNext())
		{
			type = iTypes.next();
			if(type.getEnum() == sweetener)
			{
				sweeteners.removeElementAt(count);
				break;
			}
			count++;
		}
	}
	public Iterator<Sweetener> iterator()
	{
		start = 0;
		end = sweeteners.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Sweetener next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return sweeteners.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return sweeteners.size();
	}
}
