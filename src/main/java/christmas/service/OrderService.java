package christmas.service;


import christmas.dto.OrderRequestDto;
import christmas.model.Menu;
import christmas.model.MenuRepository;
import christmas.model.Order;
import christmas.model.Orders;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final MenuRepository menuRepository;

    public OrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Orders saveOrders(List<OrderRequestDto> orderRequestDtos, int visitDate) {
        validateOrders(orderRequestDtos);

        List<Order> orders = new ArrayList<>();
        for (OrderRequestDto orderRequestDto : orderRequestDtos) {
            Menu menu = findMenuWithName(orderRequestDto.menuName());
            orders.add(new Order(menu, orderRequestDto.quantity()));
        }

        return new Orders(visitDate, orders);
    }

    private Menu findMenuWithName(String name) {
        return menuRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."));
    }

    private void validateOrders(List<OrderRequestDto> orderRequestDtos) {
        for (OrderRequestDto orderRequestDto : orderRequestDtos) {
            String menuName = orderRequestDto.menuName();
            if (menuRepository.findByName(menuName).isPresent()) {
                continue;
            }
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }
}
