function enableGetTextBtn()
{
//	alert("in enableGetTextBtn()");
	var formSbmtBtn = document.getElementById("formSbmtBtn");
	var editScopes = document.getElementsByName("editScope");
	
	var count = 0;
	var length = editScopes.length;
	
//	alert("length = " + length);	
	
	formSbmtBtn.disabled = false;
//	alert("formSbmtBtn.disabled = " + formSbmtBtn.disabled);
	
	for(count = 0; count < editScopes.length; count++)
	{
//		alert("count = " + count);	
		editScopes[count].disabled = true;
	}
	
//	alert("leaving enableGetTextBtn()");
}

function getScope()
{
//	alert("in getScope()")
	var editScopes = document.getElementsByName("editScope");
	var scope = document.getElementById("scope");
	var count = 0;
	var length = editScopes.length;
	
	for (count = 0; count < length; count++)
	{
		if(editScopes[count].checked == true)
		{
			scope.value = editScopes[count].value;
			break;
		}
	}
//	alert("leaving getScope()");
}

function enableSaveSelection()
{
	alert("entering enableGetTextBtn()");
	
	var getTextBtn = document.getElementById("getTextBtn");
	var saveSelection = document.getElementById("saveSelection")
	var editScopes = document.getElementsByName("editScope");
	
	var count = 0;
	
	saveSelection.disabled = false;
	getTextBtn.disabled = true;
	
	for(count = 0; count < editScopes.length; count++)
	{
		editScopes[count].disabled = false;
	}
	
	alert("leaving enableSaveSelection()");
}

function displayRecipeMenu()
{
//	alert("entering displayRecipeMenu()");
	menu = document.getElementById("recipeByName");
//	alert("menu.hidden = " + menu.hidden);
	menu.hidden = false;
//	alert("menu.hidden = " + menu.hidden);
//	alert("leaving displayRecipeMenu()");
}

function displayMenu(which)
{
//	alert("entering displayMenu(" + which + ")");
	
	var menu = null;
	
	switch(which)
	{
		case "recipe":
		menu = document.getElementById("recipeByName");
		break;
		
		case "ingredient":
		menu = document.getElementById("ingByName");
		break;
	}
	if(menu != null)
	{
//		alert("menu.hidden = " + menu.hidden);
		menu.hidden = false;
	}
//	alert("menu.name = " + menu.name + "\nmenu.hidden state = " + menu.hidden);
//	alert("leaving displayRecipeMenu()");
}

//function setMode(newmode)
//{
//	alert("entering setMode(" + newmode + ")");
//	byNameMenu = document.getElementById("byNameMenu");
//	getAllXml = document.getElementById("getAllXml");
//	enableEditSelection();
//	
//	switch(newmode)
//	{
//		case "showAllXml":
//		byNameMenu.style.display = "none";
//		break;
//		
//		case "showRecipeByName":
//			getAllXml.style.display = "none";
//		break;
//	}
// 	alert("leaving setMode(" + newmode + ")");
//}
//
function setRecipeByNameMenu(recipeByName)
{
//	alert("arriving in setRecipeByNameMenu(" + recipeByName + ")");
	
	byNameMenu = document.getElementById("recipeByName");
	if(recipeByName.length > 0)
		document.querySelector("#recipeByName").value = recipeByName;
	displayRecipeMenu();
//	alert("leaving setRecipeByNameMenu");
}