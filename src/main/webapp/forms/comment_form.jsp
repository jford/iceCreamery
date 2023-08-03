<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html>
	<head>
	<title>Comment Input Form</title>
			<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<%
		String screenName = request.getParameter("screenName");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String commentDate = request.getParameter("commentDate");
		String commentTxt = request.getParameter("commentTxt");
		String commentSubject = request.getParameter("commentSubject");
		String vMsgText = request.getParameter("vMsgText");
		
		screenName = screenName == null || screenName.compareTo("null") == 0 ? "" : screenName;
		firstName = firstName == null || firstName.compareTo("null") == 0 ? "" : firstName;
		lastName = lastName == null || lastName.compareTo("null") == 0 ? "" : lastName;
		email = email == null || email.compareTo("null") == 0 ? "" : email;
		commentDate = commentDate == null || commentDate.compareTo("null") == 0 ? "" : commentDate;
		commentTxt = commentTxt == null || commentTxt.compareTo("null") == 0 ? "" : commentTxt;
		commentSubject = commentSubject == null || commentSubject.compareTo("null") == 0 ? "" : commentSubject;
		vMsgText = vMsgText == null || vMsgText.compareTo("null") == 0 ? "" : vMsgText;
		
		if(!vMsgText.isEmpty())
		{
			%>
			<style>div.vMsgBox{ display: block; }</style>
			<%
		}
				
//		String vMsgText = "";
		%>
	</head>
	<body>
	<div class="contentDisplayArea">
	
		<div class="page_title" style="width: 100%; text-align: center;">
		Leave a Comment
		</div> <!-- page_title -->
		
		<form name="comment" class="comment" action="../comment" method="post"  onsubmit="getCommentText();return validateCommmentInputData();">
		
			<table class="commentInputForm">
			
				<!-- subject -->
				<tr>
					<td class="dataCategory" colspan="4">Subject</td>
				</tr>
				<tr>
					<td class="dataItemInput" colspan="4"><input type="text" value="<%= commentSubject %>" name="commentSubject" id="commentSubject" size="68"/></td>
				</tr>
			
				<!-- comment -->
				<tr>
					<td class="dataCategory" colspan="4">Comment </td>
				</tr>
				<tr>
					<td colspan="4"><textarea name="commentTxt" id="commentTxt" rows="5" cols="50"><%= commentTxt %></textarea></td>
				</tr>
				
				<!-- name -->
				<tr>
					<td class="dataCategory" colspan="4">Name</td>
				</tr>
				<tr>
					<td class="dataItemLabel">First</td>
					<td class="dataItemInput" colspan="3"><input type="text" value="<%= firstName %>" name="firstName" id="firstName" size="30"/></td>
				</tr>
				<tr>
					<td class="dataItemLabel"> Last</td>
					<td class="dataItemInput" colspan="3"><input type="text" value="<%= lastName %>" name="lastName" id="lastName" size="30"/></td>
				</tr>
				<tr>
					<td class="dataItemLabel">Screen Name</td>
					<td class="dataItemInput" colspan="3"><input type="text" value="<%= screenName %>" name="screenName" id="screenName" size="30"/></td>
				</tr>
				
				<!-- email -->
				<tr>
					<td class="dataCategory" colspan="4">Email Address</td>
				</tr>
				<tr>
					<td class="dataItemLabel">Email</td>
					<td class="dataItemInput" colspan="3"><input type="text" value="<%= email %>" name="email" id= "email" size="30" /></td>
				</tr>
				
			</table>
		
			<div class="vMsgBox" id="vMsgBox" >
				<textarea readonly class="vMsgText" id="vMsgText">
				<%
				if(!vMsgText.isEmpty())
				{
				%>
					<%= vMsgText %>
				<%
				}
				%>
				</textarea>
			</div>
		
			<div style="padding-top: 20px; padding-bottom: 20px;">
				<input type="submit" name="comment_submit" id="comment_submit" value="Post Comment"/>
				<input type="hidden" name="comment" id="comment"/>
			</div> <!-- local style for input button placement -->

		</form>
	</div> <!-- contentDisplayArea -->
	
	<div class="send_back">
		<div class="send_back_pair">
			<div class="send_back_label">Go to </div>
			<div class="send_back_target"><a href="/iceCreamery">Introduction</a></div>
		</div> <!--  send_back_pair -->
		<div class="send_back_pair">
			<div class="send_back_label">View </div>
			<div class="send_back_target"><a href="../comments/comments.jsp">Comments</a></div>
		</div> <!--  send_back_pair -->
		
	</div> <!-- send_back -->
	
	</body>
</html>

