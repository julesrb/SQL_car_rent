import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImplementation implements CustomerDAO {

    static final String CREATE_DB_CUSTOMER = """
                                    CREATE TABLE IF NOT EXISTS customer(
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE,
                                    rented_car_id INTEGER NULL,
                                    CONSTRAINT customer_fk FOREIGN KEY (rented_car_id)
                                    REFERENCES car(id)
                                    ON DELETE SET NULL
                                    ON UPDATE CASCADE)""";
    static final String ADD = """
                               INSERT INTO customer (name, rented_car_id)
                               VALUES""";
    static final String SELECT_ALL = """
                               SELECT *
                               FROM customer;""";


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
    public List<Customer> selectAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
             ResultSet sqlResult = stmt.executeQuery(SELECT_ALL);
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                int rented_car_id = sqlResult.getInt("RENTED_CAR_ID");
                customers.add(new Customer(id, name, rented_car_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void add(String name) {
        try {
            H2dbClient.run(ADD + "('" + name + "',NULL)");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateRent(Car car, int customerId) {
        try {
            H2dbClient.run("UPDATE customer SET rented_car_id = " + car.getId() + " WHERE id = " + customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateReturn(int customerId) {
        try {
            H2dbClient.run("UPDATE customer SET rented_car_id = NULL WHERE id = " + customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> selectCustomer(int customerId) {
        List<Customer> customers = new ArrayList<>();;
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet sqlResult = stmt.executeQuery("SELECT * FROM customer WHERE id = " + customerId + ";");
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                int rentedCarId = sqlResult.getInt("RENTED_CAR_ID");
                if (sqlResult.wasNull()) {
                    rentedCarId = 0;
                }
                customers.add(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> selectRentedCar(int rentedCar) {
        List<Customer> customers = new ArrayList<>();;
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet sqlResult = stmt.executeQuery("SELECT * FROM customer WHERE rented_car_id = " + rentedCar + ";");
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                int rentedCarId = sqlResult.getInt("RENTED_CAR_ID");
                if (sqlResult.wasNull()) {
                    rentedCarId = 0;
                }
                customers.add(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

}
