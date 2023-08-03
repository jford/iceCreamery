package com.electraink.iceCreamery.comments;

public class Comment
{
	private String commentDate;
	private String commentSubject;
	private String commentTxt;
	private String email;
	private String firstName;
	private String lastName;
	private String screenName;
	
	public Comment()
	{
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getLastName()
	{
		return lastName;
	}
	
	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}
	public String getScreenName()
	{
		return screenName;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail()
	{
		return email;
	}
	
	public void setCommentTxt(String commentTxt)
	{
		this.commentTxt = commentTxt;
	}
	public String getCommentTxt()
	{
		return commentTxt;
	}

	public void setDate(String commentDate)
	{
		this.commentDate = commentDate;
	}
	public String getCommentDate()
	{
		return commentDate;
	}

	public void setCommentSubject(String commentSubject)
	{
		this.commentSubject = commentSubject;
	}
	public String getCommentSubject()
	{
		return commentSubject;
	}
}
