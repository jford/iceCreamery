package com.electraink.iceCreamery.comments;

import java.util.*;

/*
 * iterator implementation from https://codereview.stackexchange.com/questions/48109/simple-example-of-an-iterable-and-an-iterator-in-java
 */

public class Comments implements Iterable<Comment>, Iterator<Comment>
{
	private Vector<Comment> comments = null;
	private int start, end, cursor;
	
	public Comments()
	{
		comments = new Vector<Comment>();
		initializeIterators();
	}
	
	public Comments(int size)
	{
		comments = new Vector<Comment>(size);
		initializeIterators();
	}
	
	public void initializeIterators()
	{
		start = 0;
		end = 0;
	}
	
	public void addComment(Comment comment)
	{
		comments.add(comment);
		end++;
		return;
	}
	
	public Vector<Comment> getComments()
	{
		return comments;
	}
	
	public Iterator<Comment> iterator()
	{
		cursor = start;
		return this;
	}
	
	public boolean hasNext()
	{
		return cursor < end;
	}
	
	public Comment next()
	{
		if(!hasNext())
//		if(cursor >= end)
		{
			cursor = start;
			throw new NoSuchElementException();
		}
		return comments.elementAt(cursor++);
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	public int size()
	{
		return comments.size();
	}
	
}
