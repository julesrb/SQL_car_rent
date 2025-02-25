import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImplementation implements CustomerDAO {

    static final String CREATE_DB_CUSTOMER = """
                                    CREATE TABLE IF NOT EXISTS customer(
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE,
                                    rented_car_id INTEGER NOT NULL,
                                    CONSTRAINT car_fk FOREIGN KEY (rented_car_id)
                                    REFERENCES car(id))""";
    static final String ADD = """
                               INSERT INTO customer (name, company_id)
                               VALUES""";
    static final String SELECT_ALL = """
                               SELECT *
                               FROM customer WHERE company_id = """;


    private final H2DbClient H2dbClient;

    public CustomerDAOImplementation() {
        H2dbClient = new H2DbClient();
        try {
            H2dbClient.run(CREATE_DB_CUSTOMER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Creating table in given database...");
    }

    @Override
    public List<Customer> selectAll(int customerId) {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
             ResultSet sqlResult = stmt.executeQuery(SELECT_ALL + customerId);
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                customers.add(new Customer(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void add(String name, int company_id) {
        try {
            H2dbClient.run(ADD + "('" + name + "'," + company_id + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
