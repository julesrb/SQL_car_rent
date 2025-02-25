public class Company {
    private String  name;
    private int     id;

    public Company(int id, String name) {
        this.name = name;
        this.id = id;
    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
