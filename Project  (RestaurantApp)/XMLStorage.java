import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class XMLStorage {

    

    public static List <Product> productsList = new ArrayList<>();;

    public static List<Product> getELementAsProduct(File file) throws ParserConfigurationException, SAXException, IOException{
        
        

        // DOM Parser/ + Factory & Builder & Singleton patterns

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);

        //###########   traversing the DOM and extracting DATA    ######################################################

        Element root = document.getDocumentElement();
    
        //System.out.println(productName.getTextContent().trim());
        NodeList products = root.getElementsByTagName("product");
        //HW1: Extract the price for first product....
        for (int i = 0; i <  products.getLength(); i++ ) {
            
            Element product= (Element)products.item(i);

            Element id = (Element) product.getElementsByTagName("id").item(0);
            
            Element productName = (Element) product.getElementsByTagName("name").item(0);
            Element productPrice = (Element) product.getElementsByTagName("price").item(0);
        
            Element priceAmount  = (Element) productPrice.getElementsByTagName("amount").item(0);
            Element priceCurrency = (Element) productPrice.getElementsByTagName("currency").item(0);

            Element availability = (Element) product.getElementsByTagName("available").item(0);


            Byte idByte = Byte.parseByte(id.getTextContent().trim());
            String name = productName.getTextContent().trim();
            int amount = Integer.parseInt(priceAmount.getTextContent().trim());
            String currency = priceCurrency.getTextContent().trim();
            Short quantity  = Short.parseShort(availability.getTextContent().trim());

            productsList.add(new Product(idByte, name, new Money(amount, currency), quantity));
            
            
 
        }
        return productsList;
    }

    

    

}
