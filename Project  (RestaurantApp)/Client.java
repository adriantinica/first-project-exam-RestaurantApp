public class Client {
    private String name;
    private Integer phoneNumber;

    public Client(String name, Integer phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phoneNumber;
    }

    public void setPhone(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client [name=" + name + ", phone=" + phoneNumber + "]";
    }

    

    

    
}

