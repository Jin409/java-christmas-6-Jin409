package christmas.config;

import christmas.controller.ChristmasController;
import christmas.model.MenuRepository;
import christmas.service.BillsService;
import christmas.service.MenuService;
import christmas.service.OrderService;

public class ChristmasConfig {
    public MenuRepository menuRepository() {
        return new MenuRepository();
    }

    public MenuService menuService() {
        return new MenuService(menuRepository());
    }

    public OrderService orderService() {
        return new OrderService(menuRepository());
    }

    public BillsService eventService() {
        return new BillsService();
    }

    public ChristmasController christmasController() {
        return new ChristmasController(menuService(), orderService(), eventService());
    }
}
