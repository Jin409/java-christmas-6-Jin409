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

    public Optional<Menu> findByName(String comparedName) {
        return menus.stream()
                .filter(menu -> menu.getName().equals(comparedName))
                .findAny();
    }
}
