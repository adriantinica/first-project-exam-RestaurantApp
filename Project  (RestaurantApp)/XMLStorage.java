import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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


    public static void copyFile(String sourcePath, String destinationPath) throws IOException {

        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);
    
        // Check if the source file exists
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new IllegalArgumentException("Source file does not exist or is not a regular file.");
        }
    
        // Create the destination file if it doesn't exist
        if (!destinationFile.exists()) {
            destinationFile.createNewFile();
        }
    
        try (FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
             FileChannel destinationChannel = new FileOutputStream(destinationFile).getChannel()) {
    
            // Transfer the data from the source file to the destination file
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }

   


    public static void createOrderXmlFile(Order order) throws TransformerConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element productsElement = document.createElement("products");
            document.appendChild(productsElement);

            List<Item<Product>> items = order.getItems();
            Element ordered = document.createElement("order");
            productsElement.appendChild(ordered);

            Element guestName = document.createElement("clientName");
            guestName.setTextContent(order.getOwner().getName());
            ordered.appendChild(guestName);

            Element guestPhone = document.createElement("clientPhone");
            guestPhone.setTextContent(String.valueOf(order.getOwner().getPhone()));
            ordered.appendChild(guestPhone);

            Element totalCost = document.createElement("totalCheck");
            totalCost.setTextContent(String.valueOf(order.getTotalCost()));
            ordered.appendChild(totalCost);

            for (int i = 0; i < items.size(); i++) {
                Item<Product> item = items.get(i);
                Product productX = item.getValue();

                Element product = document.createElement("product");
                ordered.appendChild(product);

                Element name = document.createElement("name");
                name.setTextContent(productX.getName());
                product.appendChild(name);

                Element id = document.createElement("id");
                id.setTextContent(String.valueOf(productX.getId()));
                product.appendChild(id);

                Element price = document.createElement("price");
                product.appendChild(price);

                Element amount = document.createElement("amount");
                amount.setTextContent(String.valueOf(productX.getPrice().getAmount()));
                price.appendChild(amount);

                Element currency = document.createElement("currency");
                currency.setTextContent(productX.getPrice().getCurrency());
                price.appendChild(currency);

                Element quantity = document.createElement("orderedQuantity");
                quantity.setTextContent(String.valueOf(item.getQuantity()));
                product.appendChild(quantity);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer t = tFactory.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");

            Source src = new DOMSource(document);
            Result result = new StreamResult(new File("order.xml"));
            t.transform(src, result);

            System.out.println("Order XML file created successfully.");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }




    public static void updateProductsXmlFile(Stock stock) throws TransformerConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element productsElement = document.createElement("products");
            document.appendChild(productsElement);

            List<Item<Product>> items = stock.getItems();
            Element stock1 = document.createElement("stock");
            productsElement.appendChild(stock1);


            for (int i = 0; i < items.size(); i++) {
                Item<Product> item = items.get(i);
                Product productX = item.getValue();

                Element product = document.createElement("product");
                stock1.appendChild(product);

                Element name = document.createElement("name");
                name.setTextContent(productX.getName());
                product.appendChild(name);

                Element id = document.createElement("id");
                id.setTextContent(String.valueOf(productX.getId()));
                product.appendChild(id);

                Element price = document.createElement("price");
                product.appendChild(price);

                Element amount = document.createElement("amount");
                amount.setTextContent(String.valueOf(productX.getPrice().getAmount()));
                price.appendChild(amount);

                Element currency = document.createElement("currency");
                currency.setTextContent(productX.getPrice().getCurrency());
                price.appendChild(currency);

                Element quantity = document.createElement("availabe");
                quantity.setTextContent(String.valueOf(item.getQuantity()));
                product.appendChild(quantity);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer t = tFactory.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");

            Source src = new DOMSource(document);
            Result result = new StreamResult(new File("products.xml"));
            t.transform(src, result);

            System.out.println("products XML file updated successfully.");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
