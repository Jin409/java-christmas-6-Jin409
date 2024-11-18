package christmas.model;

public class Menu {
    private final String name;
    private final long price;
    private final MenuCategory menuCategory;

    public Menu(String name, long price, MenuCategory menuCategory) {
        this.name = name;
        this.price = price;
        this.menuCategory = menuCategory;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean doesMenuBelongTo(MenuCategory comparedCategory) {
        return comparedCategory.equals(this.menuCategory);
    }
}
