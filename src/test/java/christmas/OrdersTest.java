package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.Menu;
import christmas.model.MenuCategory;
import christmas.model.Order;
import christmas.model.Orders;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OrdersTest {
    @Test
    void 주문에_음료만_포함되는_경우_예외를_던진다() {
        // given
        List<Order> orders = List.of(
                new Order(new Menu("메뉴", 1_000, MenuCategory.DRINK), 1)
        );

        // when & then
        assertThatThrownBy(() -> new Orders(1, orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
