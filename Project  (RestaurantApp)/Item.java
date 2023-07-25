public class Item <T> {

    private T value;
    private Short quantity;
        
        
    public Item(T value, Short quantity) {
        this.value = value;
        this.quantity = quantity;
            
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public Short getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int i) {
        this.quantity = (short)i;
    }
    
    @Override
    public String toString() {
        return "Item [value=" + value + ", quantity=" + quantity + "]\n";
    }
}
    

