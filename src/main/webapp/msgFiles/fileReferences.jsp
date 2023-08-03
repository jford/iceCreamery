<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.electraink.iceCreamery.utilities.Utilities,com.electraink.iceCreamery.testKitchen.datastore.imageCtl.*,java.util.*"%>
 <html>
	<head>
		<title>Recipe Input Message</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
<%
	String deleteFilename = request.getParameter("deleteFilename");
	ImageMgr imgMgr = new ImageMgr();
	Vector<String> recipeList = imgMgr.getImageUsage(deleteFilename);
%>		
	</head>
	<body>
	<div class="contentDisplayArea">
		<div class="page_title" style="margin-top: 10%">Deleted File Usage</div><!-- page_title -->
		<div class="text">
			<p>The deleted file <i><%= deleteFilename %></i> is still referenced in 
			<%
			if(recipeList.size() == 1)
			{
			%>
				the recipe <i><%= recipeList.firstElement() %></i> and should be removed.</p>
			<%
			}
			else
			{
			%>
				the following files:</p>
				<ul>
			<%
				Iterator<String> iRecipes = recipeList.iterator();
				while(iRecipes.hasNext())
				{
					String recipeName = (String)iRecipes.next();
					%>
					<li><%= recipeName %></li>
					<%
				}
				%>
				</ul>
				<%
			}
			%>
			<div class="text_note">
			To remove image references from a recipe, use the Edit Image controls 
			on the Recipe Editor page. 
			</div>
			<p>
			<button type="button" name="refreshRefList" id="refreshRefList" onclick="javascript:window.location.reload">Refresh List</button>
			</p>
		</div><!-- text -->
	</div> <!-- contentDisplayArea -->
	</body>
</html>
