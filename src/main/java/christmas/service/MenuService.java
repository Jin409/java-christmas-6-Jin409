package christmas.service;

import christmas.model.Menu;
import christmas.model.MenuRepository;
import java.util.List;

public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public void saveMenus(List<Menu> menus) {
        menus.forEach(menuRepository::save);
    }
}
