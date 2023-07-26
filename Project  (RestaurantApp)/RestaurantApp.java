import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class RestaurantApp {

    public static void main(String[] args) throws Exception {
        
       //1. parsing the XML with the menu and available quantity;
       
        File file = new File("products.xml");
        XMLStorage.getELementAsProduct(file);
        System.out.println(XMLStorage.productsList);
            
        System.out.println("\n");


        Stock stock = new Stock();
        stock.addItem( 
            new Item<>(XMLStorage.productsList.get(0), XMLStorage.productsList.get(0).getQuantity()));
        stock.addItem( 
            new Item<>(XMLStorage.productsList.get(1),XMLStorage.productsList.get(1).getQuantity()));
        stock.addItem(
            new Item<>(XMLStorage.productsList.get(2),XMLStorage.productsList.get(2).getQuantity()));

        System.out.println("Initial Stock: ");
        System.out.println(stock);
       

        // 2. Interaction with customer.....

        Scanner in = new Scanner(System.in);

        System.out.println("Wellcome to 'Savoia Bistro' !!! ");
        System.out.println("Today you can serve...\n\t\t1.Pizza \n\t\t2.Soup \n\t\t3.Salad");

       
        String clientOrderConfirm = "YES";
        String clientName = " ";
        Integer phoneNumber = 0 ;
        Order order = new Order(new Client(clientName, phoneNumber ), stock);



        while (clientOrderConfirm.equals("YES")) {

            System.out.println();

            System.out.print("Type the mark number to choose a dish : ");
            Byte chosenProduct = in.nextByte();

            System.out.print("Choose quantity: ");
            Short quantityImput = in.nextShort();

            System.out.print("Do you want something else ??? (YES/NO): ");
            clientOrderConfirm = in.next().trim().toUpperCase();
            if (!clientOrderConfirm.equals("YES") && !clientOrderConfirm.equals("NO")) {
                System.err.println("Please respond with YES/NO");
                clientOrderConfirm = in.next().trim().toUpperCase();
                
            }
            
            
           

            if (chosenProduct.equals((Byte.parseByte("1")))) {

                if(quantityImput <0 ) {
                    throw new  Exception("ERROR: amount can't be negative");
                   
                } else if(quantityImput > XMLStorage.productsList.get(0).getQuantity()){
                    throw new Exception("ERROR: you have exceeded the limit quantity");
                }
                Item<Product> item1 = new Item<>( XMLStorage.productsList.get(0), quantityImput );
                    order.addItem(item1);
                    

            } else if (chosenProduct.equals((Byte.parseByte("2")))) {

                if(quantityImput < 0  ){
                    throw new  Exception("ERROR: amount can't be negative");
                       
                } else if(quantityImput > XMLStorage.productsList.get(0).getQuantity()){
                    throw new Exception("ERROR: you have exceeded the limit quantity");
                } 
                Item<Product> item2 = new Item<>(XMLStorage.productsList.get(1),quantityImput );
                    order.addItem(item2);

            } else if (chosenProduct.equals((Byte.parseByte("3")))) {

                if(quantityImput <0  ){
                    throw new  Exception("ERROR: amount can't be negative");   
                } else if(quantityImput > XMLStorage.productsList.get(0).getQuantity()){
                    throw new Exception("ERROR: you have exceeded the limit quantity");
                } 
                Item<Product> item3 = new Item<>( XMLStorage.productsList.get(2), quantityImput );
                   order.addItem(item3);
                    
            }

           
            if(clientOrderConfirm.equals("NO")){
                System.out.println("Please provide your name and phone number for better communication  ");
                System.out.println("Name: ");
                clientName = in.next();
                System.out.println("phoneNumber: ");
                System.out.print("+373 ");
                phoneNumber = in.nextInt();
                order.getOwner().setName(clientName);
                order.getOwner().setPhone(phoneNumber);

            } 


            
           

            
        }
        System.out.println();
        System.out.println(order);

        System.out.println();

        



        
        
        System.out.println("Remaining in the stock (for verification): ");
        System.out.println(stock);

        //############################### Preparing Order document in memory !!!########################################

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder  dBuilder = dbFactory.newDocumentBuilder();
        List<Product> orderList = new ArrayList<>(); // aici ar trebui sa fie indicata lista order, care se afiseaza initial pe ecran
        //Empty DOC in memory
        Document document =  dBuilder.newDocument();

        // Create a root element "products" to hold all "product" elements
        Element productsElement = document.createElement("order.xml");
        document.appendChild(productsElement);


        for (Product productX : order.getOrderList()) {

            // Fill the DOC :
        
            Element product = document.createElement("product");
            productsElement.appendChild(product);

            Element name = document.createElement("name");
            name.setTextContent(productX.getName());
            product.appendChild(name);
            Element id = document.createElement("id");
            product.appendChild(id);
            Element price = document.createElement("price");
            product.appendChild(price);
            Element amount = document.createElement("amount");
            amount.setTextContent(String.valueOf(productX.getPrice().getAmount()));  // CONVERT int to String
            price.appendChild(amount);
            Element currency = document.createElement("currency");
            currency.setTextContent(productX.getPrice().getCurrency());
            price.appendChild(currency);
            Element quantityImput = document.createElement("orderedQuantity");
            product.appendChild(quantityImput);

          
        }
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer t = tFactory.newTransformer();

        //pretty print
        t.setOutputProperty(OutputKeys.INDENT,"yes");

        Source src = new DOMSource(document);

        Result result = new StreamResult(new File("order"));
        t.transform(src,result);


    }
}


    
