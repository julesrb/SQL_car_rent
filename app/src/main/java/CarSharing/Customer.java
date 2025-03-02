package CarSharing;

public class Customer {
    private String  name;
    private int     id;
    private int rentedCarId;

    public Customer(int id, String name,  int rentedCarId) {
        this.name = name;
        this.id = id;
        this.rentedCarId = rentedCarId;
    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    int getRentedCarId() {
        return rentedCarId;
    }

    @Override
    public String toString() {
        return name;
    }
}
