import model.Travel;
import model.Travels;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class XmlReader extends DefaultHandler {//JAXP + SAX = https://www.baeldung.com/java-sax-parser

    private static final String TRAVELS = "travels";
    private static final String TRAVEL = "travel";
    private static final String CITYFIRST = "cityfirst";
    private static final String CITYSECOND = "citysecond";
    private static final String LENGTH = "length";

    private Travels travels;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument(){
        travels = new Travels();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        switch (qName) {
            case TRAVELS:
                travels.travelList = new ArrayList<>();
                break;
            case TRAVEL:
                travels.travelList.add(new Travel());
                break;
            case CITYFIRST:
                elementValue = new StringBuilder();
                break;
            case CITYSECOND:
                elementValue = new StringBuilder();
                break;
            case LENGTH:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case CITYFIRST:
                latestArticle().setCityfirst(elementValue.toString());
                break;
            case CITYSECOND:
                latestArticle().setCitysecond(elementValue.toString());
                break;
            case LENGTH:
                latestArticle().setLength(elementValue.toString());
                break;
        }
    }

    private Travel latestArticle() {
        List<Travel> travelList = travels.getTravelList();
        int latestArticleIndex = travelList.size() - 1;
        return travelList.get(latestArticleIndex);
    }

    public Travels getWebsite() {
        return travels;
    }


}

