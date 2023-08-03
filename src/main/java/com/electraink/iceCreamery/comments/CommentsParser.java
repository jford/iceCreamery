package com.electraink.iceCreamery.comments;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.electraink.iceCreamery.utilities.*;

public class CommentsParser
{
	private DOMParser domParser = new DOMParser();
	
    private String[] screenName = { "", "" };
	private String[][] screenNamesList;

	private String node_name = "";
    
    private String xmlFilename = DataMgr.commentsDB;
    
//    private Vector<Comment> comments = new Vector<Comment>();
    private Comments comments = new Comments();
    private Vector<String[]> screenNames = new Vector<String[]>();
    
    public CommentsParser()
    {
    }
    
    public String[][] getScreenNamesList()
    {
    	int count = 0;
    	
    	screenNamesList = new String[screenNames.size()][2];
    	Iterator<String[]> listI = screenNames.iterator();
    	while(listI.hasNext())
    	{
    		String[] screenName =  { "", "" };

    		screenName = (String[])listI.next();
    		screenNamesList[count][0] = screenName[0];
    		screenNamesList[count][1] = screenName[1];
    		count++;
    	}
    	return screenNamesList;
    }
    
//    public Vector<Comment> parse(String xml)
    public Comments parse(String xml)
    {
        // Parse the XML and create a DOM node tree
        Document parsedXML = domParser.parse(xml);
        
    	// walk the DOM tree
        processDocument(parsedXML);
        
        return comments;
    }
    
    public Comments parseFromStream(String xmlContents)
    {
    	Document parsedXML = domParser.parseFromStream(xmlContents);
    	processDocument(parsedXML);
    	
    	return comments;
    }

    private void processDocument(Node n)
    {
        int type = n.getNodeType();
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
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

        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
                if(node_name.compareTo("comment") == 0)
                {
                    Comment comment = new Comment();
                	processComment(n, comment);
                	comments.addComment(comment);
                 	addScreenName();
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
    
    private void processComment(Node n, Comment comment)
    {
        int type = n.getNodeType();

        switch (type)
        {
	        case Node.ELEMENT_NODE:
	        {
	        	node_name = n.getNodeName();
	            NamedNodeMap atts = n.getAttributes();
	            if(node_name.compareTo("comment") == 0)
	            {
	                for(int i =0; i < atts.getLength(); i++)
	                {
	                    Node att = atts.item(i);
	                    processComment(att, comment);
	                }
	            }
	            if(node_name.compareTo("commentTxt") == 0)
	            {
	            	comment.setCommentTxt(getText(n));
	            }
	            if(node_name.compareTo("commentSubject") == 0)
	            {
	            	comment.setCommentSubject(getText(n));
	            }
	            if(node_name.compareTo("firstName") == 0)
	            {
	            	comment.setFirstName(getText(n));
	            }
	            if(node_name.compareTo("lastName") == 0)
	            {
	            	comment.setLastName(getText(n));
	            }
	            if(node_name.compareTo("commentDate") == 0)
	            {
	            	comment.setDate(getText(n));
	            }
	        }
	        break;
	        case Node.ATTRIBUTE_NODE:
	        {
	            node_name = n.getNodeName();
	            if(node_name.compareTo("email") == 0)
	            {
	            	comment.setEmail(getText(n));
	            	screenName[1] = getText(n);
	            }
	            if(node_name.compareTo("screenName") == 0)
	            {
	            	comment.setScreenName(getText(n));
	            	screenName[0] = getText(n);
	            }
	        }
            break;
        }
        for(Node child = n.getFirstChild();
                 child != null;
                 child = child.getNextSibling())
        {
            processComment(child, comment);
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
    
    private void addScreenName()
    {
    	String[] xfr = { "","" };
    	xfr[0] = screenName[0];
    	xfr[1] = screenName[1];
    	
    	screenNames.add(xfr);
    }
}


