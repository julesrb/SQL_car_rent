package CarSharing;

public class Car {
    private String  name;
    private int  company_id;
    private int     id;

    public Car(int id, String name, int company_id) {
        this.name = name;
        this.id = id;
        this.company_id = company_id;
    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    int getCompanyId() {
        return company_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
