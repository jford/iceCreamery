package com.electraink.iceCreamery.testKitchen.datastore.suppliers;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.Stabilizer;

public class Suppliers implements Iterable<Supplier>, Iterator<Supplier> 
{
	Vector<Supplier> suppliers = new Vector<Supplier>();
	private int start, end, cursor;
	
	public Suppliers()
	{
		
	}
	public void addSupplier(Supplier supplier)
	{
		suppliers.add(supplier);
	}
	public Vector<Supplier> getSuppliers()
	{
		return suppliers;
	}
	
	public void removeSupplier(String splrId)
	{
	    Iterator<Supplier> iSplrs = suppliers.iterator();
	    Supplier splr = null;
	    int count = 0;
	    while(iSplrs.hasNext())
	    {
	        splr = iSplrs.next();
	        if(splr.getSplrId().equals(splrId))
	        {
	            suppliers.removeElementAt(count);
	            break;
	        }
	        count++;
	    }
	}

    public Iterator<Supplier> iterator()
    {
        start = 0;
        end = suppliers.size();
        cursor = start;
        return this;
    }
    
    public boolean hasNext()
    {
        return cursor < end;
    }
    
    public Supplier next()
    {
        if(!hasNext())
//	      if(cursor >= end)
        {
            cursor = start;
            throw new NoSuchElementException();
        }
        return suppliers.elementAt(cursor++);
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
    public int size()
    {
        return suppliers.size();
    }

}
