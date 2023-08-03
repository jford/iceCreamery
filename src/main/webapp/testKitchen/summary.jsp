<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.lang.Integer,java.util.*,com.electraink.iceCreamery.testKitchen.datastore.*,com.electraink.iceCreamery.utilities.*"%>

<%
	DataMgr dataMgr = new DataMgr();
	RecipeMgr recipeMgr = new RecipeMgr();
%>
	<%
	int highScore = recipeMgr.getHighScore();
	int lowScore = recipeMgr.getLowScore();
	String scoreRpt = "";
	if(recipeMgr.getNumRecipes() == 1)
	{
		scoreRpt = "Recipe ";
		scoreRpt += lowScore > 0 ? "score is " + Integer.toString(lowScore) : "has no score";
		scoreRpt += ".";
	}
	else if(highScore == lowScore && highScore > 0)
		scoreRpt = "Highest and lowest scores are equal, at " + Integer.toString(highScore) + ".";
	else
		scoreRpt = "Scores range from " + Integer.toString(lowScore) + " to " + Integer.toString(highScore) + " (on a scale of 1:10).";
	
	// total number of recipes
	int numRecipesInDB = recipeMgr.getNumRecipes();
	
	// num recipe counter (will vary according to what's being done)
	int numRecipes = 0;
	
	Vector<String> recipesWithCookMethods = new Vector<String>();
	Vector<String> recipesWithFlavorings = new Vector<String>();
	Vector<String> recipesWithDairy = new Vector<String>();
	Vector<String> recipesWithNoEggYolkEmuls = new Vector<String>();
	Vector<String> recipesWithNoHeavyCream = new Vector<String>();
	Vector<String> recipesWithNoLightCream = new Vector<String>();
	Vector<String> recipesWithNoCream = new Vector<String>();
	Vector<String> recipesWithSalt = new Vector<String>();
	Vector<String> recipesWithEmulsifiers = new Vector<String>();
	Vector<String> recipesWithStabilizers = new Vector<String>();
	Vector<String> recipesWithSweeteners = new Vector<String>();
	
	Iterator<String> iRecipes;
	String outputText = "";
	
	int count = 0;

	String[][] cookMethodNames = { {},{} };
	String[][] emulsNames = { {},{} };
	String[][] flavorNames = { {},{} };
	String[][] dairyNames = { {},{} };
	String[][] saltsNames = { {},{} };
	String[][] stabsNames = { {},{} };
	String[][] sweetsNames = { {},{} };
	
	for(count = 0; count < flavorNames.length; count++)
	{
		String flavoringsText = "";
// 		numRecipes = recipeMgr.getNumRecipesWithFlavoring(flavorNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("flavorType", flavorNames[count][0]);
		if(numRecipes > 0)
		{
			flavoringsText = Integer.toString(numRecipes);
			flavoringsText += numRecipes > 1 ? " recipes contain " : " recipe contains ";
//			flavoringsText += "the flavoring " + dataMgr.getFlavorTypeName(flavorNames[count][0]) + ".";
			recipesWithFlavorings.add(flavoringsText.toLowerCase());
		}
	}
	for(count = 0; count < dairyNames.length; count++)
	{
		String dairyText = ""; 
//		numRecipes = recipeMgr.getNumRecipesWithDairy(dairyNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("dairy", dairyNames[count][0]);
		if(numRecipes > 0)
		{
			dairyText = Integer.toString(numRecipes);
			dairyText += numRecipes > 1 ? " recipes contain " : " recipe contains ";
//			dairyText += dataMgr.getDairyTypeName(dairyNames[count][0]) + ".";
			recipesWithDairy.add(dairyText.toLowerCase());
		}
		recipesWithNoHeavyCream = recipeMgr.getNamesRecsNoXEnum("dairy", "HEAVY_CREAM");
		recipesWithNoLightCream = recipeMgr.getNamesRecsNoXEnum("dairy", "LIGHT_CREAM");
		recipesWithNoCream = recipeMgr.getNamesRecsNoXEnum("dairy", "CREAM"); 
// 		recipesWithNoHeavyCream = recipeMgr.getRecNamesByNoDairy("HEAVY_CREAM");
// 		recipesWithNoLightCream = recipeMgr.getRecNamesByNoDairy("LIGHT_CREAM");
// 		recipesWithNoCream = recipeMgr.getRecNamesByNoDairy("CREAM");
	}
	
//	numRecipes = recipeMgr.getNumRecipesWithNoSalt();
	numRecipes = recipeMgr.getNumRecsWithNoXEnumType("salt");
	
	if(numRecipes > 0);
	{
		String saltText = "<a href=\"javascript: displayDetails('../../details/saltFreeRecipes.jsp')\">";
		saltText += Integer.toString(numRecipes);
		saltText += " recipes";
		saltText += "</a>";
		saltText += " contain no salt.";
		recipesWithSalt.add(saltText);
	}
	for(count = 0; count < saltsNames.length; count++)
	{
		String saltText = ""; 

//		numRecipes = recipeMgr.getNumRecipesWithSalt(saltsNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("salt", saltsNames[count][0]);
		if(numRecipes > 0)
		{
			saltText = Integer.toString(numRecipes);
			saltText += numRecipes > 1 ? " recipes specify \"" : " recipe specifies \"";
//			saltText += dataMgr.getSaltName(saltsNames[count][0]) + "\".";
			recipesWithSalt.add(saltText.toLowerCase());
		}
	}

	for(count = 0; count < emulsNames.length; count++)
	{
		String emulsText = "";
//		numRecipes = recipeMgr.getNumRecipesWithEmulsifier(emulsNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("emulsifier", emulsNames[count][0]);
		if(numRecipes > 0)
		{
			emulsText = Integer.toString(numRecipes);
			emulsText += numRecipes > 1 ? " recipes contain " : " recipe contains ";
//			emulsText += dataMgr.getEmulsifierName(emulsNames[count][0]) + ".";
			recipesWithEmulsifiers.add(emulsText.toLowerCase());
		}
		recipesWithNoEggYolkEmuls = recipeMgr.getNamesRecsNoXEnum("emulsifier", "EGG_YOLK");
//		recipesWithNoEggYolkEmuls = recipeMgr.getRecNamesByNoEmul("EGG_YOLK");
	}
	
	for(count = 0; count < sweetsNames.length; count++)
	{
		String sweetsText = "";
//		numRecipes = recipeMgr.getNumRecipesWithSweetener(sweetsNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("sweetener", sweetsNames[count][0]);
		if(numRecipes > 0)
		{
			sweetsText = Integer.toString(numRecipes);
			sweetsText += numRecipes > 1 ? " recipes contain " : " recipe contains ";
//			sweetsText += dataMgr.getSweetenerName(sweetsNames[count][0]) + ".";
			recipesWithSweeteners.add(sweetsText.toLowerCase());
		}
	}
	
	for(count = 0; count < stabsNames.length; count++)
	{
		String stabsText = "";
//		numRecipes = recipeMgr.getNumRecipesWithStabilizer(stabsNames[count][0]);
		numRecipes = recipeMgr.getNumRecsWithEnum("stabilizer", stabsNames[count][0]);
		if(numRecipes > 0)
		{
			stabsText = Integer.toString(numRecipes);
			stabsText += numRecipes > 1 ? " recipes contain " : " recipe contains ";
//			stabsText += dataMgr.getStabilizerName(stabsNames[count][0]) + ".";
			recipesWithStabilizers.add(stabsText.toLowerCase());
		}
	}

	for(count = 0; count < cookMethodNames.length; count++)
	{
		String cookMethodEnum = cookMethodNames[count][0];
//		numRecipes = recipeMgr.getNumRecipesWithCookMethod(cookMethodEnum);
		numRecipes = recipeMgr.getNumRecsWithEnum("cookMethod", cookMethodEnum);
		if(numRecipes > 0)
		{
			String cookMethodsText = Integer.toString(numRecipes);
			if(cookMethodEnum.equals("NONE"))
			{
				cookMethodsText += numRecipes > 1 ? " recipes explicitly specify" : " recipe explicitly specifies ";
				cookMethodsText += "not cooking the base.";
			}
			else
			{
				cookMethodsText += numRecipes > 1 ? " recipes specify " : " recipe specifies ";
				cookMethodsText += " cooking the base by ";
//				cookMethodsText += dataMgr.getCookMethodName(cookMethodEnum) + ".";
			}
			recipesWithCookMethods.add(cookMethodsText.toLowerCase());
		}
	}
	%>
	
<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
			<meta name="description" content="How I Learned to Make Ice Cream."/>
			<meta name="robots" content="noindex, nofollow"/>
			<title>The Ice Creamery: Recipe Summary</title>
			<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		</head>
		<body>
<%
if(numRecipes == 0)
{
%>
		<div class="contentDisplayArea">
			<div class="text">
			<div class="page_title" style="text-align: center">
			Test Kitchen
			</div><!-- page_title -->
			<div class="page_subtitle" style="text-align: center; font-style:italic">
			 Recipe Summary
			 </div><!-- page_subtitle -->

			<p>
			Sorry, no recipes in the database yet.
			</p>
			</div>
		</div><!-- contentDisplayArea if recipeNames.isEmtpy() -->
<%
}
else
{
%>
		<div class="contentDisplayArea">
			<div class="text">		
				<div class="page_title" style="text-align: center">
				Test Kitchen
				</div><!-- page_title -->
				<div class="page_subtitle" style="text-align: center; font-style:italic">
				 Recipe Summary
				 </div><!-- page_subtitle -->
				 
				<p>
				The Ice Creamery database currently contains <%= Integer.toString(numRecipesInDB) %> 
				<%= numRecipesInDB == 1 ? "recipe" : "recipes" %>. 
				</p>
				<p>
				<%= scoreRpt %>
				</p>
				Recipe Counts by:
				<ul>
					<li><a href="./summary.jsp#cookmethod">Cook Method</a></li>
					<li><a href="./summary.jsp#dairy">Dairy</a></li>
					<li><a href="./summary.jsp#salt"=>Salt</a></li>
					<li><a href="./summary.jsp#emulsifier">Emulsifier</a></li>
					<li><a href="./summary.jsp#flavoring">Flavoring</a></li>
					<li><a href="./summary.jsp#stabilizer">Stabilizer</a></li>
					<li><a href="./summary.jsp#sweetener">Sweetener</a></li>
				</ul>
				
<!-- recipes by cook method -->
					
				<p class="section_head">
				Recipe Count by Cook Method<a name="cookmethod"></a>
				</p>
				<ul>
				<%
				if(recipesWithCookMethods.size() > 0)
				{
					iRecipes = recipesWithCookMethods.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // end while(iRecipes.hasNet())
				} // end if(recipesWiehtCookMethods.size() > 0)
				%>
				</ul>
					

<!-- recipes by dairy -->

				<p class="section_head">
				Recipe Count by Dairy Type<a name="dairy"></a>
				</p>
				<ul>
				<%
				if(recipesWithDairy.size() > 0)
				{
					iRecipes = recipesWithDairy.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // while(iRecipes.hasNet())
				} // if(recipesWithDairy.size() > 0)
				int numRecipesWithNoDairy = recipeMgr.getNumRecsWithNoXEnumType("dairy");
				if(numRecipesWithNoDairy > 0)
				{
					String noDairyText =  Integer.toString(numRecipesWithNoDairy);
					noDairyText += numRecipesWithNoDairy > 1 ? " recipes have " : " recipe has ";
					noDairyText += "no dairy.";
					%>
					<li><%= noDairyText %></li>
					<%
					
// 					recipesWithNoHeavyCream = recipeMgr.getRecNamesByNoDairy("HEAVY_CREAM");
// 					recipesWithNoLightCream = recipeMgr.getRecNamesByNoDairy("LIGHT_CREAM");
// 					recipesWithNoCream = recipeMgr.getRecNamesByNoDairy("CREAM");
					
					int numNoCream = recipesWithNoHeavyCream.size();
					noDairyText = "<a href=\"javascript: displayDetails('../../details/creamFreeRecipes.jsp')\">";
					noDairyText += Integer.toString(numNoCream);
					noDairyText += numNoCream > 1 ? " recipes</a> have " : " recipe</a> has ";
					noDairyText += " no heavy Cream.";
						%>
						<li><%= noDairyText %></li>
						<%
					numNoCream = recipesWithNoLightCream.size();
					noDairyText = "<a href=\"javascript: displayDetails('../../details/creamFreeRecipes.jsp')\">";
					noDairyText += Integer.toString(numNoCream);
					noDairyText += numNoCream > 1 ? " recipes<a> have " : " recipe</a> has ";
					noDairyText += " no light cream.";
						%>
						<li><%= noDairyText %></li>
						<%
					numNoCream = recipesWithNoCream.size();
					noDairyText = "<a href=\"javascript: displayDetails('../../details/creamFreeRecipes.jsp')\">";
					noDairyText += Integer.toString(numNoCream);
					noDairyText += numNoCream > 1 ? " recipes</a> have " : " recipe</a> has ";
					noDairyText += " no cream.";
						%>
						<li><%= noDairyText %></li>
						<%
				} // end if(numRecipesWithNoDairy > 0)
				%>
				</ul>

<!-- recipes by salt -->

				<p class="section_head">
				Recipe Count by Salt<a name="salt"></a>
				</p>
				<ul>
				<%
				if(recipesWithSalt.size() > 0)
				{
					iRecipes = recipesWithSalt.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // while(iRecipes.hasNet())
				} // if(recipesWithSalt.size() > 0)
				%>
				</ul>

<!-- recipes by emulsifier -->

				<p class="section_head">
				Recipe Count by Emulsifier<a name="emulsifier"></a>
				</p>
				<ul>
				<%
				if(recipesWithEmulsifiers.size() > 0)
				{
					iRecipes = recipesWithEmulsifiers.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // while(iRecipes.hasNet())
				} // if(recipesWithEmulsifiers.size() > 0)

				// get number of recipes that don't specify egg yolks as emulsifiers
				int numRecipesWithNoEmuls = recipeMgr.getRecNamesByNoEmul("EGG_YOLK").size();
				if(numRecipesWithNoEmuls > 0)
				{
					String noEmulsText = "<a href=\"javascript: displayDetails('../../details/eggFreeRecipes.jsp')\">";
					noEmulsText += Integer.toString(numRecipesWithNoEmuls);
					noEmulsText += numRecipesWithNoEmuls > 1 ? " recipes</a> have " : " recipe</a> has ";
					noEmulsText += "no egg yolks as emulsifiers.";
					%>
					<li><%= noEmulsText %></li>
					<%
				} // end "no egg yolks as emulsifiers"
				// get number of recipes with no emulsifiers of any king
				numRecipesWithNoEmuls = recipeMgr.getNumRecsWithNoXEnumType("emulsifier");
				if(numRecipesWithNoEmuls > 0)
				{
					String noEmulsText = Integer.toString(numRecipesWithNoEmuls);
					noEmulsText += numRecipesWithNoEmuls > 1 ? " recipes have " : " recipe has ";
					noEmulsText += "no emulsifiers.";
					%>
					<li><%= noEmulsText %></li>
					<%
				} // end if(numRecipesWithNoFlavorings > 0)
				%>
				</ul>
				
<!-- recipes by flavoring -->

				<p class="section_head">
				Recipe Count by Flavoring<a name="flavoring"></a>
				</p>
				<ul>
				<%
				if(recipesWithFlavorings.size() > 0)
				{
					iRecipes = recipesWithFlavorings.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // while(iRecipes.hasNet())
				} // end if(recipesWithFlavors.size() > 0)
				int numRecipesWithNoFlavorings = recipeMgr.getNumRecsWithNoXEnumType("flavorType");
				if(numRecipesWithNoFlavorings > 0)
				{
					String noFlavoringsText = Integer.toString(numRecipesWithNoFlavorings);
					noFlavoringsText += numRecipesWithNoFlavorings > 1 ? " recipes have " : " recipe has ";
					noFlavoringsText += "no flavorings.";
					%>
					<li><%= noFlavoringsText %></li>
					<%
				} // end if(numRecipesWithNoFlavorings > 0)
				%>
				</ul>

<!-- recipes by stabilizer -->

				<p class="section_head">
				Recipe Count by Stabilizer<a name="stabilizer"></a>
				</p>
				<ul>
				<%
				if(recipesWithStabilizers.size() > 0)
				{
					iRecipes = recipesWithStabilizers.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
					%>
						<li><%= outputText %></li>
					<%
					} // while(iRecipes.hasNext())
				} // end if(recipesWithStabilizers.size() > 0)
				int numRecipesWithNoStabs = recipeMgr.getNumRecsWithNoXEnumType("stabilizer");
				if(numRecipesWithNoStabs > 0)
				{
					String noStabsText = Integer.toString(numRecipesWithNoStabs);
					noStabsText += numRecipesWithNoStabs > 1 ? " recipes have " : " recipe has ";
					noStabsText += "no stabilizers,";
					%>
					<li><%= noStabsText %></li>
					<%
				} // end if(numRecipesWithNoStabs > 0)
				%>
				</ul>
					
<!-- recipes by sweetener -->

				<p class="section_head">
				Recipe Count by Sweetener<a name="sweetener"></a>
				</p>
				<ul>
				<%
				if(recipesWithSweeteners.size() > 0)
				{
					iRecipes = recipesWithSweeteners.iterator();
					while(iRecipes.hasNext())
					{
						outputText = (String)iRecipes.next();
						%>
							<li><%= outputText %></li>
						<%
					} // end while(iRecipes.hasNet())
				} // end if(recipesWithSweeteners.size() > 0)
				int numRecipesWithNoSweets = recipeMgr.getNumRecsWithNoXEnumType("sweetener");
				if(numRecipesWithNoSweets > 0)
				{
					String noSweetsText = Integer.toString(numRecipesWithNoSweets);
					noSweetsText += numRecipesWithNoSweets > 1 ? " recipes have " : " recipe has ";
					noSweetsText += "no sweetemers.";
					%>
					<li><%= noSweetsText %></li>
					<%
				} // end if(numRecipesWithNoStabs > 0)
				%>
				</ul>
					
			</div><!-- text -->
		</div> <!-- contentDisplayArea if !recipeNames.isEmpty()-->
		
<%
} // if(numRecipes = 0){} else
%>

		<div class="send_back">
			<%@ include file="../page_components/send_back_links/goToLabel.jspf" %>
			<%@ include file="../page_components/send_back_links/admin.jspf" %>
		</div><!--  send_back -->
			
		</body>
</html>