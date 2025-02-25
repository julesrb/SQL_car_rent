import java.util.List;

public interface CarDAO {
    List<Car> selectAll(int companyId);
    void add(String name, int company_id);
}
