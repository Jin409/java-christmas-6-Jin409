package christmas.model.event;

import java.util.function.Predicate;

public enum SpecialEvent {
    DECEMBER_SEASON(1, 31, (visitDate) -> visitDate % 7 == 3 || visitDate == 25, 1_000);

    private final int startDate;
    private final int endDate;
    private final Predicate<Integer> isApplicable;
    private final long discountedAmount;

    SpecialEvent(int startDate, int endDate, Predicate<Integer> isApplicable, long discountedAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApplicable = isApplicable;
        this.discountedAmount = discountedAmount;
    }

    public static long getDiscountedAmount(int visitDate) {
        long discountedAmount = 0;

        for (SpecialEvent specialEvent : SpecialEvent.values()) {
            if (visitDate > specialEvent.startDate && visitDate < specialEvent.endDate
                    && specialEvent.isApplicable.test(visitDate)) {
                discountedAmount += specialEvent.discountedAmount;
            }
        }
        return discountedAmount;
    }
}
