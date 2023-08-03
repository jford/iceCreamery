<!DOCTYPE html>
<%@ page import="com.electraink.iceCreamery.testKitchen.datastore.*,
                 com.electraink.iceCreamery.testKitchen.datastore.imageCtl.*,
                 com.electraink.iceCreamery.utilities.*,
                 java.util.*" %>

<%@ include file="../jsp_fragments/xmlEditor.jspf"%>
 <html>
	<head>
		<title>Ice Creamery Recipe Input Form</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<script type="text/javascript" src="../scripts/xmlEditor.js"></script>
	</head>
	<body>
	<div class="contentDisplayArea">
		<div class="page_title">
			XML Editor v2
		</div> <!-- page_title -->
		<%@ include file="../page_components/vMsgBox.jspf" %>
		<div class="text">
			<p class="section_head">
			Edit what?
			</p>
		<form name="radio_test" 
		      action="../editXml" 
		      method="post">
				<input type="radio" 
				       name="editScope" 
				       id="allRcpsScope" 
				       value="<%= allRcpsScope %>" 
				       onclick="enableGetTextBtn();" 
				       <%= allRcpsCkd %>>
				       <label for="allRcpsScope">All Recipes</label><br/>
<!-- 				       onclick="setSlctdScp('<%= allRcpsScope %>');enableGetTextBtn();" --> 
				<input type="radio" 
				       name="editScope" 
				       id="editScopeRcpByNm" 
				       value="<%= rcpByNmScope %>" 
				       onclick="displayMenu('recipe');enableGetTextBtn();" 
				       <%= rcpByNmCkd %>>
				       <label for="editscopeRcpByNm">Recipe by name</label>
                       <%= rcpSlctMnu %><br/>
				<input type="radio" 
				       name="editScope" 
				       id="allIngsScope" 
				       value="<%= allIngsScope %>" 
				       onclick="enableGetTextBtn();" <%= allIngsCkd %>>
				       <label for="allIngsScope">All Ingredients</label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="editScopeIngByNm" 
				       value="<%= ingByNmScope %>" 
				       onclick="displayMenu('ingredient');enableGetTextBtn();" <%= ingByNmCkd %>>
				       <label for="editScopeIngByNm">Ingredient by name</label>
                       <%= ingSlctMnu %>
				<p id="editScopesIntroText">
				...or one of the following ingredient groups<a href="/iceCreamery/editors/xmlEditor.jsp#enumerations">*</a>:
				</p><!-- end editScopesIntroText p -->
				<input type="radio" 
				       name="editScope" 
				       id="<%= allDryScope %>" 
				       value="dairyTypes" 
				       onclick="enableGetTextBtn();" <%= dryCkd %>>
				       <label for="<%= allDryScope %>">Dairy types </label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="<%= allEmulsScope %>" 
				       value="emulsifiers" 
				       onclick="enableGetTextBtn();" <%= emulsCkd %>>
				       <label for="<%= allEmulsScope %>">Emulsifiers</label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="<%= allFlvsScope %>" 
				       value="flavorTypes" 
				       onclick="enableGetTextBtn();" <%= flvsCkd %>>
				       <label for="<%=  allFlvsScope %>">Flavorings </label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="<%= allSaltsScope %>" 
				       value="salts" 
				       onclick="enableGetTextBtn();" <%= saltsCkd %>>
				       <label for="<%= allSaltsScope %>">Salts </label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="<%= allStabsScope %>" 
				       value="stabilizers" 
				       onclick="enableGetTextBtn();" <%= stabsCkd %>>
				       <label for="<%= allStabsScope %>">Stabilizers </label><br/>
				<input type="radio" 
				       name="editScope" 
				       id="<%= allSwtsScope %>" 
				       value="sweeteners" 
				       onclick="enableGetTextBtn()" <%= swtsCkd %>>
				       <label for="<%= allSwtsScope %>">Sweeteners </label><br/>
				<br/>
				<input type="submit" 
				       name="formSbmtBtn" 
				       id="formSbmtBtn" 
				       value="<%= getText %>" 
				       onclick="getScope()"
				       disabled/>
				<input type="hidden"
				       name="edScpSlctd"
				       id="edScpSlctd"
				       value="" />
				       
				<p>
				<textarea name="editTxt" 
				          id="editTxt" 
				          rows="25" 
				          cols="100" 
				          oninput="enableSaveSelection()"><%= editTxt %></textarea>
				</p>
		</form>
			<p>
				<button name="clearFormBtn" 
				        onclick="document.location = 'xmlEditor.jsp'"><%= clrForm %></button>
			</p>
			<a id="enumerations"><b>* Ingredient Groups</b></a>
			<p>
			Ingredient groups (also called types or categories) identify different data type collections. 
			Adding to an existing group is easy.
			</p>
			<p>
			Adding a new ingredient type is not so straight forward. There is a significant amount of 
			programming required&mdash;the new group needs to be added to the input form, for example, and code 
			needs to be added to the parser and data management process&mdash;to support a new data type 
			collection.  
			</p>
			<p>
			It is much easier to squeeze a new ingredient into an existing group, even if the fit is not exact.  
			Nuts, for example, could be added to the flavor group. Or, make the nuts an option 
			which is defined within either the instructions or comments narrative within the recipe definition.
			</p>
		</div><!--  text -->
	</div><!-- contentDisplayArea -->
	<div class="send_back">
		<%@ include file="../page_components/send_back_links/goToLabel.jspf" %>
		<%@ include file="../page_components/send_back_links/ingEditor.jspf" %>
		<%@ include file="../page_components/send_back_links/recipeInputForm.jspf" %>
		<%@ include file="../page_components/send_back_links/recipeSelect.jspf" %>
		<%@ include file="../page_components/send_back_links/admin.jspf" %>
	</div><!--  send_back -->
			
	</body>
</html>