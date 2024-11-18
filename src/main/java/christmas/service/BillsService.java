package christmas.service;

import christmas.dto.BillsResponseDto;
import christmas.dto.BillsResponseDto.BadgeResponseDto;
import christmas.dto.BillsResponseDto.DiscountedHistoryDto;
import christmas.dto.BillsResponseDto.OfferedMenuDto;
import christmas.dto.BillsResponseDto.OrderedMenuDto;
import christmas.model.Badge;
import christmas.model.Bills;
import christmas.model.Bills.DiscountedHistory;
import christmas.model.Bills.OfferedMenu;
import christmas.model.Menu;
import christmas.model.MenuCategory;
import christmas.model.MenuRepository;
import christmas.model.Order;
import christmas.model.Orders;
import christmas.model.event.DayRelatedEvent;
import christmas.model.event.DdayEvent;
import christmas.model.event.OfferEvent;
import christmas.model.event.SpecialEvent;
import java.util.ArrayList;
import java.util.List;

public class BillsService {

    private static final long EVENT_APPLICABLE_BOUNDARY = 10_000;

    private final MenuRepository menuRepository;

    public BillsService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public BillsResponseDto getBillsResponseDto(Bills bills) {
        Orders orders = bills.getOrders();
        List<OrderedMenuDto> orderedMenuDtos = getOrderedMenuDtos(orders.getOrders());
        List<OfferedMenuDto> offeredMenuDtos = getOfferedMenuDtos(bills.getOfferedMenus());
        List<DiscountedHistoryDto> discountedHistoryDtos = getDiscountedHistoryDtos(bills.getDiscountedHistories());
        BadgeResponseDto badgeResponseDto = new BadgeResponseDto(bills.getNameOfBadge());

        return BillsResponseDto.from(orderedMenuDtos, orders.getOriginPrice(), offeredMenuDtos, discountedHistoryDtos,
                badgeResponseDto);
    }

    private List<OfferedMenuDto> getOfferedMenuDtos(List<OfferedMenu> offeredMenus) {
        if (offeredMenus == null) {
            return null;
        }

        return offeredMenus.stream()
                .map(offeredMenu -> new OfferedMenuDto(offeredMenu.getNameOfMenu(), offeredMenu.getQuantity()))
                .toList();
    }

    private List<DiscountedHistoryDto> getDiscountedHistoryDtos(List<DiscountedHistory> discountedHistories) {
        if (discountedHistories == null) {
            return null;
        }
        return discountedHistories.stream()
                .map(history -> new DiscountedHistoryDto(history.getEventName(), history.getDiscountedAmount()))
                .toList();
    }

    private List<OrderedMenuDto> getOrderedMenuDtos(List<Order> orders) {
        return orders.stream().map(order -> new OrderedMenuDto(order.getNameOfMenu(), order.getQuantity())).toList();
    }

    public Bills getBills(final Orders orders) {
        int visitDate = orders.getVisitDate();

        if (orders.getOriginPrice() < EVENT_APPLICABLE_BOUNDARY) {
            return new Bills(orders, null, null, null);
        }

        List<DiscountedHistory> discountedHistories = getDiscountedHistory(orders, visitDate);

        List<OfferedMenu> offeredMenus = null;
        return new Bills(orders, getOfferedMenus(visitDate, orders.getOriginPrice()), discountedHistories,
                findBadge(getTotalDiscountedAmount(discountedHistories)));
    }

    private long getTotalDiscountedAmount(List<DiscountedHistory> discountedHistories) {
        return discountedHistories.stream().mapToLong(DiscountedHistory::getDiscountedAmount).sum();
    }

    private List<OfferedMenu> getOfferedMenus(int visitDate, long originPrice) {
        if (OfferEvent.isInProgress(visitDate)) {
            OfferEvent appliedEvent = OfferEvent.getAppliedEvent(visitDate, originPrice);
            return List.of(new OfferedMenu(findMenuWithName(appliedEvent.getMenuName()), 1));
        }
        return null;
    }

    private Menu findMenuWithName(String name) {
        return menuRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 상품이 존재하지 않습니다."));
    }

    private Badge findBadge(long totalDiscountedAmount) {
        return Badge.findByDiscountedAmount(totalDiscountedAmount);
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
            OfferEvent appliedEvent = OfferEvent.getAppliedEvent(visitDate, originPrice);
            discountedAmount = appliedEvent.getDiscountedAmount();
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
