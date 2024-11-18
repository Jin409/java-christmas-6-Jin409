package christmas.handler;

import christmas.view.InputValidator;
import christmas.view.InputView;

public class InputHandler {
    public static int getVisitDate() {
        String rawVisitDate = InputView.getVisitDate();
        InputValidator.validateVisitDate(rawVisitDate);
        return Integer.parseInt(rawVisitDate);
    }
}
