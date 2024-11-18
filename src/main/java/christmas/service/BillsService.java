package christmas.service;

import christmas.model.Bills;
import christmas.model.Bills.DiscountedHistory;
import christmas.model.Bills.OfferedMenu;
import christmas.model.Orders;
import christmas.model.event.DayRelatedEvent;
import christmas.model.event.DdayEvent;
import christmas.model.event.OfferEvent;
import christmas.model.event.SpecialEvent;

public class BillsService {

    public void applyEvents(final Orders orders) {
        OfferedMenu offeredMenu = null;

        long originPrice = orders.getOriginPrice();
        int visitDate = orders.getVisitDate();

        long discountedAmountOfOfferEvent = OfferEvent.getDiscountedAmount(visitDate, originPrice);
        if (discountedAmountOfOfferEvent > 0) {
            offeredMenu = new OfferedMenu(null, 1);
        }

        long discountedAmountOfDdayEvent = DdayEvent.getDiscountedAmount(visitDate);
        if (DayRelatedEvent.isInProgress(visitDate)) {
            long discountedAmountOfDayRelatedEvent = DayRelatedEvent.getDiscountedAmount(visitDate, orders);
        }
        long discountedAmountOfSpecialEvent = SpecialEvent.getDiscountedAmount(visitDate);
    }

}
