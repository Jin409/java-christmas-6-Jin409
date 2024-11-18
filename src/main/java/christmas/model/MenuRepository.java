package christmas.model;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private static List<Menu> menus = new ArrayList<>();

    public MenuRepository() {
    }

    public void save(Menu menu) {
        menus.add(menu);
    }
}
