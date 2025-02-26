import java.util.List;

public interface CompanyDAO {
    List<Company> selectAll();
    Company select(int companyId);
    void add(String name);
}
