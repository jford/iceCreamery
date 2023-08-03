<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.lang.Integer,
                java.util.*,
                com.electraink.iceCreamery.testKitchen.datastore.*,
                com.electraink.iceCreamery.utilities.*"%>
<% 
	DataMgr dataMgr = new DataMgr();
	RecipeMgr recipeMgr = new RecipeMgr();
%>
<%@ include file="../jsp_fragments/ingredients.jspf" %>
<%@ include file="../jsp_fragments/recipe_select.jspf" %>
	
<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
			<meta name="description" content="How I Learned to Make Ice Cream."/>
			<meta name="robots" content="noindex, nofollow"/>
			<title>The Ice Creamery: Recipe Selector</title>
			<script type="text/javascript" src="../scripts/base_page_header.js"></script>
			<script type="text/javascript" src="../scripts/recipe_select.js"></script>
			<link rel='stylesheet' type='text/css' href='../styles/recipe_select.css'/> 
		</head>
		<body>
		<div class="contentDisplayArea">
			<%@ include file="../page_components/vMsgBox.jspf" %>
			<div class="text">
				<div class="page_title" style="text-align: center">
				Test Kitchen
				</div><!-- page_title -->
				<div class="page_subtitle" style="text-align: center; font-style:italic">
				 Recipe Selector
				 </div><!-- page_subtitle -->
				
				<form name="recipeSelectorForm" action="../recipeList" method="post">
				
					<p>
					Select recipe by name:
					</p>
					<div class="text_note">
						<p class="note_text">
							Menu contains all recipes unless filtered first.
						</p>
					</div><!-- end text_note div -->
					
					<%= rcpSlctMnu %>
					<div class="filterBox">
						<p class="section_subhead">Selection Menu Filters</p>
						<p class="note_text">
						Check boxes and enter required data for all filters to be used for populating the selection menu, 
						then click the <i>Apply Filters</i> button. The menu will be revised to 
						show only those recipes that meet all filter conditions.
						</p><!-- note_text -->
						
			<table class="recipeInputForm"><!--  form table -->
				<tr>
					<td class="dataCategory">Dominant Flavor</td>
				</tr>
				<tr>
					<td class="dataItemInput" colspan="3"><input type="text" name="domFlavor" id="domFlavor" size="58"/></td>
				</tr><!--  end dominant flavor -->
				<tr>
					<td class="dataCategory">Score <span style="font-weight: normal; text-align: right">
							<input type="radio" name="score_equality" id="score_equality" value="equals" checked="checked"/>equals  / 
							<input type="radio" name="score_equality" id="score_equality" value="less"/> is less than / 
							<input type="radio" name="score_equality" id="score_equality" value="more"/> is greater than 
							</span> <input type="text" name="score" id="score" size="3"/>
							
					</td> 
				</tr>
				<tr style="padding-top: 0">
					<td colspan="3" style="font-size: .8em">
					Valid range is 0:10; 0 means recipe has not been rated and has no score. Filter greater than 10 or less than 0 is meaningless.
					</td>
				</tr><!-- end score -->
				<tr>
					<td class="dataCategory">Flavoring(s)</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= flavsSlctTable %>
					</td>
				</tr><!--  end flavorings -->
				
<!-- dairy -->

				<tr>
					<td class="dataCategory">Dairy</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= dairySlctTable %>
					</td>
				</tr><!--  end dairy -->


<!-- emulsifiers -->

				<tr>
					<td class="dataCategory">Emulsifier(s)</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= emulsSlctTable %>
					</td>
				</tr><!--  end emulsifiers -->

<!-- salts -->

				<tr>
					<td class="dataCategory">Salt(s)</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= saltsSlctTable %>
					</td>
				</tr><!--  end salts -->

<!-- stabilizers -->

				<tr>
					<td class="dataCategory">Stabilizer(s)</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= stabsSlctTable %>
					</td>
				</tr><!--  end stabilizers -->

<!-- sweeteners -->

				<tr>
					<td class="dataCategory">Sweetener(s)</td>
				</tr>
				<tr>
					<td class=dataItemInput>
						<%= swtsSlctTable %>
					</td>
				</tr><!--  end  sweeteners -->
				
<!-- cook method -->

				<tr>
					<td colspan="4">Cook method:
					<select name="cookmethod" id="cookmethod" onchange="cookmethod.options[selectedIndex].value">
						<!-- <option value="<%= cookMethodNames[0][0]%>" selected>(Select)</</option> -->
						<option value="">(Select)</option>
					<%
					count = 0;
					while(count < numCookMethods)
					{
//						if(cookMethodNames[count][0].compareTo("NONE") != 0)
//						{
					%>
						<option value="<%= cookMethodNames[count][0] %>"><%= cookMethodNames[count][1] %></option>
					<%
//						}
						count++;
					}
					%>
					</select>
					</td>
				</tr><!-- end cook method -->
				<tr>
					<td colspan="4" style="text-align: right; padding-bottom: 3%">
						<input type="submit" name="submitEditFilters" id="submitEditFilters" value="Apply Filters"/>
					</td>
				</tr>
				</table>
				
				<p style="background-color: red; color: white; text-align: center;">-or-</p>
				
						<div class="filterInputsDiv">
							<p class="section_subhead">Selection Menu Filters</p>

				<p class="sub_head">Dominant Flavor</p>
				<input type="text" name="domFlavor" id="domFlavor" size="58"/>
				
				<p class="sub_head">
					Score 
					<input type="radio" name="score_equality" id="score_equality" value="equals" checked="checked"/>equals  / 
					<input type="radio" name="score_equality" id="score_equality" value="less"/> is less than / 
					<input type="radio" name="score_equality" id="score_equality" value="more"/> is greater than 
					<input type="text" name="score" id="score" size="3"/>
				</p>

				<div class="text_note">
					<p class="note_text">
					Valid range is 0:10; 0 means recipe has not been rated and has no score. Filter greater than 10 or less than 0 is meaningless.
					</p>
				</div><!-- end text_note div -->

					<p class="sub_head">
					Flavorings
					</p>
					<%= flavsSlctTable %>

					<p class="sub_head">
					Dairy
					</p>
					<%= dairySlctTable %>

					<p class="sub_head">
					Emulsifiers
					</p>
					<%= emulsSlctTable %>

					<p class="sub_head">
					Salts
					</p>
					<%= saltsSlctTable %>

					<p class="sub_head">
					Stabilizers
					</p>
					<%= stabsSlctTable %>

					<p class="sub_head">
					Sweeteners
					</p>
					<%= swtsSlctTable %>
					
						</div><!-- end filterInputsDiv -->	
						
					</div><!-- filterBox -->
				</form><!-- recipeSelectorForm -->
			</div><!-- text -->
		</div> <!-- contentDisplayArea if !recipeNames.isEmpty()-->

		<div class="send_back">
		<%@ include file="../page_components/send_back_links/goToLabel.jspf" %>
		<%@ include file="../page_components/send_back_links/admin.jspf" %>
		</div><!--  send_back -->
			
		</body>
</html>
