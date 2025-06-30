package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CarStoreManagement {
    public static void main(String[] args) {
        CarManager carManager = new CarManager();
        CustomerManager customerManager = new CustomerManager();
        InvoiceManager invoiceManager = new InvoiceManager();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        customerManager.addCustomer(new Customer("C001", "Nguyen Van A", "0123456789", "Hanoi"));
        customerManager.addCustomer(new Customer("C002", "Tran Thi B", "0987654321", "HCM"));
        carManager.addCar(new Car("CAR001", "Toyota", "Camry", 25000.0, "Available", new Date()));
        carManager.addCar(new Car("CAR002", "Honda", "Civic", 22000.0, "Available", new Date()));

        while (true) {
            System.out.println("\nCar Store Management System");
            System.out.println("1. Add Customer");
            System.out.println("2. Display Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Add Car");
            System.out.println("6. Display Cars");
            System.out.println("7. Update Car");
            System.out.println("8. Delete Car");
            System.out.println("9. Create Invoice");
            System.out.println("10. Display Invoices");
            System.out.println("11. Delete Invoice");
            System.out.println("12. Display Available Cars by Import Date");
            System.out.println("13. Display Customers with Transactions Today");
            System.out.println("14. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                if (choice == 14) {
                    System.out.println("Exiting...");
                    break;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter Customer ID: ");
                        String customerId = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();
                        customerManager.addCustomer(new Customer(customerId, name, phone, address));
                        break;

                    case 2:
                        customerManager.displayCustomers();
                        break;

                    case 3:
                        System.out.print("Enter Customer ID: ");
                        customerId = scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter New Phone: ");
                        phone = scanner.nextLine();
                        System.out.print("Enter New Address: ");
                        address = scanner.nextLine();
                        customerManager.updateCustomer(customerId, name, phone, address);
                        break;

                    case 4:
                        System.out.print("Enter Customer ID: ");
                        customerId = scanner.nextLine();
                        customerManager.deleteCustomer(customerId);
                        break;

                    case 5:
                        System.out.print("Enter Car ID: ");
                        String carId = scanner.nextLine();
                        System.out.print("Enter Brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Enter Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter Status (Available/Sold): ");
                        String status = scanner.nextLine();
                        System.out.print("Enter Import Date (dd/MM/yyyy): ");
                        String dateStr = scanner.nextLine();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date importDate = sdf.parse(dateStr);
                            carManager.addCar(new Car(carId, brand, model, price, status, importDate));
                        } catch (Exception e) {
                            System.out.println("Error: Invalid date format! Please use dd/MM/yyyy.");
                        }
                        break;

                    case 6:
                        carManager.displayCars();
                        break;

                    case 7:
                        System.out.print("Enter Car ID: ");
                        carId = scanner.nextLine();
                        System.out.print("Enter New Brand: ");
                        brand = scanner.nextLine();
                        System.out.print("Enter New Model: ");
                        model = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        price = scanner.nextDouble();
                        carManager.updateCar(carId, brand, model, price);
                        break;

                    case 8:
                        System.out.print("Enter Car ID: ");
                        carId = scanner.nextLine();
                        carManager.deleteCar(carId);
                        break;

                    case 9:
                        System.out.print("Enter Invoice ID: ");
                        String invoiceId = scanner.nextLine();
                        System.out.print("Enter Customer ID: ");
                        customerId = scanner.nextLine();
                        System.out.print("Enter Car ID: ");
                        carId = scanner.nextLine();
                        invoiceManager.createInvoice(invoiceId, customerId, carId, customerManager, carManager);
                        break;

                    case 10:
                        invoiceManager.displayInvoices();
                        break;

                    case 11:
                        System.out.print("Enter Invoice ID: ");
                        invoiceId = scanner.nextLine();
                        invoiceManager.deleteInvoice(invoiceId, customerManager, carManager);
                        break;

                    case 12:
                        System.out.print("Enter Import Date (dd/MM/yyyy): ");
                        dateStr = scanner.nextLine();
                        carManager.displayAvailableCarsByImportDate(dateStr);
                        break;

                    case 13:
                        customerManager.displayCustomersWithTransactionsToday(invoiceManager.getInvoices());
                        break;

                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input! Please try again.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}