<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.lang.Integer,java.util.*,
				 com.electraink.iceCreamery.testKitchen.datastore.*,
				 com.electraink.iceCreamery.testKitchen.datastore.dairy.*,
				 com.electraink.iceCreamery.testKitchen.datastore.emulsifiers.*,
				 com.electraink.iceCreamery.testKitchen.datastore.flavors.*,
				 com.electraink.iceCreamery.testKitchen.datastore.salts.*,
				 com.electraink.iceCreamery.testKitchen.datastore.stabilizers.*,
				 com.electraink.iceCreamery.testKitchen.datastore.sweeteners.*,
				 com.electraink.iceCreamery.utilities.*
				 "%>

<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
			<meta name="description" content="Ice Creamery Recipe"/>
			<meta name="robots" content="noindex, nofollow"/>
			<title>The Ice Creamery: Recipe Selector</title>
			<script type="text/javascript" src="../scripts/base_page_header.js"></script>
			<link rel='stylesheet' type='text/css' href='styles/main_styles.css'/> 
			<link rel='stylesheet' type='text/css' href='styles/iceCreamery_styles.css'/> 
		</head>
		<body>
<%
	String recipeName = request.getParameter("recipeName");
	DataMgr dataMgr = new DataMgr();
	RecipeMgr recipeMgr = new RecipeMgr();
	Recipe recipe = recipeMgr.getRecipe(recipeName);
	
	String imageFilename = recipe.getImageFilename();
	String caption = recipe.getCaption();
	
	String comments = "";
	String cookMethod = "";
	String cookTemp = "";
	String cookTime = "";
	String instructions = "";
	String flavorName = "";
	String score = "";
	
	DairyTypes dairyTypes = null;
	Emulsifiers emulsifiers = null;
	Flavor flavor = null;
	Flavorings flavorings = null;
	Salts salts = null;
	Stabilizers stabilizers = null;
	Sweeteners sweeteners = null;
	
	if(recipe == null)
		recipeName = "No recipe by that name.";
	else
	{
		comments = recipe.getComments();
		instructions = recipe.getInstructions();
		cookMethod = recipe.getCookMethod();
		cookTime = recipe.getCookTime();
		cookTemp = recipe.getCookTemp();
		score = recipe.getScoreStr();
		
		dairyTypes = recipe.getDairyTypes();
		salts = recipe.getSalts();
		emulsifiers = recipe.getEmulsifiers();
		flavor = recipe.getFlavor();
		flavorings = flavor.getFlavorings();
		flavorName = flavor.getName();
		stabilizers = recipe.getStabilizers();
		sweeteners = recipe.getSweeteners();
		
		instructions = instructions.length() > 0 ? Utilities.convertTextToHtml(instructions) : "";
		comments = comments.length() > 0 ? Utilities.convertTextToHtml(comments) : "";		
	}
%>
		<div class="contentDisplayArea">
			<div class="text">		
				<div class="page_title" style="text-align: center">
				<%= recipeName %>
				</div><!-- page_title -->
				
				<%
				if(flavorName.length() > 0)
					flavorName = "Flavor: <span style=\"font-weight: normal\">" + flavorName + "</span> ";
				if(score.length() > 0)
					score = "Score: <span style=\"font-weight: normal\">" + score + "</span> ";
				if(flavorName.length() > 0 || recipe.hasScore())
				{
				%>
					<p class="recipeDisplayGroupName"><%= flavorName %><%= score %></p>

				<%
				}
				if(recipe.hasCookTime() || 
				   recipe.hasCookTemp())
				{
					if(cookMethod.equals("NONE")) 
						cookMethod = "";
					else
						cookMethod = " via " + (dataMgr.getCookMethodName(cookMethod)).toLowerCase();
						
				%>
					<div class="text_note">
					Before chilling, base is cooked for <%= cookTime %> at <%= cookTemp %><%= cookMethod %>.
					</div>
				<%
				}
				if(!imageFilename.isEmpty())
				{
				%>
					<img src="../images/<%= imageFilename %>" alt="<%= imageFilename %>" height="240" width="320"/>
				<%
					if(!caption.isEmpty())
					{
						%>
						<div class="imgCaption">
							<%= caption %>
						</div>
						<%
					}
				}
				if(recipe.hasComments())
				{
				%>
					<!-- comments -->
					<p class="recipeDisplayGroupName">
					Comments
					</p>
					<%= comments %>
				<%
				}
				if(recipe.hasIngredients())
				{
				%>
				
				<p class="recipeDisplayGroupName">Ingredients</p>
				
				<!-- recipe display table -->
				
				<div class="recipeDisplayTable">
				
				<!-- dairy -->
				
				<%
				if(dairyTypes != null)
				{
					Iterator<DairyType> iDairy = dairyTypes.iterator();
					if(iDairy.hasNext())
					{
					%>
							<div class="ingredientCategory">Dairy
					<%
					}
					while(iDairy.hasNext())
					{
						DairyType dairyType = (DairyType)iDairy.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getDairyTypeName(dairyType.getEnum()) %></div>
							<div class="quantity"><%= dairyType.getQuantity() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iDairy.hasNext()
					%>
					</div><!-- ingedientCategory -->
					<%
				} // end if(dairyTypes != null)
				%>
				
				<!-- salts -->
				
				<%
				if(salts != null)
				{
					Iterator<Salt> iSalts = salts.iterator();
					if(iSalts.hasNext())
					{
					%>
							<div class="ingredientCategory">Salts
					<%
					}
					while(iSalts.hasNext())
					{
						Salt salt = (Salt)iSalts.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getSaltName(salt.getEnum()) %></div>
							<div class="quantity"><%=salt.getQuantity() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iSalts.hasNext()
					%>
					</div><!-- ingredientCategory -->
					<%
				} // end if(salts != null)
				%>

				<!-- sweeteners -->
				
				<%
				if(sweeteners != null)
				{
					Iterator<Sweetener> iSweeteners = sweeteners.iterator();
					if(iSweeteners.hasNext())
					{
					%>
							<div class="ingredientCategory">Sweeteners
					<%
					}
					while(iSweeteners.hasNext())
					{
						Sweetener sweetener = (Sweetener)iSweeteners.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getSweetenerName(sweetener.getEnum()) %></div>
							<div class="quantity"><%= sweetener.getQuantity() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iSweeteners.hasNext()
					%>
					</div><!-- ingredientCategory -->
					<%
				} // end if(sweeteners != null)
				%>

				<!-- flavorings -->
				
				<%
				if(flavorings != null)
				{
					Iterator<Flavoring> iFlavorings = flavorings.iterator();
					if(iFlavorings.hasNext())
					{
					%>
							<div class="ingredientCategory">Flavorings
					<%
					}
					while(iFlavorings.hasNext())
					{
						Flavoring flavoring = (Flavoring)iFlavorings.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getFlavorTypeName(flavoring.getEnum()) %></div>
							<div class="quantity"><%= flavoring.getQty() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iFlavorings.hasNext())
					%>
					</div><!-- ingredientCategory -->
					<%
				} // end if(flavorings != null)
				%>

				<!-- emulsifiers -->
				
				<%
				if(emulsifiers != null)
				{
					Iterator<Emulsifier> iEmulsifiers = emulsifiers.iterator();
					if(iEmulsifiers.hasNext())
					{
					%>
							<div class="ingredientCategory">Emulsifiers
					<%
					}
					while(iEmulsifiers.hasNext())
					{
						Emulsifier emulsifier = (Emulsifier)iEmulsifiers.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getEmulsifierName(emulsifier.getEnum()) %></div>
							<div class="quantity"><%= emulsifier.getQuantity() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iEmulsifiers.hasNext())
					%>
					</div><!-- ingredientCategory -->
					<%
				} // end if(emulsifiers != null)
				%>

				<!-- stabilizers -->
				
				<%
				if(stabilizers != null)
				{
					Iterator<Stabilizer> iStabilizers = stabilizers.iterator();
					if(iStabilizers.hasNext())
					{
					%>
							<div class="ingredientCategory">Stabilizers
					<%
					}
					while(iStabilizers.hasNext())
					{
						Stabilizer stabilizer = (Stabilizer)iStabilizers.next();
					%>
						<div class="ingredientListing">
							<div class="ingredient"><%= dataMgr.getStabilizerName(stabilizer.getEnum()) %></div>
							<div class="quantity"><%= stabilizer.getQuantity() %></div>
						</div><!-- ingredientListing -->
					<%
					} // while(iStabilizers.hasNext())
					%>
					</div><!-- ingredientCategory -->
					<%
				} // end if(stabilizers != null)
				%>

				</div><!-- recipeDisplayTable -->
				
				<%
				}
				if(recipe.hasInstructions())
				{
				%>
					<!-- instructions -->
					
					<p class="recipeDisplayGroupName">Instructions</p>
					<%= instructions %>
				<%
				}
				%>
				
			</div><!-- text -->
		</div> <!-- contentDisplayArea -->

		<div class="send_back">
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="javascript:window.close()">Close Recipe Window</a></div>
			</div><!-- send_back_pair -->
		</div><!--  send_back -->
			
		</body>
	</html>
