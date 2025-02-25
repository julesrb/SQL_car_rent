import java.util.List;

public interface CompanyDAO {
    List<Company> selectAll();
    void add(String name);
}
