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

import java.util.Scanner;


public class RestaurantApp {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
       //1. parsing the XML with the menu and available quantity;
       
        File file = new File("products.xml");
        System.out.println(file.getAbsolutePath());
        List <Product> productsList = new ArrayList<>();

        // DOM Parser/ + Factory & Builder & Singleton patterns

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);

        //###########   trabversing the DOM and extracting DATA    ######################################################

        //System.out.println(document.getDoctype());
        System.out.println();

        Element root = document.getDocumentElement();
        //NodeList products = root.getElementsByTagName("product");
        //Element firstProduct = (Element)products.item(i);
        //Element productName = (Element) firstProduct.getElementsByTagName("name").item(i);

        // ##############################################################################################################


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

        //if (productsList.size() >=3) {
        //    Product pizza = productsList.get(0);
        //    Product soup = productsList.get(1);
        //    Product salad = productsList.get(2);

        //    System.out.println(pizza);
        //    System.out.println(soup);
        //    System.out.println(salad);

        //}
            
        System.out.println("\n");


         Stock stock = new Stock();
        stock.addItem( 
            new Item<>(productsList.get(0), productsList.get(0).getQuantity()));
        stock.addItem( 
            new Item<>(productsList.get(1),productsList.get(1).getQuantity()));
        stock.addItem(
            new Item<>(productsList.get(2),productsList.get(2).getQuantity()));

        System.out.println("Initial Stock: ");
        System.out.println(stock);

        // 2. Interaction with customer.....

        Scanner in = new Scanner(System.in);

        System.out.println("Wellcome to 'Savoia Bistro' !!! ");

        System.out.println("Today you can serve...\n\t\t1.Pizza \n\t\t2.Soup \n\t\t3.Salad");

       
        String clientOrderConfirm = "YES";
        while (clientOrderConfirm.equals("YES")) {

            System.out.println();
            System.out.print("Type the mark number to choose a dish : ");
            Byte clientInput = in.nextByte();
            System.out.print("Choose quantity: ");
            Short quantityImput = in.nextShort();
            System.out.print("Do you want something else ??? (YES/NO): ");
            clientOrderConfirm = in.next().trim().toUpperCase();


            

        }
        System.out.println();

        Order cart = new Order(new Client("Paul Gasole", 69408080 ), stock);


        
        Item<Product> item1 = new Item<>( productsList.get(0), productsList.get(0).getQuantity());
        cart.addItem(item1);
            
        Item<Product> item2 = new Item<>(productsList.get(1),productsList.get(1).getQuantity());
        cart.addItem(item2);
        Item<Product> item3 = new Item<>( productsList.get(2), productsList.get(2).getQuantity());
        cart.addItem(item3);
        
        System.out.println(cart);  
        System.out.println(stock);
        





       

        
        





    
            
    



        





    }
}


    

    
