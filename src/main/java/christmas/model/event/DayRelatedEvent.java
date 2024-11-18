package christmas.model.event;

import christmas.model.MenuCategory;
import christmas.model.Orders;
import java.util.Arrays;
import java.util.function.Predicate;

public enum DayRelatedEvent {
    WEEK_DAYS(1, 31, MenuCategory.DESSERT, 2_023, (visitDate) -> visitDate % 7 != 1 && visitDate % 7 != 2), WEEKENDS(1,
            31, MenuCategory.MAIN, 2_203, (visitDate) -> visitDate % 7 == 1 || visitDate % 7 == 2);

    private final int startDate;
    private final int endDate;
    private final MenuCategory menuCategory;
    private final long discountAmount;
    private final Predicate<Integer> isApplicable;

    DayRelatedEvent(int startDate, int endDate, MenuCategory menuCategory, long discountAmount,
                    Predicate<Integer> isApplicable) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.menuCategory = menuCategory;
        this.discountAmount = discountAmount;
        this.isApplicable = isApplicable;
    }

    public static boolean isInProgress(int visitDate) {
        boolean isInProgress = false;
        for (DayRelatedEvent dayRelatedEvent : DayRelatedEvent.values()) {
            if (visitDate > dayRelatedEvent.startDate && visitDate < dayRelatedEvent.endDate) {
                isInProgress = true;
            }
        }
        return isInProgress;
    }

    public static long getDiscountedAmount(int visitDate, Orders orders) {
        DayRelatedEvent dayRelatedEvent = Arrays.asList(DayRelatedEvent.values()).stream()
                .filter(event -> event.isApplicable.test(visitDate)).findAny().orElseThrow();

        return orders.getQuantityOf(dayRelatedEvent.menuCategory) * dayRelatedEvent.discountAmount;
    }
}
