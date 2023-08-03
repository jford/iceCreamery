function setMode(mode)
{
//	alert("entering setMode(), mode is " + mode);
	
	toggleMode(mode);
	
//	alert("leaving setMode()");
}

function showHideSplrTbl(showHide)
{
//	alert("in showHideSplrTbl()");
	
	var showSplrTblBtn = document.getElementById("showSplrTblBtn");
	var splrTblDiv = document.getElementById("splrTblDiv");
	
//	var submitButton = document.getElementById('submitButton');
//  submitButton.setAttribute('onclick',  'alert("hello");');
	
	
	switch(showHide)
	{
		case "show":
		showSplrTblBtn.value = "Hide Supplier Table";
		showSplrTblBtn.setAttribute("onclick", "showHideSplrTbl('hide')");
		splrTblDiv.style.display = "block";
		break;
		
		case "hide":
		showSplrTblBtn.value = "Show Supplier Table";
		showSplrTblBtn.setAttribute("onclick", "showHideSplrTbl('show')");
		splrTblDiv.style.display = "none";
		break;
	}
//	alert("leaving showHideSplrTbl()");
}

function showHideAddNewSplrDiv(showHide)
{
//	alert("in showHideAddNewSplrDiv()");
	var addNewSplrDiv = document.getElementById("addNewSplrDiv");
	
	switch(showHide)
	{
		case "show":
		addNewSplrDiv.style.display = "block";
		break;
		
		case "hide":
		addNewSplrDiv.style.display = "none";
		break;
	}
//	alert("leaving showHideAddNewSplrDiv()");
}

function showHideAddNewIngToSplrDiv(showHide)
{
//	alert("in showHideAddNewIngToSplrDiv()");
	
	var addNewIngToSplrDiv = document.getElementById("addNewIngToSplrDiv");
	
	switch(showHide)
	{
		case "show":
		addNewIngtoSplrDiv.style.display = "block";
		break;
		
		case "hide":
		addNewIngToSplrDiv.style.display = "none";
		break;
	}
//	alert("leaving showHideAddNewIngToSplrDiv()");
}
function toggleMode(mode)
{
//	alert("entering ingEditor.toggleMode(" + mode + ")");
	
	var addIngDiv = document.getElementById("addIngDiv");
	var addNewIngToSplrDiv = document.getElementById("addNewIngToSplrDiv");
	var addNewSplrDiv = document.getElementById("addNewSplrDiv");
	var delIngDiv = document.getElementById("delIngDiv");
	var editIngDiv = document.getElementById("editIngDiv");
	var editIngName = document.getElementById("editIngName");
	var editIngNameLabel = document.getElementById("editIngNameLabel");
	var editInputsDiv = document.getElementById("editInputsDiv");
	var formCtlsDiv = document.getElementById("formCtlsDiv");
	var formSubmitBtn = document.getElementById("formSubmitBtn");
	var ingName = getPageUrlParam("ingName");
	var ingSlct = document.getElementById("ingSlct");
	var newIngName = document.getElementById("newIngName");
	var splrTblDiv = document.getElementById("splrTblDiv");
	var newSplrAdded = getPageUrlParam("newSplrAdded");
	
	addIngDiv.style.display = "none";
	delIngDiv.style.display = "none";
	editIngDiv.style.display = "none";
//	editIngName.style.display = "none";
//	editIngNameLabel.style.display = "none";
	formCtlsDiv.style.display = "none";
	editInputsDiv.style.display = "none";
	newIngName.style.display = "none";
	
	switch(mode)
	{
		case "add":
		addIngDiv.style.display = "block";
		formCtlsDiv.style.display = "inline";
		newIngName.style.display = "block";
		setInstructionText("ingAdd");
//		alert("case add complete");
		break;
		
		case "edit":
		editIngDiv.style.display = "block";
		formCtlsDiv.style.display = "none";
		setInstructionText("ingEdit");
		if( ingName != null && !ingName == "")
		{
//			alert("ingName = " + ingName);
			formCtlsDiv.style.display = "block";
			ingSlct.style.display = "none";
			editInputsDiv.style.display = "block";
			setInstructionText("ingEdit2");
		}
		splrTblDiv.style.display = "none";
		addIngDiv.style.display = "none";
		addNewSplrDiv.style.display = "none";
		// newSplrAdded url param will have content only after
		// return from adding a new supplier 
		if(newSplrAdded == "")
			addNewIngToSplrDiv.display = "block";
		else
		{
			var showHide = newSplrAdded = "true" ? "block" : "none";
			addNewIngToSplrDiv.display = showHide;
		}
		break;
		
		case "delete":
//		alert("case delete");
		setInstructionText("ingDelete");
		delIngDiv.style.display = "block";
		formCtlsDiv.style.display = "inline";
//		alert("case delete complete");
		break;
		
		default:
//		alert("case default");
		setInstructionText("");
		addIngDiv.style.display = "none";
		addNewIngToSplrDiv.style.display = "block";
        addNewSplrDiv.style.display = "none";
		delIngDiv.style.display = "none";
		editIngDiv.style.display = "none";
		formCtlsDiv.style.display = "none";
		splrTblDiv.style.display = "none";
//		alert("case default complete");
	}
	
//	alert("leaving toggleMode()");
}

function resetMode()
{
//	alert("in ingEditor->resetMode()");

	var currentLocation = window.location.toString();
	var currentParams = currentLocation.split('?')[1];
	var newParams = "?";
	var iParams = currentParams.split('&');
	for(var i = 0; i < iParams.length; i++)
	{
		if(iParams[i].startsWith("ingType" )
		   || iParams[i].startsWith("ingCategory"))
		   newParams += iParams[i] + '&';
	}
	var idx = newParams.lastIndexOf('&')
	if(idx = newParams.length)
		newParams = newParams.substring(0, idx - 1);
		
	var newLocation = currentLocation.split('?')[0] + newParams;
	
//	alert("leaving ingEditor->resetMode()");

	window.location = newLocation;
}



function showHideEditInputsDiv(showHide)
{
//	alert("in showHideEditInputsDiv(" + showHide + ")");

	var editInputsDiv = document.getElementById("editInputsDiv");
	var curVal = editInputsDiv.style.display;
	var formCtlsDiv = document.getElementById("formCtlsDiv");
	
	switch(showHide)
	{
		case "show":
//		alert("case \"show\"");
		editInputsDiv.style.display = "block";
		formCtlsDiv.style.display = "block";
		ingSlct.style.display = "none";
//		alert("case \"show\" ... break");
		break;
		
		case "hide":
//		alert("case \"hide\"");
		editInputsDiv.style.display = "none";
		formCtlsDiv.style.display = "none";
		ingSlct.style.display = "block";
//		alert("case \"hide\" ... break");
		break;
		
		default:
		editInputsDiv.style.display = curVal;
	}

//	alert("leaving showHideEditInputsDiv()");
}

function setInstructionText(activity)
{
//	alert("in setInstructionText(" + activity + ")");

	var ingType = getPageUrlParam("ingType");
	var instructionText = document.getElementById("instructionText");
	var text = "";
	
	switch(activity)
	{
		case "ingAdd":
		text = "Enter a name for the new <i>" + ingType + "</i> ingredient, then click <b>Submit</b> " +
		       "to add the ingredient to the Ice Creamery database. Click <b>Cancel</b> to clear " + 
		       "the field."; 
		break;
		
		case "ingEdit":
		text = "Choose a <i>" + ingType + "</i> ingredient from the menu and click <b>Submit</b> to " +
		"display input fields for name and category.";
		break;
		
		case "ingEdit2":
		text = "Enter a different name, and/or choose a different category for the " +
		       "ingredient, then click <b>Submit</b> to register the changes in the Ice Creamery " +
		       "database. <br/><br/><i>Id</i> is not editable.<br/><br/>Changing type may have " +
		       "unintended consequences. A recipe that calls for 1/2 cup of an ingredient " +
		       "originally defined as a sugar but that is subsequently changed to a salt " +
		       "will call for 1/2 cup of the salt until the recipe is manually updated.";
		break;
		
		case "ingDelete":
		text = "Choose an ingredient from the menu and click <b>Submit</b> to permanently remove " + 
		       "the ingredient from the database. Cancel clears your selection without taking action.";
		break;
		
		default:
		text = "Click <b>Add</b> to create a new ingredient of type <i>" + ingType + "</i>, <b>Edit</b> to " +
		       "change the name of an existing ingredient or move it to a different ingredient " +
		       "category, or <b>Delete</b> to permanently remove the ingredient from the Ice Creamery " +
		       "database. Click <b>Reset</b> at any time to return to this page.";
	}
	
	instructionText.innerHTML = text;

//	alert("leaving setInstructionText()");
}

function checkSlctMnuOptions(ingType, numOptions)
{
	alert("in checkSlctMnuOptions(); ingType = " + ingType + ", numOptions = " + numOptions);
	
	var ingNameSlctTag = document.getElementById("ingNameSlctTag");
	var ingSlctSpan = document.getElementById("ingSlctSpan");
	
	if(numOptions == 1)
	{
		ingSlctSpan.innerHTML = "<p class=\"ingSlctMnuEmpty\" id=\"ingSlctMnuEmpty\">No " + ingType + " items defined.</p>";
	}

	alert("leaving checkSlctMnuOptions()");
}

function checkDisplayValue(item)
{
	alert(item.value + ".style.display = " + item.style.display);
}

function clearMsgParam()
{

}