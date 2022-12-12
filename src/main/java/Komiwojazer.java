import model.Travel;
import model.Travels;
import model.Paths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

import java.util.Random;

public class Komiwojazer {

    private static final String PATH = "length.xml";

    public void start() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlReader xmlReader = new XmlReader();

        saxParser.parse(PATH, xmlReader);
        Travels result = xmlReader.getWebsite();
        List<Travel> travels = result.getTravelList();

        // Wygenerowanie losowych sciezek pomiedzy miastami

        System.out.println("Wygenerowane losowe sciezki pomiedzy miastami: ");
        System.out.println();

        Paths paths = new Paths(3, travels);
        paths.write_out();
    }
}
