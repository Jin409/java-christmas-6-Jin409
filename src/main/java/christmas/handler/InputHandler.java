package christmas.handler;

import christmas.dto.OrderRequestDto;
import christmas.view.Constants;
import christmas.view.InputParser;
import christmas.view.InputValidator;
import christmas.view.InputView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputHandler {
    public static int getVisitDate() {
        String rawVisitDate = InputView.getVisitDate();
        InputValidator.validateVisitDate(rawVisitDate);
        return Integer.parseInt(rawVisitDate);
    }

    public static List<OrderRequestDto> getOrders() {
        String rawOrders = InputView.getOrderedMenus();
        InputValidator.validateOrders(rawOrders);

        List<String> orders = Arrays.asList(rawOrders.split(Constants.DELIMITER_OF_ORDER));
        List<OrderRequestDto> orderRequestDtos = new ArrayList<>();
        orders.forEach(order -> orderRequestDtos.add(InputParser.toOrderRequestDto(order)));
        return orderRequestDtos;
    }

}
