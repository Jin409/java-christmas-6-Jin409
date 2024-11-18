package christmas.view;

import christmas.dto.OrderRequestDto;

public class InputParser {
    public static OrderRequestDto toOrderRequestDto(String rawOrder) {
        String[] splitOrder = rawOrder.split(Constants.DELIMITER_OF_MENU);
        return new OrderRequestDto(splitOrder[0], Long.parseLong(splitOrder[1]));
    }
}
