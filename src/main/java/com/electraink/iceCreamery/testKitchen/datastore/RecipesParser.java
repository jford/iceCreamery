package com.electraink.iceCreamery.testKitchen.datastore;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;
import com.electraink.iceCreamery.utilities.*;

import java.util.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RecipesParser
{
	private DOMParser domParser = new DOMParser();
	
	private String caption = "";
	private String filename = "";
	
	private String node_name = "";
	private String xmlFilename = DataMgr.recipesDB;
    
    private Recipes recipes = new Recipes();
    
    private Vector<String[]> enumerationDefs = new Vector<String[]>();
    
    public RecipesParser()
    {
    }

    public Vector<String[]> getEnumerationDefs()
    {
        return enumerationDefs;
    }
    
    public Recipes getCurrentRecipes()
    {
    	// check for existence of recipe file; if it's missing, the factory will create 
    	// a new one so proceed with assurance one will exist when the parser parses 
    	XmlFactory.xmlFileValid("recipes");
    	return parse(xmlFilename);
    }
    
    public Recipes parse(String xml)
    {
        // Parse the XML and create a DOM node tree
        Document parsedXML = domParser.parse(xml);
        
    	// walk the DOM tree
        processDocument(parsedXML);
        
        return recipes;
    }
    
//    public Recipes parseFromStream(String xmlContents)
//    {
//        System.out.println("in Recipes parseFromStream(xml file contents)");
//    	Document parsedXML = domParser.parseFromStream(xmlContents);
//    	processDocument(parsedXML);
//    	
//    	return recipes;
//    }

    private void processDocument(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();
        
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                if(node_name.compareTo("iceCreamery") == 0)
                {
                    processIceCreamery(n);
                }
//                if(node_name.compareTo("recipes") == 0)
//                {
//                	processRecipes(n);
//                }
//                if(node_name.compareTo("enumerations") == 0)
//                {
//                	processEnumerations(n);
//                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processDocument(child);
        }
    }
    
    private void processIceCreamery(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                if(node_name.compareTo("recipes") == 0)
                {
                    processRecipes(n);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processIceCreamery(child);
        }
    }

    private void processCookMethodDefs(Node n)
    {
    	node_name = n.getNodeName();
    	if(node_name.compareTo("cookMethodDef") == 0)
    	{
    		processCookMethodDef(n);
    	}
        for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
           processCookMethodDefs(child);
       }
    }
    
    private void processCookMethodDef(Node n)
    {
    	String[] def = new String[3];
    	def[0] = "cookMethodDef";

        NamedNodeMap attributes = n.getAttributes();
        if(attributes != null)
        {
	        int numAttrs = attributes.getLength();
	        for(int i = 0; i < numAttrs; i++)
	        {
	        	Attr attr = (Attr)attributes.item(i);
	        	String attrName = attr.getNodeName();
	        	String attrValue = attr.getNodeValue();
	        	
	        	if(attrName.compareTo("constantName") == 0)
	        	{
	        		def[1] = attrValue;
	        	}
	        	if(attrName.compareTo("displayName") == 0)
	        	{
	        		def[2] = attrValue;
	        	}
	        }
        }
        enumerationDefs.add(def);
    }
    
    private void processRecipes(Node n)
    {
        int type = n.getNodeType();
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
                if(node_name.compareTo("recipe") == 0)
                {
                	Recipe recipe = new Recipe();
                    processRecipe(n, recipe);
                    
                    // processImage() retrieved a filename for an image and 
                    // processCaption() retrieved a caption for the image, both 
                    // of which are embedded in a recipe, but neither process had 
                    // access to the recipe object until now
                    if(!filename.isEmpty())
                    	recipe.setImageFilename(filename);
                    if(!caption.isEmpty())
                    	recipe.setCaption(caption);
                    recipes.addRecipe(recipe);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processRecipes(child);
        }
    }
    
    private void processRecipe(Node n, Recipe recipe)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();
        
         NamedNodeMap attributes = n.getAttributes();
        if(attributes != null && node_name.compareTo("recipe") == 0)
        {
	        int numAttrs = attributes.getLength();
	        for(int i = 0; i < numAttrs; i++)
	        {
	        	Attr attr = (Attr)attributes.item(i);
	        	String attrName = attr.getNodeName();
	        	String attrValue = attr.getNodeValue();
	        	
	        	if(attrName.equals("id"))
	        	    recipe.setId(attrValue);
	        	if(attrName.compareTo("cookmethod") == 0)
	        		recipe.setCookMethod(attrValue);
	        	if(attrName.compareTo("cooktemp") == 0)
	        		recipe.setCookTemp(attrValue);
	        	if(attrName.compareTo("cooktime") == 0)
	        		recipe.setCookTime(attrValue);
	        	if(attrName.equals("domFlavor"))
	        	    recipe.setDomFlavor(attrValue);
	        	if(attrName.compareTo("dateofentry") == 0)
	        		recipe.setDateOfEntry(attrValue);
	        	if(attrName.compareTo("score") == 0)
	        	{
	        		// score of 0 is returned as "Unrated" by recipe's getScorStr() method;
	        		// but recipe stores the score as an integer
	        		if(attrValue.compareTo(Recipe.zeroScoreStr) == 0)
	        			attrValue = "0";
	        		recipe.setScore(Integer.parseInt(attrValue));
	        	}
	        }
        }

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
                if(node_name.equals("name"))
                {
                    recipe.setRecipeName(getText(n));
                }
                if( node_name.compareTo("image") == 0)
                {
                	processImage(n);
                }
                if(node_name.compareTo("comment") == 0)
                {
                	recipe.setComment(getText(n));
                }
                if(node_name.compareTo("instructions") == 0)
                {
                	recipe.setInstructions(getText(n));
                }
                if(node_name.equals("ingredients"))
                    processIngredients(n, recipe);
                break;
            }
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processRecipe(child, recipe);
        }
    }
    
    private void processIngredients(Node n, Recipe recipe)
    {
        int type = n.getNodeType();
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
                if(node_name.compareTo("ingredient") == 0)
                {
                    processIngredient(n, recipe);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processIngredients(child, recipe);
        }
    }
    
    private void processIngredient(Node n, Recipe recipe)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();
        
         NamedNodeMap attributes = n.getAttributes();
        if(attributes != null && node_name.compareTo("recipe") == 0)
        {
            int numAttrs = attributes.getLength();
            
            String[] ing = new String[2];
            for(int i = 0; i < numAttrs; i++)
            {
                Attr attr = (Attr)attributes.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();
                
                if(attrName.equals("ing"))
                    ing[0] = attrValue;
                if(attrName.equals("qty"))
                    ing[1] = attrValue;
            }
            recipe.addIngredient(ing);
        }

        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processIngredient(child, recipe);
        }
    }

    private void processImage(Node n)
    {
    	int type = n.getNodeType();
    	
    	node_name = n.getNodeName();
        
    	NamedNodeMap attributes = n.getAttributes();
        if(attributes != null)
        { 
        	if(node_name.compareTo("image") == 0)
        	{
		        int numAttrs = attributes.getLength();
		        for(int i = 0; i < numAttrs; i++)
		        {
		        	Attr attr = (Attr)attributes.item(i);
		        	String attrName = attr.getNodeName();
		        	String attrValue = attr.getNodeValue();
		        	
		        	if(attrName.compareTo("filename") == 0)
		        		filename = attrValue;
		        }
        	}
        }
        switch (type)
        {
	        case Node.ELEMENT_NODE:
	        {
	        	node_name = n.getNodeName();
	            if(node_name.compareTo("caption") == 0)
	            {
	                processCaption(n);
	            }
		        break;
	        }
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processImage(child);
        }
    }

    private void processCaption(Node n)
    {
    	node_name = n.getNodeName();
    	if(node_name.compareTo("caption") == 0)
    	{
    		caption = getText(n);
    	}
        for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
           processCaption(child);
       }
    }

    private void processComment(Node n, String comment)
	{
	    int type = n.getNodeType();
	
	    switch (type)
	    {
	        case Node.ELEMENT_NODE:
	        {
	        	node_name = n.getNodeName();
	            if(node_name.compareTo("comment") == 0)
	            {
	            	comment = getText(n);
	            }
		        break;
	        }
	    }
	    for(Node child = n.getFirstChild();
	             child != null;
	             child = child.getNextSibling())
	    {
	        processComment(child, comment);
	    }
	}
   
   private void processInstructions(Node n, String instructions)
   {
	    int type = n.getNodeType();
		
	    switch (type)
	    {
	        case Node.ELEMENT_NODE:
	        {
	        	node_name = n.getNodeName();
	            if(node_name.compareTo("instructions") == 0)
	            {
	            	instructions = getText(n);
	            }
		        break;
	        }
	    }
	    for(Node child = n.getFirstChild();
	             child != null;
	             child = child.getNextSibling())
	    {
	        processInstructions(child, instructions);
	    }
   }
   
   private String getText(Node n)
   {
       String text = "";
       for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
            int type = child.getNodeType();
            if(type == Node.TEXT_NODE)
        		text = child.getNodeValue();
        }
    	return text;
    }
}


