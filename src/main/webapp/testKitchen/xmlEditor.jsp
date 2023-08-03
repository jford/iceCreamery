<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   
<%@ page import="com.electraink.iceCreamery.testKitchen.datastore.*,com.electraink.iceCreamery.testKitchen.datastore.imageCtl.*,com.electraink.iceCreamery.utilities.*,java.util.*" %>

<%
String enumType = request.getParameter("enumType");
String editTxt = request.getParameter("editTxt");
String vMsgTxt = request.getParameter("vMsgTxt");

enumType = enumType == null ? "" : enumType;
editTxt = editTxt == null ? "" : editTxt;
vMsgTxt = vMsgTxt == null ? "" : vMsgTxt;
if(!editTxt.isEmpty())
{
	try
	{
		editTxt = Utilities.read_file(DataMgr.getPathToXml() + editTxt);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

if(!vMsgTxt.isEmpty())
{
	%>
	<style>div.vMsgBox{ display: block; }</style>
	<%
}
		
%>
 <html>
	<head>
		<title>Ice Creamery Recipe Input Form</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<script type="text/javascript">
		function enableEditSelection()
		{
			document.getElementById("editTxt").value = " ";
			document.getElementById("editSelection").disabled = false;
		}
		function enableSaveSelection()
		{
			document.getElementById("saveSelection").disabled = false;
		}
		</script>
	</head>
	<body>
	<div class="contentDisplayArea">
		<div class="page_title">
			XML Editor
		</div> <!-- page_title -->
		<div class="text">
			<p class="section_head">
			Edit what?
			</p>
			<form name="xmlEditorForm" action="../editXml" method="post">
				<input type="radio" name="enumType" id="enumType" value="all" onclick="enableEditSelection()" <%= enumType.equals("all") ? "checked" : "" %>>All (entire XML)</input><br/>
				<br/>
				...or one of the following enumerations<a href="/iceCreamery/testKitchen/xmlEditor.jsp#enumerations">*</a>:
				<br/><br/>
				<input type="radio" name="enumType" id="enumType" value="cookMethodDefs" onclick="enableEditSelection()" <%= enumType.equals("cookMethodDefs") ? "checked" : "" %>>Cook methods </input><br/>
				<input type="radio" name="enumType" id="enumType" value="dairyTypeDefs" onclick="enableEditSelection()" <%= enumType.equals("dairyTypeDefs") ? "checked" : "" %>>Dairy types </input><br/>
				<input type="radio" name="enumType" id="enumType" value="emulsifierDefs" onclick="enableEditSelection()" <%= enumType.equals("emulsifierDefs") ? "checked" : "" %>>Emulsifiers</input><br/>
				<input type="radio" name="enumType" id="enumType" value="flavorTypeDefs" onclick="enableEditSelection()" <%= enumType.equals("flavorTypeDefs") ? "checked" : "" %>>Flavorings </input><br/>
				<input type="radio" name="enumType" id="enumType" value="saltDefs" onclick="enableEditSelection()" <%= enumType.equals("saltDefs") ? "checked" : "" %>>Salts </input><br/>
				<input type="radio" name="enumType" id="enumType" value="stabilizerDefs" onclick="enableEditSelection()" <%= enumType.equals("stabilizerDefs") ? "checked" : "" %>>Stabilizers </input><br/>
				<input type="radio" name="enumType" id="enumType" value="sweetenerDefs" onclick="enableEditSelection()" <%= enumType.equals("sweetenerDefs") ? "checked" : "" %>>Sweeteners </input><br/>
				<br/>
				<input type="submit" name="editSelection" id="editSelection" value="Get Text" disabled/>
				<p>
					<textarea name="editTxt" id="editTxt" rows="25" cols="100" oninput="enableSaveSelection()"><%= editTxt %></textarea>
				</p>
				<input type="submit" name="saveSelection" id="saveSelection" value="SaveText" disabled/>
				
			<div class="vMsgBox" id="vMsgBox" >
				<textarea readonly class="vMsgText" id="vMsgText">
				<%
				if(!vMsgTxt.isEmpty())
				{
				%>
					<%= vMsgTxt %>
				<%
				}
				%>
				</textarea><!-- vMsgTxt -->
			</div><!-- vMsgBox -->
		
			</form>
			<p>
				<button onclick="document.location = 'xmlEditor.jsp'">Clear Form</button>
			</p>
			<a id="enumerations"><b>* Enumerations</b></a>
			<p>
			Enumerations identify different data type collections. Adding to an existing enumerator 
			group is easy&mdash;just add a new definition to an existing Defs group. The new entry 
			should show up in a recipe form automatically.
			</p>
			<p>
			Adding an enumeration for a new data type is not so straight forward. There is a significant amount of 
			programming required&mdash;the group needs to be added to the input form, and code  
			needs to be added to the parser and data management process&mdash;to support a new data type 
			collection.  
			</p>
		</div><!--  text -->
	</div><!-- contentDisplayArea -->
		<div class="send_back">
			<div class="send_back_pair">
				<div class="send_back_label">Go to </div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="../forms/recipeSelect.jsp">Recipe Selection Form</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="../forms/recipe_input_form.jsp">Recipe Input Form</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="../testKitchen.jsp">Admin</a></div>
			</div>
		</div><!--  send_back -->
			
	</body>
</html>