package CarSharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImplementation implements CompanyDAO {

    static final String CREATE_DB_COMPANY = """
                                    CREATE TABLE IF NOT EXISTS company(
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE)""";
    static final String ADD = """
                               INSERT INTO company (name)
                               VALUES""";
    static final String SELECT_ALL = """
                               SELECT *
                               FROM company;""";
    static final String SELECT = """
                               SELECT name
                               WHERE id = 
                               FROM company;""";


    private final H2DbClient H2dbClient;

    public CompanyDAOImplementation() {
        H2dbClient = new H2DbClient();
        try {
            H2dbClient.run(CREATE_DB_COMPANY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Creating table in given database...");
    }

    @Override
    public List<Company> selectAll() {
        List<Company> companies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
             ResultSet sqlResult = stmt.executeQuery(SELECT_ALL);
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public Company select(int companyId) {
        Company company = null;
        try (Connection conn = DriverManager.getConnection(H2dbClient.getDbUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet sqlResult = stmt.executeQuery("SELECT * FROM company WHERE id = " + companyId);
            while (sqlResult.next()) {
                int id = sqlResult.getInt("ID");
                String name = sqlResult.getString("NAME");
                company = new Company(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public void add(String name) {
        try {
            H2dbClient.run(ADD + "('" + name + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
