<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	// values set in contact_info.jsp
	String screenName = request.getParameter("screenName");
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String email = request.getParameter("email");
	String commentDate = request.getParameter("commentDate");
	String commentTxt = request.getParameter("commentTxt");
	String params = "?firstName=" + firstName +
			        "&lastName=" + lastName +
			        "&email" + email +
			        "&commentDate" + commentDate + 
			        "&commentTxt" + commentTxt;
%>
 <html>
	<head>
		<title>Comment Not Posted</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
	</head>
	<body>
	<div="contentDisplayArea">
	<div class="page_title">
	Comment Could Not Be Posted
	</div> <!-- page_title -->
	<p>
	The screen name <i><%= screenName %></i> has already been used. Please choose another.
	</p>
	</div> <!--  -->
	</div> <!-- contentDisplayArea -->	

	<div class="send_back">
		<div class="send_back_pair">
			<div class="send_back_label">Go to </div>
			<div class="send_back_target"><a href="/mustang_restoration">Overview</a></div>
		</div> <!--  send_back_pair -->
		<div class="send_back_pair">
			<div class="send_back_label"></div>
			<div class="send_back_target"><a href="../project_begins.jsp">Project Begins</a></div>
		</div> <!--  send_back_pair -->
		<div class="send_back_pair">
			<div class="send_back_label"></div>
			<div class="send_back_target"><a href="../work_goes_on.jsp">Work Goes On</a></div>
		</div> <!--  send_back_pair -->
		<div class="send_back_pair">
			<div class="send_back_label">View </div>
			<div class="send_back_target"><a href="../comments/comments.jsp">Comments</a></div>
		</div> <!--  send_back_pair -->
	</div><!-- send_back -->
		
	</body>
</html>