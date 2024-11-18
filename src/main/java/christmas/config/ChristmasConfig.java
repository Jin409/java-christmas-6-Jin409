package christmas.config;

import christmas.controller.ChristmasController;
import christmas.model.MenuRepository;
import christmas.service.MenuService;

public class ChristmasConfig {
    public MenuRepository menuRepository() {
        return new MenuRepository();
    }

    public MenuService menuService() {
        return new MenuService(menuRepository());
    }

    public ChristmasController christmasController() {
        return new ChristmasController(menuService());
    }
}
