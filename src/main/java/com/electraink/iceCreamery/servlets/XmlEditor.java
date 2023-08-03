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

public class XmlEditor extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	
	// form params
	private String editTxt; // ???
	private String setMode; // val is Get Text when setting scope, Update Text with submitting changes
	private String scope; // should be one of dataMgr.xmlEditScopes[] 
	private String formSbmtBtn;
	private String ingByName; // id of selected ingredient
	private String recipeByName; // id of selected recipe
	
	private String sourceFile = "";
	private String outputFile = DataMgr.getPathToXml() + "editBuff.xml";
	private String mode = "";
	
	private String vMsg = "";

	private boolean saveSelection;
	
	// return strings
	private String redirectTarget;
	private String responseMsg;
	private String redirectArgs;
	
	private Vector<String[]> htmlParams = new Vector<String[]>();
	
    public XmlEditor() 
    {
        super();
    }
    
    public void init(HttpServletRequest request, HttpServletResponse response)
    {
    	this.response = response;
    	
		redirectArgs = "";
		redirectTarget = "/iceCreamery/editors/xmlEditor.jsp";
		responseMsg = "";

		// params
		editTxt = "";
		scope = "";
		formSbmtBtn = "";
		saveSelection = false;
		setMode = "";
		
		// misc
		sourceFile = "";
		mode = "";
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// get param values
		init(request, response);
        getParams(request);
        
        if(formSbmtBtn.equals(Utilities.getProperty(DataMgr.propertiesFile, "xmlEdit")))
		{
			// a block of text has been requested for review by the Xml Editor; 
			// getEditText() will write the requested text to testKitchen/xml.editBuff.xml
		    getEditText();
		}
		else if(formSbmtBtn.equals(Utilities.getProperty(DataMgr.propertiesFile, "xmlSave")))
        {
            // changes were made in the Xml Editor, time to save them
            saveEditText();
        }
		else
		    vMsg = "Unable to process request from unknown source.";

        String vMsgText = getVmsgText(); 
        if(!vMsgText.isEmpty())
        {
            String[] vMsgParam = { "vMsgText", vMsgText };
            htmlParams.add(vMsgParam);
        }
		doRedirect();
	}
	private void saveEditText()
	{
		int idx = 0;
		int end = 0;
		String recipeXml = "";
		String editBuff = "";
		if(scope.equals("allRecipes") || scope.equals("allIngs"))
		{
		    String xmlNm = scope.equals("allRecipes") ? "recipes.xml" : "ingredients.xml"; 
		    try
    		{
    			recipeXml = Utilities.read_file(DataMgr.getPathToXml() + xmlNm);
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
		}
		if(scope.equals("recipe"))
		{
			String recByNameAttr = "name=\"" + recipeByName + "\"";
			String recCloseTag = "</recipe>";
			idx = recipeXml.indexOf(recByNameAttr);
			idx = recipeXml.lastIndexOf("<", idx);
			end = recipeXml.indexOf(recCloseTag, idx + 1) + recCloseTag.length();
			recipeXml = recipeXml.substring(0, idx) + editTxt + recipeXml.substring(end);  
		}
		else
		{
			String subTextStartMrkr = "<" + scope + ">";
			String subTextEndMrkr = "</" + scope + ">";
			
			idx = recipeXml.lastIndexOf(DataMgr.lineBrk, recipeXml.indexOf(subTextStartMrkr)) + DataMgr.lineBrk.length();
			end = recipeXml.indexOf(DataMgr.lineBrk, recipeXml.indexOf(subTextEndMrkr));
			
			String startStr = recipeXml.substring(0, idx);
			String endStr = recipeXml.substring(end);
			
			recipeXml = startStr + editTxt + endStr;
		}
		
		Utilities.write_file(DataMgr.getPathToXml() + "recipes.xml", recipeXml, true);
	}
	
	private String getEditText()
	{
		String outputFilename = "editBuff.xml";
		String subTextStartMrkr = "";
		String subTextEndMrkr = "";
		String text = "";
		
		try
		{
			text = Utilities.read_file(DataMgr.getPathToXml() + sourceFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
        //    editScope                     output file contents
		//---------------------|-----------------------------------------------------
		// 0   "allRcps"           entire recipe.xml
		// 1   "allIngs"           entire ingredients.xml
		// 2   "rcpByName"         xml for the recipe identified by recipeByName
		// 3   "ingByName"         xml for the ingredient identified by ingByName
		// 4   "dairyTypeDefs"           "                "
		// 5   "emulsifierDefs"          "                "
		// 6   "flavorTypeDefs"          "                "
		// 7   "saltDefs"                "                "
		// 8   "stabilizerDefs"          "                "
		// 9   "sweetenerDefs"           "                "
		//
		int idx = 0;
		int end = 0;
		String endMrkr = "";
		
		if(scope.equals(DataMgr.xmlEditScopes[0]) ||
		   scope.equals(DataMgr.xmlEditScopes[1]))
		{
		    // return entire source xml
		}
		if(scope.equals(DataMgr.xmlEditScopes[2]))
		{
		    // return only recipe indicated by recipeByName
		    idx = text.indexOf(recipeByName);
		    idx = text.lastIndexOf("<", idx);
		    
		    endMrkr = "</recipe>";
		    end = text.indexOf(endMrkr) + endMrkr.length();
		    
		    text = text.substring(idx, end);
		}
		if(scope.equals(DataMgr.xmlEditScopes[3]))
		{
            // return only ingredient indicated by ingByName
            idx = text.indexOf(ingByName);
            idx = text.lastIndexOf("<", idx);
            
            endMrkr = "</ingredient>";
            end = text.indexOf(endMrkr) + endMrkr.length();
            
            text = text.substring(idx, end);
		}
		if(scope.equals(DataMgr.xmlEditScopes[3]))
		{
		    // return only ingredient indicated by ingByName
		}
		else
		{
		    // return ingredients of type indicated by editScope
		}
		
//			// output file will contain xml for recipeByName
//			idx = 0;
//			end = 0;
//			subTextStartMrkr = ""; 
//			String endRcpTag = "</recipe>";
//			
//			idx = text.indexOf("name=\"" + recipeByName + "\"");
//			idx = text.lastIndexOf("<", idx);
//			end = text.indexOf(endRcpTag, idx + 1) + endRcpTag.length();
//			text = text.substring(idx, end);
//			
//			String[] recipeByNameParam =  {"recipeByName", recipeByName};
//			htmlParams.add(recipeByNameParam);
//
//		}
//		else if(editScope.equals(DataMgr.xmlEditScopes[1]) ||
//	            editScope.equals(DataMgr.xmlEditScopes[2]))
//		{
//            // output file will contain xml for ingredientByName
//            idx = 0;
//            end = 0;
//            subTextStartMrkr = ""; 
//            String endRcpTag = "</recipe>";
//            
//            idx = text.indexOf("name=\"" + recipeByName + "\"");
//            idx = text.lastIndexOf("<", idx);
//            end = text.indexOf(endRcpTag, idx + 1) + endRcpTag.length();
//            text = text.substring(idx, end);
//            
//            String[] recipeByNameParam =  {"recipeByName", recipeByName};
//            htmlParams.add(recipeByNameParam);
//		}

		Utilities.write_file(DataMgr.getPathToXml() + outputFilename, text, true);
		return outputFilename;
	}
	private String getVmsgText()
	{
		return vMsg;
	}
	private void getParams(HttpServletRequest request)
	{
	    Utilities.viewServletParams(request);
	    
		String key = "";
		String value = "";
		
		// Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		if(key.equals("scope"))
    		{
//              DataMgr public static String[] xmlEditScopes = 
//              "allRcps",  // 0
//              "allIngs",  // 1
//              "rcpByNm",  // 2
//              "ingByNm",  // 3
//              "allDrys",  // 4 
//              "allEmuls", // 5
//              "allFlvs",  // 6 
//              "allSalts", // 7
//              "allStabs", // 8
//              "allSwts"   // 9

                  if(value.equals(DataMgr.xmlEditScopes[0]) ||
                     value.equals(DataMgr.xmlEditScopes[2]))
                      sourceFile = "recipes.xml";
                  else 
                      sourceFile = "ingredients.xml";
                  
                  String[] srcParam = { "src", sourceFile };
                  htmlParams.add(srcParam);
                  String[] scopeParam = { "scope", value };
                  htmlParams.add(scopeParam);
                  
                  scope = value;
    		}
    		
    		if(key.equals("formSbmtBtn"))
    		{
    			formSbmtBtn = value;
    		}
    		
            if(key.equals("ingByName"))
                ingByName = Utilities.decodeHtmlParam(value);
            
    		if(key.equals("recipeByName"))
    			recipeByName = Utilities.decodeHtmlParam(value);
    		
    	}
        
        if(!setMode.isEmpty())
        {
            mode = "getText";
        }
        else
            mode = "saveText";
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
