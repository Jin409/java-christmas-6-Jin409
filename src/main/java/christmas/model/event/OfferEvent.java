package christmas.model.event;

import java.util.function.Predicate;

public enum OfferEvent {
    CHAMPAGNE_OFFER(1, 31, originPrice -> originPrice > 120_000, 25_000, "샴페인");

    private final int startDate;
    private final int endDate;
    private final Predicate<Long> isApplicable;
    private final long discountedAmount;
    private final String menuName;

    OfferEvent(int startDate, int endDate, Predicate<Long> isApplicable, long discountedAmount, String menuName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApplicable = isApplicable;
        this.discountedAmount = discountedAmount;
        this.menuName = menuName;
    }

    public static long getDiscountedAmount(int visitDate, long originPrice) {
        long discountedAmount = 0;

        for (OfferEvent offerEvent : OfferEvent.values()) {
            if (visitDate > offerEvent.startDate && visitDate < offerEvent.endDate && offerEvent.isApplicable.test(
                    originPrice)) {
                discountedAmount += offerEvent.discountedAmount;
            }
        }
        return discountedAmount;
    }

}
