package com.electraink.iceCreamery.utilities;

import com.electraink.iceCreamery.comments.*;
import com.electraink.iceCreamery.comments.Comment;
import com.electraink.iceCreamery.testKitchen.datastore.*;
import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.suppliers.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;

import java.util.*;
import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class XmlFactory
{
//    boolean stopForDebugging = false;
	
	public Document doc = null;
	
    private Recipes recipes;
    private RecipesParser recipesParser;
    private RecipesXmlFactory recipesXmlFactory;
    
    private Ingredients ingredients;
    private IngredientsParser ingredientsParser;
    private IngredientsXmlFactory ingredientsXmlFactory;
    
    private Suppliers suppliers;
    
    private DOMParser domParser;
    
    private static String xmlHeader = DataMgr.xmlHeader;
    
    public XmlFactory()
    {
        /*
         * Use new XmlFactory(String type) to parse current xml file 
         * and create a new data object
         * 
         * To generate new xml from current memory, use this constructor
         * with no args, then call getUpdated...Xml() and write the 
         * return string to file
         */
        domParser = new DOMParser();
    }
    
    public XmlFactory(Vector<String> filteredRecipeNames)
    {
    	/*
    	 *  writes filteredRecipeNames.xml to /iceCreamery/testKitchen/xml;
    	 *  filtered names are names that match user-defined filter 
    	 *  criteria specified in recipeSelect.jsp
    	 */
        domParser = new DOMParser();
    	doFilteredRecipeNames(filteredRecipeNames);
    }

    public XmlFactory(String type)
	{
        /*
         * parses current xml file and creates new data object;
         * to generate new xml from current memory, create new XmlFactory() 
         * without arguments, call getUpdated...Xml(), and write the return 
         * string to file
         */

        domParser = new DOMParser();
		
		switch(type)
		{
		case "recipes":
			doRecipes();
			break;
		case "ingredients":
			doIngredients();
			break;
		case "suppliers":
//		    doIngredients();
//		    String updatedXml = getUpdatedIngredientsXml(ingredients, suppliers);
//		    Utilities.write_file(DataMgr.ingredientsDB, updatedXml, false);
		    doIngredients();
		    break;
		}
	}
	
	private void doRecipes()
	{
		recipesParser = new RecipesParser();
        recipes = recipesParser.parse(DataMgr.recipesDB);
	}
	
	private void doIngredients()
	{
		ingredientsParser = new IngredientsParser();
		/*
		 * after parsing, retrieve new ingredients and suppliers objs from 
		 * parser so 
		 */
		        
        // suppliers are maintained in ingredients.xml
        ingredients = ingredientsParser.parse(DataMgr.ingredientsDB);
        suppliers = ingredientsParser.getCurrentSplrsList();
	}
	
	private void doSuppliers()
	{
	    // suppliers maintained in ingredients.xml
	    doIngredients();
	}
	
	public static boolean xmlFileValid(String type)
	{
		/*
		 *  if no recipes file exists, getRecipeFile() will create a 
		 *  valid xml file with no contents
		 */
		
	    boolean fileValid = false;
	            
	    String xmlContents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
	            + "<iceCreamery/>";
	    String target = "";
		switch(type)
		{
		    case "recipes":
		        target = DataMgr.recipesDB;
		        break;
		        
		    case "ingredients":
		        target = DataMgr.ingredientsDB;
		        break;
		}

        try
        {
        if(!fileValid)
        {
            File f = new File(target);
            fileValid = f.exists();
            
            if(!fileValid)
            {
                fileValid = f.createNewFile();
                if(fileValid)
                    Utilities.write_file(target, xmlContents, true);
            }
        }
        }
        catch(Exception e)
        {
            fileValid = false;
        }
        
		return fileValid;
	}
	
	private static void createNew(String fileType)
	{
	    /*
	     * writes new file containing only xml header and root element;
	     * if file exists, it will be overwritten
	     * 
	     * use: when updating (adding, editing or deleting) an existing
	     * db file; clear the file first, then reconstruct from data object
	     */
	    String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
		String targetFileName = DataMgr.getPathToIceCreameryKitchenData();
		
		switch(fileType)
		{
		case "recipesXml":
			targetFileName += "recipes.xml";
			break;
		case "ingredientsXml":
			targetFileName += "ingredients.xml";
			break;
		}
   		Utilities.write_file(targetFileName, xmlHeader + "<iceCreamery/>", true);
	}
	

	 
    private Text getTextNode(String text)
    {
        return doc.createTextNode(text);
    }

	private void doFilteredRecipeNames(Vector<String> filteredRecipeNames)
	{
		// write filteredNamesXml to webapps/testKitchen/xml
		String filename = DataMgr.getPathToIceCreameryKitchenData();
		String xmlContents = getFilteredNamesXml(filteredRecipeNames);
		Utilities.write_file(filename, xmlContents, true);
	}
	
	private String getFilteredNamesXml(Vector<String> filteredRecipeNames)
	{
		String filteredNamesXml = "<recipeNames>\n";
		String tagStart = "    <name>";
		String tagEnd = "</name>";
		String xmlEnd = "</recipeNames>\n";
		
		int nameCnt = 0;
		
		Iterator<String> iNames = filteredRecipeNames.iterator();
		while(iNames.hasNext())
		{
			String name = (String)iNames.next();
			filteredNamesXml += tagStart + name + tagEnd + "\n";
			nameCnt++;
		}
		filteredNamesXml = nameCnt > 0? filteredNamesXml + xmlEnd : "<names/>";
		
		return filteredNamesXml;
	}
	
	public void addRecipe(Recipe recipe)
	{
		// first, add the recipe to the recipes list
		recipes.addRecipe(recipe);
		// ...then update recipe.sml
		updateRecipesXml();
	}
	
	public void addIngredient(Ingredient ing)
	{
	    ingredients.addIngredient(ing);
	    updateIngredientsXml();
	}
	
	public void addSupplier(Supplier splr)
	{
	    suppliers.addSupplier(splr);
	    updateIngredientsXml();
	}
	
	public void deleteRecipe(String rcpId)
	{
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext()) 
		{
			Recipe recipe = (Recipe)iRecipes.next();
			if(recipe.getId().equals(rcpId))
			{
				recipes.removeRecipe(rcpId);
				break;
			}
		}
		updateRecipesXml();
	}
	
	public void deleteIngredient(String ingId)
	{
        Iterator<Ingredient> iIngs = ingredients.iterator();
        while(iIngs.hasNext()) 
        {
            Ingredient ing = iIngs.next();
            if(ing.getId().equals(ingId))
            {
                ingredients.removeIngredient(ingId);
                break;
            }
        }
        updateRecipesXml();
	}
	
	public void deleteSupplier(String splrId)
	{
	    Iterator<Supplier> iSplrs = suppliers.iterator();
	    while(iSplrs.hasNext())
	    {
	        Supplier splr = iSplrs.next();
	        if(splr.getSplrId().equals(splrId))
	        {
	            suppliers.removeSupplier(splrId);
	            break;
	        }
	    }
	    // suppliers maintained in ingredients.xml
	    updateIngredientsXml();
	}
	
	public void updateRecipe(Recipe newRecipe)
	{
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			Recipe oldRecipe = (Recipe)iRecipes.next();
			if(oldRecipe.getRecipeName().compareTo(newRecipe.getRecipeName()) == 0)
			{
				recipes.removeRecipe(oldRecipe.getRecipeName());
				break;
			}
		}
		addRecipe(newRecipe);
	}
	
	private Text getCdataNode(String text)
	{
		return doc.createCDATASection(text.trim());
	}

	private void updateRecipesXml()
	{
		// generate a new xml string from current recipes obj
		String updatedXml = getUpdatedRecipesXml(recipes);
		// write a new xml file to repository
		Utilities.write_file(DataMgr.recipesDB, xmlHeader + updatedXml, true);
	}
	
	private void updateIngredientsXml()
	{
	    String updatedXml = getUpdatedIngredientsXml(ingredients, suppliers);
	    Utilities.write_file(DataMgr.ingredientsDB,  xmlHeader + updatedXml,  true);
	}
	
	public String getUpdatedRecipesXml(Recipes recipes)
	{
		Document doc = null;
		
		Vector<Recipe> recipeList = recipes.getRecipes();
		recipesXmlFactory = new RecipesXmlFactory();
		doc = recipesXmlFactory.doNewRecipesXml(recipeList);
		
		return generateXml(doc);
	}
	
	public String getUpdatedIngredientsXml(Ingredients ingredients, Suppliers suppliers)
	{
	    Document doc = null;
	    
	    Vector<Ingredient> ingredientList = ingredients.getIngredients();
	    ingredientsXmlFactory = new IngredientsXmlFactory();
	    doc = ingredientsXmlFactory.doNewIngredientsXml(ingredientList, suppliers);
	    
	    return generateXml(doc);
	}
	
    private String generateXml(Document doc)
    {
        String newXml = "";
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = null;
        try
        {
            trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            // INDENT, sadly, doesn't indent, but it does add line breaks
            // to the XML, making it easier to read than it would otherwise be.
            
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            newXml = sw.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newXml;
    }
    
     private class IngredientsXmlFactory
    {
    	private Element appNode = null;
    	private Element dairyTypesNode = null;
    	private Element flavoringsNode = null;
    	private Element emulsifiersNode = null;
        private Element ingNameNode = null;
        private Element intTypeNode = null;
    	private Element ingredientNode = null;
    	private Element ingNotesNode = null;
    	private Element root = null;
    	private Element saltsNode = null;
    	private Element stabilizersNode = null;
    	private Element supplierNode = null;
    	private Element suppliersNode = null;
        private Element splrContactNode = null;
    	private Element splrNameNode = null;
    	private Element splrNotesNode = null;
        private Element sweetenersNode = null;
    	
    	private Document doNewIngredientsXml(Vector<Ingredient> ingredientList, 
    	                                     Suppliers suppliers)
    	{
    		int count = 0;
    		int numIngTypes = 0;
    		Iterator<Ingredient> iIngs = ingredientList.iterator();
    		
    		Vector<Ingredient> dairyTypes = new Vector<Ingredient>();
    		numIngTypes++;
    		
            Vector<Ingredient> flavorings = new Vector<Ingredient>();
            numIngTypes++;
            
            Vector<Ingredient> emulsifiers = new Vector<Ingredient>();
            numIngTypes++;
            
            Vector<Ingredient> salts = new Vector<Ingredient>();
            numIngTypes++;
            
            Vector<Ingredient> stabilizers = new Vector<Ingredient>();
            numIngTypes++;
            
            Vector<Ingredient> sweeteners = new Vector<Ingredient>();
            numIngTypes++;
            
            while(iIngs.hasNext())
            {
                Ingredient ing = iIngs.next();
                if(ing.getType().equals("dairyType"))
                    dairyTypes.add(ing);
                else if(ing.getType().equals("flavoring"))
                    flavorings.add(ing);
                else if(ing.getType().equals("emulsifier"))
                    emulsifiers.add(ing);
                else if(ing.getType().equals("salt"))
                    salts.add(ing);
                else if(ing.getType().equals("stabilizer"))
                    stabilizers.add(ing);
                else if(ing.getType().equals("sweetener"))
                    sweeteners.add(ing);
            }
    		
    		String ingredientsXml = DataMgr.ingredientsDB;
    		createNew("ingredientsXml");
    		
    		doc = domParser.parse(ingredientsXml);
    		
    		root = doc.getDocumentElement();
    		appNode = doc.createElement("ingredients");
            dairyTypesNode = doc.createElement("dairyTypes");
            flavoringsNode = doc.createElement("flavorings");
            emulsifiersNode = doc.createElement("emulsifiers");
            saltsNode = doc.createElement("salts");
            stabilizersNode = doc.createElement("stabilizers");
            sweetenersNode = doc.createElement("sweeteners");

            for(count = 0; count < numIngTypes; count++)
            {
                if(count == 0)
                    iIngs = dairyTypes.iterator();
                else if(count == 1)
                    iIngs = flavorings.iterator();
                else if(count == 2)
                    iIngs = emulsifiers.iterator();
                else if(count == 3)
                    iIngs = salts.iterator();
                else if(count == 4)
                    iIngs = stabilizers.iterator();
                else if(count == 5)
                    iIngs = sweeteners.iterator();
                
                String ingName = "";
                String id = "";
                String type = "";
                String qty = "";
                while(iIngs.hasNext())
                {
                    // reset for each time through the loop
                    ingredientNode = null;
                    Ingredient ingredient = null;

                    ingredientNode = doc.createElement("ingredient");

                    ingredient = (Ingredient)iIngs.next();
                    ingName = ingredient.getName();
                    id = ingredient.getId();
                    qty = ingredient.getQuantity();
                    type = ingredient.getType();
                    
                    ingredientNode.setAttribute("id", id);
                    ingredientNode.setAttribute("qty", qty);
                    ingredientNode.setAttribute("type", type);
                    
                    ingNameNode = doc.createElement("ingName");
                    ingNameNode.appendChild(doc.createCDATASection(ingName));
                    
                    ingredientNode.appendChild(ingNameNode);
                    
                    if(type.equals("dairyType"))
                        dairyTypesNode.appendChild(ingredientNode);
                    else if(type.equals("flavoring"))
                        flavoringsNode.appendChild(ingredientNode);
                    else if(type.equals("emulsifier"))
                        emulsifiersNode.appendChild(ingredientNode);
                    else if(type.equals("salt"))
                        saltsNode.appendChild(ingredientNode);
                    else if(type.equals("stabilizer"))
                        stabilizersNode.appendChild(ingredientNode);
                    else if(type.equals("sweetener"))
                        sweetenersNode.appendChild(ingredientNode);
                }
            }
            appNode.appendChild(dairyTypesNode);
            appNode.appendChild(flavoringsNode);
            appNode.appendChild(emulsifiersNode);
            appNode.appendChild(saltsNode);
            appNode.appendChild(stabilizersNode);
            appNode.appendChild(sweetenersNode);
            root.appendChild(appNode);
            
            if(suppliers.size() > 0)
            {
                suppliersNode = doc.createElement("suppliers");
                
                String splrContact = "";
                String splrId = "";
                String splrName = "";
                String splrNotes = "";
                String splrTelephone = "";
                
                Iterator<Supplier> iSplrs = suppliers.iterator();
                while(iSplrs.hasNext())
                {
                    supplierNode = doc.createElement("supplier");
                    Supplier splr = iSplrs.next();
                    
                    splrContact = splr.getContactName();
                    splrId = splr.getSplrId();
                    splrName = splr.getSplrName();
                    splrNotes = splr.getSplrNotes();
                    splrTelephone = splr.getSplrTelephone();

                    supplierNode.setAttribute("id", splrId);
                    supplierNode.setAttribute("splrTelephone", splrTelephone);

                    splrContactNode = doc.createElement("splrContact");
                    splrContactNode.appendChild(doc.createCDATASection(splrContact));
                    
                    splrNameNode = doc.createElement("splrName");
                    splrNameNode.appendChild(doc.createCDATASection(splrName));
                    
                    splrNotesNode = doc.createElement("splrNotes");
                    splrNotesNode.appendChild(doc.createCDATASection(splrNotes));
                    
                    supplierNode.appendChild(splrContactNode);
                    supplierNode.appendChild(splrNameNode);
                    supplierNode.appendChild(splrNotesNode);
                    
                    suppliersNode.appendChild(supplierNode);
                }
                root.appendChild(suppliersNode);
            }
            return doc;
    	}
    }
    
    private class RecipesXmlFactory
    {
    	private Element appNode = null;
    	private Element captionNode = null;
    	private Element commentNode = null;
    	private Element imageNode = null;
    	private Element ingredientNode = null;
    	private Element ingredientsNode = null;
    	private Element instructionsNode = null;
    	private Element nameNode = null;
    	private Element recipesNode = null;
    	private Element recipeNode = null;
    	private Element root = null;
    	
    	private Document doNewRecipesXml(Vector<Recipe> recipeList)
    	{
    		int count = 0;
    		String recipesXml = DataMgr.recipesDB;
    		createNew("recipesXml");
    		doc = domParser.parse(recipesXml);
    		
     		root = doc.getDocumentElement();
    		appNode = doc.createElement("recipes");
    		
    		
    		// recipes
    		recipesNode = doc.createElement("recipesNode");
    		Recipe recipe = null;
    		Iterator<Recipe> recipesI = recipeList.iterator();
     		while(recipesI.hasNext())
    		{
    			recipe = (Recipe)recipesI.next();
    			String cookTemp = recipe.getCookTemp();
    			String cookTime = recipe.getCookTime();
    			String cookMethod = recipe.getCookMethod();
    			String id = recipe.getId();
    			
    			if(cookMethod.compareTo("NONE") == 0)
   					cookMethod = "";
    			if(cookTemp.compareTo(DataMgr.cookTempDefault) == 0)
    				cookTemp = "";
    			if(cookTime.compareTo(DataMgr.cookTimeDefault) == 0)
    				cookTime = "";
    			
    			recipeNode = doc.createElement("recipe");
    			recipeNode.setAttribute("dateofentry", recipe.getDateOfEntry());
    			recipeNode.setAttribute("cookmethod", cookMethod);
    			recipeNode.setAttribute("cooktemp", cookTemp);
    			recipeNode.setAttribute("cooktime", cookTime);
    			recipeNode.setAttribute("id", id);
//    			recipeNode.setAttribute("name", recipe.getRecipeName());
    			recipeNode.setAttribute("score", recipe.getScoreStr());
    			recipesNode.appendChild(recipeNode);
    			
    			nameNode = doc.createElement("name");
    			nameNode.appendChild(doc.createCDATASection(recipe.getRecipeName()));
    			recipeNode.appendChild(nameNode);
    			
    			String imageFilename = recipe.getImageFilename();
    			String imageCaption = recipe.getCaption();
    			imageNode = doc.createElement("image");
    			captionNode = doc.createElement("caption");
    			if(imageFilename.length() > 0)
    			{
    				imageNode.setAttribute("filename", imageFilename);
    				recipeNode.appendChild(imageNode);
    				
    				if(!imageCaption.isEmpty())
    				{
    					captionNode.appendChild(getTextNode(imageCaption));
    	    			imageNode.appendChild(captionNode);
    				}
    			}
    			
    			String domFlavor = recipe.getDomFlavor();
    			if(!domFlavor.isEmpty())
    			{
    			    recipeNode.setAttribute("domFlavor", domFlavor);
    			}
    			
    			commentNode = doc.createElement("comments");
    			String commentText = recipe.getComments();
    			if(commentText.length() > 0)
    			{
        			commentNode.appendChild(doc.createCDATASection(commentText));
	    			recipeNode.appendChild(commentNode);
    			}
    			
    			instructionsNode = doc.createElement("instructions");
    			String instructionsText = recipe.getInstructions();
    			if(instructionsText.length() > 0)
    			{
	    			instructionsNode.appendChild(doc.createCDATASection(instructionsText));
	    			recipeNode.appendChild(instructionsNode);
    			}
    			Vector<String[]> ingredients = recipe.getIngredients();
    			if(ingredients.size() > 0)
    			{
    			    ingredientsNode = doc.createElement("ingredients");
    			    Iterator<String[]> iIngs = ingredients.iterator();
                    while(iIngs.hasNext())
                    {
                        ingredientNode = doc.createElement("ingredient");
                        String[] ing = iIngs.next();
                        ingredientNode.setAttribute("ing", ing[0]);
                        ingredientNode.setAttribute("qty", ing[1]);
                        ingredientsNode.appendChild(ingredientNode);
                    }
                    recipeNode.appendChild(ingredientsNode);
    			}
    			appNode.appendChild(recipeNode);
    		}
    		root.appendChild(appNode);
    		
    		return doc;
    	}
    }
}
