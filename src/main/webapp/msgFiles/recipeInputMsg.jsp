<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.electraink.iceCreamery.utilities.Utilities"%>
 <html>
	<head>
		<title>Recipe Input Message</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		
	</head>
	<body>
	<div class="cotentDisplayArea">
		<div class="page_title" style="margin-top: 10%">Errors in Recipe Submission</div><!-- page_title -->
		<div class="text">
				<%
		String vMsgText = request.getParameter("vMsgText");
		vMsgText = vMsgText == null || vMsgText.compareTo("null") == 0 ? "" : vMsgText;
		if(!vMsgText.isEmpty())
		{
			vMsgText = Utilities.decodeHtmlTags(vMsgText);
			vMsgText = vMsgText.replaceAll("- ", "</li><li>");
			if(vMsgText.startsWith("</li>"))
				vMsgText = vMsgText.substring(5);
			if(vMsgText.endsWith("<li>"))
				vMsgText = vMsgText.substring(0, vMsgText.length() -4);
			%>
			<ul>
				<%= vMsgText %>
			</ul>
			<%
		}
		%>
			<div class="text_note">
			Use the browser back button for the input page to repopluate fields.
			</div><!-- text_note -->
		</div><!-- text -->
	</div> <!-- contentDisplayArea -->
	</body>
</html>
