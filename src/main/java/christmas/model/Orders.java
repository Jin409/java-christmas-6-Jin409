package christmas.model;

import java.util.List;

public class Orders {
    private final List<Order> orders;
    private final int visitDate;

    public Orders(int visitDate, List<Order> orders) {
        this.visitDate = visitDate;
        this.orders = orders;
    }
}
