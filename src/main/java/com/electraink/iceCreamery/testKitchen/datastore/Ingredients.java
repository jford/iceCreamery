package com.electraink.iceCreamery.testKitchen.datastore;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

public class Ingredients  implements Iterable<Ingredient>, Iterator<Ingredient>
{
	Vector<Ingredient> ingredientList;
	private int start, end, cursor;
	
	public Ingredients()
	{
		ingredientList = new Vector<Ingredient>();
	}
	
	public void addIngredient(Ingredient ingredient)
	{
		ingredientList.add(ingredient);
	}
	public Ingredient getIngredient(String id)
	{
		Ingredient ing = null;
		Iterator<Ingredient> iIngs = ingredientList.iterator();
		while(iIngs.hasNext())
		{
		    ing = iIngs.next();
		    if(ing.getId().equals(id))
		        break;
		}
		return ing;
	}
	public Vector<Ingredient> getIngredients()
	{
		return ingredientList;
	}
	
	public void removeIngredient(String ingId)
	{
	    Iterator<Ingredient> iIngs = ingredientList.iterator();
	    int count = 0;
	    while(iIngs.hasNext())
	    {
	        Ingredient listIng = iIngs.next();
	        if(listIng.getId().equals(ingId))
	        {
	            ingredientList.removeElementAt(count);
	            break;
	        }
	        count++;
	    }
	}
	public Iterator<Ingredient> iterator()
	{
		start = 0;
		end = ingredientList.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Ingredient next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return ingredientList.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return ingredientList.size();
	}	
}
