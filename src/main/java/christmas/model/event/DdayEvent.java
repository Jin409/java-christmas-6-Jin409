package christmas.model.event;

import java.util.function.Function;

public enum DdayEvent {
    CHRISTMAS_D_DAY(1, 25, (visitDate) -> (long) ((visitDate - 1) * 100L + 1_000));

    private final int startDate;
    private final int endDate;
    private final Function<Integer, Long> discountedAmount;

    DdayEvent(int startDate, int endDate, Function<Integer, Long> discountedAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountedAmount = discountedAmount;
    }

    public static long getDiscountedAmount(int visitDate) {
        long discountedAmount = 0;

        for (DdayEvent dDayEvent : DdayEvent.values()) {
            if (visitDate > dDayEvent.startDate && visitDate < dDayEvent.endDate) {
                discountedAmount += dDayEvent.discountedAmount.apply(visitDate);
            }
        }
        return discountedAmount;
    }
}
