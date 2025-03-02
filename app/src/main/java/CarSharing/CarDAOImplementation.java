package CarSharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImplementation implements CarDAO {

    static final String CREATE_DB_CAR = """
                                    CREATE TABLE IF NOT EXISTS car(
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE,
                                    company_id INTEGER NOT NULL,
                                    CONSTRAINT car_fk FOREIGN KEY (company_id)
                                    REFERENCES company(id))""";
    static final String ADD = """
                               INSERT INTO car (name, company_id)
                               VALUES""";
    static final String SELECT_ALL = """
                               SELECT *
                               FROM car WHERE company_id = """;


    private final H2DbClient H2dbClient;

    public CarDAOImplementation() {
        H2dbClient = new H2DbClient();
        try {
            H2dbClient.run(CREATE_DB_CAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Creating table in given database...");
    }

    @Override
    public List<Car> selectAll(int companyId) {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
             ResultSet sqlResult = stmt.executeQuery(SELECT_ALL + companyId);
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                cars.add(new Car(id, name, companyId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void add(String name, int company_id) {
        try {
            H2dbClient.run(ADD + "('" + name + "'," + company_id + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Car> selectCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();;
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet sqlResult = stmt.executeQuery("SELECT * FROM car WHERE company_id = " + companyId + ";");
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                cars.add(new Car(id, name, companyId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> selectCarId(int carId) {
        List<Car> cars = new ArrayList<>();;
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet sqlResult = stmt.executeQuery("SELECT * FROM car WHERE id = " + carId + ";");
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                int companyId = sqlResult.getInt("COMPANY_ID");
                cars.add(new Car(id, name, companyId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

}
