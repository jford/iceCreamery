package com.electraink.iceCreamery.testKitchen.datastore;

import java.util.*;

import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.Emulsifier;

public class Recipes implements Iterable<Recipe>, Iterator<Recipe>
{
	Vector<Recipe> recipeList = new Vector<Recipe>();
	private int start, end, cursor;
	
	public Recipes()
	{
	}
	
	public void addRecipe(Recipe recipe)
	{
		recipeList.add(recipe);
	}
	public Vector<Recipe> getRecipes()
	{
		return recipeList;
	}
	
	public boolean nameUnique(String newName)
	{
		boolean ret = true;
		
		Iterator<Recipe> iRecipes = recipeList.iterator();
		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			if(recipe.getRecipeName().toLowerCase().compareTo(newName.toLowerCase()) == 0)
			{
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	public void removeRecipe(String rcpId)
	{
		Iterator<Recipe> iRecipes = recipeList.iterator();
		Recipe recipe = null;
		int count = 0;
		while(iRecipes.hasNext())
		{
			recipe = iRecipes.next();
			if(recipe.getId().equals(rcpId))
			{
				recipeList.removeElementAt(count);
				break;
			}
			count++;
		}
	}

	public Iterator<Recipe> iterator()
	{
		start = 0;
		end = recipeList.size();
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Recipe next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return recipeList.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return recipeList.size();
	}
}

