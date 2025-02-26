import java.util.List;
import java. util.Scanner;
import java.util.stream.Collectors;

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
        while (choice != 0 && choice != 1 && choice != 2 && choice != 3) {
            try {
                System.out.println();
                System.out.println("1. Log in as a manager");
                System.out.println("2. Log in as a customer");
                System.out.println("3. Create a customer");
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
                case 3:
                    createCustomerMenu();
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

    private void customerListMenu() {
        List<Customer> customers = customerDAO.selectAll();
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
                System.out.println("Customer ID: " + customers.get(0).getId());
                System.out.println("Customer ID: " + customers.get(1).getId());
                customerMenu(customers.get(choice - 1)); // ??
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
                System.out.println("Customer ID: " + customer.getId());
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("enter a valid number");
            }
            switch (choice) {
                case 0:
                    return;

                case 1:
                    rentACarListMenu(customer);
                    choice = -1;
                    break;

                case 2:
                    returnACarMenu(customer);
                    choice = -1;
                    break;
                case 3:
                    rentedCarListMenu(customer);
                    choice = -1;
                    break;
            }
        }
    }

    private void rentedCarListMenu(Customer customer) {
        List<Customer> customers = customerDAO.selectCustomer(customer.getId());
        int carId = customers.getFirst().getRentedCarId();
        System.out.println();
        if (carId == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            List<Car> cars = carDAO.selectCarId(carId);
            System.out.println("Your rented car:");
            System.out.println(cars.getFirst().getName());
            Company company = companyDAO.select(cars.getFirst().getCompanyId());
            System.out.println("Company:");
            System.out.println(company.getName());
        }
    }

    private void createCustomerMenu() {
        System.out.println();
        System.out.println("Enter the customer name:");
        System.out.print("> ");
        String name = sc.nextLine();
        customerDAO.add(name);
    }

    private void rentACarListMenu(Customer customer) {
        List<Company> companies = companyDAO.selectAll();
        System.out.println("Customer ID: " + customer.getId());
        List<Customer> customers = customerDAO.selectCustomer(customer.getId());
        int carId = customers.getFirst().getRentedCarId();
        if (companies.isEmpty()) {
            System.out.println();
            System.out.println("The company list is empty!");
            return;
        } else if (carId != 0) {
            System.out.println("You've already rented a car!");
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Car ID: " + carId);
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
                else {
                    int returned = chooseCarMenu(companies.get(choice - 1), customer);
                    if (returned == 1)
                        return;
                    choice = -1;
                }
            }
        }
    }

    private int chooseCarMenu(Company company, Customer customer) {
        List<Car> cars = carDAO.selectCompanyId(company.getId());
        List<Customer> customers = customerDAO.selectAll();
        for (Customer custom : customers) {
            System.out.println("*" + custom.getRentedCarId());
        }
        List<Integer> rentedCarIds = customers.stream()
                .map(Customer::getRentedCarId)
                .toList();
        System.out.println("**Rented Car IDs: " + rentedCarIds);
        cars.removeIf(car -> rentedCarIds.contains(car.getId()));
        if (cars.isEmpty()) {
            System.out.println();
            System.out.println("No available cars in the "+ company.getName() + " company");
            return 0;
        } else {
            int choice = -1;
            while (choice < 0 || choice > cars.size()) {
                try {
                    System.out.println();
                    System.out.println("Choose a car:");
                    int index = 1;
                    for (Car car : cars) {
                        System.out.println(index + ". " + car.getName());
                        index++;
                    }
                    System.out.println("0. Back");
                    System.out.print("> ");
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("enter a valid number");
                }
                if (choice == 0)
                    return 0;
                customerDAO.updateRent(cars.get(choice - 1), customer.getId());
                System.out.println("You rented '" + cars.get(choice - 1).getName() + "'");
                }
        }
        return 1;
    }

    private void returnACarMenu(Customer customer) {
        customerDAO.selectCustomer(customer.getId());
        List<Customer> customers = customerDAO.selectCustomer(customer.getId());
        int carId = customers.getFirst().getRentedCarId();
        if (carId == 0) {
            System.out.println("You didn't rent a car!");
        }
        else {
            customerDAO.updateReturn(carId);
            System.out.println("You've returned a rented car!");
        }

    }


}