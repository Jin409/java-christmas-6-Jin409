package christmas.model;

import java.util.List;

public class Orders {
    private static final int VALID_SIZE = 20;

    private final List<Order> orders;
    private final int visitDate;

    public Orders(int visitDate, List<Order> orders) {
        validate(orders);

        this.visitDate = visitDate;
        this.orders = orders;
    }

    public int getVisitDate() {
        return visitDate;
    }

    public List<Order> getOrders() {
        return List.copyOf(orders);
    }

    private void validate(List<Order> orders) {
        long totalQuantity = orders.stream().mapToLong(Order::getQuantity).sum();

        if (totalQuantity > VALID_SIZE) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        if (containsOnlyDrink(orders)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private boolean containsOnlyDrink(List<Order> orders) {
        boolean containsOnlyDrink = true;
        for (Order order : orders) {
            if (order.doesOrderBelongTo(MenuCategory.DRINK)) {
                continue;
            }
            containsOnlyDrink = false;
        }
        return containsOnlyDrink;
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

    public long getOriginPrice() {
        return orders.stream().mapToLong(order -> order.getOrderedPrice()).sum();
    }
}
