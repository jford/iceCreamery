package com.electraink.iceCreamery.testKitchen.datastore;

import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;
import com.electraink.iceCreamery.utilities.*;

import java.text.Collator;
import java.util.*;

public class RecipeMgr
{
	private Collator col = Collator.getInstance(Locale.US);
	
//	public DataMgr dataMgr = null;
	
	private Recipes recipes = null;
	
	private Vector<String[]> imageList = null;
	Vector<String[]> enumerationDefs;
	
	public RecipeMgr()
	{
//		dataMgr = new DataMgr();
//		recipes = dataMgr.getRecipes();
		init();
	}

	private void init()
	{
		RecipesParser recipesParser = new RecipesParser();
		recipes = recipesParser.getCurrentRecipes();
		enumerationDefs = recipesParser.getEnumerationDefs();
	}
	
	public Vector<String[]> getEnumerationDefs()
	{
		return enumerationDefs;
	}
	
	public Vector<String> getIdsUsed()
	{
	    Vector<String> idsUsed = new Vector<String>();
	    Iterator<Recipe> iRcps = recipes.iterator();
	    while(iRcps.hasNext()) 
	    {
	        idsUsed.add(iRcps.next().getId());
	    }
	    return idsUsed;
	}

	public int getNumRecipes()
	{
		return recipes.size();
	}
	
	public String getRcpSlctMnu(String name, String id, String onChange, int slctdIdx, boolean filtered)
	{
		Vector<String> recipeNames = getRecipeNames();
//		Vector<String> recipeNames = filtered ? getFilteredRecipeNames() : getRecipeNames();
		HrefTags hrefTags = new HrefTags();
		Iterator<String> iRcps = recipeNames.iterator();
		String menuClose = "\t</select>\n";
		String defaultOption = "\t\t<option>" + DataMgr.defaultSelectOption + "</option>\n";
		String optionOpen = "\t\t<option value=\"";
		String optionSlctd = "\" selected ";
		String optionBrk = "\">";
		String optionClose = "</option>\n";
		
		String menu = "\t<select name=\"" + 
	                  name +
	                  "\" id=\"" +
	                  id +
	                  "\" onChange=\"" +
	                  onChange +
	                  "\" >\n";
		int rcpCnt = 0;
		menu += defaultOption;
		while(iRcps.hasNext())
		{
			String rcpName = iRcps.next();
			String option = optionOpen;
			option += rcpName;
			if(rcpCnt > 0 && rcpCnt == slctdIdx - 1)
			    option += optionSlctd;
			option += optionBrk;
			menu += option;
			menu += rcpName;
			menu += optionClose;
			rcpCnt++;
		}
		menu += menuClose;
		return menu;
	}

	// recipeMgr.getSlctMnu(        selectName,        selectId,        onchange, slctdIdx,     filtered)
    public String getSlctMnu(String selectName, String selectId, String onchange, int slctdIdx, boolean filtered)
    {
        DataMgr dataMgr = new DataMgr();
        
        Vector<String[]> namesIds = null;
        Iterator<String[]> iNmIds = null;
        
        Vector<String> optionIds = new Vector<String>(); 
        Vector<String> displayNames = new Vector<String>();
        
        String menu = "";
        
        switch(selectName)
        {
            case "recipeByName":
            case "recipeByNameSlct":
                namesIds = dataMgr.getNamesIds("rcp");
                break;
                
            case "cookMethodSlct":
                namesIds = dataMgr.getNamesIds("ckMthd");
                break;
        }
        if(namesIds.size() > 0)
        {
            iNmIds = namesIds.iterator();
            while(iNmIds.hasNext())
            {
                String[] nmId = iNmIds.next();
                displayNames.add(nmId[0]);
                optionIds.add(nmId[1]);
            }
            
            HrefTags hrefTags = new HrefTags();
            String menuClose = "\t</select>\n";
            String optionSlctd = "\" selected=true ";
            String defaultOption = "\t\t<option value=\"0000000000000000\" >" + 
                                   DataMgr.defaultSelectOption + 
                                   "</option>\n";
            String optionOpen = "\t\t<option value=\"";
            String optionBrk = "\">";
            String optionClose = "</option>\n";
            
            menu = "\t<select name=\"" + 
                          selectName +
                          "\" id=\"" +
                          selectId +
                          "\" onchange=\"" +
                          onchange +
                          "\" hidden >\n";
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
        }
        else
            menu = "<p>No recipes have been defined.</p>";
        
        return menu;
    }

	public int getHighScore()
	{
		int highScore = 0;
		
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			int recipeScore = recipe.getScoreInt();
			if(highScore < recipeScore)
				highScore = recipeScore;
		}
		return highScore;
	}
	
	public int getLowScore()
	{
		int lowScore = 0;
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			int recipeScore = recipe.getScoreInt();
			if(lowScore == 0 || lowScore > recipeScore)
				lowScore = recipeScore;
		}
		return lowScore;
	}
	
	
//	public int getNumRecipesWithEnumType(String enumType)
//	{
//		int numRecipes = 0;
//		Iterator<Recipe> iRecipes = recipes.iterator();
//		
//		while(iRecipes.hasNext())
//		{
//			Recipe recipe = (Recipe)iRecipes.next();
//			
//			switch(enumType)
//			{
//			case "dairy":
//				DairyTypes dairyTypes = recipe.getDairyTypes();
//				if(dairyTypes != null)
//				{
//					Iterator<DairyType> iDairy = dairyTypes.iterator();
//					while(iDairy.hasNext())
//					{
//						DairyType recipeDairy = iDairy.next();
//						if(recipeDairy.getEnum().equals(enumType))
//							numRecipes++;
//					} // end while(iDairy.hasNext()
//				} // end if(dairyTypes != null)
//			break;
//			case "emulsifier":
//				Emulsifiers emuls = recipe.getEmulsifiers();
//				if(emuls != null)
//				{
//					Iterator<Emulsifier> iEmuls = emuls.iterator();
//					while(iEmuls.hasNext())
//					{
//						Emulsifier recipeEmul = iEmuls.next();
//						if(recipeEmul.getEnum().equals(enumType))
//							numRecipes++;
//					} // end while(i<Enums>.hasNext())
//				}// end if(<enumType>!= null)
//			break;
//			case "salt":
//				Salts salts = recipe.getSalts();
//				if(salts != null)
//				{
//					Iterator<Salt> iSalts = salts.iterator();
//					while(salts.hasNext())
//					{
//						Salt recipeSalt = iSalts.next();
//						if(recipeSalt.getEnum().equals(enumType))
//							numRecipes++;
//					} // end while(i<Enums>.hasNext())
//				}// end if(<enumType>!= null)
//			break;
//			case "stabilizer":
//				Stabilizers stabilizers = recipe.getStabilizers();
//				if(stabilizers != null)
//				{
//					Iterator<Stabilizer> iStabilizers = stabilizers.iterator();
//					while(iStabilizers.hasNext())
//					{
//						Stabilizer recipeStabilizer = iStabilizers.next();
//						if(recipeStabilizer.getEnum().equals(enumType))
//							numRecipes++;
//						numRecipes++;
//					} // end while(i<Enums>.hasNext())
//				} // end if(enumsList != null)
//			break;
//			case "sweetener":
//				Sweeteners sweeteners = recipe.getSweeteners();
//				if(sweeteners != null)
//				{
//					Iterator<Sweetener> iSweeteners = sweeteners.iterator();
//					while(iSweeteners.hasNext())
//					{
//						Sweetener recipeSweetener = iSweeteners.next();
//						if(recipeSweetener.getEnum().equals(enumType))
//							numRecipes++;
//						numRecipes++;
//					} // end while(i<Enums>.hasNext())
//				} // end if(enumsList != null
//				default: 
//				break;
//			} // end switch
//		} // end while(iRecipes.hasNext())
//		return numRecipes;
//	}
	
	
	public boolean nameUnique(String newName)
	{
		return recipes.nameUnique(newName) ? true : false; 
	}
	
	public void addRecipe(Recipe recipe)
	{
		// adds a new recipe to the recipe xml
		XmlFactory xmlFactory = new XmlFactory("recipes");
		xmlFactory.addRecipe(recipe);
	}
	
	public void deleteRecipe(String recipeName)
	{
		// deletes a recipe from the recipe xml
		XmlFactory xmlFactory = new XmlFactory("recipes");
		xmlFactory.deleteRecipe(recipeName);
	}
	
	public void updateRecipe(Recipe newRecipe)
	{
		// xml factory will search the recipe list for an existing recipe 
		// with the same name as the new recipe, delete the existing recipe, 
		// and add the new recipe to the recipe xml
		XmlFactory xmlFactory = new XmlFactory("recipes");
		xmlFactory.updateRecipe(newRecipe);
	}
	
	public Recipes getRecipes()
	{
		return recipes;
	}
	
	public Recipe getRecipe(String rcpId)
	{
		Recipe recipe = null;
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			recipe = (Recipe)iRecipes.next();
			if(recipe.getId().compareTo(rcpId) == 0)
				break;
		}
		return recipe;
	}
	
    public Recipe getRecipe(String id, boolean byId)
    {
        Recipe recipe = null;
        if(byId)
        {
            Iterator<Recipe> iRecipes = recipes.iterator();
            while(iRecipes.hasNext())
            {
                recipe = (Recipe)iRecipes.next();
                if(recipe.getId().equals(id))
                    break;
            }
        }
        return recipe;
    }
    
	public String getIdForName(String name)
	{
        Recipe rcp = null;
        Iterator<Recipe> iRcps = recipes.iterator();
        while(iRcps.hasNext()) 
        {
            rcp = iRcps.next();
            if(rcp.getRecipeName().equals(name))
                break;
        }
        return rcp == null ? "" : rcp.getId();
	}
	
    public String getNameForId(String id)
    {
        Recipe rcp = null;
        Iterator<Recipe> iRcps = recipes.iterator();
        while(iRcps.hasNext()) 
        {
            rcp = iRcps.next();
            if(rcp.getId().equals(id))
                break;
        }
        return rcp == null ? "" : rcp.getRecipeName();
    }
    
	public Vector<String> getRecipeNames()
	{
 		Vector<String> recipeNames = new Vector<String>();
		Recipe recipe;
		Iterator<Recipe> iRcps = recipes.iterator();
		while(iRcps.hasNext())
		{
			recipe = (Recipe)iRcps.next();
			if(recipe.getRecipeName().isEmpty())
			    continue;
			recipeNames.add(recipe.getRecipeName());
		}
        recipeNames.sort(col);
		return recipeNames;
	}
	
	public Vector<String> getRecipeIds()
	{
        Vector<String> recipeIds = new Vector<String>();
        Recipe recipe;
        Iterator<Recipe> iRcps = recipes.iterator();
        while(iRcps.hasNext())
        {
            recipe = (Recipe)iRcps.next();
            recipeIds.add(recipe.getId());
        }
        return recipeIds;
	}

	public Vector<String> getFilteredRecipeNames()
	{
		Vector<String>filteredNamesFromXml = new Vector<String>();
		String filename = DataMgr.getPathToIceCreameryKitchenData();
//		Path filteredNamesPath = Paths.get(filename);
		String filteredNames = "";
		try
		{
			filteredNames = Utilities.read_file(filename);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// read Xml and build list of recipes that meet filters
		int idx = 0;
		int end = 0;
		String tagStart = "<name>";
		String tagEnd = "</name>";
		while((idx = filteredNames.indexOf(tagStart, end)) != -1)
		{
			end = filteredNames.indexOf(tagEnd, idx);
			filteredNamesFromXml.add(filteredNames.substring(idx + tagStart.length(), end));
		}
		return filteredNamesFromXml;
	}
	public void filterRecipeNames(Vector<String> filterList)
	{
		// filterList is a list of enum values (value, not  
		// name: "Heavy cream" not "HEAVY_CREAM")
		
 		Vector<String> recipeNames = new Vector<String>();
		
		boolean matchFound = false;
		
		/**********************************************************
		 *	The filterRecipeNames() method consists of two nested 
		 *	while() loops. The first loop cycles through all recipes 
		 *	in the database. For each recipe, the second, nested while() 
		 *  loop cyclesthrough the collection of filters specified by 
		 *  the calling code. 
		 *
		 *	If the recipe contains all of the user-specified items 
		 *  (for each filter check, foundMatch = true), it is added 
		 *  to the method's return list.
		 *
		 *	If a filter check returns false, the outer while() loop gets  
		 *	the next recipe, and the inner loop starts over from the top.
		 *
		 ************************************************************/

		// cycle through all recipes
		Iterator<Recipe> iRecipes = recipes.iterator();
 		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			matchFound = false;
			
//			DairyTypes dairyTypes = recipe.getDairyTypes();
//			Emulsifiers emulsifiers = recipe.getEmulsifiers();
//			Flavorings flavorings = recipe.getFlavorings();
//			Salts salts = recipe.getSalts();
//			Stabilizers stabilizers = recipe.getStabilizers();
//			Sweeteners sweeteners = recipe.getSweeteners();
			
			// always check for null before creating iterator; recipe may not 
			// contain all ingredient types
			
//			Iterator<DairyType> iDairyTypes = null;
//			if(dairyTypes != null)
//				iDairyTypes = dairyTypes.iterator();
//			
//			
//			Iterator<Emulsifier> iEmuls = null;
//			if(emulsifiers != null)
//				iEmuls = emulsifiers.iterator();
//			
//			Iterator<Flavoring> iFlavs = null;
//			if(flavorings != null)
//				iFlavs = flavorings.iterator();
//			
//			
//			Iterator<Salt> iSalts = null;
//			if(salts != null)
//				iSalts = salts.iterator();
//			
//			Iterator<Stabilizer> iStabs = null;
//			if(stabilizers != null)
//				iStabs = stabilizers.iterator();
//			
//			Iterator<Sweetener> iSweets = null;
//			if(sweeteners != null)
//				iSweets = sweeteners.iterator();
//			
			String recipeItemType = "";

			boolean getNextFilter = false;
			
			// for each recipe, the next, inner while() loop cycles through 
			// the filters to see if there is a match in the current recipe
			Iterator<String> iFilters = filterList.iterator();
			while(iFilters.hasNext())
			{
				getNextFilter = false;
				recipeItemType = "";

				/*********************************************************
				 * 
				 *	Filters represent the choices made in the Recipe 
				 *  Selction Form. Each selection is represented by a 
				 *  filter; the filterList is a collection of all filters.
				 *  
				 *  The list of recipes returned by this method are those
				 *  recipes that match all of the filters (user choices) 
				 *  in the filter list.
				 *  
				 *  The first while loop cycles through all recipes in the 
				 *  database. For each recipe, another while loop cycles 
				 *  through the filter list. If any filter check returns false
				 *  (!matchFound), the recipe check stops without adding the recipe
				 *  to the return list, the recipe while loop retrieves 
				 *  another recipe, and the filter check begins again from the top
				 *  of the filter list.
				 * 
				 *	Filters contain two elements, in a String[] Array.class
				 *				
				 *	Element [0] identifies the datatype:
				 *				
				 *		"Cook Method: "
				 *		"Flavor: "
				 *		"Score: "
				 *		"Dairy: "
				 *		"Salt: "
				 *		"Stab: "
				 *		"Sweet: "
				 *				
				 *	Element [1] identifies the specific item that must be matched 
				 *  for the recipe to be added to the return List.class The 
				 *  item is identified by its enumeration value. For example, if 
				 *  the recipes are being filtered by the use of heavy cream,
				 *	the filter would have been constructed like this:
				 *					
				 *			String[] filter = {"Dairy: ", "HEAVY_CREAM"};
				 *				
				 ************************************************************************************/				

				
				// cross reference filter data with recipe components; if at any time
				// a filter item cannot be found in the recipe, bail. Search is for 
				// recipes that match all filters
				String filter = (String)iFilters.next();
				String filterData[] = filter.split(": ");
				
				switch(filterData[0])
				{
//				case "Dom Flavor":
					// Recipe.getFlavor() returns an object of type Flavor, 
					// which in turn has a getName() method that returns the 
					// name of the dominant flavor. But it's easier to call 
					// recipe.getDomFlavorName() than recipe.getFlavor().getName(); 
					// both should work
//					matchFound = filterData[1].equals(recipe.getDomFlavorName()) ? true : false;
//					break;
//				case "Flavor item":
//					if(flavorings != null)
//					{
//						while(iFlavs.hasNext())
//						{
//							Flavorings recipeFlavorings = recipe.getFlavorings();
//							Iterator<Flavoring> iRecFlavs = recipeFlavorings.iterator();
//							while(iRecFlavs.hasNext())
//							{
//								recipeItemType = iRecFlavs.next().getEnum();
//								matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//								if(matchFound)
//								{
//									// set flag to get out of while(iFlavs.hasNext(){}
//									getNextFilter = true;
//									// first, get out of while(iRecFlavsHasNext(){}
//									break;
//								}
//							}
//							if(getNextFilter)
//								// if flavorings matched, break; if not, stay in loop
//								break;
//						}
//					}
//					break;
//				case "Dairy":
//					if(dairyTypes != null)
//					{
//						while(iDairyTypes.hasNext())
//						{
//							DairyTypes recipeDairyTypes = recipe.getDairyTypes();
//							Iterator<DairyType> iDairy = recipeDairyTypes.iterator();
//							while (iDairy.hasNext())
//							{
//								recipeItemType = iDairy.next().getEnum();
//								matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//								if(matchFound)
//								{
//									getNextFilter = true;
//									break;
//								}
//							}
//							if(getNextFilter)
//								break;
//						}
//					}
//					break;
//					
//				case "Emul":
//					if(emulsifiers != null)
//					{
//						Emulsifiers recipeEmuls = recipe.getEmulsifiers();
//						iEmuls = recipeEmuls.iterator();
//						while (iEmuls.hasNext())
//						{
//							recipeItemType = iEmuls.next().getEnum();
//							matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//							if(matchFound)
//							{
//								getNextFilter = true;
//								break;
//							}
//						}
//						if(getNextFilter)
//							break;
//					}
//					break;
//
//				case "Salt":
//					if(salts != null)
//					{
//						while(iSalts.hasNext())
//						{
//							Salts recipeSalts = recipe.getSalts();
//							iSalts = recipeSalts.iterator();
//							while (iSalts.hasNext())
//							{
//								recipeItemType = iSalts.next().getEnum();
//								matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//								if(matchFound)
//								{
//									getNextFilter = true;
//									break;
//								}
//							}
//							if(getNextFilter)
//								break;
//						}
//					}
//					break;
//					
//				case "Stab":
//					if(stabilizers != null)
//					{
//						while(iStabs.hasNext())
//						{
//							Stabilizers recipeStabs = recipe.getStabilizers();
//							iStabs = recipeStabs.iterator();
//							while (iStabs.hasNext())
//							{
//								recipeItemType = iStabs.next().getEnum();
//								matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//								if(matchFound)
//								{
//									getNextFilter = true;
//									break;
//								}
//							}
//							if(getNextFilter)
//								break;
//						}
//					}
//					break;
//					
//				case "Sweet":
//					if(sweeteners != null)
//					{
//						while(iSweets.hasNext())
//						{
//							Sweeteners recipeSweets = recipe.getSweeteners();
//							iSweets = recipeSweets.iterator();
//							while (iSweets.hasNext())
//							{
//								recipeItemType = ((Sweetener)iSweets.next()).getEnum();
//								matchFound = recipeItemType.equals(filterData[1]) ? true : false;
//								if(matchFound)
//								{
//									getNextFilter = true;
//									break;
//								}
//							}
//							if(getNextFilter)
//								break;
//						}
//					}
//					break;
//					
				case "Cook Method":
					String cookMethod = recipe.getCookMethod();
					if(cookMethod != null)
					{
//						recipeItemType = ((Sweetener)iSweets.next()).getEnum();
						matchFound = recipeItemType.equals(filterData[1]) ? true : false;
					}
					break;
					
				case "Score":
					String scoreStr = recipe.getScoreStr();
					if(!scoreStr.equals("Unrated"))
					{
						int recipeScore = recipe.getScoreInt();
						int filterScore;
						String lessThan = "less than ";
						String moreThan = "more than ";
						String equals = "equals ";
						if(filterData[1].contains(lessThan)) 
						{
							filterScore = Integer.parseInt(filterData[1].substring(filterData[1].indexOf(lessThan) + lessThan.length()));
							matchFound = recipeScore < filterScore ? true : false;
						}
						else if(filterData[1].contains(moreThan))
						{
							filterScore = Integer.parseInt(filterData[1].substring(filterData[1].indexOf(moreThan) + moreThan.length()));
							matchFound = recipeScore > filterScore ? true : false;
						}
						else if(filterData[1].contains(equals))
						{
							filterScore = Integer.parseInt(filterData[1].substring(filterData[1].indexOf(equals) + equals.length()));
							matchFound = recipeScore == filterScore ? true : false;
						}
						else
							matchFound = false;
					}
					break;
				default:
					break;
					
				}
			}
			
			// if matchFound is still true, then recipe passes the filter test; add
			// its name to the return list
			if(matchFound)
				recipeNames.add(recipe.getRecipeName());
		} // end of while(iRecipes.hasNext()) loop
 		
		// if one or more recipes were found that pass the filter test, process the list
		if(!recipeNames.isEmpty())
		{
			// treeset removes duplicates and automatically sorts
			TreeSet<String> rNames = new TreeSet<String>(recipeNames);
			
			// once duplicates purged and list sorted, return to vector 
			recipeNames = new Vector<String>(rNames);
			// send vector to xmlFactory to write new filteredRecipeNames.xml
		}
		XmlFactory xmlFactory = new XmlFactory(recipeNames);
	}
	
	public String getInputFormValue(String rcpId, String inputName)
	{
		// recipe input form is asking for value of "param" for specified recipe
		String value = "";
		//  is input form in add mode? if so, recipeByName will be null
		if(rcpId != null && !rcpId.isEmpty())
			value = getRecipe(rcpId).getInputFormValue(inputName);
		return value;
	}
	
	public String getInputFormMenuSelected(String recipeByName, String param, String menuItem)
	{
		// does input param match cook method specified in recipe?
		String value = "";
		//  is input form in add mode? if so, recipeByName will be null
		if(recipeByName != null)
			value = getRecipe(recipeByName).getInputFormSelectedMenuItem(param, menuItem);
		return value;
	}
	
	public Vector<String> getDomFlavorNames()
	{
		Vector<String> flavorNames = new Vector<String>();
		Recipe recipe;
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			recipe = (Recipe)iRecipes.next();
//			Flavor flavor = recipe.getFlavor();
//			flavorNames.add(flavor.getName());
		}
		flavorNames.sort(col);
		return flavorNames;
	}
	public Vector<String> getRecipesByDomFlavor(String flavorName)
	{
		Vector<String> recNamesByDomFlavor = new Vector<String>();
		Recipe recipe;
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
//			recipe = (Recipe)iRecipes.next();
//			Flavor flavor = recipe.getFlavor();
//			if(flavor.getName().compareTo(flavorName) == 0)
//				recNamesByDomFlavor.add(recipe.getRecipeName());
		}
        recNamesByDomFlavor.sort(col);
		return recNamesByDomFlavor;
	}

//	public int getNumRecsWithEnum(String enumType, String enumName)
//	{
//		int numRecipes = 0;
//		Iterator<Recipe> iRecs = recipes.iterator();
//		while(iRecs.hasNext()) 
//		{
//			Recipe recipe = iRecs.next();
//		
//			switch(enumType)
//			{
//			case "cookMethod":
//				String recipeCookMethod = recipe.getCookMethod();
//				if(recipeCookMethod.equals(enumName))
//					numRecipes++;
//				break;
//			case "dairy":
//				DairyTypes dairyTypes = recipe.getDairyTypes();
//				if(dairyTypes != null)
//				{
//					Iterator<DairyType> iDairy = dairyTypes.iterator();
//					while(iDairy.hasNext())
//					{
//						DairyType recipeDairy = iDairy.next();
//						if(recipeDairy.getEnum().equals(enumName))
//							numRecipes++;
//					} // end while(iDairy.hasNext()
//				} // end if(dairyTypes != null)
//			case "emulsifier":
//				Emulsifiers emulsifiers = recipe.getEmulsifiers();
//				if(emulsifiers != null)
//				{
//					Iterator<Emulsifier> iEmulsifiers = emulsifiers.iterator();
//					while(iEmulsifiers.hasNext())
//					{
//						Emulsifier recipeEmulsifier = iEmulsifiers.next();
//						if(recipeEmulsifier.getEnum().equals(enumName))
//							numRecipes++;
//					}// end while(iEmulsifiers .hasNext()
//				} // end if(emulsifiers != null)
//				break;
//			case "flavorType":
//				Flavor flavor = recipe.getFlavor();
//				if(flavor != null)
//				{
//					Flavorings flavorings = flavor.getFlavorings();
//					if(flavorings != null)
//					{
//						Iterator<Flavoring> iFlavorings = flavorings.iterator();
//						while(iFlavorings.hasNext())
//						{
//							Flavoring recipeFlavoring = (Flavoring)iFlavorings.next();
//							if(recipeFlavoring.getEnum().equals(enumName))
//								numRecipes++;
//						}
//					}
//				}
//				break;
//			case "salt":
//				Salts salts = recipe.getSalts();
//				if(salts != null)
//				{
//					Iterator<Salt> iSalts = salts.iterator();
//					while(iSalts.hasNext())
//					{
//						Salt recipeSalt = iSalts.next();
//						if(recipeSalt.getEnum().equals(enumName))
//							numRecipes++;
//					} // end while(iSalt.hasNext()
//				} // end if(salts != null)
//				break;
//			case "stabilizer":
//				Stabilizers stabilizers = recipe.getStabilizers();
//				if(stabilizers != null)
//				{
//					Iterator<Stabilizer> iStabilizers = stabilizers.iterator();
//					while(iStabilizers.hasNext())
//					{
//						Stabilizer recipeStabilizer = iStabilizers.next();
//						if(recipeStabilizer.getEnum().equals(enumName))
//							numRecipes++;
//					} // end while(iStabilizers.hasNext())
//				} // end if(stabilizers != null)
//				break;
//			case "sweetener":
//				Sweeteners sweeteners = recipe.getSweeteners();
//				if(sweeteners != null)
//				{
//					Iterator<Sweetener> iSweeteners = sweeteners.iterator();
//					while(iSweeteners.hasNext())
//					{
//						Sweetener recipeSweetener = iSweeteners.next();
//						if(recipeSweetener.getEnum().equals(enumName))
//							numRecipes++;
//					} // end while(iSweeteners.hasNext())
//				} // end if(sweeteners != null)
//			default:
//				break;
//			}
//		}
//		return numRecipes;
//	}
//	
//	public Vector<String> getNamesRecsNoXEnum(String enumType, String xEnum)
//	{
//		/*
//		 * Return a list of all recipes that do NOT have the specified
//		 * ingredient enum (xEnum); enumType should identify the type of 
//		 * ingredient and should match one of the case labels in the 
//		 * switch(enumType) 
//		 */
//		Vector<String> recsWithNoXEnum = new Vector<String>();
//
//		String nameChk = "";
//		
//		Iterator<String> iRecNames;
//		Iterator<Recipe> iRecipes;
//
//		// return a list of names of all recipes that do not 
//		// include dairyType
//		Recipe recipe;
//		iRecipes = recipes.iterator();
//		while(iRecipes.hasNext()) 
//		{
//			// stack the return deck with names of all recipes
//			recsWithNoXEnum.add(iRecipes.next().getRecipeName());
//			// list will be pared down just before returning
//		}
//		
//		// begin search for recipes with specified enum
//		iRecipes = recipes.iterator();
//		while(iRecipes.hasNext())
//		{
//			String recEnum = "";
//			Iterator<DairyType> iRecDairy = null;
//			Iterator<Emulsifier> iRecEmuls = null;
//			Iterator<Flavoring> iRecFlavorings = null;
//			Iterator<Salt> iRecSalts = null;
//			Iterator<Stabilizer> iRecStabs = null;
//			Iterator<Sweetener> iRecSweets = null;
//			
//			// get a recipe...
//			recipe = (Recipe)iRecipes.next();
//
//			// ...and check for enum content
//			switch(enumType)
//			{
//			case "dairy":
//				DairyTypes dairy = recipe.getDairyTypes();
//				if(dairy != null)
//					iRecDairy = dairy.iterator();
//				break;
//			case "emulsifier":
//				Emulsifiers emuls = recipe.getEmulsifiers();
//				if(emuls!= null)
//					iRecEmuls = emuls.iterator();
//				break;
//			case "flavorType":
//				Flavor flavor = recipe.getFlavor();
//				Flavorings flavorings = null;
//				if(flavor != null)
//					flavorings = flavor.getFlavorings();
//				if(flavorings != null)
//					iRecFlavorings = flavorings.iterator();
//				break;
//			case "salt":
//				Salts salts = recipe.getSalts();
//				if(salts != null)
//					iRecSalts = salts.iterator();
//				break;
//			case "stabilizer":
//				Stabilizers stabs = recipe.getStabilizers();
//				if(stabs != null)
//					iRecStabs = stabs.iterator();
//				break;
//			case "sweetener":
//				Sweeteners sweets = recipe.getSweeteners();
//				if(sweets != null)
//					iRecSweets = sweets.iterator();
//				break;
//			default:
//				break;
//			}
//		
//			if(iRecDairy != null)
//				recEnum = iRecDairy.hasNext() ? iRecDairy.next().getDairyTypeName() : null;
//			else if(iRecEmuls != null)
//				recEnum = iRecEmuls.hasNext() ? iRecEmuls.next().getEmulsifierName() : null;
//			else if(iRecFlavorings != null)
//				recEnum = iRecFlavorings.hasNext() ? iRecFlavorings.next().getEnum() : null;
//			else if(iRecSalts != null)
//				recEnum = iRecSalts.hasNext() ? iRecSalts.next().getSaltName() : null;
//			else if (iRecStabs != null)
//				recEnum = iRecStabs.hasNext() ? iRecStabs.next().getEnum() : null;
//			else if (iRecSweets != null)
//				recEnum = iRecSweets.hasNext() ? iRecSweets.next().getSweetenerName() : null;
//
//			iRecNames = recsWithNoXEnum.iterator();
//			while(iRecNames.hasNext())
//			{
//				// if this recipe does not contain the specified enum,
//				// noting to do except move on to the next recipe
//				if(!recEnum.equals(xEnum))
//					break;
//				
//				// iRecNames is the iterator of the return collection; it 
//				// started out as a list compiled from the getRecipeName()
//				// for every recipe in the system; if there is a match
//				// between incoming dairyType and the dairy found in the 
//				// recipe, the recipe name will be removed from this list
//				
//				nameChk = iRecNames.next();
//				
//				if(nameChk.equals(recipe.getRecipeName()))
//				{
//					// recipe is the current recipe, for which we have 
//					// just matched an ingredient with the specified
//					// xEnum item
////					xEnumFound = true;
//					recsWithNoXEnum.removeElement(nameChk);
//					break; // get next recipe, in while(iRecipes.hasNext())
//				}
//			}
//		} // end (while(iRecipes(hasNext())
//		recsWithNoXEnum.sort(col);
//		return recsWithNoXEnum;
//	}
//	
//	public int getNumRecsWithNoXEnumType(String xEnum)
//	{
//		return getNamesRecsWithNoEnumType(xEnum).size();
//	}
//	
//	public Vector<String> getNamesRecsWithNoEnumType(String enumType)
//	{
//		Vector<String> recNames = new Vector<String>();
//		
//		Iterator<Recipe> iRecipes = recipes.iterator();
//		while(iRecipes.hasNext())
//		{
//			Recipe recipe = (Recipe)iRecipes.next();
//			Object enumObj = null;
//			
//			switch(enumType)
//			{
//			case "dairy":
//				enumObj = recipe.getDairyTypes();
//			break;
//			case "emulsifier":
//				enumObj = recipe.getEmulsifiers();
//			break;
//			case "salt":
//				enumObj = recipe.getSalts();
//			break;
//			case "flavorType":
//				Flavor flavor = recipe.getFlavor();
//				if(flavor != null)
//					enumObj = flavor.getFlavorings();
//			break;
//			case "stabilizer":
//				enumObj = recipe.getStabilizers();
//			break;
//			case "sweetener":
//				enumObj = recipe.getSweeteners();
//			break;
//			default: 
//				break;
//			} // end switch
//			if(enumObj == null)
//			{
//				recNames.add(recipe.getRecipeName());
//			} 
//		} // end while(iRecipes.hasNext())
//		return recNames;
//	}
//	
//	public Vector<String> getRecNamesByNoEmul(String emul)
//	{
//		String nameChk = "";
//
//		// return a list of names of all recipes that do not 
//		// include dairyType
//		Vector<String> recNamesByNoEmul = new Vector<String>();
//
//		// stack the return deck with names of all recipes
//		Recipe recipe;
//		Iterator<Recipe> iRecipes = recipes.iterator();
//		while(iRecipes.hasNext()) 
//		{
//			recNamesByNoEmul.add(iRecipes.next().getRecipeName());
//		}
//		Iterator<String> iRecNames = recNamesByNoEmul.iterator();
//		
//		// reset recipes iterator, then begin cycling through them
//		iRecipes = recipes.iterator();
//		
//		boolean emulFound = false;
//		while(iRecipes.hasNext())
//		{
//			emulFound = false;
//			// get a recipe...
//			recipe = (Recipe)iRecipes.next();
//			// ...and check the recipe for dairy content
//			Emulsifiers recEmuls = recipe.getEmulsifiers();
//			if(recEmuls != null)
//			{
//				// yes, the recipe contains emulsifiers; is there a match with the 
//				// specified emulsifier? Iterate through the recipe's dairy list
//				Iterator<Emulsifier> iEmuls = recEmuls.iterator();
//				while(iEmuls.hasNext())
//				{
//					int idx = 0;
//					Emulsifier recEmul = (Emulsifier)iEmuls.next();
//					
//					// does the recipe emulsifier contain the specified string?
//					if(recEmul.getEnum().contains(emul))
//					{
//						// reset recNamesByNoEmul.iterator
//						iRecNames = recNamesByNoEmul.iterator();
//						idx = 0;
//						
//						while(iRecNames.hasNext())
//						{
//							// iRecNames is the iterator of the return list; it 
//							// started out as a list compiled from the getRecipeName()
//							// for every recipe in the system; if there is a match
//							// between incoming emul and the emulsifier found in the 
//							// recipe, the recipe name will be removed from the 
//							// return list
//							nameChk = iRecNames.next();
//							if(nameChk.equals(recipe.getRecipeName()))
//							{
//								// recipe is the current recipe, for which we have 
//								// just matched the emulsifier item with the filter emul
//								emulFound = true;
//								break;
//							}
//							idx++;
//						}
//						if(emulFound)
//						{
//							recNamesByNoEmul.removeElementAt(idx);
//							break; // get next recipe, in while(iRecipes.hasNext())
//						}
//					}
//				}
//			}
//		}
//		recNamesByNoEmul.sort(col);
//		return recNamesByNoEmul;
//	}
//	
//	public Vector<String> getNamesRecsWithEnum(String enumType, String enumName)
//	{
//		Vector<String> recNames = new Vector<String>();
//		
//		Recipe recipe = null;
//		Iterator<Recipe> iRecipes = recipes.iterator();
//		while(iRecipes.hasNext()) 
//		{
//			recipe = iRecipes.next();
//			Flavor flavor = recipe.getFlavor();
//		
//			switch(enumType)
//			{
//			case "dairyType":
//				DairyTypes dairy = recipe.getDairyTypes();
//				if(dairy != null)
//				{
//					Iterator<DairyType> iTypes = dairy.iterator();
//					while(iTypes.hasNext())
//					{
//						DairyType dType = iTypes.next();
//						if(dType.getEnum().contains(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//				
//			case "domFlavor":
//				if(flavor != null)
//				{
//					String domFlavor = flavor.getName();
//					if(domFlavor.contains(enumName))
//						recNames.add(recipe.getRecipeName());
//				}
//				break;
//			
//			case "flavorType":
//				if(flavor != null)
//				{
//					Flavorings flavorings = flavor.getFlavorings();
//					Iterator<Flavoring> iTypes = flavorings.iterator();
//					while(iTypes.hasNext())
//					{
//						Flavoring dType = iTypes.next();
//						if(dType.getEnum().equals(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//				
//			case "emulsifier":
//				Emulsifiers emulsifiers = recipe.getEmulsifiers();
//				if(emulsifiers != null)
//				{
//					Iterator<Emulsifier> iEmuls = emulsifiers.iterator();
//					while(iEmuls.hasNext())
//					{
//						Emulsifier recEmul = iEmuls.next();
//						if(recEmul.getEnum().equals(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//				
//			case "salt":
//				Salts salts = recipe.getSalts();
//				if(salts != null)
//				{
//					Iterator<Salt> iSalts = salts.iterator();
//					while(iSalts.hasNext())
//					{
//						Salt recSalt = iSalts.next();
//						if(recSalt.getEnum().equals(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//			
//			case "stabilizer":
//				Stabilizers stabs = recipe.getStabilizers();
//				if(stabs != null)
//				{
//					Iterator<Stabilizer> iStabs = stabs.iterator();
//					while(iStabs.hasNext())
//					{
//						Stabilizer recStab = iStabs.next();
//						if(recStab.getEnum().equals(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//				
//			case "sweetener":
//				Sweeteners sweets = recipe.getSweeteners();
//				if(sweets != null)
//				{
//					Iterator<Sweetener> iSweets = sweets.iterator();
//					while(iSweets.hasNext())
//					{
//						Sweetener recSweet = iSweets.next();
//						if(recSweet.getEnum().equals(enumName))
//						{
//							recNames.add(recipe.getRecipeName());
//							break;
//						}
//					}
//				}
//				break;
//			default:
//				break;
//			}
//		}
//		recNames.sort(col);
//		return recNames;
//	}
	
	public Vector<String[]> getImageList()
	{
		// returns a list of all recipes that have images associated with 
		// them, in an array that includes the image filename; all images are 
		// stored in the /iceCreamery/images directory
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			String filename = recipe.getImageFilename();
			if(filename.length() > 0)
			{
				String[] image = {"",""};
				image[0] = recipe.getRecipeName();
				image[1] = filename;
				imageList.add(image);
			}
		}
		return imageList;
	}
	
}
