package com.electraink.iceCreamery.utilities;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;

import com.electraink.iceCreamery.testKitchen.datastore.DataMgr;
import com.electraink.iceCreamery.testKitchen.datastore.Ingredient;
import com.electraink.iceCreamery.testKitchen.datastore.Ingredients;
import com.electraink.iceCreamery.testKitchen.datastore.IngredientsParser;
import com.electraink.iceCreamery.testKitchen.datastore.Recipe;
import com.electraink.iceCreamery.testKitchen.datastore.Recipes;
import com.electraink.iceCreamery.testKitchen.datastore.RecipesParser;

import jakarta.servlet.http.HttpServletRequest;

public class Utilities
{
	
	// reserved-character conversion map for encoding/decoding HTML
	private static String[][] paramMap = {
		 								  {" ", "%20"},
		 								  {"!", "%21"},
									      {"\"", "%22"},
		 								  {"#", "%23"},
		 								  {"$", "%24"},
									      {"&",	"%26"},
									      {"'", "%27"},
									      {"(", "%28"},
									      {")", "%29"},
									      {"*", "%2A"},
									      {"+",	"%2B"}, 
									      {",",	"%2C"}, 
									      {"/",	"%2F"}, 
									      {":", "%3A"},
									      {";", "%3B"},
									      {"<", "%3C"},
									      {"=",	"%3D"}, 
									      {">", "%3E"},
									      {"?",	"%3F"}, 
									      {"@",	"%40"}, 
									      {"[", "%5B"},
									      {"]", "%5D"}
 									   };

	public static String getProperty(String file, String key)
	{
	    Properties props = loadProperties(file);
	    String value = props.getProperty(key);
	    return value;
	}
	
	public static Properties loadProperties(String file)
	{
	    
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(file))
        {
            // load a properties file
            props.load(input);
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        return props;
    }

 
	public static String getId()
	{
		String id = "";
		int chr = 0;
		char ch;
		
		// id is string of alphanumeric chars 
		boolean isAlpha = false;
		for(int count = 0; count < DataMgr.idSize; count++)
		{
			Random coinFlipper = new Random();
			
			isAlpha = coinFlipper.nextInt(100) > 49 ? true : false;
			if(isAlpha)
			{
				chr = coinFlipper.nextInt(26);
				ch = (char)(chr + 97);
				id += ch;
			}
			else
			{
				chr = coinFlipper.nextInt(9);
				id += Integer.toString(chr);
			}
		}
		return id;
	}
	
	
 public static String convertTextToHtml(String text)
   {
	   // format user-input text (text input fileds in an html form) for 
	   // display in a browser
	   
	   String br = "<br/>";
	   String lr = "\r"; // line feed
	   String ln = "\n"; // carriage/cursor return
	   String pgf = "<p>"; // paragraph
	   String lpgf = "</p>"; // close paragraph
	   String pgfCombo = lpgf + ln + pgf; // end one pgf and start another
	   String pgfCombos = pgfCombo + pgfCombo; // 2 pgfCombo tags with no text between them
	   
	   int idx = 0;
	   int end = 0;
	   
	   // note: Eclipse will convert all \r chars to \n automatically, but
	   // the production code running outside of Eclipse's field will still see
	   // the \r char in the line ending \r\n marker. The code needs to handle 
	   // both cases. 
	   
	   // The Eclipse properties Interpret ASCII control characters and 
	   // Interpret Carriage Regutrn (\r) as control characters 
	   // (in Eclipse, go to menu item Window -> Preferences -> Run/Debug -> Console)
	   // is supposed to allow local control of how \r is treated 
	   // (see https://stackoverflow.com/questions/14792478/how-to-get-carriage-return-without-line-feed-effect-in-eclipse-console).
	   // But it does not seem to work, at least not in this particular iteration of the 
	   // seemingly longstanding Eclipse but.

	   while((idx = text.indexOf(lr + ln)) != -1)
	   {
		   // replace MS end of line (rn + ln) with Unix end of line (\n)
		   text = Utilities.replaceChars(text, lr + ln, ln, "all");
	   }
	   
	   while((idx = text.indexOf(ln + ln)) != -1)
	   {
		   // collapse all runs of multiple line feed chars (\n\n...) to single \n 
		   text=Utilities.replaceChars(text, ln + ln, ln,  "all");
	   }

	   // remove ln after double br tags, to prevent <p> tags being
	   // put into the middle of an extended p.text_note which places 
	   //  top and bottom of the note
	   text = Utilities.replaceChars(text, br + br + ln, br + br, "all");

	   // enclose all \n chars in html tags: </p>\n<p> (assumes
	   // each line feed comes at the end of a paragraph of text
	   // and is followed by a new paragraph; initial <p> and final 
	   // </p> tags will be added later
	   text = Utilities.replaceChars(text, ln, pgfCombo, "all");
	   
	   // need to start the text with an initial <p> tag
	   text = pgf + text;
	   
	   // if original text did not end in a line feed, there won't be an html </p> tag at the 
	   // end of the string; need to add final </p>
	   if(!text.endsWith(lpgf))
		   text += lpgf;
	   
	   // put text into a StringBuffer, to get access to StringBuffer.replace()
	   idx = 0;
	   StringBuffer text_buf = new StringBuffer(text);
	   
   	   // if somehow the text includes empty pgf/closePgf tags strung together 
	   // (pdgCombos), remove them
	   while((idx = text_buf.indexOf(pgfCombos, end)) != -1)
	   {
		   end = idx + pgfCombos.length();
		   text_buf = text_buf.replace(idx, end, pgfCombo);
		   idx = end;
	   }
			   
	   // if text includes empty html <p></p> tags (no text between them), 
	   // remove them
	   while((idx = text_buf.indexOf(pgf + lpgf)) != -1)
	   {
		   end = idx + pgf.length() + lpgf.length();
		   text_buf = text_buf.replace(idx, end, "");
		   idx = end;
	   }
	   
	   // if end user has entered <p> tags, there will be double 
	   // instances of <p><p> tags; need to condense to a single tag; however, 
	   // need to accomodate mser-entered p-tag with attribues <p-space 
	   // means the user-entered tag must be preserved and the <p> tag is 
	   // the one that should be removed 
	   while(((idx = text_buf.indexOf(pgf + pgf)) != -1) ||
			   ((idx = text_buf.indexOf(pgf + "<p ")) != -1))
	   {
		   
		   end = idx + pgf.length();
		   // remove the first one (<p tag with attrs will follow the <p> tag
		   // with no attrs
		   text_buf = text_buf.replace(idx, end, "");
		   idx = end;
	   }
	   
	   // ...and any </p>\n</p> (close paragraph / line feed / close paragraph) 
	   // clusters
	   while((idx = text_buf.indexOf(lpgf + ln + lpgf)) != -1)
	   {
		   end = idx + lpgf.length() + ln.length();
		   text_buf = text_buf.replace(idx, end, "");
		   idx = end;
	   }
	   
	   // f there are still back-to-back <p>\n<p> tags,
	   // one of the <p> tags must have attributes that should be preserved
	   while((idx = text_buf.indexOf(pgf + ln + "<p")) != -1)
	   {
		   end = idx + pgf.length() + ln.length();
		   text_buf = text_buf.replace(idx, end, "");
		   idx = end;
	   }

	   // in case new multi line runs (\n\n...) were created during the processing
	   // of tags, repeat the test to reduce multi-line runs to a single \n...
	   while((idx = text.indexOf(ln + ln)) != -1)
	   {
		   // collapse all runs of multiple \n chars to single \n 
		   text=Utilities.replaceChars(text, ln + ln, ln,  "all");
	   }

	   return text_buf.toString();
   }
   
   public static String read_file(String filename)
   throws FileNotFoundException, Exception
   {
      File fin = new File(filename);
      FileInputStream fread = new FileInputStream(fin);
      int len = (int)fin.length();
      byte incoming[] = new byte[len];
      String content = "";

      try
      {
         if(fread != null)
         {
            fread.read(incoming, 0, len);
            content = new String(incoming);
         }
      }
      catch(FileNotFoundException e)
      {
         System.out.println("Unable to open file " + filename);
         e.printStackTrace();
      }
      catch(Exception e)
      {
         System.out.println("Unable to read " + filename);
         e.printStackTrace();
      }
      finally
      {
         if(fread != null)
            fread.close();
      }
      return content;
   }
   
   public static String convertNumberToRomanNumeral(int number)
   {
	   String romanNumeral = "";
	   int multiplier = 0;
	   
	   String[] zeroToTen = {"","i","ii","iii","iv","v","vi","vii","viii","ix","x"};
	   if(number >= 10)
		   multiplier = 1;
	   if(number >= 20)
		   multiplier = 2;
	   if(number >= 30)
		   multiplier = 3;
	   if(number >= 40)
		   multiplier = 4;
	   if(number >= 50)
		   multiplier =5;
	   int remainder = number % 10;
	   
	   for(int count = 0; count < multiplier; count++)
	   {
		   romanNumeral += zeroToTen[10];
	   }
	   
	   if(remainder > 0)
		   romanNumeral += zeroToTen[remainder];
	   
	   return romanNumeral;
   }

   public static void write_file(String filename, String content, boolean verbose)
   {
      FileWriter fwrite;
      try
      {
    	 fwrite = new FileWriter(filename);
         fwrite.write(content, 0, content.length());
         fwrite.flush();
         fwrite.close();
      }
      catch(Exception e)
      {
    	  System.out.println("Error writing " + filename + " file\n");
      }
   }
  // convenience method, allows char replace in Strings w/o using regex,  
  // which String class uses (this function uses StringBuffer instead);
  // regex replace doesn't appear able to handle escape char as a char
  public static String replaceChars(String base, 
                                    String target, 
                                    String substitute, 
                                    String range)
  {
     StringBuffer base_buf = new StringBuffer(base);
     int idx = 0;
     int end = 0;

     if(range.compareTo("all") == 0)
     {
        while((idx = base_buf.indexOf(target, end)) != -1)
        {
           base_buf.replace(idx, idx + target.length(), substitute);
           // start next search after the end of the new text, in case substitute contains an instance of target (i.e., replace & with &amp;)
           end = idx + substitute.length();
        }
     }
     else if (range.compareTo("first") == 0)
     {
        if((idx = base.toString().indexOf(target)) != -1)
       base_buf.replace(idx, idx + target.length(), substitute);
     }
     else if(range.compareTo("last") == 0)
     {
    	 idx = base_buf.lastIndexOf(target);
    	 base_buf.replace(idx, idx + target.length(), "");
     }
     else
        System.out.println("invalid range in Utilities.replaceChars: " + range);

     return base_buf.toString();
  }
  
  public static String encodeHtmlParam(String string)
  {
		int count = 0;
		
		for(count = 0; count < paramMap.length; count++)
		{
			string = replaceChars(string, paramMap[count][0], paramMap[count][1], "all");
		}
	  return string;
  }
  public static String decodeHtmlParam(String string)
  {
		int count = 0;
		
		for(count = 0; count < paramMap.length; count++)
		{
			string = replaceChars(string, paramMap[count][1], paramMap[count][0], "all");
		}
	  return string;
  }

  // change spaces to underscores 
  public static String encodeString(String string)
  {
      return replaceChars(string, " ", "_", "all");
  }
  
  // change underscores to spaces
  public static String decodeString(String string)
  {
      string = replaceChars(string, "_", " ", "all");
      return string;
  }
  
  // change <, >, and " to HTML codes
  public static String encodeHtmlTags(String string)
  {
	  string = replaceChars(string, "<", "&lt;", "all");
	  string = replaceChars(string, ">", "&gt;", "all");
	  string = replaceChars(string, "\"", "&quot;", "all");
	  return string;
  }
  
  // change html codes for <, >, and " back to characters
  public static String decodeHtmlTags(String string)
  {
	  string = replaceChars(string, "&lt;", "<", "all");
	  string = replaceChars(string, "&gt;", ">", "all");
	  string = replaceChars(string, "&quot;", "\"", "all");
	  return string;
  }
  
  public static boolean startsWithVowel(String text)
  {
	  boolean ret = false;
	  String[] vowels = {"a", "e", "i", "o", "u"};
	  for(int count = 0; count < vowels.length; count++)
	  {
		  if(text.substring(0,1).compareTo(vowels[count]) == 0)
		  {
			  ret = true;
			  break;
		  }
	  }
	  return ret;
  }

	public static String normalizeName(String name)
	{
		String[] illegalChars = {
				                  "*", // asterisk
				                  "&", // ampersand
				                  "'", // apostrophe
				                  "@", // at
				                  "\\", // back slash
				                  "^", // carat
				                  ",", // comma
				                  "/", // forward slash
				                  ">", // greater than (>)
				                  "<", // less than (<)
				                  ".", // period
				                  "?", // question mark
				                  " ", // space
				                };
		
		for(int count = 0; count < illegalChars.length; count++)
		{
			// replace all instances of illegalChars with underscore
			name = Utilities.replaceChars(name, illegalChars[count], "_", "all");
		}
		return name;
	}
	public static String sanitizeString(String string)
	{
		// return string with the html tags contained in the tags array removed
		String[] tags = {
			"<i>",
			"</i>",
			
			"<b>",
			"</b>",
			
			"<center>",
			"</center>",
			
			"<h1>",
			"</h1>",
			
			"<h2>",
			"</h2>",
			
			"<h3>",
			"</h3>",
			
			"<h4>",
			"</h4>",
			
			"<ul>",
			"</ul>",
			
			"<li>",
			"</li>"
		};
		// remove html tags for PDF output
 		String sanitizedString = string;
		for(int count = 0; count < tags.length; count++)
		{
			if(string.indexOf(tags[count]) != -1)
			{
				sanitizedString = sanitizedString.replaceAll(tags[count], "");
			}
		}
		// return the string minus html tags
		return sanitizedString;
	}
	
	public static String getPathMrkr()
	{
		return System.getProperty("file.separator");
	}
	
	public static String getTomcatHome()
	{
		String tomcatHome = "";
		String catalinaHome = System.getenv("CATALINA_HOME");
		File dir = null;
		File resources = null;
		
		try
		{
			if(catalinaHome != null)
				dir = new File(catalinaHome);
			else
				dir = new File(System.getenv("TOMCAT_HOME"));
	    	tomcatHome = dir.getCanonicalPath();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tomcatHome;
	} 
	
	private String convertCharSet(String parsedXml)
	{
		// begin conversion
	
		byte[] curCharSetBytes = null;
		try
		{
			curCharSetBytes = parsedXml.getBytes("UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		String convertedXml = new String(curCharSetBytes);
		
		// end UTF8 conversion
		return convertedXml;
	}
	
	public static void showUrl(String target, String className)
	{
		System.out.println("\n#### Utilities.showUrl(), from " + className + " #############\n");
		showUrl(target);
	}
	
	public static void showUrl(String target)
	{
		if(target == null)
		{
			System.out.println("########## return target is null ####################");
		}
		else if(printBreadCrumbs("showUrl()"))
		{
			String base = "";
			String params = "";
			String[] paramArray;
			
			int numParams = 0;
			
			int idx = target.indexOf("?");
			if(idx == -1)
			{
				// target contains no params
				base = target;
			}
			else
			{
				base = target.substring(0, idx);
				params = target.substring(idx + 1);
			}
			
			System.out.println("######   return target: " + base + " #########\n");
			
			if(!params.isEmpty())
			{
				System.out.println("Params:\n");
				paramArray = params.split("&");
				for(int paramCount = 0; paramCount < paramArray.length; paramCount++)
				{
					System.out.println(paramArray[paramCount]);
				}
				System.out.println("\n######   end params   #######################");
			}
		}
	}
	
	private static boolean printBreadCrumbs(String type)
	{
		boolean ret = false;
		
		// comment out both of the following two lines to shut down 
		// console output, by always returning false
		
		ret = type == "showUrl()" ? true : ret;
		ret = type == "viewServletParams()" ? true : ret;
		
		return ret;
	}
	
	public static void viewServletParams(HttpServletRequest request)
	{
	    viewServletParams(request, "");
	}
	
	public static void viewServletParams(HttpServletRequest request, String thumbprint)
	{
		if(printBreadCrumbs("viewServletParams()"))
		{
			// adapted from code snippet at https://www.java4s.com/java-servlet-tutorials/example-on-getparametermap-method-of-servlet-request-object/
	        Map m=request.getParameterMap();
	
	        Set s = m.entrySet();
	
	        String className = request.getServletPath();
	        String outMsg = "######  Utilities.viewServletParams(), called from " + className;
	        if(!thumbprint.isEmpty())
	               outMsg += "in " + thumbprint;
	               outMsg += "  ####\n";
	        System.out.println(outMsg);
	
	        String spacer = "";
	        Map.Entry<String,String[]> entry = null;
	        Iterator it = s.iterator();
	        while(it.hasNext())
	        {
	        	entry = (Map.Entry<String,String[]>)it.next();
	        	
	        	if(!it.hasNext())
	        		spacer = "\n";
	        	System.out.println(entry.getKey() + spacer);
	        }
	//        System.out.println("\n");
	        
	        it = s.iterator();
	        while(it.hasNext())
	        {
	            entry = (Map.Entry<String,String[]>)it.next();
	
	            String key             = entry.getKey();
	            String[] value         = entry.getValue();
	
	            System.out.println("Key is " + key);
	
	                if(value.length > 1){    
	                    for (int i = 0; i < value.length; i++) 
	                    {
	                    	String multiVal = value[i].toString();
	                    	if(multiVal.isEmpty())
	                    		multiVal = "undefined (empty multiVal string)";
	                    	System.out.println(" - " + multiVal);
	//                        System.out.println(" - " + value[i].toString());
	                    }
	                }
	                else
	                {
	                	String val = value[0].toString();
	                	if(val.isEmpty())
	                		val = "undefined (empty val string)";
	                	System.out.println("Value is " + val);
	                }
	
	                System.out.println("---------------------");
	        }
	        System.out.println("######   " + className + " out   #######################################\n");
		}
	}
	

} // Utilities class
