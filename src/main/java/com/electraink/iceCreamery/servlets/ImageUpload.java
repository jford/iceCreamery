package com.electraink.iceCreamery.servlets;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.imageCtl.*;
import com.electraink.iceCreamery.utilities.Utilities;

import java.io.IOException;
// import jakarta.imageio.ImageIO;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

@MultipartConfig
public class ImageUpload extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private boolean uploadSubmit;

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String imageFilename;
	private String deleteFilename;
	private boolean removeImageSubmit;

	// return strings
	private String redirectTarget;
	private String responseMsg;
	private String redirectArgs;
	private String vMsg;
	

    public ImageUpload() 
    {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException 
    {
    	/*
    	 * user has clicked upload image button on 8image_upload.jsp
    	 */
        doPost(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	this.request = request;
    	this.response = response;
    	
    	ImageMgr imageMgr = new ImageMgr();
        response.setContentType("text/html;charset=UTF-8");

		redirectArgs = "";
		responseMsg = "";
		redirectTarget = "/iceCreamery/";
		vMsg = "";

		getParams();
		if(removeImageSubmit)
		{
			if(deleteFilename.isEmpty())
				vMsg = "- No file selected for deletion.";
			else
				processRemoveImage();
		}
		else
			processUpload();

		if(!vMsg.isEmpty())
		{
			redirectTarget += "forms/recipe_input_form.jsp";
			redirectArgs = "?vMsg=" + vMsg;
		}
		doRedirect(redirectTarget, responseMsg, redirectArgs, response);
	}
    @Override
    public String getServletInfo() 
    {
        return "Servlet that uploads image for cover art";
    }
    
    private void processRemoveImage()
    {
    	ImageMgr imgMgr = new ImageMgr();
    	
    	File image = new File(ImageMgr.imageDirName + deleteFilename);
    	if(image.exists())
    		image.delete();
    	
    	Vector<String> imageUsageList = imgMgr.getImageUsage(deleteFilename);
    	if(imageUsageList.size() > 0)
    	{
    		redirectTarget += "forms/recipe_input_form.jsp";
    		redirectArgs = "?deleteFilename=" + deleteFilename;
    		if(imgMgr.getImageUsage(deleteFilename).size() > 0)
    			redirectArgs += "&vMsg=References to deleted files need to be removed manually from recipe definitions.";
    	}
    	else
    	{
    		redirectTarget += "forms/image_upload_form.jsp";
    	}
    }
    
    protected void processUpload()
            throws ServletException, IOException 
    {
        // "coverImage" is parameter passed by upload image form
        final Part filePart = request.getPart("image");
        
        // inFilename is file selected by user in file picker; need to strip path from filename string
//        final String filename = getFileName(filePart);
        String filename = getFileName(filePart);
        filename = filename.substring(filename.lastIndexOf(ImageMgr.pathMrkr) + ImageMgr.pathMrkr.length());
        // get the selected file and write it to images repository
        String buffer = "";
        String text = "";
        
        InputStream filecontent = null;
        OutputStream out = null;

        try 
        {
        	out = new FileOutputStream(new File(ImageMgr.imageDirName, filename));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while((read = filecontent.read(bytes)) != -1)
            {
            	out.write(bytes, 0, read);
            }
            redirectTarget += "testKitchen.jsp";
            responseMsg = "?vmsg=The file " + filename + " has been uploaded to the Ice Creamery image repository.";
//            redirectArgs = responseMsg;
        }
        catch (FileNotFoundException fne) 
        {
        	redirectTarget += "forms/image_upload_form.jsp";
        	if(filename.isEmpty())
        	{
	        	redirectArgs = "?vmsg=No file selected.";
        	}
        	else
        	{
        		String errMsg = "Error reading input file";
	        	System.out.println(errMsg + " in ImageUpload servlet.");
	        	fne.printStackTrace();
	        	redirectArgs = "?vMsg=" + errMsg + ".";
        	}
        }
        finally 
        {
            if (out != null) 
            {
                out.close();
            }
            if(filecontent != null)
            {
            	filecontent.close();
            }
        }
    }
    
    private String getFileName(final Part part)
    {
    	String name = "";
    	final String partHeader = part.getHeader("content-disposition");
    	for(String content : part.getHeader("content-disposition").split(";"))
    	{
    		if(content.trim().startsWith("filename"))
    		{
    			name = content.substring(content.indexOf('=') + 1).trim().replace("\"",  "");
    		}
    	}
    	imageFilename = name.substring(name.lastIndexOf(DataMgr.pathMrkr) + DataMgr.pathMrkr.length());
    	return name;
    }
    
    private void getParams()
    {
    	removeImageSubmit = false;
    	uploadSubmit = false;
    	
    	deleteFilename = "";
    	imageFilename = "";
    	
    	String key;
    	String value; 
    	
        // Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		if(key.equals("deleteFilename"))
    			deleteFilename = value;
    		
    		if(key.equals("image"))
    			imageFilename = value;
    		
    		if(key.equals("imageFilename"))
    			imageFilename = value;

    		if(key.equals("uploadSubmit"))
    			uploadSubmit = true;

    		if(key.equals("removeImageSubmit"))
    			removeImageSubmit = true;
}
    	
    }
	private void doRedirect(String redirectTarget, String responseMsg, String redirectArgs, HttpServletResponse response)
	{
		String target = redirectTarget + responseMsg + Utilities.encodeHtmlParam(redirectArgs);
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
