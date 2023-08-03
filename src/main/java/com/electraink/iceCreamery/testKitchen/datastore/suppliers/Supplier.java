package com.electraink.iceCreamery.testKitchen.datastore.suppliers;

import java.util.Vector;
import java.util.Iterator;

public class Supplier
{
	private String splrId = "";
	private String splrName = "";
	private String splrContact = "";
	private String splrTelephone= "";
	private String splrNotes = "";
	
	private Vector<InventoryItem> inventory = new Vector<InventoryItem>();
	
	public Supplier()
	{
	}

	public void addToInventory(InventoryItem item)
	{
	    inventory.add(item);
	}
	public void removeFromInventory(String ingId)
	{
	    int idx = 0;
	    Iterator<InventoryItem> iItms = inventory.iterator();
	    while(iItms.hasNext())
	    {
	        InventoryItem item = iItms.next();
	        if(item.getIngId().equals(ingId))
	        {
	            inventory.removeElementAt(idx);
	            break;
	        }
	        idx++;
	    }
	}
	public Vector<InventoryItem> getInventory()
	{
	    return inventory;
	}
	
	public void setSplrName(String name)
	{
		splrName = name;
	}
	public String getSplrName()
	{
		return splrName;
	}
	
	public void setSplrId(String id)
	{
		splrId = id;
	}
	public String getSplrId()
	{
		return splrId;
	}
	
	public void setSplrNotes(String notes)
	{
		splrNotes = notes.trim();
	}
	public String getSplrNotes()
	{
		return splrNotes;
	}

	public void setContactName(String splrContact)
	{
	    this.splrContact = splrContact;
	}
	public String getContactName()
	{
		return splrContact;
	}
	
	public void setSplrTelephone(String splrTelephone)
	{
	    this.splrTelephone = splrTelephone;
	}
    public String getSplrTelephone()
    {
        return splrTelephone;
    }
    
	public class InventoryItem
	{
/*
         *   [0]  = ing Id
         *   [1]  = unit (brand) name 
         *   [3]  = unit price
         *   [4]  = unit fees (taxes, shippping...)
         *   [5]  = unit size
         *   [6]  = notes for this item

 */
	    private String splrId;
		private String ingId;
		private String unitName;
		private String unitPrice;
		private String unitFees;
		private String unitSize;
		private String notes;
		
		public InventoryItem()
		{
		}
		public void setUnitName(String name)
		{
			unitName = name;
		}
		public String getUnitName()
		{
			return unitName;
		}

		public void setIngId(String ingId)
		{
			this.ingId = ingId;
		}
		public String getIngId()
		{
			return ingId;
		}
		
		public void setUnitSize(String unitSize)
		{
			this.unitSize = unitSize;
		}
		public String getUnitSize()
		{
			return unitSize;
		}

		public void setUnitPrice(String price)
		{
			unitPrice = price;
		}
		public String getUnitPrice()
		{
			return unitPrice;
		}

		public void setUnitFees(String unitFees)
		{
			this.unitFees = unitFees;
		}
		public String getUnitFees()
		{
			return unitFees;
		}

		public void setNotes(String notes)
		{
			this.notes = notes;
		}
		public String getNotes()
		{
			return notes;
		}
	}
}