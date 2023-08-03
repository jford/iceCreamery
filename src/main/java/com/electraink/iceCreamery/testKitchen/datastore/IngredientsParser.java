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

import java.util.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class IngredientsParser 
{
	private DOMParser domParser = new DOMParser();
	
	private String node_name = "";
	private String xmlFilename = DataMgr.ingredientsDB;
    
    private Ingredients ingredients = new Ingredients();
    private Suppliers splrsList = new Suppliers();
    
    public IngredientsParser()
    {
    }
    
    public Ingredients getCurrentIngredients()
    {
        XmlFactory.xmlFileValid("ingredients");
        ingredients =  parse(xmlFilename);
        return ingredients;
    }
    
    public Suppliers getCurrentSplrsList()
    {
        /*
         * ToDo:  build splrsList
         */
        XmlFactory.xmlFileValid("ingredients");
        splrsList = new Suppliers();
        parse(xmlFilename);
        return splrsList;
    }
    
   public Ingredients parse(String xml)
    {
       ingredients = new Ingredients();
       splrsList = new Suppliers();
       // Parse the XML and create a DOM node tree
        Document parsedXML = domParser.parse(xml);
        
    	// walk the DOM tree
        processDocument(parsedXML);
        
        return ingredients;
    }
    
//    public Ingredients parseFromStream(String xmlContents)
//    {
//        System.out.println("in Ingredients.parseFromStream(xml file contents)");
//    	Document parsedXML = domParser.parseFromStream(xmlContents);
//    	processDocument(parsedXML);
//    	
//    	return ingredients;
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
            	if(node_name.equals("ingredients"))
            		processIngredients(n);
            	
            	if(node_name.equals("suppliers"))
            	    processSuppliers(n);
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

    private void processIngredients(Node n)
    {
         int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                if(node_name.equals("dairyTypes"))
                {
                    processDairyTypes(n);
                }
                if(node_name.equals("flavorings"))
                {
                    processFlavorings(n);
                }
                if(node_name.equals("emulsifiers"))
                {
                    processEmulsifiers(n);
                }
                if(node_name.equals("salts"))
                {
                    processSalts(n);
                }
                if(node_name.equals("stabilizers"))
                {
                    processStabilizers(n);
                }
                if(node_name.equals("sweeteners"))
                {
                    processSweeteners(n);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processIngredients(child);
        }
    }

    private void processSuppliers(Node n)
    {
         int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                if(node_name.equals("supplier"))
                {
                    Supplier splr = new Supplier();
                    processSupplier(n, splr);
                    splrsList.addSupplier(splr);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processSuppliers(child);
        }
    }
    
    private void processSupplier(Node n, Supplier splr)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        NamedNodeMap attributes = n.getAttributes();
       if(attributes != null && node_name.equals("supplier"))
       {
            int numAttrs = attributes.getLength();
            for(int i = 0; i < numAttrs; i++)
            {
                Attr attr = (Attr)attributes.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();
                
                if(attrName.equals("splrId"))
                    splr.setSplrId(attrValue);
                if(attrName.equals("splrTelephone"))
                    splr.setSplrTelephone(attrValue);
            }
       }
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
                    case "splrName":
                        splr.setSplrName(getText(n));
                        break;
                        
                    case "splrNotes":
                        splr.setSplrNotes(getText(n));
                        break;

                    case "splrContact":
                        splr.setContactName(getText(n));
                        break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processSupplier(child, splr);
        }
    }
    
    
    private void processDairyTypes(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processDairyTypes(child);
        }
    }
    
    private void processFlavorings(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processFlavorings(child);
        }
    }
    
    private void processEmulsifiers(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processEmulsifiers(child);
        }
    }
    
    private void processSalts(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processSalts(child);
        }
    }
    
    private void processStabilizers(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processStabilizers(child);
        }
    }
    
    private void processSweeteners(Node n)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                switch(node_name)
                {
              case "ingredient":
                  Ingredient ing = new Ingredient();
                  processIngredient(n, ing);
                  ingredients.addIngredient(ing);
                  break;
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processSweeteners(child);
        }
    }
    
    private void processIngredient(Node n, Ingredient ing)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        NamedNodeMap attributes = n.getAttributes();
       if(attributes != null && node_name.equals("ingredient"))
       {
	        int numAttrs = attributes.getLength();
	        for(int i = 0; i < numAttrs; i++)
	        {
	        	Attr attr = (Attr)attributes.item(i);
	        	String attrName = attr.getNodeName();
	        	String attrValue = attr.getNodeValue();
	        	
	        	if(attrName.equals("type"))
	        		ing.setType(attrValue);
	        	if(attrName.equals("id"))
	        		ing.setId(attrValue);
	        	if(attrName.equals("qty"))
	        	    ing.setQuantity(attrValue);
	        }
       }
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
            	switch(node_name)
            	{
            	case "links":
            		processLinks(n, ing);
            		break;
            	case "ingNotes":
            		processIngNotes(n, ing);
            		break;
            	case "ingName":
            	    processIngName(n, ing);
            	    break;
            	}
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processIngredient(child, ing);
        }
    }
    
    private void processIngName(Node n, Ingredient ing)
    {
        int type = n.getNodeType();
       node_name = n.getNodeName();

       switch (type)
       {
           case Node.ELEMENT_NODE:
           {
               if(node_name.equals("ingName"))
               {
                   ing.setName(getText(n));
               }
           }
           default:
           break;
       }
       for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
           processIngNotes(child, ing);
       }
   }
   
private void processIngNotes(Node n, Ingredient ing)
    {
        int type = n.getNodeType();
       node_name = n.getNodeName();

       switch (type)
       {
           case Node.ELEMENT_NODE:
           {
        	   if(node_name.equals("ingNotes"))
        	   {
        		   ing.setIngNotes(getText(n));
               }
           }
           default:
           break;
       }
       for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
           processIngNotes(child, ing);
       }
   }
   


    private void processLinks(Node n, Ingredient ing)
    {
         int type = n.getNodeType();
        node_name = n.getNodeName();

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                if(node_name.equals("link"))
                {
                	String[] link = new String[2];
                    processLink(n, link);
                    ing.addLink(link);
                }
            }
            default:
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processLinks(child, ing);
        }
    }
    
    private void processLink(Node n, String[] link)
    {
        int type = n.getNodeType();
        node_name = n.getNodeName();

        NamedNodeMap attributes = n.getAttributes();
       if(attributes != null)
       {
	        int numAttrs = attributes.getLength();
	        for(int i = 0; i < numAttrs; i++)
	        {
	        	Attr attr = (Attr)attributes.item(i);
	        	String attrName = attr.getNodeName();
	        	String attrValue = attr.getNodeValue();
	        	
	        	if(attrName.compareTo("type") == 0)
	        		link[0] = attrValue;
	        	if(attrName.compareTo("id") == 0)
	        		link[1] = attrValue;
	        }
       }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processLink(child, link);
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
