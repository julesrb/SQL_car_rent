import java.util.List;
import java. util.Scanner;

public class Menu {
    private final Scanner sc;
    CompanyDAO companyDAO;
    CarDAO carDAO;
    CustomerDAO customerDAO;

    public Menu() {
        sc = new Scanner(System.in);
        companyDAO = new CompanyDAOImplementation();
        carDAO = new CarDAOImplementation();
        customerDAO = new CustomerDAOImplementation();
    }

    public void startMenu() {
        int choice = -1;
        while (choice != 0 && choice != 1) {
            try {
                System.out.println();
                System.out.println("1. Log in as a manager");
                System.out.println("2. Log in as a customer");
                System.out.println("0. Exit");
                System.out.print("> ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("enter a valid number");
            }
            switch (choice) {
                case 0:
                    return;

                case 1:
                    loginMenu();
                    choice = -1;
                    break;
                case 2:
                    customerListMenu();
                    choice = -1;
                    break;
            }
        }
    }

    private void loginMenu() {
        int choice = -1;
        while (choice != 0 && choice != 1 && choice != 2) {
            try {
                System.out.println();
                System.out.println("1. Company list");
                System.out.println("2. Create a company");
                System.out.println("0. Back");
                System.out.print("> ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("enter a valid number");
            }
            switch (choice) {
                case 0:
                    return;

                case 1:
                    companyListMenu();
                    choice = -1;
                    break;

                case 2:
                    createCompanyMenu();
                    choice = -1;
                    break;
            }
        }
    }

    private void companyListMenu() {
        List<Company> companies = companyDAO.selectAll();
        if (companies.isEmpty()) {
            System.out.println();
            System.out.println("Choose a company:");
            System.out.println("The company list is empty!");
        } else {
            int choice = -1;
            while (choice < 0 || choice > companies.size()) {
                try {
                    System.out.println();
                    System.out.println("Choose a company:");
                    for (Company company : companies)
                        System.out.println(company.getId() + ". " + company.getName());
                    System.out.println("0. Back");
                    System.out.print("> ");
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("enter a valid number");
                }
                if (choice == 0)
                    return;
                companyMenu(companies.get(choice - 1));
                return;
            }
        }
    }

    private void createCompanyMenu() {
        System.out.println("Enter the company name:");
        System.out.print("> ");
        String name = sc.nextLine();
        companyDAO.add(name);
    }

    private void companyMenu(Company company) {
        int choice = -1;
        while (choice != 0 && choice != 1 && choice != 2) {
            try {
                System.out.println();
                System.out.println(company.getName() + " name:");
                System.out.println("1. Car list");
                System.out.println("2. Create a car");
                System.out.println("0. Back");
                System.out.print("> ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("enter a valid number");
            }
            switch (choice) {
                case 0:
                    return;

                case 1:
                    carListMenu(company.getId());
                    choice = -1;
                    break;

                case 2:
                    createCarMenu(company.getId());
                    choice = -1;
                    break;
            }
        }
    }

    private void carListMenu(int companyId) {
        List<Car> cars = carDAO.selectAll(companyId);
        if (cars.isEmpty()) {
            System.out.println();
            System.out.println("Car list:");
            System.out.println("The car list is empty!");

        } else {
            System.out.println();
            System.out.println("Car list:");
            int index = 1;
            for (Car car : cars) {
                System.out.println(index + ". " + car.getName());
                index++;
            }
        }
    }

    private void createCarMenu(int companyId) {
        System.out.println("Enter the car name:");
        System.out.print("> ");
        String name = sc.nextLine();
        carDAO.add(name, companyId);
    }

    private void customerListMenu(int customerID) {
        List<Customer> customers = customerDAO.selectAll(customerID);
        if (customers.isEmpty()) {
            System.out.println();
            System.out.println("Choose a customer:");
            System.out.println("The customer list is empty!");
        } else {
            int choice = -1;
            while (choice < 0 || choice > customers.size()) {
                try {
                    System.out.println();
                    System.out.println("Choose a customer:");
                    for (Customer customer : customers)
                        System.out.println(customer.getId() + ". " + customer.getName());
                    System.out.println("0. Back");
                    System.out.print("> ");
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("enter a valid number");
                }
                if (choice == 0)
                    return;
                customerMenu(customers.get(choice - 1));
                return;
            }
        }
    }

    private void customerMenu(Customer customer) {
        int choice = -1;
        while (choice != 0 && choice != 1 && choice != 2 && choice != 3) {
            try {
                System.out.println();
                System.out.println("1. Rent a car");
                System.out.println("2. Return a rented car");
                System.out.println("3. My rented car");
                System.out.println("0. Back");
                System.out.print("> ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("enter a valid number");
            }
            switch (choice) {
                case 0:
                    return;

                case 1:
//                    rentACarMenu(company.getId());
                    choice = -1;
                    break;

                case 2:
//                    returnACarMenu(company.getId());
                    choice = -1;
                    break;
                case 3:
                    rentedCarListMenu(customer.getId());
                    choice = -1;
                    break;
            }
        }
    }

    private void rentedCarListMenu(int customerId) {
        Car car = carDAO.select(customerId);
        if (car == null) {
            System.out.println();
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println();
            System.out.println("Your rented car:");
            System.out.println();
            Customer customer = customerDAO.select(customerId);
            System.out.println("Company:");
            System.out.println();
        }
    }
}