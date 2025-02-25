import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public class H2DbClient {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public H2DbClient() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String sql) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public String getDbUrl() {
        return DB_URL;
    }

}

