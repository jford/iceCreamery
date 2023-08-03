package com.electraink.iceCreamery.servlets;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import com.electraink.iceCreamery.testKitchen.datastore.suppliers.*;
import com.electraink.iceCreamery.utilities.XmlFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import com.electraink.iceCreamery.utilities.Utilities;

public class IngsInput extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private String editIngId;
	private String editIngName;
	private String editIngType;
	private String ingCategory;
    private String ingId;
	private String ingName;
	private String ingNotes;
	private String ingSlct;
	private String ingType;
    private String mode;
    private String newIngName;
    private String newSplrCtct;
    private String newSplrName;
    private String newSplrTel;
    private String splrNotes;
    private String vMsgText;
    
    private boolean addNewSplr;

	// return URL components
	private String redirectTarget;
	private String redirectArgs;
	
	private Vector<String[]> htmlParams = new Vector<String[]>();
	private Ingredient ing;
	
	private DataMgr dataMgr;
 	
    public IngsInput() 
    {
        super();

    }
    
    public void init()
    {
        dataMgr = new DataMgr();
        
        addNewSplr = false;
        editIngId = "";
        editIngName = "";
        editIngType = "";
        ingCategory = "";
        ingId = "";
        ingName = "";
        ingNotes = "";
        ingSlct = "";
        ingType = "";
        mode = "";
        newIngName = "";
        newSplrCtct = "";
        newSplrName = "";
        newSplrTel = "";
        redirectArgs = "";
        splrNotes = "";
        vMsgText = "";
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		init();
		getParams(request);
		
		switch(mode) 
		{
		    case "add":
		        addIng();
		        break;
		        
		    case "edit":
		        /*
		         * if ingSlct (select menu) equals the default "Select from menu"  
		         * (option value 0000000000000000), then the request was called by 
		         * the form submit button and should be handled in editIng();
		         * ohterwise, just go to getIng() to get url params so the .jsp can 
		         * locate the ingredient Id'd by ingSlct
		         */
		        if(addNewSplr)
		            addSplr();
		        else if(ingSlct.equals("0000000000000000"))
		            editIng();
		        else
		            getIng();
		        break;
		        
		    case "delete":
		        deleteIng();
		        break;
		        
	        default:
	            vMsgText = "Edit operation failed; nothing to do.";
	            String[] vMsg = { "vMsgText", vMsgText };
	            htmlParams.add(vMsg);
		}
        redirectTarget = "/iceCreamery/editors/ingEditor.jsp";
		encodeHtmlParams();
		doRedirect(redirectTarget, redirectArgs, response);
	}
	
	protected void getParams(HttpServletRequest request)
	{
		if(DataMgr.debug)
			Utilities.viewServletParams(request);
		String key = "";
		String value = "";

        // Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		
    		switch(key)
    		{
    		    case "addNewSplrChkbx":
    		        addNewSplr = true;
    		        break;
    		        
    		    case "editIngId":
    		        editIngId = value;
    		        break;
    		        
                case "editIngName":
                    editIngName = value;
                    break;
                    
                case "editIngType":
                    editIngType = value;
                    break;
                    
                case "ingCategory":
                    ingCategory = value;
                    break;
                    
                case "ingId":
        			ingId = value;
        			break;
    
        		case "ingName":
        		    ingName = value;
        			break;
        			
        		case "ingNotes":
        		    ingNotes = value;
        		    break;
        			
        		case "ingSlct":
        		    ingSlct = value;
        		    break;
        		
        		case "ingType":
        			ingType = value;
        			String[][] map = dataMgr.getIngNameMap();
        			for(int i = 0; i < map.length; i++)
        			{
        			    if(map[i][1].equals(ingType))
        			    {
        			        ingCategory = map[i][0];
        			        break;
        			    }
        			}
        			break;
        		
        		case "mode":
        			mode = value;
        			break;

        		case "newSplrCtct":
        		    newSplrCtct = value;
        		    break;
        		    
        		case "newSplrName":
        		    newSplrName = value;
        		    break;
        		    
        		case "newSplrTel":
        		    newSplrTel = value;
        		    break;

        		case "newIngName":
        		    newIngName = value;
        		    break;
        		    
        		case "splrNotes":
        		    splrNotes = value;
        		    break;
    		}
        }
	}
	
	protected void addSplr()
	{
	    Supplier splr = new Supplier();
	    splr.setSplrName(newSplrName);
	    splr.setContactName(newSplrCtct);
	    splr.setSplrTelephone(newSplrTel);
	    splr.setSplrId(Utilities.getId());
	    splr.setSplrNotes(splrNotes);

	    dataMgr.addSupplier(splr);
	    
	    String[] addNewSplrParam = { "addNewSplr", "true" };
	    String[] modeParam = {"mode", "edit"};
	    String[] ingCategoryParam = { "ingCategory", ingCategory };
	    String[] ingTypeParam = { "ingType", ingType };
	    String[] ingNameParam = { "ingName", ingName };
	    String[] ingIdParam = { "ingId", ingId };
	    
	    htmlParams.add(addNewSplrParam);
        htmlParams.add(modeParam);
        htmlParams.add(ingCategoryParam);
        htmlParams.add(ingTypeParam);
        htmlParams.add(ingNameParam);
        htmlParams.add(ingIdParam);
	}
	
	protected void addIng()
	{
		ing = new Ingredient();
		ing.setName(newIngName);
		ing.setType(ingType);
		ing.setIngNotes(ingNotes);
		ing.setId(Utilities.getId());
		
		// DataMgr's addIngredient() calls XmlFactory's addIngredient() which writes 
		// a new ingredients.xml file with the new ingredient in it
		dataMgr.addIngredient(ing);
		XmlFactory xmlFactory = new XmlFactory("ingredients");

        String[] modeParam = { "mode", "edit" };
        String[] ingNameParam = { "ingName", ing.getName() };
        String[] ingTypeParam = { "ingType", ing.getType() };
        String[] ingIdParam = { "ingId", ing.getId() };

        String[] ingCategoryParam = { "ingCategory", "" };
        String[][] ingNameMap = dataMgr.getIngNameMap();
		for(int i = 0; i < ingNameMap.length; i++)
		{
		    if(ingNameMap[i][1].equals(ingType))
		        ingCategoryParam[1] = ingNameMap[i][0];
		}
        htmlParams.add(modeParam);
        htmlParams.add(ingNameParam);
        htmlParams.add(ingTypeParam);
        htmlParams.add(ingIdParam);
		htmlParams.add(ingCategoryParam);
	}
	
	protected void getIng()
	{
	    /*
	     * all that is needed is the setting of url params to tell 
	     * the ingEditor.jsp what data to display 
	     */
	    String ingCategory = "";
	    String[][] ingNameMap = dataMgr.getIngNameMap();
	    int count = 0;
	    for(count = 0; count < ingNameMap.length; count++)
	    {
	        if(ingNameMap[count][1].equals(ingType))
	        {
	            ingCategory = ingNameMap[count][0];
	            break;
	        }
	        
	    }
	    
	    String[] modeParam = { "mode", mode };
	    String[] ingNameParam = { "ingName", dataMgr.getNameForId("ing", ingSlct) };
	    String[] ingIdParam = { "ingId", ingSlct };
	    String[] ingTypeParam = { "ingType", ingType };
	    String[] ingCategoryParam = { "ingCategory", ingCategory };
	    
	    htmlParams.add(modeParam);
	    htmlParams.add(ingCategoryParam);
        htmlParams.add(ingTypeParam);
	    htmlParams.add(ingNameParam);
	    htmlParams.add(ingIdParam);
	}
	
	protected void editIng()
	{
	    /*
	     * editIng... is the collection of input values in the .jsp
	     * at time servlet was called; only name and type can be changed
	     */
	    Ingredient ing = dataMgr.getIngredient(editIngId);
	    
	    Ingredients ings = dataMgr.getIngredients();
	    Iterator<Ingredient> iIngs = ings.iterator();
	    while(iIngs.hasNext()) 
	    {
	        Ingredient oldIng = iIngs.next();
	        if(oldIng.getId().equals(ing.getId()))
	        {
	            // remove oldIng from the ings list...
	            ings.removeIngredient(oldIng.getId());
	            
	            // set name and type according to .jsp inputs...
	            ing.setName(editIngName);
	            ing.setType(editIngType);
	            
	            // return ing to ingredient collection
	            ings.addIngredient(ing);
	            break;
	        }
	    }
	    
	    XmlFactory xmlFactory = new XmlFactory();
	    String xml = xmlFactory.getUpdatedIngredientsXml(ings, dataMgr.getSuppliers());
	    Utilities.write_file(DataMgr.getPathToIceCreameryKitchenData() + "ingredients.xml", xml, false);
	    
	    // DataMgr's editIngredient() should calls XmlFactory's editIngredient() which writes 
        // a new ingredients.xml file with the updated ingredient in it
	    String[] ingNameParam = { "ingName", ing.getName() };
	    String[] ingTypeParam = { "ingType", ing.getType() };
	    String[] ingIdParam = { "ingId", editIngId };
	    String[] ingCategoryParam = { "ingCategory", ingCategory };
	    String[] modeParam = { "mode", "edit" };
	    
	    htmlParams.add(ingNameParam);
	    htmlParams.add(ingTypeParam);
	    htmlParams.add(ingCategoryParam);
	    htmlParams.add(ingIdParam);
	    htmlParams.add(modeParam);
	}
	
	protected void deleteIng()
	{
        // DataMgr's deleteIngredient() should call XmlFactory's deleteIngredient() which writes 
        // a new ingredients.xml file with the updated ingredient in it

	    Ingredients ings = dataMgr.getIngredients();
        ings.removeIngredient(ing.getId());
	}
	
	private void doRedirect(String redirectTarget, String redirectArgs, HttpServletResponse response)
	{
		
		String target = redirectTarget + redirectArgs;
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
	
}
