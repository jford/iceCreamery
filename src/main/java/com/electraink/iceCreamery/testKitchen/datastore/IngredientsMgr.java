package com.electraink.iceCreamery.testKitchen.datastore;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;
import com.electraink.iceCreamery.testKitchen.datastore.suppliers.*;
import com.electraink.iceCreamery.utilities.*;

import java.util.Vector;
import java.util.Iterator;

public class IngredientsMgr 
{
	private Ingredients ingredients;
	private IngredientsParser ingsParser;
	
	private Suppliers suppliers;
	
	private static String[][] ingNameMap = {
                                        { "Flavoring(s)", "flavoring" },
                                        { "DairyType(s)", "dairytype" },
                                        { "Emulsifier(s)", "emulsifier" },
                                        { "Salt(s)", "salt" },
                                        { "Stabilizer(s)", "stabilizer" },
                                        { "Sweetener(s)", "sweetener" }
                                     };

	public IngredientsMgr()
	{
		ingsParser = new IngredientsParser();
		ingredients = ingsParser.getCurrentIngredients();
		suppliers = ingsParser.getCurrentSplrsList();
	}
	
	public Ingredients getIngredients()
	{
		return ingredients;
	}
	
	public Suppliers getSuppliers()
	{
	    return suppliers;
	}
	
	public Ingredients refreshIngredientsList()
	{
	    IngredientsParser ingsParser = new IngredientsParser();
	    ingredients = ingsParser.getCurrentIngredients();
	    return ingredients;
	}
	
	public void addIngredient(Ingredient ingredient)
	{
	    XmlFactory xmlFactory = new XmlFactory("ingredients");
	    xmlFactory.addIngredient(ingredient);
	}
	
	public void updateIngredientsXml()
	{
	    XmlFactory xmlFactory = new XmlFactory();
	            
	}
	
	public Ingredient getIngredient(String ingId)
	{
	    Ingredient ing = null;
	    Iterator<Ingredient> iIngs = ingredients.iterator();
	    while(iIngs.hasNext())
	    {
	        ing = iIngs.next();
	        if(ing.getId().equals(ingId))
	            break;
	    }
	    return ing;
	}
	
	public String[][] getIngNameMap()
	{
	    return ingNameMap;
	}
	
	public static String[] getIngTypes()
	{
	    String[] ingTypes = new String[ingNameMap.length];
	    int count = 0;
	    for(count = 0; count < ingNameMap.length; count++)
	    {
	        ingTypes[count] = ingNameMap[count][1];
	    }
	    return ingTypes;
	}
	
   public String getIdForName(String name)
    {
        Ingredient ing = null;
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext()) 
        {
            ing = iIngs.next();
            if(ing.getName().equals(name))
                break;
        }
        return ing == null ? "" : ing.getId();
    }
    
    public String getNameForId(String id)
    {
        Ingredient ing = null;
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext()) 
        {
            ing = iIngs.next();
            if(ing.getId().equals(id))
                break;
        }
        return ing == null ? "" : ing.getName();
    }
	
    public Vector<String> getIdsUsed()
    {
        Vector<String> idsUsed = new Vector<String>();
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext()) 
        {
            idsUsed.add(iIngs.next().getId());
        }
        return idsUsed;
    }
    
    public Vector<String> getIngredientNames()
    {
        Vector<String> names = new Vector<String>();
        
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext())
        {
            names.add(iIngs.next().getName());
        }
        return names;
    }
    
    public Vector<String> getIngredientIds()
    {
        Vector<String> ids = new Vector<String>();
        
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext())
        {
            ids.add(iIngs.next().getId());
        }
        return ids;
    }

	
	
	public Ingredients getIngredientsOfType(String ingType)
	{
        /*
         * ingType must be one of:
         * - "dairyTypes"
         * - "emulsifiers"
         * - "flavors"
         * - "salts"
         * - "stabilizers"
         * - "sweeteners":
         */
		Ingredients ings = new Ingredients();
		Ingredient ing;

		Iterator<Ingredient> iIngs = ingredients.iterator();
		while(iIngs.hasNext())
		{
			ing = iIngs.next();
			if(ingType.equals("ing") || ing.getType().equals(ingType))
				ings.addIngredient(ing);
		}
		return ings;
	}
	
	public String getNewIngTypesRdoBtns()
	{
	   String inputTags = "";
	    for(int i = 0; i < ingNameMap.length; i++)
	    {
	        inputTags += "<input type=\"radio\" name=\"newIngType\" id=\"newFlav\" value=\"" + 
	                    ingNameMap[i][1] + 
	                    "\" onchange=\"editIngType.value = '" + 
	                    ingNameMap[i][1] + 
	                    "'\">" + 
	                    ingNameMap[i][1] + 
	                    "</input>\n";
	    }
	    return inputTags;
	}
	
	public int getNumIngs(String type)
	{
	    return getIngredientsOfType(type).size();
	}
	
	public String getSplrTbl(String ingId, boolean lineNums)
	{
	    String splrTbl = "Msg from the ingsMgr: getSplrTable() under construction. Stay tuned...";
	    Ingredient ing = ingredients.getIngredient(ingId);
	    
	    return splrTbl;
	}
	
	public void addSupplier(Supplier splr)
	{
//	    suppliers.addSupplier(splr);
//	    suppliers = ingsParser.getCurrentSplrsList();
        XmlFactory xmlFactory = new XmlFactory("suppliers");
        xmlFactory.addSupplier(splr);
	    
	}
	
	public String getSplrsMnu(String cssRoot)
	{
	    String splrsmnu = "getSplrsMnu() under construction in IngredientMgr ";
	    /*
	     * ToDo: build splrsList in ingsParser, then build 
	     * splrsMnu
	     */
	    
        int numSplrs = 0;
        
        splrsmnu = "\t<select class=" + cssRoot + " id=" + cssRoot + "Id\" >\n";  
        splrsmnu += HtmlTags.getMenuDefaultOption();
        
        Iterator<Supplier> iSplrs = suppliers.iterator();
        while(iSplrs.hasNext()) 
        {
            Supplier splr = iSplrs.next();
            String splrName = splr.getSplrName();
            String splrId = splr.getSplrId();
            splrsmnu += "\t\t<option value=\"" + 
                   splrId + 
                   "\" >" + 
                   splrName + 
                   "</option>\n";
            numSplrs++;
        }
        splrsmnu += "\t</select>\n";
        
        if(numSplrs == 0)
            splrsmnu = "<p class=\"splrSlctEmpty\" id=\"splrSlctEmpty\">No suppliers have been defined.</p>";
	    return splrsmnu;
	}
	
	public String getSlctMnu(String ingType)
	{
		Ingredients ings = getIngredientsOfType(ingType);
		int numIngs = 0;
		
		String mnu = "\t<select class=\"ingSlct\" id=\"ingSlct\" >\n";  
		mnu += "\t\t<option>" + DataMgr.defaultSelectOption + "</option>\n";
		
		Iterator<Ingredient> iIngs = ings.iterator();
		while(iIngs.hasNext()) 
		{
			String ingName = (iIngs.next()).getName();
			mnu += "\t\t<option value=\"" + 
			       ingName + 
			       "\" >" + 
			       ingName + 
			       "</option>\n";
			numIngs++;
		}
		mnu += "\t</select>\n";
		
		if(numIngs == 0)
			mnu = "<p class=\"ingSlctEmpty\" id=\"ingSlctEmpty\">No " + ingType + " items have been defined.</p>";
		return mnu;
	}
	
	public String getSlctMnu(String selectName, String selectId, String ingType, String onchange, int slctdIdx, boolean hidden)
	{
        DataMgr dataMgr = new DataMgr();
        
        // if hidden cannot be null; if it is consider it false (caller does not want menu hidden);  
        // otherwise, honor its value
        hidden = !hidden ? false : hidden;
        
        Vector<String[]> namesIds = dataMgr.getNamesIds("ing");
        Iterator<String[]> iNmIds = namesIds.iterator();
        
        Vector<String> optionIds = new Vector<String>(); 
        Vector<String> displayNames = new Vector<String>();
        
        while(iNmIds.hasNext())
        {
            String[] nmId = iNmIds.next();
            displayNames.add(nmId[0]);
            optionIds.add(nmId[1]);
        }
        
        HrefTags hrefTags = new HrefTags();
        String menuClose = "\t</select>\n";
        String defaultOption = "\t\t<option value=\"0000000000000000\">" + 
                               DataMgr.defaultSelectOption + 
                               "</option>\n";
        String optionOpen = "\t\t<option value=\"";
        String optionSlctd = "\" selected ";
        String optionBrk = "\">";
        String optionClose = "</option>\n";
        
        String menu = "\t<select name=\"" + 
                      selectName +
                      "\" id=\"" + selectId + "\"";
        if(!onchange.isEmpty())
            menu += " onChange=\"" + onchange + "\"";
        if(hidden) 
            menu += " hidden"; 
        menu += " >\n";
        
        int optCnt = 0;
        menu += defaultOption;
        
        iNmIds = namesIds.iterator(); 
        while(iNmIds.hasNext())
        {
            String[] nmId= iNmIds.next();
            String optName = nmId[0];
            String optValue = nmId[1];
            String option = optionOpen;
            option += optValue;
            if(optCnt > 0 && optCnt == slctdIdx - 1)
                option += optionSlctd;
            option += optionBrk;
            menu += option;
            menu += optName;
            menu += optionClose;
            optCnt++;
        }
        menu += menuClose;
        return menu;
	}
	
	public String getSlctMnuOptions(String ingType)
	{
		Ingredients ings = getIngredientsOfType(ingType);
		int numIngs = 0;
		
		String mnu = "\t\t<option>" + DataMgr.defaultSelectOption + "</option>\n";
		Iterator<Ingredient> iIngs = ings.iterator();
		while(iIngs.hasNext()) 
		{
			String ingName = (iIngs.next()).getName();
			mnu += "\t\t<option value=\"" + 
			       ingName + 
			       "\" >" + 
			       ingName + 
			       "</option>\n";
			numIngs++;
		}
		return mnu;
	}
}
