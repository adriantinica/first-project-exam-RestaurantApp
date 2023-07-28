import java.io.File;
import java.util.Scanner;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class RestaurantApp {

    public static void main(String[] args) throws Exception {
        
       //1. parsing the XML with the menu and available quantity;
      
       
        File file = new File("products.xml");
        XMLStorage.getELementAsProduct(file);
        
            
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

        try (Scanner in = new Scanner(System.in)) {
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
                Short quantityInput = in.nextShort();

                System.out.print("Do you want something else ??? (YES/NO): ");
                clientOrderConfirm = in.next().trim().toUpperCase();
                if (!clientOrderConfirm.equals("YES") && !clientOrderConfirm.equals("NO")) {
                    System.err.println("Please respond with YES/NO");
                    clientOrderConfirm = in.next().trim().toUpperCase();
                    
                }
                
                
               

                if (chosenProduct.equals((Byte.parseByte("1")))) {

                    if(quantityInput <0 ) {
                        throw new  Exception("ERROR: amount can't be negative");
                       
                    } else if(quantityInput > XMLStorage.productsList.get(0).getQuantity()){
                        throw new Exception("ERROR: you have exceeded the limit quantity");
                    }
                    Item<Product> item1 = new Item<>( XMLStorage.productsList.get(0), quantityInput );
                        order.addItem(item1);
                    
                    
                        

                } else if (chosenProduct.equals((Byte.parseByte("2")))) {

                    if(quantityInput < 0  ){
                        throw new  Exception("ERROR: amount can't be negative");
                           
                    } else if(quantityInput > XMLStorage.productsList.get(0).getQuantity()){
                        throw new Exception("ERROR: you have exceeded the limit quantity");
                    } 
                    Item<Product> item2 = new Item<>(XMLStorage.productsList.get(1),quantityInput );
                        order.addItem(item2);

                } else if (chosenProduct.equals((Byte.parseByte("3")))) {

                    if(quantityInput <0  ){
                        throw new  Exception("ERROR: amount can't be negative");   
                    } else if(quantityInput > XMLStorage.productsList.get(0).getQuantity()){
                        throw new Exception("ERROR: you have exceeded the limit quantity");
                    } 
                    Item<Product> item3 = new Item<>( XMLStorage.productsList.get(2), quantityInput );
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

            XMLStorage.createOrderXmlFile(order);
            XMLStorage.copyFile("products.xml", "products_old.xml");

            XMLStorage.updateProductsXmlFile(stock);

            

        } catch (TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

        // create a copy of a products.xml
        
        

    }
}

    }
}

    
