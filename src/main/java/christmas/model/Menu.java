package christmas.model;

import christmas.Category;

public class Menu {
    private final String name;
    private final long price;
    private final Category category;

    public Menu(String name, long price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
