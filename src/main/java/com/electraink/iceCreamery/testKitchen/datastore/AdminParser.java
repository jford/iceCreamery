package com.electraink.iceCreamery.testKitchen.datastore;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.electraink.iceCreamery.utilities.DOMParser;

public class AdminParser
{
	private DOMParser domParser = new DOMParser();
	
	private String node_name = "";
//	private String xmlFilename = DataMgr.recipesDB;
    
    private Admin admin = new Admin();
    
    public AdminParser()
    {
    }
    
    public Admin parse(String adminDB)
    {
        // Parse the XML and create a DOM node tree
        Document parsedXML = domParser.parse(adminDB);
        
    	// walk the DOM tree
        processDocument(parsedXML);
        
        return admin;
    }
    
    private void processDocument(Node n)
    {
        int type = n.getNodeType();
        switch (type)
        {
            case Node.ELEMENT_NODE:
            {
                node_name = n.getNodeName();
                if(node_name.compareTo("user") == 0)
                {
                    String[] user = new String[2];
                	processUser(n, user);
                	admin.addUser(user);
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
    
    private void processUser(Node n, String[] user)
    {
    	int type = n.getNodeType();
        
        NamedNodeMap attributes = n.getAttributes();
        if(attributes != null && node_name.compareTo("user") == 0)
        {
	        int numAttrs = attributes.getLength();
	        for(int i = 0; i < numAttrs; i++)
	        {
	        	Attr attr = (Attr)attributes.item(i);
	        	String attrName = attr.getNodeName();
	        	String attrValue = attr.getNodeValue();
	        	
	        	if(attrName.equals("id"))
	        		user[0] = attrValue;
	        	if(attrName.equals("pwd"))
	        		user[1] = attrValue;
	        }
        }
        for(Node child = n.getFirstChild();
                child != null;
                child = child.getNextSibling())
       {
           processUser(child, user);
       }

    }
    
}
