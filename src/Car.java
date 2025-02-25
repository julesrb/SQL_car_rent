public class Car {
    private String  name;
    private String  company;
    private int     id;

    public Car(int id, String name) {
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
