package christmas.service;

import christmas.dto.OrderRequestDto;
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

    public void validateOrders(List<OrderRequestDto> orderRequestDtos) {
        for (OrderRequestDto orderRequestDto : orderRequestDtos) {
            String menuName = orderRequestDto.menuName();
            if (menuRepository.findByName(menuName)) {
                continue;
            }
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }
}
