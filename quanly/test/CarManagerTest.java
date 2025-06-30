package test;
import manager.CarManager;
import model.Car;
import java.util.Scanner;

public class CarManagerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarManager carManager = new CarManager();

        carManager.addCar(new Car("XE01", "Toyota Camry", "Toyota", 50000.0, 10, "conhang"));
        carManager.addCar(new Car("XE02", "Honda Civic", "Honda", 45000.0, 0, "conhang")); 
        carManager.addCar(new Car("XE03", "Mazda CX-5", "Mazda", 55000.0, 5, "hethang")); 

        while (true) {
            System.out.println(" MENU QUAN LY XE");
            System.out.println("1. Them xe moi");
            System.out.println("2. Sua thong tin xe");
            System.out.println("3. Xoa xe");
            System.out.println("4. Hien thi danh sach xe con hang");
            System.out.println("5. Hien thi tat ca xe");
            System.out.println("6. Kiem tra xe co san");
            System.out.println("7. Thoat");
            System.out.print("Nhap lua chon cua ban: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Loi: Vui long nhap so! Nhap lai.");
                scanner.nextLine();
                continue;
            }

            if (choice == 7) {
                System.out.println("Tam biet!");
                scanner.close();
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhap Ma xe: ");
                    String carId = scanner.nextLine();
                    System.out.print("Nhap Ten xe: ");
                    String carName = scanner.nextLine();
                    System.out.print("Nhap Hang xe: ");
                    String brand = scanner.nextLine();
                    System.out.print("Nhap Gia: ");
                    double price = scanner.nextDouble();
                    System.out.print("Nhap So luong: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhap Trang thai (conhang/hethang): ");
                    String status = scanner.nextLine();
                    Car newCar = new Car(carId, carName, brand, price, quantity, status);
                    carManager.addCar(newCar);
                    break;

                case 2:
                    System.out.print("Nhap Ma xe can sua: ");
                    String editCarId = scanner.nextLine();
                    System.out.print("Nhap Ten xe moi: ");
                    String newCarName = scanner.nextLine();
                    System.out.print("Nhap Hang xe moi: ");
                    String newBrand = scanner.nextLine();
                    System.out.print("Nhap Gia moi: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Nhap So luong moi: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhap Trang thai moi (conhang/hethang): ");
                    String newStatus = scanner.nextLine();
                    carManager.updateCar(editCarId, newCarName, newBrand, newPrice, newQuantity, newStatus);
                    break;

                case 3:
                    System.out.print("Nhap Ma xe can xoa: ");
                    String deleteCarId = scanner.nextLine();
                    carManager.deleteCar(deleteCarId);
                    break;

                case 4:
                    System.out.println("Danh sach xe con hang:");
                    carManager.displayAvailableCars();
                    break;

                case 5:
                    System.out.println("Tat ca xe:");
                    carManager.printCarList();
                    break;

                case 6:
                    System.out.print("Nhap Ma xe can kiem tra: ");
                    String checkCarId = scanner.nextLine();
                    boolean isAvailable = carManager.checkCarAvailability(checkCarId);
                    if (isAvailable) {
                        System.out.println("Xe con ma " + checkCarId + " san sang de ban.");
                    }
                    break;

                default:
                    System.out.println("Lua chon khong hop le! Vui long chon lai.");
            }
        }
    }
}