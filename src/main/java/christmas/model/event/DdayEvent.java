package christmas.model.event;

import christmas.model.Orders;
import java.util.Arrays;
import java.util.function.Function;

public enum DdayEvent {
    CHRISTMAS_D_DAY("크리스마스 디데이 할인", 1, 25, (visitDate) -> (long) ((visitDate - 1) * 100L + 1_000));

    private final String name;
    private final int startDate;
    private final int endDate;
    private final Function<Integer, Long> discountedAmount;

    DdayEvent(String name, int startDate, int endDate, Function<Integer, Long> discountedAmount) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountedAmount = discountedAmount;
    }

    public String getName() {
        return name;
    }

    public static boolean isInProgress(int visitDate) {
        boolean isInProgress = false;
        for (DdayEvent dDayEvent : DdayEvent.values()) {
            if (visitDate >= dDayEvent.startDate && visitDate <= dDayEvent.endDate) {
                isInProgress = true;
            }
        }
        return isInProgress;
    }

    public static DdayEvent getAppliedEvent(int visitDate) {
        return Arrays.stream(DdayEvent.values())
                .filter(event -> event.startDate <= visitDate && event.endDate >= visitDate).findAny().orElseThrow();
    }

    public long getDiscountedAmount(int visitDate) {
        long discountedAmount = 0;

        for (DdayEvent dDayEvent : DdayEvent.values()) {
            if (visitDate > dDayEvent.startDate && visitDate < dDayEvent.endDate) {
                discountedAmount += dDayEvent.discountedAmount.apply(visitDate);
            }
        }
        return discountedAmount;
    }
}
