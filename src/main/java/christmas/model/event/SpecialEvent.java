package christmas.model.event;

import java.util.Arrays;
import java.util.function.Predicate;

public enum SpecialEvent {
    DECEMBER_SEASON("특별 할인", 1, 31, (visitDate) -> visitDate % 7 == 3 || visitDate == 25, 1_000);

    private final String name;
    private final int startDate;
    private final int endDate;
    private final Predicate<Integer> isApplicable;
    private final long discountedAmount;

    SpecialEvent(String name, int startDate, int endDate, Predicate<Integer> isApplicable, long discountedAmount) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApplicable = isApplicable;
        this.discountedAmount = discountedAmount;
    }

    public String getName() {
        return name;
    }

    public static boolean isInProgress(int visitDate) {
        boolean isInProgress = false;
        for (SpecialEvent specialEvent : SpecialEvent.values()) {
            if (visitDate >= specialEvent.startDate && visitDate <= specialEvent.endDate) {
                isInProgress = true;
            }
        }
        return isInProgress;
    }

    public static SpecialEvent getAppliedEvent(int visitDate) {
        return Arrays.stream(SpecialEvent.values())
                .filter(event -> event.startDate <= visitDate && event.endDate >= visitDate).findAny().orElseThrow();
    }

    public long getDiscountedAmount(int visitDate) {
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
