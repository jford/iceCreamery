<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   
 <html>
	<head>
		<title>Ice Creamery Recipe Test Kitchen</title>
		<script type="text/javascript" src="scripts/base_page_header.js"></script>
		<%
		String vMsg = request.getParameter("vMsg");
		vMsg = vMsg == null || vMsg.equals("null") ? "" : vMsg; 
		%>
	</head>
	<%
	if(vMsg.isEmpty())
	{
	%>
		<body>
	<%
	}
	else
	{
	%>
		<body onload="toggleVMsgDisplay()"/>
	<%
	}
	%>
	<div class="contentDisplayArea">
		<div class="page_title" style="width: 75%; text-align: left;">
		Test Kitchen Admin Index
		</div> <!-- page_title -->
		<div class="vMsgBox" id="vMsgBox">
			<textarea class="vMsgText"><%= vMsg %></textarea>
		</div>
		<div class="text">
			<p>
			The test kitchen admin pages are where recipes are created, tested, evaluated, 
			and catalogued.
			</p>
			<select name="testKitchenSelector" onChange="window.document.location.href=this.options[this.selectedIndex].value;">
			    <option>Select action...</option>
				<option value="forms/image_upload_form.jsp">Image Upload</option>
				<option value="forms/recipe_input_form.jsp">Recipe Editor</option>
				<option value="forms/recipeSelect.jsp">Recipe Selector</option>
				<option value="testKitchen/summary.jsp">Recipe Summary</option>
				<option value="testKitchen/xmlEditor.jsp">Xml Editor</option>
			</select>
		</div>
	</div> <!-- contentDisplayArea -->
	
	<div class="send_back">
		<div class="send_back_pair">
			<div class="send_back_label">Go to </div>
		<div class ="send_back_pair">
			<div class="send_back_label"></div>
			<div class="send_back_target"><a href="/iceCreamery">Introduction</a></div>
		</div> <!--  send_back_pair -->
		<div class="send_back_pair">
			<div class="send_back_label"></div>
			<div class="send_back_target"><a href="${pageContext.request.contextPath}/logout">Log out</a></div>
		</div> <!--  send_back_pair -->
		
	</div> <!-- send_back -->
	
	</body>
</html>

