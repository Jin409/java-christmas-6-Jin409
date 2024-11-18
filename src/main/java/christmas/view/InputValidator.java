package christmas.view;

public class InputValidator {
    private static final String REGEX_OF_INTEGER = "\\d+";

    public static void validateVisitDate(String rawVisitDate) {
        if (!rawVisitDate.matches(REGEX_OF_INTEGER)) {
            throw new IllegalArgumentException("[ERROR] 방문 날짜는 정수만 입력하실 수 있습니다.");
        }

        int visitDate = Integer.parseInt(rawVisitDate);

        if (hasInvalidDateRange(visitDate)) {
            throw new IllegalArgumentException("[ERROR] 방문 날짜는 1에서 31일 사이여야 합니다.");
        }
    }

    private static boolean hasInvalidDateRange(int visitDate) {
        return visitDate > 31 || visitDate < 1;
    }
}
