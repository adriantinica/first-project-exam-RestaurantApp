import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.util.Scanner;


public class RestaurantApp {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
       //1. parsing the XML with the menu and available quantity;
       
        File file = new File("products.xml");
        XMLStorage.gelELementAsProduct(file);
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
         Order cart = new Order(new Client(clientName, phoneNumber ), stock);
        while (clientOrderConfirm.equals("YES")) {

            System.out.println();
            System.out.print("Type the mark number to choose a dish : ");
            Byte chosenProduct = in.nextByte();
            System.out.print("Choose quantity: ");
            Short quantityImput = in.nextShort();
            System.out.print("Do you want something else ??? (YES/NO): ");
            clientOrderConfirm = in.next().trim().toUpperCase();
            
           

            if (chosenProduct.equals((Byte.parseByte("1")))) {

                //System.out.println("You've choosed: " + quantityImput + productsList.get(0).getName() );
                Item<Product> item1 = new Item<>( XMLStorage.productsList.get(0), quantityImput );
                    cart.addItem(item1);
                    

                } else if (chosenProduct.equals((Byte.parseByte("2")))) {

                //System.out.println("You've choosed: " + quantityImput + productsList.get(1).getName() );
                Item<Product> item2 = new Item<>(XMLStorage.productsList.get(1),quantityImput );
                    cart.addItem(item2);

                } else if (chosenProduct.equals((Byte.parseByte("3")))) {

                //System.out.println("You've choosed: " + quantityImput + productsList.get(2).getName() );
                Item<Product> item3 = new Item<>( XMLStorage.productsList.get(2), quantityImput );
                    cart.addItem(item3);
                    
                }
            
               

            if(clientOrderConfirm.equals("NO")){
                System.out.println("Please provide your name and phone number for better communication  ");
                System.out.println("Name: ");
                clientName = in.next();
                System.out.println("phoneNumber: ");
                System.out.print("+373 ");
                phoneNumber = in.nextInt();
                cart.getOwner().setName(clientName);
                cart.getOwner().setPhone(phoneNumber);

            }    

            
        }
        System.out.println(cart);
        System.out.println();



        
       
        System.out.println("Remaining in the stock (for verification): ");
        System.out.println(stock);

    }
}
