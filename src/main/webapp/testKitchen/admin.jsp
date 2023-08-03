<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.electraink.iceCreamery.testKitchen.datastore.*,
                 com.electraink.iceCreamery.utilities.*,
                 java.util.*" %>
<html>
<head>
	<title>Ice Creamery Admin</title>
	<script type="text/javascript" src="../scripts/base_page_header.js"></script>
	<script type="text/javascript" src="../scripts/admin.js"></script>
	<link rel='stylesheet' type='text/css' href='../styles/admin.css'/> 
</head>
<%@ include file="../jsp_fragments/admin.jspf" %>
<body onload="toggleVMsgDisplay()"/>
	<div class="contentDisplayArea">
		<div class="page_title" style="width: 75%; text-align: left;">
		Test Kitchen Admin Index
		</div> <!-- page_title -->
		<div class="text">
			<p>
			The test kitchen admin pages are where recipes are created, tested, evaluated, 
			and catalogued.
			</p>
			<select name="testKitchenSelector" onChange="window.document.location.href=this.options[this.selectedIndex].value;">
			    <option>Select action...</option>
				<option value="../forms/recipe_input_form.jsp">Recipe Editor</option>
				<optgroup label="Ingredients Editor for...">
					<option value="../editors/ingEditor.jsp?ingType=flavoring&ingCategory=Flavoring(s)">Flavoring(s)</option>
					<option value="../editors/ingEditor.jsp?ingType=dairytype&ingCategory=Dairy">Dairy</option>
					<option value="../editors/ingEditor.jsp?ingType=emulsifier&ingCategory=Emulsifiers">Emulsifiers</option>
					<option value="../editors/ingEditor.jsp?ingType=salt&ingCategory=Salts">Salts</option>
					<option value="../editors/ingEditor.jsp?ingType=stabilizer&ingCategory=Dairy">Stabilizers</option>
					<option value="../editors/ingEditor.jsp?ingType=sweetener&ingCategory=Dairy">Sweeteners</option>
				</optgroup>
				<option value="../forms/image_upload_form.jsp">Image Upload</option>
				<option value="../forms/recipeSelect.jsp">Recipe Selector</option>
				<option value="../testKitchen/summary.jsp">Recipe Summary</option>
				<option value="../editors/xmlEditor.jsp">Xml Editor</option>
				<option value="../editors/xmlEditor2.jsp">Xml Editor 2</option>
			</select>
		</div>
	</div> <!-- contentDisplayArea -->
	
	<div class="send_back">
		<%@ include file="../page_components/send_back_links/index.jspf"  %>
		<%@ include file="../page_components/send_back_links/ingEditor.jspf"  %>
		<%@ include file="../page_components/send_back_links/recipeInputForm.jspf"  %>
		<%@ include file="../page_components/send_back_links/recipeSelect.jspf" %>
		<%@ include file="../page_components/send_back_links/xmlEditor.jspf"  %>
	</div> <!--  send_back_pair -->
		
	</div> <!-- send_back -->
	
	</body>
</html>

