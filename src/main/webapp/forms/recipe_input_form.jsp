<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="../jsp_fragments/modeCtlVars.jspf" %>
<%@ include file="../jsp_fragments/rcpInputForm.jspf" %>
   
 <html>
	<head>
		<title>Ice Creamery Recipe Input Form</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<script type="text/javascript" src="../scripts/rcpInputForm.js"></script>
		<link rel='stylesheet' type='text/css' href='styles/rcpInputForm.css'/> 
	</head>
	<body onload="setMode('<%= mode %>');">
	<div class="contentDisplayArea">
		<div class="page_title" style="width: 75%; text-align: left;">
		Recipe Editor 
		</div> <!-- page_title -->

		<%@ include file="../page_components/vMsgBox.jspf" %>

 		<div class="text">
			<form name="recipeInput" class="recipeInput" action="../recipeInput" method="post">
			<%@ include file="../page_components/modeCtls.jspf" %>
			<%@ include file="../page_components/instructionText.jspf" %>
						
				<div class="getRcpDiv" id="getRcpDiv">
				
					<div class="recipeNameInput" id="recipeNameInput">
						<p class="dataCategory">Recipe Name</p>
						<input type="text" 
						       name="recipeName" 
						       id="recipeName" 
						       size="58"
						       value = "<%= rcpNameVal %>"/>
						<input type="submit" 
						       name="recipeSubmitBtn" 
						       id="recipeSubmitBtn" 
						       value="Add Recipe"/>
						<input type="hidden"
						       name="updateId"
						       id="updateId"
						       value="<%= rcpId %>" />
					</div><!-- recipeNameInput -->
				
					<div class="recipeNameSelect" id="recipeNameSelect">
						<p class="dataCategory">Select Recipe</p>
						<%= rcpSlctMnu %>
					</div><!-- recipeNameSelect div -->
					
					<div class="recipeEditsSubmitBtnDiv" id="recipeEditsSubmitBtnDiv">
						<input type="submit" 
						       name="submitEditsBtn" 
						       id="submitEditsBtn" 
						       value="Submit Edits"
						       onclick="document.recipeInput.submit()" />
					</div><!-- recipeEditsSubmitBtnDiv -->
						
					<div class="cloneRecipe" id="cloneRecipe" >
						<p>Clone As: </p>
						<input type="text" 
						       name="cloneRecipeName" 
						       id="cloneRecipeName" 
						       size="58" 
						       value="Enter unique name for recipe clone" />
						<input type="submit" 
						       name="cloneRecipeBtn" 
						       id="cloneRecipeBtn" 
						       value="Save" />
					</div><!-- end cloneRecipe div -->
					
				</div><!-- recipeNameSelect -->
				
			</div><!-- end getRcpDiv -->
				
	<!--  Ingredients -->
	
				<div class="recipeIngredients" id="recipeIngredients">
					<table class="ingredientsTable">
	
	<!--  Dominant Flavor -->
	
						<tr><!-- dominant flavor -->
							<td class="dataCategory">Dominant Flavor</td>
						</tr>
	
						<tr>
							<td class="dataItemInput" colspan="3">
							
								<%
									// recipeMgr.getInputFormValue() retrieves input tag's value for edit mode; if input is checkbox,
									// retrieved value should be either "" or "checked"
								%>
								<input type="text" 
								       name="domFlavor" 
								       id="domFlavor" 
								       value="<%= domFlavVal /*recipeMgr.getInputFormValue(recipeByName, "domFlavor")*/ %>" 
								       size="58"/>
							</td>
						</tr><!--  end dominant flavor -->
						 	
	<!--  Flavorings -->
				
						<tr><!-- flavorings -->
							<td class="dataCategory"><%= hrefFlavorings %></td>
						</tr>
						<tr>
							<td class="dataItemInput">
								<%= flavsSlctTable %>
							</td>
						</tr><!--  end flavorings -->
						
	<!--  Dairy -->
	
						<tr><!-- dairy -->
							<td class="dataCategory"><%= hrefDairy %></td>
						</tr>
	
						<tr>
							<td class="dataItemInput">
								<%= dairySlctTable %>
							</td>
						</tr><!-- end dairy -->
	
	<!-- emulsifiers -->
						<tr>
							<td class="dataCategory"><%= hrefEmuls %></td>
						</tr>
						<tr>
							<td class="dataItemInput">
								<%= emulsSlctTable %>
							</td>
						</tr><!--  end emulsifiers -->
		
	<!--  salts -->
						<tr>
							<td class="dataCategory"><%= hrefSalts %></td>
						</tr>
						<tr>
							<td class="dataItemInput">
								<%= saltsSlctTable %>
							</td>
						</tr><!--  salt table -->

		<!-- stabilizers -->
	
						<tr>
							<td class="dataCategory"><%= hrefStabs %></td>
						</tr>
						<tr>
							<td class="dataItemInput">
								<%= stabsSlctTable %>
							</td>
						</tr><!--  end stabilizers -->
	
	<!-- sweeteners -->
	
						<tr>
							<td class="dataCategory"><%= hrefSwts %></td>
						</tr>
						<tr>
							<td class="dataItemInput">
								<%= swtsSlctTable %>
							</td>
						</tr>
					</table><!--  end sweeteners table -->
					</div><!-- recipeIngredients -->
	
	<!--  Instructions -->
					
					<div class="recipeInstructions" id="recipeInstructions">
						<p class="dataCategory">
						Instructions
						</p>
						<textarea name="instructions" id="instructions" rows="5" cols="48"><%= recipeMgr.getInputFormValue(rcpId, "instructions")%></textarea>
						
	<!--  Cook Method -->
	
						<p>Cook method:
						<%=  ckMthdMnu %>
						
	<!-- Cook Time -->
						
	                    for <input type="text" 
	                               name="cooktime" 
	                               size="10" 
	                               value="<%= ckTimeVal /* recipeMgr.getInputFormValue(rcpId, "cooktime") */ %>"/>
	                    
	<!-- Cook Temp -->
	                     
	                    at <input type="text" 
	                              name="cooktemp" 
	                              size="10" 
	                              value="<%= ckTempVal /* recipeMgr.getInputFormValue(recipeByName, "cooktemp") */ %>"/>
						</p>
						<p class="note_text">
						Leaving the cook method menu at <i><%= DataMgr.defaultSelectOption %></i> will be reported as a no-cooking recipe on the summary page.
						</p>
					
					</div><!-- recipeInstructions -->
	
	<!--  Comments -->
	
					<div class="recipeComments" id="recipeComments">
						<p class="dataCategory">
						Comments
						</p>
						<textarea name="comments" 
						          id="comments" 
						          rows="5" 
						          cols="48"><%= commentVal /* recipeMgr.getInputFormValue(recipeByName, "comments") */ %></textarea>
					
	
	<!--  Score -->
	
						<p>
						Score: On a scale of 1-10, this recipe rates <input type="text" 
						                                                    name="score" 
						                                                    id="score" size="2" 
						                                                    value="<%= scoreVal /* recipeMgr.getInputFormValue(recipeByName, "score") */ %>" 
						                                                    onclick="clearScoreInputValue()"/>
						</p>
						<p class="text_note" style="text-align: center">
						Scores less than 1 will be entered into the database as 1; 
						greater than 10 entered as 10.<br/>To record a recipe as 
						unrated, submit the recipe with no value in the score entry box 
						</p>
					</div><!-- recipeComments -->
				
	<!--  Image -->
	
				<div class="recipeImage" id="recipeImage">
					<input name="imageEditor" 
					       id="imageEditor" 
					       type="checkbox" 
					       onchange="showImageCtrls()" /> Edit Image 
					<div class="recipeImageCtrls" id="recipeImageCtrls">
					<%
					if(mode != null && mode.equals("edit"))
					{
					%>
						<input name="imageEditMode" 
						       id="imageEditMode" 
						       type="radio" 
						       checked 
						       onchange="toggleImageEditMode('add')" />Add
						<%
							if(rcpId != null && !rcpId.equals("null") && !rcpId.isEmpty())
							{
						%>
						<input name="imageEditMode" 
						       id="imageEditMode" 
						       type="radio" 
						       value="removeImage" 
						       onchange="toggleImageEditMode('remove')" />Remove
						<%
							}
						%>
						<div class="imageAddCtrls" id="imageAddCtrls">
					<%
					}
					%>
						<div class="note_text">
						Add a previously uploaded image to this recipe. 
						<%
						if(mode == null || mode.equals("add"))
						{
						%>
							If the image has not yet been uploaded, 
							save this recipe first, upload the image, then return to the Recipe Editor in edit mode 
							to add the image to the saved recipe.
						<%
						}
						%>
						</div>
						<p>
						<%
						String imgBtnName = mode == null || mode == "add" ? "add" : "edit"; 
						%>
						The <i>Select Filename</i> menu contains all picture files that have been uploaded to the Ice 
						Creamery image repository. Select a filename, and enter caption text. When the <i><%= imgBtnName %></i> 
						button is clicked, all data currently entered in the Recipe Editor will be committed to the 
						recipe database.
						</p>
						<select name="imageFilename" id="imageFilename">
							<option value="">Select filename...</option>
							<%
							ImageMgr imgMgr = new ImageMgr();
							Vector<String> imageList = imgMgr.getImageList();
							Iterator<String> iImages = imageList.iterator();
							while(iImages.hasNext())
							{
								String imgFilename = (String)iImages.next();
							%>
							<option value = "<%= imgFilename %>"><%= imgFilename %></option>
							<%
							}
							%>
							
						</select>
						
	<!--  Caption -->					
						<p>
						Enter caption:
						</p>
						<p>
							<textarea name="caption" 
							          id="caption" 
							          rows="2" 
							          cols="48"><%= captionVal /* recipeMgr.getInputFormValue(recipeByName, "caption") */ %></textarea>
						</p>
						</div><!-- imageAddCtrls -->
						
	<!-- Remove Image -->
						
						<div class="imageRemoveCtrls" id="imageRemoveCtrls">
						<div class="note_text">
						Removes an image from this recipe, but does not delete the file from the images directory.
						</div><!-- end note_text div -->
						<p>
						<%
						if(rcpId != null)
						{
							Recipe editRecipe = recipeMgr.getRecipe(rcpId);
							String imgFilename = "";
							if(editRecipe != null)
								imgFilename = editRecipe.getImageFilename();
							if(!imgFilename.isEmpty())
							{
							%>
								The recipe <i><%= recipeByName %></i> currently includes the image file <i><%= imgFilename %></i>. This 
								reference will be removed from the recipe when the Submit Edits button is clicked. To keep 
								this reference, exit the Recipe Editor without submitting changes.
							<%
							}
						}
						%>
						</p>
						</div><!-- imageRemoveCtrls -->
					</div><!-- recipeImageCtrls -->
				</div><!-- recipeImage -->
				
			</form>

		</div><!-- end text div -->
	</div> <!-- contentDisplayArea -->
	
	<div class="send_back">
	    <%@ include file="../page_components/send_back_links/goToLabel.jspf" %>
		<%@ include file="../page_components/send_back_links/admin.jspf" %>
	</div> <!-- send_back -->
	
	</body>
</html>

