function getPageUrlParam(param)
{
//	alert("in iceCreamery.js.getPageUrl()");
	
	var pageUrl = window.location.search.substring(1);
	var paramList = pageUrl.split('&');
	var srchParam = "";
	var retParam = "";
	
//	alert("pageUrl = " + pageUrl + "\nparam = " + param);

	for(i = 0; i < paramList.length; i++)
	{
		srchParam = paramList[i].split('=')[0]
		if(srchParam === param)
			retParam = paramList[i].split('=')[1];
	}

//	alert("param = " + param + "\nretParam = " + retParam);
//	alert("leaving iceCreamery.js.getPageUrl()");	
	
	return retParam;
}

function clearUrlParams(mode)
{
//	alert("in iceCreamery_utils.clearUrlParams(" + mode + ")");
	
	var location = window.location.toString();
	var param = "mode=" + mode;

	newLocation = location.split('?')[0];
	if(mode.length > 0)
	{
		newLocation += "?" + param;
		alert("slammo " + mode);
	}
	window.location = newLocation; 
//	alert("leaving iceCreamery_utils.clearUrlParams()");	
}

function resetMode()
{
//	alert("in iceCreamery_utils.resetMode()");

	var currentLocation = window.location.toString();

	window.location = currentLocation.split('?')[0];
	setMode("");

//	alert("leaving iceCreamery_utils.resetMode()");
}

function sayHello()
{
//	alert("in sayHello()");
	
	var hello = "Yo, from iceCreamery_utils.js";

//	alert("leaving sayHello()");
	return hello;
}

function toggleVMsgDisplay()
{
//	alert("in toggleVMsgDisplay()");
	
	var vMsgBox = document.getElementById("vMsgBox");
	
//	alert("vMsgBox = " + vMsgBox + "\nvMsgBox.style.display = " + vMsgBox.style.display.value);
	
	var vMsgState = vMsgBox.style.display == "block" ? "none" : "block";
	
//	alert("vMsgState = " + vMsgState);
	
	vMsgBox.style.display = vMsgState;
	
//	alert("...and out.");
}

function checkMode()
{
	// checkMode() is triggered by a selection from the menu in the 
	// recipe input form; menu is displayed when in edit or delete mode
	
//	alert("in checkMode()");
	var mode = document.querySelector('input[name = "mode"]:checked').value;
	var recipeByName = document.getElementById("recipeByName");
	var recipeName = recipeByName.options[recipeByName.selectedIndex].value;
	var submitButton = document.getElementById("recipe_submit");
	var ret = true;
	
//	alert("recipeName = " + recipeName);
//	alert("mode == " + mode + "\nrecipeByName.selectedIndex == " + recipeByName.selectedIndex);
	
	if(mode == "delete" || recipeByName.selectedIndex == 0)
		ret = false;
	else if(mode == "edit")
	{
		recipeIngredients.style.display = "block";
		recipeInstructions.style.display = "block";
		recipeComments.style.display = "block;"
		recipeImage.style.display = "block";
		submitButton.style.display = "block";
//		displayRecipe(decodeHtmlParam(recipeByName.options[recipeByName.selectedIndex].value));
		displayRecipe(encodeHtmlParam(recipeName));
	}
	
//	alert("...and out of checkMode()" +
//	      "cloneRecipe.style.display: " + document.getElementById("cloneRecipe").style.display);
	
	return ret;
}

function decodeHtmlParam(textString)
{
//	alert("in decodeHtmParam(), incoming textString = \n" + textString);
	var count = 0;
	var paramMap = [
		                [' ',	'%20'],
				    	[',',	'%2C'], 
				    	['/',	'%2F'], 
				    	['?',	'%3F'], 
				    	['@',	'%40'], 
				    	['&',	'%26'], 
				    	['=',	'%3D'], 
				    	['+',	'%2B'], 
				    	['$',	'%24'], 
				    	['#',	'%23'], 
				    ]
	
	for(count = 0; count < paramMap.length(); count++)
	{
		while(textString.indexOf(paramMap[count][1]) != -1)
		{
			textString = textString.replace(paramMap[count][1], paramMap[count][0]);
		}
	}
//	alert("in decodeHtmParam(), outgoing textString = \n" + textString);
	
	return textString;
}

function encodeHtmlParam(textString)
{
//	alert("in encodeHtmParam(), incoming textString = \n" + textString);

	var count = 0;
	var paramMap = [ 
		                [' ',	'%20'],
	                	[',',	'%2C'], 
	                	['/',	'%2F'], 
	                	['?',	'%3F'], 
	                	['@',	'%40'], 
	                	['&',	'%26'], 
	                	['=',	'%3D'], 
	                	['+',	'%2B'], 
	                	['$',	'%24'], 
	                	['#',	'%23'], 
	                ];
	
//	alert("ready for the countdown...");
	for(count = 0; count < paramMap.length; count++)
	{
//		alert("count = " + count);
//		alert("looking for paramMap[count][0] = " + paramMap[count][0])
		while(textString.indexOf(paramMap[count][0]) != -1)
		{
//			alert("textString before the replace = " + textString);
			textString = textString.replace(paramMap[count][0], paramMap[count][1]);
//			alert("textString after the replace = " + textString);
		}
	}
//	alert("in encodeHtmParam(), outgoing textString = \n" + textString);
	
	return textString;
}

//function setMode(mode)
//{
//	alert("in ice_creamery_utils.setMode(" + mode + ")");
//	var modes = document.getElementsByName("mode");
//	
//	for(i = 0; i < modes.length; i++)
//	{
//		if(mode[i].value = mode)
//			mode[i].checked = true;
//		else
//			mode[i].checked = false;
//	}
//	toggleMode(mode);
//	alert("leaving ice_creamery_utils.setMode()");
//}
//
function toggleMode(mode)
{
//	alert("entering toggleMode(" + mode + ")");
	
	var cloneRecipe = document.getElementById("cloneRecipe");
	var getRecipeForEditBtn = document.getElementById("getRecipeForEditBtn");
	var imageControls = document.getElementById("recipeImage");
	var recipeByName = document.getElementById("recipeByName");
	var recipeNameInput = document.getElementById("recipeNameInput");
	var recipeNameSelect = document.getElementById("recipeNameSelect");
	var recipeIngredients = document.getElementById("recipeIngredients");
	var recipeInstructions = document.getElementById("recipeInstructions");
	var recipeComments = document.getElementById("recipeComments");
	var submitButton = document.getElementById("recipe_submit");
	
//	var recipeIngredients = document.getElementById("recipeIngredients");
//	var recipeInstructions = document.getElementById("recipeInstructions");
//	var recipeImage = document.getElementById("recipeImage");
	var recipeSubmitBtn = document.getElementById("recipeSubmitBtn");
//	var recipeComments = document.getElementById("recipeComments");
	
	if(mode == "add")
	{

//		alert("mode is add");
		
		submitButton.value = "Add Recipe";
		getRecipeForEditBtn.style.display = "none";
		cloneRecipe.style.display = "none";
		recipeNameInput.style.display = "block";
		recipeNameSelect.style.display = "none";
		recipeIngredients.style.display = "block";
		recipeInstructions.style.display = "block";
		recipeComments.style.display = "block";
		recipeImage.style.display = "block";
		submitButton.style.display = "block";
		
		// reset edit/delete menu
		recipeByName.selectedIndex = 0;
		alert("11");
		document.location = "recipe_input_form.jsp";
		
//		alert("...and out of add...");
	}
	if(mode == "edit")
	{

//		alert("mode is edit\n\nrecipeByName.selectedIndex = " + recipeByName.selectedIndex);
		
		
		recipeNameInput.style.display = "none";
		recipeNameSelect.style.display = "block";

		submitButton.value = "Submit Edits";
		
//		alert("...still here...1");

		getRecipeForEditBtn.style.display = "inline";

//		alert("...still here...2");

		if(recipeByName.selectedIndex == 0)
			cloneRecipe.style.display = "none";
		else
			cloneRecipe.style.display = "block";
		
		if(recipeByName.selectedIndex == 0)
		{
			// hide input controls until a recipe has been selected;
			// code to display is in checkMode(), which gets triggered
			// when a selection is made from the recipe menu
			recipeIngredients.style.display = "none";
			recipeInstructions.style.display = "none";
			recipeComments.style.display = "none";
			recipeImage.style.display = "none";
			submitButton.style.display = "none";

	//	alert("...still here...3");

		}
		else
		{
			recipeIngredients.style.display = "block";
			recipeInstructions.style.display = "block";
			recipeComments.style.display = "block";
			recipeImage.style.display = "block";
			submitButton.style.display = "block";

//		alert("...still here...4");

		}
//		alert("and out of edit toggles");
	}
	if(mode == "delete")
	{

//		alert("mode is delete");
		
		cloneRecipe.style.display="none";
		submitButton.value = "Delete Recipe";
		submitButton.style.display = "block";
		getRecipeForEditBtn.style.display = "none";
		recipeNameInput.style.display = "none";
		recipeNameSelect.style.display = "block";
		recipeIngredients.style.display = "none";
		recipeInstructions.style.display = "none";
		recipeComments.style.display = "none";
		recipeImage.style.display = "none";
		recipeSubmitBtn.style.display = "block";
		
//		alert("...and out of delete...");
	}
	
//	alert("...and out of toggleMode()");
	
}

function displayDetails(detailsFile)
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var slideDetailsWindow = window.open("details/" + detailsFile, "detailsWindow", strWindowFeatures);
}

function displayRecipe(recipeName)
{
//	alert("in displayRecipes(), recipeName = " + recipeName);
	
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=800,height=900,left=20,top=20";
	var textsearchWindow = window.open("../testKitchen/showRecipe.jsp?recipeName=" + recipeName, "", strWindowFeatures);
}

function displaySupplemental(supplementalFile)
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var supplementalWindow = window.open(supplementalFile, "supplementalFileWindow", strWindowFeatures);
}

function displaySupplementalLarge(supplementalFile)
{
//	alert("in displaySupplementalLarge(); supplementalFile = " + supplementalFile);

	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=900,height=800,left=20,top=20";
	var supplementalLargeWindow = window.open(supplementalFile, "supplementalFileWindow", strWindowFeatures);

//	alert("leaving displaySupplementalLarge()");
}

function getCommentText()
{
//	alert("entering getCommentText()");
	
	var text = document.getElementById("commentTxt").value;
	var comment = document.getElementById("comment")
	comment.value = text;
	
//	alert("comment.value: " + comment.value);
//	alert("text =" + text);
}

function validateCommmentInputData()
{
//	alert("entering validateCommentInputData()");
	
	// message box
	var vMsgBox = document.getElementById("vMsgBox");
	var lineHeight = 20;
	var boxHeight = lineHeight;
	
//	alert("vMsgBox defined");
	
	// form params
	var firstName = document.forms["comment"]["firstName"].value;
	var lastName = document.forms["comment"]["lastName"].value;
	var email = document.forms["comment"]["email"].value;
	var screenName = document.forms["comment"]["screenName"].value;
	var commentSubject = document.forms["comment"]["commentSubject"].value;
	var commentTxt = document.forms["comment"]["commentTxt"].value;
	
//	alert("Text: " + commentTxt);
	
	// return value
	var ret = true;
	
//	alert("ret declared");
	
	// error message
	var msg = "Insufficient data. Fill in the necessary fields and click Send again.";
	
//	alert("vars declared");
//	alert("msg = " + msg);
	
	if(screenName == "")
	{
//		alert("screenName empty"); 
		
		ret = false;
		msg += "\n\n   - You must choose a screen name to be associated with this comment.";
		
//		alert("msg = " + msg);
		
		boxHeight += 2 * lineHeight;
	}
	if(firstName == "" || lastName == "")
	{
//		alert("firstName/lastName empty");
		
		ret = false;
		msg += "\n\n   - First and last name fields cannot be blank (only the screen name will be displayed).";
		
//		alert("msg = " + msg);
		
		boxHeight += 2 * lineHeight;
	}
	if(email == "")
	{
//		alert("email empty");
		
		ret = false;
		msg += "\n\n   - For validation purposes, you must provide an email address (will not be displayed).";
		
//		alert("msg = " + msg);
		
		boxHeight += 2 * lineHeight;
	}
	
	if(commentSubject == "")
	{
//		alert("commentSubject empty");
		
		ret = false;
		msg += "\n\n   - Subject cannot be blank.";
		
//		alert("msg = " + msg);
		
		boxHeight += 2 * lineHeight;
	}
	if(commentTxt == "")
	{
//		alert("commentTxt empty");
		
		ret=false;
		msg += "\n\n   - Comment field cannot be blank.";
		
//		alert("msg = " + msg);
		
		boxHeight += 2 * lineHeight;
	}
	
//	alert("conditions evaluated");
	
	if(ret == false)
	{	
		var vMsgBox = document.getElementById("vMsgBox");
		var vMsgText = document.getElementById("vMsgText");
		var heightStyle = (boxHeight + lineHeight).toString() + "px";
		
		vMsgText.value = msg;
		vMsgBox.style.height = heightStyle;
		vMsgText.style.height = heightStyle;
		vMsgBox.style.display = "block";
	}
	
//	alert("ret = " + ret);
	
//	return false;
	return ret;
}

function displayRulesOfUse()
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var slideDetailsWindow = window.open("/iceCreamery/comments/rulesOfUse.jsp", "rulesOfUseWindow", strWindowFeatures);
}

function displayRecipeInputMsg(vMsgText)
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var slideDetailsWindow = window.open("/iceCreamery/msgFiles/recipeInputMsg.jsp?vMsgText=" + vMsgText, "recipeInputMsgWindow", strWindowFeatures);
}

function validateGetRecipe()
{
//	alert("now in validateGetRecipe()");
	
	var cloneRecipe = document.getElementById("cloneRecipe");
	
	cloneRecipe.style.display = "block";

//	alert("now leaving validateGetRecipe()" +
//	      "cloneRecipe.style.display = " + cloneRecipe.style.display);
}

function clearMsgBox()
{
//	alert("in clearMsgBox);
	document.getElementById("vMsgBox").style.display = "none";
//  alert("leaving clearMsgBox()");
}

function clearMsgParam()
{
	/*
		Todo: document why this function is needed  
	*/
	
//	alert("entering clearMsgParam()" +
//	       "\ncloneRecipeName.value = "  + cloneRecipeName.value);
//           "\n****js method in iceCreamery.js currently disaboled");
	
//	if(cloneRecipeName.value.includes("unique name")) 
//		document.location = "/iceCreamery/forms/recipe_input_form.jsp?mode=add"; 
		
//	alert("leaving clearMsgParam()");	
}

function clearCloneNameValue()
{
//	 alert("entering clearCloneNameValue()");
	 
	 var cloneRecipeName = document.getElementById("cloneRecipeName");
	 var name = cloneRecipeName.value;
	 
//	 alert("name = " + name);
	 
//	 alert("name.includes(\"unique name\") = " + 
//	       name.includes("unique name"));
	 
	 if(name.includes("unique name"))
		 cloneRecipeName.value = "";
		 
		 configureForCloning();

//	 alert("leaving clearCloneNameValue()" + 
//	       "cloneRecipeName.value = " + cloneRecipeName.value);
	 
}

function clearScoreInputValue()
{
//	alert("entering clearScoreInputValue()");
	
	var score = document.getElementById("score");
	
	if(score.value == "Unrated")
		score.value = "";
	
//	alert("...leaving clearScoreInputValue()");
}

function configureForCloning()
{
//	alert("entering configureForClonng()...");
	
	var recipeIngredients = document.getElementById("recipeIngredients");
	var recipeInstructions = document.getElementById("recipeInstructions");
	var recipeImage = document.getElementById("recipeImage");
	var recipeSubmitBtn = document.getElementById("recipeSubmitBtn");
	var recipeComments = document.getElementById("recipeComments");
	
	recipeIngredients.style.display = "none";
	recipeInstructions.style.display = "none";
	recipeImage.style.display = "none";
	recipeSubmitBtn.style.display = "none";
	recipeComments.style.display = "none";
	
//	alert("...leaving configureForCloning()");
}
