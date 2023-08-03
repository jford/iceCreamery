package com.electraink.iceCreamery.servlets;

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

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.Ingredient;
import com.electraink.iceCreamery.testKitchen.datastore.Ingredients;
import com.electraink.iceCreamery.utilities.Utilities;

/**
 * Servlet implementation class ingGetText
 */
public class IngGetText extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    
    private String editIngId;
    private String editIngName;
    private String editIngType;
    private String ingCategory;
    private String ingId;
    private String ingName;
    private String ingSlct;
    private String ingType;
    private String mode;
    private String newIngName;
    private String vMsgText;

    // return URL components
    private String redirectTarget;
    private String redirectArgs;
    
    private Vector<String[]> htmlParams = new Vector<String[]>();
    private Ingredient ing;
    
    private DataMgr dataMgr = new DataMgr();
    
    public IngGetText() 
    {
        super();

    }
    
    public void init()
    {
        editIngId = "";
        editIngName = "";
        editIngType = "";
        ingCategory = "";
        ingId = "";
        ingName = "";
        ingSlct = "";
        ingType = "";
        mode = "";
        newIngName = "";
        redirectArgs = "";
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
        
        Ingredient ing = dataMgr.getIngredient(ingSlct);

        String[] modeParam = { "mode", mode };
        htmlParams.add(modeParam);
        
        String[] ingIdParam = { "ingId", ingSlct };
        htmlParams.add(ingIdParam);
        
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
                case "editIngId":
                    editIngId = value;
                    
                case "editIngName":
                    ingName = value;
                    break;
                    
                case "editIngType":
                    editIngType = value;
                    break;
    
                case "ingId":
                    ingId = value;
                    break;
    
                case "ingName":
                    ingName = value;
                    break;
                    
                case "ingSlct":
                    ingSlct = value;
                    break;
                
                case "mode":
                    mode = value;
                    break;
                    
                case "newIngName":
                    newIngName = value;
                    break;
            }
        }
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