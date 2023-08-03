package com.electraink.iceCreamery.testKitchen.datastore;

import java.util.Vector;
import java.util.Iterator;
import java.util.TreeSet;

public class Ingredient 
{
	private String type;
	private String name;
	private String id;
	private String quantity;
	private String ingNotes;
	private Object ing;
	
	// links contains list of pointers to various objects
	// associated with this ingredient (such as suppliers, flavorings)
	//    link[0] == type of obj
	//    link[1] == id of linked obj
	private Vector<String[]> links = new Vector<String[]>();
	
	public Ingredient()
	{
		
	}
	
	public Ingredient(String type)
	{
		this.type = type;
	}
	
	public void addLink(String[] link)
	{
		links.add(link);
		
		// remove duplicated entry
		TreeSet<String[]> treeSet = new TreeSet<String[]>(links);
		links.clear();
		links.addAll(treeSet);
	}
	public Vector<String[]> getLinks()
	{
		return links;
	}
	public boolean removeLink(String[] link)
	{
		boolean linkRemoved = false;
		
		int idx = 0;
		Iterator<String[]> iLinks = links.iterator();
		while(iLinks.hasNext())
		{
			String[] linkFromList = iLinks.next();
			if(link[1].equals(linkFromList[1]))
			{
				links.removeElementAt(idx);
				linkRemoved = true;
			}
			if(linkRemoved)
				break;
			
			idx++;
		}
		return linkRemoved;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return id;
	}

	public void setQuantity(String qty)
	{
		quantity = qty;
	}
	public String getQuantity()
	{
		return quantity;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return type;
	}
	
	public void setIngNotes(String ingNotes)
	{
		this.ingNotes = ingNotes;
	}
	public String getIngNotes()
	{
	    return ingNotes;
	}
	
	public void addIngObj(Object ing)
	{
	    this.ing = ing;
	}
	public Object getIngObj()
	{
	    return ing;
	}
}
