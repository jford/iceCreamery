package com.electraink.iceCreamery.utilities;

public class HrefTags 
{
	String anchorOpen;
	String anchorClose;
	String hrefClose;
	String href;
	String target;
	
	public HrefTags()
	{
		anchorOpen = "<a href=\"";
		hrefClose = "\">";
		anchorClose = "</a>";
	}
	
	public String getHref(String target, String link)
	{
		this.target = target;
		href = anchorOpen +
		       target +
		       hrefClose +
		       link +
		       anchorClose;
		
		return href;
	}
	
	public String getIngEditorHref(String ingType, String link, String[] hrefArgs)
	{
	    String args = "";
	    if(hrefArgs != null)
	    {
	        for(int i = 0; i < hrefArgs.length; i++)
	        {
	            if(!hrefArgs[i].isEmpty())
	                args = "&" + hrefArgs[i];
	        }
	    }
	    
		target = "javascript:displaySupplementalLarge('../editors/ingEditor.jsp?ingType=" + 
                 ingType +
                 "&ingCategory=" +
                 link +
                 args +
                 "')";
		
		href = anchorOpen +
			   target +
			   hrefClose +
			   link +
			   anchorClose;
		
		return href;
	}
}
