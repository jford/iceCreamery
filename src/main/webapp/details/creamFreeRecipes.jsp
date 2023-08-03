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
		<title>Ice Creamery: Cream Free Recipes</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
	</head>
</head>
<body>
	<div class="contentDisplayArea">
		<div class="page_title">
		Cream Free Recipes
		</div><!-- page_title -->
			<div class="text">
			<%
			RecipeMgr recipeMgr = new RecipeMgr();
			Vector<String> recipeNames = recipeMgr.getNamesRecsWithNoEnumType("dairy");
			int listSize = recipeNames.size();
			String listSizeStr = Integer.toString(listSize);
			Iterator<String> iRecNames = recipeNames.iterator();
			String recName;
			String numRecText = listSizeStr + " ";
			numRecText = recipeNames.size() > 1 ? numRecText + "recipes do ": numRecText + "recipe does ";
			if(recipeNames.size() > 0)
			{
			%>
				<p>The following <%= numRecText %> not specifiy any dairy ingredients:</p>
				<ul>
			<%
				while(iRecNames.hasNext())
				{
					recName = iRecNames.next();
					%>
						<li><%= recName %></li>
					<%
				} 
			%>
				</ul></p>
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