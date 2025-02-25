import java.util.List;

public interface CustomerDAO {
    List<Customer> selectAll(int customerId);
    void add(String name, int customer_id);
}
