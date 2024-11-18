package christmas.controller;

import christmas.dto.OrderRequestDto;
import christmas.handler.ErrorHandler;
import christmas.handler.InputHandler;
import christmas.model.Category;
import christmas.model.Menu;
import christmas.service.MenuService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ChristmasController {
    private final MenuService menuService;

    public ChristmasController(MenuService menuService) {
        this.menuService = menuService;
    }

    public void run() {
        readyToOpen();

        int visitDate = retryOnInvalidInput(InputHandler::getVisitDate);
        List<OrderRequestDto> orderRequestDtos = retryOnInvalidInput(InputHandler::getOrders);

        menuService.validateOrders(orderRequestDtos);
    }

    private <T> T retryOnInvalidInput(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                ErrorHandler.handle(e);
            }
        }
    }

    private void readyToOpen() {
        List<Menu> menus = new ArrayList<>();

        menus.add(new Menu("양송이수프", 6_000, Category.findByValue("에피타이저")));
        menus.add(new Menu("타파스", 5_500, Category.findByValue("에피타이저")));
        menus.add(new Menu("시저샐러드", 8_000, Category.findByValue("에피타이저")));
        menus.add(new Menu("티본스테이크", 55_000, Category.findByValue("메인")));
        menus.add(new Menu("바비큐립", 54_000, Category.findByValue("메인")));
        menus.add(new Menu("해산물파스타", 35_000, Category.findByValue("메인")));
        menus.add(new Menu("크리스마스파스타", 25_000, Category.findByValue("메인")));
        menus.add(new Menu("초코케이크", 15_000, Category.findByValue("디저트")));
        menus.add(new Menu("아이스크림", 5_000, Category.findByValue("디저트")));
        menus.add(new Menu("제로콜라", 3_000, Category.findByValue("음료")));
        menus.add(new Menu("레드와인", 60_000, Category.findByValue("음료")));
        menus.add(new Menu("샴페인", 25_000, Category.findByValue("음료")));

        menuService.saveMenus(menus);

    }
}
