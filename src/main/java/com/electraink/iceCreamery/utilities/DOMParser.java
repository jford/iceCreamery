package com.electraink.iceCreamery.utilities;

import java.io.*;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.w3c.dom.*;

public class DOMParser
{
    public DOMParser()
    {
    	/*
    	 * Usage: create DOMParser(), then pass XML to be parsed 
    	 * as a string, either to parse() or parseFromStream()
    	 * 
    	 * Parsed data is return as a Document which can be traversed
    	 * by a parser class designed to traverse the schema of whatever  
    	 * flavor of XML is being parsed
    	 */
    }

    /*
     * parse() takes URL to XML file, produces a tree of nodes to be processed sequentially after
     * dom parser is done 
     */
    public Document parse(String xml)
    {
    	// incoming xml is an URL to the XML file; use parseFromStream()
    	// if xml contains XML file contents
    	
        // Build the DOM parser
        DocumentBuilderFactory documentFactory;
        documentFactory = DocumentBuilderFactory.newInstance();
                                                                                
        documentFactory.setCoalescing(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        documentFactory.setExpandEntityReferences(true);
                                                                                
        DocumentBuilder myDocumentBuilder = null;
        try 
        {
            myDocumentBuilder = documentFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException pce) 
        {
            System.out.println(pce);
            System.exit(1);
        }
                                                                                
        myDocumentBuilder.setErrorHandler(new MyErrorHandler());
                                                                                
        Document parsedXML = null;
        try 
        {
            parsedXML = myDocumentBuilder.parse(xml);
        } 
        catch (SAXException se) 
        {
            System.out.println("DOMParser encountered a SAX Error in parse() while attempting to parse XML:\n" + xml); 
            System.out.println(se.getMessage());
            System.exit(1);
        } 
        catch (Exception e) 
        {
        	System.out.println("DOMParser encountered an IO Error while attempting to parse XML:\n" + xml);
            System.out.println(e);
            System.exit(1);
        }

        return parsedXML;
    }
    
//    /*
//     * parseFromStream() takes xml file contents and produces series of events
//     */
//    public Document parseFromStream(String xml)
//    {
//        System.out.println("in DOM parser parseFromStream(xml file contents)");
//    	// xml is XML file contents; use parse() if xml is
//    	// an URL to an XML file
//    	
//        ByteArrayInputStream iStream = null;
//        try
//        {
//            iStream = new ByteArrayInputStream(xml.getBytes());
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        // Build the DOM parser
//        DocumentBuilderFactory documentFactory;
//        documentFactory = DocumentBuilderFactory.newInstance();
//                                                                                
//        documentFactory.setCoalescing(true);
//        documentFactory.setIgnoringComments(true);
//        documentFactory.setNamespaceAware(true);
//        documentFactory.setIgnoringElementContentWhitespace(true);
//        documentFactory.setExpandEntityReferences(true);
//                                                                                
//        DocumentBuilder myDocumentBuilder = null;
//        try 
//        {
//            myDocumentBuilder = documentFactory.newDocumentBuilder();
//        }
//        catch (ParserConfigurationException pce) 
//        {
//            System.out.println(pce);
//            System.exit(1);
//        }
//                                                                                
//        myDocumentBuilder.setErrorHandler(new MyErrorHandler());
//                                                                                
//        Document parsedXML = null;
//        try 
//        {
//            parsedXML = myDocumentBuilder.parse(iStream);
//        } 
//        catch (SAXException se) 
//        {
//            System.out.println("DOMParser encountered a SAX Error in parse() while attempting to parse XML:\n" + xml); 
//            System.out.println(se.getMessage());
//            System.exit(1);
//        } 
//        catch (Exception ioe) 
//        {
//            System.out.println("DOMParser encountered an IO Error while attempting to parse XML from stream:\n" + xml);
//            System.out.println(ioe);
//            System.exit(1);
//        }
//        return parsedXML;
//    }
 
    private static class MyErrorHandler implements ErrorHandler
    {
        private String getParseExceptionInfo(SAXParseException spe)
         {
            String systemid = spe.getSystemId();
            if(systemid == null)
            {
                systemid = "null";
            }
            String info = "URI=" + systemid +
                          " Line=" + spe.getLineNumber() +
                          ": " + spe.getMessage();
            return info;
        }
                                                                                
        public void warning(SAXParseException spe) throws SAXException
        {
            System.out.println("Warning: " + getParseExceptionInfo(spe));
        }
                                                                                
        public void error(SAXParseException spe) throws SAXException
        {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
                                                                                
        public void fatalError(SAXParseException spe) throws SAXException
        {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}
