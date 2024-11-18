package christmas.model.event;

import java.util.Arrays;
import java.util.function.Predicate;

public enum OfferEvent {
    CHAMPAGNE_OFFER("증정 이벤트", 1, 31, originPrice -> originPrice > 120_000, 25_000, "샴페인");

    private final String name;
    private final int startDate;
    private final int endDate;
    private final Predicate<Long> isApplicable;
    private final long discountedAmount;
    private final String menuName;

    OfferEvent(String name, int startDate, int endDate, Predicate<Long> isApplicable, long discountedAmount,
               String menuName) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApplicable = isApplicable;
        this.discountedAmount = discountedAmount;
        this.menuName = menuName;
    }

    public String getName() {
        return name;
    }

    public String getMenuName() {
        return menuName;
    }

    public static boolean isInProgress(int visitDate) {
        boolean isInProgress = false;
        for (OfferEvent offerEvent : OfferEvent.values()) {
            if (visitDate > offerEvent.startDate && visitDate < offerEvent.endDate) {
                isInProgress = true;
            }
        }
        return isInProgress;
    }

    public static OfferEvent getAppliedEvent(int visitDate, long originPrice) {
        return Arrays.stream(OfferEvent.values())
                .filter(event -> event.startDate <= visitDate && event.endDate >= visitDate && event.isApplicable.test(
                        originPrice)).findAny().orElseThrow();
    }

    public long getDiscountedAmount() {
        return discountedAmount;
    }

}
