function getUrl()
{
//	alert("in getUrl()")
	var url = document.location;
//	alert("url = " + url);
//	alert("leaving getUrl()");	
}

function setSelectedIndex(mnuId, valueToSelect) 
{    
    let element = document.getElementById(mnuId);
    element.value = valueToSelect;
}

function getUrlParam(key)
{
//	alert("in getUrlParam(" + key + ")");
	
	var urlString = document.location.toString();
	var value = "";
	var idx = urlString.indexOf("?");
	var params = urlString.substring(idx + 1);
	var paramsArray = params.split("&");
	var paramPair;
	
	for( i = 0; i < paramsArray.length; i++)
	{
		paramPair = paramsArray[i].split("=");
		if(paramPair[0].toString() === key)
		{
			value = paramPair[1].toString();
			break;
		}
	}

//	alert("value = " + value);
	
	return value;	
}

function checkUrlParams()
{
//	alert("in checkUrlParam()");
	
	var recipeByName = getUrlParam("recipeByName");
	var mode = getUrlParam("mode");
	var recipeNameSelect = document.getElementById("recipeNameSelect");
	var recipeIngredients = document.getElementById("recipeIngredients");

	if(recipeByName != "" && mode === "edit")
	{
		recipeNameSelect.style.display = "block";
		recipeIngredients.style.display = "block";
	}

//	alert("leaving checkUrlParam()");
}


function showImageCtrls()
{
	var imageCtrls = document.getElementById("recipeImageCtrls");
	
	if(imageCtrls.style.display == "inline")
		imageCtrls.style.display = "none";
	else
		imageCtrls.style.display = "inline";
}

function setInstructionText(activity)
{
//	alert("in setInstructionText(" + activity + ")");
	
	var instructionText = document.getElementById("instructionText");
	var msg = "";
	
	var hello = sayHello();
	
	switch(activity)
	{
		case "rcpInptAdd":
		msg = "To create a new recipe, enter the recipe's name and click the <b>" + 
		      document.getElementById("recipeSubmitBtn").value + 
		      "</b> button.";
		break;
		
		case "rcpInptEdit":
		msg ="<p>To edit a recipe, enter or change data in the displayed input " +
			 "fields as needed, then click the <b>" + document.getElementById("submitEditsBtn").value + 
			 "</b> button to  change the recipe's definition. </p>" +			
			 "<p>Click the <b>" + document.getElementById("modeResetBtn").value +
			 "</b> button to clear the form without saving any changes.</p>";
		break;
		
		case "getRcp":
		msg = "Choose a recipe from the menu. Use the input fields " +
		      "to modify the recipe as desired, then click the <b>" + document.getElementById('submitEditsBtn').value +
		      "</b> button."  +
		      "<p>Click the <b>" + document.getElementById('modeResetBtn').value + 
		      "</b> button to clear the form without making any changes.</p>" +
			 "<p>To open the <b>Ingredient Editor</b> for an ingredient type, click on the " + 
			 "heading for that category when the recipe is displayed.</p>";
		break;
		
		case "rcpInptDelete":
		msg = "Choose an ingredient, click <b>Submit</b>; kiss your ingredient goodbye."
		break;
		
		default:
		msg = "<p>Select <b>" + document.getElementById("modeAdd").value +
		      "</b> to create a new recipe; <b>" + document.getElementById("modeEdit").value +
		      "</b> to modify recipe details, " +
		      "or <b>" + document.getElementById("modeDelete").value + "</b> to remove a recipe " +
		      "from the database. </p>" +
		      "<p>The <b>" + document.getElementById("modeResetBtn").value + 
		      "</b> button clears the form and returns to this state at any time. </p>";
	}	
	if(instructionText != null)
		instructionText.innerHTML = msg;
		
//	alert("msg = " + msg);
//	alert("leaving setInstructionText()");
}

function toggleImageEditMode(mode)
{
	var imageAddCtrls = document.getElementById("imageAddCtrls");
	var imageRemoveCtrls = document.getElementById("imageRemoveCtrls");
	
	if(mode == "add")
	{
		imageAddCtrls.style.display = "block";
		imageRemoveCtrls.style.display = "none";
		
	}
	if(mode == "remove")
	{
		imageRemoveCtrls.style.display = "block";
		imageAddCtrls.style.display = "none";
	}
}

function setMode(mode)
{
//	alert("in rcpInputForm.setMode()");
	
	toggleMode(mode);
	
//	alert("leaving rcpInputForm.setMode()");
}

function enableSmtEdtBtn()
{
//	alert("in rcpInputForm.enableSmtEdtBtn()");
	
//	var submitEditsBtn = document.getElementById("submitEditsBtn");
//	
//	submitEditsBtn.disabled = false;
	
//	alert("leaving rcpInputForm.enableSmtEdtBtn()");
}

function disableSmtEdtBtn()
{
//	alert("in rcpInputForm.disableSmtEdtBtn()");
	
	document.getElementById("submitEditsBtn").disabled = true;

//	alert("leaving rcpInputForm.disableSmtEdtBtn()");
}

function showHideSbmtEdsBtnDiv(showHide)
{
//	alert("in showHideSbmtEdsBtnDiv(" + showHide + ")");

	var recipeEditsSubmitBtnDiv = document.getElementById("recipeEditsSubmitBtnDiv");

	switch(showHide)
	{
		case "show":
		recipeEditsSubmitBtnDiv.style.display = "block";
		break;
		
		case "hide":
		recipeEditsSubmitBtnDiv.style.display = "none";
	}
	
// 	alert("leaving showHideSbmtEdsBtnDiv()");
}

function toggleMode(mode)
{
//	alert("in rcpInputForm->toggleMode(" + mode + ")");

	var getRcpDiv = document.getElementById("getRcpDiv");
	var rcpId = getUrlParam("rcpId");
	var recipeByNameSlct = document.getElementById("recipeByNameSlct"); // recipeByName dropdown select menu
	var recipeByNameUrlParam = getUrlParam("recipeByName");
	var recipeComments = document.getElementById("recipeComments");
	var recipeImage = document.getElementById("recipeImage");
	var recipeIngredients = document.getElementById("recipeIngredients");
	var recipeInstructions = document.getElementById("recipeInstructions");
	var recipeName = document.getElementById("recipeName"); // input field
	var recipeNameInput = document.getElementById("recipeNameInput"); // div that contains input field
	var recipeNameSelect = document.getElementById("recipeNameSelect"); // div containing recipeByName menu
	var recipeSubmitBtn = document.getElementById("recipeSubmitBtn");
	var submitEditsBtn = document.getElementById("submitEditsBtn");
	var vMsgBox = document.getElementById("vMsgBox");

	var hello = sayHello(); // usage: alert(hello);

	vMsgBox.style.display = getUrlParam("vMsgText").length > 0 ? "block" : "none";
	setSbmtBtnValue(mode);
	
	switch(mode)
	{
		case "add":
		{
//		alert("case add");
			setInstructionText("rcpInptAdd");
			showHideSbmtEdsBtnDiv("hide", mode);
	 		recipeComments.style.display = "none";
			if(recipeImage != null)
	 			recipeImage.style.display = "none";
			recipeNameInput.style.display = "block";
			recipeName.value = "";
			recipeNameSelect.style.display = "none";
			recipeIngredients.style.display = "none";
			recipeInstructions.style.display = "none";
			recipeSubmitBtn.value = "Add Recipe";
			recipeSubmitBtn.style.display = "block";
//		alert("case add done");
		}
		break;
		
		case "edit":
		{
//			alert("case edit");

			recipeNameInput.style.display = "none";
			recipeSubmitBtn.style.display = "none";
			recipeComments.style.display = "none";

			if(recipeImage != null)
				recipeImage.style.display = "none";

			recipeIngredients.style.display = "none";
			recipeInstructions.style.display = "none";
			if(recipeByNameSlct != null)
			{
				if(rcpId != null && rcpId.length > 0)
				{
//					alert("rcpId = \"" + rcpId + "\"")
					recipeNameSelect.style.display = "none";
					recipeNameInput.style.display = "block";
					recipeSubmitBtn.value = "Submit Updates";
					recipeSubmitBtn.style.display = "block";
					recipeIngredients.style.display = "block";
					recipeInstructions.style.display = "block";
					recipeComments.style.display = "block";
				}
				else
				{
					recipeNameSelect.style.display = "block";
					recipeByNameSlct.style.display = "block"
					recipeNameInput.style.display = "none";
					recipeSubmitBtn.style.display = "none";
					recipeComments.style.display = "none";
				}
			}
			setInstructionText("getRcp");
			showHideSbmtEdsBtnDiv("hide", mode);

//			alert("case edit done");
		}
		break;
		
		case "delete":
		{
//			alert("case delete");
			recipeNameSelect.style.display = "block";  // div containing menu
			recipeByNameSlct.style.display = "block"; // rcpSlct menu
			if(recipeImage != null)
		 		recipeImage.style.display = "none";
	 		recipeComments.style.display = "none";
	 		recipeIngredients.style.display = "none";
			recipeInstructions.style.display = "none";
	 		recipeNameInput.style.display = "none";
			recipeByNameSlct.selectedIndex = 0;
			setInstructionText("rcpInptDelete");
			showHideSbmtEdsBtnDiv("hide", mode);
//			alert("case delete done");
		}
		break;
		
		default:
		{
//		alert("case default");
			setInstructionText("");
			showHideSbmtEdsBtnDiv("hide", mode);
	 		recipeComments.style.display = "none";
			if(recipeImage != null)
		 		recipeImage.style.display = "none";
			recipeNameSelect.style.display = "none";
			recipeIngredients.style.display = "none";
			recipeInstructions.style.display = "none";
			recipeNameInput.style.display = "none";
 			recipeSubmitBtn.style.display = "none";
			submitEditsBtn.value = "Get Recipe";
//		alert("case default done");
		}
	}
	
//	alert("leaving toggleMode()");
}

function setSbmtBtnValue(mode)
{
//	alert("in setSbmtBtnValue()");
	var submitEditsBtn = document.getElementById("submitEditsBtn");
	
	switch(mode)
	{
		case "add":
		submitEditsBtn.value = "Add Recipe";
		break;
		
		case "edit":
		var rcpId = getUrlParam("rcpId");
		if(rcpId == null)
			submitEditsBtn.value = "Edit Recipe";
		else
			submitEditsBtn.value = "Get Recipe";
		break;
		
		case "delete":
		submitEditsBtn.value = "Delete Recipe";
		break;
		
		default:
		submitEditsBtn.value = "Recipe Update Button";
	}
//	alert("leaving setSbmtBtnValue()");
}

function getSelectedIndex(recipeByName)
{
//	alert("in getSelectedIndex(" + recipeByName + ")");
	
	var recipeByNameSlct = document.getElementById("recipeByNameSlct");
	var i = 0;
	var idx = 0

	for(i = 0; i < recipeByNameSlct.options.length; i++)
	{
		var rcpName = recipeByNameSlct.options[i].text;
//		alert("1. recipeByNameSlct.options[i].text = " + recipeByNameSlct.options[i].text);
		
		if(rcpName == decodeHtmlString(recipeByName))
		{
//			alert("2. " + 
//				  "\nrcpName = " + rcpName +
//				  "\nidx = " + idx);
			idx = i;
			break;
		}			
	}

//	alert("leaving getSelectedIndex(" + rcpName + "); " + "slctedIdx = " + idx);
	return idx;
}

function decodeHtmlString(string)
{
//	alert("arriving in decodeHtmlString(" + string + ")");
	var paramMap = [
		 			[" ", "%20"],
					["!", "%21"],
					["\"", "%22"],
		 			["#", "%23"],
		 			["$", "%24"],
					["&",	"%26"],
					["'", "%27"],
					["(", "%28"],
					[")", "%29"],
					["*", "%2A"],
					["+",	"%2B"], 
					[",",	"%2C"], 
					["/",	"%2F"], 
					[":", "%3A"],
					[";", "%3B"],
					["<", "%3C"],
					["=",	"%3D"], 
					[">", "%3E"],
					["?",	"%3F"], 
					["@",	"%40"], 
					["[", "%5B"],
					["]", "%5D"]
 				   ];
 				   
	var decodeString = string;

	for(i = 0; i < paramMap.length; i++)
	{
		var spclChar = paramMap[i][1];
		var rplcChar = paramMap[i][0];
		while(decodeString.indexOf(spclChar) != -1)
			decodeString = decodeString.replace(spclChar, rplcChar);
	}
//	alert("leaving decodeHtmlString()");

	return decodeString;
}
