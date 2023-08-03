package com.electraink.iceCreamery.testKitchen.datastore;

import com.electraink.iceCreamery.testKitchen.datastore.dairy.DairyType;
import com.electraink.iceCreamery.testKitchen.datastore.dairy.DairyTypes;
import com.electraink.iceCreamery.testKitchen.datastore.suppliers.*;
import com.electraink.iceCreamery.utilities.*;

import java.text.Collator;
import java.util.*;

public class DataMgr
{
	private IngredientsMgr ingsMgr;
	private RecipeMgr recipeMgr;
	private Collator col;
	
	public static final boolean debug = true;
	public static boolean stop_for_debugging = false;
	
	public static final int idSize = 16;
	
	public static String cookTempDefault = " (enter temp)";
	public static String cookTimeDefault = " (enter time)";
	
	public static String defaultSelectOption = "(Select from Menu)";
	public static String defaultSelectId = "0000000000000000";
	
	// paramSeparator separates param name from value in RecipeMgr.getParamValue()
	public static String paramSeparator = "::";
	
	public static String pathMrkr = System.getProperty("file.separator");
	public static String lineBrk = System.getProperty("line.separator");
	public static String appAddr = Utilities.getTomcatHome() + pathMrkr + "webapps" + pathMrkr + "iceCreamery" + pathMrkr;
	
	public static String ingredientsDB = getPathToAppBase() + "testKitchen" + pathMrkr + "xml" + pathMrkr + "ingredients.xml";
	public static String recipesDB = getPathToAppBase() + "testKitchen" + pathMrkr + "xml" + pathMrkr + "recipes.xml";
	public static String commentsDB = getPathToAppBase() + "testKitchen" + pathMrkr + "xml" + pathMrkr + "comments.xml";
	public static String adminDB = getPathToAppBase() + "testKitchen" + pathMrkr + "xml" + pathMrkr + "admin.xml";
	public static String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	public static String propertiesFile = getPathToPropertiesFile() + "ui.properties";
	
	public static String[] xmlEditScopes = {
                                              "allRcps", 
                                              "allIngs", 
                                              "rcpByNm", 
                                              "ingByNm", 
                                              "allDrys", 
                                              "allEmuls", 
                                              "allFlvs", 
                                              "allSalts", 
                                              "allStabs", 
                                              "allSwts" 
                                           };

	
	public DataMgr()
	{
		init();
	}
	
	private void init()
	{
		ingsMgr = new IngredientsMgr();
		recipeMgr = new RecipeMgr();
	    col = Collator.getInstance(Locale.US);
	}
	
	private static String getPathToPropertiesFile()
	{
	    return "D:\\dev\\eclipse\\workspace\\2021-12\\iceCreamery\\src\\main\\resources\\";
	}
	
	public String[][] getIngNameMap()
	{
	    return ingsMgr.getIngNameMap();
	}
	
	public void addSupplier(Supplier splr)
	{
	    ingsMgr.addSupplier(splr);
	}
	
	public void addRecipe(Recipe rcp)
	{
	    recipeMgr.addRecipe(rcp);
	}
	
	public void addIngredient(Ingredient ing)
	{
	    ingsMgr.addIngredient(ing);
	}
	public Ingredient getIngredient(String ingId)
	{
	    return ingsMgr.getIngredient(ingId);
	}
	
	public Recipe getRecipe(String rcpId)
	{
	    return recipeMgr.getRecipe(rcpId);
	}
	
	public String makeNameUnique(String name, String type)
	{
	    Vector<String> names = null;
	    switch(type)
	    {
	        case "recipe":
	            names = recipeMgr.getRecipeNames();
	            break;
	            
	        case "ingredient":
	            names = ingsMgr.getIngredientNames();
	            break;
	    }
	    names.sort(col);
	    
        String mrkr = name + " (";
	    int reps = 1;
	    int idx = 0;
	    int end = 0;
	    Iterator<String> iNms = names.iterator();
	    while(iNms.hasNext())
	    {
	        String used = iNms.next();
	        if(used.equals(name))
	        {
	            used += " (xxx)";
	            reps++;
	        }
	        else if(used.startsWith(mrkr) && used.endsWith(")"))
	        {
	            
                idx = used.indexOf(mrkr) +  mrkr.length();
                end = used.indexOf(")", idx);
	            try
	            {
	                reps = Integer.parseInt(used.substring(idx, end));
	            }
	            catch(Exception e)
	            {
	                continue;
	            }
	            reps++;
	        }
	        if(reps > 1)
	        {
                idx = used.indexOf(mrkr) +  mrkr.length();
                end = used.indexOf(")", idx);
                name += " (" + Integer.toString(reps) + ")";
	        }
	    }
	    return name;
	}
	
	public Recipes getRecipes()
	{
		return recipeMgr.getRecipes();
	}
	
	public boolean isIdUnique(String id)
	{
	    // checks id against all ids assigned to recipes and ingredients
	    boolean isIdUnique = true;
	    Vector<String> ids = null;
	    for(int i = 0; i < 2; i++)
	    {
	        if(i == 0)
	            ids = recipeMgr.getIdsUsed();
	        else if(i == 1)
	            ids = ingsMgr.getIdsUsed();
	        
	        Iterator<String> iIds = ids.iterator();
	        while(iIds.hasNext())
	        {
	            if(iIds.next().equals(id))
	            {
	                isIdUnique = false;
	                break;
	            }
	        }
	        if(!isIdUnique)
	            break;
	    }
	    return isIdUnique;
	}
	
	public Ingredients getIngredients()
	{
		return ingsMgr.getIngredients();
	}
	
	public Suppliers getSuppliers()
	{
	    return ingsMgr.getSuppliers();
	}

	public String getSlctMnu(String ingType)
	{
		return ingsMgr.getSlctMnu(ingType);
	}
	public String getSlctMnuOptions(String ingType)
	{
		return ingsMgr.getSlctMnuOptions(ingType);
	}
	
	private static String getPathToAppBase()
	{
		String appBaseProd = Utilities.getTomcatHome() + pathMrkr + "webapps" + pathMrkr + "iceCreamery" + pathMrkr;
		String appBaseDebug = "D:\\dev\\eclipse\\workspace\\2021-12\\iceCreamery\\src\\main\\webapp\\";

		String appBase = DataMgr.debug ? appBaseDebug : appBaseProd;
		
		return appBase;
	}
	
	public static String getPathToXml()
	{
		String appBase = getPathToAppBase();
		String xmlDir = appBase + "testKitchen" + pathMrkr + "xml" + pathMrkr;
		
		return xmlDir;
	}
	
	public static String getPathToIceCreameryKitchenData()
	{
		return getPathToAppBase() + "testKitchen" + pathMrkr + "xml" + pathMrkr;
	}
	
	public static String getPathMrkr()
	{
		return pathMrkr;
	}
	
	public static String getDateStr()
	{
		return new Date().toString();
	}
	
    public Vector<String[]> getNamesIds(String which)
    {
        /* 
         *  which == one of 
         *  - "rcp"
         *  - "ckMthd"
         *  - "ing"
         *  - plural form of ingredient type:
         *      - "dairyTypes"
         *      - "emulsifiers"
         *      - "flavors"
         *      - "salts"
         *      - "stabilizers" 
         *      - "sweeteners"
         *    
         *  Need alphabetical list of names, then compile
         *  list of names/ids for return
         *  names/ids return Vector of String[] ==
         *  - [0] = name
         *  - [1] = id
         */
        Vector<String[]> namesIds = new Vector<String[]>();

        switch(which)
        {
            case "rcp":
            Vector<String> recipeNames = new Vector<String>();
            Recipe recipe;
            Iterator<Recipe> iRcps = getRecipes().iterator();
            while(iRcps.hasNext())
            {
                recipe = (Recipe)iRcps.next();
                if(recipe.getRecipeName().isEmpty())
                    continue;
                recipeNames.add(recipe.getRecipeName());
            }
            recipeNames.sort(col);
            
            Iterator<String>iNms = recipeNames.iterator();
            while(iNms.hasNext()) 
            {
                String xfr = iNms.next();
                String id = recipeMgr.getRecipe(xfr).getId();
                String[] nmId = { xfr, id };
                namesIds.add(nmId);
            }
            break;
            
            case "ckMthd":
            {
                String[][] cookMethods = {
                                           { "None", "NONE" },
                                           { "Sous Vide", "SOUSVIDE" },
                                           { "Stove top", "STOVETOP" }
                                         };
                int i = 0;
                for(i = 0; i < cookMethods.length; i++)
                {
                    String[] nmId = { cookMethods[i][0], cookMethods[i][1] };
                    namesIds.add(nmId);
                }
            }
            break;
            
            case "ing":
            case "dairyType":
            case "emulsifier":
            case "flavoring":
            case "salt":
            case "stabilizer":
            case "sweetener":
            {
                Vector<String> ingNames = new Vector<String>();
                Ingredients ings = ingsMgr.getIngredientsOfType(which);
                Ingredient ing = null;
                Iterator<Ingredient>iIngs = ings.iterator();
                while(iIngs.hasNext())
                {
                    ing = iIngs.next();
                    if(which.equals("ing") || which.equals(ing.getType()))
                    {
                        ingNames.add(ing.getName());
                    }
                }
                ingNames.sort(col);
                
                Iterator<String>iIngNms = ingNames.iterator();
                while(iIngNms.hasNext()) 
                {
                    String xfr = iIngNms.next();
                    String id = getIdForName("ing", xfr);
//                    String id = getIngredient(xfr).getId();
                    String[] nmId = {xfr, id};
                    namesIds.add(nmId);
                }
            }
            break;
        }
        return namesIds;
    }
    
    public String getNewIngTypesRdoBtns()
    {
        return ingsMgr.getNewIngTypesRdoBtns();
    }
    
    public String getNameForId(String type, String id)
    {
        String name = "";
        
        switch(type)
        {
            case "rcp":
                name = recipeMgr.getNameForId(id);
                break;
                
            case "ing":
                name = ingsMgr.getNameForId(id);
                break;
        }
        return name;
    }
    
    public String getIdForName(String type, String name)
    {
        String id = "";
        
        switch(type)
        {
            case "rcp":
                id = recipeMgr.getIdForName(name);
                break;
                
            case "ing":
                id = ingsMgr.getIdForName(name);
                break;
        }
        return id;
    }
    
    public int getNumIngs(String type)
    {
        return ingsMgr.getNumIngs(type);
    }
    
    public String getSplrTbl(String ingId, boolean lineNums)
    {
        return ingsMgr.getSplrTbl(ingId, lineNums);
    }
    
    public String getSplrsMnu(String cssRoot)
    {
        String splrsMnu = ingsMgr.getSplrsMnu(cssRoot);
        return splrsMnu;
    }
	
	public String getIngSlctTbl(String cssRoot, String rcpId, boolean qty)
	{
		/*
		 * The getIngSlctTbl() method returns an HTML-tagged table of checkboxes for the ingredients 
		 * itemized in itemNames. If qty is true, each ingredient name will be followed by a text 
		 * input labeled Qty.
		 * 
		 *  In the Recipe and Ingredients Editor (recipe_input_form.jsp), the table is used to 
		 *  add ingredient names and quantities to a recipe. In the Recipe Selector (recipeSelect.jsp), 
		 *  the table is used to pick filters for qualifying entries onto the selection menu. 
		 *  Quantities are not a factor in this qualification table, so the qty arg is false and 
		 *  the Qty: input field is omitted. The recipeByName value is not available in the Recipe 
		 *  Selector (recipeSelect.jsp), so an empty string is passed in as the recipeByName arg. 
		 *  
		 *  Ingredient entries are listed in a 2-silo list; the first silo gets the first four names.
		 *  
		 *  If there are only four names, the first silo gets them all and the 2nd silo is not displayed.
		 *  
		 *  If there is an uneven number of names, the first silo gets the extra.
		 */
	    
	    Ingredients ings = ingsMgr.getIngredientsOfType(cssRoot);

	    int numItems = ings.size();
		int count = 0;
		int rowCount = 0;
		
		int siloBrkPt = numItems <= 4 ? numItems : numItems / 2;
		
		// if itemNames contains an odd number, the division by 2 is rounded down and 
		// the first silo gets one less than silo 2; not what is wanted, so an adjustment needs
		// to be made
		if(siloBrkPt < numItems - siloBrkPt)
			siloBrkPt++;
		
		// start the silo 2 data at the break point
		int silo2count = siloBrkPt;
		
		
		
		String tableCssClass = cssRoot + "Tbl";
		String rowCssClass = cssRoot + "Row";
		String colCssClass = cssRoot + "Col";
		
		String table = HtmlTags.indent + 
				       HtmlTags.getTag("table", tableCssClass, "") +
				       DataMgr.lineBrk;
		Iterator<Ingredient> iIngs = ings.iterator();
		Ingredient ing = null;
		while(count < siloBrkPt && ings.hasNext())
		{
			int cCount = 1;
			// start a new row
			table += HtmlTags.indentRow + 
					 HtmlTags.getTag("tr", rowCssClass, rowCssClass + Integer.toString(rowCount++)) +
					 DataMgr.lineBrk;
			
			// first column in row, ingredient checkbox
			table += HtmlTags.indentCol +
					 HtmlTags.getTag("td", colCssClass, colCssClass + Integer.toString(cCount++));

			ing = ings.next();
			String ingId = ing.getId();
			String inputValue = ingId;
			String displayName = ing.getName();
			String inputTag = HtmlTags.getCheckbox(displayName,
			                                       cssRoot, 
			                                       cssRoot + "Ckbx", 
			                                       cssRoot + "#" + inputValue, 
			                                       inputValue, 
			                                       recipeMgr.getInputFormValue(rcpId, ingId));
			table += inputTag;
			
			// close 1st col
			table += HtmlTags.indentCol +
					 HtmlTags.colClose +
					 DataMgr.lineBrk;
			
			// is quantity input needed?
			if(qty)
			{
				// 2nd col, quantity input field
				inputValue = recipeMgr.getInputFormValue(rcpId, inputValue + "qty");
				table += HtmlTags.getTag("td", colCssClass, colCssClass + Integer.toString(cCount++));
				inputTag = HtmlTags.getTextField("Qty:", 
				                                 cssRoot  + "#" + ingId, 
				                                 "5", 
				                                 inputValue, 
				                                 null, 
				                                 null);
				table += inputTag;
	
				// close 2nd column
				table += HtmlTags.indentCol +
						 HtmlTags.colClose +
						 DataMgr.lineBrk;
			} // end if(qty)
			
			if(siloBrkPt < numItems && silo2count < numItems)
			{
				// first column in silo 2, ingredient checkbox
				table += HtmlTags.indentCol +
						 HtmlTags.getTag("td", colCssClass, colCssClass + Integer.toString(cCount++));
				ing = ings.next();
				inputValue = ing.getId();
				displayName = ing.getName();
				inputTag = HtmlTags.getCheckbox(displayName, 
				                                cssRoot, 
				                                cssRoot + inputValue, 
				                                cssRoot + inputValue, 
				                                inputValue, 
				                                recipeMgr.getInputFormValue(rcpId, inputValue));
				table += inputTag;
				
				// close 1st col
				table += HtmlTags.indentCol +
						 HtmlTags.colClose +
						 DataMgr.lineBrk;
				
				// is quantity input needed?
				if(qty)
				{
					// 2nd col, quantity input field
					inputValue = recipeMgr.getInputFormValue(rcpId, inputValue + "qty");
					table += HtmlTags.getTag("td", colCssClass, colCssClass + Integer.toString(cCount++));
					inputTag = HtmlTags.getTextField("Qty:", 
					                                 cssRoot, 
					                                 "5", 
					                                 inputValue, 
					                                 null, 
					                                 null);
					table += inputTag;
		
					// close 2nd column
					table += HtmlTags.indentCol +
							 HtmlTags.colClose +
							 DataMgr.lineBrk;
				} // end if(qty)
				silo2count++;
			} // end ifif(siloBrkPt < numItems && silo2count < numItems)
			
			// close row
			table += HtmlTags.indentRow + 
					 HtmlTags.rowClose +
					 DataMgr.lineBrk;

		} // end while(count < numFlavs)
		
		// close table
		table += HtmlTags.indent + 
				 HtmlTags.tableClose +
				 DataMgr.lineBrk;
		
		if(ings.size() == 0)
		    table = "<p class=\"ingTblEmptyMsg\">No " + cssRoot + " ingredients defined.</p>";
		
		return table;
	}
	

	public String getRcpSlctMnu(String name, String id, String onchange, int slctdIdx, boolean filtered)
	{
		return recipeMgr.getSlctMnu(name, id, onchange, slctdIdx, filtered);
	}
	
	public String getIngSlctMnu(String ingByName, String id, String onchange, int slctdIdx, boolean debug)
	{
	    return ingsMgr.getSlctMnu(ingByName, id, id, onchange, slctdIdx, debug);
 	}
	
    public String getSlctMnu(String selectName, String selectId, String ingType, String onchange, int slctdIdx, boolean filtered)
    {
        String menu = "";
        switch(selectName)
        {
            case "recipeByName":
            case "recipeByNameSlct":
            case "cookMethodSlct":
                menu = recipeMgr.getSlctMnu(selectName, selectId, onchange, slctdIdx, filtered);
                break;
            case "ingSlct":
                boolean hidden = filtered;
                menu = ingsMgr.getSlctMnu(selectName, selectId, ingType, onchange, slctdIdx, hidden);
//                public String getSlctMnu(String selectName, String selectId, String onchange, String slctdIdx, boolean filtered)
                break;
       }
        return menu; 
    }
    

}
