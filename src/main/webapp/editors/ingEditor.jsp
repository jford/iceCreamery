<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.util.Vector,
                 java.util.Iterator,
                 com.electraink.iceCreamery.testKitchen.datastore.*" %>
<%
	DataMgr dataMgr = new DataMgr();
%>	

<%@ include file="../jsp_fragments/modeCtlVars.jspf" %>
<%@ include file="../jsp_fragments/ingEditor.jspf" %>
   
 <html>
	<head>
		<title>Ice Creamery Ingredient Editor</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<script type="text/javascript" src="../scripts/ingEditor.js"></script>
		<link rel='stylesheet' type='text/css' href='../styles/ingEditor.css'/> 
	</head>
	
<body onload="setMode('<%= mode %>')">
	<div class="contentDisplayArea">
		
		<div class="page_title">
			<%= pageName %> Editor
		</div>
		
		<form name="editIngsForm" 
		      id="editIngsForm" 
			  action="../ingsInput"
		      method="post"> 
		<input type="hidden" 
		       name="ingType" 
		       id="ingType" 
		       value="<%= ingType %>"/>
		
		<%@ include file="../page_components/modeCtls.jspf" %>
		<%@ include file="../page_components/instructionText.jspf" %>
		
		<div class="addIngDiv" id="addIngDiv">
			<p class="section_subhead">
				Add New <%= ingCategorySingular %>
			</p>
			Name: <input type="text" 
			             class="newIngName" 
			             id="newIngName" 
			             name="newIngName" 
			             value=""/>
		</div><!-- end addIngDiv -->
		
		<div class="editIngDiv" id="editIngDiv">
			<p class="section_subhead">
				Edit <%= ingName %>
			</p>
			<%= ingSlct %>
			              
			<div class="editInputsDiv" id="editInputsDiv">
				<table class="editInputsTbl" id="editInputsTbl" name="editInputsTbl">
					<tr class="editInputsTblRow" id="editInputsTblRowA" name="editInputsTblRowA">
						<td class="editInputsTblCol" id="editInputsTblCol1" name="editInputsTblCol1">
							<label for="editIngName" 
							       id="editIngNameLabel">Name: </label>
				        </td>
						<td class="editInputsTblCol2" id="editInputsTblCol2" name="editInputsTblCol2">
					       	<input type="text" 
					               class="editIngName" 
					               id="editIngName" 
					               name="editIngName" 
					               value="<%= ingName %>"/>
				        </td>
				    </tr>
					<tr class="editInputsTblRowB" id="editInputsTblRowB" name="editInputsTblRowB">
						<td class="editInputsTblCol1" id="editInputsTblCol1" name="editInputsTblCol1">
							<label for="editIngType"
							       id="editIngTypeLabel">Current Type<%= asterisk1 %>:</label>
				        </td>
						<td class="editInputsTblCol2" id="editInputsTblCol2" name="editInputsTblCol2">
							<input type="text"
							       class="editIngType"
							       id="editIngType"
							       name="editIngType"
							       value="<%= ingType %>" 
							       readonly/>
				        </td>
				    </tr>
					<tr class="editInputsTblRowB" id="editInputsTblRowB" name="editInputsTblRowB">
						<td class="editInputsTblCol1" id="editInputsTblCol1" name="editInputsTblCol1">
							<label for="editIngType"
							       id="editIngTypeLabel">New Type<%= asterisk2 %>:</label>
				        </td>
						<td class="editInputsTblCol2" id="editInputsTblCol2" name="editInputsTblCol2">
							<%= newIngTypesRdoBtns %>
				        </td>
				    </tr>
					<tr class="editInputsTblRowA" id="editInputsTblRowA" name="editInputsTblRowA">
						<td class="editInputsTblCol1" id="editInputsTblCol1" name="editInputsTblCol1">
							<label for="editIngId"
							       id="editIngIdLabel">Id (read only)<%= asterisk3 %>:</label>
				        </td>
						<td class="editInputsTblCol2" id="editInputsTblCol2" name="editInputsTblCol2">
							<input type="text"
							       name="editIngId"
							       id="editIngId"
							       class="editIngId"
							       name="editIngId"
							       value="<%= ingId %>" 
							       readonly />
						</td>
					</tr>
					<tr>
						<td class="editInputsTblNotes" id="editInputsTblNotes" name="editInputsTblNotes" colspan="2">
							<div class="text_note">
							<p class="note_text">
							<%= asterisk1 %>Read-only field; use <i>new type</i> buttons to change type<br/>
							<%= asterisk2 %> Use these only if changing type; see above note.<br/>
							<%= asterisk3 %> Read-only field; ID cannot be changed.<br/>
							</p>
							</div><!-- end text_note div -->
						</td>
					</tr>
				</table>
				       
			</div><!-- end editInputsDiv -->
			
		</div><!-- end editIngDiv -->
		
		<div class="delIngDiv" id="delIngDiv">
			<p>Ho there, from delIngDiv.</p>
			<%= ingSlct %>
		</div><!-- end delIngDiv -->
		
		<div class="formCtlsDiv" id="formCtlsDiv">
			<input type="submit" 
			       id="formSubmitBtn" 
			       value="Submit" />
			<input type="button" 
			       id="formCancelBtn" 
			       value="Cancel" 
			       onclick="checkModeBtn(getModeSelected())" /> 
			<br/><br/>
			<input type="button"
			       id="showSplrTblBtn"
			       value="Show Suppliers Table"
			       onclick="showHideSplrTbl('show')" />
		</div><!-- end formCtlsDiv  -->
		
		<div class="splrTblDiv" id="splrTblDiv">
			<div class="splrTblInptFlds" id="splrTblInptFldsDiv">
				<p class="splrTblIntro" id="splrTblIntro">
					Current suppliers of <%= ingName %>:
				</p>
				<%= splrTbl %>
				<p class="splrAddPrompt" id="splrAddPrompt">Add row to table:</p>
				<%= splrsMnu %>
					<input type="checkbox" 
					       name="addNewSplrChkbx" 
					       id="addNewSplrChkbx" 
					       onclick="showHideAddNewSplrDiv('show'),showHideAddNewIngToSplrDiv('hide');" />
					<label for="addNewSplrChkbx"
					       id="addNewSplrChkbxLabelId">Add new supplier </label>
					<br/><br/>
					
				<div class="addNewSplrDiv" id="addNewSplrDiv">
					<p class="addNewSplrHd" id="addNewSplrHd">Add a new supplier to the database:</p>
					<label for="newSplrName" 
					       id="newSplrNameLabelId">Supplier Name: </label>
					<input type="text" 
					       name="newSplrName" 
					       id="newSplrNameId" 
					       size="10" 
					       value="" />

					<label for="newSplrCtct" 
					       id="newSplrCtctLabelId">Contact: </label>
					<input type="text" 
					       name="newSplrCtct" 
					       id="newSplrCtctId" 
					       size="10" 
					       value="" />

					<label for="newSplrTel" 
					       id="newSplrTelLabelId">Telephone: </label>
					<input type="text" 
					       name="newSplrTel" 
					       id="newSplrTelId" 
					       size="10" 
					       value="" />
					<br/><br/>
	               <label for="splrNotes"
	                      id="splrNoteslabelId">Supplier Notes: </label>
			       <textarea
			              name="splrNotes"
			              id="splrNotesId" 
			              rows="5" cols="50"></textarea>

					<input type="hidden"
					       name="ingId"
					       value="<%= ingId %>" />

					<input type="hidden"
					       name="ingName"
					       value="<%= ingName %>" />
					       
				</div><!--  end addNewSplrDiv -->
				
				<div class="addNewIngToSplrDiv" id="addNewIngToSplrDiv">
					<p class="addNewIngHd" id="addNewIngHd">Add a new product to the selected supplier's inventory:</p>	       
			       <label for "ingName"
			              id = "ingNameLabelId">Brand Name: </label>
			       <input type="text" 
			              name="ingName"
			              id="ingNameId"
			              size="10"
			              value="" />
			              
			       <label for="unitSize"
			              id="unitSizeLabelId">Size: </label>
			       <input type="text"
			              name="unitSize"
			              id="unitSizeId"
			              size="3"
			              value="" />
	
			       <label for="unitPrice"
			              id="unitPriceLabelId">Price: </label>
			       <input type="text"
			              name="unitPrice"
			              id="unitPriceId"
			              size="3"
			              value="" />
	
	               <label for="unitFees"
	                      id="unitFeeslabelId">Fees:<%= asterisk1 %></label>
			       <input type="text"
			              name="unitFees"
			              id="unitFeesId"
			              size="3"
			              value="" />
			       
			       <br/><%= asterisk1 %> Tax, shipping, other additional costs
			       
			       <br/><br/>   
			           
	               <label for="ingNotes"
	                      id="ingNotelabelId">Ingredient Notes: </label>
			       <textarea
			              name="ingNotes"
			              id="ingNotesId" 
			              rows="5" cols="50"></textarea>
		       </div><!-- end addNewIngToSplrDiv -->
			</div><!-- end splrTblInptFldsDiv -->
		</div><!--  end splrTblDiv -->
		
		</form>
	</div></-- end contentDisplayArea -->

	<div class="send_back">
	    <%@ include file="../page_components/send_back_links/goToLabel.jspf" %>
		<%@ include file="../page_components/send_back_links/admin.jspf" %>
	</div> <!-- send_back -->
	

</body>
</html>