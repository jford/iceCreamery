package com.electraink.iceCreamery.comments;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import com.electraink.iceCreamery.utilities.*;
import com.electraink.iceCreamery.utilities.Utilities;

import java.util.*;

public class CommentsMgr
{
//	private XmlFactory xmlFactory;
	private Comments comments;
	
	public CommentsMgr()
	{
		comments = generateCommentsObj();
//		xmlFactory = new XmlFactory("comments");
		// initialize factory for comments
	}
	public void addComment(Comment comment)
	{
//		xmlFactory.addComment(comment);
		comments.addComment(comment);
	}
	public boolean screenNameValid(String screenName, String email)
	{
		boolean validity = true;
		
		int count = 0;
		
		XmlFactory xmlFactory = new XmlFactory("comments");
		String[][] screenNameList = xmlFactory.getScreenNameList();
		
		for(count = 0; count < screenNameList.length; count++)
		{
			if(screenNameList[count][0].compareTo(screenName) == 0 && screenNameList[count][1].compareTo(email) != 0)
			{
				validity = false;
				break;
			}
		}
		return validity;
	}
	public Comments getComments()
	{
//		Comments comments = generateCommentsObj();
		return comments;
	}
	
	public Comments getReverseComments()
	{
//		Comments comments = generateCommentsObj();
		int count = comments.size();
//		Vector<Comment> reverseList = new Vector<Comment>(count);
		Comment obj[] = new Comment[count];
		Comment comment = null;
		
		Iterator<Comment> cmtsI = comments.iterator();
		while(cmtsI.hasNext())
		{
			comment = cmtsI.next();
			obj[--count] = comment;
//			reverseList.add(--count, comment);
		}
		Comments reverseComments = new Comments();

		count = 0;
		while(count < comments.size())
		{
			reverseComments.addComment(obj[count++]);
		}
		
		return reverseComments;
	}
	
	private Comments generateCommentsObj()
	{
		String filename = DataMgr.commentsDB;
		CommentsParser parser = new CommentsParser();
		Comments comments = parser.parse(filename);
		return comments;
	}
}
