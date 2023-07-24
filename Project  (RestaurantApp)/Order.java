import java.util.Arrays;

public class Order {
    private String[] items;  ///??? dont know the type yet
    private Client client;    ///??? dont know if the type is correct 
    private Integer total;
    
    
    
    public Order(String[] items, Client client, int total) {
        this.items = items;
        this.client = client;
        this.total = total;
    }

    public String[] getItems() {
        return items;
    }
    public void setItems(String[] items) {
        this.items = items;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order [items=" + Arrays.toString(items) + ", client=" + client + ", total=" + total + "]";
    }

    


    
    



    



}
