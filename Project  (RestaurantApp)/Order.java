import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public final class Order extends ItemRepository {

    private Client owner;
    private Money totalCost;
    private ItemRepository stock;
    private List<Product> orderList;
   

    public Order(Client owner, ItemRepository stock) {
        //super() - auto generated
        this.owner = owner;
        this.stock = stock;
        this.totalCost= new Money(0, "MDL");
        this.orderList = new ArrayList<>();
    }

    public List<Product> getOrderList() {
        return orderList;
    }

    public String getTotalCost(){
        return totalCost.toString();
    }

  
    // Method to count the total quantity input for a specific product in the order
    public int getTotalQuantityForProduct(Product product) {
        int totalQuantity = 0;
        for (Product item : orderList) {
            if (item.getValue().equals(product)) {
                totalQuantity += item.getQuantity();
            }
        }
        return totalQuantity;
    }

    public List<Item<Product>> getItems() {
    return items;
}
    
    


    @Override
    public String toString() {
        return " Your  Order [owner=" + owner +" \n\ttotalCost"+ totalCost + " ]\n" + super.toString() ;
    }

    // Cart Logic

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

   

    
    @Override
    public void addItem(Item<Product> item){

        // HW8/9: verify available quantity 
        super.addItem(item);
        addItemCost(item);
        
        Item<Product> stockItem = stock.getItemById(item.getValue().getId());
        if (item.getQuantity()< stockItem.getQuantity() && item.getQuantity() > 0) {
            stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
        } else { 
           
            item.setQuantity(stockItem.getQuantity());
            stockItem.setQuantity(0);
            
        }
       
        for (int i = 0; i <= 0; i++) {
            if (item.getValue() != null) {
                orderList.add(item.getValue()); // Add the Product, not the Item
            }
        }
    }

     //HW4: ADD removeItem(Item item)
            //removes the indicated object
            //update totalCost
    

    @Override
    public void removeItem(Item<Product> item) {
        
        Item<Product> stockItem = stock.getItemById(item.getValue().getId());
        //if (item.getQuantity()< stockItem.getQuantity() && item.getQuantity() > 0) {
            stockItem.setQuantity(stockItem.getQuantity() + item.getQuantity());
        //} 
        substractItemCost(item);
        super.removeItem(item);
    }




    // A bit of optimization for .removeItem(Item item) && addItem(Item item)
    
    public int totalCostOfDistinctProduct(Item<Product> item){
        return item.getValue().getPrice().getAmount()*item.getQuantity();
    }

    public void substractItemCost(Item<Product> item){
        totalCost.setAmount(
            totalCost.getAmount()-totalCostOfDistinctProduct(item)
        );
    }

    public void addItemCost(Item<Product> item){
        totalCost.setAmount(
            totalCost.getAmount()+totalCostOfDistinctProduct(item)
        );
    }


    //HW5: increaseItemQuantity(Item item, Integer amount);
    // set protection 


    public void increaseItemQuantity(Item<Product> item, Integer amount)throws ValueOutOfRangeException, InputMismatchException{

        Item<Product> stockItem = stock.getItemById(item.getValue().getId());
        
        if (amount >0 && amount  <= stockItem.getQuantity() ) {
            incrementFunctionProcess(item,amount);
            stockItem.setQuantity((short) (stockItem.getQuantity() - amount));
           
        } else  if(amount > stockItem.getQuantity()) {
            item.setQuantity((short) (item.getQuantity()+ stockItem.getQuantity()));
            stockItem.setQuantity((short) 0) ;
            

        } else { throw new ValueOutOfRangeException("ERROR: amount can't be negative");}
        
        
    }


    //decreaseItemQuantity(Item item, Integer amount);
    // set protection

    public void decreaseItemQuantity(Item<Product> item, Integer amount)throws ValueOutOfRangeException, InputMismatchException{
        
        Item<Product> stockItem = stock.getItemById(item.getValue().getId());
        if ((item.getQuantity()).intValue() <= amount  ) {
             throw new ValueOutOfRangeException("ERROR: try another amount or delete this item from cart !!! ");

        }else if (amount <= 0) {
            throw new ValueOutOfRangeException("ERROR: amount can't be negative");

        }else {

            decrementFunctionProcess(item,amount);
            stockItem.setQuantity((short) (stockItem.getQuantity() + amount));
            
        }
    }

    // A bit of optimization for increase, decrease methods!!!!!!!

    public void decrementFunctionProcess(Item<Product> item, Integer amount){
        item.setQuantity((short) (item.getQuantity()-amount));
        totalCost.setAmount(totalCost.getAmount()-item.getValue().getPrice().getAmount() *amount);
    }
    

    public void incrementFunctionProcess(Item<Product> item, Integer amount){
        item.setQuantity((short) (item.getQuantity()+amount));
        totalCost.setAmount(totalCost.getAmount()+item.getValue().getPrice().getAmount() *amount);
    }

    class ValueOutOfRangeException extends Exception{

        public ValueOutOfRangeException (String message){
            super(message);
        }
    }
} 
        

   





