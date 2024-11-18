package christmas.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputValidator {
    private static final String REGEX_OF_INTEGER = "\\d+";
    private static final String REGEX_OF_KOREAN = "^[ㄱ-ㅎ가-힣]*$";

    public static void validateVisitDate(String rawVisitDate) {
        if (!rawVisitDate.matches(REGEX_OF_INTEGER)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }

        int visitDate = Integer.parseInt(rawVisitDate);

        if (hasInvalidDateRange(visitDate)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private static boolean hasInvalidDateRange(int visitDate) {
        return visitDate > 31 || visitDate < 1;
    }

    public static void validateOrders(String rawOrders) {
        if (!rawOrders.contains(Constants.DELIMITER_OF_ORDER)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        List<String> orders = Arrays.asList(rawOrders.split(Constants.DELIMITER_OF_ORDER));
        orders.forEach(InputValidator::validateSingleOrder);

        hasDuplicatedMenu(orders);
    }

    public static void hasDuplicatedMenu(List<String> orders) {
        Set<String> menuName = new HashSet<>();

        for (String order : orders) {
            String[] splitOrder = order.split(Constants.DELIMITER_OF_MENU);
            menuName.add(splitOrder[0]);
        }

        if (orders.size() != menuName.size()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private static void validateSingleOrder(String order) {
        if (!order.contains(Constants.DELIMITER_OF_MENU)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        String[] splitOrder = order.split(Constants.DELIMITER_OF_MENU);

        if (!splitOrder[0].matches(REGEX_OF_KOREAN)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        if (!splitOrder[1].matches(REGEX_OF_INTEGER)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        if (hasInvalidQuantityRange(Integer.parseInt(splitOrder[1]))) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private static boolean hasInvalidQuantityRange(int quantity) {
        return quantity < 1;
    }
}
