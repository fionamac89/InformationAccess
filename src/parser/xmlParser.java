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

	List<Article> articleList;
	String file;
	String xmlFile;
	String tmpValue;
	Article articleTemp;
	String currentLine;

	public xmlParser(String file)
	{
		this.file = file;
		articleList = new ArrayList<Article>();
		parseDocument();
		//printDatas();
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
	
	private void printDatas(){
		for(Article art: articleList)
		{
			System.out.println(art.printAll());
		}

	}

	@Override

	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
		
		if (elementName.equalsIgnoreCase("doc")) {
			articleTemp = new Article();
			articleList.add(articleTemp);
		}

	}

	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		//System.out.println(element +  tmpValue + "END");
		
		if (element.equalsIgnoreCase("docno")) {
			articleTemp.setDocNo(tmpValue);
		}
		if (element.equalsIgnoreCase("docid")) {
			articleTemp.setDocId(tmpValue);
		}
		if (element.equalsIgnoreCase("hl")) {
			articleTemp.setHeadLine(tmpValue);
		}
		if (element.equalsIgnoreCase("date")) {
			articleTemp.setDate(tmpValue);
		}
		if (element.equalsIgnoreCase("so")) {
			articleTemp.setsO(tmpValue);
		}
		if (element.equalsIgnoreCase("co")) {
			articleTemp.setLeadPar(tmpValue);
		}
		if (element.equalsIgnoreCase("lp")) {
			articleTemp.setLeadPar(tmpValue);
		}
		if (element.equalsIgnoreCase("text")) {
			articleTemp.setBody(tmpValue);
		}
		tmpValue = "";
	}
	
	public Article finishedArticle()
	{
		return articleTemp;
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
 
		currentLine = new String(ac, i, j);
		//System.out.println(currentLine);
		tmpValue = tmpValue + currentLine;
		tmpValue=tmpValue.replaceAll("\n", "");
		StringEscapeUtils.escapeXml(tmpValue);
		//currentLine=currentLine.replaceAll("&(?!amp;)", "&amp;");
	}
	
}


