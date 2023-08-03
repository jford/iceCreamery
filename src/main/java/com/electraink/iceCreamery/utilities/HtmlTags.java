package com.electraink.iceCreamery.utilities;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.utilities.Utilities;

public class HtmlTags 
{
	private static String quotes = "\"";
	private static String newLine = DataMgr.lineBrk;
	
	public static String indent = "    ";
	public static String indentRow = indent + indent;
	public static String indentCol = indentRow + indent;
	public static String indentColText = indentCol + indent;
	public static String labelTagClose="</label>";
	public static String labelTagOpen ="<label for=\"";
	public static String tableClose  = "</table>" + newLine;
	
	public static String colClose = "</td>" + newLine;
	public static String hColClose = "</th>" + newLine;
	public static String tagClose = "/>";
	public static String rowClose = "</tr>" + newLine;

	public HtmlTags ()
	{
	}
	
	public static String getMenuDefaultOption()
	{
	    String mnuOpt = "\t\t<option value=\"" + DataMgr.defaultSelectId + "\" >" +
                DataMgr.defaultSelectOption +
                "</option>\n";

	    return mnuOpt;
	}
	
	public static String getColSpanTag(String cssClass, String cssId, String colSpan)
	{
		/*
		 * returns a td tag with colspan arg set to colSpan
		 */
		String spanArg = " colspan=\"" + colSpan + "\" ";
		StringBuffer tag = new StringBuffer(getTag("td", cssClass, cssId));
		tag.insert(tag.indexOf(">"), spanArg);
		return tag.toString();
	}
	
	public static String getTag(String type, String cssClass, String cssId)
	{
		// type should be one of:
		// - table
		// - tr
		// - td
		// - th
		//
		//    --or--
		//
		// use getThTag() and getTdTag() to specify width attribute in the tag
		
		String tag = "<" + type;
		
		// if cssClass is empty, tag arguments will not be included
		if(!cssClass.isEmpty())
		{
			cssClass = quotes + cssClass + quotes;
//				tag += " class=" + cssClass + " name=" + cssClass + " id=" + cssClass;
			tag += " class=" + cssClass;
		}
		if(!cssId.isEmpty())
		{
			cssId = quotes + cssId + quotes;
			tag += " id=" + cssId;
		}

		tag += ">" + newLine; 

		return tag;
	}
	
	public static String getTdTag(String cssClass, String cssId, String width)
	{
		// get td tag with width specified (can also use getTag() and 
		// specify width in css style section
		StringBuffer tag = new StringBuffer(getTag("td", cssClass, cssId));
		int idx = tag.indexOf(">");
		tag.insert(idx, " width=\"" + width + "\" ");
		return tag.toString();
	}
	
	public static String getThTag(String cssClass, String cssId, String width)
	{
		// can also use getTag(), and specifi width in css style section
		String tag = getTdTag(cssClass, cssId, width);
		tag = Utilities.replaceChars(tag, "td", "th", "first");
		return tag;
	}
	
	public static String getTextAreaField(String cssClass, String rows, String cols, String value)
	{
		/*
		 * Arguments:
		 * 
		 * - cssClass: required value, cannot be empty or null; used for class, name, and id values
		 * - rows:     can be empty or null; used for value of rows attribute
		 * - cols:     can be empty or null; used for value of cols attribute
		 */
		String tag = "<textarea name=\"" + cssClass + "\" ";
		if(!rows.isEmpty())
			tag += "rows=\"" + rows + "\" ";
		if(!cols.isEmpty())
			tag += "cols=\"" + cols + "\" ";
		tag += ">";
		if(!value.isEmpty())
			tag += value;
		tag += "</textarea>";
		
		return tag;
	}

//	public static String getTextField(String cssClass, String cssId, String size, String value, String formaction, String method)
	public static String getTextField(String displayName, String cssRoot, String size, String value, String formaction, String method)
	{
//	        - value      can be "" or null; input's value arg---if null or "", arg will not be included 
//	                     in input tag
//	
//	        - formaction can be "" or null; action to be taken when input (submit?) is clicked; neither 
//	                     formaction or method args will be included if formaction is null or ""
	    
		String cssClass = cssRoot + "TextField";
		String cssId = cssRoot + value;
		
		// neutralize nulls
		formaction = formaction == null ? "" : formaction;
		method = method == null ? "" : method;
		value = value == null ? "" : value;
		size = size == null ? "" : size;
		
		String inputTag = "<input type=\"text\" " +
	                    "name=\"" + cssClass + "\" " +
				        "id=\"" + cssId + "\" ";
		if(!value.isEmpty())
		{
			inputTag += "value=\"" + value + "\" ";
		}
		if(!size.isEmpty())
		{
			inputTag += "size=\"" + size + "\" ";
		}
		if(!formaction.isEmpty())
		{
			inputTag += "formaction=\"" + formaction + "\" ";
			if(!method.isEmpty())
				inputTag += "method=\"" + method + "\" ";
		}
		inputTag += "/>";

		String labelTag = labelTagOpen +
		          cssId + 
		          "\">" +
		          displayName + 
		          labelTagClose;

		
		return labelTag + inputTag;
	}
	
	public static String getInput(String displayName, 
			                      String type, 
			                      String name, 
			                      String cssClass, 
			                      String cssId, 
			                      String value)
	{
		String inputTag = "<input type=\"" +
	                      type +
	                      "\" name=\"" +
	                      name +
	                      "\" class=\"" +
                          cssClass +
	                      "\" id=\"" +
	                      cssId +
	                      "\" value=\"" +
	                      value +
	                      tagClose;
		
		String labelTag = labelTagOpen +
				          cssId +
				          "\">" +
				          displayName +
				          labelTagClose;
		
		return inputTag + labelTag;
	}
	
	public static String getCheckbox(String displayName, 
			                         String name, 
                                     String cssClass, 
                                     String cssId, 
                                     String value,
                                     String checked)
	{
//		String checked = isChecked ? "" : "checked";
		String inputTag = "<input  type=\"checkbox\" " +
                          "name=\"" +
                          name +
                          "\" class=\"" +
                          cssClass +
                          "\" id=\"" +
                          cssId +
                          "\" value=\"" +
                          value +
                          "\" " +
                          checked + 
                          tagClose;
		
		String labelTag = labelTagOpen +
				          cssId + 
				          "\">" +
				          displayName + 
				          labelTagClose;
		
		return inputTag + labelTag;
	}
	
	public static String getRadioBtn(String displayName, 
			                         String name, 
                                     String cssClass, 
                                     String cssId, 
                                     String value,
                                     boolean isChecked)
	{
		String checked = isChecked ? "" : "checked";
		
		String inputTag = "<input type=\"radio\" \"name=\"" +
                          name +
                          "\" class=\"" +
                          cssClass +
                          "\" id=\"" +
                          cssId +
                          "\" value=\"" +
                          value +
                          "\" " +
                          checked + 
                          "/>";
		
		String labelTag = "<label for=\"" +
                          cssId +
                          "\">" + 
                          displayName + 
                          "</label>" +
                          newLine;
		
		return inputTag + labelTag;
	}
	
}
