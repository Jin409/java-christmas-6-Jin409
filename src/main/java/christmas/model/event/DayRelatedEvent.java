package christmas.model.event;

import christmas.model.MenuCategory;
import christmas.model.Orders;
import java.util.Arrays;
import java.util.function.Predicate;

public enum DayRelatedEvent {
    WEEK_DAYS("평일 할인", 1, 31, MenuCategory.DESSERT, 2_023,
            (visitDate) -> visitDate % 7 != 1 && visitDate % 7 != 2), WEEKENDS("주말 할인", 1, 31, MenuCategory.MAIN, 2_203,
            (visitDate) -> visitDate % 7 == 1 || visitDate % 7 == 2);

    private final String name;
    private final int startDate;
    private final int endDate;
    private final MenuCategory menuCategory;
    private final long discountAmount;
    private final Predicate<Integer> isApplicable;

    DayRelatedEvent(String name, int startDate, int endDate, MenuCategory menuCategory, long discountAmount,
                    Predicate<Integer> isApplicable) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.menuCategory = menuCategory;
        this.discountAmount = discountAmount;
        this.isApplicable = isApplicable;
    }

    public String getName() {
        return name;
    }

    public static boolean isInProgress(int visitDate) {
        boolean isInProgress = false;
        for (DayRelatedEvent dayRelatedEvent : DayRelatedEvent.values()) {
            if (visitDate >= dayRelatedEvent.startDate && visitDate <= dayRelatedEvent.endDate) {
                isInProgress = true;
            }
        }
        return isInProgress;
    }

    public static DayRelatedEvent getAppliedEvent(int visitDate) {
        return Arrays.stream(DayRelatedEvent.values()).filter(event -> event.isApplicable.test(visitDate)).findAny()
                .orElseThrow();
    }

    public long getDiscountedAmount(Orders orders) {
        return orders.getQuantityOf(menuCategory) * discountAmount;
    }
}
