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
import com.electraink.iceCreamery.comments.Comments;
import com.electraink.iceCreamery.comments.CommentsMgr;
import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.utilities.EmailUtility;
import com.electraink.iceCreamery.utilities.Utilities;
import com.electraink.iceCreamery.utilities.XmlFactory;

public class CommentReceptor extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private CommentsMgr commentsMgr;
	private XmlFactory xmlFactory;
	
	private boolean updateXml;
	
	// email message header
	private String host;
	private String port;
	private String user;
	private String pass;
	private String recipient;
	private String emailSubject;

	// data from comments form
	private String screenName = "";
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String commentDate = "";
	private String commentTxt = "";
	private String commentSubject = "";
	
	private String redirectTarget = "/iceCreamery/";
	private String responseMsg = "";
	private String redirectArgs = "";
	
    public CommentReceptor() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init()
    {
    	updateXml = false;
    	
    	// get mail server info from web.xml
    	ServletContext context = getServletContext();
    	host = context.getInitParameter("host");
    	port = context.getInitParameter("port");
    	user = context.getInitParameter("user");
    	pass = context.getInitParameter("pass");
    	recipient = context.getInitParameter("recipient");
    	emailSubject = context.getInitParameter("subject");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		getParams(request);

		commentsMgr = new CommentsMgr();
		
		if(validScreenName())
		{
			addComment();
			sendEmail();
		}
		
		if(updateXml)
			updateXml();
		
		doRedirect(redirectTarget, responseMsg, redirectArgs, response);
	}
	
	private void updateXml()
	{
		xmlFactory = new XmlFactory("comments");
		Comments comments = commentsMgr.getComments();
		String updatedXml = xmlFactory.getUpdatedCommentsXml(comments);
		Utilities.write_file(DataMgr.commentsDB, DataMgr.xmlHeader + updatedXml, true);
		
	}
	
	private boolean validScreenName()
	{
		boolean ret = true;
		
		if(!commentsMgr.screenNameValid(screenName, email))
		{
			responseMsg = "forms/comment_form.jsp";
//			responseMsg = "/screenNameConflict.jsp";
			redirectArgs = "?screenName=" + screenName +
						   "&firstName=" + firstName +
					       "&lastName=" + lastName +
					       "&email=" + email +
					       "&commentSubject=" + commentSubject +
					       "&commentTxt=" + commentTxt +
					       "&vMsgText=Screen name%20already%20used;%20choose%20another";
			ret = false;
		}
		else
		{
			redirectArgs = "";
			responseMsg = "msgFiles/success.jsp";
		}
		
		return ret;
	}
	
	private void addComment()
	{
		String formatPattern = "MM dd yyyy HH:mm";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
		Date date = new Date();
		
		Comment comment = new Comment();
		comment.setCommentTxt(commentTxt);
		comment.setDate(date.toString());
		comment.setEmail(email);
		comment.setFirstName(firstName);
		comment.setLastName(lastName);
		comment.setScreenName(screenName);
		comment.setCommentSubject(commentSubject);
		
		commentsMgr.addComment(comment);
		updateXml = true;
	}
		
	private void sendEmail()
	{
		String msgText = "A comment has been added to the Ice Creamery site by " +
	                  screenName + 
	                  " (" + 
	                  firstName + 
	                  " " + 
	                  lastName + 
	                  " at " +
	                  email +
	                  "): \n\n" +
	                  "Subject: " + 
	                  commentSubject + 
	                  "\n\n" +
				      commentTxt;
		
		try
		{
			EmailUtility.sendEmail(host, port, user, pass, recipient, emailSubject, msgText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			responseMsg = "msgFiles/error.jsp";
		}
	}
	
	private void getParams(HttpServletRequest request)
	{
		String key = "";
		String value = "";
		
		screenName = "";
		firstName = "";
		lastName = "";
		email = "";
		commentTxt = "";
		commentDate = new Date().toString();
		commentSubject = "";
		
        // Get current values for all request parameters
        Enumeration<?> params = request.getParameterNames();
        while(params.hasMoreElements())
        {
        	key = (String)params.nextElement();
    		value = request.getParameter(key);
    		
    		if(key.compareTo("screenName") == 0)
    			screenName = value;
    		
    		if(key.compareTo("firstName") == 0)
    			firstName = value;
    		
    		if(key.compareTo("lastName") == 0)
    			lastName = value;

    		if(key.compareTo("email") == 0)
    			email = value;
    		
    		if(key.compareTo("commentSubject") == 0)
    			commentSubject = value;
    		
    		if(key.compareTo("commentTxt") == 0)
    			commentTxt = value;
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
