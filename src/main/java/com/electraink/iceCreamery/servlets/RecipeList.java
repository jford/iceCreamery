package com.electraink.iceCreamery.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.electraink.iceCreamery.utilities.Utilities;
import com.electraink.iceCreamery.utilities.XmlFactory;
import com.electraink.iceCreamery.testKitchen.datastore.*;

public class RecipeList extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private RecipeMgr recipeMgr;
	private DataMgr dataMgr = new DataMgr();
	private Recipes recipes;
	
	private Vector<String> filterList;
	private Vector<String> ingredientsList;
	
	
	// data from comments form
	private boolean cookMethodFilter;
	private boolean dairyFilter;
	private boolean domflavorNameFilter;
	private boolean flavoringsFilter;
	private boolean emulsifiersFilter;
	private boolean ingredientFilter;
	private boolean saltsFilter;
	private boolean scoreFilter;
	private boolean stabilizersFilter;
	private boolean sweetenersFilter;
	
	private HttpServletResponse response;
	
	private int filterCount;
	private int ingredientCount;
	
	private String cookMethod;
	private String domflavorName;
	private String score;
	private String equality;
	
	// return strings
	private String redirectTarget;
	private String responseMsg;
	private String redirectArgs;
	
	private Vector<String[]> htmlParams = new Vector<String[]>();
	
    public RecipeList() 
    {
        super();
    }
    
    public void init(HttpServletRequest request, HttpServletResponse response)
    {
    	this.response = response;
    	
		redirectArgs = "";
		redirectTarget = "/iceCreamery/forms/recipeSelect.jsp";
		responseMsg = "";
		
    	recipeMgr= new RecipeMgr();
		recipes = recipeMgr.getRecipes();
		
		filterList = new Vector<String>();
		
		// data from comments form
		cookMethodFilter = false;
		dairyFilter = false;
		domflavorNameFilter = false;
		flavoringsFilter = false;
		emulsifiersFilter = false;
		ingredientFilter = false;
		scoreFilter = false;
		stabilizersFilter = false;
		sweetenersFilter = false;
		
		cookMethod = "";
		equality = "";
		domflavorName = "";
		score = "";
		
		filterCount = 0;
		ingredientCount = 0;
		
		ingredientsList = new Vector<String>();

		getParams(request);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		init(request, response);
		
		recipeMgr.filterRecipeNames(filterList);
		
		String vMsgText = getVmsgText(); 
		if(!vMsgText.isEmpty())
		{
			String[] urlParam = { "vMsgText", vMsgText };
			htmlParams.add(urlParam);
		}
		doRedirect();
	}
	private String getVmsgText()
	{
		int idx = 0;

		String filterEquality = "";
		String ingredient;
		String ingredients = "";
		String text = "";
		
		if(scoreFilter)
		{
			String comparisonType = equality.compareTo("equals") == 0 ? "equal to " :
				                    equality.indexOf("less") != -1 ? "less than " : "greater than ";
			filterEquality = "with a score " + comparisonType + score + ", ";
		}
		
		Iterator<String> iIngredients;
		if(ingredientCount > 0)
		{
			ingredients = ingredientCount == 2 ? "both of " : "";
			ingredients += ingredientCount > 2 ? "all of " : "";
			ingredients += ingredientCount > 1 ? "these ingredients: " : "";
			
			iIngredients = ingredientsList.iterator();
			int ingredientCtr = 0;
			while(iIngredients.hasNext())
			{
				ingredientCtr++;
				ingredient = new String();
				ingredient = (String)iIngredients.next();
				ingredient = dataMgr.getAnonEnumName(ingredient).toLowerCase();
				if(ingredientCtr == ingredientCount)
					ingredient += ".";
				else if(ingredientCtr == 1 && ingredientCount == 2)
					ingredient += " and ";
				else
					ingredient += ", ";
				ingredients += ingredient;
			}
		}
		if(filterCount > 0)
		{
			text = "List of names has been filtered to include only ";
			text += domflavorNameFilter ? domflavorName.toLowerCase() + " ": ""; 
			text += "recipes ";
			text += scoreFilter ? filterEquality : "";
			if(scoreFilter && cookMethodFilter)
			{
				text += " and ";
			}
			text += cookMethodFilter ? "cooked by " + dataMgr.getCookMethodName(cookMethod).toLowerCase() + ", " : "";
			if(ingredientCount > 0)
			{
//				text += ingredientFilter ? "containing " + ingredients : "";
//				iIngredients = ingredientsList.iterator();
//				while(iIngredients.hasNext())
//				{
//					ingredient = (String)iIngredients.next();
//					text += ingredient;
//					if(ingredientCount > 3)
//						text += ", ";
//				}
				
				text += "containing " + ingredients;
			}
			if(text.endsWith(";") || text.endsWith(", "))
			{
				text = text.substring(0, text.length() - 1) + ".";
			}
			
			if(text.endsWith(",."))
			{
				text = text.substring(0, text.length() - 2) + ".";
			}
			
			if(ingredientCount > 1)
			{
				idx = text.lastIndexOf(", ");
				if(idx != -1)
				{
					String strFront = text.substring(0, idx);
					String strBack = text.substring(idx + 2);
					text = strFront + " and " + strBack;
				}
			}
				
		}
		return text;
	}
	private void getParams(HttpServletRequest request)
	{
		String key = "";
		String value = "";
		
        // Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		if(key.compareTo("cookmethod") == 0)
    		{
    			if(!value.isEmpty())
    			{
	    			cookMethod = value; // enum name (NONE, SOUS_VIDE, or COOK_TOP)
	    			filterList.add("Cook Method: " + cookMethod);
	    			cookMethodFilter = true;
	    			filterCount++;
    			}
    		}
    		
    		if(key.compareTo("dairy") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("dairy").length; i++)
    			{
    				String dairyItem = ((request.getParameterValues("dairy"))[i]);
    				filterList.add("Dairy: " + dairyItem);
    				ingredientsList.add(dairyItem);
        			ingredientCount++;
    			}
    			dairyFilter = true;
    			ingredientFilter = true;
    			filterCount++;
    		}
    		
    		if(key.compareTo("domFlavor") == 0)
    		{
    			if(!value.isEmpty())
				{
	    			domflavorName = value;
	    			filterList.add("Dom Flavor: " + domflavorName);
	    			domflavorNameFilter = true;
	    			filterCount++;
				}
    		}
    		
    		if(key.compareTo("emulsifiers") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("emulsifiers").length; i++)
    			{
    				String emulsifierItem = (request.getParameterValues("emulsifiers"))[i];
    				filterList.add("Emul: " + emulsifierItem);
    				ingredientsList.add("Emul:" + emulsifierItem);
        			ingredientFilter = true;
        			ingredientCount++;
    			}
    			emulsifiersFilter = true;
    			filterCount++;
    		}
    		
    		if(key.compareTo("flavors") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("flavors").length; i++)
    			{
    				String flavorItem = (request.getParameterValues("flavors"))[i];
    				filterList.addElement("Flavor item: " + flavorItem);
    				ingredientsList.addElement(flavorItem);
        			ingredientCount++;
    			}
    			flavoringsFilter = true;
    			ingredientFilter = true;
    			filterCount++;
    		}
    		
    		if(key.compareTo("salts") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("salts").length; i++)
    			{
    				String saltItem = (request.getParameterValues("salts"))[i]; 
    				filterList.addElement("Salt: " + saltItem);
    				ingredientsList.add(saltItem);
    				ingredientCount++;
    			}
    			ingredientFilter = true;
    			saltsFilter = true;
    			filterCount++;
    		}
    		
    		if(key.compareTo("score") == 0)
    		{
    			if(!value.isEmpty())
    			{
	    			score = value;
	    			scoreFilter = true;
	    			filterCount++;
    			}
    		}
    		
    		if(key.compareTo("score_equality") == 0)
    		{
    			if(value.compareTo("equals") == 0)
    				equality = value;
    			else if(value.compareTo("more") == 0 || 
    					value.compareTo("less") == 0)
    						equality = value + " than";
    		}
    			
    		if(key.compareTo("stabilizers") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("stabilizers").length; i++)
    			{
    				String stabilizerItem = (request.getParameterValues("stabilizers"))[i]; 
    				filterList.addElement("Stab: " + stabilizerItem);
    				ingredientsList.add(stabilizerItem);
    				ingredientCount++;
    			}
    			ingredientFilter = true;
    			stabilizersFilter = true;
    			filterCount++;
    		}
    		
    		if(key.compareTo("sweeteners") == 0)
    		{
    			for(int i = 0; i < request.getParameterValues("sweeteners").length; i++)
    			{
    				String sweetenerItem = (request.getParameterValues("sweeteners"))[i]; 
    				filterList.addElement("Sweet: " + sweetenerItem);
    				ingredientsList.add(sweetenerItem);
    				ingredientCount++;
    			}
    			ingredientFilter = true;
    			sweetenersFilter = true;
    			filterCount++;
    		}
        }
        if(cookMethodFilter)
        {
        	filterList.add("Cook Method: " + cookMethod);
        }
        if(scoreFilter)
        {
			filterList.add("Score: " + equality + " " + score);
        }
	}
	
	private void encodeHtmlParams()
	{
		int count = 0;
		String firstParamMrkr = "?";
		String paramMrkr = "&";
		
		Iterator<String[]> iParams = htmlParams.iterator();
		if(iParams.hasNext())
		{
			for(count = 0; count < htmlParams.size(); count++)
			{
				String[] urlParam = (String[])iParams.next();
				String paramKey = Utilities.encodeHtmlParam(urlParam[0]);
				String paramValue = Utilities.encodeHtmlParam(urlParam[1]);
				redirectArgs += count == 0 ? firstParamMrkr : paramMrkr;
				redirectArgs += paramKey + "=" + paramValue;
			}
		}
		
		// reset for next servlet call
		htmlParams = new Vector<String[]>();
	}
	
	private void doRedirect()
	{
		encodeHtmlParams();
		String target = redirectTarget + responseMsg + redirectArgs;
		String redirect = 
		"<html>\n"
	            + "    <body onload=\"document.location = \'" + target + "\'\" />\n"
	            + "</html>";
        response.setContentLength(redirect.length());
        try
        {
	        ServletOutputStream op = response.getOutputStream();
	        op.write(redirect.getBytes(), 0, redirect.length());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
}

