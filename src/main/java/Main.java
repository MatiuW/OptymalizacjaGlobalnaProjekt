import model.Travels;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Komiwojazer komiwojazer = new Komiwojazer();
        komiwojazer.start();

//        Komiwojazer2 komiwojazer = new Komiwojazer2();
//        komiwojazer.start();
    }
}
