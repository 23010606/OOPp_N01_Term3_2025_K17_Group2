package test;
import manager.CustomerManager;
import model.Customer;

import java.util.Scanner;


import java.time.LocalDateTime;

public class CustomerManagerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerManager manager = new CustomerManager();

        while (true) {
            System.out.println("MENU QUAN LY KHACH HANG");
            System.out.println("1. Them khach hang moi");
            System.out.println("2. Cap nhat thong tin khach hang");
            System.out.println("3. Xoa khach hang");
            System.out.println("4. Hien thi danh sach khach hang");
            System.out.println("5. Tim khach hang theo so dien thoai");
            System.out.println("6. Thoat");
            System.out.print("Nhap lua chon cua ban: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Đọc dòng mới
            } catch (Exception e) {
                System.out.println("Loi: Vui long nhap so! Nhap lai.");
                scanner.nextLine(); // Xóa lỗi
                continue;
            }

            if (choice == 6) {
                System.out.println("Tam biet!");
                scanner.close();
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhap ID khach hang: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Đọc dòng mới
                    System.out.print("Nhap ten khach hang: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhap so dien thoai: ");
                    String phone = scanner.nextLine();
                    System.out.print("Nhap dia chi: ");
                    String address = scanner.nextLine();
                    Customer newCustomer = new Customer(id, name, phone, address, LocalDateTime.now());
                    manager.addCustomer(newCustomer);
                    break;

                case 2:
                    System.out.print("Nhap ID khach hang can cap nhat: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Đọc dòng mới
                    System.out.print("Nhap ten moi: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhap so dien thoai moi: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Nhap đia chi moi: ");
                    String newAddress = scanner.nextLine();
                    manager.updateCustomer(updateId, newName, newPhone, newAddress);
                    break;

                case 3:
                    System.out.print("Nhap ID khach hang can xoa: ");
                    int deleteId = scanner.nextInt();
                    manager.deleteCustomer(deleteId);
                    break;
                case 4:
                    System.out.println("Danh sach khach hang:");
                    for (Customer customer : manager.getCustomers()) {
                        System.out.println(customer);
                    }
                    break;
                case 5: 
                    System.out.print("Nhap so dien thoai can tim: ");
                    String searchPhone = scanner.nextLine();
                    boolean found = false;
                    for (Customer customer : manager.getCustomers()) {
                        if (customer.getPhone().equals(searchPhone)) {
                            System.out.println("Khach hang tim thay: " + customer);
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        System.out.println("Khong tim thay khach hang voi so dien thoai: " + searchPhone);
                    }
                    break;
                default:
                    System.out.println("out system");
            }
        }
    }
}
