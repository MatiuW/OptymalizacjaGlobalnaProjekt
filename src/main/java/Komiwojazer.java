import model.Travel;
import model.Travels;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class Komiwojazer {

    private static final String PATH = "length.xml";

    public void start() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlReader xmlReader = new XmlReader();

        //test
        saxParser.parse(PATH, xmlReader);
        Travels result = xmlReader.getWebsite();
        List<Travel> travels = result.getTravelList();

        for(int i = 0; i < travels.size(); i++) {
            System.out.println("====================================================================");
            System.out.println("miasto pierwsze: " + travels.get(i).getCityfirst() + ", miasto drugie: " + travels.get(i).getCitysecond() + ", odleglosc: " + travels.get(i).getLength());
        }
    }
}
