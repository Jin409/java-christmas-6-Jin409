package christmas.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class InputValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"메뉴-수량", "메뉴,", "1-3", "메뉴-0", "메뉴:1"})
    void 유효하지_않은_주문의_형태인_경우_예외를_던진다(String invalidOrder) {
        // when & then
        assertThatThrownBy(() -> InputValidator.validateOrders(invalidOrder)).isInstanceOf(
                IllegalArgumentException.class).hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
