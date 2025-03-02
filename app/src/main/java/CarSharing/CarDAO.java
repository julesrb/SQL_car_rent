package CarSharing;

import java.util.List;

public interface CarDAO {
    List<Car> selectAll(int companyId);
    List<Car> selectCompanyId(int companyId);
    List<Car> selectCarId(int carId);
    void add(String name, int company_id);
}
