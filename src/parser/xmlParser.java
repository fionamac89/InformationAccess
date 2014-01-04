package parser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class xmlParser extends DefaultHandler{

	String file = "";
	String xmlFile = "";
	String tmpValue = "";
	Article articleTemp = null;
	String currentLine = "";
	StringBuffer accumulator = new StringBuffer();

	public xmlParser(String file)
	{
		this.file = file;
		parseDocument();
	}

	private void parseDocument(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : Syntax issue");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override

	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
		accumulator.setLength(0);
		if (elementName.equalsIgnoreCase("doc")) {
			articleTemp = new Article();
		}

	}

	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		//System.out.println(element +  tmpValue + "END");
		
		if (element.equalsIgnoreCase("docno")) {
			articleTemp.setDocNo(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("docid")) {
			articleTemp.setDocId(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("hl")) {
			articleTemp.setHeadLine(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("date")) {
			articleTemp.setDate(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("so")) {
			articleTemp.setSo(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("co")) {
			articleTemp.setCo(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("gv")) {
			articleTemp.setGv(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("in")) {
			articleTemp.setIn(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("lp")) {
			articleTemp.setLeadPar(accumulator.toString().trim());
		}
		if (element.equalsIgnoreCase("text")) {
			articleTemp.setBody(accumulator.toString().trim());
		}
		
	}
	
	public Article finishedArticle()
	{
		return articleTemp;
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
 
		accumulator.append(ac, i, j);
		//System.out.println(currentLine);
		//tmpValue = tmpValue + currentLine;
		//tmpValue=tmpValue.replaceAll("\n", "");
		//StringEscapeUtils.escapeXml(tmpValue);
		//currentLine=currentLine.replaceAll("&(?!amp;)", "&amp;");
	}
	
}


