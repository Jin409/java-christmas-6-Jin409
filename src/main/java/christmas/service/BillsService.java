package christmas.service;

import christmas.model.Bills;
import christmas.model.Bills.DiscountedHistory;
import christmas.model.Bills.OfferedMenu;
import christmas.model.Orders;
import christmas.model.event.DayRelatedEvent;
import christmas.model.event.DdayEvent;
import christmas.model.event.OfferEvent;
import christmas.model.event.SpecialEvent;
import java.util.ArrayList;
import java.util.List;

public class BillsService {

    private static final long EVENT_APPLICABLE_BOUNDARY = 10_000;

    public Bills getBills(final Orders orders) {
        if (orders.getOriginPrice() < EVENT_APPLICABLE_BOUNDARY) {
            return new Bills(orders, null, null);
        }

        OfferedMenu offeredMenu = null;
        List<DiscountedHistory> discountedHistories = getDiscountedHistory(orders, orders.getVisitDate());
        return new Bills(orders, offeredMenu, discountedHistories);
    }

    private List<DiscountedHistory> getDiscountedHistory(final Orders orders, final int visitDate) {
        List<DiscountedHistory> discountedHistories = new ArrayList<>();

        Bills.DiscountedHistory specialEventHistory = applySpecialEvent(visitDate);
        if (specialEventHistory != null) {
            discountedHistories.add(specialEventHistory);
        }

        Bills.DiscountedHistory offerEventHistory = applyOfferEvent(visitDate, orders.getOriginPrice());
        if (offerEventHistory != null) {
            discountedHistories.add(offerEventHistory);
        }

        Bills.DiscountedHistory dDayEventHistory = applyDdayEvent(visitDate);
        if (dDayEventHistory != null) {
            discountedHistories.add(dDayEventHistory);
        }

        Bills.DiscountedHistory dayRelatedEventHistory = applyDayRelatedEvent(visitDate, orders);
        if (dayRelatedEventHistory != null) {
            discountedHistories.add(dayRelatedEventHistory);
        }

        return discountedHistories;
    }

    private Bills.DiscountedHistory applySpecialEvent(int visitDate) {
        long discountedAmount = 0;
        if (SpecialEvent.isInProgress(visitDate)) {
            SpecialEvent appliedEvent = SpecialEvent.getAppliedEvent(visitDate);
            discountedAmount = appliedEvent.getDiscountedAmount(visitDate);
            return new Bills.DiscountedHistory(appliedEvent.getName(), discountedAmount);
        }
        return null;
    }

    private Bills.DiscountedHistory applyOfferEvent(int visitDate, long originPrice) {
        long discountedAmount = 0;
        if (OfferEvent.isInProgress(visitDate)) {
            OfferEvent appliedEvent = OfferEvent.getAppliedEvent(visitDate);
            discountedAmount = appliedEvent.getDiscountedAmount(visitDate, originPrice);
            return new Bills.DiscountedHistory(appliedEvent.getName(), discountedAmount);
        }
        return null;
    }

    private Bills.DiscountedHistory applyDdayEvent(int visitDate) {
        long discountedAmount = 0;
        if (DdayEvent.isInProgress(visitDate)) {
            DdayEvent appliedEvent = DdayEvent.getAppliedEvent(visitDate);
            discountedAmount = appliedEvent.getDiscountedAmount(visitDate);
            return new Bills.DiscountedHistory(appliedEvent.getName(), discountedAmount);
        }
        return null;
    }

    private Bills.DiscountedHistory applyDayRelatedEvent(int visitDate, Orders orders) {
        long discountedAmount = 0;
        if (DayRelatedEvent.isInProgress(visitDate)) {
            DayRelatedEvent appliedEvent = DayRelatedEvent.getAppliedEvent(visitDate);
            discountedAmount = appliedEvent.getDiscountedAmount(orders);
            return new Bills.DiscountedHistory(appliedEvent.getName(), discountedAmount);
        }
        return null;
    }

}
