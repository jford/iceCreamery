package com.electraink.iceCreamery.testKitchen.datastore;

import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;
import com.electraink.iceCreamery.utilities.Utilities;

import java.util.*;

public class Recipe
{
	private int score = 0;
	
	private String cookTemp = "";
	private String cookTime = "";
	private String comments = "";
	private String dateOfEntry = "";
	private String domFlavor = "";
	private String id = "";
	private String instructions = "";
	private String recipeName = "";
	private String imageFilename = "";
	private String imageCaption = "";
	
//	private DataMgr.CookMethod cookMethod = DataMgr.CookMethod.NONE;
	private String cookMethod = "NONE";
	
	private Vector<String []> ingredients = new Vector<String[]>(); // String[] ing = { ingId, qtyVal)
	
	public static String zeroScoreStr = "Unrated";
	
	public Recipe()
	{
	}
	
	public void addIngredient(String[] ing)
	{
	    ingredients.add(ing);
	}
	public Vector<String[]> getIngredients()
	{
	    return ingredients;
	}
	
	public void setDateOfEntry(String dateOfEntry)
	{
		this.dateOfEntry = dateOfEntry;
	}
	
	public String getDateOfEntry()
	{
		return dateOfEntry;
	}
	
	public void setCookMethod(/*DataMgr.CookMethod*/ String cookMethod)
	{
		this.cookMethod = cookMethod;
	}
	public /*DataMgr.CookMethod*/ String getCookMethod()
	{
		return cookMethod;
	}
//	public String getCookMethodStr()
//	{
//		return cookMethod.name(); // .getCookMethodName(cookMethod);
//	}
	
	public void setCookTime(String cookTime)
	{
		this.cookTime = cookTime;
	}
	public String getCookTime()
	{
		String cookTime = this.cookTime;
		if(cookTime.length() == 0)
		{
			// if a parameter value is an empty string, showRecipe.jsp ignores the 
			// parameter; but if there is a cook temp or cook method, an empty cook time
			// is likely an oversight, not intentional
			if(cookTemp.length() > 0 || cookMethod.equals("NONE"))
				cookTime = "an unspecified amount of time";
		}
		return cookTime;
	}
	
	public boolean hasCookTime()
	{
		boolean hasCookTime = cookTime.indexOf("unspecified") == -1 ? true : false;
		boolean hasCookTemp = cookTemp.indexOf("unspecified") == -1 ? true : false;
		
		return hasCookTime;
	}
	
	
	public void setImageFilename(String imageFilename)
	{
		// imageFilename is the name of an image file that is associated with this recipe;
		// the file should exist in the /iceCreamery/images directory
		this.imageFilename = imageFilename; 
	}
	public String getImageFilename()
	{
		return imageFilename;
	}
	
	public void clearImage()
	{
		imageFilename = "";
	}
	
	public void setCaption(String imageCaption)
	{
		this.imageCaption = imageCaption;
	}
	public String getCaption()
	{
		return imageCaption;
	}
	
	public void setRecipeName(String recipeName)
	{
		this.recipeName = recipeName;
	}
	public String getRecipeName()
	{
		return recipeName;
	}
	
	public void setId(String id)
	{
	    this.id = id;
	}
	public String getId()
	{
	    return id;
	}
	public void setInstructions(String instructions)
	{
		this.instructions = instructions;
	}
	public String getInstructions()
	{
		/* 
		 * Instructions text is stored in the xml as a CDATA structure.
		 *  
		 * It looks like web services code that produces CDATA structures is
		 * adding extraneous lines to the text.
		 * 
		 * If it's not CDATA code, it's still getting generated, and it still 
		 * needs to be removed, here and in the getComment() method
		 */
		String tripleLine = "\n\n\n";
		String dblLine = "\n\n";
		while(instructions.indexOf(tripleLine) != -1)
		{
			instructions = Utilities.replaceChars(instructions, tripleLine, dblLine, "all");
		}
		return instructions;
	}
	public boolean hasInstructions()
	{
		boolean hasInstructions = instructions.length() > 0 ? true : false;
		return hasInstructions;
	}
	
	public String prepNewText(String text)
	{
		return Utilities.convertTextToHtml(text);
	}
	
	public boolean hasIngredients()
	{
		return ingredients != null && ingredients.size() > 0 ? true : false;
	}
	
	private String addMarkupForText(String text)
	{
		String cdataStartTag = "<![CDATA[";
		String cdataEndTag = "]]>";
		String paraStartTag = "<p>";
		String paraEndTag = "</p>";
		
		if(!text.contains(cdataStartTag))
		{
			text = cdataStartTag + text + cdataEndTag;
			while(text.indexOf("\n\n") != -1)
			{
				text = text.replaceAll("\n\n",  "\n");
			}
			text.replaceAll("\n", paraStartTag + "\n\n" + paraEndTag);
		}
		return text;
	}
	
	public void setCookTemp(String cookTemp)
	{
		this.cookTemp = cookTemp;
	}
	public String getCookTemp()
	{
		String cookTemp = this.cookTemp;
		if(cookTemp.length() == 0)
		{
			// if a parameter value is an empty string, showRecipe.jsp ignores the 
			// parameter; but if there is a cook time or cook method, an empty cook temp
			// is likely an oversight, not intentional
			if(cookTime.length() > 0 || cookMethod.equals("NONE"))
				cookTemp = "an unspecified temperature";
		}
		return cookTemp;
	}
	
	public boolean hasCookTemp()
	{
		boolean hasCookTemp = cookTemp.indexOf("unspecified") == -1 ? true : false;
		return hasCookTemp;
	}
	
	public void setComment(String comments)
	{
		this.comments = comments;
	}
	public String getComments()
	{
		/* 
		 * Comment text is stored in the xml as a CDATA structure.
		 *  
		 * It looks like web services code that produces CDATA structures is
		 * adding extraneous lines to the text.
		 * 
		 * If it's not CDATA code, it's still getting generated, and it still 
		 * needs to be removed, here and in the getInstructions() method
		 */
		String tripleLine = "\n\n\n";
		String dblLine = "\n\n";
		while(comments.indexOf(tripleLine) != -1)
		{
			comments = Utilities.replaceChars(comments, tripleLine, dblLine, "all");
		}
		return comments;
	}
	public boolean hasComments()
	{
		boolean hasComments = comments.length() > 0 ? true : false;
		return hasComments;
	}

	public void setScore(int score)
	{
		this.score = score;
	}
	public int getScoreInt()
	{
		return score;
	}
	public String getScoreStr()
	{
		String score = this.score > 0 ? Integer.toString(this.score) : zeroScoreStr; 
		return score;
	}
	
	public boolean hasScore()
	{
		boolean hasScore = score > 0 ? true : false;
		return hasScore;
	}
	
	public String getInputFormValue(String param)
	{
		
		// recipe input form has asked recipe manager for value of "param"
		String value = "";
		
		if(param.compareTo("recipeName") == 0)
		    value = getRecipeName();
		if(param.compareTo("domFlavor") == 0)
			value = getDomFlavor();
		else if(param.compareTo("comments") == 0)
			value = getComments();
		else if(param.compareTo("instructions") == 0)
			value = getInstructions();
		else if(param.compareTo("cooktime") == 0)
			value = getCookTime();
		else if(param.compareTo("cooktemp") == 0)
			value = getCookTemp();
		else if(param.compareTo("score") == 0)
			value = getScoreStr();
		
		return value;
	}
	
	public String getInputFormSelectedMenuItem(String param, String menuItem)
	{
		// param is name of select element in input form; menu item is the 
		// select option item being evaulauted; if matched, need to add "select" to 
		// the input form
		
		// does input param match cook method specified in recipe?
		return cookMethod.equals(menuItem) ? "selected" : "";
	}
	

	public String getDomFlavor()
	{
		return domFlavor;
	}
	public void setDomFlavor(String domFlavor)
	{
	    this.domFlavor = domFlavor;
	}
	public boolean hasDomFlavorName()
	{
		return domFlavor.length() > 0 ? true : false;
	}

	public boolean hasDairyType(String type)
	{
		boolean matchFound = false;
		return matchFound;
	}
	
	public boolean hasEmulsifier(String type)
	{
		boolean matchFound = false;
		return matchFound;
	}
	
	public boolean hasFlavoring(String type)
	{
		boolean matchFound = false;
		return matchFound;
	}

	public boolean hasStabilizer(String type)
	{
		boolean matchFound = false;
		return matchFound;
	}
	
	public boolean hasSweetener(String type)
	{
		boolean matchFound = false;
		return matchFound;
	}
}
