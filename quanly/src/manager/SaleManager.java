package manager;

public class SaleManager {
    private CarManager carManager = new CarManager();
    private OrderManager orderManager = new OrderManager();

    public void manageCarSaleProcess(int orderId, int customerId, String carId, double deposit) {
        if (!carManager.checkCarAvailability(carId)) {
            return;
        }
        orderManager.createOrderWithDeposit(orderId, customerId, carId, deposit);
        orderManager.findOrderById(orderId);
    }
}