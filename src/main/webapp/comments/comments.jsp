<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.lang.Integer,java.util.Iterator,com.electraink.iceCreamery.comments.*"%>

 <html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="description" content="How I Learned to Make Ice Cream"/>
		<meta name="robots" content="noindex, nofollow"/>
		<title>Ice Creamery&mdash;Comments</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
			<link rel='stylesheet' type='text/css' href='../styles/comments_page_styles.css'/> 		
	</head>
	<body>
		<div class="contentDisplayArea" style="margin-top: 10%">
		
			<div class="page_title">Comments</div><!-- page_title -->
			<div class="text_note">
			<p>
			This comments section is not a full-fledged message board. 
			You can leave a comment, but you cannot fix your comment to other comments (no reply function).
			</p>
			<p>If you post a question related to ice cream making, I'll try to answer 
			as best I can. 
			</p>
			<p>To see the few simple rules of usage that govern the comments page, click 
			<a href="javascript:displayRulesOfUse()">here.</a>  
			</p>
			<p>
			Posts will be displayed in reverse order of arrival&mdash;most recent post goes to the top.
			</p>
			<p>
			The <a href="mailto:electraink.webmaster@gmail.com">webmaster</a>.
			</p>
			</div><!-- text_note -->
			
<%
	CommentsMgr commentsMgr = new CommentsMgr();
//	Comments comments = commentsMgr.getComments();
	Comments comments = commentsMgr.getReverseComments();
	Comment comment = null;
	String screenName = "";
	String commentSubject = "";
	String commentDate = "";
	String commentTxt = "";

	Iterator<Comment> cmtsI = comments.iterator();
	while(cmtsI.hasNext())
	{
		comment = (Comment)cmtsI.next();
		screenName = comment.getScreenName();
		commentSubject = comment.getCommentSubject();
		commentDate = comment.getCommentDate();
		commentTxt = comment.getCommentTxt();
%>
	<p class="subject"><span class="label">Subject:</span> <%= commentSubject %></p>
	<p class="screenName"><span class="label">Posted by:</span> <%= screenName %></p>
	<p class="date"><%= commentDate %></p>
	<p class="text"><%= commentTxt %></p>
	<hr/>
<%
	}
%>			
			
		</div> <!-- contentDisplayArea -->

		<div class="send_back">
			<div class="send_back_pair">
				<div class="send_back_label">Go to </div>
				<div class="send_back_target"><a href="../">Introduction</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="../forms/comment_form.jsp">Comment Input Form</a></div>
			</div><!-- send_back_pair -->
		</div><!--  send_back -->
			
	</body>
</html>
