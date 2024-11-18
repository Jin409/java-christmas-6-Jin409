package christmas.model;

public class Order {
    private final Menu menu;
    private final long quantity;

    public Order(Menu menu, long quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public boolean doesOrderBelongTo(MenuCategory category) {
        return menu.doesMenuBelongTo(category);
    }
}
