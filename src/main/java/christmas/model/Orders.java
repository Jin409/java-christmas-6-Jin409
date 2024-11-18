package christmas.model;

import java.util.List;

public class Orders {
    private final List<Order> orders;
    private final int visitDate;

    public Orders(int visitDate, List<Order> orders) {
        this.visitDate = visitDate;
        this.orders = orders;
    }

    public long getQuantityOf(MenuCategory applicableCategory) {
        long quantity = 0;
        for (Order order : orders) {
            if (order.doesOrderBelongTo(applicableCategory)) {
                quantity += order.getQuantity();
            }
        }
        return quantity;
    }
}
