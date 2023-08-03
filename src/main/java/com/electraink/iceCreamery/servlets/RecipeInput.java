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

import com.electraink.iceCreamery.comments.Comment;
import com.electraink.iceCreamery.comments.CommentsMgr;
import com.electraink.iceCreamery.testKitchen.datastore.dairy.*;
import com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*;
import com.electraink.iceCreamery.testKitchen.datastore.flavors.*;
import com.electraink.iceCreamery.testKitchen.datastore.salts.*;
import com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*;
import com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*;
import com.electraink.iceCreamery.utilities.EmailUtility;
import com.electraink.iceCreamery.utilities.Utilities;
import com.electraink.iceCreamery.utilities.XmlFactory;
import com.electraink.iceCreamery.testKitchen.datastore.*;

public class RecipeInput extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private DataMgr dataMgr;
	private RecipeMgr recipeMgr;
	private IngredientsMgr ingsMgr;
	private Ingredients ings;
	
	// data from comments form
	private boolean cloneRecipe;
	private boolean removeImage;
	
	private String caption;
	private String cloneRecipeName;
	private String comments;
	private String cookmethod;
	private String cooktemp;
	private String cooktime;
	private String domFlavor;
	private String imageEditMode;
	private String imageFilename;
	private String instructions;
	private String mode;
	private String rcpId;
	private String recipeByName;
	private String recipeName;
	private String recipeSubmitBtn; // active in add mode, and in edit mode when submitting updates
	private String score;
	private String submitEditsBtn; // active in edit mode when retrieving recipe for edits
	
	private String[] dairy = null;
	private String[] emulsifiers = null;
	private String[] flavors = null;
	private String[] salts = null;
	private String[] stabilizers = null;
	private String[] sweeteners = null;
	private String[] quantityEntry = null;

	private Vector<String> ingredients = null;
	private Vector<String[]> quantities = null;
	
	// return URL components
	private String redirectTarget;
	private String responseMsg;
	private String redirectArgs;
	
	private Vector<String[]> htmlParams = new Vector<String[]>();
	
    public RecipeInput() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init()
    {
    	dataMgr = new DataMgr();
    	recipeMgr = new RecipeMgr();
    	ingsMgr = new IngredientsMgr();
    	
		dairy = new String[dataMgr.getNumIngs("dairyTypes")];
		emulsifiers = new String[dataMgr.getNumIngs("emulsifiers")];
		flavors = new String[dataMgr.getNumIngs("flavors")];
		salts = new String[dataMgr.getNumIngs("salts")];
		stabilizers = new String[dataMgr.getNumIngs("stabilizers")];
		sweeteners = new String[dataMgr.getNumIngs("sweeteners")];
		
		ingredients = new Vector<String>();
		quantities = new Vector<String[]>();

		cloneRecipe = false;
		removeImage = false;
		
		caption = "";
		cloneRecipeName = "";
		comments = "";
		cookmethod = "";
		cooktemp = "";
		cooktime = "";
		domFlavor = "";
		imageEditMode = "";
		recipeByName = "";
		imageFilename = "";
		instructions = "";
		mode = "";
		rcpId = "";
		recipeByName = "";
		recipeName = "";
		recipeSubmitBtn = "";
		score = "";
		submitEditsBtn = "";
    }

	protected void doGet(HttpServletRequest request, 
			             HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}
	
	private void cloneRecipe()
	{
		Recipe recipe = recipeMgr.getRecipe(recipeByName);
		recipe.setRecipeName(cloneRecipeName);
		recipe.setDateOfEntry(DataMgr.getDateStr());
		recipeMgr.addRecipe(recipe);
		
		String[] modeParam = {"mode", "edit"};
		String[] recipeNameParam = {"recipeName", recipe.getRecipeName()};
		htmlParams.add(modeParam);
		htmlParams.add(recipeNameParam);
	}
	
	protected void doPost(HttpServletRequest request, 
			              HttpServletResponse response) throws ServletException, IOException 
	{
		// init() called from servlet constructor by super(); but servlet may not get 
		// constructed from scratch at each call, so need to reinitialize for every 
		// visit...
		init();
		
		redirectArgs = "";
		responseMsg = "";
		redirectTarget = "/iceCreamery/forms/recipe_input_form.jsp";
		
		getParams(request);
		if(validateInput())
		{
			switch(mode)
			{
			case "add":
				{
					addRecipe();
				}
				break;
			case "edit":
				{
					if(!recipeByName.equals(DataMgr.defaultSelectId))
						getRecipeForEdit();
					else if(cloneRecipe)
						cloneRecipe();
					else if(!recipeSubmitBtn.isEmpty())
						editRecipe();
				}
				break;
			case "delete":
				{
					deleteRecipe(rcpId);
				}
				break;
			default:
				{
					
				}
			}
		}
		encodeHtmlParams();
		doRedirect(redirectTarget, responseMsg, redirectArgs, response);
	}
	private void addRecipe()
	{
        Recipe recipe = new Recipe();
        boolean idUnique = false;
        while(!idUnique)
        {
            rcpId = Utilities.getId();
            idUnique = dataMgr.isIdUnique(rcpId);
        }
        recipe.setId(rcpId); 
        recipe.setRecipeName(recipeName);
        recipe.setDateOfEntry(DataMgr.getDateStr());
        recipeMgr.addRecipe(recipe);
        
        String[] modeParam = {"mode", "edit"};
        String[] rcpIdParam = { "rcpId", rcpId };
        htmlParams.add(modeParam);
        htmlParams.add(rcpIdParam);
		
	}
	
	private void editRecipe()
	{
        Recipe rcp = new Recipe();
        rcp = recipeMgr.getRecipe(rcpId);
        if(recipeName.isEmpty())
            recipeName = rcp.getRecipeName();

        recipeMgr.deleteRecipe(rcpId);
        
        if(ingredients.size() > 0)
            getIngs(rcp);
        
        rcp.setCaption(caption);
        rcp.setComment(comments);
        rcp.setCookMethod(cookmethod);
        rcp.setCookTemp(cooktemp);
        rcp.setCookTime(cooktime);
        rcp.setImageFilename(imageFilename);
        rcp.setInstructions(instructions);
        rcp.setRecipeName(recipeName);
        if(score.length() > 0)
        {
            int scoreInt = 0;
            try
            {
                int mrkr = score.indexOf(".");
                if(mrkr == -1)
                    mrkr = score.indexOf(" ");
                if(mrkr != -1)
                    score = score.substring(0, mrkr);
                if(score.length() == 0)
                    score = "0";
                scoreInt = Integer.parseInt(score);
            }
            catch(Exception e)
            {
                // e.printStackTrace();
                // java.lan.NumberFormatException; score == "Unrated",
                // which trips up Integer.parseInt(score);
                // no harm no foul so ignore the excception 
            }
            rcp.setScore(scoreInt);
        }
        
        
        recipeMgr.addRecipe(rcp);
        
        String[] modeParam = {"mode", "edit"};      
        String[] rcpIdParam = { "rcpId", rcpId };
        htmlParams.add(modeParam);
        htmlParams.add(rcpIdParam);
	}
	
	private void getIngs(Recipe rcp)
	{
	    /*
	     * in getParams(),
	     *     ingredients Vector<String> contains
	     *         String ing = ingId
	     * and
	     *     quantities Vector<String{}> contains
	     *         String[] qty = { key, value } where key is ingId  
	     *         and value is quantity string   
	     */
	    Iterator<String> iIngs = ingredients.iterator();
	    Iterator<String[]> iQs = quantities.iterator();
	    while(iIngs.hasNext())
	    {
	        String ingParam = iIngs.next();
	        while(iQs.hasNext())
	        {
	            String[] qtyParam = iQs.next();
	            if(qtyParam[0].equals(ingParam))
	            {
	                /*
 	                 * for rcp.addIngredient(String[] ing),
	                 *     String[] ing = { ingId, qtyVal };
	                 */
                    String[] ing = { ingParam, qtyParam[1] };
	                rcp.addIngredient(ing);
	            };
	        }
	    }
	}

	private void getRecipeInputValues()
	{
	    Vector<String> rcpNames = recipeMgr.getRecipeNames();
	    
	    // recipeByName menu selected option
	    int rcpNmSlctdIdx = 1; // idx 0 == DataMgr.defaultSelectOption
	    
	    // recipeByName val is rcp's id, not name
	    String rcpNm = dataMgr.getNameForId("rcp", recipeByName);
	    Iterator<String> iNms = rcpNames.iterator();
	    while(iNms.hasNext())
	    {
	        String name = iNms.next();
	        if(name.equals(rcpNm))
	            break;
	        rcpNmSlctdIdx++;
	    }
	    String[] rcpNmSelectedIndexParam = { "rcpNmSelectedIndex", Integer.toString(rcpNmSlctdIdx) };
	    htmlParams.add(rcpNmSelectedIndexParam);
	    
	    // cook method menu selected option
	    int ckMthdSlctdIdx = 1; // idx 0 == DataMgr.defaultSelectOption 
        Vector<String[]> cookMethods = dataMgr.getNamesIds("ckMthd");
        Iterator<String[]> iCkMthds = cookMethods.iterator();
        while(iCkMthds.hasNext())
        {
            String[] ckMthd = iCkMthds.next();
            if(ckMthd[0].equals(cookmethod))
                break;
            ckMthdSlctdIdx++;
        }
        String[] cookMthdSelectedIndexParam = { "cookMethodSelectedIndex", Integer.toString(ckMthdSlctdIdx) };
        htmlParams.add(cookMthdSelectedIndexParam);
        
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
	
	private void deleteRecipe(String rcpId)
	{
		recipeMgr.deleteRecipe(rcpId);
		
		String[] modeParam = {"mode", "delete"};
		String[] rcpIdParam = { "rcpId", DataMgr.defaultSelectId };
		htmlParams.add(modeParam);
		htmlParams.add(rcpIdParam);
	}
	
	private void getRecipeForEdit()
	{
		Recipes recipes = recipeMgr.getRecipes();
		Recipe recipe = null;
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			recipe = (Recipe)iRecipes.next();
			if(recipe.getId().compareTo(rcpId) == 0)
				break;
		}
		if(removeImage)
			recipe.clearImage();
		// recipeByName param val is id, not name; need to translate
		String[] rcpIdParam = {"rcpId", rcpId};
		String[] mode = {"mode", "edit" };
		htmlParams.add(rcpIdParam);
		htmlParams.add(mode);
		
		getRecipeInputValues();
	}
	
	private boolean validateInput()
	{
		RecipeMgr recipeMgr = new RecipeMgr();
		String msg = "";
		
		boolean inputValid = true;
		boolean nameValid = true;
		boolean qtyValid = true;

		// first, check for a recipe name
		if(recipeName.isEmpty() && 
		   (recipeByName.isEmpty() || 
		    recipeByName.equals(dataMgr.defaultSelectId)))
		{
			nameValid = false;
			msg = "- Recipe name cannot be blank.";
		}
		else if(mode.compareTo("add") == 0 && !recipeMgr.nameUnique(recipeName)) 
		{
			nameValid =  false;
			msg = "- A recipe named \"" + recipeName + "\" already exists.";
		}
		else if(recipeByName == DataMgr.defaultSelectOption)
		{
			nameValid = false;
			msg = "Select a recipe from the list.";
		}
		
		if(rcpId.isEmpty())
		    // recipeByName is option value of menu selection,
		    // not the name 
		    rcpId = recipeByName;

		// determine validity of input
		if(nameValid && qtyValid)
			inputValid = true;
		else
			inputValid = false;
		
		if(!inputValid)
		{
			String[] vMsgParam = { "vMsgText", msg};
			htmlParams.add(vMsgParam);
			if(recipeByName.equals(DataMgr.defaultSelectOption))
			{
				String[] mode = {"mode", "edit" };
				htmlParams.add(mode);
			}
		}
		return inputValid;
	}
	
	private void getParams(HttpServletRequest request)
	{
		String key = "";
		String value = "";
		
		if(DataMgr.debug)
			Utilities.viewServletParams(request);
		
        // Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		if(key.compareTo("caption") == 0)
    		{
    			caption = value;
    		}

    		if(key.compareTo("cloneRecipeBtn") == 0)
    		{
    			cloneRecipe = true;
    		}
    		
    		if(key.compareTo("cloneRecipeName") == 0)
    		{
    			cloneRecipeName = value;
    		}
    		
    		if(key.compareTo("comments") == 0)
    		{
    			comments = value;
    		}
    		
    		if(key.compareTo("cooktemp") == 0)
    		{
    			cooktemp = value;
    		}
    		
    		if(key.compareTo("cooktime") == 0)
    		{
    			cooktime = value;
    		}
    		
    		if(key.compareTo("cookmethod") == 0)
    		{
    			cookmethod = value;
    		}
    		
    		if(key.compareTo("dairyType") == 0)
    		{
                ingredients.add(value);
    		}
    			
    		if(key.compareTo("domFlavor") == 0)
    		{
    			domFlavor = value;
    		}

    		if(key.compareTo("emulsifier") == 0)
    		{
    		    ingredients.add(value);
    		}
    		
    		if(key.compareTo("flavoring") == 0)
    		{
    		    ingredients.add(value);
    		}
    		
    		if(key.compareTo("imageEditMode") == 0)
    		{
    			removeImage = value.equals("removeImage") ? true : false;
    		}
    		if(key.compareTo("imageFilename") == 0)
    		{
    			imageFilename = value;
    		}
    		
    		if(key.compareTo("instructions") == 0)
    		{
    			instructions = value;
    		}
    		
    		if(key.compareTo("mode") == 0)
    		{
    			mode = value;
    		}

    		if(key.endsWith("TextField"))
    		{
    		    if(key.startsWith("dairyType") ||
    		       key.startsWith("emulsifier") ||
                   key.startsWith("flavoring") ||
                   key.startsWith("salt") ||
                   key.startsWith("stabilizer") ||
                   key.startsWith("sweetener"))
    		    {
    		        key = key.split("#")[1];
    		        key = key.split("T")[0];
                    String[] qty = { key, value };
                    quantities.add(qty);
    		    }
    		}
    		
    		if (key.equals("rcpId"))
    		    rcpId = value;
    		
    		if(key.equals("recipeSubmitBtn"))
    		    recipeSubmitBtn = value;

    		if(key.compareTo("recipeByNameSlct") == 0)
    		{
    			recipeByName = value;
    		}
    		
    		if(key.compareTo("recipeName") == 0)
    		{
    			recipeName = value;
    		}
    		
    		if(key.compareTo("salt") == 0)
    		{
                ingredients.add(value);
    		}
    		
    		if(key.compareTo("score") == 0)
    		{
    			if(!score.isEmpty())
    			{
    				if(Integer.parseInt(value) > 10)
    					score =  "10";
    				else if(Integer.parseInt(value) < 1)
    					score = "1";
    				else
    					score = value;
    			}
    		}
    		
    		if(key.compareTo("stabilizer") == 0)
    		{
                ingredients.add(value);
    		}
    		
    		if(key.equals("submitEditsBtn"))
    		    submitEditsBtn = value;
    		
    		if(key.compareTo("sweetener") == 0)
    		{
                ingredients.add(value);
    		}
        }
	}
	
	private void doRedirect(String redirectTarget, String responseMsg, String redirectArgs, HttpServletResponse response)
	{
		
		String target = redirectTarget + responseMsg + redirectArgs;
		if(DataMgr.debug)
			Utilities.showUrl(target);
		
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
