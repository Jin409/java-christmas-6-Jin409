package christmas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuRepository {
    private static List<Menu> menus = new ArrayList<>();

    public MenuRepository() {
    }

    public void save(Menu menu) {
        menus.add(menu);
    }

    public boolean isExist(String comparedName) {
        Optional<Menu> foundMenu = menus.stream()
                .filter(menu -> menu.getName().equals(comparedName))
                .findAny();
        return foundMenu.isPresent();
    }
}
