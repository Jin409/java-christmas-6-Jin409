package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.config.ChristmasConfig;
import christmas.dto.OrderRequestDto;
import christmas.service.MenuService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MenuServiceTest {
    @Test
    void 존재하지_않는_메뉴에_대한_주문인_경우_예외를_던진다() {
        // given
        List<OrderRequestDto> orderRequestDtos = List.of(new OrderRequestDto("없는메뉴", 1));
        MenuService menuService = new ChristmasConfig().menuService();

        // when & then
        assertThatThrownBy(() -> menuService.validateOrders(orderRequestDtos))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
