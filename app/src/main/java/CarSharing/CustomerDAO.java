package CarSharing;

import java.util.List;

public interface CustomerDAO {
    List<Customer> selectAll();
    void updateRent(Car car, int customerId);
    void updateReturn(int rentedCarId);
    List<Customer> selectCustomer(int customerId);
    List<Customer> selectRentedCar(int rentedCar);
    void add(String name);
}
