function showTitlePage()
{
	document.getElementById("title_page_display").style.display = "inline";
}

function hideTitlePage()
{
	document.getElementById("title_page_display").style.display = "none";
}

function get_doc(doc)
{
   document.location = doc;
}

function c_to_f(centigrade)
{
   fahr = 0;
   fahr = (centigrade.value * 9/5) + 32;
   alert(centigrade.value + " degrees Centigrade = " + fahr + " degrees Fahrenheit");  
}

function f_to_c(fahrenheit)
{
   cent = 0;
   cent = (fahrenheit.value - 32) * 5/9; 
   alert(fahrenheit.value + " degrees Fahrenheit = " + cent.toPrecision(3) + " degrees Centigrade");
}

// target="_blank" substitute; target not allowed in HTML 4.0 Strict
function target_sub()
{
   // dom capable browser?
   if(!document.getElementsByTagName) return;
   
   // yes, get list of anchor tags
   var anchors = document.getElementsByTagName("a");
   for(var i=0; i < anchors.length; ++i)
   {
      var anchor = anchors[i];
      if(anchor.getAttribute("href") && (anchor.getAttribute("rel") == "external"))
      {
         anchor.target = "_blank";
      }
   }

}

function setGender(gender)
{
	var genderMaleButton = document.getElementById("male");
	var genderFemaleButton = document.getElementById("female");
	
	if(gender == "male")
		genderMaleButton.checked = true;
	else if(gender == "female")
		genderFemaleButton.checked = true;
}

function newUserMsg()
{
	var msg = "ElectraInk's Interactive Book Editor provides the means by which you can edit and modify " + 
	          "published works of literature. The literary works provided by ElectraInk, which serve " +
	          "as templates that you can modify using the eBook Editor application, " +
	          "are either in the public domain, or permission for use has been granted by the copyright " +
	          "holder. Tools are also provided by which you can upload other works, to create your own " +
              "templates.\n\n" +
              "It is ElectraInk's intention that users of these eBook editing tools be restricted to " + 
              "working only on those works for which the user has the legal right to modify. By " + 
              "creating a user account, you are consenting to abide by this intention.\n\n" +
	          "Click OK to accept this condition and proceed to create a user account, or click " +
	          "Cancel to  decline.";

	return confirm(msg);
}

function displayHelp(helpFile)
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var textsearchWindow = window.open("help/" + helpFile, "helpWindow", strWindowFeatures);
}

function displayMsg()
{
	if(location.search)
	{
		var show = location.search.substring(1).split("=");
		
		alert("show = " + show.value);
		alert("show[0] = " + show[0] + "\nshow[1] = " + show[1]);
		
		if(show[0] == "msg")
			alert(decodeMsg(show[1]));
	}
}

function displayTextblock(descriptorCount, manuscriptId)
{
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,top=10,left=10";
	var textblockWindow = window.open("showFullText.jsp?manuscriptId=" + manuscriptId + "&descriptorCount=" + descriptorCount, "textblockWindow", strWindowFeatures);
}

function validateForgotPasswordUserId()
{
	var ret = true;

	var userId = document.getElementById("userId");

	if(userId.value.localeCompare("") == 0)
	{
		alert("Enter a user name.");
		ret = false;
	}
	return ret;
}

function validateSignupData()
{
	var newPassword = document.getElementById("signupNewPassword").value;
	var confirmPassword = document.getElementById("signupConfirmPassword").value;
	var emailAddr = document.getElementById("signupEmailAddr").value;
	var ret = true;
	var idx = 0;
	
	if(newPassword.localeCompare(confirmPassword) != 0)
	{
		alert("Passwords do not match.");
		ret = false;
	}
	if((idx = emailAddr.indexOf("@")) == -1 ||
	    emailAddr.indexOf(".") == -1)
	{
		alert("You must provide a valid email address.")
		ret = false;
	}
	return ret;
}

function stringStartsWith(string, prefix)
{
	return string.slice(0, prefix.length) == prefix;
}

function stringEndsWith(string, suffix)
{
	return suffix == '' || string.slice(-suffix.length) == suffix;
}

function whyUserAccounts()
{
	var msg =
		"Why do you need a user account to use a free program?\n\n" +
	    "There's really nothing to hide. There's no credit card information \n" +
	    "involved, no personal data to protect. \n\n" +
	    "So why do you need a password-protected user account to access \n" +
	    "the Web Scraper?\n\n" +
	    "Convenience.\n\n" +
	    "User accounts provide a convenient way to compartmentalize files so \n" +
	    "that multiple users aren't tripping over each other's data.\n\n" +
	    "Your data set is accessible only to your user account so that your \n" +
	    "entries don't show up on my watch list and vice versa. \n\n" +
	    "So, user accounts.";
	
	alert(msg);
}

function numInRange(totalNum, deleteNum)
{
	var ret = true;
	deleteNum = deleteNum.trim();
	
	if(deleteNum % 1 != 0 || 
	   parseInt(deleteNum, 10) > totalNum ||
	   deleteNum.indexOf(",") != -1 ||
	   deleteNum.indexOf(" ") != -1 ||
	   deleteNum.indexOf(".") != -1 || 
	   deleteNum.indexOf("-") != -1)
	{
		alert("Invalid entry (whole numbers---integers---only, no commas, no periods, must be in valid range).");
		ret = false;
	}
	else if(parseInt(deleteNum, 10) == 0 || 
		isNaN(parseInt(deleteNum)))
	{	
		alert("No number entered, nothing to do.")
		ret = false;
	}
	return ret;
}

// webscraper functions

function setDisplayType()
{
//	alert("1. Entering setDisplayType()");

	var prvwDisplayType;
	if(document.getElementById("clearText").checked)
		prvwDisplayType = "clearText";
	else
		prvwDisplayType = "htmlMarkup";
	
//	alert("2. prvwDisplayType = " + prvwDisplayType);
	
	var watchedSiteDisplayType = document.getElementById("watchedSiteClearText");
	
//	alert("3. watchedSiteDisplayType = " + watchedSiteDisplayType);
	
	document.getElementById("selectedDisplayType").value = prvwDisplayType;
	
//	alert("4. selectedDisplayType.value = " + prvwDisplayType);
	
	document.getElementById("selectedWatchedSiteDisplayType").value = watchedSiteDisplayType;
	
//	alert("5. selectedWatchedSiteDisplayType.value = " + prvwDisplayType);
	
}

function setRadioButtons(selectedPreviewRadioButton, selectedWatchedSiteRadioButton)
{
//	alert("1. Here I am, entering setRadioButtons()\n" +
//		  "\nselectedPreviewRadioButton = " + selectedPreviewRadioButton +
//		  "\nselectedWatchedSiteRadioButton = " + selectedWatchedSiteRadioButton);

	document.getElementById(selectedPreviewRadioButton).checked=true;
//	alert("1.5 Anybody home?");
	document.getElementById(selectedWatchedSiteRadioButton).checked=true;

//	alert("2. Now leaving setRadioButtons()"  +
//			"\nselectedPreviewRadioButton = " + selectedPreviewRadioButton + 
//		  "\nselectedWatchedSiteRadioButton = " + selectedWatchedSiteRadioButton);
}

function setWatchedSiteDisplayType()
{
//	alert("Entering setWatchedSiteDisplayType()");
	var displayType = document.getElementById("watchedSiteClearText").checked ? "clearText" : "htmlMarkup";
	document.getElementById("selectedWatchedSiteDisplayType").value = displayType;
	return displayType;
}

// end webscraper functions

// login.html functions

function confirmEmailFormat(emailAddr)
{
	var ret = true;
	var idx = 0;
	
	if(idx = emailAddr.value.indexOf("@") == -1)
		ret = false;
	else if(emailAddr.value.indexOf(".", idx) == -1)
		ret = false;
	if(ret == false)
		alert("An e-mail address is required.");
	return ret;
}

function confirmPasswordEntry(pword, cword)
{
	var yesno = "";
	var ret = true;

	if(pword.value == "" || cword.value == "")
	{
		alert("Please enter password twice to confirm spelling.");
		ret = false
	}
	else
	{
		if(pword.value.localeCompare(cword.value) != 0)
		{
			alert("Password entries do not match.");
			ret = false;
		}
	}
	return ret;
}

function aboutApp()
{
	var version   = "0.1";
	var buildDate = "March 2018";
	
	var msg = "WebScraper version " + version + "\n" +
			"Build date: " + buildDate;
	
	alert(msg);
}

function togglePreviewPane()
{
	var url = document.getElementById("siteUrl");
	if(url.value.length != 0)
	{
		var previewPane = document.getElementById("contentDisplayArea");
		if(previewPane.style.visibility === "hidden" || previewPane.style.visibility == "")
		{
			previewPane.style.visibility = "visible";
		}
		else
		{
			previewPane.style.visibility = "hidden";
		}
	}
}
function setPaneStates(prvwState, watchedState)
{
//	alert("1. Arriving in setPaneStates()\nvalue of watchedState = " + watchedState + 
//			"\nvalue of prvwState = " + prvwState);
	
	var previewPane = document.getElementById("contentDisplayArea");
	var watchedPane = document.getElementById("watchedSiteDisplayArea");
	var addToWatchListBtn = document.getElementById("addToWatchListBtn");

	previewPane.style.display = prvwState;
	watchedPane.style.display = watchedState;
	
	// enable add to watchlist button if preview pane is visible
	if(prvwState == "inline")
	{
		addToWatchListBtn.disabled = false;
		addToWatchListBtn.style.background = "lightgreen";
	}
	else
	{
		addToWatchListBtn.disabled = true;
	}
	
//	alert("2. Leaving setPaneStates();\n" +
//			"watchedPane.style.display = " + watchedPane.style.display +
//			"\npreviewPane.style.display = " + previewPane.style.display);
}

function showPreviewPane()
{ 
	var urlElement = document.getElementById("siteUrl");
	alert("here I am, in showPreviewPane()\r\n" +
			"siteUrl.value.length = " + urlElement.value.length);
	var url = document.getElementById("siteUrl");
	if(url.value.length != 0)
	{
		var previewPane = document.getElementById("contentDisplayArea");
		previewPane.style.visibility = "visible";
		alert("here I am, in showPreviewPane()\r\n" +
				"document.getElementById('contentDisplayArea') = " + 
						document.getElementById("contentDisplayArea").style.visibility);
	}
}
function hidePreviewPane()
{
	alert("here I am, in hidePreviewPane()\r\n" +
			"document.getElementById('contentDisplayArea') = " + 
					document.getElementById("contentDisplayArea").style.visibility);
	var previewPane = document.getElementById("contentDisplayArea");
	var documentAddress = document.getElementById("siteUrl");
	previewPane.style.visibility = "hidden";
	alert("here I am, in hidePreviewPane()\r\n" +
			"document.getElementById('contentDisplayArea') = " + 
					document.getElementById("contentDisplayArea").style.visibility);
	documentAddress.value = "";
}

function validateWebsiteSelection()
{
	var websiteSelection = document.getElementById("websiteSelection");
	var selection = websiteSelection.options[websiteSelection.selectedIndex].value;
	var showSiteTextBtn = document.getElementById("showSiteText");
	var clearWatchedSitePaneBtn = document.getElementById("clearWatchedSitePane");
	var removeSiteBtn = document.getElementById("removeSite");

	if(selection != "Select...")
	{
		showSiteTextBtn.style.background = "lightgreen";
		showSiteTextBtn.disabled = false;
		clearWatchedSitePaneBtn.style.background = "lightgreen";
		clearWatchedSitePaneBtn.disabled = false;
		removeSiteBtn.style.background = "lightgreen";
		removeSiteBtn.disabled = false;
	}
	else
	{
		showSiteTextBtn.style.background = "lightgrey";
		showSiteTextBtn.disabled = true;
		clearWatchedSitePaneBtn.style.background = "lightgrey";
		clearWatchedSitePaneBtn.disabled = true;
		removeSiteBtn.style.background = "lightgrey";
		removeSiteBtn.disabled = true;
	}
	if(watchedSiteTextPane.value.length > 0)
	{
//		alert("Here I am, in validatePreviewUrlEntry()'s if(watchedSiteTextPane.value.length > 0)");
		clearWatchedSitePaneBtn.style.background = "lightgreen";
		clearWatchedSitePaneBtn.disabled = false;
	}
}

function showEmbeddedLinks(embeddedLinksFile)
{
//	alert("1. From showEmbeddedLinks(), embeddedLinksFile = " + embeddedLinksFile);
	var strWindowFeatures = "scrollbars=yes,resizable=yes,width=500,height=500,left=20,top=20";
	var textsearchWindow = window.open("help/" + embeddedLinksFile, "helpWindow", strWindowFeatures);

}
function validatePreviewUrlEntry()
{
//	alert("Here we are in validatePreviewUrlEntry()");
	
	var previewUrlEntry = document.getElementById("siteUrl");
	var previewBtn = document.getElementById("previewNewUrl");
	var cancelPrvwBtn = document.getElementById("cancelPreview");
	var addToWatchListBtn = document.getElementById("addToWatchListBtn");
	
	var watchedSiteTextPane = document.getElementById("watchedSiteTextPane");
	
//	alert("previewUrlEntry.value = " + previewUrlEntry.value);
	
	if(previewUrlEntry.value.length > 0)
	{
		previewBtn.style.background = "lightgreen";
		previewBtn.disabled = false;
		cancelPrvwBtn.style.background = "lightgreen";
		cancelPrvBtn.disabled = false;
	}
	else
	{
		previewBtn.style.background = "lightgrey";
		previewBtn.disabled = true;
		cancelPrvwBtn.style.background = "lightgrey";
		cancelPrvwBtn.disabled = true;
	}
}

function enableSiteUrlButtons()
{
	var previewBtn = document.getElementById("previewNewUrl");
	var cancelPrvwBtn = document.getElementById("cancelPreview");

	alert("1. Here we are, in enableSiteUrlButtons()");
	
	previewBtn.style.background = "lightgreen";
	previewBtn.disabled = false;
	cancelPrvwBtn.style.background = "lightgreen";
	cancelPrvBtn.disabled = false;
}

// end login.html functions
// window.onload = target_sub;
