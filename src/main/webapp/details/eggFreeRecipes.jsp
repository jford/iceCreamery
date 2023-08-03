<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.lang.Integer,
                 java.util.*,
                 com.electraink.iceCreamery.testKitchen.datastore.*"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="description" content="How I Learned to Make Ice Cream."/>
		<meta name="robots" content="noindex, nofollow"/>
		<title>Ice Creamery: Egg Free Recipes</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
	</head>
</head>
<body>
	<div class="contentDisplayArea">
		<div class="page_title">
		Recipes With No Egg Yolk
		</div><!-- page_title -->
			<div class="text">
			<%
			RecipeMgr recipeMgr = new RecipeMgr();
			Vector<String> recipeNames = recipeMgr.getNamesRecsNoXEnum("emulsifier", "EGG_YOLK");
			Iterator<String> iRecNames = recipeNames.iterator();
			String recName;
			// if true, list was started and needs to be closed
			boolean itemize = false;
			if(!iRecNames.hasNext())
			{
			%>
				<p>There are no egg yolk free recipes.</p>
			<%
			}
			else
			{
				itemize = true;
			%>
				<p>
				The following <%= Integer.toString(recipeNames.size()) %> recipes do not specify egg yolks as an emulsifier:
				</p>
				<ul>
			<%
			}
			while(iRecNames.hasNext())
			{
				recName = iRecNames.next();
				%>
					<li><%= recName %></li>
				<%
			} // end iRecipes.hasNext() loop
			
			// if list of recipes was empty, there would not be a <ul> tag
			// and itemize would be false; if list was valid, tag needs to close
			// it with </ul> tag
			if(itemize)
			{
			%>
				</ul>
			<%
			}
			%>
			</div><!-- text -->
		<div class="send_back" style="left: 65%">
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="/iceCreamery/testKitchen/summary.jsp">Close Window</a></div>
			</div><!-- send_back_pair -->
		</div><!-- send_back -->
	
	</div><!-- contentDisplayArea -->
</body>
</html>